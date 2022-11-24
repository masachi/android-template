package ga.cv3sarato.android.activity.contact;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import com.chenenyu.router.Router;
import com.chenenyu.router.annotation.InjectParam;
import com.chenenyu.router.annotation.Route;
import ga.cv3sarato.android.R;
import ga.cv3sarato.android.adapter.contact.ContactAdapter;
import ga.cv3sarato.android.base.BaseRecyclerAdapter;
import ga.cv3sarato.android.base.BaseToolbarActivity;
import ga.cv3sarato.android.common.Request;
import ga.cv3sarato.android.constant.RequestCode;
import ga.cv3sarato.android.entity.response.contact.ContactEntity;
import ga.cv3sarato.android.entity.response.contact.ContactStaffEntity;
import ga.cv3sarato.android.net.ServerApi;
import ga.cv3sarato.android.net.observer.HylaaObserver;
import ga.cv3sarato.android.service.ContactService;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;
import com.viethoa.RecyclerViewFastScroller;
import com.viethoa.models.AlphabetItem;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@Route(value = "gtedx://contact")
public class ContactActivity extends BaseToolbarActivity implements BaseRecyclerAdapter.OnItemClickListener {

    @BindView(R.id.contact_recyclerView)
    RecyclerView contactRecyclerView;
    @BindView(R.id.contact_fast_scroller)
    RecyclerViewFastScroller contactFastScroller;
    @BindView(R.id.contact_filter_frame)
    RelativeLayout contactFilterFrame;

    private List<AlphabetItem> mAlphabetItems;
    private ContactAdapter adapter;

    private List<ContactEntity> alphabetData = new ArrayList<>();
    private ArrayList<ContactEntity.ContactItem> data = new ArrayList<>();

    @InjectParam(key = "mode")
    int mode;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Router.injectParams(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void init() throws Exception {
        getContact();
        contactFastScroller.setVisibility(View.GONE);
        adapter = new ContactAdapter(this, data, R.layout.item_contact);
        contactRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        contactRecyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
        if(mode != 0) {
            contactFilterFrame.setVisibility(View.GONE);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_contact;
    }


    private void getContact() {
        ServerApi.defaultInstance()
                .create(ContactService.class)
                .getSortedContact(new Request(new Object()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HylaaObserver<ContactStaffEntity>() {
                    @Override
                    public void onNext(ContactStaffEntity o) {
                        Iterator iterator = o.getStaff().entrySet().iterator();
                        while (iterator.hasNext()) {
                            TreeMap.Entry staff = (TreeMap.Entry) iterator.next();
                            alphabetData.add(new ContactEntity((String) staff.getKey(), (List<ContactEntity.ContactItem>) staff.getValue()));
                            for (ContactEntity.ContactItem item : (List<ContactEntity.ContactItem>) staff.getValue()) {
                                item.setAlphabet((String) staff.getKey());
                                data.add(item);
                            }
                        }

                        setFastScroller();
                    }
                });
    }

    private void setFastScroller() {
        mAlphabetItems = new ArrayList<>();
        for (int i = 0; i < alphabetData.size(); i++) {
            mAlphabetItems.add(new AlphabetItem(i, alphabetData.get(i).getTitle(), false));
        }
        final StickyRecyclerHeadersDecoration headersDecor = new StickyRecyclerHeadersDecoration(adapter);
        contactRecyclerView.addItemDecoration(headersDecor);

        contactFastScroller.setRecyclerView(contactRecyclerView);
        contactFastScroller.setUpAlphabet(mAlphabetItems);
        contactFastScroller.setVisibility(View.VISIBLE);
    }

    @Override
    public void onItemClickListener(View v, int position) {
        switch (mode){
            case 0:
                Router.build("gtedx://contactDetail").with("id", data.get(position).getId()).go(this);
                break;
            case RequestCode.AT_PERSON_SELECT:
                Intent resultIntent = new Intent();
                resultIntent.putExtra("id", data.get(position).getId());
                resultIntent.putExtra("name", data.get(position).getName());
                this.setResult(RESULT_OK, resultIntent);
                finish();
                break;
        }
    }

    @OnClick(R.id.contact_filter_frame)
    public void onViewClicked() {
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", data);
        Router.build("gtedx://contactFilter").with("data", bundle).go(this);
    }
}
