package com.github.fashionbrot.handler;

import com.github.fashionbrot.validated.validator.MarsValidatorImpl;
import javassist.util.proxy.MethodHandler;

import java.lang.reflect.Method;

public class MyMethodHandler implements MethodHandler {


    @Override
    public Object invoke(Object arg0, Method arg1, Method arg2, Object[] arg3)
            throws Throwable {
        System.out.println("我来自代理");

        MarsValidatorImpl marsValidatorImpl =new MarsValidatorImpl();

        marsValidatorImpl.parameterAnnotationValid(arg1,arg3);

        Object result =  arg2.invoke(arg0, arg3);

        return result;
    }

}
