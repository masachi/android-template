package ga.cv3sarato.android.utils.image;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.chenenyu.router.Router;
import com.chenenyu.router.annotation.InjectParam;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.github.piasy.biv.view.BigImageView;
import ga.cv3sarato.android.BuildConfig;
import ga.cv3sarato.android.MainApplication;
import ga.cv3sarato.android.R;
import ga.cv3sarato.android.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class BigImageFragment extends BaseFragment {


    Unbinder unbinder;
    @InjectParam(key = "uri")
    String uri;
    @BindView(R.id.bigImageView)
    BigImageView bigImageView;

    private View.OnClickListener OnClickListener;
    private View.OnLongClickListener OnLongClickListener;

    public static BigImageFragment newInstance(String uri) {
        BigImageFragment fragment = new BigImageFragment();
        Bundle bundle = new Bundle();
        bundle.putString("uri", uri);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_big_image, null);
        unbinder = ButterKnife.bind(this, rootView);
        Router.injectParams(this);
        bigImageView.showImage(convertUriWithHeaders(uri));
        bigImageView.getSSIV().setOrientation(SubsamplingScaleImageView.ORIENTATION_USE_EXIF);
        bigImageView.setOnClickListener(OnClickListener);
        bigImageView.setOnLongClickListener(OnLongClickListener);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public BigImageFragment setOnClickListener(View.OnClickListener OnClickListener) {
        this.OnClickListener = OnClickListener;
        return this;
    }

    public BigImageFragment setOnLongClickListener(View.OnLongClickListener OnLongClickListener) {
        this.OnLongClickListener = OnLongClickListener;
        return this;
    }

    private Uri convertUriWithHeaders(Object path) {
        if (path instanceof String && ((String) path).startsWith(BuildConfig.SERVER_URL)) {
            GlideUrl pathWithHeader = new GlideUrl(
                    (String) path,
                    new LazyHeaders.Builder()
                            .addHeader("Authorization", "Bearer " + MainApplication.getInstance().getAccessToken())
                            .addHeader("X-Hylaa-TenantId", MainApplication.getInstance().getTenantID())
                            .build()
            );

            return Uri.parse(pathWithHeader.toString());
        }

        return Uri.parse((String) path);
    }
}
