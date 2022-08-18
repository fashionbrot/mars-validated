# mars-validated springmvc springboot springcloud dubbo  参数校验
简单好用的 springmvc springboot springcloud dubbo  参数校验
validated 是 控制 springmvc  springboot 的验证框架。此框架基于spring 开发。

##### github:https://github.com/fashionbrot/mars-validated.git
##### gitee :https://gitee.com/fashionbrot/mars-validated.git

| 版本        | 新增功能                                                                                                                                                                                                                                               |
|-----------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| 1.0.0     | 1、参数验证基本功能上线                                                                                                                                                                                                                                       |
| 1.0.1     | 1、新增验证注解 <br>2、Validated 注解增加validClass方法，可以选择性验证自己想要的参数。 <br> 3、新增Default 注解                                                                                                                                                                      |
| 1.0.2     | 1、代码逻辑优化。<br>2、新增groups 功能，可以根据groups 选择性验证                                                                                                                                                                                                        |
| 1.0.3     | 1、Constraint 接口增加  validatedByBean 方法，用来自定义注解实现 <br/>2、新增国际化支持 EnableValidatedConfig 中增加了 language方法、localeParamName方法用来实现消息内容国际化支持，目前支持中英文两种。其他语言请自行添加 <br/> 3、增加1.0.3 的validated-springboot-starter 支持                                           |
| 2.0.0     | 1、Constraint 接口删除  validatedByBean 方法 <br/> 2、删除ConstraintValidatorBean 接口 <br/>  3、ConstraintValidator 新增  modify 方法、validObject 方法 <br/> 4、Validated 注解增加 failFast快速失败方法、增加validReturnValue 方法（验证返回值） <br/> 5、重构了以前的逻辑，相比hibernate valid 速度快1倍左右 |
| 2.0.1     | 1、修复 @Validation.groups={AddGroup.class} 并且 注解.groups ={} 时,注解.groups ={}代表默认Groups,则不验证是否包含  @Validation.groups={AddGroup.class},代表跳过groups 验证                                                                                                    |
| 2.0.2     | 1、优化bean注入问题 2、maven 编译修改为gradle 编译 3、代码优化                                                                                                                                                                                                         |
| ~~2.0.3~~ | 1、优化验证逻辑实现   2、删除 ConstraintValidator接口中的 validObject 方法。   3、所有注解可实现bean 验证，不在是对基本类型的验证，所有对象都可验证                                                                                                                                                  |
| ~~2.0.4~~ | 优化快速失败逻辑                                                                                                                                                                                                                                           |
| ~~2.0.5~~ | 修改默认注解groups={DefaultGroup.class} @Validated(groups={DefaultGroup.class})                                                                                                                                                                          |
| ~~2.0.6~~ | 修复2.0.3、2.0.4、2.0.5 参数带其他注解时导致参数验证失败bug                                                                                                                                                                                                            |
| 2.0.7     | 修复自定义注解无法验证问题                                                                                                                                                                                                                                      |
### hibernate valid 最新版本  和 mars validated 2.0.1 比较 调用1000次接口时间比较,验证参数10个 速度最优还是 mars-validated



# validated 参数验证

## 使用环境

spring4.0 及以上
jdk1.8    及以上


|Annotation|Supported data types|作用
|---|--------|---|
|NotBlank|String|验证String 字符串是否为空|
|NotNull|String,Object,Integer,Long,Double,Short,Float,BigDecimal, BigInteger| 验证对象是否为空|
|NotEmpty|String |验证字符串不能为空|
|AssertFalse|Boolean,boolean,String|只能为false|
|AssertTrue|Boolean,boolean,String|只能为true|
|BankCard|String|验证银行卡|
|CreditCard|String|验证信用卡|
|Default|Integer,Double,Long,Short,Float,BigDecimal,String|设置默认值|
|Digits|String|验证是否是数字|
|Email|String|验证是否是邮箱|
|IdCard|String|验证是否是身份证，验证18岁|
|Length|int,long,short,double,Integer,Long,Float,Double,Short,String|验证长度|
|Pattern|String|正则表达式验证|
|Phone|String|验证手机号是否正确|
|Size|object[],boolean[],byte[],char[],double[],float[],int[],long[],short[],String length,Collection,Map|验证大小值|
|NotEqualLength|String|验证长度|


