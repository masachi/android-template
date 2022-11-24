package ga.cv3sarato.android.activity.apply;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chenenyu.router.Router;
import com.chenenyu.router.annotation.Route;
import ga.cv3sarato.android.R;
import ga.cv3sarato.android.adapter.common.ImageSelectAdapter;
import ga.cv3sarato.android.adapter.common.SwipeOutAdapter;
import ga.cv3sarato.android.base.BaseRecyclerAdapter;
import ga.cv3sarato.android.base.BaseToolbarActivity;
import ga.cv3sarato.android.common.Request;
import ga.cv3sarato.android.constant.RequestCode;
import ga.cv3sarato.android.entity.file.FileEntity;
import ga.cv3sarato.android.entity.request.apply.LeaveCreateEntity;
import ga.cv3sarato.android.entity.request.common.IdEntity;
import ga.cv3sarato.android.entity.response.apply.LeaveTypeEntity;
import ga.cv3sarato.android.entity.response.contact.ContactEntity;
import ga.cv3sarato.android.net.FileUpload;
import ga.cv3sarato.android.net.ServerApi;
import ga.cv3sarato.android.net.observer.HylaaObserver;
import ga.cv3sarato.android.service.ApplyService;
import ga.cv3sarato.android.utils.annotation.HylaaValidator;
import ga.cv3sarato.android.utils.dateTimePicker.DatePickerFragment;
import ga.cv3sarato.android.utils.dateTimePicker.DateTimePicker;
import ga.cv3sarato.android.utils.dateTimePicker.TimePickerFragment;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnClickListener;
import com.orhanobut.dialogplus.ViewHolder;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import org.angmarch.views.NiceSpinner;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
@Route(value = "gtedx://leaveCreate")
public class LeaveCreateActivity extends BaseToolbarActivity implements DatePickerFragment.Callback, BaseRecyclerAdapter.OnItemClickListener, OnClickListener, View.OnClickListener {

    @BindView(R.id.leave_create_type_spinner)
    NiceSpinner leaveCreateTypeSpinner;
    @BindView(R.id.leave_create_type_frame)
    LinearLayout leaveCreateTypeFrame;
    @BindView(R.id.leave_create_start_date_textView)
    TextView leaveCreateStartDateTextView;
    @BindView(R.id.leave_create_start_time_frame)
    LinearLayout leaveCreateStartTimeFrame;
    @BindView(R.id.leave_create_end_date_textView)
    TextView leaveCreateEndDateTextView;
    @BindView(R.id.leave_create_end_time_frame)
    LinearLayout leaveCreateEndTimeFrame;
    @BindView(R.id.leave_create_duration_edittext)
    EditText leaveCreateDurationEditText;
    @BindView(R.id.leave_create_content_edittext)
    EditText leaveCreateContentEditText;
    @BindView(R.id.leave_create_image_recyclerView)
    RecyclerView leaveCreateImageRecyclerView;
    @BindView(R.id.leave_create_reviewer_add_btn)
    Button leaveCreateReviewerAddBtn;
    @BindView(R.id.leave_create_reviewers_recyclerView)
    RecyclerView leaveCreateReviewersRecyclerView;
    @BindView(R.id.leave_create_viewer_add_btn)
    Button leaveCreateViewerAddBtn;
    @BindView(R.id.leave_create_viewers_recyclerView)
    RecyclerView leaveCreateViewersRecyclerView;

    private List<String> leaveTypes = new ArrayList<>();
    private List<LeaveTypeEntity.LeaveTypeItem> typesWithId = new ArrayList<>();

    private List<String> selectedImages = new ArrayList<String>() {{
        add("ADD");
    }};
    private ArrayList<ContactEntity.ContactItem> reviewers = new ArrayList<>();
    private ArrayList<ContactEntity.ContactItem> viewers = new ArrayList<>();

    private ImageSelectAdapter selectAdapter;
    private SwipeOutAdapter reviewersAdapter;
    private SwipeOutAdapter viewersAdapter;

