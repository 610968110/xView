package lbx.xview.utils;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.os.Handler;
import android.os.Looper;

import lbx.xview.views.progress.drawable.base.BaseProgressDrawable;

/**
 * Created by ybq.
 */
public class AnimationUtils {

    private static Handler mHandler = new Handler(Looper.getMainLooper());

    public static void start(Animator animator) {
        if (animator != null && !animator.isStarted()) {
            animator.start();
        }
    }

    public static void stop(Animator animator) {
        if (animator != null && !animator.isRunning()) {
            animator.end();
        }
    }

    public static boolean isRunning(ValueAnimator animator) {
        return animator != null && animator.isRunning();
    }

    public static boolean isStarted(ValueAnimator animator) {
        return animator != null && animator.isStarted();
    }


    public static void start(BaseProgressDrawable... drawables) {
        if (drawables == null) {
            return;
        }
        for (BaseProgressDrawable drawable : drawables) {
            if (drawable != null) {
                drawable.start();
            }
        }
    }

    public static void stop(BaseProgressDrawable... drawables) {
        if (drawables == null) {
            return;
        }
        for (BaseProgressDrawable drawable : drawables) {
            if (drawable != null) {
                drawable.stop();
            }
        }
    }

    public static boolean isRunning(BaseProgressDrawable... drawables) {
        for (BaseProgressDrawable drawable : drawables) {
            if (drawable != null) {
                if (drawable.isRunning()) {
                    return true;
                }
            }
        }
        return false;
    }

    public static Handler getMainHandler() {
        return mHandler;
    }

    public static void runOnUI(Runnable runnable) {
        mHandler.post(runnable);
    }
}
