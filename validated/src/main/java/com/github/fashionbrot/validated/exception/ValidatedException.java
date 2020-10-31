package com.github.fashionbrot.validated.exception;

import com.github.fashionbrot.validated.constraint.MarsViolation;
import lombok.Data;
import java.util.List;

@Data
public class ValidatedException extends RuntimeException  {


    private List<MarsViolation> violations;

    public ValidatedException(List<MarsViolation> violations) {
        super();
        this.violations = violations;
    }
}
