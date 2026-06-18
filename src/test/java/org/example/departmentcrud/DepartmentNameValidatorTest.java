package org.example.departmentcrud;

import org.example.departmentcrud.validation.DepartmentNameValidator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DepartmentNameValidatorTest {

    private final DepartmentNameValidator validator = new DepartmentNameValidator();

    @Test
    void shouldReturnTrueForValidDepartmentName() {

        assertTrue(validator.isValid("IT", null));
        assertTrue(validator.isValid("Human Resources", null));
        assertTrue(validator.isValid("Research & Development", null));
        assertTrue(validator.isValid("Sales-Marketing", null));
    }

    @Test
    void shouldReturnFalseForInvalidDepartmentName() {

        assertFalse(validator.isValid("IT@@@", null));
        assertFalse(validator.isValid("Finance123", null));
        assertFalse(validator.isValid("Admin!", null));
    }

    @Test
    void shouldReturnTrueForNullOrBlankBecauseOtherAnnotationsHandleIt() {

        assertTrue(validator.isValid(null, null));
        assertTrue(validator.isValid("", null));
        assertTrue(validator.isValid("   ", null));
    }
}