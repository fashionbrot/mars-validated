package com.github.fashionbrot.validated.validator;

import com.github.fashionbrot.validated.constraint.ConstraintValidator;
import lombok.Builder;
import lombok.Data;

import java.lang.annotation.Annotation;

@Data
@Builder
public class AnnotationModel {

    private Annotation annotation;

    private ConstraintValidator constraintValidator;

    private String msg;
}
