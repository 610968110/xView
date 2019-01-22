package lbx.xview.views.progress.drawable.pyramid;

import android.graphics.Point;

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
 * @date 2018/8/20.
 */

class PyramidBoundDrawable7 extends BasePyramidBoundDrawable {

    private int[] offset;
    private int startPoint = 7;
    private int endPoint = 2;

    PyramidBoundDrawable7(Point[] points, int pos, int duration, int delay, int color, int alpha) {
        super(points, pos, duration, delay, color, alpha);
        offset = new int[2];
    }

    @Override
    public void animatorUpdate(float percent, int count) {
        int[] point = point2Point(startPoint, endPoint, offset);
        float dx = point[0] * percent;
        float dy = point[1] * percent;
        mCenterX = (int) (this.point.x + dx);
        mCenterY = (int) (this.point.y + dy);
    }

    @Override
    public void animationRepeat(int count) {
        switch (count) {
            case 1:
                //From mPoints[0], To mPoints[1]
                startPoint = 2;
                endPoint = 5;
                break;
            case 2:
                startPoint = 5;
                endPoint = 6;
                break;
            case 3:
                startPoint = 6;
                endPoint = 1;
                break;
            case 4:
                startPoint = 1;
                endPoint = 0;
                break;
            case 5:
                startPoint = 0;
                endPoint = 7;
                break;
            case 6:
                startPoint = 7;
                endPoint = 2;
                break;
            default:
                reset();
                break;
        }
        setPoint(startPoint);
    }
}
