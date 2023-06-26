package com.github.fashionbrot.validated.validator;

import com.github.fashionbrot.validated.annotation.Valid;
import com.github.fashionbrot.validated.annotation.Validated;
import com.github.fashionbrot.validated.constraint.*;
import com.github.fashionbrot.validated.exception.ValidatedException;
import com.github.fashionbrot.validated.groups.DefaultGroup;
import com.github.fashionbrot.validated.util.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.*;
import java.util.stream.Collectors;


@Slf4j
public class MarsValidatorImpl implements MarsValidator , BeanFactoryAware {

    private BeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    public static final String BEAN_NAME = "defaultMarsValidatorImpl";
    private static final String METHOD_NAME_MODIFY = "modify";
    private static final String MSG = "msg";
    private static final String GROUPS = "groups";


    public void entityFieldsAnnotationValid(Validated validated, String valueTypeName, Class<?> clazz, Object[] params,Integer paramIndex) {


        // 判断是否 有继承类
        checkClassSuper(validated, clazz, params,paramIndex);

        //如果填写 validClass
        Class<?>[] validClass = validated != null ? validated.validClass() : null;
        if (!isValidClass(validClass, clazz)) {
            return;
        }


        Field[] fields = clazz.getDeclaredFields();
        if (ObjectUtil.isEmpty(fields)) {
            return;
        }

        for (Field field : fields) {
            if (JavaUtil.isFinal(field)) {
                continue;
            }
            Class<?> fieldClassType = field.getType();
            String fieldName = field.getName();


            List<Annotation> validAnnotation = getValidAnnotation(field.getDeclaredAnnotations());
            if (ObjectUtil.isNotEmpty(validAnnotation)) {
                for (Annotation annotation : validAnnotation) {
                    validated(validated, params,paramIndex, fieldClassType, fieldName, annotation, field);
                }
            }else{
                Valid valid = field.getDeclaredAnnotation(Valid.class);
                String typeName = fieldClassType.getTypeName();
                if (JavaUtil.isArray(typeName)) {
                    if (valid==null){
                        continue;
                    }
                    validArrayObject(validated,field,params,paramIndex);
                } else if (JavaUtil.isCollection(typeName)) {
                    if (valid==null){
                        continue;
                    }
                    validListObject(validated,field, params,paramIndex);
                } else {
                    //验证参数属性
                    entityFieldsAnnotationValid(validated, typeName, fieldClassType, params, paramIndex);
                }
            }
        }

    }



    private void checkClassSuper(Validated validated, Class clazz, Object[] params,Integer valueIndex) {
        Class superclass = clazz.getSuperclass();
        if (superclass != null && JavaUtil.isNotObject(superclass)) {
            //如果不是定义的类型，则把 class 当做bean 进行校验 field
            entityFieldsAnnotationValid(validated, superclass.getName(), superclass, params,valueIndex);
        }
    }

    @Override
    public void returnValueAnnotationValid(Validated validated, Object object) {
        try {
            if (JavaUtil.isNotPrimitive(object.getClass().getName())) {
                //验证参数属性
                entityFieldsAnnotationValid(validated, object.getClass().getTypeName(), object.getClass(), new Object[]{object},0);
            }
            if (!validated.failFast()) {
                ExceptionUtil.throwException();
            }
        } finally {
            if (!validated.failFast()) {
                ExceptionUtil.reset();
            }
        }
    }


    @Override
    public void parameterAnnotationValid(Method method, Object[] params) {
        Parameter[] parameters = method.getParameters();
        Validated validated = method.getDeclaredAnnotation(Validated.class);
        try {

            if (ObjectUtil.isNotEmpty(parameters)) {
                for (int j = 0; j < parameters.length; j++) {

                    Parameter parameter = parameters[j];
                    Class<?> classType = parameter.getType();
                    String parameterTypeName = classType.getTypeName();
                    if (JavaUtil.isMvcIgnoreParams(parameterTypeName)) {
                        continue;
                    }
                    Annotation[] declaredAnnotations = parameter.getDeclaredAnnotations();

                    List<Annotation> validAnnotation = getValidAnnotation(declaredAnnotations);
                    if (ObjectUtil.isNotEmpty(validAnnotation)) {

                        for (int i = 0; i < validAnnotation.size(); i++) {
                            validated(validated, params,j, parameter.getType(), parameter.getName(), validAnnotation.get(i), null);
                        }

                    } else {
                        Valid valid = parameter.getDeclaredAnnotation(Valid.class);
                        if (JavaUtil.isArray(parameterTypeName) ) {
                            if (valid==null){
                                continue;
                            }
                            validArrayObject(validated,parameter.getType(),params,j,parameter.getName());
                        } else if (JavaUtil.isCollection(parameterTypeName)) {
                            if (valid==null){
                                continue;
                            }
                            validListObject(validated,parameter,params,j);
                        } else {
                            //验证参数属性
                            entityFieldsAnnotationValid(validated, parameterTypeName, classType, params, j);
                        }
                    }
                }
            }
            if (!validated.failFast()) {
                ExceptionUtil.throwException();
            }

        } finally {
            if (!validated.failFast()) {
                ExceptionUtil.reset();
            }
        }
    }


