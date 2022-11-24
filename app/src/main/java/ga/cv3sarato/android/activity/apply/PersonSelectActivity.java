package ga.cv3sarato.android.activity.apply;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.chenenyu.router.Router;
import com.chenenyu.router.annotation.InjectParam;
import com.chenenyu.router.annotation.Route;
import com.daimajia.swipe.SwipeLayout;
import ga.cv3sarato.android.R;
import ga.cv3sarato.android.adapter.apply.PersonSelectAdapter;
import ga.cv3sarato.android.base.BaseRecyclerAdapter;
import ga.cv3sarato.android.base.BaseToolbarActivity;
import ga.cv3sarato.android.common.Request;
import ga.cv3sarato.android.entity.response.contact.ContactEntity;
import ga.cv3sarato.android.entity.response.contact.ContactSimpleEntity;
import ga.cv3sarato.android.net.ServerApi;
import ga.cv3sarato.android.net.observer.HylaaObserver;
import ga.cv3sarato.android.service.ContactService;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@Route(value = "gtedx://personSelect")
public class PersonSelectActivity extends BaseToolbarActivity implements BaseRecyclerAdapter.OnItemClickListener,View.OnClickListener {

    @BindView(R.id.person_select_recyclerView)
    RecyclerView personSelectRecyclerView;

    @InjectParam(key = "selected")
    Serializable selected;

    private PersonSelectAdapter selectAdapter;
    private List<ContactEntity.ContactItem> staffs = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Router.injectParams(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void init() throws Exception {
        Button confirmBtn = new Button(this);
        confirmBtn.setText("确定");
        confirmBtn.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        confirmBtn.setOnClickListener(this);
        toolbar.setRightView(confirmBtn);

        selectAdapter = new PersonSelectAdapter(this, staffs, R.layout.item_person_select);
        personSelectRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        personSelectRecyclerView.setAdapter(selectAdapter);
        selectAdapter.setOnItemClickListener(this);

        getSimpleContact();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_person_select;
    }

    private void getSimpleContact() {
        ServerApi.defaultInstance()
                .create(ContactService.class)
                .getSimpleContact(new Request(new Object()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HylaaObserver<ContactSimpleEntity>() {
                    @Override
                    public void onNext(ContactSimpleEntity contactSimpleEntity) {
                        staffs.clear();
                        staffs.addAll(contactSimpleEntity.getStaffs());
                        restoreSelected();
                    }
                });
    }

    private void restoreSelected() {
        ArrayList<ContactEntity.ContactItem> selectedItems = (ArrayList<ContactEntity.ContactItem>) selected;
        if(selectedItems.size() > 0) {
            for(ContactEntity.ContactItem item : selectedItems) {
                System.out.println(staffs.indexOf(item));
                if(staffs.indexOf(item) >= 0) {
                    selectAdapter.setItemChecked(staffs.indexOf(item), true);
                }
            }
        }
        selectAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClickListener(View v, int position) {
        switch (v.getId()) {
            case R.id.person_select_checkBox:
                if(selectAdapter.isItemChecked(position)) {
                    selectAdapter.setItemChecked(position,false);
                }
                else {
                    selectAdapter.setItemChecked(position,true);
                }
                break;
            case R.id.person_select_frame:
                if(selectAdapter.isItemChecked(position)) {
                    selectAdapter.setItemChecked(position,false);
                }
                else {
                    selectAdapter.setItemChecked(position,true);
                }
                selectAdapter.notifyItemChanged(position);
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View view) {
        if(view instanceof TextView) {
            Intent result = new Intent();
            result.putExtra("selected", selectAdapter.getSelecedItem());
            this.setResult(RESULT_OK, result);
            finish();
        }
    }
}
