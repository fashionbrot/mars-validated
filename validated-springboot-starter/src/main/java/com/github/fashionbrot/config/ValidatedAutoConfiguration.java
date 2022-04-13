package com.github.fashionbrot.config;

import com.github.fashionbrot.validated.config.ValidatedConfigBeanDefinitionRegistrar;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@EnableConfigurationProperties(ValidatedConfigurationProperties.class)
@Import(value = {
        ValidatedConfigBeanDefinitionRegistrar.class
})
public class ValidatedAutoConfiguration {



}
