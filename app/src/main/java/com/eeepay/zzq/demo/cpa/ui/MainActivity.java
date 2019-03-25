package com.eeepay.zzq.demo.cpa.ui;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.alibaba.android.arouter.launcher.ARouter;
import com.eeepay.zzq.demo.cpa.R;
import com.eeepay.zzq.demo.lib_common.view._tab.AbstractCommonTabLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述：app 壳 集成了子模块的功能
 * 作者：zhuangzeqin
 * 时间: 2019/3/22-11:14
 * 邮箱：zzq@eeepay.cn
 * 备注:
 */
public class MainActivity extends AbstractCommonTabLayout {
    private String[] mTitles = {"首页", "消息", "我的"};//标题
    private int[] mIconUnselectIds = {
            R.mipmap.homepage_nor,
            R.mipmap.agency_nor,
            R.mipmap.user_nor
    };//未选中
    private int[] mIconSelectIds = {
            R.mipmap.homepage_sel,
            R.mipmap.agency_sel,
            R.mipmap.user_sel
    };//选中
    private ArrayList<Fragment> mFragments = null;//Fragment 集合

    @Override
    protected String setTitle() {
        return "Tab 通用框架";
    }

    @Override
    protected int getBarColorId() {
        return R.color.unify_txt_color_ff7e00;
    }

    @Override
    protected String[] getTitles() {
        return mTitles;
    }

    @Override
    protected int[] getIconSelectIds() {
        return mIconSelectIds;
    }

    @Override
    protected int[] getIconUnselectIds() {
        return mIconUnselectIds;
    }

    @Override
    protected ArrayList<Fragment> getFragmentList() {
        mFragments = new ArrayList<>(mTitles.length);//Fragment 集合
        // 获取Fragment
        Fragment homefragment = (Fragment) ARouter.getInstance().build("/home/HomeFragment").navigation();
        Fragment chatfragment = (Fragment) ARouter.getInstance().build("/chat/ChatFragment").navigation();
        Fragment minefragment = (Fragment) ARouter.getInstance().build("/mine/MineFragment").navigation();
        mFragments.add(homefragment);
        mFragments.add(chatfragment);
        mFragments.add(minefragment);
        return mFragments;
    }

    @Override
    protected int getCommonTabLayout() {
        return R.id.tl_2;
    }

    @Override
    protected int getCommonViewPager() {
        return R.id.vp_2;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
    }

    @Override
    public void eventOnClick() {

    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {

    }
}
