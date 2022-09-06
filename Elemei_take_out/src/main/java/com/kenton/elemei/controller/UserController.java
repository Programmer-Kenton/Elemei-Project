package com.kenton.elemei.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.kenton.elemei.common.R;
import com.kenton.elemei.entity.User;
import com.kenton.elemei.service.UserService;
import com.kenton.elemei.utils.SMSUtils;
import com.kenton.elemei.utils.ValidateCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @author: Kenton
 * @description 用户控制层
 * @date: 2022/9/5 9:41
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 发送手机验证码
     * @param user
     * @return
     */
    @PostMapping("/sendMsg")
    public R<String> sendMsg(@RequestBody User user, HttpSession session){
        // 获取手机号
        String phone = user.getPhone();

        if(StringUtils.isNotEmpty(phone)){
            // 生成随机的4位验证码
            String  code = ValidateCodeUtils.generateValidateCode(4).toString();
            // 打印服务器本地生成的验证码日志
            log.info("本次验证码 = {}",code);
            // 调用阿里云提供的短信服务API完成发送短信
            // SMSUtils.sendMessage("饿了美外卖","","17622339366","xxxx");

            // 将生成的验证码保存到Session中
            session.setAttribute(phone,code);

            return R.success("手机验证码短信发送成功");
        }

        return R.error("短信验证发送失败,请稍后再试...");

    }

    /**
     * 移动端用户登录
     * @param map
     * @param session
     * @return
     */
    @PostMapping("/login")
    // TODO 还可以使用UserDto继承User类后 新增属性 同时接收phone和code值的集合
    public R<User> login(@RequestBody Map map, HttpSession session){

        log.info(map.toString());

        // 从map中获取用户手机号
        String phone = map.get("phone").toString();

        // 获取验证码
        String code = map.get("code").toString();

        // 从Session中获取保存的验证码
        Object codeSession = session.getAttribute(phone);

        // 进行验证码的比对
        if (codeSession != null && codeSession.equals(code)) {
            // 如果比对成功 说明登录成功

            // 判断当前手机号对应的用户是否是新用户 如果是新用户就自动完成注册
            LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
            userLambdaQueryWrapper.eq(User::getPhone,phone);
            User user = userService.getOne(userLambdaQueryWrapper);
            if (user == null) {
                // 数据库的user表的电话查不到 说明此号码需要注册
                user = new User();
                user.setPhone(phone);
                user.setStatus(1);
                userService.save(user);
            }

            // 将用户id保存到本次会话的Session中 拦截器不会拦截登录
            session.setAttribute("user",user.getId());
            return R.success(user);
        }

        return R.error("登录失败失败,请稍后再试...");
    }
}
