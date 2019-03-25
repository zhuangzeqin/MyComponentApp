package com.eeepay.zzq.demo.lib_common.base;

import pub.devrel.easypermissions.EasyPermissions;

/**
 * 描述：抽象出接口
 * 作者：zhuangzeqin
 * 时间: 2018/7/10-17:51
 * 邮箱：zzq@eeepay.cn
 * 备注:
 */
public interface BaseViewInfterface extends EasyPermissions.PermissionCallbacks {
    /**
     * @return 布局id
     */
    int getLayoutId();

    /**
     * 初始化控件
     */
    void initView();

    /**
     * 事件绑定
     */
    void eventOnClick();

    /**
     * 显示loading界面
     **/
    void showLoading();

    /**
     * 隐藏loading界面
     **/
    void hideLoading();

    /**
     * 提示错误信息
     **/
    void showError(String error);
}
