package com.github.fashionbrot.validated.validator;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class ParameterAnnotation {
    private int index;

    private String paramName;

    private List<AnnotationModel> annotationModelList;
}
