package com.github.fashionbrot.validated.util;

import com.github.fashionbrot.validated.config.GlobalValidatedProperties;
import com.github.fashionbrot.validated.config.ValidatedMethodPostProcessor;
import com.github.fashionbrot.validated.spring.intercept.ValidatedMethodIntercept;
import com.github.fashionbrot.validated.validator.SpvValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.SingletonBeanRegistry;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.env.PropertyResolver;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.*;


@Slf4j
public class BeanUtil {

    private static final String[] EMPTY_BEAN_NAMES = new String[0];



    public static void registerglobalValidatedProperties(AnnotationAttributes attributes, BeanDefinitionRegistry registry, PropertyResolver propertyResolver, String beanName) {

        registerGlobalProperties(attributes, registry, propertyResolver,beanName);
    }


    public static void registerGlobalProperties(Map<?,?> globalProperties ,
                                                     BeanDefinitionRegistry registry,
                                                     PropertyResolver propertyResolver,
                                                     String beanName) {
        if (!CollectionUtils.isEmpty(globalProperties)){
            Properties properties = resolveProperties(globalProperties,propertyResolver);
            if (properties!=null) {
                GlobalValidatedProperties validatedProperties = GlobalValidatedProperties.builder()
                        .fileName(properties.getProperty(GlobalValidatedProperties.FILENAME,"valid"))
                        .localeParamName(properties.getProperty(GlobalValidatedProperties.LOCALE_PARAM_NAME,"lang"))
                        .language(properties.getProperty("language","zh_CN"))
                        .build();
                if(propertyResolver.containsProperty("mars.validated.file-name")){
                    validatedProperties.setFileName(propertyResolver.getProperty("mars.validated.file-name","valid"));
                }
                if(propertyResolver.containsProperty("mars.validated.language")){
                    validatedProperties.setLanguage(propertyResolver.getProperty("mars.validated.language","zh_CN"));
                }
                if(propertyResolver.containsProperty("mars.validated.locale-param-name")){
                    validatedProperties.setLocaleParamName(propertyResolver.getProperty("mars.validated.locale-param-name","lang"));
                }
                registerSingleton(registry, beanName,validatedProperties );
            }
        }else{
            GlobalValidatedProperties validatedProperties = GlobalValidatedProperties.builder().build();
            if(propertyResolver.containsProperty("mars.validated.file-name")){
                validatedProperties.setFileName(propertyResolver.getProperty("mars.validated.file-name","valid"));
            }
            if(propertyResolver.containsProperty("mars.validated.language")){
                validatedProperties.setLanguage(propertyResolver.getProperty("mars.validated.language","zh_CN"));
            }
            if(propertyResolver.containsProperty("mars.validated.locale-param-name")){
                validatedProperties.setLocaleParamName(propertyResolver.getProperty("mars.validated.locale-param-name","lang"));
            }
            registerSingleton(registry, beanName,validatedProperties );
        }

    }


    public static void registerValidatedMethodPostProcessor(BeanDefinitionRegistry registry) {
        registerInfrastructureBeanIfAbsent(registry, ValidatedMethodPostProcessor.BEAN_NAME,ValidatedMethodPostProcessor.class);
    }


    public static void registerValidatedMethodInterceptor(BeanDefinitionRegistry registry) {
        registerInfrastructureBeanIfAbsent(registry, ValidatedMethodIntercept.BEAN_NAME,ValidatedMethodIntercept.class);
    }

