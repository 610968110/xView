package lbx.xview.views.flood;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import lbx.xview.utils.BitmapUtil;

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
 * @date 2018/7/13.
 */

public class XFloodLayout extends RelativeLayout {

    private View mBackgroundLayout;
    private View mCenterLayout;
    private View mForegroundLayout;
    private ImageView topImg;
    private ImageView bottomImg;
    private int line = 10;
    private boolean isFlood;
    private boolean isAniming;

    public XFloodLayout(@NonNull Context context) {
        this(context, null);
    }

    public XFloodLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XFloodLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

    }

    public void flood(int duration) {
        flood(duration, new LinearInterpolator());
    }

    public void flood(int duration, Interpolator interpolator) {
        if (isAniming) {
            return;
        }
        Bitmap bitmap = BitmapUtil.cut(mForegroundLayout);
        if (bitmap != null) {
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            topImg.setImageBitmap(Bitmap.createBitmap(bitmap, 0, 0, width, height / 2));
            bottomImg.setImageBitmap(Bitmap.createBitmap(bitmap, 0, height / 2, width, height / 2));
            bitmap.recycle();
            mCenterLayout.setVisibility(VISIBLE);
            mForegroundLayout.setVisibility(GONE);
            animStart(duration, interpolator);
        }
    }

    private void animStart(int duration, Interpolator interpolator) {
        ValueAnimator animator = ValueAnimator.ofFloat(0, 100);
        animator.setInterpolator(interpolator);
        animator.setDuration(duration);
        animator.addUpdateListener(animation -> {
            float value = (float) animation.getAnimatedValue();
            float move = topImg.getMeasuredHeight() * value / 100;
            topImg.setTranslationY(-move);
            bottomImg.setTranslationY(move);
            isAniming = true;
            if (onFloodUpdateListener != null) {
                onFloodUpdateListener.onUpdate(value, true);
            }
        });
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                if (onFloodUpdateListener != null) {
                    onFloodUpdateListener.onStart();
                }
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                isFlood = true;
                mCenterLayout.setVisibility(GONE);
                mForegroundLayout.setVisibility(GONE);
                isAniming = false;
                if (onFloodUpdateListener != null) {
                    onFloodUpdateListener.onFinish();
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.start();
    }

    public void unFlood(int duration) {
        unFlood(duration, new LinearInterpolator());
    }

    public void unFlood(int duration, Interpolator interpolator) {
        if (isAniming) {
            return;
        }
        mCenterLayout.setVisibility(VISIBLE);
        ValueAnimator animator = ValueAnimator.ofFloat(0, 100);
        animator.setInterpolator(interpolator);
        animator.setDuration(duration);
        animator.addUpdateListener(animation -> {
            float value = (float) animation.getAnimatedValue();
            float move = topImg.getMeasuredHeight() * (1 - value / 100);
            topImg.setTranslationY(-move);
            bottomImg.setTranslationY(move);
            isAniming = true;
            if (onFloodUpdateListener != null) {
                onFloodUpdateListener.onUpdate(value, false);
            }
        });
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                if (onFloodUpdateListener != null) {
                    onFloodUpdateListener.onStart();
                }
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                isFlood = false;
                isAniming = false;
                mCenterLayout.setVisibility(GONE);
                mForegroundLayout.setVisibility(VISIBLE);
                if (onFloodUpdateListener != null) {
                    onFloodUpdateListener.onFinish();
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.start();
    }

    public void init(View foregroundLayout, View backgroundLayout) {
        if (getChildCount() != 0) {
            removeAllViews();
        }
        this.mForegroundLayout = foregroundLayout;
        this.mCenterLayout = makeCenter();
        this.mBackgroundLayout = backgroundLayout;
        addView(backgroundLayout, getParams());
        addView(this.mCenterLayout, getParams());
        addView(foregroundLayout, getParams());
    }

    @NonNull
    private ViewGroup.LayoutParams getParams() {
        return new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }

    private View makeCenter() {
        LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        topImg = new ImageView(getContext());
        bottomImg = new ImageView(getContext());
        LinearLayout.LayoutParams topP = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
        topP.weight = 1;
        linearLayout.addView(topImg, topP);
        LinearLayout.LayoutParams centerP = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
        topP.height = line;
        linearLayout.addView(new View(getContext()), centerP);
        LinearLayout.LayoutParams bottomP = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
        bottomP.weight = 1;
        linearLayout.addView(bottomImg, bottomP);
        return linearLayout;
    }

    private OnFloodUpdateListener onFloodUpdateListener;

    public interface OnFloodUpdateListener {
        void onStart();

        void onUpdate(float percent, boolean isFlood);

        void onFinish();
    }


    public View getForegroundLayout() {
        return mForegroundLayout;
    }

    public View getBackgroundLayout() {
        return mBackgroundLayout;
    }

    public boolean isFlood() {
        return isFlood;
    }

    public void setOnFloodUpdateListener(OnFloodUpdateListener onFloodUpdateListener) {
        this.onFloodUpdateListener = onFloodUpdateListener;
    }
}
