package lbx.xview.views.progress.drawable.splitcube;

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
 * @date 2018/8/22.
 */

public class SplitCubeDrawable extends ProgressDrawableContainer {

    private float mPercent;
    private int mDelay;
    private int mDuration;
    private Rect mBounds;
    private int mChildSize;
    /**
     * 正方体之间的缝隙
     */
    private int mCrevice = 4;
    protected BaseProgressDrawable[] drawables;

    public float getPercent() {
        return mPercent;
    }

    public void setCrevice(int crevice) {
        this.mCrevice = crevice;
    }

    public int getChildSize() {
        return mChildSize;
    }

    public SplitCubeDrawable(int color, int alpha, int duration, int delay) {
        this.mDuration = duration;
        this.mDelay = delay;
        drawables = new BaseProgressDrawable[]{
                new SplitCubeItemDrawable(color, alpha, duration, delay),
                new SplitCubeItemDrawable(color, alpha, duration, delay),
                new SplitCubeItemDrawable(color, alpha, duration, delay),
                new SplitCubeItemDrawable(color, alpha, duration, delay)
        };
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
    protected void onBoundsChange(Rect bounds) {
        mBounds = bounds;
        mChildSize = bounds.width() / 5;
        //正方体发的边到边框的距离
        int offset = mBounds.width() / 2 - mChildSize;
        drawables[0].setBounds(new Rect(
                offset - mCrevice / 2,
                offset - mCrevice / 2,
                offset + mChildSize + mCrevice / 2,
                offset + mChildSize + mCrevice / 2));
        drawables[1].setBounds(new Rect(
                offset + mChildSize - mCrevice / 2,
                offset - mCrevice / 2,
                offset + mChildSize * 2 + mCrevice / 2,
                offset + mChildSize + mCrevice / 2));
        drawables[2].setBounds(new Rect(
                offset - mCrevice / 2,
                offset + mChildSize - mCrevice / 2,
                offset + mChildSize + mCrevice / 2,
                offset + mChildSize * 2 + mCrevice / 2));
        drawables[3].setBounds(new Rect(
                offset + mChildSize - mCrevice / 2,
                offset + mChildSize - mCrevice / 2,
                offset + mChildSize * 2 + mCrevice / 2,
                offset + mChildSize * 2 + mCrevice / 2));
        super.onBoundsChange(null);
    }

    @Override
    public void onDraw(@NonNull Canvas canvas) {
        for (BaseProgressDrawable d : drawables) {
            if (d != null) {
                d.draw(canvas);
            }
        }
    }

    @Override
    public void animatorUpdate(@NonNull float progress) {
        mPercent = progress / 1000F;
    }

    @Override
    public BaseProgressDrawable[] onCreateChild() {
        return drawables;
    }
}
