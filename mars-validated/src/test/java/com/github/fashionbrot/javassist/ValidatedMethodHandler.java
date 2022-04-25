//package com.github.fashionbrot.javassist;
//
//import com.github.fashionbrot.validated.annotation.Validated;
//import com.github.fashionbrot.validated.validator.DefaultValidator;
//import com.github.fashionbrot.validated.validator.MarsValidator;
//import javassist.util.proxy.MethodHandler;
//import org.springframework.beans.BeansException;
//import org.springframework.beans.factory.BeanFactory;
//import org.springframework.beans.factory.BeanFactoryAware;
//
//import java.lang.reflect.Method;
//
//public class ValidatedMethodHandler implements MethodHandler, BeanFactoryAware {
//
//    public static final String BEAN_NAME = "marsValidatedMethodHandler";
//
//    private MarsValidator validator;
//
//    @Override
//    public Object invoke(Object arg0, Method arg1, Method arg2, Object[] arg3)
//            throws Throwable {
//
//        Validated validated = arg1.getDeclaredAnnotation(Validated.class);
//        if (validated!=null) {
//            validator.parameterAnnotationValid(arg1, arg3);
//        }
//        Object result =  arg2.invoke(arg0, arg3);
//
//        if (validated.validReturnValue()) {
//            validator.returnValueAnnotationValid(validated, result);
//        }
//        return result;
//    }
//
//    @Override
//    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
//        if (beanFactory!=null){
//            this.validator = (DefaultValidator) beanFactory.getBean(DefaultValidator.BEAN_NAME);
//        }
//    }
//}
