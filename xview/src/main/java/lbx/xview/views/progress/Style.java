package lbx.xview.views.progress;

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

public enum Style {
    BOUND(0),
    DOUBLE_BOUNDS(1),
    WAVE(2),
    WANDERING_CUBES(3),
    PYRAMID_BOUNDS(4),
    RING(5),
    DOUBLE_BOUNDS_VARIATION(6),
    WHEEL(7),
    SPLIT_CUBE(8),
    SPLIT_CUBE_VARIATION(9);

    private int mStyle;

    Style(int style) {
        this.mStyle = style;
    }
}