## @Validated 注解支持功能说明： 接口方法添加此注解开启参数验证
|方法| 默认值                  | 说明                       |
|------|----------------------|--------------------------|
|Class<?>[] validClass() default {}| {}                   | 需要校验的 class,只对填写的class验证 |
|Class<?>[] groups() default { }| {DefaultGroup.class} | 校验组                      |
|boolean failFast() default true| true                 | true 快速失败                |
|boolean validReturnValue() default false| false                | 验证返回值 默认false            |



## 1、springboot 使用配置
### 1.1、导入jar
#### maven 依赖
```xml
        <!-- springboot 依赖-->
        <dependency>
               <groupId>com.github.fashionbrot</groupId>
               <artifactId>validated-springboot-starter</artifactId>
               <version>2.0.5</version>
        </dependency>
```
#### gradle 依赖
```bash
implementation 'com.github.fashionbrot:validated-springboot-starter:2.0.5'
```

### 1.2、参数验证
```java
@Controller
public class NotEmptyController {

    @RequestMapping("/notEmpty")
    @ResponseBody
    @Validated       //开启接口注解
    public String  test(@NotEmpty(msg = "入参 abc is null") String abc){
        return "ok";
    }

}
```


## 2、springmvc  使用配置
### 2.1、导入jar
#### maven依赖
```xml
        <!-- springmvc 依赖-->
        <dependency>
            <groupId>com.github.fashionbrot</groupId>
            <artifactId>mars-validated</artifactId>
            <version>2.0.5</version>
        </dependency>
```
#### gradle 依赖
```bash
implementation 'com.github.fashionbrot:mars-validated:2.0.5'
```

### 2.2、配置扫描类
```java
@Component
@Configuration
@EnableValidatedConfig
public class ValidConfig {

}
```
### 2.3、使用配置
```java
@Controller
public class NotEmptyController {

    @RequestMapping("/notEmpty")
    @ResponseBody
    @Validated(failFast = true) //开启验证
    public NotEmptyModel  test(@NotEmpty(msg = "入参 abc is null") String abc){

        NotEmptyModel b=new NotEmptyModel();

        return b;
    }
    @RequestMapping("/notEmpty2")
    @ResponseBody
    @Validated(failFast = false)//开启验证
    public String test(@NotEmpty NotEmptyModel notNullModel){
        return notNullModel.getAbc();
    }
}

```

## 3、配置全局异常处理

```java
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ValidatedException.class)
    @ResponseStatus(HttpStatus.OK)
    public Object ValidatedException(ValidatedException e) {
        List<MarsViolation> violations = e.getViolations();
        String msg = e.getMsg();
        if (ObjectUtil.isNotEmpty(violations)) {
            if (violations.size() == 1) {
                msg = violations.get(0).getMsg();
            } else {
                msg = String.join(",", violations.stream().map(m -> m.getMsg()).collect(Collectors.toList()));
            }
        }
        return msg;
    }

}
```



# 以下是介绍各种配置

| 配置项                      | 默认值   | 说明                                                                                      |
|--------------------------|-------|-----------------------------------------------------------------------------------------|
| mars.validated.file-name | valid | 代表resources下面的配置文件  valid_zh_CN.properties 的文件名                                         |
|mars.validated.language|zh_CN| 代表resources下面配置文件 valid_zh_CN.properties   语言                                           |
|mars.validated.locale-param-name|lang| 浏览器参数配置 lang=zh_CN`  `mars.validated.file-name=valid 则读取的配置文件为valid_zh_CN.properties语言包 |


#### springboot配置方式
```properties
mars.validated.file-name=valid
mars.validated.language=zh_CN
mars.validated.locale-param-name=lang
```

#### springmvc 配置方式
```java
@Component
@Configuration
@EnableValidatedConfig(fileName = "valid",language="zh_CN",localeParamName="lang")
public class ValidConfig {
}
```

