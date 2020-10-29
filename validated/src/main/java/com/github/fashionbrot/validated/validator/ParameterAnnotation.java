package com.github.fashionbrot.validated.validator;

import com.github.fashionbrot.validated.constraint.ConstraintValidator;
import com.github.fashionbrot.validated.constraint.ConstraintValidatorBean;
import lombok.Builder;
import lombok.Data;

import java.lang.annotation.Annotation;
import java.util.List;

@Data
@Builder
public class ParameterAnnotation {
    private int index;

    private Annotation annotation;

    private List<ConstraintValidator> constraintValidator;

    private List<ConstraintValidatorBean> constraintValidatorBean;
}
