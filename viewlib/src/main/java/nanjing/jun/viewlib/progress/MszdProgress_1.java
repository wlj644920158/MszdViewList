package nanjing.jun.viewlib.progress;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import nanjing.jun.viewlib.R;

/**
 * 利用贝塞尔曲线来实现的一个有一点点特效的进度条效果,至于对贝塞尔的理解,自己百度咯,这里我们用的是3阶贝塞尔,2阶的效果不理想,更高阶的没必要,浪费计算时间
 * Created by 王黎军 on 2017/7/27.
 */

public class MszdProgress_1 extends View {
    //默认的进度条的颜色
    private static final int DEFAULT_FOREGROUD_COLOR = 0xFFe90000;
    //默认的背景的颜色
    private static final int DEFAULT_BACKGROUD_COLOR = 0xffdddddd;


    private int foreGroudColor = DEFAULT_FOREGROUD_COLOR;
    private int backGroungColor = DEFAULT_BACKGROUD_COLOR;
    //背景的画笔
    private Paint bgPaint;
    //进度条的画笔
    private Paint foreGgroundPaint;
    //裁剪画笔
    private Paint clipPaint;
    //裁剪的路径
    Path clipPath;
    //当前的进度
    private int curProgress = 0;//进度在0-100之间

    private int direction;
    private boolean isRound = true;

    private Random random = new Random();


    //设置当前的进度
    public void setProgress(int p) {
        if (p < 0) {
            p = 0;
        }
        if (p > 100) {
            p = 100;
        }
        this.curProgress = p;
    }

    public MszdProgress_1(Context context) {
        super(context);
        init();
    }

