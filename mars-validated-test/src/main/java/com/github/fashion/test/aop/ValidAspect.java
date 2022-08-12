package com.github.fashion.test.aop;

import com.github.fashionbrot.validated.validator.MarsValidatorImpl;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

//@Component
//@Aspect
public class ValidAspect {

    @Pointcut("execution(* com.github.fashion.test.service.*.*(..))")
    private void pointcut() {}

    @Autowired
    private MarsValidatorImpl marsValidator;

    @Autowired
    private HttpServletRequest request;

    @Around(value = "pointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("---------------aop begin");
        //打印方法的信息
        Object[] args = joinPoint.getArgs();
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature)signature;
        //自定义参数验证
        marsValidator.parameterAnnotationValid(methodSignature.getMethod(),args);

        return joinPoint.proceed();
    }

}
