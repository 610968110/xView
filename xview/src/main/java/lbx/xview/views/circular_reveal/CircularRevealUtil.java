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
 * @date 2019/1/22
 */
public class CircularRevealUtil {

    private static CircularRevealUtil INSTANCE;

    public static CircularRevealUtil getInstance() {
        if (INSTANCE == null) {
            synchronized (CircularRevealUtil.class) {
                if (INSTANCE == null) {
                    INSTANCE = new CircularRevealUtil();
                }
            }
        }
        return INSTANCE;
    }

    private CircularRevealUtil() {
    }

    public Intent makeCircularRevealIntent(Context context,
                                           Class<? extends ICircularReveal> clazz,
                                           int centerX, int centerY) {
        Intent intent = new Intent(context, clazz);
        intent.putExtra(Circular.CENTER_X, centerX);
        intent.putExtra(Circular.CENTER_Y, centerY);
        return intent;
    }

    public void setCircularRevealAnim(ICircularReveal reveal, boolean reversed) {
        Intent intent = reveal.getActivity().getIntent();
        int x = intent.getIntExtra(Circular.CENTER_X, 0);
        int y = intent.getIntExtra(Circular.CENTER_Y, 0);
        setCircularRevealAnim(reveal, x, y, reversed);
    }

    public void setCircularRevealAnim(ICircularReveal reveal, int centerX, int centerY, boolean reversed) {
        reveal.getRootView().post(() -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Animator animator = createRevealAnimator(reveal.getActivity(), reveal.getRootView(), centerX, centerY, reversed);
                animator.start();
            }
        });
    }

    private Animator createRevealAnimator(Activity activity, View rootView, int x, int y, boolean reversed) {
        float hypot = (float) Math.hypot(rootView.getHeight(), rootView.getWidth());
        float startRadius = reversed ? hypot : 0;
        float endRadius = reversed ? 0 : hypot;

        Animator animator = ViewAnimationUtils.createCircularReveal(
                rootView, x, y,
                startRadius,
                endRadius);
        animator.setDuration(800);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        if (reversed) {
            animator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    rootView.setVisibility(View.INVISIBLE);
                    activity.finish();
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
        }
        return animator;
    }

    public void onBackPressed(ICircularReveal reveal, int x, int y) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Animator animator = createRevealAnimator(reveal.getActivity(), reveal.getRootView(), x, y, true);
            animator.start();
        } else {
            reveal.getActivity().finish();
        }
    }
}
