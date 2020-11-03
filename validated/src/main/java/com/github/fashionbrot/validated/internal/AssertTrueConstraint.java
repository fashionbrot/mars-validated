
package com.github.fashionbrot.validated.internal;


import com.github.fashionbrot.validated.annotation.AssertTrue;
import com.github.fashionbrot.validated.constraint.ConstraintValidator;

/**
 * Validates that the value passed is true
 *
 * @author Alaa Nassef
 */
public class AssertTrueConstraint implements ConstraintValidator<AssertTrue, Boolean> {

	@Override
	public boolean isValid(AssertTrue annotation, Boolean bool, Class<?> valueType) {
		return bool == null || bool;
	}
}
