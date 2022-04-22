package com.github.fashionbrot.validated.internal;

import com.github.fashionbrot.validated.annotation.Default;
import com.github.fashionbrot.validated.constraint.ConstraintValidator;
import com.github.fashionbrot.validated.util.StringUtil;

import java.math.BigDecimal;

public class DefaultConstraint implements ConstraintValidator<Default,Object> {

    @Override
    public boolean isValid(Default annotation, Object var1,Class<?> valueType) {
        return true;
    }

    @Override
    public Object modify(Default annotation, Object value, Class<?> valueType) {
        if (value==null){
            return annotation.value();
        }
        if (value instanceof CharSequence){
            CharSequence sequence = (CharSequence) value;
            if (StringUtil.isEmpty(sequence)){
                return annotation.value();
            }
        }else if (value instanceof BigDecimal){
            BigDecimal bigDecimal = (BigDecimal) value;
            if (bigDecimal==null){
                return annotation.value();
            }
        }else{
            String str = StringUtil.formatString(value);
            if (StringUtil.isEmpty(str)){
                return annotation.value();
            }
        }
        return value;
    }
}
