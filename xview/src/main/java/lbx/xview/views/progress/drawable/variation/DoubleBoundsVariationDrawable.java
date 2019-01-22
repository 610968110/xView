package lbx.xview.views.progress.drawable.variation;

import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.view.animation.LinearInterpolator;

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

public class DoubleBoundsVariationDrawable extends ProgressDrawableContainer {

    private float mProgress;
    private int mDelay;
    private int mDuration;
    private Rect mBounds;
    private BaseProgressDrawable[] drawables = new BaseProgressDrawable[2];
    /**
     * 球到边框的距离
     */
    private static int OFFSET;
    private int mChildSize;

    public DoubleBoundsVariationDrawable(int color, int alpha, int duration, int delay) {
        this.mDuration = duration;
        this.mDelay = delay;
        drawables[0] = new VariationBoundsDrawable(duration/2, delay/2, color, alpha);
        drawables[1] = new VariationBoundsDrawable(duration/2, duration/2, color, alpha);
    }

    @Override
    public ValueAnimator onCreateAnimation() {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0F, 1000F);
        valueAnimator.setDuration(mDuration);
        valueAnimator.setStartDelay(mDelay);
        valueAnimator.setRepeatMode(ValueAnimator.RESTART);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.setInterpolator(new LinearInterpolator());
        return valueAnimator;
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        mBounds = bounds;
        OFFSET = bounds.width() / 4;
        mChildSize = bounds.width() / 3;
        super.onBoundsChange(new Rect(0, 0, mChildSize, mChildSize));
    }

    @Override
    public void onDraw(@NonNull Canvas canvas) {
        float percent = mProgress / 1000F;
        int save = canvas.save();
        canvas.rotate(percent * 360, mBounds.centerX(), mBounds.centerY());
        drawChild(canvas);
        canvas.restoreToCount(save);
    }

    private void drawChild(@NonNull Canvas canvas) {
        int save = canvas.save();
        canvas.translate(OFFSET, OFFSET);
        drawables[0].draw(canvas);
        canvas.restoreToCount(save);

        canvas.translate(mBounds.width() - OFFSET - mChildSize, mBounds.height() - OFFSET - mChildSize);
        drawables[1].draw(canvas);
        canvas.restoreToCount(save);
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
