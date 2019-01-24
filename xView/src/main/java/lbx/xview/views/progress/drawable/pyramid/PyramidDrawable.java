package lbx.xview.views.progress.drawable.pyramid;

import android.animation.ValueAnimator;
import android.graphics.Point;
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

public class PyramidDrawable extends ProgressDrawableContainer {

    private float mProgress;
    private int mDelay;
    private int mDuration;
    private int mColor;
    private int mAlpha;
    private BaseProgressDrawable[] drawables = new BaseProgressDrawable[3];
    private Point[] mPoints = new Point[8];

    public PyramidDrawable(int color, int alpha, int duration, int delay) {
        this.mDuration = duration;
        this.mDelay = delay;
        mColor = color;
        mAlpha = alpha;
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        //球的大小
        int boundSize = bounds.width() / 6;
        //球0的球心到边框的距离
        int offset = (int) (boundSize * 2.2F);
        //垂直/水平的球的间距
        int distance = bounds.width() - offset * 2;
        Rect rect = new Rect(0, 0, boundSize, boundSize);
        mPoints[0] = new Point(offset, offset);
        mPoints[1] = new Point(offset + distance / 2, mPoints[0].y);
        mPoints[2] = new Point(offset + distance, mPoints[0].y);
        mPoints[3] = new Point(mPoints[0].x, mPoints[1].x);
        mPoints[4] = new Point(mPoints[2].x, mPoints[3].y);
        mPoints[5] = new Point(mPoints[0].x, mPoints[2].x);
        mPoints[6] = new Point(mPoints[1].x, mPoints[5].y);
        mPoints[7] = new Point(mPoints[2].x, mPoints[5].y);
        drawables[0] = new PyramidBoundDrawable1(mPoints, 1, mDuration, mDelay, mColor,mAlpha);
        drawables[1] = new PyramidBoundDrawable5(mPoints, 5, mDuration, mDelay, mColor,mAlpha);
        drawables[2] = new PyramidBoundDrawable7(mPoints, 7, mDuration, mDelay, mColor,mAlpha);
        super.onBoundsChange(rect);
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
    public BaseProgressDrawable[] onCreateChild() {
        return drawables;
    }

    @Override
    public void animatorUpdate(@NonNull float progress) {
        mProgress = progress;
    }

}