package com.eeepay.zzq.demo.lib_common.view._tab;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述：封装抽象出ViewPage 的适配器 方便以后使用
 * 作者：zhuangzeqin
 * 时间: 2017/8/30-9:36
 * 邮箱：zzq@eeepay.cn
 */
public class BaseViewPageStateAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> mFragmentTab = new ArrayList<>();

    private String[] mTitles;

    public BaseViewPageStateAdapter(FragmentManager fm) {
        super(fm);
    }

    /**
     * 填充显示的Fragment
     * @param list
     */
    public void setListFragments(List<Fragment> list) {
        mFragmentTab.clear();
        mFragmentTab.addAll(list);
        notifyDataSetChanged();
    }

    /**
     * 设置标题
     * @param mTitles
     */
    public void setTitles(String[] mTitles) {
        this.mTitles = mTitles;
    }


    /**
     * 获取Fragment 实例
     *
     * @param position
     * @return
     */
    @Override
    public Fragment getItem(int position) {
        return mFragmentTab.get(position);
    }


    /**
     * 数量
     *
     * @return
     */
    @Override
    public int getCount() {
        return mFragmentTab.size();
    }

    /**
     * 页面标题
     *
     * @param position
     * @return
     */
    @Override
    public CharSequence getPageTitle(int position) {
        if (mTitles==null)
            throw new RuntimeException("String[] is null,you can must instantiation.");

       return mTitles[position];
    }
}
