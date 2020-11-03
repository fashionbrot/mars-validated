package com.github.fashionbrot.validated.internal;


import com.github.fashionbrot.validated.annotation.NotEqualSize;
import com.github.fashionbrot.validated.constraint.ConstraintValidator;


public class NotEqualsSizeConstraint implements ConstraintValidator<NotEqualSize, Object> {

	@Override
	public boolean isValid(NotEqualSize notEqualSize, Object value, Class<?> valueType) {

		int valueLength = 0;
		int size = notEqualSize.size();
		if (value != null) {
			valueLength = value.toString().length();
		}
		if (value == null || valueLength != size) {
			return false;
		}
		return true;
	}

}
