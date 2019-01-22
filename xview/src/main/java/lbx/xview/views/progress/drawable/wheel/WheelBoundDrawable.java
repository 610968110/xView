package lbx.xview.views.progress.drawable.wheel;

import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.view.animation.AnticipateInterpolator;

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

public class WheelBoundDrawable extends BaseProgressDrawable {

    private int mDelay;
    private int mDuration;
    private Rect mBounds;
    private Paint mPaint;
    private float percent;

    public WheelBoundDrawable(int color, int alpha, int duration, int delay) {
        this.mDuration = duration;
        this.mDelay = delay;
        mPaint = DrawUtil.getDefaultPaint(Color.WHITE, 255);
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
        valueAnimator.setRepeatMode(ValueAnimator.REVERSE);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.setInterpolator(new AnticipateInterpolator());
        return valueAnimator;
    }

    @Override
    public void onDraw(@NonNull Canvas canvas) {
        canvas.drawCircle(mBounds.centerX(), mBounds.centerY(), mBounds.width() / 2 * (1.0F - percent * 0.4F), mPaint);
    }

    @Override
    public void animatorUpdate(@NonNull float progress) {
        percent = progress / 1000F;
    }
}
