package ga.cv3sarato.android.activity.review;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.chenenyu.router.Router;
import com.chenenyu.router.annotation.InjectParam;
import com.chenenyu.router.annotation.Route;
import ga.cv3sarato.android.R;
import ga.cv3sarato.android.adapter.apply.ReviewProgressAdapter;
import ga.cv3sarato.android.adapter.common.CommonImageAdapter;
import ga.cv3sarato.android.base.BaseToolbarActivity;
import ga.cv3sarato.android.common.Request;
import ga.cv3sarato.android.constant.ApplicationType;
import ga.cv3sarato.android.constant.Operation;
import ga.cv3sarato.android.constant.ReviewStatus;
import ga.cv3sarato.android.entity.file.FileEntity;
import ga.cv3sarato.android.entity.request.common.IdEntity;
import ga.cv3sarato.android.entity.request.review.ReviewOperateEntity;
import ga.cv3sarato.android.entity.response.apply.LeaveTripDetailEntity;
import ga.cv3sarato.android.net.ServerApi;
import ga.cv3sarato.android.net.observer.HylaaObserver;
import ga.cv3sarato.android.service.ApplyService;
import ga.cv3sarato.android.service.ReviewService;
import ga.cv3sarato.android.utils.imageLoader.GlideUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


@Route(value = "gtedx://leaveReview")
public class LeaveReviewActivity extends BaseToolbarActivity {

    @BindView(R.id.leave_review_avatar_imageView)
    ImageView leaveReviewAvatarImageView;
    @BindView(R.id.leave_review_name_textView)
    TextView leaveReviewNameTextView;
    @BindView(R.id.leave_review_creator_frame)
    LinearLayout leaveReviewCreatorFrame;
    @BindView(R.id.leave_review_status_imageView)
    ImageView leaveReviewStatusImageView;
    @BindView(R.id.leave_review_content_textView)
    TextView leaveReviewContentTextView;
    @BindView(R.id.leave_review_type_textView)
    TextView leaveReviewTypeTextView;
    @BindView(R.id.leave_review_type_frame)
    LinearLayout leaveReviewTypeFrame;
    @BindView(R.id.leave_review_time_textView)
    TextView leaveReviewTimeTextView;
    @BindView(R.id.leave_review_time_frame)
    LinearLayout leaveReviewTimeFrame;
    @BindView(R.id.leave_review_duration_textView)
    TextView leaveReviewDurationTextView;
    @BindView(R.id.leave_review_duration_frame)
    LinearLayout leaveReviewDurationFrame;
    @BindView(R.id.leave_review_image_recyclerView)
    RecyclerView leaveReviewImageRecyclerView;
    @BindView(R.id.leave_review_image_frame)
    LinearLayout leaveReviewImageFrame;
    @BindView(R.id.leave_review_progress_recyclerView)
    RecyclerView leaveReviewProgressRecyclerView;
    @BindView(R.id.leave_review_remark_editText)
    EditText leaveReviewRemarkEditText;
    @BindView(R.id.leave_review_return_btn)
    Button leaveReviewReturnBtn;
    @BindView(R.id.leave_review_reject_btn)
    Button leaveReviewRejectBtn;
    @BindView(R.id.leave_review_approve_btn)
    Button leaveReviewApproveBtn;
    @BindView(R.id.leave_review_operation_frame)
    LinearLayout leaveReviewOperationFrame;
    @BindView(R.id.leave_review_comment_btn)
    Button leaveReviewCommentBtn;
    @BindView(R.id.leave_review_detail_frame)
    ScrollView leaveReviewDetailFrame;
    @BindView(R.id.leave_review_remark_frame)
    LinearLayout leaveReviewRemarkFrame;

    @InjectParam(key = "id")
    String id;

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
        leaveReviewImageRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        leaveReviewProgressRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        leaveReviewImageRecyclerView.setAdapter(imageAdapter);
        leaveReviewProgressRecyclerView.setAdapter(progressAdapter);
        getLeaveDetail();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_leave_review;
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
                .setImageView(leaveReviewAvatarImageView)
                .withDefaultHeaders()
                .loadImage();

        setReviewStatus(leaveTripDetailEntity.getReviewStatus());

        leaveReviewNameTextView.setText(leaveTripDetailEntity.getCreator().getName());
        leaveReviewContentTextView.setText(leaveTripDetailEntity.getContent());
        leaveReviewTypeTextView.setText(leaveTripDetailEntity.getLeaveType().getName());
        String time = leaveTripDetailEntity.getStartTime().substring(0, 10) + "-" + leaveTripDetailEntity.getEndTime().substring(0, 10);
        leaveReviewTimeTextView.setText(time);
        leaveReviewDurationTextView.setText(String.valueOf(leaveTripDetailEntity.getDays()));
        if (leaveTripDetailEntity.getFiles().size() > 0) {
            leaveReviewImageFrame.setVisibility(View.VISIBLE);
            files.clear();
            files.addAll(leaveTripDetailEntity.getFiles());
            imageAdapter.notifyDataSetChanged();
        }
        if (leaveTripDetailEntity.getHistories().size() > 0) {
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

        setOperation(leaveTripDetailEntity.getOperations());
    }

