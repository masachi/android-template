package ga.cv3sarato.android.activity.apply;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chenenyu.router.Router;
import com.chenenyu.router.annotation.InjectParam;
import com.chenenyu.router.annotation.Route;
import ga.cv3sarato.android.R;
import ga.cv3sarato.android.adapter.apply.ReviewProgressAdapter;
import ga.cv3sarato.android.adapter.common.CommonImageAdapter;
import ga.cv3sarato.android.base.BaseRecyclerAdapter;
import ga.cv3sarato.android.base.BaseToolbarActivity;
import ga.cv3sarato.android.common.Request;
import ga.cv3sarato.android.constant.ApplicationType;
import ga.cv3sarato.android.constant.ReviewStatus;
import ga.cv3sarato.android.entity.file.FileEntity;
import ga.cv3sarato.android.entity.request.common.IdEntity;
import ga.cv3sarato.android.entity.response.apply.LeaveTripDetailEntity;
import ga.cv3sarato.android.net.ServerApi;
import ga.cv3sarato.android.net.observer.HylaaObserver;
import ga.cv3sarato.android.service.ApplyService;
import ga.cv3sarato.android.utils.imageLoader.GlideUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


@Route(value = "gtedx://leaveDetail")
public class LeaveDetailActivity extends BaseToolbarActivity implements BaseRecyclerAdapter.OnItemClickListener{

    @BindView(R.id.leave_detail_avatar_imageView)
    ImageView leaveDetailAvatarImageView;
    @BindView(R.id.leave_detail_name_textView)
    TextView leaveDetailNameTextView;
    @BindView(R.id.leave_detail_creator_frame)
    LinearLayout leaveDetailCreatorFrame;
    @BindView(R.id.leave_detail_status_imageView)
    ImageView leaveDetailStatusImageView;
    @BindView(R.id.leave_detail_content_textView)
    TextView leaveDetailContentTextView;
    @BindView(R.id.leave_detail_type_textView)
    TextView leaveDetailTypeTextView;
    @BindView(R.id.leave_detail_type_frame)
    LinearLayout leaveDetailTypeFrame;
    @BindView(R.id.leave_detail_time_textView)
    TextView leaveDetailTimeTextView;
    @BindView(R.id.leave_detail_time_frame)
    LinearLayout leaveDetailTimeFrame;
    @BindView(R.id.leave_detail_duration_textView)
    TextView leaveDetailDurationTextView;
    @BindView(R.id.leave_detail_duration_frame)
    LinearLayout leaveDetailDurationFrame;
    @BindView(R.id.leave_detail_image_recyclerView)
    RecyclerView leaveDetailImageRecyclerView;
    @BindView(R.id.leave_detail_image_frame)
    LinearLayout leaveDetailImageFrame;
    @BindView(R.id.leave_detail_review_progress_recyclerView)
    RecyclerView leaveDetailReviewProgressRecyclerView;

    @InjectParam(key = "id")
    private String id;

    private CommonImageAdapter imageAdapter;
    private ReviewProgressAdapter progressAdapter;
    private List<FileEntity> files = new ArrayList<>();
    private List<LeaveTripDetailEntity.ReviewProgress> progress = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Router.injectParams(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void init() throws Exception {
        imageAdapter = new CommonImageAdapter(this, files, R.layout.item_image_leave_trip_detail);
        progressAdapter = new ReviewProgressAdapter(this, progress, R.layout.item_review_progress, ApplicationType.LEAVE);
        leaveDetailImageRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        leaveDetailReviewProgressRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        leaveDetailImageRecyclerView.setAdapter(imageAdapter);
        leaveDetailReviewProgressRecyclerView.setAdapter(progressAdapter);
        getLeaveDetail();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_leave_detail;
    }

    private void getLeaveDetail() {
        ServerApi.defaultInstance()
                .create(ApplyService.class)
                .getLeaveRequestDetail(new Request(new IdEntity(id)))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HylaaObserver<LeaveTripDetailEntity>() {
                    @Override
                    public void onNext(LeaveTripDetailEntity leaveTripDetailEntity) {
                        setData(leaveTripDetailEntity);
                    }
                });
    }

    private void setData(LeaveTripDetailEntity leaveTripDetailEntity) {
        GlideUtils.newInstance(this)
                .setPath(leaveTripDetailEntity.getCreator().getAvatar())
                .setImageView(leaveDetailAvatarImageView)
                .withDefaultHeaders()
                .loadImage();

        switch (leaveTripDetailEntity.getReviewStatus()) {
            case ReviewStatus.PENDING:
                leaveDetailStatusImageView.setImageResource(R.drawable.status_pending);
                break;
            case ReviewStatus.APPROVED:
                leaveDetailStatusImageView.setImageResource(R.drawable.status_approve);
                break;
            case ReviewStatus.REFUSED:
                leaveDetailStatusImageView.setImageResource(R.drawable.status_reject);
                break;
            case ReviewStatus.RETURNED:
                leaveDetailStatusImageView.setImageResource(R.drawable.status_return);
                break;
            case ReviewStatus.CLOSED:
                leaveDetailStatusImageView.setImageResource(R.drawable.status_close);
                break;
            default:
                break;
        }

        leaveDetailNameTextView.setText(leaveTripDetailEntity.getCreator().getName());
        leaveDetailContentTextView.setText(leaveTripDetailEntity.getContent());
        leaveDetailTypeTextView.setText(leaveTripDetailEntity.getLeaveType().getName());
        String time = leaveTripDetailEntity.getStartTime().substring(0,10) + "-" + leaveTripDetailEntity.getEndTime().substring(0,10);
        leaveDetailTimeTextView.setText(time);
        leaveDetailDurationTextView.setText(String.valueOf(leaveTripDetailEntity.getDays()));
        if(leaveTripDetailEntity.getFiles().size() > 0) {
            leaveDetailImageFrame.setVisibility(View.VISIBLE);
            files.clear();
            files.addAll(leaveTripDetailEntity.getFiles());
            imageAdapter.notifyDataSetChanged();
        }
        if(leaveTripDetailEntity.getHistories().size() > 0) {
            progress.clear();
            progress.addAll(leaveTripDetailEntity.getHistories());
            progressAdapter.notifyDataSetChanged();
        }

        imageAdapter.setOnItemClickListener((view, position) -> {
            ArrayList<String> images = new ArrayList<>();
            for (FileEntity file : leaveTripDetailEntity.getFiles()) {
                images.add(file.getViewUrl());
            }

            Bundle bundle = new Bundle();
            bundle.putStringArrayList("uri", images);
            Router.build("gtedx://bigImage")
                    .with(bundle)
                    .with("index", position)
                    .go(this);
        });
    }

    @Override
    public void onItemClickListener(View v, int position) {

    }
}
