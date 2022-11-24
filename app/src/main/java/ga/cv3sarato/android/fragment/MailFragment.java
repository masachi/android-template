package ga.cv3sarato.android.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chenenyu.router.Router;
import ga.cv3sarato.android.R;
import ga.cv3sarato.android.common.Request;
import ga.cv3sarato.android.entity.response.user.DefaultMailEntity;
import ga.cv3sarato.android.net.ServerApi;
import ga.cv3sarato.android.net.observer.HylaaObserver;
import ga.cv3sarato.android.service.MailService;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class MailFragment extends Fragment {


    @BindView(R.id.status_bar)
    View statusBar;
    @BindView(R.id.mail_inbox)
    LinearLayout mailInbox;
    @BindView(R.id.mail_draft)
    LinearLayout mailDraft;
    @BindView(R.id.mail_send)
    LinearLayout mailSend;
    @BindView(R.id.mail_relate)
    LinearLayout mailRelate;
    Unbinder unbinder;
    @BindView(R.id.default_email_textView)
    TextView defaultEmailTextView;

    public MailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_mail, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void initDefaultMailAddress() {
        ServerApi.defaultInstance()
                .create(MailService.class)
                .getDefaultMailAddress(new Request(new Object()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HylaaObserver<DefaultMailEntity>() {
                    @Override
                    public void onNext(DefaultMailEntity defaultMailEntity) {
                        defaultEmailTextView.setText(defaultMailEntity.getEmail().equals("") ? "masachi.zhang@gtedx.com" : defaultMailEntity.getEmail());
                    }
                });
    }

    @OnClick({R.id.mail_inbox, R.id.mail_draft, R.id.mail_send, R.id.mail_relate})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mail_inbox:
                Router.build("gtedx://mailBox")
                        .with("address", "masachi.zhang@gtedx.com")
                        .with("type", "INBOX")
                        .go(this);
                break;
            case R.id.mail_draft:
//                Router.build("gtedx://mailBox")
//                        .with("address", "masachi.zhang@gtedx.com")
//                        .with("type", "DRAFT")
//                        .go(this);
                break;
            case R.id.mail_send:
//                Router.build("gtedx://mailBox").go(this);
                break;
            case R.id.mail_relate:
                break;
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        initDefaultMailAddress();
    }
}
