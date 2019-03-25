package com.eeepay.zzq.demo.lib_common.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.RequiresApi;

import pub.devrel.easypermissions.EasyPermissions;

/**
 * 描述：查看权限是否已申请 已经具有所需的权限
 * 作者：zhuangzeqin
 * 时间: 2018/7/16-11:28
 * 邮箱：zzq@eeepay.cn
 * 备注:
 */
public final class PermissionsLogUtils {
    private static StringBuffer logStringBuffer = new StringBuffer();

    // 查看权限是否已申请
    @RequiresApi(api = Build.VERSION_CODES.M)
    public static String checkPermissions(Context context, String... permissions) {
        logStringBuffer.delete(0, logStringBuffer.length());
        for (String permission : permissions) {
            logStringBuffer.append(permission);
            logStringBuffer.append(" is applied? \n     ");
            logStringBuffer.append(isAppliedPermission(context, permission));
            logStringBuffer.append("\n\n");
        }
        return logStringBuffer.toString();
    }

    //使用EasyPermissions查看权限是否已申请
    public static String easyCheckPermissions(Context context, String... permissions) {
        logStringBuffer.delete(0, logStringBuffer.length());
        for (String permission : permissions) {
            logStringBuffer.append(permission);
            logStringBuffer.append(" is applied? \n     ");
            logStringBuffer.append(EasyPermissions.hasPermissions(context, permission));
            logStringBuffer.append("\n\n");
        }
        return logStringBuffer.toString();
    }

    // 查看权限是否已申请
    @RequiresApi(api = Build.VERSION_CODES.M)
    private static boolean isAppliedPermission(Context context, String permission) {
        return context.checkSelfPermission(permission) ==
                PackageManager.PERMISSION_GRANTED;
    }
}
