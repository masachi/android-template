package ga.cv3sarato.android.utils.imageLoader;

import android.view.View;

import com.bumptech.glide.request.RequestOptions;

import java.util.HashMap;

public abstract class ImageLoader {

    public abstract void loadImage();

    public abstract void loadGIF();

    public abstract void loadImageWithTransform();

    public abstract ImageLoader setPath(Object path);

    public abstract ImageLoader setImageView(View view);

    public abstract ImageLoader setOptions(RequestOptions options);

    public abstract ImageLoader setRequestHeader(HashMap<String, String> headers);

    public abstract ImageLoader withDefaultHeaders();

}
