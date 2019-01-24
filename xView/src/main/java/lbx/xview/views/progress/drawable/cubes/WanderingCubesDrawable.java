package lbx.xview.views.progress.drawable.cubes;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.view.animation.AccelerateDecelerateInterpolator;

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
 * @date 2018/7/10.
 */

public class WanderingCubesDrawable extends ProgressDrawableContainer {

    private float mProgress;
    private int mDelay;
    private int mDuration;
    private Rect mBounds;
    private BaseProgressDrawable[] drawables = new BaseProgressDrawable[2];
    private int offset;
    private int mChildW;
    private int mCount;
    private int color;
    private int alpha;

    public WanderingCubesDrawable(int color, int alpha, int duration, int delay) {
        this.mDuration = duration;
        this.mDelay = delay;
        this.color = color;
        this.alpha = alpha;
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

            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                mCount++;
            }
        });
        return valueAnimator;
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        mBounds = bounds;
        mChildW = bounds.width() / 6;
        offset = (int) (mChildW * 1.4F);
        for (int i = 0; i < drawables.length; i++) {
            drawables[i] = new CubesDrawable(mDuration, color, alpha);
        }
        super.onBoundsChange(new Rect(0, 0, mChildW, mChildW));
    }

    @Override
    public void onDraw(@NonNull Canvas canvas) {
        float percent = mProgress / 1000F;
        //旋转角度
        int angle = (int) (180 * percent) + (mCount % 2 == 0 ? 0 : 180);
        int count = canvas.save();
        canvas.rotate(angle, mBounds.centerX(), mBounds.centerY());
        drawChild(canvas, angle);
        canvas.restoreToCount(count);
    }

    private void drawChild(@NonNull Canvas canvas, int angle) {
        if (drawables != null) {
            int length = drawables.length;
            for (int i = 0; i < length; i++) {
                BaseProgressDrawable drawable = drawables[i];
                if (drawable != null) {
                    int count = canvas.save();
                    if (i == 0) {
                        //第一个方块
                        canvas.translate(offset, offset);
                    } else if (i == 1) {
                        //第二个方块
                        int dx = mBounds.width() - offset - mChildW;
                        int dy = mBounds.height() - offset - mChildW;
                        canvas.translate(dx, dy);
                    }
                    drawable.draw(canvas);
                    canvas.restoreToCount(count);
                }
            }
        }
    }

    @Override
    public BaseProgressDrawable[] onCreateChild() {
        return drawables;
    }

    @Override
    public void animatorUpdate(@NonNull float progress) {
        mProgress = progress;
    }
}