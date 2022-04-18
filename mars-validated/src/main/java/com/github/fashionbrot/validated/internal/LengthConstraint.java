package com.github.fashionbrot.validated.internal;


import com.github.fashionbrot.validated.annotation.Length;
import com.github.fashionbrot.validated.constraint.ConstraintValidator;


public class LengthConstraint implements ConstraintValidator<Length, Object> {

	@Override
	public boolean isValid(Length length, Object value, Class<?> valueType) {

		int valueLength = 0;
		int min = length.min();
		int max = length.max();
		if (value != null) {
			valueLength = value.toString().length();
		}
		if (value == null || min > valueLength || valueLength > max) {
			return false;
		}
		return true;
	}

}
