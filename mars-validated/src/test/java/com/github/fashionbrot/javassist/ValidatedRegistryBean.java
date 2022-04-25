//package com.github.fashionbrot.javassist;
//
//import org.springframework.beans.BeansException;
//import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
//import org.springframework.beans.factory.support.BeanDefinitionBuilder;
//import org.springframework.beans.factory.support.BeanDefinitionRegistry;
//import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
//import org.springframework.beans.factory.support.GenericBeanDefinition;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.ApplicationContextAware;
//import org.springframework.context.EnvironmentAware;
//import org.springframework.context.ResourceLoaderAware;
//import org.springframework.core.env.Environment;
//import org.springframework.core.io.Resource;
//import org.springframework.core.io.ResourceLoader;
//import org.springframework.core.io.support.ResourcePatternResolver;
//import org.springframework.core.io.support.ResourcePatternUtils;
//import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
//import org.springframework.core.type.classreading.MetadataReader;
//import org.springframework.core.type.classreading.MetadataReaderFactory;
//import org.springframework.stereotype.Component;
//import org.springframework.util.ClassUtils;
//
//import java.io.IOException;
//import java.util.LinkedHashSet;
//import java.util.Set;
//
//
//@Component
//public class ValidatedRegistryBean implements ApplicationContextAware, BeanDefinitionRegistryPostProcessor, ResourceLoaderAware, EnvironmentAware {
//
//    public static final String BEAN_NAME="marsValidatedRegistryBean";
//
//    private ApplicationContext ctx;
//    @Override
//    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
//        System.out.println("postProcessBeanFactory");
//    }
//    @Override
//    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {
//
//
////        Class<?> cls = beanDefinitionRegistry.getBeanDefinition("notEmptyController").getClass();
////        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(cls);
////        GenericBeanDefinition definition = (GenericBeanDefinition) builder.getRawBeanDefinition();
////        definition.getPropertyValues().add("interfaceClass", definition.getBeanClassName());
////        definition.setBeanClass(ValidatedProxyFactory.class);
////        definition.setAutowireMode(GenericBeanDefinition.AUTOWIRE_BY_TYPE);
////        // 注册bean名,一般为类名首字母小写
////        beanDefinitionRegistry.registerBeanDefinition(cls.getSimpleName(), definition);
//
//        Set<Class<?>> beanClazzs  = scannerPackages("com.github.fashionbrot.controller");
//        for (Class beanClazz : beanClazzs) {
//            BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(beanClazz);
//            GenericBeanDefinition definition = (GenericBeanDefinition) builder.getRawBeanDefinition();
//
//            //在这里，我们可以给该对象的属性注入对应的实例。
//            //比如mybatis，就在这里注入了dataSource和sqlSessionFactory，
//            // 注意，如果采用definition.getPropertyValues()方式的话，
//            // 类似definition.getPropertyValues().add("interfaceType", beanClazz);
//            // 则要求在FactoryBean（本应用中即ServiceFactory）提供setter方法，否则会注入失败
//            // 如果采用definition.getConstructorArgumentValues()，
//            // 则FactoryBean中需要提供包含该属性的构造方法，否则会注入失败
//            definition.getConstructorArgumentValues().addGenericArgumentValue(beanClazz);
//
//            //注意，这里的BeanClass是生成Bean实例的工厂，不是Bean本身。
//            // FactoryBean是一种特殊的Bean，其返回的对象不是指定类的一个实例，
//            // 其返回的是该工厂Bean的getObject方法所返回的对象。
//            definition.setBeanClass(ValidatedProxyFactory.class);
//
//            //这里采用的是byType方式注入，类似的还有byName等
//            definition.setAutowireMode(GenericBeanDefinition.AUTOWIRE_BY_TYPE);
//            beanDefinitionRegistry.registerBeanDefinition(beanClazz.getSimpleName(), definition);
//        }
//
////        List<Class> classList = BeanUtil.registerJavassistClass("com/github/fashionbrot/controller", beanDefinitionRegistry.getClass().getClassLoader());
////        if (ObjectUtil.isNotEmpty(classList)){
////            for (Class clazz :classList){
////                // 需要被代理的接口
////                Class<?> cls = beanDefinitionRegistry.getBeanDefinition("notEmptyController").getClass();
////                BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(cls);
////                GenericBeanDefinition definition = (GenericBeanDefinition) builder.getRawBeanDefinition();
////                definition.getPropertyValues().add("interfaceClass", definition.getBeanClassName());
////                definition.setBeanClass(ValidatedProxyFactory.class);
////                definition.setAutowireMode(GenericBeanDefinition.AUTOWIRE_BY_TYPE);
////                // 注册bean名,一般为类名首字母小写
////                beanDefinitionRegistry.registerBeanDefinition(cls.getSimpleName(), definition);
////            }
////        }
//
//    }
//    @Override
//    public void setApplicationContext(ApplicationContext ctx) throws BeansException {
//        this.ctx = ctx;
//        this.applicationContext  = ctx;
//    }
//
//    private Set<Class<?>> scannerPackages(String basePackage) {
//        Set<Class<?>> set = new LinkedHashSet<>();
//        String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX +
//                resolveBasePackage(basePackage) + '/' + DEFAULT_RESOURCE_PATTERN;
//        try {
//            Resource[] resources = this.resourcePatternResolver.getResources(packageSearchPath);
//            for (Resource resource : resources) {
//                if (resource.isReadable()) {
//                    MetadataReader metadataReader = this.metadataReaderFactory.getMetadataReader(resource);
//                    String className = metadataReader.getClassMetadata().getClassName();
//                    Class<?> clazz;
//                    try {
//                        clazz = Class.forName(className);
//                        set.add(clazz);
//                    } catch (ClassNotFoundException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return set;
//    }
//    private static final String DEFAULT_RESOURCE_PATTERN = "**/*.class";
//
//    private MetadataReaderFactory metadataReaderFactory;
//
//    protected String resolveBasePackage(String basePackage) {
//        return ClassUtils.convertClassNameToResourcePath(this.getEnvironment().resolveRequiredPlaceholders(basePackage));
//    }
//    private ResourcePatternResolver resourcePatternResolver;
//
//    private ApplicationContext applicationContext;
//
//    @Override
//    public void setResourceLoader(ResourceLoader resourceLoader) {
//        this.resourcePatternResolver = ResourcePatternUtils.getResourcePatternResolver(resourceLoader);
//        this.metadataReaderFactory = new CachingMetadataReaderFactory(resourceLoader);
//    }
//
//    private Environment getEnvironment() {
//        return applicationContext.getEnvironment();
//    }
//
//    @Override
//    public void setEnvironment(Environment environment) {
//
//    }
//}
