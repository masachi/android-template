package ga.cv3sarato.android.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import ga.cv3sarato.android.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ApplyTagView extends LinearLayout {


    @BindView(R.id.apply_tag_icon_imageView)
    ImageView applyTagIconImageView;
    @BindView(R.id.apply_tag_text_textView)
    TextView applyTagTextTextView;

    private int icon;
    private String tag;
    private int iconHeight;
    private int iconWidth;
    private boolean iconVisible;

    public ApplyTagView(Context context) {
        super(context);
    }

    public ApplyTagView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        View tagView = LayoutInflater.from(context).inflate(R.layout.apply_tag_view, this, true);
        ButterKnife.bind(tagView);

        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.ApplyTagView);
        icon = attributes.getResourceId(R.styleable.ApplyTagView_setIcon, 0);
        tag = attributes.getString(R.styleable.ApplyTagView_setText);
        iconHeight = attributes.getDimensionPixelSize(R.styleable.ApplyTagView_iconHeight, 10);
        iconWidth = attributes.getDimensionPixelSize(R.styleable.ApplyTagView_iconWidth, 10);
        iconVisible = attributes.getBoolean(R.styleable.ApplyTagView_iconVisible, true);
        attributes.recycle();

        applyTagIconImageView.setImageResource(icon);
        applyTagIconImageView.setLayoutParams(new ViewGroup.LayoutParams(iconWidth, iconHeight));
        applyTagIconImageView.setVisibility(iconVisible ? View.VISIBLE : View.GONE);
        applyTagTextTextView.setText(tag);

    }

    public ApplyTagView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ApplyTagView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}
