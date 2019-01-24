package lbx.xview.views.progress.drawable.base;

import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.PixelFormat;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import lbx.xview.utils.AnimationUtils;

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

public abstract class BaseProgressDrawable extends Drawable implements
        Animatable,
        ValueAnimator.AnimatorUpdateListener,
        Drawable.Callback {

    private ValueAnimator mValueAnimator;

    @Override
    public void start() {
        if (AnimationUtils.isStarted(mValueAnimator)) {
            return;
        }
        mValueAnimator = obtainAnimation();
        if (mValueAnimator == null) {
            return;
        }
        AnimationUtils.start(mValueAnimator);
        invalidateSelf();
    }

    @Override
    public boolean isRunning() {
        return AnimationUtils.isRunning(mValueAnimator);
    }

    @Override
    public void stop() {
        if (AnimationUtils.isStarted(mValueAnimator)) {
            mValueAnimator.removeAllUpdateListeners();
            mValueAnimator.end();
        }
    }

    private ValueAnimator obtainAnimation() {
        if (mValueAnimator == null) {
            mValueAnimator = onCreateAnimation();
        }
        if (mValueAnimator != null) {
            mValueAnimator.addUpdateListener(this);
        }
        return mValueAnimator;
    }

    public abstract ValueAnimator onCreateAnimation();

    public abstract void onDraw(@NonNull Canvas canvas);

    public abstract void animatorUpdate(@NonNull float progress);

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        final Callback callback = getCallback();
        if (callback != null) {
            callback.invalidateDrawable(this);
        }
        float value = (float) animation.getAnimatedValue();
        animatorUpdate(value);
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        onDraw(canvas);
    }

    @Override
    public void setAlpha(@IntRange(from = 0, to = 255) int alpha) {

    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {
        return PixelFormat.RGBA_8888;
    }

    @Override
    public void invalidateDrawable(Drawable who) {
        invalidateSelf();
    }

    @Override
    public void scheduleDrawable(@NonNull Drawable who, @NonNull Runnable what, long when) {

    }

    @Override
    public void unscheduleDrawable(@NonNull Drawable who, @NonNull Runnable what) {

    }
}
