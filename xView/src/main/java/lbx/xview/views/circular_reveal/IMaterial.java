package lbx.xview.views.circular_reveal;

import android.view.View;

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
public interface IMaterial {

    /**
     * @return 动画控制的button
     */
    View getFloatingButton();

    /**
     * @return 动画后显示页面的根布局
     */
    View getTopView();

    /**
     * @return 动画后显示页面的根布局中的所有子view，动画前会隐藏这些view，动画后会给这些子view显示出来
     */
    View[] getTopChildView();

    /**
     * @return 动画前显示页面的根布局
     */
    View getBottomView();
}
