package lbx.xview.views.wave;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import lbx.xview.R;
import lbx.xview.utils.DrawUtil;
import lbx.xview.utils.WindowUtil;

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
 * @date 2018/7/11.
 */

public class XWaveView extends View {

    /**
     * 波浪A的速度
     */
    private int mWaveSpeedA;
    /**
     * 波浪B的速度
     */
    private int mWaveSpeedB;
    /**
     * 波浪A的振幅
     */
    private int mWaveHeightA;
    /**
     * 波浪B的振幅
     */
    private int mWaveHeightB;
    /**
     * 波浪A的周期
     */
    private float mWaveACycle;
    /**
     * 波浪B的周期
     */
    private float mWaveBCycle;
    /**
     * 波浪A的偏移
     */
    private int mOffsetA;
    /**
     * 波浪B的偏移
     */
    private int mOffsetB;
    /**
     * 当前的进度
     */
    private int mProgress;
    private int mMinWave;
    /**
     * 是否处于波浪状态
     */
    private boolean isWaveMoving = true;

    private Paint mWavePaint, mTextPaint, mBgPaint, mArcPaint;
    /**
     * 球形遮罩
     */
    private Bitmap mBallBitmap;
    /**
     * 进度增长的动画
     */
    private ObjectAnimator mProgressAnimator;
    /**
     * 波浪停止的动画
     */
    private ObjectAnimator mWaveStopAnimator;

    private int mBgColor;
    private int mArcColor;
    private int mWaveColor1;
    private int mWaveColor2;
    private int mTextColor;
    private float mTextSize;
    private static final int MAX = 100;
    private static final int MIN = 0;
    private RectF mRectF;
    private Rect mTextRect;
    private Canvas mCacheCanvas;
    private int mSize;
    private int mArcWidth;

    public XWaveView(Context context) {
        this(context, null);
    }

