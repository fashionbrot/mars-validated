package com.github.fashionbrot.validated.internal;

import com.github.fashionbrot.validated.constraint.ConstraintValidator;
import com.github.fashionbrot.validated.util.StringUtil;

import java.util.regex.Pattern;

public class PatternConstraint implements ConstraintValidator<com.github.fashionbrot.validated.annotation.Pattern,Object> {


    @Override
    public boolean isValid(com.github.fashionbrot.validated.annotation.Pattern pattern, Object objectValue,Class<?> valueType) {

        if (objectValue == null) {
            return false;
        } else {
            String regexp = pattern.regexp();
            String str = StringUtil.formatString(objectValue);
            if (StringUtil.isNotEmpty(regexp)) {
                Pattern patternV = Pattern.compile(pattern.regexp());
                return patternV.matcher(str).matches();
            }
        }
        return true;
    }

}
