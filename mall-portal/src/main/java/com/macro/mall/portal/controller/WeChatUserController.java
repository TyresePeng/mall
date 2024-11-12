package com.macro.mall.portal.controller;

import com.macro.mall.common.api.CommonResult;
import com.macro.mall.portal.domain.LoginTokenResult;
import com.macro.mall.portal.service.WeChatUserService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Description: 微信用户相关
 * @Author: peng.guo
 * @Create: 2024-11-06 18:49
 * @Version 1.0
 **/
@Controller
@Api(tags = "WeChatUserController")
@Tag(name = "WeChatUserController", description = "微信用户api相关")
@RequestMapping("/weixin")
public class WeChatUserController {

    @Resource
    private WeChatUserService weChatUserService;

    @PostMapping("/login")
    @ResponseBody
    public CommonResult<LoginTokenResult> login(String jsCode) {
        return CommonResult.success(weChatUserService.login(jsCode));
    }


}
