//package com.github.fashionbrot.javassist;
//
//import com.github.fashionbrot.validated.config.GlobalValidatedProperties;
//import com.github.fashionbrot.validated.util.BeanUtil;
//import com.github.fashionbrot.validated.util.StringUtil;
//import org.springframework.beans.factory.support.BeanDefinitionRegistry;
//import org.springframework.context.EnvironmentAware;
//import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
//import org.springframework.core.annotation.AnnotationAttributes;
//import org.springframework.core.env.Environment;
//import org.springframework.core.type.AnnotationMetadata;
//
//import static org.springframework.core.annotation.AnnotationAttributes.fromMap;
//
//public class JavassistValidatedConfigBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar, EnvironmentAware {
//
//    private Environment environment;
//
//    @Override
//    public void setEnvironment(Environment environment) {
//        this.environment = environment;
//    }
//
//    @Override
//    public void registerBeanDefinitions(AnnotationMetadata metadata, BeanDefinitionRegistry registry) {
//
//        AnnotationAttributes attributes = fromMap(metadata.getAnnotationAttributes(ValidatedConfig.class.getName()));
//
//
//        BeanUtil.registerglobalValidatedProperties(attributes,registry,environment, GlobalValidatedProperties.BEAN_NAME);
//
//
//        BeanUtil.registerValidated(registry);
//
//        if (StringUtil.isEmpty(attributes.getString("scanPackage"))) {
//
//            BeanUtil.registerValidatedMethodInterceptor(registry);
//
//            BeanUtil.registerValidatedMethodPostProcessor(registry);
//        }else{
////            BeanUtil.registerJavassistClass(attributes.getString("scanPackage"));
//
//            BeanUtil.registerJavassist(registry);
//        }
//
//
//    }
//}
