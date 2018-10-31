
package com.megvii.livenesslib.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

/**
 *画倒计时圆通过传递 Progress和max来画
 */
public class CircleProgressBar extends View {

    private static final int STD_WIDTH = 20;
    private static final int STD_RADIUS = 75;

    private final TextPaint textPaint;
    SweepGradient sweepGradient = null;
    private int progress = 100;
    private int max = 100;
    private Paint paint;
    private RectF oval;
    private int mWidth = STD_WIDTH;
    private int mRadius = STD_RADIUS;
    private Bitmap bit;

    public CircleProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        oval = new RectF();
        textPaint = new TextPaint();
        // bit = BitmapFactory.decodeResource(getResources(), R.drawable.mg_liveness_circle);
        sweepGradient = new SweepGradient(getWidth() / 2, getHeight() / 2, new int[]{0xfffe9a8e, 0xff3fd1e4
                , 0xffdc968e}, null);
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int use = width > height ? height : width;
        int sum = STD_WIDTH + STD_RADIUS;
        try {
            mWidth = (STD_WIDTH * use) / (2 * sum);
            mRadius = (STD_RADIUS * use) / (2 * sum);
        } catch (Exception e) {
            mWidth = 1;
            mRadius = 1;
        }
        setMeasuredDimension(use, use);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setAntiAlias(true);
        paint.setFlags(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(0xff000000);
        paint.setStrokeWidth(mWidth);// 设置画笔宽度
        paint.setStyle(Paint.Style.STROKE);// 设置中空的样式
        canvas.drawCircle(mWidth + mRadius, mWidth + mRadius, mRadius, paint);// 在中心为（100,100）的地方画个半径为55的圆，宽度为setStrokeWidth：10，也就是灰色的底边
        paint.setColor(0xff3fd1e4);// 设置画笔为绿色
        oval.set(mWidth, mWidth, mRadius * 2 + mWidth, (mRadius * 2 + mWidth));// 设置类似于左上角坐标（45,45），右下角坐标（155,155），这样也就保证了半径为55
        canvas.drawArc(oval, -90, ((float) progress / max) * 360, false, paint);// 画圆弧，第二个参数为：起始角度，第三个为跨的角度，第四个为true的时候是实心，false的时候为空心
        paint.reset();

    }
}
