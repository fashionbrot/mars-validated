package com.github.fashionbrot;


import com.alibaba.fastjson2.JSON;
import com.github.fashionbrot.validated.annotation.Default;
import com.github.fashionbrot.validated.annotation.NotEmpty;
import com.github.fashionbrot.validated.annotation.Valid;
import com.github.fashionbrot.validated.annotation.Validated;
import com.github.fashionbrot.validated.exception.ValidatedException;
import com.github.fashionbrot.validated.validator.MarsValidator;
import com.github.fashionbrot.validated.validator.MarsValidatorImpl;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

public class DefaultTest {


    public class TestController{
        @Validated
        private void test1(@Default(value = "abc") String string){
        }
    }

    @Test
    public void test1(){
        Method[] methods = DefaultTest.TestController.class.getDeclaredMethods();
        Method method  = Arrays.stream(methods).filter(m -> m.getName().equals("test1")).findFirst().get();

        String result="[\"abc\"]";
        String returnResult="";

        Object[] param=new Object[]{null};
        MarsValidator marsValidator = new MarsValidatorImpl();
        marsValidator.parameterAnnotationValid(method,param);
        returnResult=JSON.toJSONString(param);
        System.out.println(JSON.toJSONString(param));

        Assert.assertEquals(result,returnResult);
    }


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class Test2Req{
        @Default(value = "abc")
        private String abc;
    }
    public class TestController2{
        @Validated
        private void test2( Test2Req req){
        }
    }

    @Test
    public void test2(){
        Method[] methods = DefaultTest.TestController2.class.getDeclaredMethods();
        Method method  = Arrays.stream(methods).filter(m -> m.getName().equals("test2")).findFirst().get();

        String result="[{\"abc\":\"abc\"}]";
        String returnResult="";

        Test2Req test2Req =new Test2Req();
        test2Req.setAbc(null);
        Object[] params = {test2Req};

        MarsValidator marsValidator = new MarsValidatorImpl();
        marsValidator.parameterAnnotationValid(method,params);
        returnResult = JSON.toJSONString(params);
        System.out.println(JSON.toJSONString(params));
        Assert.assertEquals(result,returnResult);
    }


    public class TestController3{
        @Validated
        private void test3(@Valid List<Test2Req> req){

        }
    }

    @Test
    public void test3(){
        Method[] methods = DefaultTest.TestController3.class.getDeclaredMethods();
        Method method  = Arrays.stream(methods).filter(m -> m.getName().equals("test3")).findFirst().get();

        String result="[[{\"abc\":\"abc1\"},{\"abc\":\"abc\"}]]";
        String returnResult="";

        Test2Req test2Req =new Test2Req();
        test2Req.setAbc("abc1");

        Test2Req test22Req =new Test2Req();
        test22Req.setAbc(null);
        Object[] params = {Arrays.asList(test2Req, test22Req)};

        MarsValidator marsValidator = new MarsValidatorImpl();
        marsValidator.parameterAnnotationValid(method,params);
        returnResult = JSON.toJSONString(params);
        System.out.println(JSON.toJSONString(params));
        Assert.assertEquals(result,returnResult);
    }

    public class TestController4{
        @Validated
        private void test4(@Valid Test2Req[] req){

        }
    }

    @Test
    public void test4(){
        Method[] methods = DefaultTest.TestController4.class.getDeclaredMethods();
        Method method  = Arrays.stream(methods).filter(m -> m.getName().equals("test4")).findFirst().get();

        String result="[[{\"abc\":\"abc1\"},{\"abc\":\"abc\"}]]";
        String returnResult="";

        Test2Req test2Req =new Test2Req();
        test2Req.setAbc("abc1");

        Test2Req test22Req =new Test2Req();
        test22Req.setAbc(null);
        Object[] params = {new Object[]{test2Req, test22Req}};

        MarsValidator marsValidator = new MarsValidatorImpl();
        marsValidator.parameterAnnotationValid(method,params);
        returnResult = JSON.toJSONString(params);
        System.out.println(JSON.toJSONString(params));
        Assert.assertEquals(result,returnResult);
    }



    @Data
    public class TestReq5{

        @Valid
        private List<TestReq55> testReq55s;
    }
    @Data
    public class TestReq55{
        @Default(value = "abc")
        private String abc;
    }
    public class TestController5{
        @Validated
        private void test5(TestReq5 testReq5){

        }
    }

    @Test
    public void test5(){
        Method[] methods = DefaultTest.TestController5.class.getDeclaredMethods();
        Method method  = Arrays.stream(methods).filter(m -> m.getName().equals("test5")).findFirst().get();

        String result="[{\"testReq55s\":[{\"abc\":\"abc\"},{\"abc\":\"abc\"}]}]";
        String returnResult="";

        TestReq55 r1 =new TestReq55();
        r1.setAbc("abc");

        TestReq55 r2 =new TestReq55();
        r2.setAbc(null);

        TestReq5 testReq5=new TestReq5();
        testReq5.setTestReq55s(Arrays.asList(r1,r2));
        Object[] params =new Object[] {testReq5};

        MarsValidator marsValidator = new MarsValidatorImpl();
        marsValidator.parameterAnnotationValid(method,params);

        returnResult=JSON.toJSONString(params);
        System.out.println(returnResult);
        Assert.assertEquals(result,returnResult);
    }

}
