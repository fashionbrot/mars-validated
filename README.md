# mars-validated springmvc springboot springcloud dubbo  参数校验
简单好用的 springmvc springboot springcloud dubbo  参数校验
validated 是 控制 springmvc  springboot 的验证框架。此框架基于spring 开发。


# 使用4步配置


## 1、导入jar

```bash

        <dependency>
            <groupId>com.github.fashionbrot</groupId>
            <artifactId>mars-validated</artifactId>
            <version>1.0.0</version>
        </dependency>

```

## 2、使用注解

### 2.1 springboot 配置
fileName 如果不填默认jar 包自带提示，如果需要批量自定义请按照jar 包下的valid_zh_CN.properties 修改提示语内容
```java
@SpringBootApplication
@EnableValidatedConfig(fileName = "test")    // fileName 默认中文jar包自带 如需要批量自定义请自己创建 test.properties  放在自己项目中的resources 下
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}
```
### 2.2 spring配置

```java
@Component
@Configuration
@EnableValidatedConfig(fileName = "valid_zh_CN")
public class Config {


}

```
## 3使用 @Validated 开启接口验证 @Email验证邮箱格式

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
    @Validated
    public String demo(IdCardModel idCardModel){
        return testService.test("ac");
    }
    
    @RequestMapping("/idcardCheck2")
    @ResponseBody
    public String demo2(IdCardModel idCardModel){
        return testService.test2("ac");
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


### 4 自定义实现全局异常处理

拦截 ValidatedException 


```bash
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ValidatedException.class)
    @ResponseStatus(HttpStatus.OK)
    public RespMessage ValidationException(ValidatedException e){

        return new RespMessage(-100,e.getMsg());
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
|Size|int,long,short,Integer,Long,Short|验证大小值|
|NotEqualSize|String|验证长度|






```


### 支持自定义注解 如下：

```bash
1、实现  ConstraintValidator 此接口
2、自定义注解如下：  CustomConstraintValidator.class,CustomConstraintValidator2.class 实现类可多个，至少有一个

    @Documented
    @Target({ElementType.FIELD,  ElementType.PARAMETER})
    @Retention(RetentionPolicy.RUNTIME)
    @Constraint(validatedBy = {CustomConstraintValidator.class,CustomConstraintValidator2.class})
    public @interface Custom {
    
        String msg() default "com.spv.valid.Custom.msg";
    
        int min();
    }


3、代码实现



public class CustomConstraintValidator implements ConstraintValidator<Custom,String> {

    @Override
    public boolean isValid(Custom custom,String var1) {
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
