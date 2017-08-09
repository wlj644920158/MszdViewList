package nanjing.jun.viewlib.load;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import nanjing.jun.viewlib.R;

/**
 * 定义每个扇形的角度是30度,3个刚好组成90度
 * Created by 王黎军 on 2017/7/31.
 */

public class MszdLoading_1 extends View {

    //默认每次刷新视图移动的度数
    private static final int MOVE_STEP = 9;
    private static final int DEFAULT_WIDTH = 100;
    private static final int DEFAULT_HEIGHT = 100;
    MszdLoading_1_Arc[] arc;
    private Paint paint;

    private int radius;

    private float moveDegree = 0;
    private int curArcIndex;


    public MszdLoading_1(Context context) {
        super(context);
        init();
    }

    public MszdLoading_1(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init();
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MszdLoading_1);
        int color1 = typedArray.getColor(R.styleable.MszdLoading_1_loading1color, Color.BLUE);
        int color2 = typedArray.getColor(R.styleable.MszdLoading_1_loading2color, Color.RED);
        int color3 = typedArray.getColor(R.styleable.MszdLoading_1_loading3color, Color.YELLOW);

        arc[0].setColor(color1);
        arc[1].setColor(color2);
        arc[2].setColor(color3);

    }


    public void setColor1(int color) {
        arc[0].setColor(color);
    }

    public void setColor2(int color) {
        arc[1].setColor(color);
    }

    public void setColor3(int color) {
        arc[2].setColor(color);
    }






    private void init() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        arc = new MszdLoading_1_Arc[3];
        arc[0] = new MszdLoading_1_Arc();
        arc[0].setStartDegree(0);
        arc[0].setSweepDegree(30);

        arc[1] = new MszdLoading_1_Arc();
        arc[1].setStartDegree(30);
        arc[1].setSweepDegree(30);

        arc[2] = new MszdLoading_1_Arc();
        arc[2].setStartDegree(60);
        arc[2].setSweepDegree(30);
        curArcIndex = arc.length - 1;
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
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        radius = Math.min(w, h) / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.TRANSPARENT);

        for (int i = 0; i < arc.length; i++) {
            arc[i].myDraw(canvas, paint, radius);
        }

        moveDegree = moveDegree + MOVE_STEP;
        if (moveDegree > 270) {
            moveDegree = 0;
            curArcIndex--;
            if (curArcIndex < 0) {
                curArcIndex = arc.length - 1;
            }
        } else {
            arc[curArcIndex].setStartDegree(arc[curArcIndex].getStartDegree() + MOVE_STEP);
        }
        invalidate();
    }
}
