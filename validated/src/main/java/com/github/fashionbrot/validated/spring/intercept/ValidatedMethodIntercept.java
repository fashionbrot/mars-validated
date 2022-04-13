package com.github.fashionbrot.validated.spring.intercept;

import com.github.fashionbrot.validated.annotation.Validated;
import com.github.fashionbrot.validated.util.ExceptionUtil;
import com.github.fashionbrot.validated.validator.DefaultValidator;
import com.github.fashionbrot.validated.validator.MarsValidator;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import java.lang.reflect.Method;

@Slf4j
public class ValidatedMethodIntercept implements MethodInterceptor, BeanFactoryAware {

    public static final String BEAN_NAME = "marsValidatedMethodIntercept";

    private MarsValidator validator;

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {

        Object[] params=methodInvocation.getArguments();
        Method method=methodInvocation.getMethod();
        Validated validated=method.getDeclaredAnnotation(Validated.class);
        if (validated!=null) {
            validator.parameterAnnotationValid(method, params);
        }

        Object object =  methodInvocation.proceed();
        if (validated.validReturnValue()) {
            validator.returnValueAnnotationValid(validated, object);
        }
        return object;
    }




    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        if (beanFactory!=null){
            this.validator = (MarsValidator) beanFactory.getBean(DefaultValidator.BEAN_NAME);
        }
    }
}
