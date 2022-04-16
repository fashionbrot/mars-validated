package com.github.fashionbrot.javassist;

import com.github.fashionbrot.validated.util.ObjectUtil;
import javassist.util.proxy.ProxyObject;

import java.util.List;

public class ProxyFactory {




    public static void proxy(List<Class> clazzList){
        if (ObjectUtil.isNotEmpty(clazzList)){
            for (Class clazz : clazzList) {
                proxy(clazz);
            }
        }
    }

    public static Object proxy(Class clazz){
        ValidatedMethodHandler myMethodHandler = new ValidatedMethodHandler();
        javassist.util.proxy.ProxyFactory proxyFactory = new javassist.util.proxy.ProxyFactory();
        proxyFactory.setSuperclass(clazz);
        Class<?> newClazz = proxyFactory.createClass();
        Object obj = null;
        try {
            obj = newClazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        //设置拦截代理
        ((ProxyObject) obj).setHandler(myMethodHandler);

        return obj;
    }
}
