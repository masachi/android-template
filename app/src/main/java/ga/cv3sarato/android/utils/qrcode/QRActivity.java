package ga.cv3sarato.android.utils.qrcode;

import android.Manifest;
import android.app.DialogFragment;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;

import com.chenenyu.router.Router;
import com.chenenyu.router.annotation.Route;
import com.google.zxing.ResultPoint;
import com.google.zxing.client.android.BeepManager;
import ga.cv3sarato.android.R;
import ga.cv3sarato.android.base.BaseActivity;
import ga.cv3sarato.android.fragment.QRResultFragment;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.CaptureManager;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;

@Route(value = "gtedx://qrcode")
public class QRActivity extends BaseActivity implements BarcodeCallback{

    @BindView(R.id.barcodeView)
    DecoratedBarcodeView barcodeView;
    private CaptureManager captureManager;

    private String lastText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        callQRScan(savedInstanceState);
    }

    @Override
    protected void init() {
        captureManager = new CaptureManager(this, barcodeView);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_qr;
    }
    public void callQRScan(Bundle savedInstanceState) {
        captureManager.initializeFromIntent(getIntent(), savedInstanceState);
        barcodeView.decodeContinuous(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        captureManager.onPause();
        barcodeView.pause();
    }

    @Override
    public void onResume() {
        super.onResume();
        captureManager.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        captureManager.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        captureManager.onSaveInstanceState(outState);
    }

    @Override
    public void barcodeResult(BarcodeResult result) {
        if(result.getText() == null || result.getText().equals(lastText)) {
            // Prevent duplicate scans
            return;
        }
        lastText = result.getText();
        ((QRResultFragment) Router.build("gtedx://qr_result").with("qr_result", result.getText()).getFragment(this)).show(this.getSupportFragmentManager(), "QR_RESULT");
    }

    @Override
    public void possibleResultPoints(List<ResultPoint> resultPoints) {

    }
}
