package nanjing.jun.viewlib.button;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.BounceInterpolator;

import nanjing.jun.viewlib.R;

/**
 * 该自定义view是继承view实现,就时相当于自己做一个自己的控件了
 * <p>
 * Created by 王黎军 on 2017/7/26.
 */

public class MszdButton_2 extends View {


    //回调的接口定义里面有3个回调方法,其中onFail其实是没啥用的,onSuccess是为了让用户看到成功的效果,并且在显示成功完成后以回调的方式通知登陆成功的状态显示结束了
    //虽然说的有点点懵逼,总结一下就是为了让用户能看到成功的提示,所以才加了一个onSuccess回调
    private onMszdButtonClickListener listener;

    public void setOnMszdButtonClickListener(onMszdButtonClickListener listener) {
        this.listener = listener;
    }


    //状态值
    private static final int STATUS_AIDL = 0;//初始状态
    private static final int STATUS_LOADING_STEP_1 = 1;//加载状态第一步
    private static final int STATUS_LOADING_STEP_2 = 2;//加载状态第二部
    private static final int STATUS_LOADING_SUCCESS = 3;//加载成功
    private static final int STATUS_LOADING_FAIL = 4;//加载失败

    private static final String DEFAULT_TEXT = "Sign In";//默认的初始状态文字
    private static final int DEFAULT_TEXT_COLOR = 0xffffffff;//默认的文字颜色
    private static final int DEFAULT_BUTTON_COLOR = 0xFFe90000;//默认的按钮颜色
    private static final int DEFAULT_TEXT_SIZE = 40;//默认文字大小


    //关于文字的一些东西
    private String normalText = DEFAULT_TEXT;//按钮显示的文字
    private int textColor = DEFAULT_TEXT_COLOR;//按钮文字颜色
    private float textSize = DEFAULT_TEXT_SIZE;//按钮文字大小


    private int buttonColor = DEFAULT_BUTTON_COLOR;//按钮颜色


    //我喜欢定义很多画笔,我也不知道为什么
    private TextPaint textPaint;//绘制文字画笔
    private Paint progressPaint;//绘制转动的圆圈的画笔
    private Paint buttonPaint;//绘制按钮的画笔
    private int status = STATUS_AIDL;//设置初始状态
    private Paint.FontMetrics metrics;
    private int textY;

    private int buttonRadius;//按钮的圆角
    private int buttonWidth;//控制变量,用来控制当前按钮的宽度,用于配合动画实现按钮往中间缩放或者往两边展开
    private int width;
    private int height;
    private int centerX, centerY;//中心点XY坐标


