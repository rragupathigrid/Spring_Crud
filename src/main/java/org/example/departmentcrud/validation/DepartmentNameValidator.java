package org.example.departmentcrud.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DepartmentNameValidator implements ConstraintValidator<ValidDepartmentName, String> {

    private static final String DEPARTMENT_NAME_PATTERN = "^[A-Za-z &-]+$";

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        if (value == null || value.isBlank()) {
            return true;
        }

        return value.matches(DEPARTMENT_NAME_PATTERN);
    }
}