package com.macro.mall.common.openfeign;

import com.alibaba.fastjson.JSONObject;
import feign.Logger;
import feign.RequestTemplate;
import feign.codec.Decoder;
import lombok.extern.log4j.Log4j2;
import net.logstash.logback.util.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.net.URL;


/**
 * @Description:
 * @Author: peng.guo
 * @Create: 2024-11-08 15:12
 * @Version 1.0
 **/
@Log4j2
@Component
public class DefaultFeignInterceptor {

    @Bean
    public Logger.Level feignLoggerLevel(){
        return Logger.Level.FULL;
    }


    @Bean
    public Decoder feignDecoder() {
        return new CustomFeignDecoder();
    }

}