    //利用handler实现的延时消息分发,主要是为了加载成功或者失败的时候提示的信息能展示一定的时间
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (status) {
                //失败的时候要重新回到初始的状态
                case STATUS_LOADING_FAIL:
                    if (listener != null) {
                        listener.onFail();
                    }
                    set_circle_2_rect();
                    break;
                case STATUS_LOADING_SUCCESS:
                    if (listener != null) {
                        listener.onSuccess();
                    }
                    break;
            }
        }
    };


    public MszdButton_2(Context context) {
        super(context);
        init();
    }

    public MszdButton_2(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.MszdButton_2);
        String normalText_ = ta.getString(R.styleable.MszdButton_2_btn2text);
        if (!TextUtils.isEmpty(normalText_)) {
            this.normalText = normalText_;
        }
        textColor = ta.getColor(R.styleable.MszdButton_2_btn2textColor, DEFAULT_TEXT_COLOR);
        textSize = ta.getDimensionPixelSize(R.styleable.MszdButton_2_btn2textSize, DEFAULT_TEXT_SIZE);
        buttonColor = ta.getColor(R.styleable.MszdButton_2_btn2buttonColor, DEFAULT_BUTTON_COLOR);
        ta.recycle();
        init();
    }

    public void setNormalText(String text) {
        this.normalText = text;
    }

    public void setTextColor(int color) {
        this.textColor = color;
    }

    public void setTextSize(float textSize) {
        this.textSize = textSize;
    }

    public void setButtonColor(int color) {
        this.buttonColor = color;
    }

    /**
     * 主要是乱七八糟的画笔的设置,一般其实一个画笔就能解决问题的,建议只是用一支画笔
     */
    private void init() {
        textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG | Paint.LINEAR_TEXT_FLAG);
        textPaint.setTextSize(textSize);
        textPaint.setColor(textColor);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setTextAlign(Paint.Align.CENTER);

        metrics = textPaint.getFontMetrics();

        progressPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        progressPaint.setColor(textColor);
        progressPaint.setStyle(Paint.Style.STROKE);
        progressPaint.setTextAlign(Paint.Align.CENTER);
        progressPaint.setStrokeWidth(8);


        buttonPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        buttonPaint.setStyle(Paint.Style.FILL);
        buttonPaint.setColor(buttonColor);
    }


    /**
     * 不管padding 的取值是怎样的不会影响文字的居中显示,因为我们没有可以去处理这个padding
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int minw = (int) (getPaddingLeft() + getPaddingRight() + textPaint.measureText(normalText));
        int w = resolveSizeAndState(minw, widthMeasureSpec, 0);

        int minh = (int) (getPaddingTop() + getPaddingBottom() + Math.abs(metrics.bottom - metrics.top));
        int h = resolveSizeAndState(minh, heightMeasureSpec, 0);

        setMeasuredDimension(w, h);
    }


    /**
     * 获取按钮宽度,按钮的圆角值
     *
     * @param w
     * @param h
     * @param oldw
     * @param oldh
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        buttonWidth = w;
        buttonRadius = 0;
        centerX = w / 2;
        centerY = h / 2;
        width = w;
        height = h;
        textY = (int) (getHeight() / 2 - metrics.descent + (metrics.bottom - metrics.top) / 2);
    }

    /**
     * 绘制函数
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.TRANSPARENT);
        switch (status) {
            case STATUS_AIDL:
                drawButton(canvas);
                drawText(canvas);
                break;
            case STATUS_LOADING_STEP_1:
                drawButton(canvas);
                break;
            case STATUS_LOADING_STEP_2:
                drawButton(canvas);
                drawProgress(canvas);
                invalidate();
                break;
            case STATUS_LOADING_FAIL:
                drawButton(canvas);
                drawFail(canvas);
                break;
            case STATUS_LOADING_SUCCESS:
                drawButton(canvas);
                drawSuccess(canvas);
                break;
        }
    }

    /**
     * 绘制按钮
     *
     * @param canvas
     */
    private void drawButton(Canvas canvas) {
        int id = canvas.saveLayer(0, 0, width, height, null);
        canvas.drawRoundRect(centerX - buttonWidth / 2, 0, centerX + buttonWidth / 2, height, buttonRadius, buttonRadius, buttonPaint);
        canvas.restoreToCount(id);
    }

    /**
     * 绘制中间的文字,就是初始状态的时候的文字
     *
     * @param canvas
     */
    private void drawText(Canvas canvas) {
        canvas.drawText(normalText, centerX, textY, textPaint);
    }


    /**
     * 绘制中间转动的白色圈圈,我们看到的其实就是两个相距180度的圆弧在那一直转
     */
    private float startAngle = 0;//用这个来控制起始的角度,每次界面刷新的时候用一个递增的值就能实现转动的效果
    private int progressPadding = 10;//这个是为了让圆弧靠里边一点,看效果就知道了

    private void drawProgress(Canvas canvas) {
        canvas.drawArc(centerX - height / 2 + progressPadding, progressPadding, centerX + height / 2 - progressPadding, height - progressPadding, startAngle, 60, false, progressPaint);
        canvas.drawArc(centerX - height / 2 + progressPadding, progressPadding, centerX + height / 2 - progressPadding, height - progressPadding, startAngle + 180, 60, false, progressPaint);
        startAngle = startAngle + 4;
    }

    /**
     * 当前状态为STATUS_LOADING_FAIL 的时候,中间绘制一个x号,简单来说就是绘制两条线段
     *
     * @param canvas
     */
    private void drawFail(Canvas canvas) {
        canvas.drawLine(centerX - height / 4, height / 4, centerX + height / 4, height * 3 / 4, progressPaint);
        canvas.drawLine(centerX + height / 4, height / 4, centerX - height / 4, height * 3 / 4, progressPaint);
    }

    /**
     * 当前状态为STATUS_LOADING_SUCCESS 的时候,中间绘制一个√,也是两条线段,有点丑,不过这个要自己定义一下比例什么的,主要是找到转折点在哪
     * ,对于这种比较复杂一点的图形我建议用path来绘制,我这里偷懒一下
     *
     * @param canvas
     */
    private void drawSuccess(Canvas canvas) {
        canvas.drawLine(centerX - height / 4, height / 2, centerX - height / 8, height * 3 / 4, progressPaint);
        canvas.drawLine(centerX - height / 8, height * 3 / 4, centerX + height / 4, height / 4, progressPaint);
    }

    /**
     * 开始一个动画,把初始状态长方形的按钮变成圆的
     */
    private void set_rect_2_circle() {
        //第一个动画是把长方形变成圆角长方形
        ValueAnimator rect_2_round_animator = ValueAnimator.ofInt(0, height / 2);
        rect_2_round_animator.setDuration(400);
        rect_2_round_animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                buttonRadius = (int) animation.getAnimatedValue();
                invalidate();
            }
        });
        //第二个动画是把圆角长方形变成圆形
        ValueAnimator round_2_circle_animator = ValueAnimator.ofInt(width, height);
        round_2_circle_animator.setDuration(800);
        round_2_circle_animator.setInterpolator(new BounceInterpolator());
        round_2_circle_animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                buttonWidth = (int) animation.getAnimatedValue();
                invalidate();
            }
        });
        //把两个动画串起来执行
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playSequentially(rect_2_round_animator, round_2_circle_animator);
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                status = STATUS_LOADING_STEP_2;
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animatorSet.start();
    }

    /**
     * 开启一个动画把圆形状态变回初始的长方形,和上面的方法是逆向的
     */
    private void set_circle_2_rect() {
        //第一个动画圆形变到圆角
        ValueAnimator circle_2_round_animator = ValueAnimator.ofInt(height, width);
        circle_2_round_animator.setDuration(800);
        circle_2_round_animator.setInterpolator(new BounceInterpolator());
        circle_2_round_animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                buttonWidth = (int) animation.getAnimatedValue();
                invalidate();
            }
        });
        //第二个动画圆角变成长方形
        ValueAnimator round_2_rect_animator = ValueAnimator.ofInt(height / 2, 0);
        round_2_rect_animator.setDuration(400);
        round_2_rect_animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                buttonRadius = (int) animation.getAnimatedValue();
                invalidate();
            }
        });


        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playSequentially(circle_2_round_animator, round_2_rect_animator);
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                status = STATUS_AIDL;//动画结束状态变成初始状态
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animatorSet.start();
    }

    private boolean hasActionDown = false;//这个变量主要是确定手指是不是在按钮区域按下的,如果是在当前按钮按下的才看成是一次完整的点击

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                hasActionDown = true;
                break;
            case MotionEvent.ACTION_UP:
                if (hasActionDown && status == STATUS_AIDL) {
                    status = STATUS_LOADING_STEP_1;
                    hasActionDown = false;
                    set_rect_2_circle();
                    if (listener != null) {
                        listener.onMszdButtonClick();
                    }
                }
                break;
        }
        return true;
    }


    /**
     * 登陆失败时候调用,该方法可能会在动画执没有行完调用,后期优化
     */
    public void setOnLoadingFail() {
        status = STATUS_LOADING_FAIL;
        invalidate();
        handler.sendEmptyMessageDelayed(0, 1000);
    }

    /**
     * 登陆成功时候调用,该方法可能会在动画执没有行完调用,后期优化
     */
    public void setOnLoadingSuccess() {
        status = STATUS_LOADING_SUCCESS;
        invalidate();
    }

}
