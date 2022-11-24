package ga.cv3sarato.android.fragment;


import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.chenenyu.router.Router;
import com.chenenyu.router.annotation.InjectParam;
import com.chenenyu.router.annotation.Route;
import ga.cv3sarato.android.MainApplication;
import ga.cv3sarato.android.R;
import ga.cv3sarato.android.entity.request.user.QREntity;
import ga.cv3sarato.android.net.DomainApi;
import ga.cv3sarato.android.net.observer.HylaaObserver;
import ga.cv3sarato.android.service.UserService;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */

@Route(value = "gtedx://qr_result")
public class QRResultFragment extends DialogFragment {


    @BindView(R.id.qr_login_imageBtn)
    Button qrLoginImageBtn;
    @BindView(R.id.qr_cancel_imageBtn)
    Button qrCancelImageBtn;
    Unbinder unbinder;

    @InjectParam(key = "qr_result")
    String result;

    public QRResultFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Router.injectParams(this);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        super.onActivityCreated(savedInstanceState);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        getDialog().getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        getDialog().getWindow().setWindowAnimations(R.style.dialog_animation_fade);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_qrresult, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.qr_login_imageBtn, R.id.qr_cancel_imageBtn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.qr_login_imageBtn:
                QREntity request = new QREntity(result.substring(result.indexOf("=") + 1, result.length()), MainApplication.getInstance().getRefreshToken());
                DomainApi.getInstance()
                        .create(UserService.class)
                        .authorizeQRCode(request)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new HylaaObserver<Object>() {
                            @Override
                            public void onNext(Object o) {
                                getActivity().finish();
                            }
                        });
                break;
            case R.id.qr_cancel_imageBtn:
                this.dismiss();
                break;
        }
    }
}