    Calendar startDate = Calendar.getInstance();
    Calendar endDate = Calendar.getInstance();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void init() throws Exception {
        getLeaveType();

        Button submitBtn = new Button(this);
        submitBtn.setText("提交");
        submitBtn.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        toolbar.setRightView(submitBtn);
        submitBtn.setOnClickListener(this);

        selectAdapter = new ImageSelectAdapter(this, selectedImages, R.layout.item_apply_selected_image);
        reviewersAdapter = new SwipeOutAdapter(this, reviewers, R.layout.item_swipe_out);
        viewersAdapter = new SwipeOutAdapter(this, viewers, R.layout.item_swipe_out);
        leaveCreateImageRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        leaveCreateReviewersRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        leaveCreateViewersRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        leaveCreateImageRecyclerView.setAdapter(selectAdapter);
        leaveCreateReviewersRecyclerView.setAdapter(reviewersAdapter);
        leaveCreateViewersRecyclerView.setAdapter(viewersAdapter);

        selectAdapter.setOnItemClickListener(this);
        reviewersAdapter.setOnItemClickListener((view, position) -> {
            reviewers.remove(position);
            reviewersAdapter.notifyItemRemoved(position);
            reviewersAdapter.notifyItemRangeChanged(position, reviewers.size());
        });

        viewersAdapter.setOnItemClickListener((view, position) -> {
            viewers.remove(position);
            viewersAdapter.notifyItemRemoved(position);
            viewersAdapter.notifyItemRangeChanged(position, viewers.size());
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_leave_create;
    }

    @OnClick({R.id.leave_create_start_time_frame, R.id.leave_create_end_time_frame, R.id.leave_create_reviewer_add_btn, R.id.leave_create_viewer_add_btn})
    public void onViewClicked(View view) {
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            case R.id.leave_create_start_time_frame:
                DatePickerFragment.newInstance(startDate)
                        .setOKColor(Color.BLUE)
                        .setCancelColor(Color.BLUE)
                        .setDateCallback(this)
                        .show(getFragmentManager(), "START_TIME_PICKER");
                break;
            case R.id.leave_create_end_time_frame:
                DatePickerFragment.newInstance(endDate)
                        .setOKColor(Color.BLUE)
                        .setCancelColor(Color.BLUE)
                        .setDateCallback(this)
                        .show(getFragmentManager(), "END_TIME_PICKER");
                break;
            case R.id.leave_create_reviewer_add_btn:
                bundle.putSerializable("selected", reviewers);
                Router.build("gtedx://personSelect")
                        .requestCode(RequestCode.REVIEWERS_SELECT)
                        .with(bundle)
                        .go(this);
                break;
            case R.id.leave_create_viewer_add_btn:
                bundle.putSerializable("selected", viewers);
                Router.build("gtedx://personSelect")
                        .requestCode(RequestCode.VIEWERS_SELECT)
                        .with(bundle)
                        .go(this);
                break;
        }
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = year + "-" + (monthOfYear + 1 < 10 ? "0" + (monthOfYear + 1) : (monthOfYear + 1)) + "-" + (dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth) + " ";
        switch (view.getTag()) {
            case "START_TIME_PICKER":
                startDate.set(year, monthOfYear, dayOfMonth);
                leaveCreateStartDateTextView.setText(date);
                break;
            case "END_TIME_PICKER":
                endDate.set(year, monthOfYear, dayOfMonth);
                leaveCreateEndDateTextView.setText(date);
                break;
        }
        if (!leaveCreateStartDateTextView.getText().toString().equals("请选择") && !leaveCreateEndDateTextView.getText().toString().equals("请选择")) {
            long days = (endDate.getTimeInMillis() - startDate.getTimeInMillis()) / (1000 * 3600 * 24);
            if (days < 0) {
                days = 0;
            }

            leaveCreateDurationEditText.setText(String.valueOf(days));
        }
    }

    private void getLeaveType() {
        ServerApi.defaultInstance()
                .create(ApplyService.class)
                .getLeaveType(new Request(new Object()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HylaaObserver<LeaveTypeEntity>() {
                    @Override
                    public void onNext(LeaveTypeEntity leaveTypeEntity) {
                        typesWithId.addAll(leaveTypeEntity.getTypes());
                        for (LeaveTypeEntity.LeaveTypeItem item : leaveTypeEntity.getTypes()) {
                            leaveTypes.add(item.getName());
                        }
                        leaveCreateTypeSpinner.attachDataSource(leaveTypes);
                    }
                });
    }

    private void createLeaveRequest() {
        FileUpload.uploadFiles(this, selectedImages)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Function<List<FileEntity>, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(List<FileEntity> fileEntities) throws Exception {
                        Request<LeaveCreateEntity> request = new Request<>();
                        LeaveCreateEntity body = new LeaveCreateEntity();
                        body.setLeaveTypeId(typesWithId.get(leaveCreateTypeSpinner.getSelectedIndex()).getId());
                        body.setContent(leaveCreateContentEditText.getText().toString());
                        body.setStartTime(leaveCreateStartDateTextView.getText().toString() + "00:00:00");
                        body.setEndTime(leaveCreateEndDateTextView.getText().toString() + "00:00:00");
                        body.setDays(Float.valueOf(leaveCreateDurationEditText.getText().toString()));
                        body.setIsPublished(1);
                        body.setFiles(fileEntities);
                        body.setManagers((List<IdEntity>) CollectionUtils.collect(reviewers, new Transformer() {
                            @Override
                            public Object transform(Object input) {
                                return new IdEntity(((ContactEntity.ContactItem) input).getId());
                            }
                        }));
                        body.setCoordinators((List<IdEntity>) CollectionUtils.collect(viewers, new Transformer() {
                            @Override
                            public Object transform(Object input) {
                                return new IdEntity(((ContactEntity.ContactItem) input).getId());
                            }
                        }));
                        HylaaValidator.validate(body);
                        request.setBody(body);
                        return ServerApi.defaultInstance()
                                .create(ApplyService.class)
                                .createLeaveRequest(request)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread());

                    }
                })
                .subscribe(new HylaaObserver<Object>() {
                    @Override
                    public void onNext(Object o) {
                        finish();
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case RequestCode.REVIEWERS_SELECT:
                if (resultCode == RESULT_OK) {
                    reviewers.clear();
                    reviewers.addAll((ArrayList<ContactEntity.ContactItem>) data.getSerializableExtra("selected"));
                    reviewersAdapter.notifyDataSetChanged();
                }
                break;
            case RequestCode.VIEWERS_SELECT:
                if (resultCode == RESULT_OK) {
                    viewers.clear();
                    viewers.addAll((ArrayList<ContactEntity.ContactItem>) data.getSerializableExtra("selected"));
                    viewersAdapter.notifyDataSetChanged();
                }
                break;
            case RequestCode.IMAGE_CAMERA:
                if (resultCode == RESULT_OK) {
                    selectedImages.add(0, data.getStringExtra("uri"));
                    selectAdapter.notifyDataSetChanged();
                }
                break;
            case RequestCode.IMAGE_GALLERY:
                if (resultCode == RESULT_OK) {
                    selectedImages.addAll(0, Arrays.asList(data.getStringArrayExtra("selected")));
                    selectAdapter.notifyDataSetChanged();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemClickListener(View v, int position) {
        switch (v.getId()) {
            case R.id.selected_imageView:
                if (selectedImages.get(position).equals("ADD")) {
                    LeaveCreateActivityPermissionsDispatcher.showDialogWithPermissionCheck(this);
                } else {

                }
                break;
            case R.id.delete_selected_btn:
                selectedImages.remove(position);
                selectAdapter.notifyDataSetChanged();
                break;
            default:
                break;
        }
    }

    @NeedsPermission({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA})
    public void showDialog() {
        DialogPlus dialog = DialogPlus.newDialog(this)
                .setContentHolder(new ViewHolder(R.layout.dialog_select_image))
                .setContentHeight(ViewGroup.LayoutParams.WRAP_CONTENT)
                .setOnClickListener(this)
                .setExpanded(true)
                .setGravity(Gravity.BOTTOM)
                .create();
        dialog.show();
    }

    @Override
    public void onClick(DialogPlus dialog, View view) {
        switch (view.getId()) {
            case R.id.select_from_album_btn:
                Router.build("gtedx://multiImagePicker")
                        .requestCode(RequestCode.IMAGE_GALLERY)
                        .go(this);
                break;
            case R.id.select_from_camera_btn:
                Router.build("gtedx://camera").requestCode(RequestCode.IMAGE_CAMERA).go(this);
                break;
            default:
                break;
        }
        dialog.dismiss();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        LeaveCreateActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @Override
    public void onClick(View view) {
        if (view instanceof Button) {
            createLeaveRequest();
        }
    }
}
