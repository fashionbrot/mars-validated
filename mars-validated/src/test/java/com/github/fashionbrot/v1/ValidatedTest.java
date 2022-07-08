package com.github.fashionbrot.v1;

import com.github.fashionbrot.handler.MyMethodHandler;
import com.github.fashionbrot.javassist.ResolverUtil;
import com.github.fashionbrot.validated.annotation.Validated;
import com.github.fashionbrot.validated.util.ObjectUtil;
import javassist.util.proxy.ProxyFactory;
import javassist.util.proxy.ProxyObject;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

public class ValidatedTest {


    @Test
    public void search() throws IOException {
        List<String> list = ResolverUtil.list("com/github/fashionbrot/v1");

        Map<Class,Method[]> map=new HashMap<>();
        List<Class> proxyList = new ArrayList<>();
        List<Class<?>> classList = ResolverUtil.toClass(list);
        if (ObjectUtil.isNotEmpty(classList)){
            classList.forEach(clazz->{
                Method[] declaredMethods = clazz.getDeclaredMethods();
                if (ObjectUtil.isNotEmpty(declaredMethods)){
                    Arrays.stream(declaredMethods).forEach(method -> {
                        if (method.getDeclaredAnnotation(Validated.class)!=null){
                            System.out.println(method.getName());
                            if (!map.containsKey(clazz)) {
                                map.put(clazz, declaredMethods);
                            }
                            proxyList.add(clazz);
                        }
                    });
                }
            });
        }

        List<Class> collect = proxyList.stream().distinct().collect(Collectors.toList());

        Object obj = null;
        if (ObjectUtil.isNotEmpty(collect)){

            MyMethodHandler myMethodHandler = new MyMethodHandler();
            for (Class clazz : collect) {
                ProxyFactory proxyFactory = new ProxyFactory();

                proxyFactory.setSuperclass(clazz);

                Class<?> newClazz = proxyFactory.createClass();

                Object testUser = null;
                try {
                    testUser = newClazz.newInstance();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                obj = testUser;

                //设置拦截代理
                ((ProxyObject) testUser).setHandler(myMethodHandler);
            }
        }

        System.out.println("代理完成");


    }

}
