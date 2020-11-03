package com.github.fashionbrot.validated.internal;


import com.github.fashionbrot.validated.annotation.Digits;
import com.github.fashionbrot.validated.constraint.ConstraintValidator;
import com.github.fashionbrot.validated.util.StringUtil;
import java.math.BigDecimal;


public class DigitsConstraint implements ConstraintValidator<Digits, Object> {

	@Override
	public boolean isValid(Digits annotation, Object value, Class<?> valueType) {

		if (value instanceof Number){
			return true;
		}else if (value instanceof BigDecimal ) {
			return true;
		}else{
			String strValue = StringUtil.formatString(value);
			return StringUtil.isDigits(strValue);
		}
	}

}
