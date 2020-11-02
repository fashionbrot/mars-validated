package com.github.fashionbrot.validated.validator;

import com.github.fashionbrot.validated.annotation.Default;
import com.github.fashionbrot.validated.annotation.Validated;
import com.github.fashionbrot.validated.constraint.*;
import com.github.fashionbrot.validated.enums.AnnotationTypeEnum;
import com.github.fashionbrot.validated.enums.ClassTypeEnum;
import com.github.fashionbrot.validated.util.*;
import com.github.fashionbrot.validated.validator.support.AnnotationCustom;
import com.github.fashionbrot.validated.validator.support.AnnotationFieldCustom;
import com.github.fashionbrot.validated.validator.support.AnnotationParameterCustom;
import com.github.fashionbrot.validated.validator.support.ParameterType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.*;

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


       /* if (annotationTypeEnum != null) {
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
        }*/
    }





    @Override
    public List<MarsViolation> entityFieldsAnnotationValid(Validated validated, String valueTypeName, Class<?> clazz, Object[] params, int index) {
        long start=System.currentTimeMillis();

        List<MarsViolation> violationSet = null;

        if (!IgnoreClassUtil.checkIgnorePackage(valueTypeName)) {

            // 判断是否 有继承类
            List<MarsViolation> superMarsViolationList= checkClassSuper(validated,clazz, params, index);
            if (StringUtil.isNotEmpty(superMarsViolationList)){
                if (violationSet==null){
                    violationSet = new ArrayList<>();
                }
                violationSet.addAll(superMarsViolationList);
                return violationSet;
            }

            /**
             * 如果填写 validClass
             */
            Class<?>[] validClass =validated!=null ? validated.validClass():null;
            if (!isValidClass(validClass,clazz)){
                return null;
            }


            Field[] fields = clazz.getDeclaredFields();
            if (fields==null || fields.length<=0){
                return null;
            }
            System.out.println("----------------2000:"+(System.currentTimeMillis()-start));

            for (Field field : fields) {

                Annotation[] annotations = field.getDeclaredAnnotations();
                if (annotations != null && annotations.length > 0) {
                    field.setAccessible(true);

                    for (Annotation annotation : annotations) {

                        Object value = MethodUtil.getFieldValue(field,params[index]);

                        if (annotation instanceof Default){

                            validatedDefault(validated,params,index,null,field,value,annotation);

                        }else{

                            System.out.println("----------------2:"+(System.currentTimeMillis()-start));
                            Class valueType = field.getType();
                            String fieldName = field.getName();
                            List<MarsViolation> marsViolationList = validated(validated,params,index,valueType,fieldName,annotation);

                            if (StringUtil.isNotEmpty(marsViolationList)){
                                if (violationSet==null){
                                    violationSet = new ArrayList<>();
                                }
                                violationSet.addAll(marsViolationList);
                            }
                        }
                    }
                }
            }
        }
        return violationSet;
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
    private List<MarsViolation> checkClassSuper(Validated validated,Class clazz,Object[] params, int index){
        //获取 superclass 是否是 appClassloader 加载的
        Class superclass = clazz.getSuperclass();
        if (superclass != null) {
            //如果不是定义的类型，则把 class 当做bean 进行校验 field
            boolean exist = ClassTypeEnum.checkClass(superclass.getName());
            if (!exist) {
                return entityFieldsAnnotationValid(validated,superclass.getName(), superclass, params, index);
            }
        }
        return null;
    }



    @Override
    public void parameterAnnotationValid(Method method, Object[] params) {

        Parameter[] parameters = method.getParameters();
        Validated validated = method.getDeclaredAnnotation(Validated.class);

        if (parameters != null && parameters.length > 0) {

            List<MarsViolation> violationSet=new ArrayList<>();
            for (int j = 0; j < parameters.length; j++) {

                Parameter parameter = parameters[j];
                Class<?> classType = parameter.getType();
                String parameterTypeName = classType.getTypeName();

                Annotation[] annotations = parameter.getDeclaredAnnotations();
                if (annotations!=null && annotations.length >0){
                    //直接验证参数，不验证参数属性
                    for (Annotation annotation : annotations) {
                        if (annotation instanceof Default){
                            validatedDefault(validated,params,j,parameter.getType(),null,null,annotation);
                        }else{
                            String paramName = parameter.getName();

                            List<MarsViolation> marsViolationList= validated(validated,params,j,parameter.getType(), paramName, annotation);
                            setMarsViolation(violationSet,marsViolationList);
                        }
                    }
                }else{
                    //验证参数属性
                    List<MarsViolation> marsViolationList = entityFieldsAnnotationValid(validated,parameterTypeName, classType, params, j);
                    setMarsViolation(violationSet,marsViolationList);
                }
            }

            if (!CollectionUtils.isEmpty(violationSet)){
                ExceptionUtil.throwException(violationSet);
            }
        }
    }

    private void setMarsViolation(List<MarsViolation> all, List<MarsViolation> marsViolationList) {
        if (StringUtil.isNotEmpty(marsViolationList)) {
            all.addAll(marsViolationList);
        }
    }

    private void validatedDefault(Validated validated,Object[] params,int index,Class valueType,Field field,Object value, Annotation annotation) {

        Class<?>[] vGroupClass = validated!=null ? validated.groups():null;

        Map<String, Object> annotationAttributes = AnnotationUtils.getAnnotationAttributes(annotation);
        if (checkGroup(vGroupClass, annotationAttributes)){
            return;
        }
        List<ConstraintValidator> constraintList = ConstraintHelper.getConstraint(annotation.annotationType());
        if (!CollectionUtils.isEmpty(constraintList)){
            ConstraintValidator constraint = constraintList.get(0);
            if (field!=null){
                Class type =field.getType();
                Object object = constraint.defaultValue(annotation,value,type);
                if (object!=null){
                    Object reValue =  StringUtil.formatObject(object,type);
                    if (reValue!=null){
                        try {
                            field.set(params[index],reValue);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }else{
                Object object = constraint.defaultValue(annotation,params[index],valueType);
                if (object!=null){
                    params[index] = StringUtil.formatObject(object,valueType);
                }
            }
        }
    }


    private List<MarsViolation> validated(Validated validated,Object[] params,int index ,Class<?> valueType,String paramName, Annotation annotation) {
        boolean failFast = validated.failFast();
        Class<?>[] vGroupClass = validated!=null ? validated.groups():null;
        Map<String, Object> annotationAttributes = AnnotationUtils.getAnnotationAttributes(annotation);
        if (checkGroup(vGroupClass, annotationAttributes)){
            return null;
        }
        List<MarsViolation> violationSet = null;


        List<ConstraintValidator> constraintValidatorList = ConstraintHelper.getConstraint(annotation.annotationType());
        if (StringUtil.isNotEmpty(constraintValidatorList)){

            violationSet = validatedConstrain(constraintValidatorList,annotationAttributes,failFast,annotation,params,index,paramName,valueType);

        }else{
            Class<? extends Annotation> annotationType = annotation.annotationType();
            if (annotationType.isAnnotationPresent(Constraint.class)){
                Constraint constraint = annotationType.getDeclaredAnnotation(Constraint.class);
                if (constraint!=null){
                    Class<? extends ConstraintValidator<? extends Annotation, ?>>[] classes = constraint.validatedBy();
                    if (classes == null || classes.length <= 0) {
                        return null;
                    }
                    ConstraintHelper.putConstraintValidator(annotation.annotationType(),classes);

                    constraintValidatorList = ConstraintHelper.getConstraint(annotation.annotationType());
                    if (!CollectionUtils.isEmpty(constraintValidatorList)){

                        violationSet = validatedConstrain(constraintValidatorList,annotationAttributes,failFast,annotation,params,index,paramName,valueType);

                    }
                }
            }
        }
        return violationSet;
    }



    private List<MarsViolation> validatedConstrain(List<ConstraintValidator> constraintValidatorList,
                                         Map<String, Object> annotationAttributes,
                                         boolean failFast,
                                         Annotation annotation,
                                         Object[] params,
                                         int index,
                                         String paramName,
                                         Class valueType){
        List<MarsViolation> violationSet = null;
        if (!CollectionUtils.isEmpty(constraintValidatorList)){
            Object value = params[index];
            for(int i=0;i<constraintValidatorList.size();i++){
                ConstraintValidator constraintValidator = constraintValidatorList.get(i);
                boolean isValid = constraintValidator.isValid(annotation,value,valueType);
                if (!isValid){
                    String msg = (String) annotationAttributes.get(ValidMethod.MSG.getMethod());
                    violationSet = setMarsViolations(value, paramName, annotation, violationSet, constraintValidatorList, msg);
                    if (failFast){
                        ExceptionUtil.throwException(violationSet);
                    }
                }
            }
        }
        return violationSet;
    }

    private List<MarsViolation> setMarsViolations(Object value, String paramName, Annotation annotation, List<MarsViolation> violationSet, List<ConstraintValidator> constraintValidatorList, String msg) {
        if (violationSet == null) {
            violationSet = new ArrayList<>(constraintValidatorList.size());
        }
        violationSet.add(MarsViolation.builder()
                .annotationName(annotation.annotationType().getName())
                .fieldName(paramName)
                .msg(ValidatorUtil.filterMsg(msg))
                .value(value)
                .build());
        return violationSet;
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
                                        //ExceptionUtil.throwException(isValid,value);
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
     * @param attributes
     * @return
     */
    private boolean checkGroup(Class<?>[] vGroupClass, Map<String, Object> attributes) {
        //groups==null ,则不验证groups
        if (vGroupClass==null || vGroupClass.length<=0) {
            return false;
        }
        //检测groups 是否匹配
        if(attributes.containsKey(ValidMethod.GROUPS.getMethod())){
            Class[] groups = (Class[]) attributes.get(ValidMethod.GROUPS.getMethod());
            if (!checkGroup(vGroupClass,groups)){
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
                                    //ExceptionUtil.throwException(msgValue, fieldName);
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
