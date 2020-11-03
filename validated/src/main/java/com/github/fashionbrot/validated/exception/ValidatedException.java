package com.github.fashionbrot.validated.exception;

import com.github.fashionbrot.validated.constraint.MarsViolation;
import com.github.fashionbrot.validated.util.StringUtil;
import lombok.Data;
import java.util.List;

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
}
