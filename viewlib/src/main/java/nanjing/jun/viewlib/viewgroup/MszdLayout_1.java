package nanjing.jun.viewlib.viewgroup;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by 王黎军 on 2017/8/1.
 */

public class MszdLayout_1 extends ViewGroup {
    public MszdLayout_1(Context context) {
        super(context);
    }

    public MszdLayout_1(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureChildren(widthMeasureSpec,heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int curX = l, curY = t;
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            view.layout(curX, curY, curX + view.getMeasuredWidth(), curY + view.getMeasuredHeight());
            curX = curX + view.getMeasuredWidth();
            curY = curY + view.getMeasuredHeight();
        }
    }

}
