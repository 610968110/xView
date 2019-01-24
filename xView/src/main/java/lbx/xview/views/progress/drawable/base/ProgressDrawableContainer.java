package lbx.xview.views.progress.drawable.base;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;

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
 * @date 2018/7/11.
 */

public abstract class ProgressDrawableContainer extends BaseProgressDrawable {

    private BaseProgressDrawable[] drawables;

    private void initCallBack() {
        if (drawables != null) {
            for (BaseProgressDrawable drawable : drawables) {
                if (drawable != null) {
                    drawable.setCallback(this);
                }
            }
        }
    }

    public void onChildCreated(BaseProgressDrawable... drawables) {

    }

    public int getChildCount() {
        return drawables == null ? 0 : drawables.length;
    }

    public BaseProgressDrawable getChildAt(int index) {
        return drawables == null ? null : drawables[index];
    }

    @Override
    public void onDraw(@NonNull Canvas canvas) {
        if (drawables != null) {
            for (BaseProgressDrawable d : drawables) {
                if (d != null) {
                    int count = canvas.save();
                    d.draw(canvas);
                    canvas.restoreToCount(count);
                }
            }
        }
    }

    @CallSuper
    @Override
    protected void onBoundsChange(Rect bounds) {
        getChildDrawables();
        if (drawables == null || bounds == null) {
            return;
        }
        for (BaseProgressDrawable drawable : drawables) {
            if (drawable != null) {
                drawable.setBounds(bounds);
            }
        }
    }

    private void getChildDrawables() {
        if (drawables == null) {
            drawables = onCreateChild();
            initCallBack();
            onChildCreated(drawables);
        }
    }

    @Override
    public void start() {
        super.start();
        AnimationUtils.start(drawables);
    }

    @Override
    public void stop() {
        super.stop();
        AnimationUtils.stop(drawables);
    }

    @Override
    public boolean isRunning() {
        return AnimationUtils.isRunning(drawables) || super.isRunning();
    }

    public abstract BaseProgressDrawable[] onCreateChild();

}