## 支持 默认值设置   hibernate默认不支持
```java
import com.github.fashionbrot.validated.annotation.Default;
@Data
public class BaseModel {

    @Default("1")
    private Integer pageNo;

    @Default("20")
    private Integer pageSize;
}
```



####  通过aop 自定义拦截
```java
@Component
@Aspect
public class ValidAspect {

    @Pointcut("execution(* com.github.fashion.test.service.*.*(..))")
    private void pointcut() {}

    @Autowired
    private MarsValidator marsValidator;

    @Around(value = "pointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        //打印方法的信息
        Object[] args = joinPoint.getArgs();
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature)signature;
        //自定义参数验证
        marsValidator.parameterAnnotationValid(methodSignature.getMethod(),args);

        return joinPoint.proceed();
    }

}

```


## ** 通过 group 来分组验证参数  **
```bash
访问：http://localhost:8080/group/add  返回：name 不能为空
访问：http://localhost:8080/group/edit 返回：id 不能为空,name 不能为空
```
```java
package com.github.fashion.test.controller;
import com.github.fashion.test.groups.EditGroup;
import com.github.fashion.test.model.GroupModel;
import com.github.fashionbrot.validated.annotation.Validated;
import com.github.fashionbrot.validated.groups.DefaultGroup;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/group")
public class GroupController {

    @GetMapping("add")
    @ResponseBody
    @Validated(failFast = false)
    public String add(GroupModel groupModel){
        return "ok";
    }

    @GetMapping("edit")
    @ResponseBody
    @Validated(groups ={EditGroup.class,DefaultGroup.class},failFast = false)
    public String edit(GroupModel groupModel){
        return "ok";
    }

}
```
```java
package com.github.fashion.test.model;

import com.github.fashion.test.groups.EditGroup;
import com.github.fashionbrot.validated.annotation.NotEmpty;
import com.github.fashionbrot.validated.annotation.NotNull;
import lombok.Data;

@Data
public class GroupModel {


    private String abc;

    @NotNull(msg = "id 不能为空",groups = {EditGroup.class})
    private Long id;

    @NotEmpty(msg = "name 不能为空")
    private String name;

}

```











## ** 自定义注解看这里 **
```java
@RequestMapping("/valid/bean")
@Controller
public class ValidBeanController {

    @RequestMapping("/test")
    @ResponseBody
    @Validated
    public String test(@CustomBean ValidBeanModel validBeanModel){
        return validBeanModel.getA1()+validBeanModel.getA2();
    }
}
```
#### 定义注解
```java
package com.github.fashion.test.annotation;

import com.github.fashionbrot.validated.constraint.Constraint;

import java.lang.annotation.*;

@Documented
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {CustomBeanConstraintValidatorBean.class}) //设置自定义注解实现类
public @interface CustomBean {

    String msg() default "CustomBean 验证失败";

    /**
     * true 快速失败
     * @return boolean
     */
    boolean failFast() default true;
}
```
#### 自定义注解实现
```java
package com.github.fashion.test.annotation;


import com.github.fashion.test.model.ValidBeanModel;
import com.github.fashionbrot.validated.constraint.ConstraintValidator;
import com.github.fashion.test.test.CustomModel;
import com.github.fashionbrot.validated.exception.ValidatedException;
import com.github.fashionbrot.validated.util.ExceptionUtil;
import com.github.fashionbrot.validated.util.StringUtil;
import lombok.Data;

import java.util.StringJoiner;

public class CustomBeanConstraintValidatorBean implements ConstraintValidator<CustomBean, Object> {

    @Override
    public boolean isValid(CustomBean annotation, Object value, Class<?> valueType) {
        if (value instanceof ValidBeanModel){
            ValidBeanModel beanModel= (ValidBeanModel) value;
            StringJoiner msg=new StringJoiner(",");
            if (StringUtil.isEmpty(beanModel.getA1())){
                msg.add("A1 不能为空") ;
                if (annotation.failFast()){
                    ValidatedException.throwMsg("A1",msg.toString());
                }
            }
            if (StringUtil.isEmpty(beanModel.getA2())){
                msg.add("A2 不能为空") ;
                if (annotation.failFast()){
                    ValidatedException.throwMsg("A2",msg.toString());
                }
            }
            if (msg.length()>0){
                ValidatedException.throwMsg("req",msg.toString());
            }
        }
        return true;
    }
    @Override
    public Object modify(CustomBean annotation, Object value, Class<?> valueType) {
        System.out.println("CustomConstraintValidator:"+value);
        if (value instanceof ValidBeanModel){
            ValidBeanModel beanModel= (ValidBeanModel) value;
            beanModel.setA1("1");
            beanModel.setA2("2");
            return beanModel;
        }if (value instanceof CustomModel){
            CustomModel customModel=(CustomModel)value;
            if (customModel!=null){
                customModel.setAbc("在 valid modify 中修改的abc");
            }
            return customModel;
        }
        return value;
    }

}

```