    public void validArrayObject(Validated validated, Class objectClass,Object[] params,Integer paramIndex, String paramName) {
        Class convertClass = objectClass.getComponentType();
        if (JavaUtil.isNotPrimitive(convertClass.getTypeName())) {
            Object[] array = (Object[]) params[paramIndex];
            if (ObjectUtil.isNotEmpty(array)) {
                for (int objIndex = 0; objIndex < array.length; objIndex++) {
                    entityFieldsAnnotationValid(validated, paramName, convertClass, array , objIndex);
                }
            }
        }
    }

    public void validArrayObject(Validated validated, Field field,Object[] params,Integer paramIndex) {
        Class convertClass = field.getType().getComponentType();
        if (JavaUtil.isNotPrimitive(convertClass.getTypeName())) {
            Object[] array = (Object[]) MethodUtil.getFieldValue(field, params[paramIndex]);
            if (ObjectUtil.isNotEmpty(array)) {
                for (int objIndex = 0; objIndex < array.length; objIndex++) {
                    entityFieldsAnnotationValid(validated, field.getName(), convertClass, array , objIndex);
                }
            }
        }
    }


    public void validListObject(Validated validated,Parameter parameter,Object[] params ,Integer paramIndex ){
        Type[] actualTypeArguments = TypeUtil.getActualTypeArguments(parameter);
        if (ObjectUtil.isNotEmpty(actualTypeArguments) &&
            actualTypeArguments[0] instanceof Class &&
            JavaUtil.isNotPrimitive(actualTypeArguments[0].getTypeName())) {

            Class typeConvertClass = TypeUtil.typeConvertClass(actualTypeArguments[0]);
            if (typeConvertClass != null && params[paramIndex] instanceof List) {
                List param = (List) params[paramIndex];

                if (ObjectUtil.isNotEmpty(param)) {
                    for (int listIndex = 0; listIndex < param.size(); listIndex++) {

                        entityFieldsAnnotationValid(validated, parameter.getName(), typeConvertClass, param.toArray(),listIndex);
                    }
                }
            }
        }
    }

    public void validListObject(Validated validated,Field field,Object[] params ,Integer paramIndex  ){
        Type[] actualTypeArguments = TypeUtil.getActualTypeArguments(field);
        if (ObjectUtil.isNotEmpty(actualTypeArguments) &&
            actualTypeArguments[0] instanceof Class &&
            JavaUtil.isNotPrimitive(actualTypeArguments[0].getTypeName())) {

            Class typeConvertClass = TypeUtil.typeConvertClass(actualTypeArguments[0]);
            if (typeConvertClass != null ) {
                List param = (List)MethodUtil.getFieldValue(field, params[paramIndex]);
                if (ObjectUtil.isNotEmpty(param)) {
                    for (int listIndex = 0; listIndex < param.size(); listIndex++) {

                        entityFieldsAnnotationValid(validated, field.getName(), typeConvertClass, param.toArray(),listIndex);
                    }
                }
            }
        }
    }

    private List<Annotation> getValidAnnotation(Annotation[] annotations) {
        if (ObjectUtil.isNotEmpty(annotations)) {
            return Arrays.stream(annotations)
                .filter(annotation -> isMarsValidAnnotation(annotation))
                .collect(Collectors.toList());
        }
        return null;
    }

    private boolean isMarsValidAnnotation(Annotation annotation){
        return annotation!=null && (ConstraintHelper.containsKey(annotation.annotationType()) || annotation.annotationType().isAnnotationPresent(Constraint.class));
    }


