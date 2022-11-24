package ga.cv3sarato.android.utils.pdf;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import com.chenenyu.router.Router;
import com.chenenyu.router.annotation.InjectParam;
import com.chenenyu.router.annotation.Route;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.util.FitPolicy;
import ga.cv3sarato.android.R;
import ga.cv3sarato.android.base.BaseToolbarActivity;

import java.io.IOException;
import java.net.URL;

import butterknife.BindView;


@Route(value = "gtedx://pdfViewer")
public class PDFViewerActivity extends BaseToolbarActivity {

    @InjectParam(key = "uri")
    String uri;
    @BindView(R.id.pdf_viewer)
    PDFView pdfViewer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Router.injectParams(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void init() throws Exception {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    pdfViewer.fromStream(new URL(uri).openStream())
                            .enableSwipe(true)
                            .enableDoubletap(true)
                            .defaultPage(0)
                            .enableAnnotationRendering(false)
                            .enableAntialiasing(true)
                            .spacing(0)
                            .autoSpacing(false)
                            .pageFitPolicy(FitPolicy.WIDTH)
                            .pageSnap(true)
                            .pageFling(false)
                            .load();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pdfviewer;
    }
}
