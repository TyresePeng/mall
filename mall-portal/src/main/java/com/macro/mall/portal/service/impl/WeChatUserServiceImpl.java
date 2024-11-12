package com.macro.mall.portal.service.impl;

import com.macro.mall.common.exception.Asserts;
import com.macro.mall.common.exception.BusinessException;
import com.macro.mall.model.UmsMember;
import com.macro.mall.portal.domain.LoginTokenResult;
import com.macro.mall.portal.domain.WeixinLoginResponse;
import com.macro.mall.portal.openfeign.WeixinFeign;
import com.macro.mall.portal.service.UmsMemberService;
import com.macro.mall.portal.service.WeChatUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @Description: 微信用户业务实现
 * @Author: peng.guo
 * @Create: 2024-11-11 18:23
 * @Version 1.0
 **/
@Service
public class WeChatUserServiceImpl implements WeChatUserService {

    @Resource
    private WeixinFeign weixinFeign;

    @Resource
    private UmsMemberService memberService;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Override
    public LoginTokenResult login(String jsCode) {
        WeixinLoginResponse weixinLoginResponse = weixinFeign.jscode2session(jsCode);
        if(weixinLoginResponse.isSuccess()){
            UmsMember member = memberService.getByUsername(weixinLoginResponse.getOpenid());
            if(Objects.isNull(member)){
                memberService.register(weixinLoginResponse.getOpenid(),weixinLoginResponse.getOpenid());
            }
            String token = memberService.login(weixinLoginResponse.getOpenid(), weixinLoginResponse.getOpenid());
            return LoginTokenResult.builder().token(token).tokenHead(tokenHead).build();
        }else {
            throw new BusinessException("微信登录异常");
        }
    }
}
