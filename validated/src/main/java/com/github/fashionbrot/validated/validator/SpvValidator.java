package com.github.fashionbrot.validated.validator;

import com.github.fashionbrot.validated.annotation.Mars;
import com.github.fashionbrot.validated.annotation.Validated;
import com.github.fashionbrot.validated.constraint.Constraint;
import com.github.fashionbrot.validated.constraint.ConstraintHelper;
import com.github.fashionbrot.validated.constraint.ConstraintValidator;
import com.github.fashionbrot.validated.constraint.ConstraintValidatorBean;
import com.github.fashionbrot.validated.enums.AnnotationTypeEnum;
import com.github.fashionbrot.validated.enums.ClassTypeEnum;
import com.github.fashionbrot.validated.util.*;
import com.github.fashionbrot.validated.validator.support.AnnotationCustom;
import com.github.fashionbrot.validated.validator.support.AnnotationFieldCustom;
import com.github.fashionbrot.validated.validator.support.AnnotationParameterCustom;
import com.github.fashionbrot.validated.validator.support.ParameterType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class SpvValidator implements Validator {

    public static final String BEAN_NAME="spvValidator";



    private static final String METHOD_NAME_IS_VALID = "isValid";
    private static final String METHOD_NAME_MODIFY = "modify";
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
                case NOT_BLANK:
                    ValidatorUtil.checkNotBlank(parameterType);
                    break;
                case LENGTH:
                    ValidatorUtil.checkLength(parameterType);
                    break;
                case DEFAULT:
                    ValidatorUtil.checkDefault(parameterType, params, index);
                    break;
                case ASSERT_TRUE:
                    ValidatorUtil.checkAssertTrue(parameterType);
                    break;
                case ASSERT_FALSE:
                    ValidatorUtil.checkAssertFalse(parameterType);
                    break;
                case BANKCARD:
                    ValidatorUtil.checkBankCard(parameterType);
                    break;
                case CREDIT_CARD:
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
                case ID_CARD:
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
            Class<?>[] validClass =validated!=null ? validated.validClass():null;
            if (!isValidClass(validClass,clazz)){
                return;
            }


            Field[] fields = clazz.getDeclaredFields();
            if (fields==null || fields.length<=0){
                return;
            }
            Class<?>[] vGroupClass=validated!=null?validated.groups():null;
            for (Field field : fields) {

                Annotation[] annotations = field.getDeclaredAnnotations();
                if (annotations != null && annotations.length > 0) {
                    field.setAccessible(true);

                    for (Annotation annotation : annotations) {

                        if (checkGroup(vGroupClass, annotation)){
                            continue;
                        }

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
                            checkCustomValid(annotation, valueObject, fieldTypeName, fileName,params,index,field);

                        }
                    }

                }
            }
        }
    }


    private boolean isValidClass(Class<?>[] validClass,Class<?> clazz){
        if (validClass==null || validClass.length<=0){
            return true;
        }
        return Arrays.asList(validClass).contains(clazz);
    }


    /**
     * Check to see if the class has a parent
     * @param validated
     * @param clazz
     * @param params
     * @param index
     */
    private void checkClassSuper(Validated validated,Class clazz,Object[] params, int index){
        //获取 superclass 是否是 appClassloader 加载的
        Class superclass = clazz.getSuperclass();
        if (superclass != null) {
            //如果不是定义的类型，则把 class 当做bean 进行校验 field
            boolean exist = ClassTypeEnum.checkClass(superclass.getName());
            if (!exist) {
                entityFieldsAnnotationValid(validated,superclass.getName(), superclass, params, index);
            }
        }
    }



    @Override
    public void parameterAnnotationValid(Method method, Object[] params) {
        long a=System.currentTimeMillis();
        Parameter[] parameters = method.getParameters();
        System.out.println("-----------1:"+(System.currentTimeMillis()-a));
        //Validated validated = method.getDeclaredAnnotation(Validated.class);
        ValidatedMethod validatedMethod = null;
        if (parameters != null && parameters.length > 0) {
            Class<?>[] vGroupClass=null;//validated!=null?validated.groups():null;
            String[] methodParameters =null;
            long aa=System.currentTimeMillis();
            List<ParameterAnnotation> parameterAnnotations=new ArrayList<>(params.length);
            validatedMethod = ValidatorUtil.getMethod(method);
            System.out.println("-----------2:"+(System.currentTimeMillis()-aa));
            if (validatedMethod==null){
                long aaa=System.currentTimeMillis();
                validatedMethod = ValidatedMethod.builder()
                        .parameterAnnotations(parameterAnnotations)
                        .build();
                ValidatorUtil.setMethod(method,validatedMethod);
                System.out.println("-----------3:"+(System.currentTimeMillis()-aaa));
            }else{
                long aaa=System.currentTimeMillis();
                List<ParameterAnnotation> parameterAnnotationList = validatedMethod.getParameterAnnotations();
                if (!CollectionUtils.isEmpty(parameterAnnotationList)){
                    for(int p=0;p<parameterAnnotationList.size();p++){

                        ParameterAnnotation parameterAnnotation = parameterAnnotationList.get(p);

                        List<AnnotationModel> annotationModelList = parameterAnnotation.getAnnotationModelList();
                        if (!CollectionUtils.isEmpty(annotationModelList)){
                            for(int c=0;c<annotationModelList.size();c++){
                                AnnotationModel annotationModel=annotationModelList.get(c);

                                ConstraintValidator constraintValidator = annotationModel.getConstraintValidator();
                                boolean isValid = constraintValidator.isValid(annotationModel.getAnnotation(),params[parameterAnnotation.getIndex()]);
                                if (!isValid){
                                    System.out.println("-----------3:"+(System.currentTimeMillis()-aaa));
                                    ExceptionUtil.throwException(annotationModel.getMsg(),parameterAnnotation.getParamName());
                                }
                            }
                        }
                    }
                }

                return;
            }

            for (int j = 0; j < parameters.length; j++) {



                Parameter parameter = parameters[j];
                int annotationCount = parameter.getAnnotations().length;
                List<AnnotationModel> annotationModelList=new ArrayList<>(annotationCount);
                ParameterAnnotation parameterAnnotation = ParameterAnnotation.builder()
                        .paramName(parameter.getName())
                        .annotationModelList(annotationModelList)
                        .index(j)
                        .build();
                parameterAnnotations.add(parameterAnnotation);


                Class<?> classType = parameter.getType();
                String parameterTypeName = classType.getTypeName();
                //判断是否是 数据类型，还是 bean
                boolean exist = ClassTypeEnum.checkClass(parameterTypeName);
                if (!exist) {
                    //验证 bean 是否实现了 ConstraintValidatorBean 接口
                    validatedByBean(parameter,params, j);

                    //获取 class 的Field[]  验证field 值
                    entityFieldsAnnotationValid(null,parameterTypeName, parameter.getType(), params, j);
                    //entityFieldsAnnotationValid(validated,parameterTypeName, parameter.getType(), params, j);
                } else {
                    Annotation[] annotations = parameter.getDeclaredAnnotations();

                    if (annotations != null && annotations.length > 0) {





                        for (Annotation annotation : annotations) {

                            if (checkGroup(vGroupClass, annotation)){
                                continue;
                            }
                            Class<? extends ConstraintValidator<? extends Annotation, ?>> constraint = ConstraintHelper.getConstraint(annotation.annotationType());
                            if (constraint!=null){

                                ConstraintValidator constraintValidator =MethodUtil.newInstance(constraint);
                                if (constraintValidator!=null){



                                    boolean isValid = constraintValidator.isValid(annotation, params[j]);
                                    String msg = (String) AnnotationUtil.getAnnotationValue(annotation,"msg");
                                    annotationModelList.add(AnnotationModel.builder()
                                            .annotation(annotation)
                                            .constraintValidator(constraintValidator)
                                            .msg(msg)
                                            .build());
                                    if (!isValid){
                                        ExceptionUtil.throwException(msg,parameter.getName());
                                    }
                                }
                            }


                            if (methodParameters==null){
                                validatedMethod = ValidatorUtil.getMethodParameter(method);
                                if (validatedMethod==null){
                                    log.error("getMethodParameter error");
                                    return;
                                }
                                methodParameters=validatedMethod.getParameterNames();
                            }


                            Mars mars = annotation.annotationType().getDeclaredAnnotation(Mars.class);
                            Object valueObject = params[j];



                            String methodParameter = methodParameters[j];

                            if (mars != null) {
                                Class<? extends Annotation> aClass = annotation.annotationType();

                                //constraintValidatorList.add()
                                //validatedMethod.setParameterAnnotations();


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

                                checkCustomValid(annotation,params[j],params,j, methodParameter,null);

                            }
                        }

                    }

                }
            }
        }
    }

    private void validatedByBean(Parameter parameter, Object[] params,int index) {

        Annotation[] annotations = parameter.getDeclaredAnnotations();
        if (annotations!=null && annotations.length>0){
            for(Annotation annotation : annotations){
                Class<? extends Annotation> annotationType = annotation.annotationType();
                Constraint constraint = annotationType.getDeclaredAnnotation(Constraint.class);
                if (constraint != null) {
                    Class<? extends ConstraintValidatorBean<? extends Annotation, ?>>[] classes = constraint.validatedByBean();
                    if (classes == null || classes.length <= 0) {
                        return ;
                    }
                    for (Class clazz : classes) {
                        Class<?>[] interfaces = clazz.getInterfaces();
                        if (interfaces != null && interfaces.length > 0 && interfaces[0].isAssignableFrom(ConstraintValidatorBean.class)) {

                            Object instanceObject = MethodUtil.getInstance(clazz);

                            if (instanceObject != null) {
                                Object value = params[index];
                                /**
                                 * 判断是否实现ConstraintValidator isValid 方法
                                 */
                                Method method = MethodUtil.getMethod(clazz, METHOD_NAME_IS_VALID, Annotation.class, Object.class);
                                if (method != null) {
                                    Object isValid = ReflectionUtils.invokeMethod(method, instanceObject, annotation, value);
                                    if (isValid!=null && !"".equals(isValid)) {
                                        ExceptionUtil.throwException(isValid,value);
                                    }
                                }

                                /**
                                 * 判断是否实现 ConstraintValidator 中的 modify 方法
                                 */
                                Method modifyMethod = getMethod(clazz,METHOD_NAME_MODIFY);
                                if (modifyMethod!=null){
                                    try {
                                        //执行 modify 方法
                                        Object newValue = ReflectionUtils.invokeMethod(modifyMethod, instanceObject, annotation, value);
                                        if (log.isDebugEnabled()){
                                            log.debug("newValue:"+newValue+" oldValue:"+value);
                                        }
                                        params[index] = newValue;
                                    }catch (Exception e){
                                        log.error("invoke method error",e);
                                    }
                                }
                            }

                        }
                    }
                }
            }
        }
    }

    /**
     * check groups matches
     * @param vGroupClass
     * @param annotation
     * @return
     */
    private boolean checkGroup(Class<?>[] vGroupClass, Annotation annotation) {
        //groups==null ,则不验证groups
        if (vGroupClass==null || vGroupClass.length<=0) {
            return false;
        }
        //检测groups 是否匹配
        Method groups = MethodUtil.getAnnotationTypeMethod(annotation.annotationType(), "groups", null);
        if(groups!=null) {
            Class<?>[] groupClass= (Class<?>[]) ReflectionUtils.invokeMethod(groups,annotation);
            if (!checkGroup(vGroupClass,groupClass)){
                return true;
            }
        }
        return false;
    }

    private boolean checkGroup(Class<?>[] vGroup,Class<?>[] aGroup){
        if (aGroup!=null && aGroup.length>0){
            if (log.isDebugEnabled()){
                log.debug("@Validated groups:{}  annotation groups:{}",vGroup,aGroup);
            }
            for(Class v :vGroup){
                if (checkGroup(v,aGroup)){
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkGroup(Class clazz,Class<?>[] aGroup){
        for(Class c: aGroup){
            if (clazz.equals(c)){
                return true;
            }
        }
        return false;
    }

    @Override
    public void checkCustomValid(Annotation annotation,Object value,Object[] params,int index, String fieldName,Field field) {
        Class<? extends Annotation> annotationType = annotation.annotationType();
        Constraint constraint = annotationType.getDeclaredAnnotation(Constraint.class);
        if (constraint != null) {
            Class<? extends ConstraintValidator<? extends Annotation, ?>>[] classes = constraint.validatedBy();
            if (classes == null || classes.length <= 0) {
                return;
            }
            for (Class clazz : classes) {
                Class<?>[] interfaces = clazz.getInterfaces();
                if (interfaces != null && interfaces.length > 0 && interfaces[0].isAssignableFrom(ConstraintValidator.class)) {

                    Object instanceObject = MethodUtil.getInstance(clazz);

                    if (instanceObject != null) {
                        /**
                         * 判断是否实现ConstraintValidator isValid 方法
                         */
                        Method method = MethodUtil.getMethod(clazz, METHOD_NAME_IS_VALID, Annotation.class, Object.class);
                        if (method != null) {
                            boolean isValid = (boolean) ReflectionUtils.invokeMethod(method, instanceObject, annotation, value);

                            if (!isValid ) {
                                Method annotationMethod = MethodUtil.getAnnotationTypeMethod(annotationType, ANNOTATION_MSG);
                                if (annotationMethod!=null){
                                    Object msgValue = ReflectionUtils.invokeMethod(annotationMethod, annotation);
                                    ExceptionUtil.throwException(msgValue, fieldName);
                                }
                            }
                        }

                        /**
                         * 判断是否实现 ConstraintValidator 中的 modify 方法
                         */
                        Method modifyMethod = getMethod(clazz,METHOD_NAME_MODIFY);
                        if (modifyMethod!=null){
                            try {
                                //执行 modify 方法
                                Object newValue = ReflectionUtils.invokeMethod(modifyMethod, instanceObject, annotation, value);
                                if (log.isDebugEnabled()){
                                    log.debug("newValue:"+newValue+" oldValue:"+value);
                                }
                                    //field 字段
                                if (field!=null){
                                    field.set(params[index],newValue);
                                }else{//param 字段
                                    params[index] = newValue;
                                }
                                value = newValue;
                            }catch (Exception e){
                                log.error("invoke method error",e);
                            }
                        }
                    }
                }
            }
        }
    }


    @Override
    public void checkCustomValid(Annotation annotation, Object value, String fieldType, String fieldName,Object[] params,int index, Field field) {

        //判断是否是 数据类型，还是 bean
        boolean exist = ClassTypeEnum.checkClass(fieldType);
        if (exist){
            checkCustomValid(annotation,value,params,index,fieldName,field);
        }
    }


    public Method getMethod(Class clazz,String methodName){
        Method[] methods = clazz.getDeclaredMethods();
        if (methods!=null && methods.length>0){
            for (Method method :methods){
                if (method.getName().equals(methodName)){
                    return method;
                }
            }
        }
        return null;
    }

}
