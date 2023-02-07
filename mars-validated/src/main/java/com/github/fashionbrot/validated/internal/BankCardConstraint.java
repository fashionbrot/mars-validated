package com.github.fashionbrot.validated.internal;

import com.github.fashionbrot.validated.annotation.BankCard;
import com.github.fashionbrot.validated.annotation.NotNull;
import com.github.fashionbrot.validated.constraint.ConstraintValidator;
import com.github.fashionbrot.validated.util.ObjectUtil;
import com.github.fashionbrot.validated.util.PatternSts;
import com.github.fashionbrot.validated.util.ValidatorUtil;

import java.util.regex.Pattern;

public class BankCardConstraint implements ConstraintValidator<BankCard,Object> {


    @Override
    public boolean isValid(BankCard bankCard, Object value,Class<?> valueType) {
        String str = ObjectUtil.formatString(value);
        if (ObjectUtil.isBlank(str)) {
            return false;
        } else {
            String regexp = bankCard.regexp();
            if (PatternSts.BANKCARD_PATTERN.pattern().equals(regexp)) {
                return PatternSts.BANKCARD_PATTERN.matcher(str).matches();
            } else {
                Pattern pattern ;
                if (ObjectUtil.isBlank(regexp)) {
                    pattern =PatternSts.BANKCARD_PATTERN;
                } else {
                    pattern = Pattern.compile(regexp);
                }
                return pattern.matcher(str).matches();
            }
        }
    }

}
