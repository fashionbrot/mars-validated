package com.fashion.mars.validated.config;

import com.fashion.mars.validated.config.annotation.EnableValidatedConfig;
import com.fashion.mars.validated.util.BeanUtil;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;

import static org.springframework.core.annotation.AnnotationAttributes.fromMap;

public class ValidatedConfigBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar, EnvironmentAware {

    private Environment environment;

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata metadata, BeanDefinitionRegistry registry) {

        AnnotationAttributes attributes = fromMap(metadata.getAnnotationAttributes(EnableValidatedConfig.class.getName()));


        BeanUtil.registerglobalValidatedProperties(attributes,registry,environment, GlobalValidatedProperties.BEAN_NAME);

        BeanUtil.registerValieator(registry);

        BeanUtil.registerValidatedMethodInterceptor(registry);

        BeanUtil.registerValidatedMethodPostProcessor(registry);

    }
}
