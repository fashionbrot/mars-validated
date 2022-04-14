# mars-validated springmvc springboot springcloud dubbo  参数校验
简单好用的 springmvc springboot springcloud dubbo  参数校验
validated 是 控制 springmvc  springboot 的验证框架。此框架基于spring 开发。


|版本|新增功能|
|---|--------|
|1.0.0|1、参数验证基本功能上线|
|1.0.1|1、新增验证注解 <br>2、Validated 注解增加validClass方法，可以选择性验证自己想要的参数。 <br> 3、新增Default 注解|
|1.0.2|1、代码逻辑优化。<br>2、新增groups 功能，可以根据groups 选择性验证|
|1.0.3|1、Constraint 接口增加  validatedByBean 方法，用来自定义注解实现 <br/>2、新增国际化支持 EnableValidatedConfig 中增加了 language方法、localeParamName方法用来实现消息内容国际化支持，目前支持中英文两种。其他语言请自行添加 <br/> 3、增加1.0.3 的validated-springboot-starter 支持 |
|2.0.0|1、Constraint 接口删除  validatedByBean 方法 <br/> 2、删除ConstraintValidatorBean 接口 <br/>  3、ConstraintValidator 新增  modify 方法、validObject 方法 <br/> 4、Validated 注解增加 failFast快速失败方法、增加validReturnValue 方法（验证返回值） <br/> 5、重构了以前的逻辑，相比hibernate valid 速度快1倍左右 |      
|2.0.1|1、修复 @Validation.groups={AddGroup.class} 并且 注解.groups ={} 时,注解.groups ={}代表默认Groups,则不验证是否包含  @Validation.groups={AddGroup.class},代表跳过groups 验证|

### hibernate valid  和 mars validated 比较 调用1000次接口时间比较,验证参数10个 笔记本可能测的不太准确，也没有设置jvm 参数，后期设置jvm 在linux 测试


#### mars-validated 2.0.2 failFast=true
```log
INFO http-nio-8080-exec-6 2022-04-14 23:21:35.035 compare:mars: 一共耗时：1919 毫秒 执行次数:1000 系统：Windows 10
INFO http-nio-8080-exec-2 2022-04-14 23:21:38.600 compare:mars: 一共耗时：1923 毫秒 执行次数:1000 系统：Windows 10
INFO http-nio-8080-exec-6 2022-04-14 23:21:42.574 compare:mars: 一共耗时：1908 毫秒 执行次数:1000 系统：Windows 10
INFO http-nio-8080-exec-2 2022-04-14 23:21:46.226 compare:mars: 一共耗时：1900 毫秒 执行次数:1000 系统：Windows 10
INFO http-nio-8080-exec-3 2022-04-14 23:21:50.028 compare:mars: 一共耗时：1948 毫秒 执行次数:1000 系统：Windows 10
INFO http-nio-8080-exec-9 2022-04-14 23:21:53.911 compare:mars: 一共耗时：1879 毫秒 执行次数:1000 系统：Windows 10
INFO http-nio-8080-exec-1 2022-04-14 23:21:58.018 compare:mars: 一共耗时：1911 毫秒 执行次数:1000 系统：Windows 10
INFO http-nio-8080-exec-9 2022-04-14 23:22:02.002 compare:mars: 一共耗时：1928 毫秒 执行次数:1000 系统：Windows 10
INFO http-nio-8080-exec-7 2022-04-14 23:22:05.886 compare:mars: 一共耗时：1897 毫秒 执行次数:1000 系统：Windows 10
INFO http-nio-8080-exec-5 2022-04-14 23:22:09.846 compare:mars: 一共耗时：1956 毫秒 执行次数:1000 系统：Windows 10

```

