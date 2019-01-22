package lbx.xview.views.progress.drawable.pyramid;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.view.animation.AccelerateDecelerateInterpolator;

import lbx.xview.utils.DrawUtil;
import lbx.xview.views.progress.drawable.base.BaseProgressDrawable;

/**
 * .  ┏┓　　　┏┓
 * .┏┛┻━━━┛┻┓
 * .┃　　　　　　　┃
 * .┃　　　━　　　┃
 * .┃　┳┛　┗┳　┃
 * .┃　　　　　　　┃
 * .┃　　　┻　　　┃
 * .┃　　　　　　　┃
 * .┗━┓　　　┏━┛
 * .    ┃　　　┃        神兽保佑
 * .    ┃　　　┃          代码无BUG!
 * .    ┃　　　┗━━━┓
 * .    ┃　　　　　　　┣┓
 * .    ┃　　　　　　　┏┛
 * .    ┗┓┓┏━┳┓┏┛
 * .      ┃┫┫　┃┫┫
 * .      ┗┻┛　┗┻┛
 *
 * @author lbx
 * @date 2018/8/20.
 */

public abstract class BasePyramidBoundDrawable extends BaseProgressDrawable {

    private Paint mPaint;
    private Rect bounds;
    int mCenterX;
    int mCenterY;
    private int mDuration;
    private int mDelay;
    private int count;
    private Point[] mPoints;
    Point point;

    public BasePyramidBoundDrawable(Point[] points, int pos, int duration, int delay, int color,int alpha) {
        this.mPoints = points;
        this.point = points[pos];
        this.mCenterX = point.x;
        this.mCenterY = point.y;
        this.mDuration = duration;
        this.mDelay = delay;
        mPaint = DrawUtil.getDefaultPaint(color, alpha);
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        this.bounds = bounds;
    }

    @Override
    public ValueAnimator onCreateAnimation() {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0F, 1000F);
        valueAnimator.setDuration(mDuration);
        valueAnimator.setStartDelay(mDelay);
        valueAnimator.setRepeatMode(ValueAnimator.RESTART);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                count = 0;
            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                count++;
                animationRepeat(count);
            }
        });
        return valueAnimator;
    }

    @Override
    public void onDraw(@NonNull Canvas canvas) {
        canvas.drawCircle(mCenterX, mCenterY, bounds.width() / 2, mPaint);
    }

    @Override
    public void animatorUpdate(@NonNull float progress) {
        if (count > 5) {
            count = 0;
        }
        float percent = progress / 1000F;
        animatorUpdate(percent, count);
    }

    public abstract void animatorUpdate(float percent, int count);

    public abstract void animationRepeat(int count);

    int[] point2Point(int pointStart, int pointEnd, int[] array) {
        array[0] = mPoints[pointEnd].x - mPoints[pointStart].x;
        array[1] = mPoints[pointEnd].y - mPoints[pointStart].y;
        return array;
    }

    void reset() {
        mCenterX = point.x;
        mCenterY = point.y;
    }

    void setPoint(int pos) {
        this.point = mPoints[pos];
        reset();
    }
}