    public static void registerValieator(BeanDefinitionRegistry registry) {

        registerInfrastructureBeanIfAbsent(registry, SpvValidator.BEAN_NAME,SpvValidator.class);

        registerInfrastructureBeanIfAbsent(registry, ValidatorUtil.BEAN_NAME,ValidatorUtil.class);

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


    /**
     * Get Bean Names from {@link ListableBeanFactory} by type.
     *
     * @param beanFactory        {@link ListableBeanFactory}
     * @param beanClass          The  {@link Class} of Bean
     * @param includingAncestors including ancestors or not
     * @return If found , return the array of Bean Names , or empty array.
     */
    public static String[] getBeanNames(ListableBeanFactory beanFactory, Class<?> beanClass,
                                        boolean includingAncestors) {

        final BeanFactory actualBeanFactory;


        if (beanFactory instanceof ConfigurableApplicationContext) {


            ConfigurableApplicationContext applicationContext = ConfigurableApplicationContext.class.cast(beanFactory);

            actualBeanFactory = applicationContext.getBeanFactory();

        } else {

            actualBeanFactory = beanFactory;

        }


        if (actualBeanFactory instanceof ConfigurableListableBeanFactory) {

            return getBeanNames((ConfigurableListableBeanFactory) actualBeanFactory, beanClass, includingAncestors);

        }

        return EMPTY_BEAN_NAMES;

    }


    /**
     * Get Bean Names from {@link ConfigurableListableBeanFactory} by type.
     *
     * @param beanFactory        {@link ConfigurableListableBeanFactory}
     * @param beanClass          The  {@link Class} of Bean
     * @param includingAncestors including ancestors or not
     * @return If found , return the array of Bean Names , or empty array.
     */
    public static String[] getBeanNames(ConfigurableListableBeanFactory beanFactory, Class<?> beanClass,
                                        boolean includingAncestors) {

        Set<String> beanNames = new LinkedHashSet<String>();

        beanNames.addAll(doGetBeanNames(beanFactory, beanClass));

        if (includingAncestors) {

            BeanFactory parentBeanFactory = beanFactory.getParentBeanFactory();

            if (parentBeanFactory instanceof ConfigurableListableBeanFactory) {

                ConfigurableListableBeanFactory configurableListableBeanFactory =
                        (ConfigurableListableBeanFactory) parentBeanFactory;

                String[] parentBeanNames = getBeanNames(configurableListableBeanFactory, beanClass, includingAncestors);

                beanNames.addAll(Arrays.asList(parentBeanNames));

            }

        }

        return StringUtils.toStringArray(beanNames);
    }

    /**
     * Get Bean names from {@link ConfigurableListableBeanFactory} by type
     *
     * @param beanFactory {@link ConfigurableListableBeanFactory}
     * @param beanType    The  {@link Class type} of Bean
     * @return the array of bean names.
     */
    protected static Set<String> doGetBeanNames(ConfigurableListableBeanFactory beanFactory, Class<?> beanType) {

        String[] allBeanNames = beanFactory.getBeanDefinitionNames();

        Set<String> beanNames = new LinkedHashSet<String>();

        for (String beanName : allBeanNames) {

            BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanName);

            Class<?> beanClass = resolveBeanType(beanFactory, beanDefinition);

            if (beanClass != null && ClassUtils.isAssignable(beanType, beanClass)) {

                beanNames.add(beanName);

            }

        }

        return Collections.unmodifiableSet(beanNames);

    }

    private static Class<?> resolveBeanType(ConfigurableListableBeanFactory beanFactory, BeanDefinition beanDefinition) {

        String factoryBeanName = beanDefinition.getFactoryBeanName();

        ClassLoader classLoader = beanFactory.getBeanClassLoader();

        Class<?> beanType = null;

        if (StringUtils.hasText(factoryBeanName)) {

            beanType = getFactoryBeanType(beanFactory, beanDefinition);

        }

        if (beanType == null) {

            String beanClassName = beanDefinition.getBeanClassName();

            if (StringUtils.hasText(beanClassName)) {

                beanType = resolveBeanType(beanClassName, classLoader);

            }

        }

        if (beanType == null) {

            if (log.isErrorEnabled()) {

                String message = beanDefinition + " can't be resolved bean type!";

                log.error(message);
            }

        }

        return beanType;

    }

    private static Class<?> getFactoryBeanType(ConfigurableListableBeanFactory beanFactory,
                                               BeanDefinition factoryBeanDefinition) {

        BeanDefinition actualFactoryBeanDefinition = factoryBeanDefinition;

        final List<Class<?>> beanClasses = new ArrayList<Class<?>>(1);

        ClassLoader classLoader = beanFactory.getBeanClassLoader();

        String factoryBeanClassName = actualFactoryBeanDefinition.getBeanClassName();

        if (StringUtils.isEmpty(factoryBeanClassName)) {

            String factoryBeanName = factoryBeanDefinition.getFactoryBeanName();

            actualFactoryBeanDefinition = beanFactory.getBeanDefinition(factoryBeanName);

            factoryBeanClassName = actualFactoryBeanDefinition.getBeanClassName();

        }

        if (StringUtils.hasText(factoryBeanClassName)) {

            Class<?> factoryBeanClass = resolveBeanType(factoryBeanClassName, classLoader);

            final String factoryMethodName = factoryBeanDefinition.getFactoryMethodName();

            // @Configuration only allow one method FactoryBean
            ReflectionUtils.doWithMethods(factoryBeanClass, new ReflectionUtils.MethodCallback() {

                @Override
                public void doWith(Method method) throws IllegalArgumentException, IllegalAccessException {

                    beanClasses.add(method.getReturnType());

                }
            }, new ReflectionUtils.MethodFilter() {

                @Override
                public boolean matches(Method method) {
                    return factoryMethodName.equals(method.getName());
                }
            });

        }

        return beanClasses.isEmpty() ? null : beanClasses.get(0);

    }

    /**
     * Resolve Bean Type
     *
     * @param beanClassName the class name of Bean
     * @param classLoader   {@link ClassLoader}
     * @return Bean type if can be resolved , or return <code>null</code>.
     */
    public static Class<?> resolveBeanType(String beanClassName, ClassLoader classLoader) {

        if (!StringUtils.hasText(beanClassName)) {
            return null;
        }

        Class<?> beanType = null;

        try {

            beanType = ClassUtils.resolveClassName(beanClassName, classLoader);

            beanType = ClassUtils.getUserClass(beanType);

        } catch (Exception e) {

            if (log.isErrorEnabled()) {
                log.error(e.getMessage(), e);
            }

        }

        return beanType;

    }


}
