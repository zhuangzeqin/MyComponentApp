package com.eeepay.zzq.demo.lib_common.view._tab;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;

import java.util.List;

/**
 * 描述：封装抽象的TabLayout 基类 方便以后使用
 * 作者：zhuangzeqin
 * 时间: 2017/8/30-9:11
 * 邮箱：zzq@eeepay.cn
 */
public abstract class BaseTabLayoutActivity extends FragmentActivity {
    protected TabLayout mTab_layout;//tag_Layout 组件
    protected ViewPager mViewpager;//ViewPage 组件
    protected Context mContext;//上下文对象
    protected List<Fragment> mFragmentTab;//Fragment 集合
    protected BaseViewPageStateAdapter mAdapter;//适配器

    private static final int DEFAULT_OFFSCREEN_PAGES = 2;//预加载值2

    private int limit = DEFAULT_OFFSCREEN_PAGES;//预加载值

    protected abstract int getLayoutId();//布局id

    protected abstract int getTabLayoutID();//tag_Layout 组件id

    protected abstract int getViewPagerID();//ViewPage 组件id

    protected abstract String[] getTitleArray();//标题 数组

    protected abstract List<Fragment> getFragmentTabList();//要显示的内容Fragment


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(getLayoutId());
        initTab();
        eventOnClick();
    }

    public void initTab() {
        mFragmentTab = getFragmentTabList();
        mAdapter = new BaseViewPageStateAdapter(getSupportFragmentManager());
        mAdapter.setListFragments(mFragmentTab);//填充数据
        mAdapter.setTitles(getTitleArray());//设置标题
        mTab_layout = getViewById(getTabLayoutID());
        mViewpager = getViewById(getViewPagerID());
        mViewpager.setAdapter(mAdapter);
        mViewpager.setOffscreenPageLimit(limit);
//        viewpager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tab_layout));
        mTab_layout.setupWithViewPager(mViewpager);//关联viewPage
        mTab_layout.setTabGravity(TabLayout.GRAVITY_FILL);
        mTab_layout.setTabMode(TabLayout.MODE_FIXED);
    }
    public void eventOnClick(){

    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public <T extends View> T getViewById(int id) {
        View view = findViewById(id);
        return (T) view;
    }

}
