package com.eeepay.zzq.demo.lib_common.view.recyclerview;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * 描述：自定义RecyclerView 线性布局，避免在使用的时候重复设置一些RecyclerView 的基本属性
 * 有自己需要的可以自己去扩展； 目前基本可以满足类似ListView 列表显示
 * 作者：zhuangzeqin
 * 时间: 2017/7/24-17:12
 * 邮箱：zzq@eeepay.cn
 */
public class CommonLinerRecyclerView extends RecyclerView {
    public CommonLinerRecyclerView(Context context) {
        this(context, null);
    }


    public CommonLinerRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CommonLinerRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        inintView(context);
    }

    private void inintView(Context context) {
        //设置固定大小
        setHasFixedSize(true);
        //创建线性布局
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(context);
        mLayoutManager.setAutoMeasureEnabled(true);
        //垂直方向
        mLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        //给RecyclerView设置布局管理器
        setLayoutManager(mLayoutManager);
        //添加默认的动画效果
        setItemAnimator(new DefaultItemAnimator());
        //设置分割线
        addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL_LIST));
    }
    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);
    }
}
