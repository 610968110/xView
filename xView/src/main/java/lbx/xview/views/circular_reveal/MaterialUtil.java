package lbx.xview.views.circular_reveal;

import android.animation.Animator;
import android.content.Context;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

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
public class MaterialUtil {

    private static MaterialUtil INSTANCE;
    private Animation alphaAppear, alphaDisappear;
    private float pixelDensity;

    public static MaterialUtil getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (MaterialUtil.class) {
                if (INSTANCE == null) {
                    INSTANCE = new MaterialUtil(context);
                }
            }
        }
        return INSTANCE;
    }

    private MaterialUtil(Context context) {
        alphaAppear = AnimationUtils.loadAnimation(context, R.anim.alpha_anim);
        alphaDisappear = AnimationUtils.loadAnimation(context, R.anim.alpha_disappear);
        pixelDensity = context.getResources().getDisplayMetrics().density;

    }

    public void launchTwitter(final IMaterial iMaterial) {
        View bottomView = iMaterial.getBottomView();
        View topView = iMaterial.getTopView();
        View floatingButton = iMaterial.getFloatingButton();
        View[] topChildView = iMaterial.getTopChildView();
        for (View view : topChildView) {
            if (view != null) {
                view.setVisibility(View.INVISIBLE);
            }
        }
        int width = bottomView.getWidth();
        int height = bottomView.getHeight();
        int x = width / 2;
        int y = height / 2;
        int hypotenuse = (int) Math.hypot(x, y);

        x = (int) (x - ((16 * pixelDensity) + (28 * pixelDensity)));

        ViewGroup.LayoutParams parameters = topView.getLayoutParams();
        parameters.height = bottomView.getHeight();
        topView.setLayoutParams(parameters);
        floatingButton.animate()
                .translationX(-x)
                .translationY(-y)
                .setDuration(200)
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        Animator anim = ViewAnimationUtils.createCircularReveal(
                                topView, width / 2, height / 2,
                                28 * pixelDensity, hypotenuse);
                        anim.setDuration(350);
                        anim.addListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animator) {

                            }

                            @Override
                            public void onAnimationEnd(Animator animator) {
                                for (View view : topChildView) {
                                    if (view != null) {
                                        view.setVisibility(View.VISIBLE);
                                        view.startAnimation(alphaAppear);
                                    }
                                }
                            }

                            @Override
                            public void onAnimationCancel(Animator animator) {

                            }

                            @Override
                            public void onAnimationRepeat(Animator animator) {

                            }
                        });
                        floatingButton.setVisibility(View.INVISIBLE);
                        topView.setVisibility(View.VISIBLE);
                        anim.start();
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                });
    }

    public void closeTwitter(final IMaterial iMaterial) {
        View bottomView = iMaterial.getBottomView();
        View topView = iMaterial.getTopView();
        View floatingButton = iMaterial.getFloatingButton();
        View[] topChildView = iMaterial.getTopChildView();
        int width = bottomView.getWidth();
        int height = bottomView.getHeight();
        int hypotenuse = (int) Math.hypot(width / 2, height / 2);
        alphaDisappear.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Animator anim = ViewAnimationUtils.createCircularReveal(
                        topView, width / 2, height / 2,
                        hypotenuse, 28 * pixelDensity);
                anim.setDuration(350);
                anim.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        topView.setVisibility(View.INVISIBLE);
                        floatingButton.setVisibility(View.VISIBLE);
                        floatingButton.animate()
                                .translationX(0f)
                                .translationY(0f)
                                .setDuration(200)
                                .setListener(null);
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                });
                anim.start();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        for (View view : topChildView) {
            if (view != null) {
                view.setVisibility(View.INVISIBLE);
                view.startAnimation(alphaDisappear);
            }
        }
    }
}
