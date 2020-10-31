package com.github.fashionbrot.validated.validator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ValidatedMethod {

    private List<ParameterAnnotation> parameterAnnotations;

}
