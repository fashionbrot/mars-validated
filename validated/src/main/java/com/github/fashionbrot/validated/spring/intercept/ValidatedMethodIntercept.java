package com.github.fashionbrot.validated.spring.intercept;

import com.github.fashionbrot.validated.annotation.Validated;
import com.github.fashionbrot.validated.validator.SpvValidator;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.reflect.Method;

public class ValidatedMethodIntercept implements MethodInterceptor, BeanFactoryAware {

    public static final String BEAN_NAME = "validatedMethodIntercept";

    private SpvValidator validator;

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        Object[] params=methodInvocation.getArguments();
        Method method=methodInvocation.getMethod();
        Validated validated =AnnotationUtils.findAnnotation(methodInvocation.getMethod(),Validated.class);
        if (validated!=null) {
            validator.parameterAnnotationValid(method, params);
        }
        return methodInvocation.proceed();
    }




    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        if (beanFactory!=null){
            this.validator = (SpvValidator) beanFactory.getBean(SpvValidator.BEAN_NAME);
        }
    }
}