#### mars-validated 2.0.2 failFast=false
```log
INFO http-nio-8080-exec-5 2022-04-14 23:27:34.503 compare:mars: 一共耗时：2324 毫秒 执行次数:1000 系统：Windows 10
INFO http-nio-8080-exec-2 2022-04-14 23:27:39.716 compare:mars: 一共耗时：2078 毫秒 执行次数:1000 系统：Windows 10
INFO http-nio-8080-exec-1 2022-04-14 23:27:42.450 compare:mars: 一共耗时：1975 毫秒 执行次数:1000 系统：Windows 10
INFO http-nio-8080-exec-8 2022-04-14 23:27:45.939 compare:mars: 一共耗时：2021 毫秒 执行次数:1000 系统：Windows 10
INFO http-nio-8080-exec-1 2022-04-14 23:27:49.063 compare:mars: 一共耗时：2044 毫秒 执行次数:1000 系统：Windows 10
INFO http-nio-8080-exec-2 2022-04-14 23:27:53.165 compare:mars: 一共耗时：1957 毫秒 执行次数:1000 系统：Windows 10
INFO http-nio-8080-exec-5 2022-04-14 23:27:59.187 compare:mars: 一共耗时：1989 毫秒 执行次数:1000 系统：Windows 10
INFO http-nio-8080-exec-2 2022-04-14 23:28:02.058 compare:mars: 一共耗时：1928 毫秒 执行次数:1000 系统：Windows 10
INFO http-nio-8080-exec-3 2022-04-14 23:28:04.775 compare:mars: 一共耗时：2059 毫秒 执行次数:1000 系统：Windows 10
INFO http-nio-8080-exec-1 2022-04-14 23:28:07.615 compare:mars: 一共耗时：1916 毫秒 执行次数:1000 系统：Windows 10


INFO http-nio-8080-exec-2 2022-04-14 23:55:39.437 compare:mars: 一共耗时：4318 毫秒 执行次数:2000 系统：Windows 10
INFO http-nio-8080-exec-8 2022-04-14 23:55:45.803 compare:mars: 一共耗时：4283 毫秒 执行次数:2000 系统：Windows 10
INFO http-nio-8080-exec-5 2022-04-14 23:55:59.818 compare:mars: 一共耗时：4302 毫秒 执行次数:2000 系统：Windows 10
INFO http-nio-8080-exec-3 2022-04-14 23:57:04.937 compare:mars: 一共耗时：4320 毫秒 执行次数:2000 系统：Windows 10
INFO http-nio-8080-exec-7 2022-04-14 23:57:19.673 compare:mars: 一共耗时：4533 毫秒 执行次数:2000 系统：Windows 10
INFO http-nio-8080-exec-4 2022-04-14 23:57:28.210 compare:mars: 一共耗时：4701 毫秒 执行次数:2000 系统：Windows 10
INFO http-nio-8080-exec-10 2022-04-14 23:57:35.630 compare:mars: 一共耗时：4294 毫秒 执行次数:2000 系统：Windows 10
INFO http-nio-8080-exec-7 2022-04-14 23:58:03.614 compare:mars: 一共耗时：3971 毫秒 执行次数:2000 系统：Windows 10
INFO http-nio-8080-exec-1 2022-04-14 23:58:15.647 compare:mars: 一共耗时：3947 毫秒 执行次数:2000 系统：Windows 10
INFO http-nio-8080-exec-7 2022-04-14 23:58:26.813 compare:mars: 一共耗时：3941 毫秒 执行次数:2000 系统：Windows 10
INFO http-nio-8080-exec-6 2022-04-14 23:58:40.095 compare:mars: 一共耗时：4000 毫秒 执行次数:2000 系统：Windows 10
```


#### hibernate 6.2.0.Final failFast=true

```bash
INFO http-nio-8080-exec-2 2022-04-14 23:13:43.987 compare:hibernate: 一共耗时：2106 毫秒 执行次数:1000 系统：Windows 10
INFO http-nio-8080-exec-6 2022-04-14 23:13:47.721 compare:hibernate: 一共耗时：2032 毫秒 执行次数:1000 系统：Windows 10
INFO http-nio-8080-exec-9 2022-04-14 23:13:51.204 compare:hibernate: 一共耗时：2035 毫秒 执行次数:1000 系统：Windows 10
INFO http-nio-8080-exec-4 2022-04-14 23:13:54.494 compare:hibernate: 一共耗时：1986 毫秒 执行次数:1000 系统：Windows 10
INFO http-nio-8080-exec-3 2022-04-14 23:13:57.797 compare:hibernate: 一共耗时：2050 毫秒 执行次数:1000 系统：Windows 10
INFO http-nio-8080-exec-5 2022-04-14 23:14:01.053 compare:hibernate: 一共耗时：2039 毫秒 执行次数:1000 系统：Windows 10
INFO http-nio-8080-exec-4 2022-04-14 23:14:04.272 compare:hibernate: 一共耗时：2004 毫秒 执行次数:1000 系统：Windows 10
INFO http-nio-8080-exec-1 2022-04-14 23:14:07.667 compare:hibernate: 一共耗时：2058 毫秒 执行次数:1000 系统：Windows 10
INFO http-nio-8080-exec-8 2022-04-14 23:14:20.490 compare:hibernate: 一共耗时：2018 毫秒 执行次数:1000 系统：Windows 10
INFO http-nio-8080-exec-9 2022-04-14 23:14:24.071 compare:hibernate: 一共耗时：2049 毫秒 执行次数:1000 系统：Windows 10
```

#### hibernate 6.2.0.Final failFast=false

