package com.eeepay.zzq.demo.lib_common.view;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * 描述：自定义圆环进度
 * 作者：zhuangzeqin
 * 时间: 2018/9/29-10:35
 * 邮箱：zzq@eeepay.cn
 * 备注:
 */
public class CustomRound extends View {
    public CustomRound(Context context) {
        super(context);
    }

    public CustomRound(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomRound(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    /** ------注释说明--测量布局------ **/
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
