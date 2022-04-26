package com.github.fashionbrot.validated.util;

import com.github.fashionbrot.validated.config.GlobalValidatedProperties;
import com.github.fashionbrot.validated.config.ValidatedMethodPostProcessor;
import com.github.fashionbrot.validated.spring.intercept.ValidatedMethodIntercept;
import com.github.fashionbrot.validated.validator.MarsValidatorImpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.SingletonBeanRegistry;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.env.PropertyResolver;

import java.lang.reflect.Constructor;
import java.util.*;

import static org.springframework.beans.factory.BeanFactoryUtils.beanNamesForTypeIncludingAncestors;


@Slf4j
public class BeanUtil {

    private static final String[] EMPTY_BEAN_NAMES = new String[0];


    public static void registerglobalValidatedProperties(AnnotationAttributes attributes, BeanDefinitionRegistry registry, PropertyResolver propertyResolver, String beanName) {

        registerGlobalProperties(attributes, registry, propertyResolver, beanName);
    }


    public static void registerGlobalProperties(Map<?, ?> globalProperties,
                                                BeanDefinitionRegistry registry,
                                                PropertyResolver propertyResolver,
                                                String beanName) {
        if (ObjectUtil.isNotEmpty(globalProperties)) {
            Properties properties = resolveProperties(globalProperties, propertyResolver);
            if (properties != null) {
                GlobalValidatedProperties validatedProperties = GlobalValidatedProperties.builder()
                        .fileName(properties.getProperty(GlobalValidatedProperties.FILENAME, "valid"))
                        .localeParamName(properties.getProperty(GlobalValidatedProperties.LOCALE_PARAM_NAME, "lang"))
                        .language(properties.getProperty("language", "zh_CN"))
                        .build();
                if (propertyResolver.containsProperty("mars.validated.file-name")) {
                    validatedProperties.setFileName(propertyResolver.getProperty("mars.validated.file-name", "valid"));
                }
                if (propertyResolver.containsProperty("mars.validated.language")) {
                    validatedProperties.setLanguage(propertyResolver.getProperty("mars.validated.language", "zh_CN"));
                }
                if (propertyResolver.containsProperty("mars.validated.locale-param-name")) {
                    validatedProperties.setLocaleParamName(propertyResolver.getProperty("mars.validated.locale-param-name", "lang"));
                }
                registerSingleton(registry, beanName, validatedProperties);
            }
        } else {
            GlobalValidatedProperties validatedProperties = GlobalValidatedProperties.builder().build();
            if (propertyResolver.containsProperty("mars.validated.file-name")) {
                validatedProperties.setFileName(propertyResolver.getProperty("mars.validated.file-name", "valid"));
            }
            if (propertyResolver.containsProperty("mars.validated.language")) {
                validatedProperties.setLanguage(propertyResolver.getProperty("mars.validated.language", "zh_CN"));
            }
            if (propertyResolver.containsProperty("mars.validated.locale-param-name")) {
                validatedProperties.setLocaleParamName(propertyResolver.getProperty("mars.validated.locale-param-name", "lang"));
            }
            registerSingleton(registry, beanName, validatedProperties);
        }

    }


    public static void registerValidatedMethodPostProcessor(BeanDefinitionRegistry registry) {
        registerInfrastructureBeanIfAbsent(registry, ValidatedMethodPostProcessor.BEAN_NAME, ValidatedMethodPostProcessor.class);
    }


    public static void registerValidatedMethodInterceptor(BeanDefinitionRegistry registry) {
        registerInfrastructureBeanIfAbsent(registry, ValidatedMethodIntercept.BEAN_NAME, ValidatedMethodIntercept.class);
    }

    public static void registerValidated(BeanDefinitionRegistry registry) {

        registerInfrastructureBeanIfAbsent(registry, MarsValidatorImpl.BEAN_NAME, MarsValidatorImpl.class);

        registerInfrastructureBeanIfAbsent(registry, ValidatorUtil.BEAN_NAME, ValidatorUtil.class);

    }


