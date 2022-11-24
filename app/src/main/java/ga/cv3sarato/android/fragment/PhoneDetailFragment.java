package ga.cv3sarato.android.fragment;

import android.Manifest;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import ga.cv3sarato.android.R;
import ga.cv3sarato.android.base.BaseFragment;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class PhoneDetailFragment extends BaseFragment {


    @BindView(R.id.phoneDetail_linearLayout)
    LinearLayout phoneDetailLinearLayout;
    Unbinder unbinder;
    @BindView(R.id.phoneDetail_scrollView)
    ScrollView phoneDetailScrollView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_phone_detail, null);
        unbinder = ButterKnife.bind(this, rootView);
        PhoneDetailFragmentPermissionsDispatcher.initDetailWithPermissionCheck(this);
        return rootView;
    }

    private void addTextView(String title, String info) {
        LinearLayout detailItem = new LinearLayout(getContext());
        detailItem.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        detailItem.setOrientation(LinearLayout.VERTICAL);
        TextView detailTitle = new TextView(getContext());
        TextView detailInfo = new TextView(getContext());

        detailTitle.setText(title);
        detailInfo.setText(info);

        detailItem.addView(detailTitle);
        detailItem.addView(detailInfo);

        phoneDetailLinearLayout.addView(detailItem);
    }

    @NeedsPermission({Manifest.permission.READ_PHONE_STATE})
    public void initDetail() {
        addTextView("机器代号", Build.BOARD);
        addTextView("引导", Build.BOOTLOADER);
        addTextView("品牌", Build.BRAND);
        addTextView("X64 X86", Arrays.toString(Build.SUPPORTED_ABIS));
        addTextView("工业代号", Build.DEVICE);
        addTextView("版本号", Build.DISPLAY);
        addTextView("指纹", Build.FINGERPRINT);
        addTextView("生产厂商", Build.MANUFACTURER);
        addTextView("机器", Build.MODEL);
        addTextView("产品代号", Build.PRODUCT);
        addTextView("基带", Build.getRadioVersion());
        addTextView("CPU", Build.HARDWARE);
        addTextView("系统版本", Build.VERSION.RELEASE);
        addTextView("SDK版本", Build.VERSION.SDK + " " + Build.VERSION.SDK_INT);
        addTextView("Android ID", Settings.Secure.getString(getContext().getContentResolver(), Settings.Secure.ANDROID_ID));
        addTextView("IMEI", ((TelephonyManager) getContext().getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId());
        addTextView("IMSI", ((TelephonyManager) getContext().getSystemService(Context.TELEPHONY_SERVICE)).getSubscriberId());
        try {
            addTextView("序列号", Build.getSerial());
        } catch (NoSuchMethodError error) {
            error.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PhoneDetailFragmentPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}

