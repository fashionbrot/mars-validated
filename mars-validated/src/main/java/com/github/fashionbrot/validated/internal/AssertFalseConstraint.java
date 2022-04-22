
package com.github.fashionbrot.validated.internal;


import com.github.fashionbrot.validated.annotation.AssertFalse;
import com.github.fashionbrot.validated.constraint.ConstraintValidator;

/**
 * Validates that the value passed is false
 */
public class AssertFalseConstraint implements ConstraintValidator<AssertFalse, Boolean> {

	@Override
	public boolean isValid(AssertFalse annotation, Boolean bool, Class<?> valueType) {
		return bool == null || !bool;
	}
}
