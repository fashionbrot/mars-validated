package com.github.fashionbrot.validated.constraint;


import com.github.fashionbrot.validated.annotation.*;
import com.github.fashionbrot.validated.internal.*;
import com.github.fashionbrot.validated.util.BeanUtil;
import com.github.fashionbrot.validated.util.MethodUtil;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;

import java.lang.annotation.Annotation;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ConstraintHelper {


    private static Map<Class<? extends Annotation>, List<ConstraintValidator>> builtinConstraint = new ConcurrentHashMap<>();


    static {
        Map<Class<? extends Annotation>, List<ConstraintValidator>> builtinConstraints = new HashMap<>();
        putTemp(builtinConstraints, NotNull.class, NotNullConstraint.class);
        putTemp(builtinConstraints, Default.class, DefaultConstraint.class);
        putTemp(builtinConstraints, AssertFalse.class, AssertFalseConstraint.class);
        putTemp(builtinConstraints, AssertTrue.class, AssertTrueConstraint.class);
        putTemp(builtinConstraints, BankCard.class, BankCardConstraint.class);
        putTemp(builtinConstraints, CreditCard.class, CreditCardConstraint.class);
        putTemp(builtinConstraints, Digits.class, DigitsConstraint.class);
        putTemp(builtinConstraints, Email.class, EmailConstraint.class);
        putTemp(builtinConstraints, IdCard.class, IdCardConstraint.class);
        putTemp(builtinConstraints, Length.class, LengthConstraint.class);
        putTemp(builtinConstraints, NotBlank.class, NotBlankConstraint.class);
        putTemp(builtinConstraints, NotEmpty.class, NotEmptyConstraint.class);
        putTemp(builtinConstraints, NotEqualLength.class, NotEqualsLengthConstraint.class);
        putTemp(builtinConstraints, Pattern.class, PatternConstraint.class);
        putTemp(builtinConstraints, Phone.class, PhoneConstraint.class);
        putTemp(builtinConstraints, Size.class, SizeConstraint.class);
        builtinConstraint.putAll(builtinConstraints);
    }


    private static <A extends Annotation> void putTemp(
        Map<Class<? extends Annotation>, List<ConstraintValidator>> builtinConstraints,
        Class<A> constraintType,
        Class<? extends ConstraintValidator>... constraintValidators) {
        if (constraintValidators!=null && constraintValidators.length > 0) {
            List<ConstraintValidator> list = new ArrayList<>(constraintValidators.length);
            for (int i = 0; i < constraintValidators.length; i++) {
                list.add(MethodUtil.newInstance(constraintValidators[i]));
            }
            builtinConstraints.put(constraintType, list);
        }
    }

    public static <A extends Annotation> void putConstraintValidator(
        Class<A> constraintType,
        Class<? extends ConstraintValidator>[] constraintValidators) {
        if (constraintValidators != null && constraintValidators.length > 0) {
            List<ConstraintValidator> list = new ArrayList<>(constraintValidators.length);
            for (int i = 0; i < constraintValidators.length; i++) {
                list.add(MethodUtil.newInstance(constraintValidators[i]));
            }
            builtinConstraint.putIfAbsent(constraintType, list);
        }
    }

    public static <A extends Annotation> void putConstraintValidator(BeanFactory beanFactory, Class<A> constraintType, Class<? extends ConstraintValidator>[] constraintValidators) {
        if (constraintValidators != null && constraintValidators.length > 0) {
            List<ConstraintValidator> list = new ArrayList<>(constraintValidators.length);
            for (int i = 0; i < constraintValidators.length; i++) {
                Class<? extends ConstraintValidator> constraintValidator = constraintValidators[i];
                //issue#5 注入spring容器
                BeanUtil.registerInfrastructureBeanIfAbsent((BeanDefinitionRegistry) beanFactory,constraintValidator.getName(),constraintValidator);
                ConstraintValidator bean = beanFactory.getBean(constraintValidator);
                if (bean!=null) {
                    list.add(bean);
                }
            }
            builtinConstraint.putIfAbsent(constraintType, list);
        }
    }


    public static <A extends Annotation> List<ConstraintValidator> getConstraint(Class<A> constraintType) {
            return builtinConstraint.get(constraintType);
    }

    public static boolean containsKey(Class constraintType) {
        return builtinConstraint.containsKey(constraintType);
    }


}
