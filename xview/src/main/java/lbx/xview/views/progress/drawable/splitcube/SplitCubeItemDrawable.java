package lbx.xview.views.progress.drawable.splitcube;

import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Paint;
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
 * @date 2018/8/22.
 */

public class SplitCubeItemDrawable extends BaseProgressDrawable {

    private float mPercent;
    private int mDelay;
    private int mDuration;
    private Rect mBounds;
    private Paint mPaint;

    public SplitCubeItemDrawable(int color, int alpha, int duration, int delay) {
        this.mDuration = duration;
        this.mDelay = delay;
        mPaint = DrawUtil.getDefaultPaint(color, alpha);
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        mBounds = bounds;
    }

    @Override
    public ValueAnimator onCreateAnimation() {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0F, 1000F);
        valueAnimator.setDuration(mDuration);
        valueAnimator.setStartDelay(mDelay);
        valueAnimator.setRepeatMode(ValueAnimator.RESTART);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        return valueAnimator;
    }

    @Override
    public void onDraw(@NonNull Canvas canvas) {
        int save = canvas.save();
        canvas.rotate(360 * mPercent, mBounds.centerX(), mBounds.centerY());
        canvas.drawRect(mBounds, mPaint);
        canvas.restoreToCount(save);
    }

    @Override
    public void animatorUpdate(@NonNull float progress) {
        mPercent = progress / 1000F;
    }
}
