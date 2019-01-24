package lbx.xview.views.progress.drawable;

import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.view.animation.AccelerateDecelerateInterpolator;

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
 * @date 2018/7/10.
 */

public class WaveDrawable extends ProgressDrawableContainer {

    private final Paint mPaint;
    private float mProgress;
    private int mDelay;
    private int mDuration;
    private Rect[] mRect;
    /**
     * 条条数量
     */
    private int num = 5;

    public WaveDrawable(int color, int alpha, int duration, int delay) {
        mPaint = DrawUtil.getDefaultPaint(color, alpha);
        this.mDuration = duration;
        this.mDelay = delay;
        mRect = new Rect[num];
        for (int i = 0; i < mRect.length; i++) {
            mRect[i] = new Rect();
        }
    }

    @Override
    public ValueAnimator onCreateAnimation() {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0F, 1500F);
        valueAnimator.setDuration(mDuration);
        valueAnimator.setStartDelay(mDelay);
        valueAnimator.setRepeatMode(ValueAnimator.REVERSE);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        return valueAnimator;
    }

    @Override
    public void onDraw(@NonNull Canvas canvas) {
        if (mProgress < 300) {
            mProgress = 300;
        }
        Rect rect = getBounds();
        int width = rect.width();
        //左边间距
        int leftPadding = width / 6;
        //条条宽度
        final int sizeW = 20;
        //条条间距
        final int margin = (width - 2 * leftPadding - num * sizeW) / (num - 1);
        //上边距
        canvas.scale(1, 0.6F, rect.centerX(), rect.centerY());
        for (int i = 0; i < num; i++) {
            float top = width * (1500F - mProgress) / 1500F / 2F;
            int left = i * (sizeW + margin) + leftPadding;
            mRect[i].set(left, (int) top, left + sizeW, (int) (rect.bottom - top));
            canvas.drawRect(mRect[i], mPaint);
        }
    }

    @Override
    public BaseProgressDrawable[] onCreateChild() {
        return null;
    }

    @Override
    public void animatorUpdate(@NonNull float progress) {
        mProgress = progress;
    }
}