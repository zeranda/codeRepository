package com.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class WxAop {
    @Pointcut("execution(* com.controller.WxController.replyMessage(..))")
    public void pointCut() {
    }


    @Around("pointCut()")
    public void around(ProceedingJoinPoint pjp) {
        try {
            //获取AOP方法的参数列表
            Object[] args = pjp.getArgs();
            HttpServletRequest request = (HttpServletRequest) args[0];
            System.out.println(args.toString() + "=====");
            System.out.println("环绕前===="+request.getRequestURL().toString());
            pjp.proceed();
            System.out.println("环绕后====");
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            System.out.println("环绕异常===");
        } finally {
            System.out.println("最终环绕====");
        }
    }
}
