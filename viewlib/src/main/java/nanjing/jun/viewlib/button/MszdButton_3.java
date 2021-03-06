package nanjing.jun.viewlib.button;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import nanjing.jun.viewlib.R;

/**
 * Created by 王黎军 on 2017/7/26.
 */

public class MszdButton_3 extends View {


    private onMszdButtonClickListener listener;

    public void setOnMszdButtonClickListener(onMszdButtonClickListener listener) {
        this.listener = listener;
    }


    private static final int STATUS_AIDL = 0;
    private static final int STATUS_LOADING = 1;
    private static final int STATUS_LOADING_SUCCESS = 3;
    private static final int STATUS_LOADING_FAIL = 4;

    private static final String DEFAULT_TEXT = "Sign In";
    private static final String DEFAULT_LOADING_TEXT = "Sign In Now";
    private static final String DEFAULT_LOADING_FAIL_TEXT = "Sign In Fail";
    private static final String DEFAULT_LOADING_SUCCESS_TEXT = "Sign In Success";
    private static final int DEFAULT_TEXT_COLOR = 0xffffffff;
    private static final int DEFAULT_BUTTON_COLOR = 0xFFe90000;
    private static final int DEFAULT_TEXT_SIZE = 40;

    //关于文字的一些东西
    private String normalText = DEFAULT_TEXT;
    private String loadingText = DEFAULT_LOADING_TEXT;
    private String loadingFailText = DEFAULT_LOADING_FAIL_TEXT;
    private String loadingSuccessText = DEFAULT_LOADING_SUCCESS_TEXT;
    private int textColor = DEFAULT_TEXT_COLOR;
    private float textSize = DEFAULT_TEXT_SIZE;
    private int buttonColor = DEFAULT_BUTTON_COLOR;


    private Paint.FontMetrics textMetrics;
    private int textY;


    private TextPaint textPaint;
    private Paint buttonPaint;
    private int status = STATUS_AIDL;