```bash
INFO http-nio-8080-exec-9 2022-04-14 23:29:48.050 compare:hibernate: 一共耗时：2613 毫秒 执行次数:1000 系统：Windows 10
INFO http-nio-8080-exec-8 2022-04-14 23:29:51.571 compare:hibernate: 一共耗时：2610 毫秒 执行次数:1000 系统：Windows 10
INFO http-nio-8080-exec-6 2022-04-14 23:29:54.931 compare:hibernate: 一共耗时：2662 毫秒 执行次数:1000 系统：Windows 10
INFO http-nio-8080-exec-10 2022-04-14 23:29:58.469 compare:hibernate: 一共耗时：2655 毫秒 执行次数:1000 系统：Windows 10
INFO http-nio-8080-exec-8 2022-04-14 23:30:02.278 compare:hibernate: 一共耗时：2657 毫秒 执行次数:1000 系统：Windows 10
INFO http-nio-8080-exec-10 2022-04-14 23:30:05.986 compare:hibernate: 一共耗时：2638 毫秒 执行次数:1000 系统：Windows 10
INFO http-nio-8080-exec-8 2022-04-14 23:30:11.653 compare:hibernate: 一共耗时：2567 毫秒 执行次数:1000 系统：Windows 10
INFO http-nio-8080-exec-2 2022-04-14 23:30:34.583 compare:hibernate: 一共耗时：2550 毫秒 执行次数:1000 系统：Windows 10
INFO http-nio-8080-exec-8 2022-04-14 23:30:49.462 compare:hibernate: 一共耗时：2662 毫秒 执行次数:1000 系统：Windows 10
INFO http-nio-8080-exec-5 2022-04-14 23:30:53.032 compare:hibernate: 一共耗时：2660 毫秒 执行次数:1000 系统：Windows 10


INFO http-nio-8080-exec-2 2022-04-14 23:49:41.644 compare:hibernate: 一共耗时：4836 毫秒 执行次数:2000 系统：Windows 10
INFO http-nio-8080-exec-8 2022-04-14 23:49:49.082 compare:hibernate: 一共耗时：5232 毫秒 执行次数:2000 系统：Windows 10
INFO http-nio-8080-exec-3 2022-04-14 23:50:00.638 compare:hibernate: 一共耗时：8877 毫秒 执行次数:2000 系统：Windows 10
INFO http-nio-8080-exec-5 2022-04-14 23:50:12.151 compare:hibernate: 一共耗时：9172 毫秒 执行次数:2000 系统：Windows 10
INFO http-nio-8080-exec-7 2022-04-14 23:50:30.909 compare:hibernate: 一共耗时：5114 毫秒 执行次数:2000 系统：Windows 10
INFO http-nio-8080-exec-4 2022-04-14 23:50:51.617 compare:hibernate: 一共耗时：4998 毫秒 执行次数:2000 系统：Windows 10
INFO http-nio-8080-exec-6 2022-04-14 23:51:06.028 compare:hibernate: 一共耗时：12682 毫秒 执行次数:2000 系统：Windows 10
INFO http-nio-8080-exec-1 2022-04-14 23:51:18.058 compare:hibernate: 一共耗时：7956 毫秒 执行次数:2000 系统：Windows 10
INFO http-nio-8080-exec-9 2022-04-14 23:51:37.973 compare:hibernate: 一共耗时：4992 毫秒 执行次数:2000 系统：Windows 10
INFO http-nio-8080-exec-6 2022-04-14 23:52:37.387 compare:hibernate: 一共耗时：5301 毫秒 执行次数:2000 系统：Windows 10
```

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


# 使用4步配置



## 1、导入jar

```bash
        <!-- springmvc 依赖-->
        <dependency>
            <groupId>com.github.fashionbrot</groupId>
            <artifactId>mars-validated</artifactId>
            <version>2.0.1</version>
        </dependency>
        <!-- springboot 依赖-->
        <dependency>
               <groupId>com.github.fashionbrot</groupId>
               <artifactId>validated-springboot-starter</artifactId>
               <version>2.0.1</version>
        </dependency>

```

## 1、validated springboot 的使用方式 1 (application.properties 直接添加)
```properties
mars.validated.file-name=valid
mars.validated.language=zh_CN
mars.validated.locale-param-name=lang
```

## 1、validated springboot 的使用方式 2 （fileName 如果不填默认jar 包自带提示，如果需要批量自定义请按照jar 包下的valid_zh_CN.properties 修改提示语内容）
```java
@SpringBootApplication
@EnableValidatedConfig(fileName = "valid",language="zh_CN",localeParamName="lang")    // fileName 默认中文jar包自带 如需要批量自定义请自己创建 valid_zh_CN.properties  放在自己项目中的resources 下
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}
```
## 3、spring适配方式
```java
@Component
@Configuration
@EnableValidatedConfig(fileName = "valid",language="zh_CN",localeParamName="lang") 
public class ValidConfig {
}
```



