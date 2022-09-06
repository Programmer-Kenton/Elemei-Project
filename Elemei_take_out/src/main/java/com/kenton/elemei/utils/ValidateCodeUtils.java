package com.kenton.elemei.utils;

import java.util.Random;

/**
 * @author: Kenton
 * @description 随机生成验证码工具类
 * @date: 2022/9/5 9:43
 */
public class ValidateCodeUtils {

    /**
     * 随机生成验证码
     * @param length 验证码长度为4或者6
     * @return
     */
    public static Integer generateValidateCode(int length){
        Integer code = null;
        if (length == 4) {
            // 生成随机数 最大为9999
            code = new Random().nextInt(9999);
            if (code < 1000) {
                // 确保随机数为4位数字
                code = code + 1000;
            }
        } else if (length == 6) {
            // 生成随机数 最大为999999
            code = new Random().nextInt(999999);
            if(code < 100000){
                code = code + 100000;
            }
        }else {
            throw new RuntimeException("只能生成4位或者6位数字验证码");
        }
        return code;
    }

    /**
     * 随机生成指定长度字符串验证码
     * @param length
     * @return
     */
    public static String generateValidateCodeSpecifyString(int length){
        Random random = new Random();
        String hexString = Integer.toHexString(random.nextInt());
        String capStr = hexString.substring(0, length);
        return capStr;
    }
}
