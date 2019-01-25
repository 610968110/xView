package lbx.xview.views.snow;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.PointF;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import lbx.xview.R;
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
 * @date 2018/7/12.
 */

public class XSnowLayout extends BaseSnowdriftLayout {

    private ScheduledExecutorService mService;
    private int mSpeed;
    /**
     * 堆积量
     */
    private int mAccumulation;
    private int mBottomViewCount;

    public XSnowLayout(Context context) {
        this(context, null);
    }

    public XSnowLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XSnowLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.xSnowdrift);
        mSpeed = a.getInt(R.styleable.xSnowdrift_xSnowdriftSpeed, 1000);
        mAccumulation = a.getInt(R.styleable.xSnowdrift_xSnowdriftAccumulation, 0);
        a.recycle();
    }

    @Override
    public PointF startPoint(RectF rectF, int imgSize) {
        float x = rectF.width() - imgSize;
        return new PointF(getRandom().nextInt((int) x), -imgSize);
    }

    @Override
    public PointF endPoint(RectF rectF, int imgSize) {
        float bottom = mAccumulation != 0 || mBottomViewCount > mAccumulation ? rectF.bottom - imgSize : rectF.bottom;
        return new PointF(getRandom().nextInt((int) rectF.width()), bottom);
    }

    @Override
    public ImageView makeChild() {
        ImageView imageView;
        if (mBottomViewCount < mAccumulation) {
            mBottomViewCount++;
            imageView = new AccumulationView(getContext());
        } else {
            imageView = new ImageView(getContext());
        }
        return imageView;
    }

    @Override
    public void onAccumulationDown(ImageView imageView) {
        if (!(imageView instanceof AccumulationView)) {
            //删除
            removeView(imageView);
        }
    }

    public void startAnimation() {
        startAnimation(0);
    }

    public void startAnimation(int de) {
        if (mService == null) {
            mService = new ScheduledThreadPoolExecutor(1);
            if (mSpeed <= 0) {
                mSpeed = 10;
            }
            mService.scheduleAtFixedRate(new Runnable() {
                @Override
                public void run() {
                    AnimationUtils.runOnUI(new Runnable() {
                        @Override
                        public void run() {
                            praise();
                        }
                    });
                }
            }, de, mSpeed, TimeUnit.MILLISECONDS);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        cancelAnimation();
    }

    public void cancelAnimation() {
        if (mService != null && !mService.isTerminated()) {
            mService.shutdown();
            mService = null;
        }
    }

    public void setSpeed(int speed) {
        if (this.mSpeed != speed) {
            this.mSpeed = speed;
            cancelAnimation();
            startAnimation(speed);
        }
    }

    public void setAccumulation(int accumulation) {
        removeAccumulationView();
        this.mAccumulation = accumulation;
    }


    private void clearBottomViewCount() {
        this.mBottomViewCount = 0;
    }

    /**
     * 移除堆积的view
     */
    private void removeAccumulationView() {
        List<View> list = new ArrayList<>();
        for (int i = 0; i < getChildCount(); i++) {
            ImageView view = (ImageView) getChildAt(i);
            if (view instanceof AccumulationView) {
                list.add(view);
            }
        }
        for (View v : list) {
            removeView(v);
        }
        clearBottomViewCount();
    }
}
