package com.github.fashionbrot;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;
import org.junit.Test;

public class JavassistTest {



    @Test
    public void test1() throws NotFoundException {
        TestUser t=new TestUser();
        ClassPool pool = ClassPool.getDefault();
        CtClass aClass = pool.get("com.github.fashionbrot.TestUser");
        for (int i = 0; i < aClass.getDeclaredFields().length; i++) {
            System.out.println( aClass.getDeclaredFields()[i].getName());
        }
    }

}