    public MszdProgress_1(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MszdProgress_1);
        foreGroudColor = typedArray.getColor(R.styleable.MszdProgress_1_progress1color, DEFAULT_FOREGROUD_COLOR);
        backGroungColor = typedArray.getColor(R.styleable.MszdProgress_1_progress1bgcolor, DEFAULT_BACKGROUD_COLOR);
        isRound = typedArray.getBoolean(R.styleable.MszdProgress_1_progress1round, true);
        typedArray.recycle();
        init();
    }

    public void setForeGroudColor(int color) {
        this.foreGroudColor = color;
    }

    public void setBackGroungColor(int color) {
        this.backGroungColor = color;
    }

    public void isRound(boolean isRound) {
        this.isRound = isRound;
    }

    /**
     * 主要是定义一些画笔
     */
    private void init() {
        bgPaint = new Paint();
        bgPaint.setAntiAlias(true);
        bgPaint.setStyle(Paint.Style.FILL);
        bgPaint.setColor(backGroungColor);

        foreGgroundPaint = new Paint();
        foreGgroundPaint.setAntiAlias(true);
        foreGgroundPaint.setStyle(Paint.Style.FILL);
        foreGgroundPaint.setColor(foreGroudColor);


        clipPaint = new Paint();
        clipPaint.setStyle(Paint.Style.FILL);
        clipPaint.setColor(Color.BLUE);
        clipPaint.setAntiAlias(true);
        //裁剪用的画笔我们设置一个渲染模式CLEAR
        clipPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));

        clipPath = new Path();
    }

    //定义一些圆点,这些圆点会从右边沿着贝塞尔曲线向左做动画移动
    List<MszdProgress_1_Pointer> pointerList = new ArrayList<>();

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (w < h) {
            direction = MszdDirection.MSZD_DIRECTION_VERTICAL;
        }
        if (w > h) {
            direction = MszdDirection.MSZD_DIRECTION_HORIZONTAL;
        }

        if (pointerList.size() > 0) {
            pointerList.clear();
        }

        if (direction == MszdDirection.MSZD_DIRECTION_HORIZONTAL) {
            Path p1 = new Path();
            p1.moveTo(0, h / 2);
            p1.addArc(0, 0, h, h, 180, 90);
            p1.lineTo(0, 0);
            p1.close();

            Path p2 = new Path();
            p2.moveTo(h / 2, h);
            p2.addArc(0, 0, h, h, 90, 90);
            p2.lineTo(0, h);
            p2.close();

            Path p3 = new Path();
            p3.moveTo(w - h / 2, 0);
            p3.addArc(w - h, 0, w, h, -90, 90);
            p3.lineTo(w, 0);
            p3.close();

            Path p4 = new Path();
            p4.moveTo(w, h / 2);
            p4.addArc(w - h, 0, getWidth(), h, 0, 90);
            p4.lineTo(w, h);
            p4.close();

            clipPath.reset();
            clipPath.addPath(p1);
            clipPath.addPath(p2);
            clipPath.addPath(p3);
            clipPath.addPath(p4);

            Point startPoint = new Point(w + h / 10, h / 2);//贝塞尔曲线的起始点
            //定义15个圆点
            for (int i = 0; i < 15; i++) {
                Point endPoint = new Point(0, random.nextInt(h));
                Point controll1, controll2;//定义两个控制点
//            两个控制点其实都是随机找的
                controll1 = new Point(random.nextInt(w), random.nextInt(h));
                controll2 = new Point(random.nextInt(w), random.nextInt(h));
                //随机点的半径,这个是按当前高度的比例来的
                int r = random.nextInt(h / 10);
                MszdProgress_1_Pointer mszdProgress_1_pointer = new MszdProgress_1_Pointer();
                mszdProgress_1_pointer.startP = startPoint;
                mszdProgress_1_pointer.endP = endPoint;
                mszdProgress_1_pointer.con_1 = controll1;
                mszdProgress_1_pointer.con_2 = controll2;
                mszdProgress_1_pointer.r = r;
                pointerList.add(mszdProgress_1_pointer);
            }


        } else {
            Path p1 = new Path();
            p1.moveTo(0, w / 2);
            p1.addArc(0, 0, w, w, 180, 90);
            p1.lineTo(0, 0);
            p1.close();

            Path p2 = new Path();
            p2.moveTo(w / 2, 0);
            p2.addArc(0, 0, w, w, -90, 90);
            p2.lineTo(w, 0);
            p2.close();

//
            Path p3 = new Path();
            p3.moveTo(w / 2, h);
            p3.addArc(0, h - w, w, h, 90, 90);
            p3.lineTo(0, h);
            p3.close();

            Path p4 = new Path();
            p4.moveTo(w, h - w / 2);
            p4.addArc(0, h - w, w, h, 0, 90);
            p4.lineTo(w, h);
            p4.close();

            clipPath.reset();
            clipPath.addPath(p1);
            clipPath.addPath(p2);
            clipPath.addPath(p3);
            clipPath.addPath(p4);

            Point startPoint = new Point(w / 2, 0);//贝塞尔曲线的起始点
            //定义15个圆点
            for (int i = 0; i < 15; i++) {
                Point endPoint = new Point(random.nextInt(w), h);
                Point controll1, controll2;//定义两个控制点
//            两个控制点其实都是随机找的
                controll1 = new Point(random.nextInt(w), random.nextInt(h));
                controll2 = new Point(random.nextInt(w), random.nextInt(h));
                //随机点的半径,这个是按当前高度的比例来的
                int r = random.nextInt(w / 10);
                MszdProgress_1_Pointer mszdProgress_1_pointer = new MszdProgress_1_Pointer();
                mszdProgress_1_pointer.startP = startPoint;
                mszdProgress_1_pointer.endP = endPoint;
                mszdProgress_1_pointer.con_1 = controll1;
                mszdProgress_1_pointer.con_2 = controll2;
                mszdProgress_1_pointer.r = r;
                pointerList.add(mszdProgress_1_pointer);
            }
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        setLayerType(LAYER_TYPE_HARDWARE, null);
        drawBackGround(canvas);
        drawProgress(canvas);
        drawPoints(canvas);
        clipCanvas(canvas);
    }


    /**
     * 裁剪画布达到,四个角都是圆角的效果
     *
     * @param canvas
     */
    private void clipCanvas(Canvas canvas) {
        if (!isRound) {
            return;
        }
        canvas.drawPath(clipPath, clipPaint);
    }

    /**
     * 绘制背景,其实就是绘制一个在长方形,非常的简单
     *
     * @param canvas
     */
    private void drawBackGround(Canvas canvas) {
        canvas.drawRect(0, 0, getWidth(), getHeight(), bgPaint);
    }

    /**
     * 绘制当前进度是的没看错还是绘制一个长方形
     *
     * @param canvas
     */
    private void drawProgress(Canvas canvas) {
        if (direction == MszdDirection.MSZD_DIRECTION_HORIZONTAL) {
            int l = (int) (getWidth() * curProgress / 100.f);
            canvas.drawRect(0, 0, l, getHeight(), foreGgroundPaint);
        } else {
            int l = (int) (getHeight() * curProgress / 100.f);
            canvas.drawRect(0, getHeight() - l, getWidth(), getHeight(), foreGgroundPaint);
        }


    }

    //绘制当前的点
    private void drawPoints(Canvas canvas) {
        for (MszdProgress_1_Pointer pointer : pointerList) {
            pointer.draw(canvas, foreGgroundPaint);
        }
    }

    ValueAnimator valueAnimator;//把动画设置为全局变量主要是这个动画是无限循环的要在特定时间关掉,避免内存泄漏

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        startProgress();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (valueAnimator != null && valueAnimator.isRunning()) {
            valueAnimator.cancel();
            valueAnimator = null;
        }
    }


    /**
     * 开启循环动画,来不断的更改小圆点的坐标
     */
    private void startProgress() {
        //取值0-1,是因为贝塞尔曲线的t的取值,这个不明白的自己要去百度
        valueAnimator = ValueAnimator.ofFloat(0f, 1f);
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        valueAnimator.setDuration(2000);
        valueAnimator.setRepeatCount(-1);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float fraction = (float) animation.getAnimatedValue();
                for (MszdProgress_1_Pointer pointer : pointerList) {
                    //以下就是3阶贝塞尔的计算方法
                    pointer.x = (int) (pointer.startP.x * (1 - fraction) * (1 - fraction) * (1 - fraction) + 3
                            * pointer.con_1.x * fraction * (1 - fraction) * (1 - fraction) + 3
                            * pointer.con_2.x * (1 - fraction) * fraction * fraction + pointer.endP.x * fraction * fraction * fraction);// 实时计算最新的点X坐标
                    pointer.y = (int) (pointer.startP.y * (1 - fraction) * (1 - fraction) * (1 - fraction) + 3
                            * pointer.con_1.y * fraction * (1 - fraction) * (1 - fraction) + 3
                            * pointer.con_2.y * (1 - fraction) * fraction * fraction + pointer.endP.y * fraction * fraction * fraction);// 实时计算最新的点Y坐标
                }
                invalidate();
            }
        });
//        valueAnimator.addListener(new Animator.AnimatorListener() {
//            @Override
//            public void onAnimationStart(Animator animation) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animator animation) {
//
//            }
//
//            @Override
//            public void onAnimationCancel(Animator animation) {
//
//            }
//
//            @Override
//            public void onAnimationRepeat(Animator animation) {
//
////                //我在动画重复的回调里模拟了进度增长
////                curProgress++;
////                if (curProgress >= 100) {
////                    curProgress = 0;
////                }
//            }
//        });
        valueAnimator.start();

    }

}
