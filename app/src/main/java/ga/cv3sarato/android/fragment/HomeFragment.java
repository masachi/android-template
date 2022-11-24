package ga.cv3sarato.android.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chenenyu.router.Router;
import ga.cv3sarato.android.MainApplication;
import ga.cv3sarato.android.R;
import ga.cv3sarato.android.adapter.user.AppConfigAdapter;
import ga.cv3sarato.android.base.BaseFragment;
import ga.cv3sarato.android.base.BaseRecyclerAdapter;
import ga.cv3sarato.android.entity.response.user.AppConfigEntity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class HomeFragment extends BaseFragment implements BaseRecyclerAdapter.OnItemClickListener {

    Unbinder unbinder;
    @BindView(R.id.home_recyclerView)
    RecyclerView homeRecyclerView;
    AppConfigAdapter configAdapter;


    //    List<AppConfigEntity> appConfig = MainApplication.getInstance().getUser().getAppConfig();
    List<AppConfigEntity> appConfig = new ArrayList<AppConfigEntity>() {{
        add(new AppConfigEntity("MEETING","会议"));
        add(new AppConfigEntity("ATTENDANCE", "考勤"));
        add(new AppConfigEntity("CONTACT", "通讯录"));
        add(new AppConfigEntity("FORUM", MainApplication.getInstance().getUser().getTenant().getCommunityName().equals("") ? "论坛" : MainApplication.getInstance().getUser().getTenant().getCommunityName()));
        add(new AppConfigEntity("APPLY", "申请"));
        add(new AppConfigEntity("PDF", "PDF"));
        add(new AppConfigEntity("REVIEW", "审核"));
    }};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, null);
        unbinder = ButterKnife.bind(this, rootView);

        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        configAdapter = new AppConfigAdapter(getContext(), appConfig, R.layout.item_home_recyclerview);
        homeRecyclerView.setLayoutManager(layoutManager);
        homeRecyclerView.setAdapter(configAdapter);
        configAdapter.setOnItemClickListener(this);

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onItemClickListener(View v, int position) {
        switch (appConfig.get(position).getCode()) {
            case "MEETING":
                Router.build("gtedx://meetingRoom").go(this);
                break;
            case "ATTENDANCE":
                Router.build("gtedx://attendance").go(this);
                break;
            case "CONTACT":
                Router.build("gtedx://contact")
                        .with("mode", 0)
                        .go(this);
                break;
            case "FORUM":
                Router.build("gtedx://forum").go(this);
//                Router.build("gtedx://section").go(this);
                break;
            case "APPLY":
                Router.build("gtedx://applyHome").go(this);
                break;
            case "PDF":
                Router.build("gtedx://pdfViewer")
                        .with("uri", "https://services.github.com/on-demand/downloads/github-git-cheat-sheet.pdf")
                        .go(this);
                break;
            case "REVIEW":
                Router.build("gtedx://review").go(this);
            default:
                break;
        }
    }
}
