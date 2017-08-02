package nanjing.jun.viewlib.progress;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

/**
 * Created by 王黎军 on 2017/7/28.
 */

public class MszdProgress_1_Pointer {

    public Point startP;
    public Point endP;
    public Point con_1;
    public Point con_2;

    public int x;
    public int y;
    public int r;




    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MszdProgress_1_Pointer that = (MszdProgress_1_Pointer) o;

        if (x != that.x) return false;
        if (y != that.y) return false;
        return r == that.r;

    }

    /**
     * 这里我是绘制了一个圆形,其实可以绘制各种各样的图形,比如矩形,三角形,五角星之类的
     * @param canvas
     * @param paint
     */
    public void draw(Canvas canvas, Paint paint) {
        canvas.drawCircle(x, y, r, paint);
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        result = 31 * result + r;
        return result;
    }
}
