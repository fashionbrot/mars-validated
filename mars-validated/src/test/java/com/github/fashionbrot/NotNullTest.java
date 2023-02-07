package com.github.fashionbrot;


import com.github.fashionbrot.validated.annotation.NotNull;
import com.github.fashionbrot.validated.annotation.Valid;
import com.github.fashionbrot.validated.annotation.Validated;
import com.github.fashionbrot.validated.exception.ValidatedException;
import com.github.fashionbrot.validated.validator.MarsValidator;
import com.github.fashionbrot.validated.validator.MarsValidatorImpl;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class NotNullTest {




    public class TestController{
        @Validated
        private void test1(@NotNull(msg = "string 不能为空") String string){

        }
    }

    @Test
    public void test1(){
        Method[] methods = NotNullTest.TestController.class.getDeclaredMethods();
        Method method  = Arrays.stream(methods).filter(m -> m.getName().equals("test1")).findFirst().get();

        String result="ValidatedException(fieldName=arg0, msg=string 不能为空, annotationName=com.github.fashionbrot.validated.annotation.NotNull, value=null, valueIndex=0, violations=null)";
        String returnResult="";
        try {
            MarsValidator marsValidator = new MarsValidatorImpl();
            marsValidator.parameterAnnotationValid(method,new Object[]{null});
        }catch (ValidatedException e){
            returnResult = e.toString();
        }
        System.out.println(returnResult);
        Assert.assertEquals(result,returnResult);
    }


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class Test2Req{
        @NotNull(msg = "abc不能为null")
        private String abc;
    }
    public class TestController2{
        @Validated
        private void test2( Test2Req req){
        }
    }

    @Test
    public void test2(){
        Method[] methods = NotNullTest.TestController2.class.getDeclaredMethods();
        Method method  = Arrays.stream(methods).filter(m -> m.getName().equals("test2")).findFirst().get();

        String result="ValidatedException(fieldName=abc, msg=abc不能为null, annotationName=com.github.fashionbrot.validated.annotation.NotNull, value=null, valueIndex=0, violations=null)";
        String returnResult="";
        try {
            Test2Req test2Req =new Test2Req();
            test2Req.setAbc(null);

            MarsValidator marsValidator = new MarsValidatorImpl();
            marsValidator.parameterAnnotationValid(method,new Object[]{test2Req});
        }catch (ValidatedException e){
            returnResult = e.toString();
        }
        System.out.println(returnResult);
        Assert.assertEquals(result,returnResult);
    }


    public class TestController3{
        @Validated
        private void test3(@Valid List<Test2Req> req){

        }
    }

    @Test
    public void test3(){
        Method[] methods = NotNullTest.TestController3.class.getDeclaredMethods();
        Method method  = Arrays.stream(methods).filter(m -> m.getName().equals("test3")).findFirst().get();

        String result="ValidatedException(fieldName=abc, msg=abc不能为null, annotationName=com.github.fashionbrot.validated.annotation.NotNull, value=null, valueIndex=1, violations=null)";
        String returnResult="";
        try {
            Test2Req test2Req =new Test2Req();
            test2Req.setAbc("abc");

            Test2Req test22Req =new Test2Req();
            test22Req.setAbc(null);

            MarsValidator marsValidator = new MarsValidatorImpl();
            marsValidator.parameterAnnotationValid(method,new Object[]{Arrays.asList(test2Req,test22Req)});
        }catch (ValidatedException e){
            returnResult = e.toString();
        }
        System.out.println(returnResult);
        Assert.assertEquals(result,returnResult);
    }

    public class TestController4{
        @Validated
        private void test4(@Valid Test2Req[] req){

        }
    }

    @Test
    public void test4(){
        Method[] methods = NotNullTest.TestController4.class.getDeclaredMethods();
        Method method  = Arrays.stream(methods).filter(m -> m.getName().equals("test4")).findFirst().get();

        String result="ValidatedException(fieldName=abc, msg=abc不能为null, annotationName=com.github.fashionbrot.validated.annotation.NotNull, value=null, valueIndex=1, violations=null)";
        String returnResult="";
        try {
            Test2Req test2Req =new Test2Req();
            test2Req.setAbc("abc");

            Test2Req test22Req =new Test2Req();
            test22Req.setAbc(null);

            MarsValidator marsValidator = new MarsValidatorImpl();
            marsValidator.parameterAnnotationValid(method,new Object[]{new Object[]{test2Req,test22Req}});
        }catch (ValidatedException e){
            returnResult = e.toString();
        }
        System.out.println(returnResult);
        Assert.assertEquals(result,returnResult);
    }



    @Data
    public class TestReq5{

        @Valid
        private List<Test2Req> test2Reqs;
    }
    public class TestController5{
        @Validated
        private void test5(TestReq5 testReq5){

        }
    }

    @Test
    public void test5(){
        Method[] methods = NotNullTest.TestController5.class.getDeclaredMethods();
        Method method  = Arrays.stream(methods).filter(m -> m.getName().equals("test5")).findFirst().get();

        String result="ValidatedException(fieldName=abc, msg=abc不能为null, annotationName=com.github.fashionbrot.validated.annotation.NotNull, value=null, valueIndex=1, violations=null)";
        String returnResult="";
        try {
            Test2Req test2Req =new Test2Req();
            test2Req.setAbc("abc");

            Test2Req test22Req =new Test2Req();
            test22Req.setAbc(null);
            TestReq5 testReq5=new TestReq5();
            testReq5.setTest2Reqs(Arrays.asList(test2Req,test22Req));
            Object[] params =new Object[] {testReq5};

            MarsValidator marsValidator = new MarsValidatorImpl();
            marsValidator.parameterAnnotationValid(method,params);
        }catch (ValidatedException e){
            returnResult = e.toString();
        }
        System.out.println(returnResult);
        Assert.assertEquals(result,returnResult);
    }



    @Data
    public class TestReq6{

        @Valid
        private Test2Req[] test2Reqs;
    }
    public class TestController6{
        @Validated
        private void test6(TestReq6 testReq6){

        }
    }

    @Test
    public void test6(){
        Method[] methods = NotNullTest.TestController6.class.getDeclaredMethods();
        Method method  = Arrays.stream(methods).filter(m -> m.getName().equals("test6")).findFirst().get();

        String result="ValidatedException(fieldName=abc, msg=abc不能为null, annotationName=com.github.fashionbrot.validated.annotation.NotNull, value=null, valueIndex=1, violations=null)";
        String returnResult="";
        try {
            Test2Req test2Req =new Test2Req();
            test2Req.setAbc("abc");

            Test2Req test22Req =new Test2Req();
            test22Req.setAbc(null);

            TestReq6 testReq6=new TestReq6();
            testReq6.setTest2Reqs(new Test2Req[]{test2Req,test22Req});
            Object[] params =new Object[] {testReq6};

            MarsValidator marsValidator = new MarsValidatorImpl();
            marsValidator.parameterAnnotationValid(method,params);
        }catch (ValidatedException e){
            returnResult = e.toString();
        }
        System.out.println(returnResult);
        Assert.assertEquals(result,returnResult);
    }

}
