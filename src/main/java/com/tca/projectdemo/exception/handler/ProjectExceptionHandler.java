package com.tca.projectdemo.exception.handler;

import com.tca.beans.ErrorCode;
import com.tca.beans.ReturnBaseMessageBean;
import com.tca.projectdemo.exception.ProjectException;
import com.tca.utils.WebBaseUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author zhoua
 * @Date 2020/2/27
 */
@ControllerAdvice
@Slf4j
@ResponseBody
public class ProjectExceptionHandler {


    @ExceptionHandler(ProjectException.class)
    public ReturnBaseMessageBean projectExceptionHandler(ProjectException e) {
        log.error("服务异常: {}, {}", e.getReturnCode(), e.getReturnMessage());
        ReturnBaseMessageBean resultBean = new ReturnBaseMessageBean();
        resultBean.setReturnMessage(e.getReturnMessage());
        resultBean.setReturnCode(e.getReturnCode());
        return resultBean;
    }


    @ExceptionHandler(Exception.class)
    public ReturnBaseMessageBean exceptionHandler(Exception e) {
        log.error("全局异常: {}", e);
        ReturnBaseMessageBean resultBean = new ReturnBaseMessageBean();
        WebBaseUtils.setReturnBaseMessage(resultBean, ErrorCode.S9999);
        return resultBean;
    }
}
