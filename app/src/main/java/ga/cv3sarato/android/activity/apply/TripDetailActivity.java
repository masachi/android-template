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

@Route(value = "gtedx://tripDetail")
public class TripDetailActivity extends BaseToolbarActivity implements BaseRecyclerAdapter.OnItemClickListener{

    @BindView(R.id.trip_detail_avatar_imageView)
    ImageView tripDetailAvatarImageView;
    @BindView(R.id.trip_detail_name_textView)
    TextView tripDetailNameTextView;
    @BindView(R.id.trip_detail_creator_frame)
    LinearLayout tripDetailCreatorFrame;
    @BindView(R.id.trip_detail_status_imageView)
    ImageView tripDetailStatusImageView;
    @BindView(R.id.trip_detail_content_textView)
    TextView tripDetailContentTextView;
    @BindView(R.id.trip_detail_address_textView)
    TextView tripDetailAddressTextView;
    @BindView(R.id.trip_detail_type_frame)
    LinearLayout tripDetailTypeFrame;
    @BindView(R.id.trip_detail_time_textView)
    TextView tripDetailTimeTextView;
    @BindView(R.id.trip_detail_time_frame)
    LinearLayout tripDetailTimeFrame;
    @BindView(R.id.trip_detail_duration_textView)
    TextView tripDetailDurationTextView;
    @BindView(R.id.trip_detail_duration_frame)
    LinearLayout tripDetailDurationFrame;
    @BindView(R.id.trip_detail_image_recyclerView)
    RecyclerView tripDetailImageRecyclerView;
    @BindView(R.id.trip_detail_image_frame)
    LinearLayout tripDetailImageFrame;
    @BindView(R.id.trip_detail_review_progress_recyclerView)
    RecyclerView tripDetailReviewProgressRecyclerView;

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
        tripDetailImageRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        tripDetailReviewProgressRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        tripDetailImageRecyclerView.setAdapter(imageAdapter);
        tripDetailReviewProgressRecyclerView.setAdapter(progressAdapter);
        getTripDetail();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_trip_detail;
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
                .setImageView(tripDetailAvatarImageView)
                .withDefaultHeaders()
                .loadImage();

        switch (leaveTripDetailEntity.getReviewStatus()) {
            case ReviewStatus.PENDING:
                tripDetailStatusImageView.setImageResource(R.drawable.status_pending);
                break;
            case ReviewStatus.APPROVED:
                tripDetailStatusImageView.setImageResource(R.drawable.status_approve);
                break;
            case ReviewStatus.REFUSED:
                tripDetailStatusImageView.setImageResource(R.drawable.status_reject);
                break;
            case ReviewStatus.RETURNED:
                tripDetailStatusImageView.setImageResource(R.drawable.status_return);
                break;
            case ReviewStatus.CLOSED:
                tripDetailStatusImageView.setImageResource(R.drawable.status_close);
                break;
            default:
                break;
        }

        tripDetailNameTextView.setText(leaveTripDetailEntity.getCreator().getName());
        tripDetailContentTextView.setText(leaveTripDetailEntity.getContent());
        tripDetailAddressTextView.setText(leaveTripDetailEntity.getAddress());
        String time = leaveTripDetailEntity.getStartTime().substring(0,10) + "-" + leaveTripDetailEntity.getEndTime().substring(0,10);
        tripDetailTimeTextView.setText(time);
        tripDetailDurationTextView.setText(String.valueOf(leaveTripDetailEntity.getDays()));
        if(leaveTripDetailEntity.getFiles().size() > 0) {
            tripDetailImageFrame.setVisibility(View.VISIBLE);
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
