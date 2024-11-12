package com.macro.mall.portal.service;

import com.macro.mall.portal.domain.LoginTokenResult;

/**
 * 微信用户Service
 *
 * @author macro
 * @date 2018/8/30
 */
public interface WeChatUserService {

    /**
     * 登录
     * @param jsCode 微信登录凭证
     * @return LoginTokenResult
     */
    LoginTokenResult login(String jsCode);
}
