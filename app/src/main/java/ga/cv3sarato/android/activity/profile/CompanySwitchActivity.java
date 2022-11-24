package ga.cv3sarato.android.activity.profile;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chenenyu.router.Router;
import com.chenenyu.router.annotation.InjectParam;
import com.chenenyu.router.annotation.Route;
import ga.cv3sarato.android.MainApplication;
import ga.cv3sarato.android.R;
import ga.cv3sarato.android.adapter.user.CompanySwitchAdapter;
import ga.cv3sarato.android.base.BaseRecyclerAdapter;
import ga.cv3sarato.android.base.BaseToolbarActivity;
import ga.cv3sarato.android.common.Request;
import ga.cv3sarato.android.entity.response.user.CompanyEntity;
import ga.cv3sarato.android.net.ServerApi;
import ga.cv3sarato.android.net.interceptor.HttpHeaderInterceptor;
import ga.cv3sarato.android.net.observer.HylaaObserver;
import ga.cv3sarato.android.service.LoginService;
import ga.cv3sarato.android.service.UserService;
import ga.cv3sarato.android.utils.persistence.SharedPreferenceUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@Route(value = "gtedx://companySwitch")
public class CompanySwitchActivity extends BaseToolbarActivity implements BaseRecyclerAdapter.OnItemClickListener, View.OnClickListener{

    @BindView(R.id.company_switch_icon_imageView)
    ImageView companySwitchIconImageView;
    @BindView(R.id.company_select_current_textView)
    TextView companySelectCurrentTextView;
    @BindView(R.id.company_select_recyclerView)
    RecyclerView companySelectRecyclerView;

    @InjectParam(key = "company")
    Bundle bundle;

    private ArrayList<CompanyEntity> companyData = new ArrayList<>();
    private CompanySwitchAdapter switchAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Router.injectParams(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void init() throws Exception {
        Button switchBtn = new Button(this);
        switchBtn.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        switchBtn.setText("切换");
        switchBtn.setOnClickListener(this);
        toolbar.setRightView(switchBtn);

        companyData = (ArrayList<CompanyEntity>) bundle.getSerializable("data");
        switchAdapter = new CompanySwitchAdapter(this, companyData, R.layout.item_company_switch);
        companySelectRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        companySelectRecyclerView.setAdapter(switchAdapter);
        switchAdapter.setOnItemClickListener(this);
        if(companyData.size() == 0) {
            getCompanyList();
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_company_switch;
    }

    private void getCompanyList() {
        ServerApi.getInstance(new HttpHeaderInterceptor.Builder()
                .addHeaderParams("Authorization", "Bearer " + MainApplication.getInstance().getAccessToken())
                .addHeaderParams("Content-Type", "application/json")
                .build())
                .create(UserService.class)
                .getCompanyList(new Request(new Object()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HylaaObserver<ArrayList<CompanyEntity>>() {
                    @Override
                    public void onNext(ArrayList<CompanyEntity> companyEntities) {
                        companyData.clear();
                        companyData.addAll(companyEntities);
                        switchAdapter.notifyDataSetChanged();
                    }
                });
    }

    @Override
    public void onItemClickListener(View v, int position) {
        switch (v.getId()) {
            case R.id.company_select_radio_btn:
                break;

            case R.id.company_select_sub_company_btn:
                Bundle bundle = new Bundle();
                bundle.putSerializable("data", companyData.get(position).getSubsets());
                Router.build("gtedx://companySwitch")
                        .with("company", bundle)
                        .go(this);
                break;
        }
    }

    @Override
    public void onClick(View view) {
        MainApplication.getInstance().setTenantID(companyData.get(switchAdapter.getSelectedPosition()).getId());
        String username = (String) SharedPreferenceUtils.newInstance(this).getData("username", new String());
        String password = (String) SharedPreferenceUtils.newInstance(this).getData("password", new String());
        SharedPreferenceUtils.newInstance(this).setData("tenantID", MainApplication.getInstance().getTenantID());
        LoginService.loginIntoSystem(this, username, password, false, this);
    }
}
