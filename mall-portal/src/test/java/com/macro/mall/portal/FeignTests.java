package com.macro.mall.portal;

import com.alibaba.fastjson.JSONObject;
import com.macro.mall.portal.controller.WeChatUserController;
import com.macro.mall.portal.openfeign.WeixinFeign;
import feign.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @Description: feignTest
 * @Author: peng.guo
 * @Create: 2024-11-08 16:15
 * @Version 1.0
 **/
@SpringBootTest
public class FeignTests {

    @Resource
    private WeChatUserController weChatUserController;
    @Test
    public void contextLoads() {

    }
}
