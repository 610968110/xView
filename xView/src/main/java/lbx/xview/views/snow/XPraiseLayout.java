package lbx.xview.views.snow;

import android.content.Context;
import android.graphics.PointF;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

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
 * @date 2018/7/12.
 */

public class XPraiseLayout extends BaseSnowdriftLayout {

    public XPraiseLayout(Context context) {
        super(context);
    }

    public XPraiseLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public XPraiseLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public PointF startPoint(RectF rectF, int imgSize) {
        return new PointF(rectF.width() - imgSize, rectF.height() - imgSize);
    }

    @Override
    public PointF endPoint(RectF rectF, int imgSize) {
        return new PointF(getRandom().nextInt((int) rectF.width()), -imgSize);
    }

    @Override
    public ImageView makeChild() {
        return new ImageView(getContext());
    }

    @Override
    public void onAccumulationDown(ImageView imageView) {
        removeView(imageView);
    }
}
