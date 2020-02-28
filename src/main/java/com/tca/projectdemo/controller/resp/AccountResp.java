package com.tca.projectdemo.controller.resp;

import com.tca.beans.ReturnBaseMessageBean;
import lombok.Data;
import lombok.ToString;

/**
 * @author zhoua
 * @Date 2020/2/27
 */
@Data
@ToString
public class AccountResp extends ReturnBaseMessageBean{

    /**
     * id主键
     */
    private Integer id;

    /**
     * 用户名
     */
    private String loginName;

    /**
     * 密码
     */
    private String password;

    /**
     * 状态(0禁用,1可用)
     */
    private Byte status;

}
