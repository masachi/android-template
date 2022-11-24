package ga.cv3sarato.android.activity.login;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chenenyu.router.Router;
import com.chenenyu.router.annotation.InjectParam;
import com.chenenyu.router.annotation.Route;
import ga.cv3sarato.android.MainApplication;
import ga.cv3sarato.android.R;
import ga.cv3sarato.android.adapter.user.CompanySelectAdapter;
import ga.cv3sarato.android.base.BaseRecyclerAdapter;
import ga.cv3sarato.android.base.BaseToolbarActivity;
import ga.cv3sarato.android.entity.response.user.CompanyEntity;
import ga.cv3sarato.android.service.LoginService;
import ga.cv3sarato.android.utils.persistence.SharedPreferenceUtils;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;

@Route(value = "gtedx://companySelect")
public class CompanySelectActivity extends BaseToolbarActivity implements BaseRecyclerAdapter.OnItemClickListener {

    @BindView(R.id.company_select_recyclerView)
    RecyclerView companySelectRecyclerView;

    @InjectParam(key = "company")
    Bundle bundle;
    @InjectParam(key = "username")
    String username;
    @InjectParam(key = "password")
    String password;
    @InjectParam(key = "splash")
    boolean splash;

    private ArrayList<CompanyEntity> companyData;
    private CompanySelectAdapter companySelectAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Router.injectParams(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void init() throws Exception {
        companyData = (ArrayList<CompanyEntity>) bundle.getSerializable("data");
        companySelectRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        companySelectAdapter = new CompanySelectAdapter(this, companyData, R.layout.item_company_select);
        companySelectRecyclerView.setAdapter(companySelectAdapter);
        companySelectAdapter.setOnItemClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_company_select;
    }

    @Override
    public void onItemClickListener(View v, int position) {
        switch (v.getId()) {
            case R.id.company_select_frame:
                MainApplication.getInstance().setTenantID(companyData.get(position).getId());
                SharedPreferenceUtils.newInstance(this).setData("tenantID", MainApplication.getInstance().getTenantID());
                LoginService.loginIntoSystem(this, username, password, splash, this);
                break;
        }
    }
}
