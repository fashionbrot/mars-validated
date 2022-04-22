package com.github.fashionbrot.validated.internal;


import com.github.fashionbrot.validated.annotation.NotBlank;
import com.github.fashionbrot.validated.constraint.ConstraintValidator;
import com.github.fashionbrot.validated.util.StringUtil;


public class NotBlankConstraint implements ConstraintValidator<NotBlank, Object> {

	@Override
	public boolean isValid(NotBlank length, Object value, Class<?> valueType) {

		if (value == null || StringUtil.isBlank(value.toString())) {
			return false;
		}
		return true;
	}

}
