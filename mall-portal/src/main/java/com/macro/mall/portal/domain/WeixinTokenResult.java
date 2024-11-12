package com.macro.mall.portal.domain;

import lombok.Data;

/**
 * @Description: 微信获取token返回
 * @Author: peng.guo
 * @Create: 2024-11-12 16:44
 * @Version 1.0
 **/
@Data
public class WeixinTokenResult {

    private String access_token;
    private String expires_in;
}
