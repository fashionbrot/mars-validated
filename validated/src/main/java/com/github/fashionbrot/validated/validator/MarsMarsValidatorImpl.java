package com.github.fashionbrot.validated.validator;

import com.github.fashionbrot.validated.annotation.Default;
import com.github.fashionbrot.validated.annotation.Validated;
import com.github.fashionbrot.validated.constraint.*;
import com.github.fashionbrot.validated.enums.ClassTypeEnum;
import com.github.fashionbrot.validated.internal.DefaultConstraint;
import com.github.fashionbrot.validated.util.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.CollectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.*;

@Slf4j
public class MarsMarsValidatorImpl implements MarsValidator {

    public static final String BEAN_NAME="marsValidator";
    private static final String METHOD_NAME_MODIFY = "modify";
    private static final String METHOD_VALID_OBJECT = "validObject";


    @Override
    public List<MarsViolation> entityFieldsAnnotationValid(Validated validated, String valueTypeName, Class<?> clazz, Object[] params, int index) {

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

            //如果填写 validClass
            Class<?>[] validClass =validated!=null ? validated.validClass():null;
            if (!isValidClass(validClass,clazz)){
                return null;
            }


            Field[] fields = clazz.getDeclaredFields();
            if (fields==null || fields.length<=0){
                return null;
            }

            for (Field field : fields) {

                Annotation[] annotations = field.getDeclaredAnnotations();
                if (annotations != null && annotations.length > 0) {
                    field.setAccessible(true);

                    for (Annotation annotation : annotations) {

                        Class<?> valueType = field.getType();
                        String fieldName = field.getName();
                        List<MarsViolation> marsViolationList = validated(validated,params,index,valueType,fieldName,annotation,field);

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
     * return  List<MarsViolation>
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
    public void returnValueAnnotationValid(Validated validated,Object object) {
        //验证当前参数是类，还是普通类型
        ClassTypeEnum classTypeEnum = ClassTypeEnum.getValue (object.getClass().getName());
        List<MarsViolation> violationSet=new ArrayList<>();
        if (classTypeEnum==null){
            //验证参数属性
            List<MarsViolation> marsViolationList = entityFieldsAnnotationValid(validated,object.getClass().getTypeName(), object.getClass(),new Object[]{object},0);
            setMarsViolation(violationSet,marsViolationList);
        }
        if (!CollectionUtils.isEmpty(violationSet)){
            ExceptionUtil.throwException(violationSet);
        }
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
                    //直接验证参数
                    for (Annotation annotation : annotations) {

                        String paramName = parameter.getName();
                        List<MarsViolation> marsViolationList= validated(validated,params,j,parameter.getType(), paramName, annotation,null);
                        setMarsViolation(violationSet,marsViolationList);

                        //验证当前参数是类，还是普通类型
                        ClassTypeEnum classTypeEnum = ClassTypeEnum.getValue (parameterTypeName);
                        if (classTypeEnum==null){
                            //验证参数属性
                            marsViolationList = entityFieldsAnnotationValid(validated,parameterTypeName, classType, params, j);
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



    private List<MarsViolation> validated(Validated validated,Object[] params,int index ,Class<?> valueType,String paramName, Annotation annotation,Field field) {
        boolean failFast = validated.failFast();
        Class<?>[] vGroupClass = validated!=null ? validated.groups():null;
        Map<String, Object> annotationAttributes = AnnotationUtils.getAnnotationAttributes(annotation);
        if (checkGroup(vGroupClass, annotationAttributes)){
            return null;
        }
        List<MarsViolation> violationSet = null;


        List<ConstraintValidator> constraintValidatorList = ConstraintHelper.getConstraint(annotation.annotationType());
        if (StringUtil.isNotEmpty(constraintValidatorList)){

            violationSet = validatedConstrain(constraintValidatorList,annotationAttributes,failFast,annotation,params,index,paramName,valueType,field);

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

                        violationSet = validatedConstrain(constraintValidatorList,annotationAttributes,failFast,annotation,params,index,paramName,valueType,field);

                    }
                }
            }
        }
        return violationSet;
    }



    private  List<MarsViolation> validatedConstrain(List<ConstraintValidator> constraintValidatorList,
                                                                 Map<String, Object> annotationAttributes,
                                                                 boolean failFast,
                                                                 Annotation annotation,
                                                                 Object[] params,
                                                                 int index,
                                                                 String paramName,
                                                                 Class valueType,
                                                                 Field field){
        List<MarsViolation> violationSet = null;
        if (StringUtil.isNotEmpty(constraintValidatorList)){
            Object value = params[index];
            if (field!=null){
                value = MethodUtil.getFieldValue(field,params[index]);
            }
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

                Class validConstraintClass= constraintValidator.getClass();
                if (MethodUtil.checkDeclaredMethod(validConstraintClass,METHOD_NAME_MODIFY)){
                    Object reValue = constraintValidator.modify(annotation,value,valueType);
                    if (reValue!=null){
                        if (field!=null){
                            Object param = params[index];
                            Object formatValue =  StringUtil.formatObject(reValue,field.getType());
                            if (formatValue!=null && field.getType() == formatValue.getClass()){
                                try {
                                    field.set(param,formatValue);
                                } catch (IllegalAccessException e) {
                                    e.printStackTrace();
                                }
                            }else{
                                log.error("mars valid error setField fieldName:{} annotation:{} oldType:{} newType:{} ",field.getName(),annotation.annotationType().getName(),field.getType(),reValue.getClass());
                            }
                        }else{
                            if (reValue!=null){
                                params[index] = StringUtil.formatObject(reValue,valueType);
                            }
                        }
                    }
                }
                if (MethodUtil.checkDeclaredMethod(validConstraintClass,METHOD_VALID_OBJECT)){
                    String msg = constraintValidator.validObject(annotation,value,valueType);
                    if (StringUtil.isNotEmpty(msg)) {
                        violationSet = setMarsViolations(value, paramName, annotation, violationSet, constraintValidatorList, msg);
                        if (failFast) {
                            ExceptionUtil.throwException(violationSet);
                        }
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
            if (checkGroup(vGroupClass, groups)) {
                return false;
            }
            return true;
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


}
