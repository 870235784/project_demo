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
public class AccountLoginResp extends ReturnBaseMessageBean{

    /**
     * token
     */
    private String token;
}
