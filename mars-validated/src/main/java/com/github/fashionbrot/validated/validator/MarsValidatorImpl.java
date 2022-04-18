package com.github.fashionbrot.validated.validator;

import com.github.fashionbrot.validated.annotation.Validated;
import com.github.fashionbrot.validated.constraint.*;
import com.github.fashionbrot.validated.enums.ClassTypeEnum;
import com.github.fashionbrot.validated.util.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.AnnotationUtils;
import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.*;


@Slf4j
public class MarsValidatorImpl implements MarsValidator {

    public static final String BEAN_NAME = "defaultMarsValidatorImpl";

    private static final String METHOD_NAME_MODIFY = "modify";
    private static final String METHOD_VALID_OBJECT = "validObject";
    private static final String MSG = "msg";
    private static final String GROUPS = "groups";


    @Override
    public void entityFieldsAnnotationValid(Validated validated, String valueTypeName, Class<?> clazz, Object[] params, int index) {

        if (!IgnoreClassUtil.checkIgnorePackage(valueTypeName)) {

            // 判断是否 有继承类
            checkClassSuper(validated, clazz, params, index);

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

                Annotation[] annotations = field.getDeclaredAnnotations();
                if (ObjectUtil.isNotEmpty(annotations)) {
                    field.setAccessible(true);

                    for (Annotation annotation : annotations) {

                        Class<?> valueType = field.getType();
                        String fieldName = field.getName();
                        validated(validated, params, index, valueType, fieldName, annotation, field);
                    }
                }
            }
        }
    }


    private boolean isValidClass(Class<?>[] validClass, Class<?> clazz) {
        if (ObjectUtil.isEmpty(validClass)) {
            return true;
        }
        return Arrays.asList(validClass).contains(clazz);
    }


    /**
     * Check to see if the class has a parent
     *
     * @param validated
     * @param clazz
     * @param params
     * @param index     return  List<MarsViolation>
     */
    private void checkClassSuper(Validated validated, Class clazz, Object[] params, int index) {
        //获取 superclass 是否是 appClassloader 加载的
        Class superclass = clazz.getSuperclass();
        if (superclass != null) {
            //如果不是定义的类型，则把 class 当做bean 进行校验 field
            boolean exist = ClassTypeEnum.checkClass(superclass.getName());
            if (!exist) {
                entityFieldsAnnotationValid(validated, superclass.getName(), superclass, params, index);
            }
        }
    }

    @Override
    public void returnValueAnnotationValid(Validated validated, Object object) {
        //验证当前参数是类，还是普通类型
        try {
            ClassTypeEnum classTypeEnum = ClassTypeEnum.getValue(object.getClass().getName());
            if (classTypeEnum == null) {
                //验证参数属性
                entityFieldsAnnotationValid(validated, object.getClass().getTypeName(), object.getClass(), new Object[]{object}, 0);
            }
            ExceptionUtil.throwException();
        } finally {
            ExceptionUtil.reset();
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

                    Annotation[] annotations = parameter.getDeclaredAnnotations();
                    if (ObjectUtil.isNotEmpty(annotations)) {
                        //直接验证参数
                        for (Annotation annotation : annotations) {

                            validated(validated, params, j, parameter.getType(), parameter.getName(), annotation, null);
                            //验证当前参数是类，还是普通类型
                            ClassTypeEnum classTypeEnum = ClassTypeEnum.getValue(parameterTypeName);
                            if (classTypeEnum == null) {
                                //验证参数属性
                                entityFieldsAnnotationValid(validated, parameterTypeName, classType, params, j);
                            }
                        }
                    } else {
                        //验证参数属性
                        entityFieldsAnnotationValid(validated, parameterTypeName, classType, params, j);
                    }
                }
            }

            ExceptionUtil.throwException();
        } finally {
            ExceptionUtil.reset();
        }
    }


    private void validated(Validated validated,
                           Object[] params,
                           int index,
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
        if (StringUtil.isNotEmpty(constraintValidatorList)) {

            validatedConstrain(constraintValidatorList, annotationAttributes, failFast, annotation, params, index, paramName, valueType, field);

        } else {
            Class<? extends Annotation> annotationType = annotation.annotationType();
            if (annotationType.isAnnotationPresent(Constraint.class)) {
                Constraint constraint = annotationType.getDeclaredAnnotation(Constraint.class);
                if (constraint != null) {
                    Class<? extends ConstraintValidator<? extends Annotation, ?>>[] classes = constraint.validatedBy();
                    if (ObjectUtil.isEmpty(classes)) {
                        return;
                    }
                    ConstraintHelper.putConstraintValidator(annotation.annotationType(), classes);
                    constraintValidatorList = ConstraintHelper.getConstraint(annotation.annotationType());
                    if (ObjectUtil.isNotEmpty(constraintValidatorList)) {
                        validatedConstrain(constraintValidatorList, annotationAttributes, failFast, annotation, params, index, paramName, valueType, field);
                    }
                }
            }
        }
    }


    private void validatedConstrain(List<ConstraintValidator> constraintValidatorList,
                                    Map<String, Object> annotationAttributes,
                                    boolean failFast,
                                    Annotation annotation,
                                    Object[] params,
                                    int index,
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
                    addMarsViolations(value, paramName, annotation, constraintValidatorList, (String) annotationAttributes.get(MSG));
                    if (failFast) {
                        ExceptionUtil.throwException();
                    }
                }

                Class validConstraintClass = constraintValidator.getClass();
                if (MethodUtil.checkDeclaredMethod(validConstraintClass, METHOD_NAME_MODIFY)) {
                    Object reValue = constraintValidator.modify(annotation, value, valueType);
                    if (reValue != null) {
                        if (field != null) {
                            Object param = params[index];
                            Object formatValue = StringUtil.formatObject(reValue, field.getType());
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
                                params[index] = StringUtil.formatObject(reValue, valueType);
                            }
                        }
                    }
                }
                if (MethodUtil.checkDeclaredMethod(validConstraintClass, METHOD_VALID_OBJECT)) {
                    String msg = constraintValidator.validObject(annotation, value, valueType);
                    if (StringUtil.isNotEmpty(msg)) {
                        addMarsViolations(value, paramName, annotation, constraintValidatorList, msg);
                        if (failFast) {
                            ExceptionUtil.throwException();
                        }
                    }
                }
            }
        }
    }

    private void addMarsViolations(Object value, String paramName, Annotation annotation, List<ConstraintValidator> constraintValidatorList, String msg) {
        ExceptionUtil.addMarsViolation(MarsViolation.builder()
                .annotationName(annotation.annotationType().getName())
                .fieldName(paramName)
                .msg(ValidatorUtil.filterMsg(msg))
                .value(value)
                .build());
    }


    /**
     * check groups matches
     *
     * @param vGroupClass
     * @param attributes
     * @return
     */
    private boolean checkGroup(Class<?>[] vGroupClass, Map<String, Object> attributes) {
        //groups==null ,则不验证groups
        if (ObjectUtil.isEmpty(vGroupClass)) {
            return false;
        }
        //检测groups 是否匹配
        if (attributes.containsKey(GROUPS)) {
            Class[] groups = (Class[]) attributes.get(GROUPS);
            //如果 vGroupClass 不为空，则默认 annotation 注解 groups=DefaultGroup.class
            if (ObjectUtil.isEmpty(groups)) {
                return false;
            }
            if (checkGroup(vGroupClass, groups)) {
                return false;
            }
            return true;
        }

        //如果 vGroupClass 不为空，则默认 annotation 注解 groups=DefaultGroup.class
        return false;
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