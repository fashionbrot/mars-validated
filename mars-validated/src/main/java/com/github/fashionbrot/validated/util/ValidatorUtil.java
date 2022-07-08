package com.github.fashionbrot.validated.util;

import com.github.fashionbrot.validated.config.GlobalValidatedProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.SingletonBeanRegistry;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;


@Slf4j
public class ValidatorUtil implements BeanFactoryAware {

    public static final String BEAN_NAME = "marsValidatorUtil";

    private static GlobalValidatedProperties globalValidatedProperties;

    private static String defaultFileName;

    private static Map<String,ResourceBundle> MSG_MAP=new ConcurrentHashMap<>();

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {

        SingletonBeanRegistry beanRegistry = null;
        if (beanFactory instanceof SingletonBeanRegistry) {
            beanRegistry = (SingletonBeanRegistry) beanFactory;
        } else if (beanFactory instanceof AbstractApplicationContext) {
            // Maybe AbstractApplicationContext or its sub-classes
            beanRegistry = ((AbstractApplicationContext) beanFactory).getBeanFactory();
        }
        if (beanFactory.containsBean(GlobalValidatedProperties.BEAN_NAME)) {
            GlobalValidatedProperties properties = (GlobalValidatedProperties) beanRegistry.getSingleton(GlobalValidatedProperties.BEAN_NAME);
            if (properties != null) {
                globalValidatedProperties = properties;
                defaultFileName = globalValidatedProperties.getFileName()+"_"+globalValidatedProperties.getLanguage();
                log.info("Validator fileName:{} localeParamName:{} language:{}",globalValidatedProperties.getFileName(),globalValidatedProperties.getLocaleParamName(),globalValidatedProperties.getLanguage());
            }
        }
    }


    public static String filterMsg(String msg) {
        if (StringUtil.isEmpty(msg)){
            return null;
        }
        boolean isDefaultMsg = msg.startsWith("com.mars.valid");
        if (isDefaultMsg) {
            msg = getMsg(msg);
        }
        return msg;
    }

    private static String getMsg(String msg) {

        String language = null;

        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes!=null){
            HttpServletRequest request = ((ServletRequestAttributes)requestAttributes).getRequest();
            if (request!=null){
                language = request.getParameter(globalValidatedProperties.getLocaleParamName());
            }
        }
        if (StringUtil.isEmpty(defaultFileName)){
            defaultFileName = "valid_zh_CN";
        }
        ResourceBundle resourceBundle =null;
        if (StringUtil.isEmpty(language)){
            resourceBundle = getResourceBundle(defaultFileName);
        }else{
            String fileName = globalValidatedProperties.getFileName()+"_"+language;
            resourceBundle = getResourceBundle(fileName);
        }
        if (resourceBundle==null){
            throw new MissingResourceException(globalValidatedProperties.getFileName()+"_"+language+" does not exist", "EnableValidatedConfig","language");
        }
        if (resourceBundle.containsKey(msg)){
            msg = resourceBundle.getString(msg);
        }

        return msg;
    }

    public static ResourceBundle getResourceBundle(final String fileName){
        ResourceBundle resourceBundle = MSG_MAP.get(fileName);
        if (resourceBundle==null){
            resourceBundle = ResourceBundle.getBundle(fileName);
            if (resourceBundle!=null){
                MSG_MAP.put(fileName,resourceBundle);
            }
        }
        return resourceBundle;
    }

}
