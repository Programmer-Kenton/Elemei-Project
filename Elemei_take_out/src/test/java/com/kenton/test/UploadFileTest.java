package com.kenton.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author: Kenton
 * @description 测试文件上传的文件名后缀截取
 * @date: 2022/9/4 10:37
 */
@Slf4j
public class UploadFileTest {

    @Test
    public void test01(){
        String fileName = "123.jpg";
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        System.out.println(suffix);
        log.info("{}",suffix);
    }
}
