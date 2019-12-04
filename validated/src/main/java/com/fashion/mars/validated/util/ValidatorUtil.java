package com.fashion.mars.validated.util;

import com.fashion.mars.validated.annotation.*;
import com.fashion.mars.validated.config.GlobalValidatedProperties;
import com.fashion.spv.validated.annotation.*;
import com.fashion.mars.validated.validator.support.ParameterType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.SingletonBeanRegistry;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.regex.Pattern;


@Slf4j
public class ValidatorUtil implements BeanFactoryAware {

    public static final String BEAN_NAME = "spvValidatorUtil";


    private final static LocalVariableTableParameterNameDiscoverer discoverer = new LocalVariableTableParameterNameDiscoverer();


    private static ResourceBundle resourceBundle;

    private static String fileName;

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
                fileName = properties.getFileName();
                resourceBundle = ResourceBundle.getBundle(fileName);
            }
        }
    }


    private static final Pattern pattern = Pattern.compile("com.spv.valid");





    private static final Pattern BANKCARD_PATTERN = Pattern.compile(PatternSts.BANKCARD_REGEXP);

    private static final Pattern CREDIT_CARD_PATTERN = Pattern.compile(PatternSts.CREDITCARD_REGEXP);

    private static final Pattern EMAIL_PATTERN = Pattern.compile(PatternSts.EMAIL_REGEXP);

    private static final Pattern PHONE_PATTERN = Pattern.compile(PatternSts.PHONE_REGEXP);
    private static final Pattern ID_CARD_PATTERN = Pattern.compile(PatternSts.IDCARD_REGEXP);


    /**
     * 根据 method 获取参数名称
     *
     * @param method get method parameter
     * @return String[]
     */
    public static String[] getMethodParameter(Method method) {
        return discoverer.getParameterNames(method);
    }


    public static void checkNotBlank(ParameterType parameterType) {
        Object value = parameterType.getValue();
        NotBlank notBlank = parameterType.getAnnotationCustom().getDeclaredAnnotation(parameterType, NotBlank.class);
        if (value == null || StringUtil.isBlank(value.toString())) {
            ExceptionUtil.throwException(notBlank.msg(), parameterType.getFieldName());
        }
    }


    public static void checkLength(ParameterType parameterType) {
        Object value = parameterType.getValue();
        Length length = parameterType.getAnnotationCustom().getDeclaredAnnotation(parameterType, Length.class);
        if (length != null) {
            int valueLength = 0;
            int min = length.min();
            int max = length.max();
            if (value != null) {
                valueLength = value.toString().length();
            }
            if (value == null || min > valueLength || valueLength > max) {
                String msg = filterMsg(length.msg());
                msg = String.format(msg, min, max);
                ExceptionUtil.throwExceptionNotCheckMsg(msg, parameterType.getFieldName());
            }
        }
    }

    public static void checkDefault(ParameterType parameterType, Object[] params, int index) {
        Default aDefault = parameterType.getAnnotationCustom().getDeclaredAnnotation(parameterType, Default.class);
        if (aDefault != null) {
            if (parameterType.getField()!=null){
                if (StringUtils.isEmpty(parameterType.getValue())){
                    parameterType.getAnnotationCustom().setDefault(parameterType, aDefault.value(), params, index);
                }
            }else if (parameterType.getParameter()!=null){
                if (StringUtils.isEmpty(params[index])) {
                    parameterType.getAnnotationCustom().setDefault(parameterType, aDefault.value(), params, index);
                }
            }
        }
    }


    public static void checkAssertTrue(ParameterType parameterType) {
        AssertTrue assertTrue = parameterType.getAnnotationCustom().getDeclaredAnnotation(parameterType, AssertTrue.class);
        if (assertTrue != null) {
            Object value = parameterType.getValue();
            String strValue = StringUtil.formatString(value);
            if (!"true".equalsIgnoreCase(strValue)) {
                ExceptionUtil.throwException(assertTrue.msg(), parameterType.getFieldName());
            }
        }
    }

    public static void checkAssertFalse(ParameterType parameterType) {
        AssertFalse assertFalse = parameterType.getAnnotationCustom().getDeclaredAnnotation(parameterType, AssertFalse.class);
        if (assertFalse != null) {
            Object value = parameterType.getValue();
            String strValue = StringUtil.formatString(value);
            if (!"false".equalsIgnoreCase(strValue)) {
                ExceptionUtil.throwException(assertFalse.msg(), parameterType.getFieldName());
            }
        }
    }

    public static void checkBankCard(ParameterType parameterType) {
        BankCard bankCard = parameterType.getAnnotationCustom().getDeclaredAnnotation(parameterType, BankCard.class);
        if (bankCard != null) {
            Object value = parameterType.getValue();
            String str = StringUtil.formatString(value);
            if (StringUtil.isBlank(str)) {
                ExceptionUtil.throwException(bankCard.msg(), parameterType.getFieldName());
            } else {
                String regexp = bankCard.regexp();
                if (BANKCARD_PATTERN.pattern().equals(regexp)) {
                    boolean isMatcher = BANKCARD_PATTERN.matcher(str).matches();
                    if (!isMatcher) {
                        ExceptionUtil.throwException(bankCard.msg(), parameterType.getFieldName());
                    }
                } else {
                    Pattern pattern ;
                    if (StringUtil.isBlank(regexp)) {
                        pattern = BANKCARD_PATTERN;
                    } else {
                        pattern = Pattern.compile(regexp);
                    }
                    if (!pattern.matcher(str).matches()) {
                        ExceptionUtil.throwException(bankCard.msg(), parameterType.getFieldName());
                    }
                }
            }
        }
    }

    public static void checkCreditCard(ParameterType parameterType) {
        CreditCard creditCard = parameterType.getAnnotationCustom().getDeclaredAnnotation(parameterType, CreditCard.class);
        if (creditCard != null) {
            Object value = parameterType.getValue();
            String str = StringUtil.formatString(value);
            if (StringUtil.isBlank(str)) {
                ExceptionUtil.throwException(creditCard.msg(), parameterType.getFieldName());
            } else {
                String regexp = creditCard.regexp();
                if (CREDIT_CARD_PATTERN.pattern().equals(regexp)) {
                    boolean isMatcher = CREDIT_CARD_PATTERN.matcher(str).matches();
                    if (!isMatcher) {
                        ExceptionUtil.throwException(creditCard.msg(), parameterType.getFieldName());
                    }
                } else {
                    Pattern pattern ;
                    if (StringUtil.isBlank(regexp)) {
                        pattern = CREDIT_CARD_PATTERN;
                    } else {
                        pattern = Pattern.compile(regexp);
                    }

                    if (!pattern.matcher(str).matches()) {
                        ExceptionUtil.throwException(creditCard.msg(), parameterType.getFieldName());
                    }
                }
            }
        }
    }

    public static void checkSize(ParameterType parameterType) {
        Object objectValue = parameterType.getValue();
        Size size = parameterType.getAnnotationCustom().getDeclaredAnnotation(parameterType, Size.class);
        if (size != null) {
            String msg = filterMsg(size.msg());
            long value = 0;
            long min = size.min();
            long max = size.max();
            if (objectValue != null) {
                value = StringUtil.getLongValue(objectValue);
            }
            if (objectValue == null || min > value || value > max) {
                if (StringUtil.isNotBlank(msg)) {
                    msg = String.format(msg, min, max);
                    ExceptionUtil.throwExceptionNotCheckMsg(msg, parameterType.getFieldName());
                }
            }
        }
    }

    public static void checkNotNull(ParameterType parameterType) {
        Object value = parameterType.getValue();
        NotNull notBlank = parameterType.getAnnotationCustom().getDeclaredAnnotation(parameterType, NotNull.class);
        if (value == null) {
            ExceptionUtil.throwException(notBlank.msg(), parameterType.getFieldName());
        }
    }

    public static void checkPattern(ParameterType parameterType) {
        com.fashion.mars.validated.annotation.Pattern pattern = parameterType.getAnnotationCustom().getDeclaredAnnotation(parameterType, com.fashion.mars.validated.annotation.Pattern.class);
        if (pattern != null) {
            Object objectValue = parameterType.getValue();
            if (objectValue == null) {
                ExceptionUtil.throwException(pattern.msg(), parameterType.getFieldName());
            } else {
                String regexp = pattern.regexp();
                String str = StringUtil.formatString(objectValue);
                if (StringUtil.isNotBlank(regexp)) {
                    Pattern patternV = Pattern.compile(pattern.regexp());
                    if (!patternV.matcher(str).matches()) {
                        ExceptionUtil.throwException(pattern.msg(), parameterType.getFieldName());
                    }
                }
            }
        }
    }


    public static void checkEmail(ParameterType parameterType) {
        Email email = parameterType.getAnnotationCustom().getDeclaredAnnotation(parameterType, Email.class);
        if (email != null) {
            Object objectValue = parameterType.getValue();
            String regexp = email.regexp();
            String value = StringUtil.formatString(objectValue);
            if (StringUtil.isBlank(value)) {
                ExceptionUtil.throwException(email.msg(), parameterType.getFieldName());
            }
            if (EMAIL_PATTERN.pattern().equals(regexp)) {
                boolean isMatcher = EMAIL_PATTERN.matcher(value).matches();
                if (!isMatcher) {
                    ExceptionUtil.throwException(email.msg(), parameterType.getFieldName());
                }
            } else {
                Pattern pattern ;
                if (StringUtil.isBlank(regexp)) {
                    pattern = EMAIL_PATTERN;
                } else {
                    pattern = Pattern.compile(regexp);
                }
                if (!pattern.matcher(value).matches()) {
                    ExceptionUtil.throwException(email.msg(), parameterType.getFieldName());
                }
            }
        }
    }

    public static void checkPhone(ParameterType parameterType) {
        Phone phone = parameterType.getAnnotationCustom().getDeclaredAnnotation(parameterType, Phone.class);
        if (phone != null) {
            Object objectValue = parameterType.getValue();
            String regexp = phone.regexp();
            String value = StringUtil.formatString(objectValue);

            if (StringUtil.isBlank(value)) {
                ExceptionUtil.throwException(phone.msg(), parameterType.getFieldName());
            } else {
                if (PHONE_PATTERN.pattern().equals(regexp)) {
                    boolean isMatcher = PHONE_PATTERN.matcher(value).matches();
                    if (!isMatcher) {
                        ExceptionUtil.throwException(phone.msg(), parameterType.getFieldName());
                    }
                } else {
                    Pattern pattern ;
                    if (StringUtil.isBlank(regexp)) {
                        pattern = PHONE_PATTERN;
                    } else {
                        pattern = Pattern.compile(regexp);
                    }
                    if (!pattern.matcher(value).matches()) {
                        ExceptionUtil.throwException(phone.msg(), parameterType.getFieldName());
                    }
                }
            }

        }
    }

    public static void checkDigits(ParameterType parameterType) {
        Digits digits = parameterType.getAnnotationCustom().getDeclaredAnnotation(parameterType, Digits.class);
        if (digits != null) {
            String strValue = StringUtil.formatString(parameterType.getValue());
            if (!StringUtil.isDigits(strValue)) {
                ExceptionUtil.throwException(digits.msg(), parameterType.getFieldName());
            }
        }
    }

    public static void checkIdCard(ParameterType parameterType) {
        IdCard idCard = parameterType.getAnnotationCustom().getDeclaredAnnotation(parameterType, IdCard.class);
        if (idCard != null) {
            Object objectValue = parameterType.getValue();
            String regexp = idCard.regexp();
            String value = StringUtil.formatString(objectValue);

            if (StringUtil.isBlank(value)) {
                ExceptionUtil.throwException(idCard.msg(), parameterType.getFieldName());
            } else {
                if (ID_CARD_PATTERN.pattern().equals(regexp)) {
                    boolean isMatcher = ID_CARD_PATTERN.matcher(value).matches();
                    if (!isMatcher) {
                        ExceptionUtil.throwException(idCard.msg(), parameterType.getFieldName());
                    }
                    if (idCard.below18()) {
                        checkIdCard(value, idCard.below18Msg(), parameterType);
                    }
                } else {
                    Pattern pattern ;
                    if (StringUtil.isBlank(regexp)) {
                        pattern = ID_CARD_PATTERN;
                    } else {
                        pattern = Pattern.compile(regexp);
                    }

                    if (!pattern.matcher(value).matches()) {
                        ExceptionUtil.throwException(idCard.msg(), parameterType.getFieldName());
                    }
                    if (idCard.below18()) {
                        checkIdCard(value, idCard.below18Msg(), parameterType);
                    }
                }
            }

        }
    }

    private static void checkIdCard(String idCard, String msg, ParameterType parameterType) {
        if (idCard.length() == 18) {
            String chkidcard = idCard.substring(6, 10);//截取年
            Calendar cal = Calendar.getInstance();
            int year = cal.get(Calendar.YEAR);
            if ((year - StringUtil.getIntValue(chkidcard) < 18)) {
                ExceptionUtil.throwException(msg, parameterType.getFieldName());
            }
        }
    }


    public static String filterMsg(String msg) {
        boolean isDefaultMsg = pattern.matcher(msg).lookingAt();
        if (isDefaultMsg) {
            msg = getMsg(msg);

        }
        return msg;
    }

    private static String getMsg(String msg) {
        if (resourceBundle.containsKey(msg)) {
            msg = resourceBundle.getString(msg);
        } else {
            MissingResourceException exception = new MissingResourceException(" is not key", fileName, msg);
            log.error("fileName:" + fileName + " is not key:" + msg, exception);
        }
        return msg;
    }


}
