package com.github.fashionbrot;


import com.github.fashionbrot.validated.annotation.NotEmpty;
import com.github.fashionbrot.validated.annotation.Size;
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

public class SizeTest {


    public class TestController{
        @Validated
        private void test1(@Size(min = 1,max = 3,msg = "1~3之间") String string){

        }
    }

    @Test
    public void test1(){
        Method[] methods = SizeTest.TestController.class.getDeclaredMethods();
        Method method  = Arrays.stream(methods).filter(m -> m.getName().equals("test1")).findFirst().get();

        String result="ValidatedException(fieldName=arg0, msg=1~3之间, annotationName=com.github.fashionbrot.validated.annotation.Size, value=null, valueIndex=0, violations=null)";
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



    public class TestController2{
        @Validated(failFast = false)
        private void test2(@Valid @Size(min = 2,max = 3,msg = "abc1 必须2~3") Integer[] abc1 ,@Valid @Size(min = 2,max = 3,msg = "abc2 必须2~3")List<Integer> abc2){
        }
    }

    @Test
    public void test2(){
        Method[] methods = SizeTest.TestController2.class.getDeclaredMethods();
        Method method  = Arrays.stream(methods).filter(m -> m.getName().equals("test2")).findFirst().get();

        String result="ValidatedException(fieldName=null, msg=null, annotationName=null, value=null, valueIndex=null, violations=[MarsViolation(fieldName=arg0, msg=abc1 必须2~3, annotationName=com.github.fashionbrot.validated.annotation.Size, value=[Ljava.lang.Integer;@1c37fc4b, valueIndex=0), MarsViolation(fieldName=arg1, msg=abc2 必须2~3, annotationName=com.github.fashionbrot.validated.annotation.Size, value=[2], valueIndex=1)])";
        String returnResult="";
        try {

            MarsValidator marsValidator = new MarsValidatorImpl();
            marsValidator.parameterAnnotationValid(method,new Object[]{new Integer[]{1},Arrays.asList(Integer.valueOf(2))});
        }catch (ValidatedException e){
            returnResult = e.toString();
        }
        System.out.println(returnResult);
        Assert.assertEquals(result,returnResult);
    }


}
