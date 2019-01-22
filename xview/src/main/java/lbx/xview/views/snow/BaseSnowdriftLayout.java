package lbx.xview.views.snow;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.FloatRange;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.Random;

import lbx.xview.R;

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
 * @date 2018/7/12.
 */

public abstract class BaseSnowdriftLayout extends RelativeLayout {

    private Drawable[] mDrawables;
    private Interpolator[] mInterpolators;
    private Random mRandom = new Random();
    private RectF mRectF;
    private int mSize;
    private int mDuration;
    private float mAlphaTo = 255F;
    private float mAlphaFrom = 255F;

    public BaseSnowdriftLayout(Context context) {
        this(context, null);
    }

    public BaseSnowdriftLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseSnowdriftLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.xSnowdrift);
        mSize = (int) a.getDimension(R.styleable.xSnowdrift_xSnowdriftImageSize, 80);
        mDuration = a.getInt(R.styleable.xSnowdrift_xSnowdriftDuration, 2000);
        a.recycle();
        init();
    }

    private void init() {
        mInterpolators = makeDefaultInterpolator();
        mDrawables = new Drawable[0];
        mRectF = new RectF();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mRectF.set(0, 0, w, h);
    }

    public void setResources(@DrawableRes int... resources) {
        Drawable[] drawables = new Drawable[resources.length];
        Resources resource = getContext().getResources();
        for (int i = 0; i < resources.length; i++) {
            drawables[i] = resource.getDrawable(resources[i], null);
        }
        setDrawables(drawables);
    }

    public void setBitmaps(Bitmap... bitmaps) {
        Drawable[] drawables = new Drawable[bitmaps.length];
        Resources resource = getContext().getResources();
        for (int i = 0; i < bitmaps.length; i++) {
            drawables[i] = new BitmapDrawable(resource, bitmaps[i]);
        }
        setDrawables(drawables);
    }

    public void setDrawables(Drawable... drawables) {
        this.mDrawables = drawables;
        praise(drawables);
    }

    /**
     * 进场动画，三种同时播放
     * alpha透明度 （80%-0%）
     * scaleX 宽度 target（20%-100%）
     * scaleY 高度
     *
     * @param view view
     * @return return
     */
    private AnimatorSet getAnimator(final View view) {
        ObjectAnimator alpha = ObjectAnimator.ofFloat(view, View.ALPHA, mAlphaFrom / 255F, mAlphaTo / 255F);
        AnimatorSet set = new AnimatorSet();
        set.setTarget(view);
        set.setInterpolator(new LinearInterpolator());
        set.setDuration(0).playTogether(alpha);
        set.setDuration(mDuration);
        return set;
    }

    public void praise() {
        praise(mDrawables);
    }

    private void praise(Drawable[] drawables) {
        if (mRectF.width() == 0 || mRectF.height() == 0) {
            return;
        }
        final ImageView imageView = makeChild();
        int anInt = mRandom.nextInt(drawables.length);
        imageView.setImageDrawable(drawables[anInt]);
        LayoutParams params = new LayoutParams(mSize, mSize);
        params.addRule(ALIGN_PARENT_END, TRUE);
        params.addRule(ALIGN_PARENT_BOTTOM, TRUE);
        addView(imageView, params);
        AnimatorSet animatorSet = new AnimatorSet();
        //贝塞尔曲线路径动画
        ValueAnimator bezierValueAnimator = getBezierValueAnimator(imageView);
        AnimatorSet alphaAnimator = getAnimator(imageView);
        //动画将一个接一个地启动
        animatorSet.playTogether(bezierValueAnimator, alphaAnimator);
        animatorSet.setInterpolator(mInterpolators[mRandom.nextInt(mInterpolators.length)]);
        animatorSet.setTarget(imageView);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                onAccumulationDown(imageView);
            }
        });
        animatorSet.start();
    }

    /**
     * 初始化贝塞尔估值器
     *
     * @param target view
     * @return return
     */
    public ValueAnimator getBezierValueAnimator(final View target) {
        //随机产生两个点，以确定一条3阶贝塞尔曲线
        BezierEvaluator evaluator = new BezierEvaluator(getPointF(2), getPointF(1));
        ValueAnimator animator = ValueAnimator.ofObject(evaluator, startPoint(mRectF, mSize), endPoint(mRectF, mSize));
        animator.setTarget(target);
        animator.addUpdateListener(valueAnimator -> {
            // 这里获取到贝塞尔曲线计算出来的的x y值 赋值给view 这样就能让爱心随着曲线走啦
            PointF pointF = (PointF) valueAnimator.getAnimatedValue();
            target.setX(pointF.x);
            target.setY(pointF.y);
        });

        animator.setDuration(mDuration);
        return animator;
    }

    public abstract PointF startPoint(RectF rectF, int imgSize);

    public abstract PointF endPoint(RectF rectF, int imgSize);

    public abstract ImageView makeChild();

    public abstract void onAccumulationDown(ImageView imageView);

    private PointF getPointF(int i) {
        PointF pointF = new PointF();
        //pointF.x,pointF.y都是随机，因此可以产生n多种轨迹 0~mWidth
        int width = (int) mRectF.width();
        int height = (int) mRectF.height();
        pointF.x = mRandom.nextInt(width);
        //为了美观,建议尽量保证P2在P1上面,
        //只需要将该布局的高度分为上下两部分,让p1只能在下面部分范围内变化(1/2height~height),让p2只能在上面部分范围内变化(0~1/2height),因为坐标系是倒着的;
        //0~loveLayout.Height/2
        if (i == 1) {
            //P1点Y轴坐标变化
            pointF.y = mRandom.nextInt(height / 2) + height / 2;
        } else if (i == 2) {
            //P2点Y轴坐标变化
            pointF.y = mRandom.nextInt(height / 2);
        }
        return pointF;
    }

    private Interpolator[] makeDefaultInterpolator() {
        return new Interpolator[]{new LinearInterpolator()};
    }

    /**
     * 设置插值器
     *
     * @param interpolators interpolators
     */
    public void setInterpolators(Interpolator... interpolators) {
        if (interpolators == null || interpolators.length == 0) {
            interpolators = makeDefaultInterpolator();
        }
        this.mInterpolators = interpolators;
    }

    public void setImageSize(int size) {
        this.mSize = size;
    }

    public void setDuration(int duration) {
        this.mDuration = duration;
    }

    public Random getRandom() {
        return mRandom;
    }

    public void setAlphaTo(@FloatRange(from = 0F, to = 255F) float alphaTo) {
        this.mAlphaTo = alphaTo;
    }

    public void setAlphaFrom(@FloatRange(from = 0F, to = 255F) float alphaFrom) {
        this.mAlphaFrom = alphaFrom;
    }

}
