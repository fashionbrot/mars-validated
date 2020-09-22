package com.github.fashionbrot.config;

import com.github.fashionbrot.validated.config.ValidatedConfigBeanDefinitionRegistrar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

@Configuration
@EnableConfigurationProperties(ValidatedConfigurationProperties.class)
@Import(value = {
        ValidatedConfigBeanDefinitionRegistrar.class
})
public class ValidatedAutoConfiguration {



}
