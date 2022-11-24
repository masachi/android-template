package ga.cv3sarato.android.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import ga.cv3sarato.android.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProfileItem extends LinearLayout {
    @BindView(R.id.item_leftText)
    TextView itemLeftText;
    @BindView(R.id.item_rightText)
    TextView itemRightText;
    @BindView(R.id.separator_view)
    View separatorView;
    @BindView(R.id.left_iconView)
    ImageView leftIconView;
    @BindView(R.id.left_view)
    RelativeLayout leftView;
    @BindView(R.id.right_iconView)
    ImageView rightIconView;
    @BindView(R.id.right_view)
    RelativeLayout rightView;

    private String leftText;
    private String rightText;
    private boolean needSeparator;
    private int separatorColor;
    private int leftIcon;
    private int rightIcon;

    public ProfileItem(Context context) {
        super(context);
    }

    public ProfileItem(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        View itemView = LayoutInflater.from(context).inflate(R.layout.profile_item, this, true);
        ButterKnife.bind(itemView);

        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.ProfileItem);
        leftText = attributes.getString(R.styleable.ProfileItem_setLeftText);
        rightText = attributes.getString(R.styleable.ProfileItem_setRightText);
        leftIcon = attributes.getResourceId(R.styleable.ProfileItem_setLeftIcon, 0);
        rightIcon = attributes.getResourceId(R.styleable.ProfileItem_setRightIcon, 0);
        needSeparator = attributes.getBoolean(R.styleable.ProfileItem_needSeparator, true);
        separatorColor = attributes.getColor(R.styleable.ProfileItem_separatorColor, Color.parseColor("#cccccc"));
        attributes.recycle();

        itemLeftText.setText(leftText);
        itemRightText.setText(rightText);

        if(leftIcon == 0) {
            leftIconView.setVisibility(View.GONE);
        }
        else {
            leftIconView.setImageResource(leftIcon);
        }

        if(rightIcon == 0) {
            rightIconView.setVisibility(View.GONE);
        }
        else {
            rightIconView.setImageResource(rightIcon);
        }

        if (needSeparator) {
            separatorView.setVisibility(View.VISIBLE);
            separatorView.setBackgroundColor(separatorColor);
        } else {
            separatorView.setVisibility(View.GONE);
        }
    }

    public ProfileItem(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ProfileItem(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    public void setLeftText(String leftText) {
        itemLeftText.setText(leftText);
    }

    public void setRightText(String rightText) {
        itemRightText.setText(rightText);
    }

    public String getLeftText() {
        return itemLeftText.getText().toString();
    }

    public String getRightText() {
        return itemRightText.getText().toString();
    }

    public boolean isNeedSeparator() {
        return needSeparator;
    }

    public void setNeedSeparator(boolean needSeparator) {
        this.needSeparator = needSeparator;
    }

    public int getSeparatorColor() {
        return separatorColor;
    }

    public void setSeparatorColor(int separatorColor) {
        this.separatorColor = separatorColor;
    }

    public int getLeftIcon() {
        return leftIcon;
    }

    public void setLeftIcon(int leftIcon) {
        this.leftIcon = leftIcon;
    }

    public int getRightIcon() {
        return rightIcon;
    }

    public void setRightIcon(int rightIcon) {
        this.rightIcon = rightIcon;
    }
}
