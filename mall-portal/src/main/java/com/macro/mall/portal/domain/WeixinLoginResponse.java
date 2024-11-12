package com.macro.mall.portal.domain;

import lombok.Data;

/**
 * @Description: 微信开放平台响应
 * @Author: peng.guo
 * @Create: 2024-11-08 17:41
 * @Version 1.0
 **/
@Data
public class WeixinLoginResponse {
    private String openid;
    private String session_key;
    private String unionid;
    private int errcode;
    private String errmsg;

    public boolean isSuccess(){
        return errcode==0;
    }
}