    private void setReviewStatus(String status) {
        switch (status) {
            case ReviewStatus.PENDING:
                leaveReviewStatusImageView.setImageResource(R.drawable.status_pending);
                break;
            case ReviewStatus.APPROVED:
                leaveReviewStatusImageView.setImageResource(R.drawable.status_approve);
                break;
            case ReviewStatus.REFUSED:
                leaveReviewStatusImageView.setImageResource(R.drawable.status_reject);
                break;
            case ReviewStatus.RETURNED:
                leaveReviewStatusImageView.setImageResource(R.drawable.status_return);
                break;
            case ReviewStatus.CLOSED:
                leaveReviewStatusImageView.setImageResource(R.drawable.status_close);
                break;
            default:
                break;
        }
    }

    private void setOperation(List<String> operations) {

        if (operations.contains(Operation.APPROVE)) {
            leaveReviewRemarkFrame.setVisibility(View.VISIBLE);
            leaveReviewApproveBtn.setVisibility(View.VISIBLE);
            leaveReviewOperationFrame.setVisibility(View.VISIBLE);
        }

        if (operations.contains(Operation.REFUSE)) {
            leaveReviewRemarkFrame.setVisibility(View.VISIBLE);
            leaveReviewRejectBtn.setVisibility(View.VISIBLE);
            leaveReviewOperationFrame.setVisibility(View.VISIBLE);
        }

        if (operations.contains(Operation.RETURN)) {
            leaveReviewRemarkFrame.setVisibility(View.VISIBLE);
            leaveReviewReturnBtn.setVisibility(View.VISIBLE);
            leaveReviewOperationFrame.setVisibility(View.VISIBLE);
        }

        if (operations.contains(Operation.COMMENT)) {
            leaveReviewRemarkFrame.setVisibility(View.VISIBLE);
            leaveReviewCommentBtn.setVisibility(View.VISIBLE);
        }
    }

    @OnClick({R.id.leave_review_return_btn, R.id.leave_review_reject_btn, R.id.leave_review_approve_btn, R.id.leave_review_comment_btn})
    public void onViewClicked(View view) {
        Request<ReviewOperateEntity> request = new Request<>();
        ReviewOperateEntity body = new ReviewOperateEntity(id, leaveReviewRemarkEditText.getText().toString());
        request.setBody(body);
        switch (view.getId()) {
            case R.id.leave_review_return_btn:
                returnLeave(request);
                break;
            case R.id.leave_review_reject_btn:
                rejectLeave(request);
                break;
            case R.id.leave_review_approve_btn:
                approveLeave(request);
                break;
            case R.id.leave_review_comment_btn:
                commentLeave(request);
                break;
        }
    }

    private void returnLeave(Request request) {
        ServerApi.defaultInstance()
                .create(ReviewService.class)
                .returnLeave(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HylaaObserver<LeaveTripDetailEntity>() {
                    @Override
                    public void onNext(LeaveTripDetailEntity leaveTripDetailEntity) {
                        setReviewStatus(leaveTripDetailEntity.getReviewStatus());
                        setOperation(leaveTripDetailEntity.getOperations());
                        progress.clear();
                        progress.addAll(leaveTripDetailEntity.getHistories());
                        progressAdapter.notifyDataSetChanged();
                        leaveReviewRemarkEditText.setText("");
                    }
                });
    }

    private void rejectLeave(Request request) {
        ServerApi.defaultInstance()
                .create(ReviewService.class)
                .refuseLeave(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HylaaObserver<LeaveTripDetailEntity>() {
                    @Override
                    public void onNext(LeaveTripDetailEntity leaveTripDetailEntity) {
                        setReviewStatus(leaveTripDetailEntity.getReviewStatus());
                        setOperation(leaveTripDetailEntity.getOperations());
                        progress.clear();
                        progress.addAll(leaveTripDetailEntity.getHistories());
                        progressAdapter.notifyDataSetChanged();
                        leaveReviewRemarkEditText.setText("");
                    }
                });
    }

    private void approveLeave(Request request) {
        ServerApi.defaultInstance()
                .create(ReviewService.class)
                .approveLeave(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HylaaObserver<LeaveTripDetailEntity>() {
                    @Override
                    public void onNext(LeaveTripDetailEntity leaveTripDetailEntity) {
                        setReviewStatus(leaveTripDetailEntity.getReviewStatus());
                        setOperation(leaveTripDetailEntity.getOperations());
                        progress.clear();
                        progress.addAll(leaveTripDetailEntity.getHistories());
                        progressAdapter.notifyDataSetChanged();
                        leaveReviewRemarkEditText.setText("");
                    }
                });
    }

    private void commentLeave(Request request) {
        ServerApi.defaultInstance()
                .create(ReviewService.class)
                .commentLeave(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HylaaObserver<LeaveTripDetailEntity>() {
                    @Override
                    public void onNext(LeaveTripDetailEntity leaveTripDetailEntity) {
                        setReviewStatus(leaveTripDetailEntity.getReviewStatus());
                        setOperation(leaveTripDetailEntity.getOperations());
                        progress.clear();
                        progress.addAll(leaveTripDetailEntity.getHistories());
                        progressAdapter.notifyDataSetChanged();
                        leaveReviewRemarkEditText.setText("");
                    }
                });
    }
}
