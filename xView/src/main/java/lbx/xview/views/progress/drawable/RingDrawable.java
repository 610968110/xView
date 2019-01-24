package lbx.xview.views.progress.drawable;

import android.animation.ValueAnimator;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.view.animation.LinearInterpolator;

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

public class RingDrawable extends BaseProgressDrawable {

    private float mProgress;
    private int mDelay;
    private int mDuration;
    private Paint mPaint;
    private Rect mBounds;
    private int radius;
    /**
     * 小球的大小
     */
    private int smallBoundSize;
    /**
     * 圆环到边框的距离
     */
    private static final int OFFSET = 30;
    private BlurMaskFilter mMaskFilter;
    private final RectF mArcRectF;
    /**
     * 圆圈的宽度
     */
    private static final int CIRCLE_WIDTH = 4;
    /**
     * 尾巴的弧度
     */
    private static final int TAIL_RADIAN = 70;
    /**
     * 尾巴的起始弧度
     */
    private static final int START_RADIAN = -160;

    public RingDrawable(int color, int alpha, int duration, int delay) {
        this.mDuration = duration;
        this.mDelay = delay;
        mPaint = DrawUtil.getDefaultPaint(color, alpha);
        mArcRectF = new RectF();
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
        super.onBoundsChange(bounds);
        this.mBounds = bounds;
        radius = mBounds.width() / 2 - OFFSET;
        smallBoundSize = radius / 8;
        mMaskFilter = new BlurMaskFilter(smallBoundSize * 2 / 3, BlurMaskFilter.Blur.SOLID);
    }

    @Override
    public void onDraw(@NonNull Canvas canvas) {
        float percent = mProgress / 1000F;
        mPaint.setStrokeWidth(CIRCLE_WIDTH);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAlpha(100);
        mPaint.setShader(null);
        mPaint.setMaskFilter(null);
        canvas.drawCircle(mBounds.centerX(), mBounds.centerY(), radius, mPaint);
        mPaint.setAlpha(255);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setMaskFilter(mMaskFilter);
        int save = canvas.save();
        canvas.rotate(360 * percent, mBounds.centerX(), mBounds.centerY());
        canvas.drawCircle(mBounds.width() / 2, OFFSET, smallBoundSize, mPaint);
        drawTail(canvas);
        canvas.restoreToCount(save);
    }

    /**
     * 画尾巴
     *
     * @param canvas canvas
     */
    private void drawTail(@NonNull Canvas canvas) {
        int save = canvas.save();
        mArcRectF.set(mBounds.left + OFFSET, mBounds.top + OFFSET, mBounds.right - OFFSET, mBounds.bottom - OFFSET);
        mPaint.setStyle(Paint.Style.STROKE);
        //从球的宽度过度到圆圈的宽度的格子数
        int lattice = 5;
        //把尾巴平分后的item弧度(每次画弧度为 itemLattice，lattice 次后总弧度是 TAIL_RADIAN )
        float itemLattice = TAIL_RADIAN * 1.0F / lattice;
        //把笔的宽度平分后的item宽度(分 lattice 次从 smallBoundSize 过渡到 CIRCLE_WIDTH )
        float itemPaintW = smallBoundSize * 1.0F / lattice;
        for (int i = 0; i < lattice; i++) {
            mPaint.setStrokeWidth(mPaint.getStrokeWidth() + itemPaintW);
            canvas.drawArc(mArcRectF, START_RADIAN, itemLattice, false, mPaint);
            canvas.rotate(itemLattice, mBounds.centerX(), mBounds.centerY());
        }
        canvas.restoreToCount(save);
    }

    @Override
    public void animatorUpdate(@NonNull float progress) {
        this.mProgress = progress;
    }
}
