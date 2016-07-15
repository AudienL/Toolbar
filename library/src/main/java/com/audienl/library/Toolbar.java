package com.audienl.library;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * @author audienl@qq.com on 2016/7/15.
 */
public class Toolbar extends RelativeLayout {
    protected Context context;

    /** 标题 */
    protected String mTitle;
    /** 是否显示返回 */
    protected boolean mShowReturn = false;
    /** 右边起第一个按钮的图片资源，如果没有，则为-1 */
    protected int mBtn1ImageResId = -1;
    /** 右边起第二个按钮的图片资源，如果没有，则为-1 */
    protected int mBtn2ImageResId = -1;

    ///////////////////////////////////////////////////////////////////////////
    // Toolbar层
    ///////////////////////////////////////////////////////////////////////////
    /** Toolbar层 */
    private ViewGroup mLayerToolbar;
    /** 返回layout */
    private ViewGroup mLayoutReturn;
    /** 左边的标题 */
    private TextView mTvTitleLeft;
    /** 中间的标题 */
    private TextView mTvTitleCenter;
    /** 右边起第一个按钮 */
    private ViewGroup mLayoutButton1;
    /** 右边起第二个按钮 */
    private ViewGroup mLayoutButton2;

    ///////////////////////////////////////////////////////////////////////////
    // 选择层
    ///////////////////////////////////////////////////////////////////////////
    /** 选择层 */
    private ViewGroup mLayerSelect;
    /** 取消按钮 */
    private Button mBtnCancel;
    /** 提示选择了多少项 */
    private TextView mTvSelectCount;
    /** 全选按钮 */
    private Button mBtnSelectAll;

    /** 是否已经全选了 */
    private boolean isSelectedAll = false;

    private int mDefaultWidth;
    private int mDefaultHeight;

    public Toolbar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Toolbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init(attrs, defStyleAttr);
    }

    private void init(AttributeSet attrs, int defStyleAttr) {
        // load attrs
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.Toolbar, defStyleAttr, 0);
        mTitle = a.getString(R.styleable.Toolbar_toolbar_title);
        mShowReturn = a.getBoolean(R.styleable.Toolbar_toolbar_show_return, false);
        mBtn1ImageResId = a.getResourceId(R.styleable.Toolbar_toolbar_src_button1, -1);
        mBtn2ImageResId = a.getResourceId(R.styleable.Toolbar_toolbar_src_button2, -1);
        a.recycle();

        mDefaultWidth = context.getResources().getDisplayMetrics().widthPixels;
        mDefaultHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 48, context.getResources().getDisplayMetrics());

        View view = LayoutInflater.from(context).inflate(R.layout.tool_bar, this, true);
        initLayerToolbar(view);
        initLayerSelect(view);
    }

