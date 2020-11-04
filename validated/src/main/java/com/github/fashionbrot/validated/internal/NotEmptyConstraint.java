package com.github.fashionbrot.validated.internal;


import com.github.fashionbrot.validated.annotation.NotEmpty;
import com.github.fashionbrot.validated.constraint.ConstraintValidator;
import com.github.fashionbrot.validated.util.StringUtil;


public class NotEmptyConstraint implements ConstraintValidator<NotEmpty, Object> {

	@Override
	public boolean isValid(NotEmpty notEmpty, Object value, Class<?> valueType) {

		if (value == null || StringUtil.isEmpty(value.toString())) {
			return false;
		}
		return true;
	}

}
