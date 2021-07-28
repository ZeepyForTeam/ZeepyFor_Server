package com.zeepy.server.common.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

/**
 * Created by Minky on 2021-07-16
 */
public class EnumListValidator implements ConstraintValidator<EnumList, List<String>> {
    private EnumList annotation;

    @Override
    public void initialize(EnumList constraintAnnotation) {
        this.annotation = constraintAnnotation;
    }

    @Override
    public boolean isValid(List<String> value, ConstraintValidatorContext context) {
        boolean result = true;

        Object[] enumValues = this.annotation.enumClass().getEnumConstants();

        if (enumValues == null) {
            return result;
        }

        for (String valueOne : value) {
            if (isEnumValid(valueOne, enumValues) == false) {
                result = false;
                break;
            }
        }

        return result;
    }

    public boolean isEnumValid(String valueOne, Object[] enumValues) {
        boolean result = false;

        for (Object enumValue : enumValues) {
            if (valueOne.equals(enumValue.toString()) || (this.annotation.ignoreCase() && valueOne.equalsIgnoreCase(
                    enumValue.toString()))) {
                result = true;
                break;
            }
        }

        return result;
    }
}