//    @Override
//    protected void onFinishInflate() {
//        super.onFinishInflate();
//        View view = LayoutInflater.from(context).inflate(R.layout.tool_bar, this, true);
//
//        initLayerToolbar(view);
//        initLayerSelect(view);
//    }

    /**
     * 初始化Toolbar层
     */
    private void initLayerToolbar(View rootView) {
        mLayerToolbar = (ViewGroup) rootView.findViewById(R.id.layer_toolbar);
        mLayerSelect = (ViewGroup) rootView.findViewById(R.id.layer_select);
        mLayoutReturn = (ViewGroup) rootView.findViewById(R.id.layout_return);
        mTvTitleLeft = (TextView) rootView.findViewById(R.id.tv_title_left);
        mTvTitleCenter = (TextView) rootView.findViewById(R.id.tv_title_center);
        mLayoutButton1 = (ViewGroup) rootView.findViewById(R.id.layout_button1);
        mLayoutButton2 = (ViewGroup) rootView.findViewById(R.id.layout_button2);

        // 返回+标题
        if (mShowReturn) {
            // 显示返回
            mLayoutReturn.setVisibility(VISIBLE);
            if (mTitle != null) {
                // 设置左边标题
                mTvTitleLeft.setText(mTitle);
            }
            // 设置返回监听事件
            mLayoutReturn.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnToolbarClickedListener != null) {
                        mOnToolbarClickedListener.onReturnClick();
                    }
                }
            });
        } else {
            // 不显示返回，在中间显示标题
            if (mTitle != null) {
                mTvTitleCenter.setText(mTitle);
            }
        }

        // 右边起第一个按钮
        if (mBtn1ImageResId != -1) {
            // 监听点击
            mLayoutButton1.setVisibility(VISIBLE);
            mLayoutButton1.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnToolbarClickedListener != null) {
                        mOnToolbarClickedListener.onButton1Click();
                    }
                }
            });
            // 设置图片
            ImageView ivButton1 = (ImageView) rootView.findViewById(R.id.iv_button1);
            ivButton1.setImageResource(mBtn1ImageResId);
        }

        // 右边起第二个按钮
        if (mBtn2ImageResId != -1) {
            // 监听点击
            mLayoutButton2.setVisibility(VISIBLE);
            mLayoutButton2.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnToolbarClickedListener != null) {
                        mOnToolbarClickedListener.onButton2Click();
                    }
                }
            });
            // 设置图片
            ImageView image = (ImageView) rootView.findViewById(R.id.iv_button2);
            image.setImageResource(mBtn2ImageResId);
        }
    }

    /**
     * 初始化选择层
     */
    private void initLayerSelect(View rootView) {
        mBtnCancel = (Button) rootView.findViewById(R.id.btn_cancel);
        mTvSelectCount = (TextView) rootView.findViewById(R.id.tv_select_count);
        mBtnSelectAll = (Button) rootView.findViewById(R.id.btn_select_all);

        // 取消
        mBtnCancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnToolbarClickedListener != null) {
                    mOnToolbarClickedListener.onCancelClick();
                }
            }
        });
        // 全选
        mBtnSelectAll.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isSelectedAll) {
                    // 全选的时候显示的是全不选
                    mBtnSelectAll.setText("全选");
                    if (mOnToolbarClickedListener != null) {
                        mOnToolbarClickedListener.onUnSelectAllClick();
                    }
                    isSelectedAll = false;
                } else {
                    mBtnSelectAll.setText("全不选");
                    if (mOnToolbarClickedListener != null) {
                        mOnToolbarClickedListener.onSelectAllClick();
                    }
                    isSelectedAll = true;
                }
            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int width = 0;
        int height = 0;

        switch (widthMode) {
            case MeasureSpec.AT_MOST:
            case MeasureSpec.EXACTLY:
                width = widthSize;
                break;
            case MeasureSpec.UNSPECIFIED:
                width = mDefaultWidth;
                break;
        }

        switch (heightMode) {
            case MeasureSpec.AT_MOST:
                height = mDefaultHeight;
                break;
            case MeasureSpec.EXACTLY:
                height = heightSize;
                break;
            case MeasureSpec.UNSPECIFIED:
                height = mDefaultHeight;
                break;
        }

        setMeasuredDimension(width, height);
    }

    public void hideButton1() {
        mLayoutButton1.setVisibility(GONE);
    }

    public void hideButton2() {
        mLayoutButton2.setVisibility(GONE);
    }

    public void showButton1() {
        mLayoutButton1.setVisibility(VISIBLE);
    }

    public void showButton2() {
        mLayoutButton2.setVisibility(VISIBLE);
    }

    public void setTitle(@NonNull String title) {
        mTitle = title;
        if (mShowReturn) {
            mTvTitleLeft.setText(title);
        } else {
            mTvTitleCenter.setText(title);
        }
    }

    public void showSelectLayout() {
        mBtnSelectAll.setText("全选");
        isSelectedAll = false;
        mLayerToolbar.setVisibility(GONE);
        mLayerSelect.setVisibility(VISIBLE);
    }

    public void hideSelectLayout() {
        mLayerToolbar.setVisibility(VISIBLE);
        mLayerSelect.setVisibility(GONE);
    }

    /**
     * 设置已经选中了多少项
     */
    public void setSelectedCount(int count) {
        if (mTvSelectCount != null) {
            mTvSelectCount.setText("已选中 " + count + " 项");
        }
    }

    // =========================================================
    // OnToolbarClickedListener
    // =========================================================

    private OnToolbarClickedListener mOnToolbarClickedListener;

    public void setOnToolbarClickedListener(OnToolbarClickedListener listener) {
        mOnToolbarClickedListener = listener;
    }

    public interface OnToolbarClickedListener {
        void onReturnClick();

        void onButton1Click();

        void onButton2Click();

        void onCancelClick();

        void onSelectAllClick();

        void onUnSelectAllClick();
    }

    public static abstract class SimpleOnToolbarClickedListener implements OnToolbarClickedListener {
        @Override
        public abstract void onReturnClick();

        /** 右边起第一个按钮 */
        @Override
        public void onButton1Click() {
        }

        /** 右边起第二个按钮 */
        @Override
        public void onButton2Click() {
        }

        @Override
        public void onCancelClick() {
        }

        @Override
        public void onSelectAllClick() {
        }

        @Override
        public void onUnSelectAllClick() {
        }
    }
}
