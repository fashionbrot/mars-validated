package com.github.fashionbrot.validated.config.annotation;

import com.github.fashionbrot.validated.config.ValidatedConfigBeanDefinitionRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;


@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(ValidatedConfigBeanDefinitionRegistrar.class)
public @interface EnableValidatedConfig {

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

}
