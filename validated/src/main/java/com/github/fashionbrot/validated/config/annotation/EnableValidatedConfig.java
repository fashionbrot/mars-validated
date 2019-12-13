package com.github.fashionbrot.validated.config.annotation;

import com.github.fashionbrot.validated.config.ValidatedConfigBeanDefinitionRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;


@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(ValidatedConfigBeanDefinitionRegistrar.class)
public @interface EnableValidatedConfig {

    String DEFAULTFILENAME="valid_zh_CN";

    String fileName()  default DEFAULTFILENAME;



}
