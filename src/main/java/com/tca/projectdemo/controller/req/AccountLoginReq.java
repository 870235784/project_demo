package com.tca.projectdemo.controller.req;

import lombok.Data;
import lombok.ToString;

/**
 * @author zhoua
 * @Date 2020/2/27
 */
@Data
@ToString
public class AccountLoginReq {

    /**
     * 用户名
     */
    private String loginName;

    /**
     * 密码
     */
    private String password;
}
