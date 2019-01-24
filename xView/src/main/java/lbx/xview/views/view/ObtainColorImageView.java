package lbx.xview.views.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.support.annotation.AttrRes;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import lbx.xview.R;
import lbx.xview.utils.DrawableUtil;

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
 * @date 2018/8/28.
 */

public class ObtainColorImageView extends android.support.v7.widget.AppCompatImageView {

    private int x;
    private int y;
    private Bitmap mThumbBitmap;
    private Point mPoint;
    private Bitmap bg;
    private int defaultColor = Color.WHITE;
    private
    @DrawableRes
    int thumb = R.drawable.yanse_anniu;

    public void setThumb(int thumb) {
        this.thumb = thumb;
        mThumbBitmap = BitmapFactory.decodeResource(getContext().getResources(), thumb);
    }

    public void setThumbBitmap(Bitmap thumbBitmap) {
        this.mThumbBitmap = thumbBitmap;
    }

    public ObtainColorImageView(@NonNull Context context) {
        this(context, null);
    }

    public ObtainColorImageView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ObtainColorImageView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPoint = new Point();
        mThumbBitmap = BitmapFactory.decodeResource(getContext().getResources(), thumb);
    }

    private void initDrawable() {
        Drawable background = getBackground();
        if (background == null) {
            background = getDrawable();
        }
        if (background != null) {
            bg = DrawableUtil.drawableToBitmap(background);
        }
    }

    @Override
    public void setBackgroundResource(@DrawableRes int resId) {
        super.setBackgroundResource(resId);
        initDrawable();
    }

    @Override
    public void setBackgroundDrawable(Drawable background) {
        super.setBackgroundDrawable(background);
        initDrawable();
    }

    @Override
    public void setImageBitmap(Bitmap bm) {
        super.setImageBitmap(bm);
        initDrawable();
    }

    @Override
    public void setImageDrawable(@Nullable Drawable drawable) {
        super.setImageDrawable(drawable);
        initDrawable();
    }

    @Override
    public void setImageResource(@DrawableRes int resId) {
        super.setImageResource(resId);
        initDrawable();
    }

    @Override
    public void setBackgroundColor(@ColorInt int color) {
        super.setBackgroundColor(color);
        defaultColor = color;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        x = w / 2;
        y = h / 2;
        mPoint.x = x;
        mPoint.y = y;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mThumbBitmap != null) {
            canvas.drawBitmap(
                    mThumbBitmap,
                    x - mThumbBitmap.getWidth() / 2,
                    y - mThumbBitmap.getHeight() / 2,
                    null);
        }
    }

    public void selectPoint(int x, int y) {
        this.x = x;
        this.y = y;
        invalidate();
    }

    public Point getCenterPoint() {
        return mPoint;
    }

    public int getColor(int x, int y) {
        return bg != null ? bg.getPixel(x, y) : defaultColor;
    }


}
