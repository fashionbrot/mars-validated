package com.github.fashionbrot.validated.validator;

import com.github.fashionbrot.validated.annotation.Mars;
import com.github.fashionbrot.validated.annotation.Validated;
import com.github.fashionbrot.validated.constraint.Constraint;
import com.github.fashionbrot.validated.constraint.ConstraintValidator;
import com.github.fashionbrot.validated.enums.AnnotationTypeEnum;
import com.github.fashionbrot.validated.enums.ClassTypeEnum;
import com.github.fashionbrot.validated.util.*;
import com.github.fashionbrot.validated.validator.support.AnnotationCustom;
import com.github.fashionbrot.validated.validator.support.AnnotationFieldCustom;
import com.github.fashionbrot.validated.validator.support.AnnotationParameterCustom;
import com.github.fashionbrot.validated.validator.support.ParameterType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ReflectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;

@Slf4j
public class SpvValidator implements Validator {

    public static final String BEAN_NAME="spvValidator";



    private static final String METHOD_NAME = "isValid";
    private static final String ANNOTATION_MSG = "msg";


    private AnnotationCustom annotationFieldCustom = new AnnotationFieldCustom();

    private AnnotationCustom annotationParameterCustom = new AnnotationParameterCustom();


    @Override
    public void validParameter(AnnotationTypeEnum annotationTypeEnum, ParameterType parameterType, Object[] params, int index) {
        validParameterAndEntityFields( annotationTypeEnum, parameterType, params, index);
    }


    @Override
    public void validEntityFields(AnnotationTypeEnum annotationTypeEnum, ParameterType parameterType, Object[] params, int index) {
        validParameterAndEntityFields(annotationTypeEnum,parameterType, params, index);
    }

    private void validParameterAndEntityFields(AnnotationTypeEnum annotationTypeEnum, ParameterType parameterType, Object[] params, int index) {


        if (annotationTypeEnum != null) {
            switch (annotationTypeEnum) {
                case NOTBLANK:
                    ValidatorUtil.checkNotBlank(parameterType);
                    break;
                case LENGTH:
                    ValidatorUtil.checkLength(parameterType);
                    break;
                case DEFAULT:
                    ValidatorUtil.checkDefault(parameterType, params, index);
                    break;
                case ASSERTTRUE:
                    ValidatorUtil.checkAssertTrue(parameterType);
                    break;
                case ASSERTFALSE:
                    ValidatorUtil.checkAssertFalse(parameterType);
                    break;
                case BANKCARD:
                    ValidatorUtil.checkBankCard(parameterType);
                    break;
                case CREDITCARD:
                    ValidatorUtil.checkCreditCard(parameterType);
                    break;
                case SIZE:
                    ValidatorUtil.checkSize(parameterType);
                    break;
                case NOTNULL:
                    ValidatorUtil.checkNotNull(parameterType);
                    break;
                case PATTERN:
                    ValidatorUtil.checkPattern(parameterType);
                    break;
                case EMAIL:
                    ValidatorUtil.checkEmail(parameterType);
                    break;
                case PHONE:
                    ValidatorUtil.checkPhone(parameterType);
                    break;
                case IDCARD:
                    ValidatorUtil.checkIdCard(parameterType);
                    break;
                case DIGITS:
                    ValidatorUtil.checkDigits(parameterType);
                    break;
                case NOT_EMPTY:
                    ValidatorUtil.checkNotEmpty(parameterType);
                    break;
                case NOT_EQUAL_SIZE:
                    ValidatorUtil.checkNotEqualSize(parameterType);
                default:
                    break;
            }
        }
    }





    @Override
    public void entityFieldsAnnotationValid(Validated validated, String valueTypeName, Class<?> clazz, Object[] params, int index) {

        if (!IgnoreClassUtil.checkIgnorePackage(valueTypeName)) {

            // 判断是否 有继承类
            checkClassSuper(validated,clazz, params, index);

            /**
             * 如果填写 validClass
             */
            Class<?>[] validClass = validated.validClass();
            if (!isValidClass(validClass,clazz)){
                return;
            }


            Field[] fields = clazz.getDeclaredFields();

            for (Field field : fields) {

                Annotation[] annotations = field.getDeclaredAnnotations();
                if (annotations != null && annotations.length > 0) {
                    field.setAccessible(true);

                    for (Annotation annotation : annotations) {


                        Mars mars = annotation.annotationType().getDeclaredAnnotation(Mars.class);
                        String fileName = field.getName();
                        Object valueObject = MethodUtil.getMethod(clazz, field, params[index], fileName);

                        if (mars != null) {

                            String annotationName = annotation.annotationType().getSimpleName();
                            AnnotationTypeEnum annotationTypeEnum = AnnotationTypeEnum.getValue(annotationName);
                            ParameterType parameterType = new ParameterType(valueObject, field, annotationFieldCustom);

                            if (annotationTypeEnum == AnnotationTypeEnum.DEFAULT) {
                                validEntityFields(annotationTypeEnum, parameterType, params, index);
                            } else {
                                validEntityFields(annotationTypeEnum, parameterType, null, 0);
                            }

                        } else {

                            String fieldTypeName = field.getType().getName();
                            checkCustomValid(annotation, valueObject, fieldTypeName, fileName);

                        }
                    }

                }
            }
        }
    }


