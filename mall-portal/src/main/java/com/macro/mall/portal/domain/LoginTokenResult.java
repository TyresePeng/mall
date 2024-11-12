package com.macro.mall.portal.domain;

import lombok.*;

/**
 * @Description: 登录返回
 * @Author: peng.guo
 * @Create: 2024-11-11 18:21
 * @Version 1.0
 **/
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Builder(toBuilder = true)
@Data
public class LoginTokenResult {

    private String token;
    private String tokenHead;
}