    private float singleTextWidth;


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (status) {
                case STATUS_LOADING:
                    curJumpIndex++;
                    curJumpIndex = curJumpIndex % loadingText.length();
                    invalidate();
                    sendEmptyMessageDelayed(0, 160);
                    break;
                case STATUS_LOADING_FAIL:
                    if (listener != null) {
                        listener.onFail();
                    }
                    status = STATUS_AIDL;
                    invalidate();
                    break;
                case STATUS_LOADING_SUCCESS:
                    if (listener != null) {
                        listener.onSuccess();
                    }
                    break;
            }
        }
    };


    public MszdButton_3(Context context) {
        super(context);
        init();
    }

    public MszdButton_3(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.MszdButton_3);
        String normalText_ = ta.getString(R.styleable.MszdButton_3_btn3text);
        String loadingText_ = ta.getString(R.styleable.MszdButton_3_btn3loadingText);
        String loadingFailText_ = ta.getString(R.styleable.MszdButton_3_btn3loadingFailText);
        String loadingSuccessText_ = ta.getString(R.styleable.MszdButton_3_btn3loadingSuccessText);
        if (TextUtils.isEmpty(normalText_) || TextUtils.isEmpty(loadingText_) || TextUtils.isEmpty(loadingFailText_) || TextUtils.isEmpty(loadingSuccessText_)) {
            throw new RuntimeException("text must contains 4 status");
        }

        this.normalText = normalText_;
        this.loadingText = loadingText_;
        this.loadingFailText = loadingFailText_;
        this.loadingSuccessText = loadingSuccessText_;
        textColor = ta.getColor(R.styleable.MszdButton_3_btn3textColor, DEFAULT_TEXT_COLOR);
        textSize = ta.getDimensionPixelSize(R.styleable.MszdButton_3_btn3textSize, DEFAULT_TEXT_SIZE);
        buttonColor = ta.getColor(R.styleable.MszdButton_3_btn3buttonColor, DEFAULT_BUTTON_COLOR);
        ta.recycle();
        init();
    }

    public void setButtonTexts(String normalText_, String loadingText_, String loadingFailText_, String loadingSuccessText_) {
        if (TextUtils.isEmpty(normalText_) || TextUtils.isEmpty(loadingText_) || TextUtils.isEmpty(loadingFailText_) || TextUtils.isEmpty(loadingSuccessText_)) {
            throw new RuntimeException("text must contains 4 status");
        }
        this.normalText = normalText_;
        this.loadingText = loadingText_;
        this.loadingFailText = loadingFailText_;
        this.loadingSuccessText = loadingSuccessText_;
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

    private void init() {
        textPaint = new TextPaint();
        textPaint.setTextSize(textSize);
        textPaint.setColor(textColor);
        textPaint.setAntiAlias(true);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setTextAlign(Paint.Align.CENTER);

        textMetrics = textPaint.getFontMetrics();
        singleTextWidth = textPaint.measureText(loadingText) / loadingText.length();

        buttonPaint = new Paint();
        buttonPaint.setAntiAlias(true);
        buttonPaint.setStyle(Paint.Style.FILL);
        buttonPaint.setColor(buttonColor);
    }

    /**
     * 不管padding 的取值是怎样的不会影响文字的居中显示,因为我们没有可以去处理这个padding
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int normalTextLength = (int) textPaint.measureText(normalText);
        int loadingTextLength = (int) textPaint.measureText(loadingText);
        int loadingFailTextLength = (int) textPaint.measureText(loadingFailText);
        int loadingSucceessTextLength = (int) textPaint.measureText(loadingSuccessText);


        int maxLength = normalTextLength;
        if (maxLength < loadingTextLength) {
            maxLength = loadingTextLength;
        }
        if (maxLength < loadingFailTextLength) {
            maxLength = loadingFailTextLength;
        }
        if (maxLength < loadingSucceessTextLength) {
            maxLength = loadingSucceessTextLength;
        }

        int minw = (int) (getPaddingLeft() + getPaddingRight() + maxLength);
        int w = resolveSizeAndState(minw, widthMeasureSpec, 0);

        int minh = (int) (getPaddingTop() + getPaddingBottom() + Math.abs(textMetrics.bottom - textMetrics.top));
        int h = resolveSizeAndState(minh, heightMeasureSpec, 0);

        setMeasuredDimension(w, h);
    }

    private int centerX, centerY;

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        centerX = w / 2;
        centerY = h / 2;

        textY = (int) (getHeight() / 2 - textMetrics.descent + (textMetrics.bottom - textMetrics.top) / 2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(buttonColor);
        switch (status) {
            case STATUS_AIDL:
                drawText(canvas);
                break;
            case STATUS_LOADING:
                drawLoadingText(canvas);
                break;
            case STATUS_LOADING_FAIL:
                drawFail(canvas);
                break;
            case STATUS_LOADING_SUCCESS:
                drawSuccess(canvas);
                break;
        }
    }


    private void drawText(Canvas canvas) {
        canvas.drawText(normalText, centerX, textY, textPaint);
    }


    private void drawLoadingText(Canvas canvas) {
        for (int i = 0; i < loadingText.length(); i++) {
            int y = textY;
            if (i == curJumpIndex) {
                y = y - jumpHeight;
            }
            canvas.drawText(String.valueOf(loadingText.charAt(i)), centerX - singleTextWidth * loadingText.length() / 2 + singleTextWidth * i + singleTextWidth / 2, y, textPaint);
        }

    }

    private void drawFail(Canvas canvas) {
        canvas.drawText(loadingFailText, centerX, textY, textPaint);
    }

    private void drawSuccess(Canvas canvas) {
        canvas.drawText(loadingSuccessText, centerX, textY, textPaint);
    }


    private int curJumpIndex = 0;
    private int jumpHeight = 10;


    private boolean hasActionDown = false;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                hasActionDown = true;
                break;
            case MotionEvent.ACTION_UP:
                if (hasActionDown && status == STATUS_AIDL) {
                    status = STATUS_LOADING;
                    hasActionDown = false;
                    handler.sendEmptyMessage(0);
                    if (listener != null) {
                        listener.onMszdButtonClick();
                    }

                }
                break;
        }
        return true;
    }


    public void setOnLoadingFail() {
        status = STATUS_LOADING_FAIL;
        invalidate();
        handler.sendEmptyMessageDelayed(0, 1000);
    }


    public void setOnLoadingSuccess() {
        status = STATUS_LOADING_SUCCESS;
        invalidate();
    }

}
