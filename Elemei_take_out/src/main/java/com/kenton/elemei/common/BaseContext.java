package com.kenton.elemei.common;

/**
 * @author: Kenton
 * @description 基于ThreadLocal封装工具 用户保存和获取当前登录用户id
 * @date: 2022/9/5 10:08
 */
public class BaseContext {

    private static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    /**
     * 设置值
     * @param id
     */
    public static void setCurrentId(Long id){
        threadLocal.set(id);
    }

    /**
     * 获取值
     * @return
     */
    public static Long getCurrentId(){
        return threadLocal.get();
    }
}
