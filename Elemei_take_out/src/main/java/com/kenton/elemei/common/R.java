package com.kenton.elemei.common;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: Kenton
 * @description 通用返回结果类
 * @date: 2022/9/3 10:17
 */

@Data
public class R<T> {

    // 编码:1 成功 , 0和其他代表失败
    private Integer code;

    // 错误信息
    private String msg;

    // 数据
    private T data;

    // 动态数据
    private Map map = new HashMap();

    // 成功
    public static <T> R<T> success(T object){
        R<T> r = new R<T>();
        r.data = object;
        r.code = 1;
        return r;
    }

    // 失败
    public static <T> R<T> error(String msg){
        R r = new R();
        r.msg = msg;
        r.code = 0;
        return r;
    }

    // 操作动态数据
    public R<T> add(String key,Object value){
        this.map.put(key,value);
        return this;
    }
}
