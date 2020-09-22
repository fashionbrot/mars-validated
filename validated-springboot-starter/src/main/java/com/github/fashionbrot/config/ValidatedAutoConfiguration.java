package com.github.fashionbrot.config;

import com.github.fashionbrot.validated.config.ValidatedConfigBeanDefinitionRegistrar;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(value = {
        ValidatedConfigBeanDefinitionRegistrar.class
})
public class ValidatedAutoConfiguration {


}
