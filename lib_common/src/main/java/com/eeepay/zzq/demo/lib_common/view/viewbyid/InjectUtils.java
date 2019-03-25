package com.eeepay.zzq.demo.lib_common.view.viewbyid;

import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 描述：注解的工具类
 * 作者：zhuangzeqin
 * 时间: 2018/8/3-16:42
 * 邮箱：zzq@eeepay.cn
 * 备注:
 */
public class InjectUtils {

    private InjectUtils() {
    }

    private static class SingletonHolder {
        private static final InjectUtils INSTANCE = new InjectUtils();
    }

    public static InjectUtils getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * 绑定当前Activity
     *
     * @param activity
     */
    public void inject(Activity activity) {
        //绑定控件
        bindView(activity);
        //点击事件的绑定
        bindOnClickEvent(activity);
        //注解长按事件
        bindOnLongClickEvent(activity);
    }

    private void bindView(Activity activity) {
        Class<? extends Activity> aClass = activity.getClass();
        //获取到这个类的所有的成员字段
        Field[] declaredFields = aClass.getDeclaredFields();
        //遍历所有的字段
        for (int i = 0; i < declaredFields.length; i++) {
            ViewById annotation = declaredFields[i].getAnnotation(ViewById.class);
            //如果没有这个注解，跳过这个成员字段
            if (annotation == null)
                continue;
            //获取注解的值,在这里就是控件的id
            int resId = annotation.value();
            //得到这个控件
            View view = activity.findViewById(resId);
            try {
                //暴力反射，如果不设置这个，那么如果成员是private的话，就不能进行绑定
                declaredFields[i].setAccessible(true);
                //将这个控件跟activity绑定
                declaredFields[i].set(activity, view);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 注解长按事件
     *
     * @param activity
     */
    private void bindOnLongClickEvent(final Activity activity) {
        Class<? extends Activity> aClass = activity.getClass();
        //获取所有的方法
        Method[] declaredMethods = aClass.getDeclaredMethods();
        //遍历所有的方法
        for (final Method method : declaredMethods) {
            //获取方法上的OnLongClickEvent注解
            OnLongClickEvent annotation = method.getAnnotation(OnLongClickEvent.class);
            if (annotation == null)
                continue;
            //获得资源id
            int[] resId = annotation.value();
            for (int i = 0; i < resId.length; i++) {
                final View view = activity.findViewById(resId[i]);
                view.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        try {
                            //暴力反射
                            method.setAccessible(true);
                            method.invoke(activity, view);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        }
                        return false;
                    }
                });
            }
        }
    }

    /**
     * item 的 点击事件
     * @param activity
     */
    private void bindOnItemClickEvent(final Activity activity)
    {
        Class<? extends Activity> aClass = activity.getClass();
//        aClass.getAnnotationsByType(OnItemClickEvent.class);
//        aClass.getDeclaredAnnotationsByType()方法获取到的注解不包括父类，
        boolean annotationPresent = aClass.isAnnotationPresent(OnItemClickEvent.class);
        if (annotationPresent)
        {
            //获取所有的方法
            Method[] declaredMethods = aClass.getDeclaredMethods();
            //遍历所有的方法
            for (final Method method : declaredMethods) {
                //获取方法上的OnClick注解
                OnItemClickEvent onClick = method.getAnnotation(OnItemClickEvent.class);
                if (onClick == null)
                    continue;
                int[] resId = onClick.value();
                for (int i = 0; i < resId.length; i++) {
                    final ListView view = (ListView)activity.findViewById(resId[i]);
                    view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            try {
                                //暴力反射
                                method.setAccessible(true);
                                method.invoke(activity, adapterView,view,i,l);
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            } catch (InvocationTargetException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        }
    }
    /**
     * 注解点击事件
     *
     * @param activity
     */
    private void bindOnClickEvent(final Activity activity) {
        Class<? extends Activity> aClass = activity.getClass();
        //获取所有的方法
        Method[] declaredMethods = aClass.getDeclaredMethods();
        //遍历所有的方法
        for (final Method method : declaredMethods) {
            //获取方法上的OnClick注解
            OnClickEvent onClick = method.getAnnotation(OnClickEvent.class);
            if (onClick == null)
                continue;
            int[] resId = onClick.value();
            for (int i = 0; i < resId.length; i++) {
                final View view = activity.findViewById(resId[i]);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            //暴力反射
                            method.setAccessible(true);
                            method.invoke(activity, view);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }
    }
}
