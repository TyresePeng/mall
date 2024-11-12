package com.macro.mall.portal.openfeign.Interceptor;

import com.alibaba.fastjson.JSONObject;
import com.macro.mall.common.exception.ApiException;
import com.macro.mall.common.openfeign.BaseFeignInterceptor;
import com.macro.mall.common.openfeign.CustomFeignDecoder;
import com.macro.mall.common.service.RedisService;
import com.macro.mall.portal.domain.WeixinTokenResult;
import feign.RequestInterceptor;
import feign.RequestTemplate;

import feign.Response;
import feign.codec.Decoder;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.util.StreamUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @Author: peng.guo
 * @Create: 2024-11-08 15:12
 * @Version 1.0
 **/
@Configuration
@Log4j2
public class WeixinFeignInterceptor extends BaseFeignInterceptor {

    @Value("${open-api.weixin-open-api.appid}")
    private String appid;

    @Value("${open-api.weixin-open-api.secret}")
    private String secret;

    @Resource
    private RedisService redisService;


    //需要拦截的url
    private static final List<String> INCLUDE_URL =new ArrayList<String>(){
        {
            add("/sns/jscode2session");
        }
    };

    /**
     * feign 请求拦截器
     */
    @Bean
    public RequestInterceptor feignRequestInterceptor() {
        return new WeixinFeignInterceptor.FeignRequestInterceptor().setAppid(appid).setSecret(secret);
    }

    /**
     * feign 响应拦截器
     */
    @Bean
    public Decoder feignResponseInterceptor() {
        return new WeixinFeignInterceptor.FeignResponseInterceptor();
    }

    /**
     * 实现拦截器ResponseInterceptor
     */
    static class FeignResponseInterceptor extends CustomFeignDecoder{
        @Override
        public Object decode(Response response, Type type) throws IOException {
            String body = StreamUtils.copyToString(response.body().asInputStream(), StandardCharsets.UTF_8);
            if(response.request().url().contains("/cgi-bin/token")){
                if(response.status()== HttpStatus.OK.value()){
                    WeixinTokenResult weixinTokenResult = JSONObject.parseObject(body, WeixinTokenResult.class);
                }else {
                   throw new ApiException("获取微信token异常");
                }
            }
            return super.decode(response, type);
        }
    }

    /**
     * 实现拦截器RequestInterceptor
     */
    static class FeignRequestInterceptor implements RequestInterceptor {
        private String appid;
        private String secret;
        @Override
        public void apply(RequestTemplate template) {
            String url = template.url();
            JSONObject params = getParamsFromURL(url);
            //登录接口
            INCLUDE_URL.stream().filter(url::contains).findAny().ifPresent(iter->{
                if(params!=null){
                    String jsCode = params.getString("jsCode");
                    template.query("appid",appid);
                    template.query("secret",secret);
                    template.query("js_code",jsCode);
                }
            });
            //获取token接口
            if(url.contains("/cgi-bin/token")){
                template.query("appid",appid);
                template.query("secret",secret);
                template.query("grant_type","client_credential");
            }
        }

        public FeignRequestInterceptor  setAppid(String appid){
            this.appid = appid;
            return this;
        }
        public FeignRequestInterceptor  setSecret(String secret){
            this.secret = secret;
            return this;
        }
    }

}
