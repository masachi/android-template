package ga.cv3sarato.android.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import ga.cv3sarato.android.R;

public class CircleView extends View {

    private Paint paint;
    private Paint.Style style = Paint.Style.FILL;
    private int color;
    private int radius;

    public CircleView(Context context) {
        super(context);
    }

    public CircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.CircleView);
        color = attributes.getColor(R.styleable.CircleView_innerColor, Color.BLACK);
        radius = attributes.getDimensionPixelSize(R.styleable.CircleView_radius, 50);
        initPaint();
    }

    private void initPaint() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(color);
        paint.setStyle(style);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, radius, paint);
    }

    public void setStyle(Paint.Style style) {
        paint.setStyle(style);
        invalidate();
    }

    public void setColor(int color) {
        paint.setColor(color);
        invalidate();
    }
}