    public static Properties resolveProperties(Map<?, ?> properties, PropertyResolver propertyResolver) {
        PropertiesPlaceholderResolver propertiesPlaceholderResolver = new PropertiesPlaceholderResolver(propertyResolver);
        return propertiesPlaceholderResolver.resolve(properties);
    }


    /**
     * Register an object to be Singleton Bean
     *
     * @param registry        {@link BeanDefinitionRegistry}
     * @param beanName        bean name
     * @param singletonObject singleton object
     */
    public static void registerSingleton(BeanDefinitionRegistry registry, String beanName, Object singletonObject) {
        SingletonBeanRegistry beanRegistry = null;
        if (registry instanceof SingletonBeanRegistry) {
            beanRegistry = (SingletonBeanRegistry) registry;
        } else if (registry instanceof AbstractApplicationContext) {
            // Maybe AbstractApplicationContext or its sub-classes
            beanRegistry = ((AbstractApplicationContext) registry).getBeanFactory();
        }
        // Register Singleton Object if possible
        if (beanRegistry != null) {
            beanRegistry.registerSingleton(beanName, singletonObject);
        }
    }


    /**
     * Register Infrastructure Bean if absent
     *
     * @param registry        {@link BeanDefinitionRegistry}
     * @param beanName        the name of bean
     * @param beanClass       the class of bean
     * @param constructorArgs the arguments of {@link Constructor}
     */
    public static void registerInfrastructureBeanIfAbsent(BeanDefinitionRegistry registry, String beanName, Class<?> beanClass,
                                                          Object... constructorArgs) {
        if (!isBeanDefinitionPresent(registry, beanName, beanClass) && !registry.containsBeanDefinition(beanName)) {
            registerInfrastructureBean(registry, beanName, beanClass, constructorArgs);
        }
    }

    /**
     * Register Infrastructure Bean
     *
     * @param registry        {@link BeanDefinitionRegistry}
     * @param beanName        the name of bean
     * @param beanClass       the class of bean
     * @param constructorArgs the arguments of {@link Constructor}
     */
    public static void registerInfrastructureBean(BeanDefinitionRegistry registry, String beanName, Class<?> beanClass,
                                                  Object... constructorArgs) {
        // Build a BeanDefinition for serviceFactory class
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.rootBeanDefinition(beanClass);
        for (Object constructorArg : constructorArgs) {
            beanDefinitionBuilder.addConstructorArgValue(constructorArg);
        }
        // ROLE_INFRASTRUCTURE
        beanDefinitionBuilder.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
        // Register
        registry.registerBeanDefinition(beanName, beanDefinitionBuilder.getBeanDefinition());
    }


    /**
     * Is {@link BeanDefinition} present in {@link BeanDefinitionRegistry}
     *
     * @param registry        {@link BeanDefinitionRegistry}
     * @param beanName        the name of bean
     * @param targetBeanClass the type of bean
     * @return If Present , return <code>true</code>
     */
    public static boolean isBeanDefinitionPresent(BeanDefinitionRegistry registry, String beanName, Class<?> targetBeanClass) {
        String[] beanNames = BeanUtil.getBeanNames((ListableBeanFactory) registry, targetBeanClass);
        return ArrayUtils.contains(beanNames, beanName);
    }


    /**
     * Get Bean Names from {@link ListableBeanFactory} by type.
     *
     * @param beanFactory {@link ListableBeanFactory}
     * @param beanClass   The  {@link Class} of Bean
     * @return If found , return the array of Bean Names , or empty array.
     */
    public static String[] getBeanNames(ListableBeanFactory beanFactory, Class<?> beanClass) {
        return getBeanNames(beanFactory, beanClass, false);
    }


    public static String[] getBeanNames(ListableBeanFactory beanFactory, Class<?> beanClass,
                                        boolean includingAncestors) {
        // Issue : https://github.com/alibaba/spring-context-support/issues/22
        if (includingAncestors) {
            return beanNamesForTypeIncludingAncestors(beanFactory, beanClass, true, false);
        } else {
            return beanFactory.getBeanNamesForType(beanClass, true, false);
        }
    }


}