## 支持 国际化消息提示（现支持中英文两种）其他语言，请按照 mars-validated  resources 下面的 valid_zh_CN.properties 添加其他语言
```html
请求：http://localhost:8080/l18n/test?lang=en_us
提示：must not be empty
请求:http://localhost:8080/l18n/test?lang=zh_CN
提示：不能为空
```
```java

@RequestMapping("/l18n")
@Controller
public class I18nController {

    @RequestMapping("/test")
    @ResponseBody
    @Validated
    public String test(@NotEmpty() String abc){
        return abc;
    }

}
```


## 支持 dubbo 接口、实现类上方法上添加 @Validated ,设置 dubbo DubboProviderFilter 拦截器做统一处理

```java

public class DubboProviderFilter implements Filter {

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        Result result =  invoker.invoke(invocation);
        if(result.hasException() && result.getException() instanceof ValidationException){
            throw new CustomException(result.getException()); //自定义异常，全局拦截控制,或抛出 RpcException 自行拦截
        }
        return result;
    }
}
```




####  使用参数验证方式@Validated 开启接口验证 @Email验证邮箱格式,其他注解不在一一介绍

```bash
@Controller
public class DemoController {

    @Autowired
    private TestService testService;

    @RequestMapping("/emailCheck")
    @ResponseBody
    @Validated //注意此处
    public String demo(@Email String abc,){
        return testService.test(abc);
    }
}
@Controller
public class DemoController {

    @Autowired
    private TestService testService;

    @RequestMapping("/idcardCheck")
    @ResponseBody
    @Validated //注意此处
    public String demo(IdCardModel idCardModel){
        return testService.test("ac");
    }
}


**此处支持多继承验证***

public class IdCardModel extends BaseModel{

    @IdCard
    private String idCardModel;

    public String getIdCardModel() {
        return idCardModel;
    }

    public void setIdCardModel(String idCardModel) {
        this.idCardModel = idCardModel;
    }
}

@Service
public class TestService{
    @Validated
    public void test2(@IdCard String abc){

    }
}

```

#### 按照 @Validated(groups = {EditGroup.class}) valid 校验
```java
@Controller
public class DemoController {
     @RequestMapping("/test1")
     @ResponseBody
     @Validated(groups = {EditGroup.class})
     public String test1( @Custom(min = 1,groups = {EditGroup.class,AddGroup.class}) String abc1){
         return abc1;
     }


     @RequestMapping("/test2")
     @ResponseBody
     @Validated(groups = AddGroup.class)
     public String test2(GroupModel groupModel){
         return groupModel.getAbc();
     }
}
```


#### 可通过项目中的 https://github.com/fashionbrot/mars-validated/tree/master/mars-validated-test 参考使用demo
#### 如有问题请通过 https://github.com/fashionbrot/mars-validated/issues 提出 告诉我们。我们非常认真地对待错误和缺陷，在产品面前没有不重要的问题。不过在创建错误报告之前，请检查是否存在报告相同问题的issues。


### 如有问题请联系邮箱 fashionbrot@163.com 反馈问题
