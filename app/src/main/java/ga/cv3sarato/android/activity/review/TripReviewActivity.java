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

@Route(value = "gtedx://tripReview")
public class TripReviewActivity extends BaseToolbarActivity {

    @BindView(R.id.trip_review_avatar_imageView)
    ImageView tripReviewAvatarImageView;
    @BindView(R.id.trip_review_name_textView)
    TextView tripReviewNameTextView;
    @BindView(R.id.trip_review_creator_frame)
    LinearLayout tripReviewCreatorFrame;
    @BindView(R.id.trip_review_status_imageView)
    ImageView tripReviewStatusImageView;
    @BindView(R.id.trip_review_content_textView)
    TextView tripReviewContentTextView;
    @BindView(R.id.trip_review_address_textView)
    TextView tripReviewAddressTextView;
    @BindView(R.id.trip_review_type_frame)
    LinearLayout tripReviewTypeFrame;
    @BindView(R.id.trip_review_time_textView)
    TextView tripReviewTimeTextView;
    @BindView(R.id.trip_review_time_frame)
    LinearLayout tripReviewTimeFrame;
    @BindView(R.id.trip_review_duration_textView)
    TextView tripReviewDurationTextView;
    @BindView(R.id.trip_review_duration_frame)
    LinearLayout tripReviewDurationFrame;
    @BindView(R.id.trip_review_image_recyclerView)
    RecyclerView tripReviewImageRecyclerView;
    @BindView(R.id.trip_review_image_frame)
    LinearLayout tripReviewImageFrame;
    @BindView(R.id.trip_review_progress_recyclerView)
    RecyclerView tripReviewProgressRecyclerView;
    @BindView(R.id.trip_review_remark_editText)
    EditText tripReviewRemarkEditText;
    @BindView(R.id.trip_review_return_btn)
    Button tripReviewReturnBtn;
    @BindView(R.id.trip_review_reject_btn)
    Button tripReviewRejectBtn;
    @BindView(R.id.trip_review_approve_btn)
    Button tripReviewApproveBtn;
    @BindView(R.id.trip_review_operation_frame)
    LinearLayout tripReviewOperationFrame;
    @BindView(R.id.trip_review_comment_btn)
    Button tripReviewCommentBtn;
    @BindView(R.id.trip_review_remark_frame)
    LinearLayout tripReviewRemarkFrame;

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
        progressAdapter = new ReviewProgressAdapter(this, progress, R.layout.item_review_progress, ApplicationType.TRIP);
        tripReviewImageRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        tripReviewProgressRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        tripReviewImageRecyclerView.setAdapter(imageAdapter);
        tripReviewProgressRecyclerView.setAdapter(progressAdapter);
        getTripDetail();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_trip_review;
    }

    private void getTripDetail() {
        ServerApi.defaultInstance()
                .create(ApplyService.class)
                .getBusinessTripDetail(new Request(new IdEntity(id)))
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
                .setImageView(tripReviewAvatarImageView)
                .withDefaultHeaders()
                .loadImage();

        setReviewStatus(leaveTripDetailEntity.getReviewStatus());

        tripReviewNameTextView.setText(leaveTripDetailEntity.getCreator().getName());
        tripReviewContentTextView.setText(leaveTripDetailEntity.getContent());
        tripReviewAddressTextView.setText(leaveTripDetailEntity.getAddress());
        String time = leaveTripDetailEntity.getStartTime().substring(0, 10) + "-" + leaveTripDetailEntity.getEndTime().substring(0, 10);
        tripReviewTimeTextView.setText(time);
        tripReviewDurationTextView.setText(String.valueOf(leaveTripDetailEntity.getDays()));
        if (leaveTripDetailEntity.getFiles().size() > 0) {
            tripReviewImageFrame.setVisibility(View.VISIBLE);
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
                tripReviewStatusImageView.setImageResource(R.drawable.status_pending);
                break;
            case ReviewStatus.APPROVED:
                tripReviewStatusImageView.setImageResource(R.drawable.status_approve);
                break;
            case ReviewStatus.REFUSED:
                tripReviewStatusImageView.setImageResource(R.drawable.status_reject);
                break;
            case ReviewStatus.RETURNED:
                tripReviewStatusImageView.setImageResource(R.drawable.status_return);
                break;
            case ReviewStatus.CLOSED:
                tripReviewStatusImageView.setImageResource(R.drawable.status_close);
                break;
            default:
                break;
        }
    }

    private void setOperation(List<String> operations) {

        if (operations.contains(Operation.APPROVE)) {
            tripReviewRemarkFrame.setVisibility(View.VISIBLE);
            tripReviewApproveBtn.setVisibility(View.VISIBLE);
            tripReviewOperationFrame.setVisibility(View.VISIBLE);
        }

        if (operations.contains(Operation.REFUSE)) {
            tripReviewRemarkFrame.setVisibility(View.VISIBLE);
            tripReviewRejectBtn.setVisibility(View.VISIBLE);
            tripReviewOperationFrame.setVisibility(View.VISIBLE);
        }

        if (operations.contains(Operation.RETURN)) {
            tripReviewRemarkFrame.setVisibility(View.VISIBLE);
            tripReviewReturnBtn.setVisibility(View.VISIBLE);
            tripReviewOperationFrame.setVisibility(View.VISIBLE);
        }

        if (operations.contains(Operation.COMMENT)) {
            tripReviewRemarkFrame.setVisibility(View.VISIBLE);
            tripReviewCommentBtn.setVisibility(View.VISIBLE);
        }
    }

    @OnClick({R.id.trip_review_return_btn, R.id.trip_review_reject_btn, R.id.trip_review_approve_btn, R.id.trip_review_comment_btn})
    public void onViewClicked(View view) {
        Request<ReviewOperateEntity> request = new Request<>();
        ReviewOperateEntity body = new ReviewOperateEntity(id, tripReviewRemarkEditText.getText().toString());
        request.setBody(body);
        switch (view.getId()) {
            case R.id.trip_review_return_btn:
                returnTrip(request);
                break;
            case R.id.trip_review_reject_btn:
                rejectTrip(request);
                break;
            case R.id.trip_review_approve_btn:
                approveTrip(request);
                break;
            case R.id.trip_review_comment_btn:
                commentTrip(request);
                break;
        }
    }

    private void returnTrip(Request request) {
        ServerApi.defaultInstance()
                .create(ReviewService.class)
                .returnBusinessTrip(request)
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
                        tripReviewRemarkEditText.setText("");
                    }
                });
    }

    private void rejectTrip(Request request) {
        ServerApi.defaultInstance()
                .create(ReviewService.class)
                .refuseBusinessTrip(request)
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
                        tripReviewRemarkEditText.setText("");
                    }
                });
    }

    private void approveTrip(Request request) {
        ServerApi.defaultInstance()
                .create(ReviewService.class)
                .approveBusinessTrip(request)
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
                        tripReviewRemarkEditText.setText("");
                    }
                });
    }

    private void commentTrip(Request request) {
        ServerApi.defaultInstance()
                .create(ReviewService.class)
                .commentBusinessTrip(request)
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
                        tripReviewRemarkEditText.setText("");
                    }
                });
    }
}