# 自定义实现全局异常处理
拦截 ValidatedException异常类
```bash
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ValidatedException.class)
    @ResponseStatus(HttpStatus.OK)
    public Object ValidatedException(ValidatedException e) {
        List<MarsViolation> violations = e.getViolations();
        return String.join(",",violations.stream().map(m-> m.getMsg()).collect(Collectors.toList()));
    }

}
```
## @Validated 注解支持功能
|方法|作用
|------|------|
|Class<?>[] validClass() default {}|需要校验的 class|
|Class<?>[] groups() default { }|校验组|
|boolean failFast() default true|true 快速失败|
|boolean validReturnValue() default false|验证返回值 默认false|


###  使用参数验证方式（1）通过aop 自定义拦截
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

##  使用参数验证方式（2） @Validated 开启接口验证 @Email验证邮箱格式

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

### 按照 @Validated(groups = {EditGroup.class}) valid 校验
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


## 支持对 bean 对象的直接验证
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
@Documented
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {CustomBeanConstraintValidatorBean.class})
public @interface CustomBean {
    //没有任何参数

}

public class CustomBeanConstraintValidatorBean implements ConstraintValidator<CustomBean, Object> {

    @Override
    public boolean isValid(CustomBean annotation, Object value, Class<?> valueType) {
        return true;
    }


    @Override
    public String validObject(CustomBean custom, Object var,Class<?> valueType) {

        if (var instanceof ValidBeanModel){
            ValidBeanModel beanModel= (ValidBeanModel) var;
            if (beanModel!=null && (beanModel.getA1()==null || beanModel.getA2()==null)){
                return "a1 或者 a2 为空";
            }
        }
        /**
         * return null 则验证成功 其他验证失败
          */
        return null;
    }
    @Override
    public Object modify(CustomBean annotation, Object var,Class<?> valueType) {
        System.out.println("CustomConstraintValidator:"+var);
        if (var instanceof ValidBeanModel){
            ValidBeanModel beanModel= (ValidBeanModel) var;
            beanModel.setA1("1");
            beanModel.setA2("2");
            return beanModel;
        }
        return var;
    }
}
```

## 支持 国际化消息提示（现支持中英文两种）
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
        if(result.hasException() && result.getException() instanceof com.sgr.common.valid.exception.ValidationException){
            throw new CustomException(result.getException()); //自定义异常，全局拦截控制,或抛出 RpcException 自行拦截
        }
        return result;
    }
}
```




### ConstraintValidator 接口新增方法
```java
package com.github.fashionbrot.validated.constraint;
import java.lang.annotation.Annotation;
public  interface ConstraintValidator<A extends Annotation, T> {

        boolean isValid(A annotation, T var1);
    
        //实现类可实现可不实现,使用场景如 ajax传入 base64字符,可以在自己逻辑中实现自定义逻辑
        /**
         * 修改 value 值
         * @param annotation
         * @param value
         * @param valueType
         * @return
         */
        default T modify(A annotation,T value,Class<?> valueType){
            return value;
        }
        /**
         * return value==null?验证通过:验证不通过
         * 验证不过 throw Exception value
         * @param annotation
         * @param value
         * @param valueType
         * @return
         */
        default String validObject(A annotation, T value,Class<?> valueType){
            return null;
        }

}

```




### 支持自定义注解 如下：
1、实现  ConstraintValidator 此接口
2、自定义注解如下：  CustomConstraintValidator.class,CustomConstraintValidator2.class 实现类可多个，至少有一个
```java
    @Documented
    @Target({ElementType.FIELD,  ElementType.PARAMETER})
    @Retention(RetentionPolicy.RUNTIME)
    @Constraint(validatedBy = {CustomConstraintValidator.class,CustomConstraintValidator2.class})
    public @interface Custom {
    
        String msg() default "com.mars.valid.Custom.msg";
    
        int min();
    }
```

3、代码实现


```java
public class CustomConstraintValidator implements ConstraintValidator<Custom,String> {

    @Override
    public boolean isValid(Custom custom,String var1,Class<?> valueType) {
        /**
         * 自定义方法
         */
        int min=custom.min();
        /**
         * value
         */
        System.out.println(var1);
        /**
         * return true 则验证成功 false 验证失败
          */
        return true;
    }
}

```


#### 6、可通过 test项目中的 https://github.com/fashionbrot/mars-validated/tree/master/test/springboot-test 参考使用demo 
#### 7、如有问题请通过 https://github.com/fashionbrot/mars-validated/issues 提出 告诉我们。我们非常认真地对待错误和缺陷，在产品面前没有不重要的问题。不过在创建错误报告之前，请检查是否存在报告相同问题的issues。


### 如有问题请联系官方qq群：52842583
