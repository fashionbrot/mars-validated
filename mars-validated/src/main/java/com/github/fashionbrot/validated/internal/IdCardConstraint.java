package com.github.fashionbrot.validated.internal;

import com.github.fashionbrot.validated.annotation.IdCard;
import com.github.fashionbrot.validated.constraint.ConstraintValidator;
import com.github.fashionbrot.validated.util.ObjectUtil;
import com.github.fashionbrot.validated.util.PatternSts;

import java.util.Calendar;
import java.util.regex.Pattern;


public class IdCardConstraint implements ConstraintValidator<IdCard, Object> {

	@Override
	public boolean isValid(IdCard idCard, Object objectValue, Class<?> valueType) {

		String regexp = idCard.regexp();
		String value = ObjectUtil.formatString(objectValue);

		if (ObjectUtil.isEmpty(value)) {
			return false;
		} else {
			if (PatternSts.ID_CARD_PATTERN.pattern().equals(regexp)) {
				boolean isMatcher = PatternSts.ID_CARD_PATTERN.matcher(value).matches();
				if (!isMatcher) {
					return false;
				}
				if (idCard.below18()) {
					return checkIdCard(value);
				}
				return true;
			} else {
				Pattern pattern;
				if (ObjectUtil.isEmpty(regexp)) {
					pattern = PatternSts.ID_CARD_PATTERN;
				} else {
					pattern = Pattern.compile(regexp);
				}

				if (!pattern.matcher(value).matches()) {
					return false;
				}
				if (idCard.below18()) {
					return checkIdCard(value);
				}
				return true;
			}
		}
	}

	public  boolean checkIdCard(String idCard) {
		if (idCard.length() == 18) {
			String str = idCard.substring(6, 10);//截取年
			Calendar cal = Calendar.getInstance();
			int year = cal.get(Calendar.YEAR);
			if ((year - ObjectUtil.formatInteger(str) < 18)) {
				return false;
			}
		}
		return true;
	}


}
