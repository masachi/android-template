package ga.cv3sarato.android.utils.textInput;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class CleanKeyEditText extends AppCompatEditText implements TextWatcher, View.OnFocusChangeListener{
    private Drawable rightDrawable;

    private boolean hasFocus = false;

    private int xUp = 0;

    public CleanKeyEditText(Context context) {
        super(context);
        init();
    }

    public CleanKeyEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CleanKeyEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        rightDrawable = getCompoundDrawables()[2];
        setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        setOnFocusChangeListener(this);
        addTextChangedListener(this);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if(hasFocus){
            if(TextUtils.isEmpty(charSequence)){
                setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
            }
            else{
                if(rightDrawable == null){
                    rightDrawable = getCompoundDrawables()[2];
                }
                setCompoundDrawablesWithIntrinsicBounds(null, null, rightDrawable, null);
            }
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    @Override
    public void onFocusChange(View view, boolean b) {
        try {
            this.hasFocus = b;
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        try {
            switch (event.getAction()){
                case MotionEvent.ACTION_UP:
                    xUp = (int)event.getX();
                    if((getWidth() - xUp) <= getCompoundPaddingRight()){
                        if(!TextUtils.isEmpty(getText().toString())){
                            setText("");
                        }
                    }
                    break;
                default:
                    break;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return super.onTouchEvent(event);
    }
}
