package lbx.xview.views.progress;

import android.graphics.Color;

import lbx.xview.views.progress.drawable.BoundDrawable;
import lbx.xview.views.progress.drawable.DoubleBoundsDrawable;
import lbx.xview.views.progress.drawable.RingDrawable;
import lbx.xview.views.progress.drawable.WaveDrawable;
import lbx.xview.views.progress.drawable.base.BaseProgressDrawable;
import lbx.xview.views.progress.drawable.cubes.WanderingCubesDrawable;
import lbx.xview.views.progress.drawable.pyramid.PyramidDrawable;
import lbx.xview.views.progress.drawable.splitcube.SplitCubeDrawable;
import lbx.xview.views.progress.drawable.splitcube.variation.SplitCubeVariationDrawable;
import lbx.xview.views.progress.drawable.variation.DoubleBoundsVariationDrawable;
import lbx.xview.views.progress.drawable.wheel.WheelDrawable;

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

public class XProgressStyleFactory {

    public static BaseProgressDrawable create(Style style) {
        return create(style, Color.RED, 255, 1000, 0);
    }

    public static BaseProgressDrawable create(Style style, int color, int alpha) {
        return create(style, color, alpha, 1000, 0);
    }

    public static BaseProgressDrawable create(Style style, int color, int alpha, int duration, int delay) {
        BaseProgressDrawable drawable = null;
        switch (style) {
            case BOUND:
                drawable = new BoundDrawable(color, alpha, duration, delay);
                break;
            case DOUBLE_BOUNDS:
                drawable = new DoubleBoundsDrawable(color, alpha, duration, delay);
                break;
            case WAVE:
                drawable = new WaveDrawable(color, alpha, duration, delay);
                break;
            case WANDERING_CUBES:
                drawable = new WanderingCubesDrawable(color, alpha, duration, delay);
                break;
            case PYRAMID_BOUNDS:
                drawable = new PyramidDrawable(color, alpha, duration, delay);
                break;
            case RING:
                drawable = new RingDrawable(color, alpha, duration, delay);
                break;
            case DOUBLE_BOUNDS_VARIATION:
                drawable = new DoubleBoundsVariationDrawable(color, alpha, duration, delay);
                break;
            case WHEEL:
                drawable = new WheelDrawable(color, alpha, duration, delay);
                break;
            case SPLIT_CUBE:
                drawable = new SplitCubeDrawable(color, alpha, duration, delay);
                break;
            case SPLIT_CUBE_VARIATION:
                drawable = new SplitCubeVariationDrawable(color, alpha, duration, delay);
                break;
            default:
                break;
        }
        return drawable;
    }
}
