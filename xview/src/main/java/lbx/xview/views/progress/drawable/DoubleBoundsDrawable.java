package lbx.xview.views.progress.drawable;

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
 * @date 2018/7/10.
 */

public class DoubleBoundsDrawable extends BaseProgressDrawable {

    private final Paint mPaint;
    private float mProgress;
    private int mDelay;
    private int mDuration;

    public DoubleBoundsDrawable(int color, int alpha, int duration, int delay) {
        mPaint = DrawUtil.getDefaultPaint(color, alpha);
        this.mDuration = duration;
        this.mDelay = delay;
    }

    @Override
    public ValueAnimator onCreateAnimation() {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0F, 1000F);
        valueAnimator.setDuration(mDuration);
        valueAnimator.setStartDelay(mDelay);
        valueAnimator.setRepeatMode(ValueAnimator.REVERSE);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        return valueAnimator;
    }

    @Override
    public void onDraw(@NonNull Canvas canvas) {
        Rect rect = getBounds();
        canvas.drawCircle(rect.centerX(), rect.centerY(), mProgress / 1000f * rect.width() / 3, mPaint);
        canvas.drawCircle(rect.centerX(), rect.centerY(), (1000 - mProgress) / 1000f * rect.width() / 3, mPaint);
    }

    @Override
    public void animatorUpdate(@NonNull float progress) {
        mProgress = progress;
    }
}