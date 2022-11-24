package ga.cv3sarato.android.activity.contact;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.chenenyu.router.Router;
import com.chenenyu.router.annotation.InjectParam;
import com.chenenyu.router.annotation.Route;
import ga.cv3sarato.android.R;
import ga.cv3sarato.android.adapter.contact.ContactFilterAdapter;
import ga.cv3sarato.android.base.BaseActivity;
import ga.cv3sarato.android.base.BaseRecyclerAdapter;
import ga.cv3sarato.android.entity.response.contact.ContactEntity;
import com.wuhenzhizao.titlebar.statusbar.StatusBarUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

@Route(value = "gtedx://contactFilter")
public class ContactFilterActivity extends BaseActivity implements BaseRecyclerAdapter.OnItemClickListener, TextWatcher {

    @BindView(R.id.contact_filter)
    Button contactFilter;
    @BindView(R.id.contact_filter_recyclerView)
    RecyclerView contactFilterRecyclerView;
    @BindView(R.id.contact_filter_textInput)
    EditText contactFilterTextInput;

    ArrayList<ContactEntity.ContactItem> data;
    ArrayList<ContactEntity.ContactItem> filteredData = new ArrayList<>();

    private ContactFilterAdapter filterAdapter;

    @InjectParam(key = "data")
    Bundle bundle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Router.injectParams(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void init() throws Exception {
        StatusBarUtils.setStatusBarColor(getWindow(), Color.parseColor("#5587ea"), 0);
        data = (ArrayList<ContactEntity.ContactItem>) bundle.getSerializable("data");
        filterAdapter = new ContactFilterAdapter(this, data, R.layout.item_contact_filter, filteredData);
        contactFilterRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        contactFilterRecyclerView.setAdapter(filterAdapter);
        filterAdapter.setOnItemClickListener(this);
        contactFilterTextInput.addTextChangedListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_contact_filter;
    }

    @OnClick(R.id.contact_filter)
    public void onViewClicked() {
        super.onBackPressed();
    }

    @Override
    public void onItemClickListener(View v, int position) {
        Router.build("gtedx://contactDetail").with("id", filterAdapter.getFilteredData().get(position).getId()).go(this);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void afterTextChanged(Editable editable) {
        filterAdapter.getFilter().filter(editable.toString());
    }
}
