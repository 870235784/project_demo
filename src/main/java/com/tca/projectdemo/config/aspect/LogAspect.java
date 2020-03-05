package com.tca.projectdemo.config.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author zhoua
 * @Date 2020/3/5
 * 日志切面
 */
@Aspect
@Component
@Slf4j
public class LogAspect {

    private static final String POINT_CUT = "execution(* com.tca.projectdemo.controller..*.*(..))";

    /**
     * 切入点:
     *  1.定义为private
     *  2.无实现
     *  3.使用pointcut
     */
    @Pointcut(POINT_CUT)
    private void pointcut(){}

   /* @Before(value = "pointcut()")
    public void before(JoinPoint joinPoint) {
        log.info("开始执行方法: {}, 入参: {}", joinPoint.getSignature(), joinPoint.getArgs());
    }*/

    @Around(value = "pointcut()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) {
        try {
            long startTime = System.currentTimeMillis();
            Object result = proceedingJoinPoint.proceed();
            long endTime = System.currentTimeMillis();
            log.info("execute:[{}], in:[{}], out:[{}], time:[{} ms]", proceedingJoinPoint.getSignature(),
                    proceedingJoinPoint.getArgs(), result, endTime - startTime);
            return result;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return null;
    }
}
