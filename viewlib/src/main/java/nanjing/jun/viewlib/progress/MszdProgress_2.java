package nanjing.jun.viewlib.progress;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by 王黎军 on 2017/7/28.
 */

public class MszdProgress_2 extends View {

    //默认吃豆怪嘴巴张开的角度,这里是角度的一半
    private static final float DEFAULT_OPEN_DEGREE = 40;
    //默认的小黑点之间的距离
    private static final int DEFAULT_POINT_SPACING = 40;
    //    默认的背景颜色
    private static final int DEFAULT_BG_COLOR = 0xffdddddd;

    private int bgColor = DEFAULT_BG_COLOR;

    private static final int DEFAULT_WIDTH = 200;
    private static final int DEFAULT_HEIGHT = 30;


    private Paint paint;
    private Paint pointsPaint;
    private Paint bgPaint;
    //当前的进度
    private int curProgress = 0;
    private int width, height;


    //设置当前进度
    public void setCurProgress(int p) {
        if (p < 0) {
            p = 0;
        }
        if (p > 100) {
            p = 100;
        }
        this.curProgress = p;
    }

    public MszdProgress_2(Context context) {
        super(context);
        init();
    }

    public MszdProgress_2(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    /**
     * 画笔定义
     */
    private void init() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);

        pointsPaint = new Paint();
        pointsPaint.setStyle(Paint.Style.FILL);
        pointsPaint.setColor(Color.BLACK);
        pointsPaint.setAntiAlias(true);
        pointsPaint.setStrokeWidth(pointsRadius);

        bgPaint = new Paint();

        bgPaint.setStyle(Paint.Style.FILL);
        bgPaint.setColor(Color.TRANSPARENT);
        bgPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int minw = getPaddingLeft() + getPaddingRight() + DEFAULT_WIDTH;
        int w = resolveSizeAndState(minw, widthMeasureSpec, 0);

        int minh = getPaddingTop() + getPaddingBottom() + DEFAULT_HEIGHT;
        int h = resolveSizeAndState(minh, heightMeasureSpec, 0);

        setMeasuredDimension(w, h);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(bgColor);
        setLayerType(LAYER_TYPE_HARDWARE, null);
        drawPoints(canvas);
        drawKarton(canvas);
    }


    private int eyeWhiteRadius;//眼白的半径
    private int eyeBlackRadius;//瞳孔的半径
    private int pointsRadius;//小黑点的半径

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
        if (w < h) {
            throw new IllegalArgumentException("MszdProgress_2:width must be longer than height");
        }
        eyeWhiteRadius = h / 10;
        eyeBlackRadius = h / 14;
        pointsRadius = h / 12;
    }

    private boolean isColose = true;//用于辅助小怪物张口,判断当前是张口还是闭口
    private float openAgress = DEFAULT_OPEN_DEGREE;//当前张考或者闭口的角度


    /**
     * 绘制小黑点
     *
     * @param canvas
     */
    private void drawPoints(Canvas canvas) {
        int layerid = canvas.saveLayer(0, 0, width, height, null);
        int pointCount = width / DEFAULT_POINT_SPACING;
        for (int i = 1; i <= pointCount; i++) {
            canvas.drawCircle(i * DEFAULT_POINT_SPACING, height / 2, pointsRadius, pointsPaint);
        }
        //通过在小怪物左边绘制一个和背景色一样的矩形来模拟被小怪物吃掉的黑点
        int offsetX = (int) (curProgress * width / 100.0f);
        if (offsetX <= height / 2) {
            offsetX = height / 2;
        }
        if (offsetX >= width - height / 2) {
            offsetX = width - height / 2;
        }
        offsetX = offsetX + height / 2;
        canvas.drawRect(0, 0, offsetX, height, bgPaint);
        canvas.restoreToCount(layerid);
    }

    /**
     * 绘制小怪物
     *
     * @param canvas
     */
    private void drawKarton(Canvas canvas) {
        //计算小怪物当前的X左边的位置
        int offsetX = (int) (curProgress * width / 100.0f);
        //这里处理两个临界值
        if (offsetX <= height / 2) {
            //左边的临界值
            offsetX = height / 2;
        }
        if (offsetX >= width - height / 2) {
            //右边的临界值
            offsetX = width - height / 2;
        }
        paint.setColor(0xffffc600);
        //绘制小怪物的脸
        canvas.drawArc(offsetX - height / 2, 0, offsetX - height / 2 + height, height, openAgress, 360 - openAgress * 2, true, paint);
        paint.setColor(Color.WHITE);
        //绘制小怪物的眼白
        canvas.drawCircle(offsetX, height / 4, eyeWhiteRadius, paint);
        paint.setColor(Color.BLACK);
        //绘制小怪物的瞳孔
        canvas.drawCircle(offsetX, height / 4, eyeBlackRadius, paint);
        //变量控制小怪物张口闭口
        if (isColose) {
            openAgress = openAgress - 4;
            if (openAgress <= 0) {
                isColose = false;
            }
        } else {
            openAgress = openAgress + 4;
            if (openAgress >= DEFAULT_OPEN_DEGREE) {
                isColose = true;
            }
        }
        invalidate();
    }
}
