package lbx.xview.views.progress.drawable.cubes;

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
 * @date 2018/8/21.
 */

public class CubesDrawable extends BaseProgressDrawable {

    private int mDuration;
    private int w;
    private Paint mPaint;
    private float mProgress;

    public CubesDrawable(int duration, int color, int alpha) {
        this.mDuration = duration;
        mPaint = DrawUtil.getDefaultPaint(color, alpha);
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        w = bounds.width();
    }

    @Override
    public ValueAnimator onCreateAnimation() {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0F, 1000F);
        valueAnimator.setDuration(mDuration);
        valueAnimator.setRepeatMode(ValueAnimator.REVERSE);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        return valueAnimator;
    }

    @Override
    public void onDraw(@NonNull Canvas canvas) {
        float percent = mProgress / 1000F;
        int save = canvas.save();
        float sc = 1.0F - (percent >= 0.5F ? 1 - percent : percent) * 0.5F;
        canvas.scale(sc, sc);
        canvas.drawRect(0, 0, w, w, mPaint);
        canvas.restoreToCount(save);
    }

    @Override
    public void animatorUpdate(@NonNull float progress) {
        mProgress = progress;
    }
}
