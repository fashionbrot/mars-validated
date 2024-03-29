package com.github.fashionbrot.validated.internal;

import com.github.fashionbrot.validated.annotation.Phone;
import com.github.fashionbrot.validated.constraint.ConstraintValidator;
import com.github.fashionbrot.validated.util.ObjectUtil;
import com.github.fashionbrot.validated.util.PatternSts;
import java.util.regex.Pattern;

public class PhoneConstraint implements ConstraintValidator<Phone,Object> {


    @Override
    public boolean isValid(Phone phone, Object objectValue, Class<?> valueType) {

        String regexp = phone.regexp();
        String value = ObjectUtil.formatString(objectValue);
        if (ObjectUtil.isEmpty(value)) {
            return false;
        } else {
            if (PatternSts.PHONE_PATTERN.pattern().equals(regexp)) {
                return PatternSts.PHONE_PATTERN.matcher(value).matches();
            } else {
                Pattern pattern ;
                if (ObjectUtil.isEmpty(regexp)) {
                    pattern = PatternSts.PHONE_PATTERN;
                } else {
                    pattern = Pattern.compile(regexp);
                }
                return pattern.matcher(value).matches();
            }
        }
    }

}
