package lbx.xview.views.circular_reveal;

import android.animation.Animator;
import android.view.View;
import android.view.ViewAnimationUtils;

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
public class ViewCircularRevealUtil {

    private static ViewCircularRevealUtil INSTANCE;

    static ViewCircularRevealUtil getInstance() {
        if (INSTANCE == null) {
            synchronized (ViewCircularRevealUtil.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ViewCircularRevealUtil();
                }
            }
        }
        return INSTANCE;
    }

    private ViewCircularRevealUtil() {
    }

    /**
     * 根据点击的view为中心进行CircularReveal动画来显示页面2
     *
     * @param clickView    点击的view
     * @param floatingView 显示的页面2
     */
    public void showFloatingViewByClickView(View clickView, View floatingView) {
        floatingView.setVisibility(View.VISIBLE);
        int centerX = (clickView.getLeft() + clickView.getRight()) / 2;
        int centerY = (clickView.getTop() + clickView.getBottom()) / 2;
        showFloatingViewByPosition(centerX, centerY, floatingView);
    }

    /**
     * 根据坐标为中心进行CircularReveal动画来显示页面2
     *
     * @param x            x
     * @param y            y
     * @param floatingView 显示的页面2
     */
    public void showFloatingViewByPosition(int x, int y, View floatingView) {
        float finalRadius = (float) Math.hypot(x, y);
        Animator mCircularReveal = ViewAnimationUtils.createCircularReveal(
                floatingView, x, y, 0, finalRadius);
        mCircularReveal.start();
    }


    public void goneFloatingViewByClickView(View clickView, View floatingView) {
        floatingView.setVisibility(View.VISIBLE);
        int centerX = (clickView.getLeft() + clickView.getRight()) / 2;
        int centerY = (clickView.getTop() + clickView.getBottom()) / 2;
        goneFloatingViewByClickPosition(centerX, centerY, floatingView);
    }

    public void goneFloatingViewByClickPosition(int x, int y, final View floatingView) {
        final float finalRadius = (float) Math.hypot((double) x, (double) y);
        final Animator mCircularReveal = ViewAnimationUtils.createCircularReveal(
                floatingView, x, y, finalRadius, 0);
        mCircularReveal.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                floatingView.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        mCircularReveal.start();
    }
}
