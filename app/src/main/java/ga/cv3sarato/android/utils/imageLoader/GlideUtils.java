package ga.cv3sarato.android.utils.imageLoader;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.bumptech.glide.request.RequestOptions;
import ga.cv3sarato.android.BuildConfig;
import ga.cv3sarato.android.MainApplication;

import java.net.URL;
import java.util.HashMap;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class GlideUtils extends ImageLoader {
    private Context context;
    private Object path;
    private ImageView view;
    private RequestOptions options = RequestOptions.circleCropTransform();

    public GlideUtils(Context context) {
        this.context = context;
    }

    public static ImageLoader newInstance(Context context) {
        return new GlideUtils(context);
    }

    @Override
    public ImageLoader setPath(Object path) {
        this.path = path;
        return this;
    }

    @Override
    public ImageLoader setImageView(View view) {
        this.view = (ImageView) view;
        return this;
    }

    @Override
    public ImageLoader setOptions(RequestOptions options) {
        this.options = options;
        return this;
    }

    @Override
    public ImageLoader setRequestHeader(HashMap<String, String> headers) {
        return this;
    }

    @Override
    public ImageLoader withDefaultHeaders() {
        if (path instanceof String && ((String) path).startsWith(BuildConfig.SERVER_URL)) {
            GlideUrl pathWithHeader = new GlideUrl(
                    (String) path,
                    new LazyHeaders.Builder()
                            .addHeader("Authorization", "Bearer " + MainApplication.getInstance().getAccessToken())
                            .addHeader("X-Hylaa-TenantId", MainApplication.getInstance().getTenantID())
                            .build()
            );
            path = pathWithHeader;
        }
        return this;
    }

    @Override
    public void loadImage() {
        GlideApp.with(context)
                .load(path)
                .transition(withCrossFade())
                .into(view);
    }

    @Override
    public void loadGIF() {
        GlideApp.with(context)
                .asGif()
                .load(path)
                .transition(withCrossFade())
                .into(view);
    }

    @Override
    public void loadImageWithTransform() {
        GlideApp.with(context)
                .load(path)
                .apply(options)
                .transition(withCrossFade())
                .into(view);
    }
}
