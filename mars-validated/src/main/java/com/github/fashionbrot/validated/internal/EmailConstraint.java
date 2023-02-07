package com.github.fashionbrot.validated.internal;

import com.github.fashionbrot.validated.annotation.Email;
import com.github.fashionbrot.validated.constraint.ConstraintValidator;
import com.github.fashionbrot.validated.util.ObjectUtil;
import com.github.fashionbrot.validated.util.PatternSts;
import java.util.regex.Pattern;


public class EmailConstraint implements ConstraintValidator<Email, Object> {

	@Override
	public boolean isValid(Email email, Object object, Class<?> valueType) {

		String regexp = email.regexp();

		String value = ObjectUtil.formatString(object);
		if (ObjectUtil.isEmpty(value)) {
			return false;
		}
		if (PatternSts.EMAIL_PATTERN.pattern().equals(regexp)) {
			return PatternSts.EMAIL_PATTERN.matcher(value).matches();
		} else {
			Pattern pattern ;
			if (ObjectUtil.isEmpty(regexp)) {
				pattern = PatternSts.EMAIL_PATTERN;
			} else {
				pattern = Pattern.compile(regexp);
			}
			return pattern.matcher(value).matches();
		}
	}

}
