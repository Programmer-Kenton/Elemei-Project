package com.kenton.elemei.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.ldap.Control;
import java.sql.SQLIntegrityConstraintViolationException;

/**
 * @author: Kenton
 * @description 全局异常处理器
 * @date: 2022/9/3 13:36
 */
// @ControllerAdvice 配合 @ExceptionHandler 实现全局异常处理
// 拦截加入了注解@RestController的控制层
@ControllerAdvice(annotations = {RestController.class, Controller.class})
// 返回JSON数据
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 进行异常处理方法
     * @return
     */
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public R<String> exceptionHandler(SQLIntegrityConstraintViolationException ex){
        log.error(ex.getMessage());

        // 报错信息包含"Duplicate entry" 说明添加账号username(数据库中unique唯一修饰)已经存在 username也就是账号
        // 提示更详细的错误信息 帮助管理员更改
        if (ex.getMessage().contains("Duplicate entry")){
            // 获取报错信息用空格分开 这样方便取出那个用户名(账号)重复了
            String[] split = ex.getMessage().split(" ");
            String msg = split[2] + "已存在";
            return R.error(msg);
        }
        return R.error("未知错误,服务器繁忙请稍后再试...");
    }
}
