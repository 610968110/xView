package lbx.xview.views.snow;

import android.animation.TypeEvaluator;
import android.graphics.PointF;

/**
 * @author lbx
 */
public class BezierEvaluator implements TypeEvaluator<PointF> {

    private PointF mControlP1;
    private PointF mControlP2;

    BezierEvaluator(PointF controlP1, PointF controlP2) {
        this.mControlP1 = controlP1;
        this.mControlP2 = controlP2;
    }

    @Override
    public PointF evaluate(float time, PointF start, PointF end) {

        float timeLeft = 1.0f - time;
        PointF point = new PointF();

        point.x = timeLeft * timeLeft * timeLeft * (start.x) + 3 * timeLeft * timeLeft * time *
                (mControlP1.x) + 3 * timeLeft * time *
                time * (mControlP2.x) + time * time * time * (end.x);

        point.y = timeLeft * timeLeft * timeLeft * (start.y) + 3 * timeLeft * timeLeft * time *
                (mControlP1.y) + 3 * timeLeft * time *
                time * (mControlP2.y) + time * time * time * (end.y);
        return point;
    }
}