    private boolean isValidClass(Class<?>[] validClass,Class<?> clazz){
        if (validClass.length==0){
            return true;
        }
        return Arrays.asList(validClass).contains(clazz);
    }


    private void checkClassSuper(Validated validated,Class clazz,Object[] params, int index){
        //获取 superclass 是否是 appClassloader 加载的
        Class superclass = clazz.getSuperclass();
        if (superclass != null) {
            String superValueTypeName = superclass.getName();
            ClassTypeEnum classTypeEnum = ClassTypeUtil.getClassTypeEnum(superValueTypeName);
            if (classTypeEnum == null) {
                entityFieldsAnnotationValid(validated,superValueTypeName, superclass, params, index);
            }
        }
    }



    @Override
    public void parameterAnnotationValid(Method method, Object[] params) {
        Parameter[] parameters = method.getParameters();

        Validated validated = method.getDeclaredAnnotation(Validated.class);

        if (parameters != null && parameters.length > 0) {

            String[] methodParameters =null;
            for (int j = 0; j < parameters.length; j++) {

                if (methodParameters==null){
                    methodParameters=ValidatorUtil.getMethodParameter(method);
                }

                Parameter parameter = parameters[j];


                Class<?> classType = parameter.getType();
                String parameterTypeName = classType.getTypeName();
                //判断是否是 数据类型，还是 bean
                ClassTypeEnum classTypeEnum = ClassTypeUtil.getClassTypeEnum(parameterTypeName);
                if (classTypeEnum == null) {
                    //获取 class 的Field[]  验证码field 值
                    entityFieldsAnnotationValid(validated,parameterTypeName, parameter.getType(), params, j);
                } else {
                    Annotation[] annotations = parameter.getDeclaredAnnotations();

                    if (annotations != null && annotations.length > 0) {

                        for (Annotation annotation : annotations) {

                            Mars mars = annotation.annotationType().getDeclaredAnnotation(Mars.class);
                            Object valueObject = params[j];
                            String methodParameter = methodParameters[j];

                            if (mars != null) {

                                String annotationName = annotation.annotationType().getSimpleName();
                                AnnotationTypeEnum annotationTypeEnum = AnnotationTypeEnum.getValue(annotationName);
                                ParameterType parameterType = new ParameterType(methodParameter, valueObject, parameter, annotationParameterCustom);

                                //验证 基础 参数
                                if (annotationTypeEnum == AnnotationTypeEnum.DEFAULT) {
                                    validParameter(annotationTypeEnum, parameterType, params, j);
                                } else {
                                    validParameter(annotationTypeEnum, parameterType, null, 0);
                                }

                            } else {

                                checkCustomValid(annotation, valueObject, classTypeEnum, methodParameter);

                            }
                        }
                    }

                }
            }
        }
    }


    @Override
    public void checkCustomValid(Annotation annotation, Object value, ClassTypeEnum fieldType, String fieldName) {
        Class<? extends Annotation> annotationType = annotation.annotationType();
        Constraint constraint = annotationType.getDeclaredAnnotation(Constraint.class);
        if (constraint != null) {
            Class<? extends ConstraintValidator<? extends Annotation, ?>>[] classes = constraint.validatedBy();
            if (classes == null || classes.length <= 0) {
                return;
            }
            for (Class clazz : classes) {
                Class<?>[] interfaces = clazz.getInterfaces();
                if (interfaces != null && interfaces.length == 1 && interfaces[0].isAssignableFrom(ConstraintValidator.class)) {

                    Object instanceObject = MethodUtil.getInstance(clazz);
                    if (instanceObject != null) {

                        Method method = MethodUtil.getMethod(clazz, METHOD_NAME, Annotation.class, Object.class);
                        if (method != null) {
                            boolean isValid = (boolean) ReflectionUtils.invokeMethod(method, instanceObject, annotation, value);

                            if (!isValid) {

                                Method annotationMethod = MethodUtil.getAnnotationTypeMethod(annotationType, ANNOTATION_MSG, (Class<?>) null);
                                Object object = ReflectionUtils.invokeMethod(annotationMethod, annotation, (Object) null);

                                ExceptionUtil.throwException(object, fieldName);
                            }
                        }
                    }
                }
            }
        }
    }


    @Override
    public void checkCustomValid(Annotation annotation, Object value, String fieldType, String fieldName) {

        //判断是否是 数据类型，还是 bean
        ClassTypeEnum classTypeEnum = ClassTypeUtil.getClassTypeEnum(fieldType);
        if (classTypeEnum!=null){
            checkCustomValid(annotation,value,classTypeEnum,fieldName);
        }
    }

}
