package lbx.xview.views.circular_reveal;

import android.animation.Animator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateDecelerateInterpolator;

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
 * @date 2019/1/23
 */
public class ActivityCircularRevealUtil {

    private static ActivityCircularRevealUtil INSTANCE;

    static ActivityCircularRevealUtil getInstance() {
        if (INSTANCE == null) {
            synchronized (ActivityCircularRevealUtil.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ActivityCircularRevealUtil();
                }
            }
        }
        return INSTANCE;
    }

    private ActivityCircularRevealUtil() {
    }

    /**
     * 跳转到进行CircularReveal的页面，一般用在主动跳转页面中
     *
     * @param context context
     * @param clazz   Activity.class
     * @param centerX 圆心x坐标
     * @param centerY 圆心y坐标
     * @return Intent
     */
    public Intent makeCircularRevealIntent(Context context,
                                           Class<? extends ICircularReveal> clazz,
                                           int centerX, int centerY) {
        Intent intent = new Intent(context, clazz);
        intent.putExtra(Circular.CENTER_X, centerX);
        intent.putExtra(Circular.CENTER_Y, centerY);
        return intent;
    }

    /**
     * 对rootView开始CircularReveal动画，一般用在被跳转页面
     *
     * @param reveal   ICircularReveal
     * @param reversed 反转动画
     */
    public void setCircularRevealAnim(ICircularReveal reveal, boolean reversed) {
        Intent intent = reveal.getActivity().getIntent();
        int x = intent.getIntExtra(Circular.CENTER_X, 0);
        int y = intent.getIntExtra(Circular.CENTER_Y, 0);
        setCircularRevealAnim(reveal, x, y, reversed);
    }

    /**
     * 对rootView开始CircularReveal动画，一般用在被跳转页面
     *
     * @param reveal   ICircularReveal
     * @param centerX  圆心x坐标
     * @param centerY  圆心y坐标
     * @param reversed 反转动画
     */
    public void setCircularRevealAnim(ICircularReveal reveal, int centerX,
                                      int centerY, boolean reversed) {
        setCircularRevealAnim(reveal, centerX, centerY, reversed, null);
    }

    /**
     * 对rootView开始CircularReveal动画，一般用在被跳转页面
     *
     * @param reveal   ICircularReveal
     * @param centerX  圆心x坐标
     * @param centerY  圆心y坐标
     * @param reversed 反转动画
     * @param listener listener
     */
    public void setCircularRevealAnim(ICircularReveal reveal, int centerX,
                                      int centerY, boolean reversed, Animator.AnimatorListener listener) {
        reveal.getRootView().post(() -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Animator animator = createRevealAnimator(reveal.getActivity(),
                        reveal.getRootView(), centerX, centerY, reversed, listener);
                animator.start();
            }
        });
    }

    /**
     * 生成CircularReveal动画
     *
     * @param activity activity
     * @param rootView xml中根部布局的view
     * @param x        圆心x坐标
     * @param y        圆心y坐标
     * @param reversed ICircularReveal
     * @return Animator
     */
    public Animator createRevealAnimator(Activity activity, View rootView, int x, int y, boolean reversed) {
        return createRevealAnimator(activity, rootView, x, y, reversed, null);
    }

    /**
     * @param activity activity activity
     * @param rootView rootView xml中根部布局的view
     * @param x        x        圆心x坐标
     * @param y        y        圆心y坐标
     * @param reversed reversed ICircularReveal
     * @param listener listener
     * @return Animator
     */
    public Animator createRevealAnimator(Activity activity, View rootView,
                                         int x, int y, boolean reversed, Animator.AnimatorListener listener) {
        float hypot = (float) Math.hypot(rootView.getHeight(), rootView.getWidth());
        float startRadius = reversed ? hypot : 0;
        float endRadius = reversed ? 0 : hypot;

        Animator animator = ViewAnimationUtils.createCircularReveal(
                rootView, x, y,
                startRadius,
                endRadius);
        animator.setDuration(800);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        if (reversed && listener == null) {
            animator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    rootView.setVisibility(View.INVISIBLE);
                    activity.finish();
                    activity.overridePendingTransition(0, 0);
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
        } else {
            animator.addListener(listener);
        }
        return animator;
    }

    /**
     * 点击返回键后的CircularReveal动画效果
     *
     * @param reveal ICircularReveal
     * @param x      圆心x坐标
     * @param y      圆心y坐标
     */
    public void onBackPressed(ICircularReveal reveal, int x, int y) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Animator animator = createRevealAnimator(reveal.getActivity(), reveal.getRootView(), x, y, true);
            animator.start();
        } else {
            reveal.getActivity().finish();
        }
    }
}