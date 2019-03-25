package com.eeepay.zzq.demo.lib_common.retrofit;

import io.reactivex.disposables.Disposable;

/**
  * 描述：RxJavaAction管理接口
  * 作者：zhuangzeqin
  * 时间: 2018/8/16-22:18
  * 邮箱：zzq@eeepay.cn
  * 备注:
  */
public interface IRxActionManager<T> {
   /**
    * 添加
    *
    * @param tag
    * @param disposable
    */
   void add(T tag, Disposable disposable);

   /**
    * 移除
    *
    * @param tag
    */
   void remove(T tag);

   /**
    * 取消
    *
    * @param tag
    */
   void cancel(T tag);

}