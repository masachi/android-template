package ga.cv3sarato.android.service;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.chenenyu.router.Router;
import ga.cv3sarato.android.MainApplication;
import ga.cv3sarato.android.R;
import ga.cv3sarato.android.common.Request;
import ga.cv3sarato.android.constant.StartPageType;
import ga.cv3sarato.android.entity.response.user.StartPageEntity;
import ga.cv3sarato.android.net.IAMBackendApi;
import ga.cv3sarato.android.net.interceptor.HttpHeaderInterceptor;
import ga.cv3sarato.android.entity.request.user.LoginEntity;
import ga.cv3sarato.android.entity.response.user.CompanyEntity;
import ga.cv3sarato.android.entity.response.common.TokenEntity;
import ga.cv3sarato.android.entity.common.UserEntity;
import ga.cv3sarato.android.net.IAMApi;
import ga.cv3sarato.android.net.ServerApi;
import ga.cv3sarato.android.net.ServerException;
import ga.cv3sarato.android.net.observer.HylaaObserver;
import ga.cv3sarato.android.utils.persistence.SharedPreferenceUtils;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class LoginService {

    public static void login(Context context, String username, String password, boolean splash, Activity activity) {
        Request<LoginEntity> iamRequest = new Request<>();
        iamRequest.setBody(new LoginEntity(username));
        iamRequest.setSecure(new HashMap<String, String>() {{
            put("password", password);
        }});
        IAMBackendApi.getInstance()
                .create(UserService.class)
                .loginIAM(iamRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .flatMap(new Function<TokenEntity, ObservableSource<ArrayList<CompanyEntity>>>() {
                    @Override
                    public ObservableSource<ArrayList<CompanyEntity>> apply(TokenEntity tokenEntity) throws Exception {
                        if (tokenEntity.getAccessToken() != null) {
                            MainApplication.getInstance().setAccessToken(tokenEntity.getAccessToken());
                            MainApplication.getInstance().setRefreshToken(tokenEntity.getRefreshToken());
                            return ServerApi.getInstance(new HttpHeaderInterceptor.Builder()
                                    .addHeaderParams("Authorization", "Bearer " + tokenEntity.getAccessToken())
                                    .addHeaderParams("Content-Type", "application/json")
                                    .build())
                                    .create(UserService.class)
                                    .getCompanyList(new Request(new Object()));
                        } else {
                            throw new ServerException(500, "IAM Down");
                        }
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HylaaObserver<ArrayList<CompanyEntity>>() {
                    @Override
                    public void onNext(ArrayList<CompanyEntity> companyEntities) {
                        if(companyEntities.size() == 0) {
                            System.exit(500);
                        }

                        if (MainApplication.getInstance().getTenantID().equals("")) {
                            if (companyEntities.size() == 1) {
                                MainApplication.getInstance().setTenantID(companyEntities.get(0).getId());
                                SharedPreferenceUtils.newInstance(context).setData("tenantID", MainApplication.getInstance().getTenantID());
                                loginIntoSystem(context, username, password, splash, activity);
                            } else {
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("data", companyEntities);
                                Router.build("gtedx://companySelect")
                                        .with("company", bundle)
                                        .with("username", username)
                                        .with("password", password)
                                        .with("splash", splash)
                                        .go(activity);
                            }
                        } else {
                            loginIntoSystem(context, username, password, splash, activity);
                        }
                    }
                });
    }

    public static void loginIntoSystem(Context context, String username, String password, boolean splash, Activity activity) {
        ServerApi.defaultInstance()
                .create(UserService.class)
                .login(new Request())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HylaaObserver<UserEntity>() {
                    @Override
                    public void onNext(UserEntity userEntity) {
                        MainApplication.getInstance().setUser(userEntity);
                        SharedPreferenceUtils.newInstance(context).setData("username", username);
                        SharedPreferenceUtils.newInstance(context).setData("password", password);
                        SharedPreferenceUtils.newInstance(context).setData("splash", userEntity.getTenant().getLogo());
//                        Router.build("gtedx://main").with("page", 0).go(context);
//                        activity.finish();
                        if (splash) {
                            activity.findViewById(R.id.skip_btn).setVisibility(View.VISIBLE);
                        } else {
                            Router.build("gtedx://main")
                                    .with("page", 0)
                                    .go(context);
                            activity.finishAffinity();
                        }
                        getStartPage(context);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        MainApplication.getInstance().Toast.setText("Login Error").withIcon(false).show();
                        if (splash) {
//                            Router.build("gtedx://login").go(context);
//                            activity.finish();
                            activity.findViewById(R.id.skip_btn).setVisibility(View.VISIBLE);
                        } else {
                            Router.build("gtedx://login").go(context);
                            activity.finishAffinity();
                        }
                    }
                });
    }

    public static void getStartPage(Context context) {
        HashMap<String, StartPageEntity.StartPageItem> pageMap = new HashMap<>();
        ServerApi.defaultInstance()
                .create(UserService.class)
                .getStartPages(new Request(new Object()))
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(new HylaaObserver<StartPageEntity>() {
                    @Override
                    public void onNext(StartPageEntity startPageEntity) {
                        for (StartPageEntity.StartPageItem startPageItem : startPageEntity.getStartPages()) {
                            pageMap.put(startPageItem.getType(), startPageItem);
                        }
                        float density = context.getResources().getDisplayMetrics().density;
                        if (density <= 1.0) {
                            getProperStartPage(context, pageMap, "S");
                        } else if (density <= 2.0) {
                            getProperStartPage(context, pageMap, "M");
                        } else if (density <= 3.0) {
                            getProperStartPage(context, pageMap, "L");
                        } else {
                            getProperStartPage(context, pageMap, "XL");
                        }
                    }
                });
    }

    private static void getProperStartPage(Context context, HashMap<String, StartPageEntity.StartPageItem> pageMap, String type) {
        String url = "";
        int duration = 5;
        String existPageSize = "";
        if (pageMap.containsKey(type)) {
            existPageSize = type;
        } else if (pageMap.containsKey(StartPageType.XL)) {
            existPageSize = StartPageType.XL;
        } else if (pageMap.containsKey(StartPageType.L)) {
            existPageSize = StartPageType.L;
        } else if (pageMap.containsKey(StartPageType.M)) {
            existPageSize = StartPageType.M;
        } else if (pageMap.containsKey(StartPageType.S)) {
            existPageSize = StartPageType.S;
        } else {
            existPageSize = "";
        }

        if (!existPageSize.equals("")) {
            url = pageMap.get(existPageSize).getUrl();
            duration = pageMap.get(existPageSize).getDuration();
        }

        SharedPreferenceUtils.newInstance(context).setData("splash", url);
        SharedPreferenceUtils.newInstance(context).setData("duration", duration * 1000);
    }

}
