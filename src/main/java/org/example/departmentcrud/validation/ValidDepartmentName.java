package org.example.departmentcrud.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = DepartmentNameValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidDepartmentName {

    String message() default "Department name can contain only letters, spaces, hyphen and ampersand";

    Class<?>[] groups() default {};

}