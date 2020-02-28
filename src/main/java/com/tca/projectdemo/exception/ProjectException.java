package com.tca.projectdemo.exception;

import lombok.Data;
import lombok.ToString;

/**
 * @author zhoua
 * @Date 2020/2/27
 */
@Data
@ToString
public class ProjectException extends Exception {

    /**
     * 错误码
     */
    private String returnCode;

    /**
     * 错误消息
     */
    private String returnMessage;

    public ProjectException(String returnCode, String returnMessage) {
        this.returnCode = returnCode;
        this.returnMessage = returnMessage;
    }


}
