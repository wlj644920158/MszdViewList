package nanjing.jun.viewlib.load;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by 王黎军 on 2017/7/31.
 */

public class MszdLoading_1_Arc {
    private float startDegree;
    private float sweepDegree;
    private int color;

    public float getStartDegree() {
        return startDegree;
    }

    public void setStartDegree(float startDegree) {
        this.startDegree = startDegree;
    }

    public float getSweepDegree() {
        return sweepDegree;
    }

    public void setSweepDegree(float sweepDegree) {
        this.sweepDegree = sweepDegree;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void myDraw(Canvas canvas, Paint paint, int R) {
        paint.setColor(color);
        canvas.drawArc(canvas.getWidth() / 2 - R, canvas.getHeight() / 2 - R, canvas.getWidth() / 2 + R, canvas.getHeight() / 2 + R,
                startDegree, sweepDegree, true, paint);
    }

}
