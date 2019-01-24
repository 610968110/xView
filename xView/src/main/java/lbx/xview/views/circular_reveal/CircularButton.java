package lbx.xview.views.circular_reveal;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

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
 * @date 2019/1/23.
 */
public class CircularButton extends FrameLayout {

    private View mProgressBar;
    private View mButton;
    private @CircularButtonStyle
    int currentState;

    public CircularButton(@NonNull Context context) {
        this(context, null);
    }

    public CircularButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircularButton(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View view = LayoutInflater.from(context).inflate(R.layout.view_circular_button, null);
        addView(view);
        mProgressBar = view.findViewById(R.id.circular_button_progressBar);
        mButton = view.findViewById(R.id.circular_button_change);
        mButton.setClickable(false);
    }

    @IntDef({CircularButtonStyle.TYPE_BUTTON, CircularButtonStyle.TYPE_PROGRESS})
    public @interface CircularButtonStyle {
        int TYPE_BUTTON = 0x010;
        int TYPE_PROGRESS = 0x011;
    }

    public void change(@CircularButtonStyle int style) {
        switch (style) {
            case CircularButtonStyle.TYPE_BUTTON:
                mProgressBar.setVisibility(View.INVISIBLE);
                // 伸展按钮
                CircularAnim.show(mButton).go();
                break;
            case CircularButtonStyle.TYPE_PROGRESS:
                mProgressBar.setVisibility(View.VISIBLE);
                // 收缩按钮
                CircularAnim.hide(mButton).go();
                break;
            default:
                mProgressBar.setVisibility(View.INVISIBLE);
                // 伸展按钮
                CircularAnim.show(mButton).go();
                style = CircularButtonStyle.TYPE_BUTTON;
                break;
        }
        currentState = style;
    }

    public int getCurrentState() {
        return currentState;
    }

    public View getProgressBar() {
        return mProgressBar;
    }

    public View getButton() {
        return mButton;
    }
}