    public XWaveView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XWaveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }


    private void init(Context context, @Nullable AttributeSet attrs) {
        //初始化画笔
        mWavePaint = DrawUtil.getDefaultPaint();
        mTextPaint = DrawUtil.getDefaultPaint();
        mBgPaint = DrawUtil.getDefaultPaint();
        mArcPaint = DrawUtil.getDefaultPaint();
        mRectF = new RectF(0, 0, getWidth(), getHeight());
        mTextRect = new Rect();
        mCacheCanvas = new Canvas();

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.xWave);
        mBgColor = a.getColor(R.styleable.xWave_xWaveBackground, Color.parseColor("#accccccc"));
        mTextColor = a.getColor(R.styleable.xWave_xWaveTextColor, Color.WHITE);
        mTextSize = a.getDimension(R.styleable.xWave_xWaveTextSize, 50);
        mWaveColor1 = a.getColor(R.styleable.xWave_xWaveColor1, Color.BLUE);
        mWaveColor2 = a.getColor(R.styleable.xWave_xWaveColor2, Color.RED);
        mArcColor = a.getColor(R.styleable.xWave_xWaveArcColor, Color.WHITE);
        mArcWidth = (int) a.getDimension(R.styleable.xWave_xWaveArcWidth, 4F);
        mMinWave = (int) a.getDimension(R.styleable.xWave_xMinWave, 0F);
        mProgress = checkProgress(a.getInt(R.styleable.xWave_xWaveProgress, 50));
        a.recycle();

        mWavePaint.setFilterBitmap(true);
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setColor(mTextColor);
        mBgPaint.setColor(mBgColor);
        mBgPaint.setStrokeWidth(4);
        mBgPaint.setStyle(Paint.Style.STROKE);
        mBgPaint.setStyle(Paint.Style.FILL);
        mArcPaint.setColor(mArcColor);
        mArcPaint.setStyle(Paint.Style.STROKE);
        mArcPaint.setStrokeWidth(mArcWidth);

        setWaveProgress(mProgress, 1000, 0);
    }

    private int checkProgress(int progress) {
        if (progress > MAX) {
            progress = MAX;
        } else if (progress < MIN) {
            progress = MIN;
        }
        return progress;
    }

    /**
     * 设置进度
     *
     * @param progress 进度
     * @param duration 动画时间
     * @param delay    延时
     */
    public void setWaveProgress(int progress, long duration, long delay) {
        mProgress = progress;
        startProgress(progress, duration, delay);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int w = MeasureSpec.getSize(widthMeasureSpec);
        int h = MeasureSpec.getSize(heightMeasureSpec);
        mSize = Math.min(w, h);
        setMeasuredDimension(mSize, mSize);
        mRectF.set(0, 0, mSize, mSize);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (w > 0 && h > 0) {
            /**
             * 根据宽高初始化波浪的一些参数
             * 波浪的速度根据宽度的一定比例，这样不同宽度波浪移动的效果保持差不多
             * 波浪的振幅根据高度和默认值，当高度太小就设为高度的一定比例，这样保证不同高度下波浪效果明显
             * 波浪的周期固定即可
             */
            mWaveSpeedA = w / 25;
            mWaveSpeedB = w / 40;
            mWaveHeightA = WindowUtil.getInstance(getContext()).dip2px(5);
            mWaveHeightB = WindowUtil.getInstance(getContext()).dip2px(3);
            if (h / 10 < mWaveHeightA) {
                mWaveHeightA = h / 10;
                mWaveHeightB = h / 17;
            }
            initStopAnimator(mWaveHeightA, mWaveHeightB);
            mWaveACycle = (float) (3 * Math.PI / w);
            mWaveBCycle = (float) (4 * Math.PI / w);

            /**
             * 初始化圆形遮罩
             * 圆形遮罩是一个与组件同大小的椭圆，并且四周为透明
             * 注意：
             *     不在onDraw中直接绘制这个遮罩，因为那样绘制后遮罩只是一个椭圆，使用DST_IN的话在椭圆外的部分
             *     就不会做任何处理，达不到效果；而先做成bitmap的话遮罩是一个方形，椭圆外部分就会去掉，达到效果
             *
             */
            mBallBitmap = Bitmap.createBitmap(mSize, mSize, Bitmap.Config.ARGB_8888);
            mCacheCanvas.setBitmap(mBallBitmap);
            mCacheCanvas.drawOval(mRectF, mWavePaint);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (getHeight() > 0 && getWidth() > 0) {
            canvas.drawCircle(mRectF.centerX(), mRectF.centerY(), (mRectF.width() - mArcWidth) / 2, mBgPaint);
            drawWave(canvas);
            //绘制边缘
            canvas.drawArc(
                    mRectF.left + mArcWidth,
                    mRectF.top + mArcWidth,
                    mRectF.right - mArcWidth,
                    mRectF.bottom - mArcWidth,
                    0, 360, false, mArcPaint);
            drawText(canvas);
        }
    }

    private void drawText(Canvas canvas) {
        String text = mProgress + "%";
        mTextPaint.setColor(mTextColor);
        mTextPaint.setTextSize(60);
        //设置粗体
        mTextPaint.setTypeface(Typeface.DEFAULT_BOLD);
        mTextPaint.getTextBounds(text, 0, text.length(), mTextRect);
        canvas.drawText(text, mRectF.centerX() - mTextRect.width() / 2, mRectF.centerY() + mTextRect.height() / 2, mTextPaint);
    }

    private void drawWave(Canvas canvas) {
        int sc = canvas.saveLayer(0, 0, getWidth(), getHeight(), null, Canvas.ALL_SAVE_FLAG);
        if (isWaveMoving) {
            if (mWaveHeightA == 0) {
                mWaveHeightA = 5;
            }
            if (mWaveHeightB == 0) {
                mWaveHeightB = 5;
            }
            /**
             * 如果有波浪，则绘制两条波浪
             * 波浪实际上是一条条一像素的直线组成的，线的顶端是根据正弦函数得到的
             */
            for (int i = 0; i < getWidth(); i++) {
                mWavePaint.setColor(mWaveColor1);
                canvas.drawLine(i, (int) getWaveY(i, mOffsetA, mWaveHeightA, mWaveACycle), i, getHeight(), mWavePaint);
                mWavePaint.setColor(mWaveColor2);
                canvas.drawLine(i, (int) getWaveY(i, mOffsetB, mWaveHeightB, mWaveBCycle), i, getHeight(), mWavePaint);
            }
        } else {
            /**
             * 如果没有波浪，则绘制两次矩形
             * 之所以绘制两次，是因为波浪有两条，所以除了浪尖的部分，其他部分都是重合的，颜色较重
             */
            float height = (1 - mProgress / 100.0f) * getHeight();
            mWavePaint.setColor(mWaveColor1);
            canvas.drawRect(0, height, getWidth(), getHeight(), mWavePaint);
            mWavePaint.setColor(mWaveColor2);
            canvas.drawRect(0, height, getWidth(), getHeight(), mWavePaint);
        }
        //设置遮罩效果，绘制遮罩
        mWavePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        canvas.drawBitmap(mBallBitmap, 0, 0, mWavePaint);
        mWavePaint.setXfermode(null);
        canvas.restoreToCount(sc);
    }

    /**
     * 设置进度，并且以动画的形式上涨到该进度
     *
     * @param progress 进度
     * @param duration 持续时间
     * @param delay    延时
     */
    public void startProgress(int progress, long duration, long delay) {
        if (mProgressAnimator != null && mProgressAnimator.isRunning()) {
            mProgressAnimator.cancel();
        }
        if (mWaveStopAnimator != null && mWaveStopAnimator.isRunning()) {
            mWaveStopAnimator.cancel();
        }
        isWaveMoving = true;
        mProgressAnimator = ObjectAnimator.ofInt(this, "Progress", progress);
        mProgressAnimator.setDuration(duration);
        mProgressAnimator.setStartDelay(delay);
        mProgressAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mWaveStopAnimator.start();
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
        mProgressAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //改变曲线的偏移，达到波浪运动的效果
                mOffsetA += mWaveSpeedA;
                mOffsetB += mWaveSpeedB;
                invalidate();
            }
        });
        mProgressAnimator.start();
    }

    private void initStopAnimator(final int waveHeightA, final int waveHeightB) {
        /**
         * 创建波浪停止动画
         * 两条波浪振幅逐渐减小
         */
        PropertyValuesHolder holderA = PropertyValuesHolder.ofInt("WaveHeightA", 0);
        PropertyValuesHolder holderB = PropertyValuesHolder.ofInt("WaveHeightB", 0);
        mWaveStopAnimator = ObjectAnimator.ofPropertyValuesHolder(this, holderA, holderB);
        mWaveStopAnimator.setDuration(2000);
        mWaveStopAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                isWaveMoving = false;
                mWaveHeightA = waveHeightA;
                mWaveHeightB = waveHeightB;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                mWaveHeightA = waveHeightA;
                mWaveHeightB = waveHeightB;
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
        mWaveStopAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //改变曲线的偏移，达到波浪运动的效果
                mOffsetA += mWaveSpeedA;
                mOffsetB += mWaveSpeedB;
                invalidate();
            }
        });
    }

    /**
     * 波浪的函数，用于求y值
     * 正弦函数为A*sin(ω*(x + φ))+ k
     * • a - 振幅，影响的是波浪的浪高
     * •ω - 周期，影响的是两个浪头之间的距离
     * •φ - 偏移，改变这个参数来实现曲线的移动
     * • k - 高度，即水平线的高度，曲线在这个高度上下波动（实际上是进度）
     *
     * @param x          x轴
     * @param offset     φ偏移
     * @param waveHeight A振幅
     * @param waveCycle  ω周期
     * @return result
     */
    private double getWaveY(int x, int offset, int waveHeight, float waveCycle) {
        return Math.max(waveHeight, mMinWave) * Math.sin(waveCycle * (x + offset)) + (1 - mProgress / 100.0) * getHeight();
    }

    public int getWaveHeightA() {
        return mWaveHeightA;
    }

    private void setWaveHeightA(int waveHeightA) {
        this.mWaveHeightA = waveHeightA;
    }

    private int getWaveHeightB() {
        return mWaveHeightB;
    }

    private void setWaveHeightB(int waveHeightB) {
        this.mWaveHeightB = waveHeightB;
    }

    public int getProgress() {
        return mProgress;
    }

    private void setProgress(int progress) {
        this.mProgress = progress;
    }

    public int getWaveSpeedA() {
        return mWaveSpeedA;
    }

    public void setWaveSpeedA(int waveSpeedA) {
        this.mWaveSpeedA = waveSpeedA;
    }

    public int getWaveSpeedB() {
        return mWaveSpeedB;
    }

    public void setWaveSpeedB(int waveSpeedB) {
        this.mWaveSpeedB = waveSpeedB;
    }

    public float getWaveACycle() {
        return mWaveACycle;
    }

    public void setWaveACycle(float waveACycle) {
        this.mWaveACycle = waveACycle;
    }

    public float getWaveBCycle() {
        return mWaveBCycle;
    }

    public void setWaveBCycle(float waveBCycle) {
        this.mWaveBCycle = waveBCycle;
    }

    public int getOffsetA() {
        return mOffsetA;
    }

    public void setOffsetA(int offsetA) {
        this.mOffsetA = offsetA;
    }

    public int getOffsetB() {
        return mOffsetB;
    }

    public void setOffsetB(int offsetB) {
        this.mOffsetB = offsetB;
    }

    public int getBgColor() {
        return mBgColor;
    }

    public void setBgColor(int bgColor) {
        this.mBgColor = bgColor;
    }

    public int getArcColor() {
        return mArcColor;
    }

    public void setArcColor(int arcColor) {
        this.mArcColor = arcColor;
    }

    public int getWaveColor1() {
        return mWaveColor1;
    }

    public void setWaveColor1(int waveColor1) {
        this.mWaveColor1 = waveColor1;
    }

    public int getWaveColor2() {
        return mWaveColor2;
    }

    public void setWaveColor2(int waveColor2) {
        this.mWaveColor2 = waveColor2;
    }

    public int getTextColor() {
        return mTextColor;
    }

    public void setTextColor(int textColor) {
        this.mTextColor = textColor;
    }

    public float getTextSize() {
        return mTextSize;
    }

    public void setTextSize(float textSize) {
        this.mTextSize = textSize;
    }

    public int getArcWidth() {
        return mArcWidth;
    }

    public void setArcWidth(int arcWidth) {
        this.mArcWidth = arcWidth;
    }
}
