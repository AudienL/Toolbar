package com.audienl.library;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 * @author audienl@qq.com on 2016/7/18.
 */
public class ControlView extends LinearLayout {
    private Context context;

    private int mWidth;
    private int mHeight;

    public ControlView(Context context) {
        this(context, null);
    }

    public ControlView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ControlView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;

        // 默认宽高
        mWidth = context.getResources().getDisplayMetrics().widthPixels;
        mHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 48, context.getResources().getDisplayMetrics());

        this.setOrientation(HORIZONTAL);
        this.setBackgroundColor(0xFFFFFF);
    }

    public void addButton() {
        Button btn = new Button(context);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
        btn.setLayoutParams(params);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        switch (widthMode) {
            case MeasureSpec.AT_MOST:
            case MeasureSpec.EXACTLY:
                mWidth = widthSize;
                break;
            case MeasureSpec.UNSPECIFIED:
                break;
        }

        switch (heightMode) {
            case MeasureSpec.AT_MOST:
            case MeasureSpec.EXACTLY:
                mHeight = heightSize;
                break;
            case MeasureSpec.UNSPECIFIED:
                break;
        }

        setMeasuredDimension(mWidth, mHeight);
    }
}
