package com.macro.mall.portal.domain;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.*;

/**
 * @Description: 微信登录实入参
 * @Author: peng.guo
 * @Create: 2024-11-08 17:33
 * @Version 1.0
 **/

@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Builder(toBuilder = true)
@Data
public class WeixinLoginParam {


    /**
     * 微信小程序appid
     */
    private String appid;

    /**
     * 微信小程序secret
     *
     */
    private String secret;

    /**
     * 登录时获取的 code，可通过wx.login获取
     */
    private String js_code;

    /**
     * 授权类型
     */
    @JSONField(name = "grant_type")
    private String grant_type="authorization_code";
}
