package lbx.xview.views.progress.drawable.wheel;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.view.animation.LinearInterpolator;

import lbx.xview.utils.DrawUtil;
import lbx.xview.views.progress.drawable.base.BaseProgressDrawable;
import lbx.xview.views.progress.drawable.base.ProgressDrawableContainer;

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
 * @date 2018/8/22.
 */

public class WheelDrawable extends ProgressDrawableContainer {

    private float mProgress;
    private int mDelay;
    private int mDuration;
    private Rect mBounds;
    /**
     * 球到边框的距离
     */
    private int OFFSET;
    private int radius;
    private int mChildSize;
    private Paint mPaint;
    private BaseProgressDrawable[] drawables;
    private int count;

    public WheelDrawable(int color, int alpha, int duration, int delay) {
        this.mDuration = duration;
        this.mDelay = delay;
        mPaint = DrawUtil.getDefaultPaint(color, alpha);
        drawables = new BaseProgressDrawable[]{new WheelBoundDrawable(color, alpha, duration / 4, delay)};
    }

    @Override
    public ValueAnimator onCreateAnimation() {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0F, 1000F);
        valueAnimator.setDuration(mDuration);
        valueAnimator.setStartDelay(mDelay);
        valueAnimator.setRepeatMode(ValueAnimator.REVERSE);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

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
            }
        });
        return valueAnimator;
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        mBounds = bounds;
        OFFSET = bounds.width() / 6;
        radius = mBounds.width() / 2 - OFFSET;
        mChildSize = bounds.width() / 5;
        super.onBoundsChange(new Rect(0, 0, mChildSize, mChildSize));
    }

    @Override
    public void onDraw(@NonNull Canvas canvas) {
        float percent = mProgress / 1000F;
        int save = canvas.save();
        canvas.rotate(360 * (count % 2 == 0 ? percent : 1 - percent), mBounds.centerX(), mBounds.centerY());
        draw(canvas, percent);
        canvas.restoreToCount(save);
    }

    private void draw(@NonNull Canvas canvas, float percent) {
        canvas.drawCircle(mBounds.centerX(), mBounds.centerY(), radius * (1.0F - percent * 0.2F), mPaint);
        int save = canvas.save();
        canvas.translate(OFFSET + mChildSize / 1.5F, OFFSET + mChildSize / 1.5F);
        drawChild(canvas);
        canvas.restoreToCount(save);
    }

    private void drawChild(Canvas canvas) {
        for (BaseProgressDrawable d : drawables) {
            if (d != null) {
                d.draw(canvas);
            }
        }
    }

    @Override
    public void animatorUpdate(@NonNull float progress) {
        mProgress = progress;
    }

    @Override
    public BaseProgressDrawable[] onCreateChild() {
        return drawables;
    }
}