    private void validated(Validated validated,
                           Object[] params,
                           Integer paramIndex,
                           Class<?> valueType,
                           String paramName,
                           Annotation annotation,
                           Field field) {

        boolean failFast = validated != null ? validated.failFast() : true;
        Class<?>[] vGroupClass = validated != null ? validated.groups() : null;

        Map<String, Object> annotationAttributes = AnnotationUtils.getAnnotationAttributes(annotation);
        if (checkGroup(vGroupClass, annotationAttributes)) {
            return;
        }

        List<ConstraintValidator> constraintValidatorList = ConstraintHelper.getConstraint(annotation.annotationType());
        if (ObjectUtil.isEmpty(constraintValidatorList)) {
            Constraint constraint = annotation.annotationType().getDeclaredAnnotation(Constraint.class);
            if (constraint != null) {
                Class<? extends ConstraintValidator<? extends Annotation, ?>>[] classes = constraint.validatedBy();
                if (ObjectUtil.isEmpty(classes)) {
                    return;
                }
                ConstraintHelper.putConstraintValidator(beanFactory,annotation.annotationType(), classes);
                constraintValidatorList = ConstraintHelper.getConstraint(annotation.annotationType());
            }
        }
        if (ObjectUtil.isNotEmpty(constraintValidatorList)) {
            validatedConstrain(constraintValidatorList, annotationAttributes, failFast, annotation, params,paramIndex, paramName, valueType, field);
        }
    }


    private void validatedConstrain(List<ConstraintValidator> constraintValidatorList,
                                    Map<String, Object> annotationAttributes,
                                    boolean failFast,
                                    Annotation annotation,
                                    Object[] params,
                                    Integer index,
                                    String paramName,
                                    Class valueType,
                                    Field field) {

        if (ObjectUtil.isNotEmpty(constraintValidatorList)) {
            Object value = params[index];
            if (field != null) {
                value = MethodUtil.getFieldValue(field, params[index]);
            }
            for (int i = 0; i < constraintValidatorList.size(); i++) {

                ConstraintValidator constraintValidator = constraintValidatorList.get(i);
                boolean isValid = constraintValidator.isValid(annotation, value, valueType);
                if (!isValid) {
                    if (failFast) {
                        ValidatedException.throwMsg(paramName, ValidatorUtil.filterMsg((String) annotationAttributes.get(MSG)), annotation.annotationType().getName(), value,index);
                    } else {
                        addMarsViolations(value, paramName, annotation, (String) annotationAttributes.get(MSG),index);
                    }
                }

                Class validConstraintClass = constraintValidator.getClass();
                if (MethodUtil.checkDeclaredMethod(validConstraintClass, METHOD_NAME_MODIFY)) {
                    Object reValue = constraintValidator.modify(annotation, value, valueType);
                    if (reValue != null) {
                        if (field != null) {
                            Object param = params[index];
                            Object formatValue = ObjectUtil.formatObject(reValue, field.getType());
                            if (formatValue != null && field.getType() == formatValue.getClass()) {
                                try {
                                    field.set(param, formatValue);
                                } catch (IllegalAccessException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                log.error("mars valid error setField fieldName:{} annotation:{} oldType:{} newType:{} ", field.getName(), annotation.annotationType().getName(), field.getType(), reValue.getClass());
                            }
                        } else {
                            if (reValue != null) {
                                params[index] = ObjectUtil.formatObject(reValue, valueType);
                            }
                        }
                    }
                }
            }
        }
    }




    private void addMarsViolations(Object value, String paramName, Annotation annotation, String msg,Integer valueIndex) {
        ExceptionUtil.addMarsViolation(MarsViolation.builder()
                .annotationName(annotation.annotationType().getName())
                .fieldName(paramName)
                .msg(ValidatorUtil.filterMsg(msg))
                .value(value)
                .valueIndex(valueIndex)
            .build());
    }


    private boolean isValidClass(Class<?>[] validClass, Class<?> clazz) {
        if (ObjectUtil.isEmpty(validClass)) {
            return true;
        }
        return Arrays.asList(validClass).contains(clazz);
    }


    /**
     * check groups matches
     *
     * @param validatedGroupClass
     * @param attributes
     * @return boolean
     */
    private boolean checkGroup(Class<?>[] validatedGroupClass, Map<String, Object> attributes) {
        //groups==null ,则不验证groups
        if (ObjectUtil.isEmpty(validatedGroupClass)) {
            return false;
        }
        Class[] groups = (Class[]) attributes.get(GROUPS);
        //issue#6 如果注解 groups 为空，则默认 annotation 注解 groups=DefaultGroup.class
        if (checkGroup(DefaultGroup.class,validatedGroupClass)) {
            return false;
        }
        if (checkGroup(validatedGroupClass, groups)) {
            return false;
        }
        return true;

    }

    private boolean checkGroup(Class<?>[] vGroup, Class<?>[] aGroup) {
        if (ObjectUtil.isNotEmpty(aGroup)) {
            if (log.isDebugEnabled()) {
                log.debug("@Validated groups:{}  annotation groups:{}", vGroup, aGroup);
            }
            for (Class v : vGroup) {
                if (checkGroup(v, aGroup)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkGroup(Class clazz, Class<?>[] aGroup) {
        for (Class c : aGroup) {
            if (clazz.equals(c)) {
                return true;
            }
        }
        return false;
    }




}
