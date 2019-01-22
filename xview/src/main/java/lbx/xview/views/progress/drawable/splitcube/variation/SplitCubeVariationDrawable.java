package lbx.xview.views.progress.drawable.splitcube.variation;

import android.graphics.Canvas;
import android.support.annotation.NonNull;

import lbx.xview.views.progress.drawable.base.BaseProgressDrawable;
import lbx.xview.views.progress.drawable.splitcube.SplitCubeDrawable;

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
 * @date 2018/8/22.
 */

public class SplitCubeVariationDrawable extends SplitCubeDrawable {


    public SplitCubeVariationDrawable(int color, int alpha, int duration, int delay) {
        super(color, alpha, duration , delay);
        setCrevice(0);
        drawables = new BaseProgressDrawable[]{
                new SplitCubeItemVariationDrawable(color, alpha, duration, delay, 1),
                new SplitCubeItemVariationDrawable(color, alpha, duration, delay, 0),
                new SplitCubeItemVariationDrawable(color, alpha, duration, delay, 0),
                new SplitCubeItemVariationDrawable(color, alpha, duration, delay, 1)
        };
    }

    @Override
    public void onDraw(@NonNull Canvas canvas) {
        float percent = getPercent();
        int offset = getChildSize() / 2;
        //左上
        int save = canvas.save();
        canvas.translate(-offset * percent, -offset * percent);
        drawables[0].draw(canvas);
        canvas.restoreToCount(save);
        //右上
        int save1 = canvas.save();
        canvas.translate(offset * percent, -offset * percent);
        drawables[1].draw(canvas);
        canvas.restoreToCount(save1);
        //左下
        int save2 = canvas.save();
        canvas.translate(-offset * percent, offset * percent);
        drawables[2].draw(canvas);
        canvas.restoreToCount(save2);
        //右下
        int save3 = canvas.save();
        canvas.translate(offset * percent, offset * percent);
        drawables[3].draw(canvas);
        canvas.restoreToCount(save3);
    }
}
