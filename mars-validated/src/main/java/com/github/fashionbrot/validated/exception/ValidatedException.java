package com.github.fashionbrot.validated.exception;

import com.github.fashionbrot.validated.constraint.MarsViolation;
import com.github.fashionbrot.validated.util.StringUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper=false)
@Data
public class ValidatedException extends RuntimeException  {

    private String fieldName;

    private String msg;

    private String annotationName;

    private Object value;

    private List<MarsViolation> violations;

    public ValidatedException(List<MarsViolation> violations) {
        super();
        if (StringUtil.isNotEmpty(violations) && violations.size()==1){
            MarsViolation marsViolation=  violations.get(0);
            this.fieldName = marsViolation.getFieldName();
            this.msg = marsViolation.getMsg();
            this.annotationName = marsViolation.getAnnotationName();
            this.value = marsViolation.getValue();
        }
        this.violations = violations;
    }

    public ValidatedException(String fieldName,String msg,String annotationName,Object value){
        super();
        this.fieldName = fieldName;
        this.msg = msg;
        this.annotationName = annotationName;
        this.value = value;
    }

    public static void throwMsg(String fieldName,String msg){
        throw new ValidatedException(fieldName,msg,null,null);
    }

    public static void throwMsg(String fieldName,String msg,String annotationName){
        throw new ValidatedException(fieldName,msg,annotationName,null);
    }

    public static void throwMsg(String fieldName,String msg,String annotationName,Object value){
        throw new ValidatedException(fieldName,msg,annotationName,value);
    }
}
