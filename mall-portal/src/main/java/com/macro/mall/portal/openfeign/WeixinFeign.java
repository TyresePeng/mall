package com.macro.mall.portal.openfeign;

import com.macro.mall.portal.domain.WeixinLoginResponse;
import com.macro.mall.portal.domain.WeixinTokenResult;
import com.macro.mall.portal.openfeign.Interceptor.WeixinFeignInterceptor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @Description: 微信开发平台请求
 * @Author: peng.guo
 * @Create: 2024-11-08 14:55
 * @Version 1.0
 **/
@FeignClient(name = "${open-api.weixin-open-api.service-name}", url = "${open-api.weixin-open-api.url}", configuration = WeixinFeignInterceptor.class)
public interface WeixinFeign {

    @GetMapping(value = "/sns/jscode2session")
    WeixinLoginResponse jscode2session(@RequestParam String jsCode);


    @GetMapping(value = "/cgi-bin/token")
    WeixinTokenResult getToken();


    @PostMapping(value = "/wxa/business/getuserphonenumber")
    WeixinTokenResult getUserPhoneNumber(@RequestBody String access_token);

}


