package nanjing.jun.viewlib.combine;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import nanjing.jun.viewlib.R;

/**
 * Created by 王黎军 on 2017/8/1.
 */

public class MszdMenuItem_1 extends LinearLayout {
    private TextView labelTv;
    private CheckBox selectCb;
    private String labelText;

    public MszdMenuItem_1(Context context) {
        super(context);
        init();
    }

    public MszdMenuItem_1(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MszdMenuItem_1);
        labelText = typedArray.getString(R.styleable.MszdMenuItem_1_labelText);
        typedArray.recycle();
        init();
    }
    //    private void init() {
//        labelTv = new TextView(getContext());
//        LinearLayout.LayoutParams labelLayoutParams = new LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT);
//        labelLayoutParams.weight = 1;
//        labelTv.setLayoutParams(labelLayoutParams);
//        labelTv.setText(labelText);
//        addView(labelTv);
//        selectCb = new CheckBox(getContext());
//        selectCb.setChecked(false);
//        addView(selectCb);
//    }
    private void init() {
        View.inflate(getContext(), R.layout.layout_menu_1, this);
        labelTv = (TextView) findViewById(R.id.tv_label);
        selectCb = (CheckBox) findViewById(R.id.cb_select);
        labelTv.setText(labelText);
    }


    public void setLabelText(String labelText) {
        labelTv.setText(labelText);
    }

    public void setSelectCb(boolean selected) {
        selectCb.setChecked(selected);
    }
}
