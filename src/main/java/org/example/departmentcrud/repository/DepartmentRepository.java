package org.example.departmentcrud.repository;

import org.example.departmentcrud.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository
        extends JpaRepository<Department, Long> {
}