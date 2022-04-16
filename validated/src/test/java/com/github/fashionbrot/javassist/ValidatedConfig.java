package com.github.fashionbrot.javassist;

//import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
//@Import(JavassistValidatedConfigBeanDefinitionRegistrar.class)
public @interface ValidatedConfig {

    /**
     * properties file name
     * @return
     */
    String fileName()  default "valid";

    /**
     * language
     * @return
     */
    String language() default "zh_CN";
    /**
     * request locale param name
     * @return
     */
    String localeParamName() default "lang";

    String scanPackage();

}
