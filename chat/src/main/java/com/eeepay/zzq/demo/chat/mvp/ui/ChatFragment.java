package com.eeepay.zzq.demo.chat.mvp.ui;

import android.support.annotation.NonNull;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.eeepay.zzq.demo.chat.R;
import com.eeepay.zzq.demo.lib_common.base.BaseFragment;

import java.util.List;

/**
 * 描述：class describe
 * 作者：zhuangzeqin
 * 时间: 2019/3/22-14:12
 * 邮箱：zzq@eeepay.cn
 * 备注:
 */
@Route(path = "/chat/ChatFragment")
public class ChatFragment extends BaseFragment {

    @Override
    public int getLayoutId() {
        return R.layout.fragment_chat;
    }

    @Override
    public void initView() {

    }

    @Override
    public void eventOnClick() {

    }

    @Override
    protected void init() {

    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {

    }
}
