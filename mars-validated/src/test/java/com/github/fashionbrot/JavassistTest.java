package com.github.fashionbrot;

import com.github.fashionbrot.validated.util.ObjectUtil;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;
import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.Proxy;
import javassist.util.proxy.ProxyFactory;
import javassist.util.proxy.ProxyObject;
import org.junit.Test;

import java.lang.reflect.Method;

public class JavassistTest {



    @Test
    public void test1() throws NotFoundException, IllegalAccessException, InstantiationException {
//        TestUser t=new TestUser();
//        ClassPool pool = ClassPool.getDefault();
//        CtClass aClass = pool.get("com.github.fashionbrot.TestUser");
//        for (int i = 0; i < aClass.getDeclaredFields().length; i++) {
//            System.out.println( aClass.getDeclaredFields()[i].getName());
//        }

//        ProxyFactory proxyFactory = new ProxyFactory();
//        //指定父类/proxtfactory会自动生成继承该类的子类
//        proxyFactory.setSuperclass(TestUser.class);
//
//        Class<?> clazz = proxyFactory.createClass();

        //设置过滤器,判断哪些方法需要被拦截
//        proxyFactory.setFilter((m) ->{
//            if(m.getName().equals("writeWork")) {
//                return true;
//            }
//            return false;
//        });
        //创建Person代理类,并创建对象


//        Object testUser =clazz.newInstance();
//
//        MyMethodHandler myMethodHandler = new MyMethodHandler();
//        //设置拦截代理
//        ((ProxyObject)testUser).setHandler(myMethodHandler);
//
//        ((TestUser)testUser).say();



    }

    class MyMethodHandler implements MethodHandler {

        @Override
        public Object invoke(Object arg0, Method arg1, Method arg2, Object[] arg3)
                throws Throwable {
            System.out.println("我来自代理");
            if (true) {
                throw new RuntimeException("报错了");
            }
            return arg2.invoke(arg0, arg3);
        }

    }

}
