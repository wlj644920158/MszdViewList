package nanjing.jun.viewlib.button;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import nanjing.jun.viewlib.R;


/**
 * 这个自定义按钮就是自定义View其中的一种方式-->继承已有的控件进行扩展,这个MszdButton_1非常的简单
 * Created by 王黎军 on 2017/7/25.
 */

public class MszdButton_1 extends android.support.v7.widget.AppCompatButton implements View.OnClickListener {

    //回调的接口定义里面有3个回调方法,其中onFail其实是没啥用的,onSuccess是为了让用户看到成功的效果,并且在显示成功完成后以回调的方式通知登陆成功的状态显示结束了
    //虽然说的有点点懵逼,总结一下就是为了让用户能看到成功的提示,所以才加了一个onSuccess回调
    private onMszdButtonClickListener listener;

    public void setOnMszdButtonClickListener(onMszdButtonClickListener listener) {
        this.listener = listener;
    }

    //设置一些默认的值
    private static final String DEFAULT_NORMAL_TEXT = "Sign In";//默认的初始状态的文字
    private static final String DEFAULT_LOADING_TEXT = "Sign In Now";//默认的正在操作的文字
    private static final String DEFAULT_LOADING_FAIL_TEXT = "Sign In Fail";//默认的操作失败时的文字
    private static final int DEFAULT_LOADING_FINISH_DELAY = 1000;//默认加载成功后延迟多久执行操作,成功时回调,失败时回到初始文字状态

    //各种状态值状态
    private static final int STATUS_AIDL = 0;//初始状态
    private static final int STATUS_LOADING = 1;//正在加载的状态
    private static final int STATUS_LOADING_FINISH_FAIL = 2;//加载失败时的状态
    private static final int STATUS_LOADING_FINISH_SUCCESS = 3;//加载成功时的状态


    private String normalText = DEFAULT_NORMAL_TEXT;//初始文字
    private String loadingText = DEFAULT_LOADING_TEXT;//加载中文字
    private String loadingFailText = DEFAULT_LOADING_FAIL_TEXT;//加载失败文字
    private String loadingSuccessText = DEFAULT_LOADING_FAIL_TEXT;//加载失败文字
    private int loadingFinishDelay = DEFAULT_LOADING_FINISH_DELAY;//加载结束后延迟时间
    private String[] pointerText = {"", ".", "..", "..."};//这个主要是添加到正在加载文字的最后形成一个动态的效果
    private int pointerIndex = 0;//辅助索引值,用来记录当前正在加载文字后面要添加哪个

    private int status = STATUS_AIDL;//默认初始状态

    //利用handler来做延时操作,至于延时操作的方法很多不局限于handler
    //2个地方用到了handler,当用户按下按钮的时候,发送一次消息,用户设置加载完成时发送一次消息
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (status) {
                //当用户按下按钮的时候,handler发送消息,利用sendEmptyMessageDelayed来实现后面....的动态效果
                case STATUS_LOADING:
                    setText(loadingText + pointerText[pointerIndex]);
                    pointerIndex++;
                    pointerIndex = pointerIndex % pointerText.length;
                    sendEmptyMessageDelayed(0, 800);
                    break;
                case STATUS_LOADING_FINISH_FAIL:
                    status = STATUS_AIDL;
                    setText(normalText);
                    pointerIndex = 0;
                    if (listener != null) {
                        listener.onFail();
                    }
                    break;
                case STATUS_LOADING_FINISH_SUCCESS:
                    status = STATUS_AIDL;
                    setText(normalText);
                    pointerIndex = 0;
                    if (listener != null) {
                        listener.onSuccess();
                    }
                    break;
            }
        }
    };

    public MszdButton_1(Context context) {
        super(context);
        init();
    }

    public MszdButton_1(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.MszdButton_1);
        String normalText_ = ta.getString(R.styleable.MszdButton_1_btn1normalText);
        String loadingText_ = ta.getString(R.styleable.MszdButton_1_btn1loadingText);
        String loadingFailText_ = ta.getString(R.styleable.MszdButton_1_btn1loadingFailText);
        String loadingSuccessText_ = ta.getString(R.styleable.MszdButton_1_btn1loadingSuccessText);

        //如果你要设置文字参数,那么就要同事设置4个

        if (TextUtils.isEmpty(normalText_) || TextUtils.isEmpty(loadingText_) || TextUtils.isEmpty(loadingFailText_) || TextUtils.isEmpty(loadingSuccessText_)) {
            throw new RuntimeException("text must contains 4 status");
        }
        this.normalText = normalText_;
        this.loadingText = loadingText_;
        this.loadingFailText = loadingFailText_;
        this.loadingSuccessText = loadingSuccessText_;
        this.loadingFinishDelay = ta.getInteger(R.styleable.MszdButton_1_btn1delayTime, DEFAULT_LOADING_FINISH_DELAY);
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

    public void setFinishDelayTime2Normal(int delay) {
        this.loadingFinishDelay = delay;
    }


    /**
     * 自己处理自己的点击事件,然后用回调的方式通知用户点击了
     */
    private void init() {
        this.setOnClickListener(this);
        setText(normalText);
    }

    /**
     * 加载失败的时候设置
     *
     * @param
     */
    public void setLoadingFail() {
        handler.removeMessages(0);
        status = STATUS_LOADING_FINISH_FAIL;
        setText(loadingFailText);
        handler.sendEmptyMessageDelayed(0, loadingFinishDelay);
    }

    /**
     * 登陆成功后调用,移除STATUS_LOADING状态下handler发出的消息
     */
    public void setLoadingSuccess() {
        handler.removeMessages(0);
        status = STATUS_LOADING_FINISH_SUCCESS;
        setText(loadingSuccessText);
        handler.sendEmptyMessageDelayed(0, loadingFinishDelay);
    }

    @Override
    public void onClick(View v) {
        if (listener != null) {
            listener.onMszdButtonClick();
        }
        switch (status) {
            case STATUS_AIDL:
                status = STATUS_LOADING;
                setText(loadingText);
                handler.sendEmptyMessage(0);
                break;
        }
    }
}
