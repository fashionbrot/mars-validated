package com.github.fashionbrot.validated.spring.intercept;

import com.github.fashionbrot.validated.annotation.Validated;
import com.github.fashionbrot.validated.util.ValidatorUtil;
import com.github.fashionbrot.validated.validator.SpvValidator;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

@Slf4j
public class ValidatedMethodIntercept implements MethodInterceptor, BeanFactoryAware {

    public static final String BEAN_NAME = "validatedMethodIntercept";

    private SpvValidator validator;

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        long start = System.currentTimeMillis();
        Object[] params=methodInvocation.getArguments();
        Method method=methodInvocation.getMethod();

        if (ValidatorUtil.getMethod(method)!=null){
            validator.parameterAnnotationValid(method, params);
        }else {
            Validated validated= AnnotationUtils.findAnnotation(method,Validated.class);
            if (validated!=null) {
                validator.parameterAnnotationValid(method, params);
            }
        }
        if (log.isDebugEnabled()) {
            log.debug("valid time:" , (System.currentTimeMillis() - start));
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
