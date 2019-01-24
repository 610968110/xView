package lbx.xview.views.progress;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ProgressBar;

import lbx.xview.R;
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
 * @date 2018/7/10.
 */

public class XProgressBar extends ProgressBar {

    private BaseProgressDrawable mDrawable;

    public XProgressBar(Context context) {
        super(context);
        init(context, null);
    }

    public XProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public XProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, R.style.XProgress);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.xProgress);
        setIndeterminate(true);

        int delay = a.getInteger(R.styleable.xProgress_xProgressDelay, 0);
        int duration = a.getInteger(R.styleable.xProgress_xProgressDuration, 1000);
        int alpha = a.getInteger(R.styleable.xProgress_xProgressAlpha, 255);
        int color = a.getColor(R.styleable.xProgress_xProgressColor, Color.LTGRAY);
        int style = a.getInt(R.styleable.xProgress_xProgressStyle, 0);

        BaseProgressDrawable drawable = XProgressStyleFactory.create(Style.values()[style], color, alpha, duration, delay);

        setProgressDrawable(drawable);

        a.recycle();
    }

    public void setProgressDrawable(BaseProgressDrawable drawable) {
        mDrawable = drawable;
        super.setIndeterminateDrawable(drawable);
        invalidate();
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        if (hasWindowFocus && mDrawable != null) {
            mDrawable.start();
        }
    }

    @Override
    public void onScreenStateChanged(int screenState) {
        if (screenState == View.SCREEN_STATE_OFF) {
            mDrawable.stop();
        }
    }

    @Override
    protected void onVisibilityChanged(@NonNull View changedView, int visibility) {
        if (visibility == VISIBLE) {
            mDrawable.start();
        } else {
            mDrawable.stop();
        }
    }
}
