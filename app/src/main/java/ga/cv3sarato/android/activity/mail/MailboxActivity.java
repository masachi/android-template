package ga.cv3sarato.android.activity.mail;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chenenyu.router.Router;
import com.chenenyu.router.annotation.InjectParam;
import com.chenenyu.router.annotation.Route;
import ga.cv3sarato.android.R;
import ga.cv3sarato.android.adapter.mail.MailBoxAdapter;
import ga.cv3sarato.android.base.BaseToolbarActivity;
import ga.cv3sarato.android.entity.response.mail.MailEntity;
import ga.cv3sarato.android.net.MailApi;
import ga.cv3sarato.android.net.observer.HylaaObserver;
import ga.cv3sarato.android.service.MailService;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


@Route(value = "gtedx://mailBox")
public class MailboxActivity extends BaseToolbarActivity {

    @BindView(R.id.mailbox_recyclerView)
    RecyclerView mailboxRecyclerView;

    private List<MailEntity> mailList = new ArrayList<>();
    private MailBoxAdapter mailAdapter;

    @InjectParam(key = "address")
    String mailAddress;

    @InjectParam(key = "type")
    String type;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Router.injectParams(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void init() throws Exception {
        mailAdapter = new MailBoxAdapter(this, mailList, R.layout.item_mail_box);
        mailboxRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mailboxRecyclerView.setAdapter(mailAdapter);

        initMailBox();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mailbox;
    }

    private void initMailBox() {
        switch (type) {
            case "INBOX":
                getInboxData();
                break;
            case "DRAFT":
                getDraftData();
                break;
            case "SENT":
                getSentData();
                break;
            default:
                break;
        }
    }

    private void getInboxData () {
        MailApi.defaultInstance()
                .create(MailService.class)
                .getInboxMails(mailAddress)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HylaaObserver<List<MailEntity>>() {
                    @Override
                    public void onNext(List<MailEntity> mailEntities) {
                        mailList.clear();
                        mailList.addAll(mailEntities);
                        mailAdapter.notifyDataSetChanged();
                    }
                });

    }

    private void getDraftData () {

    }

    private void getSentData () {

    }
}
