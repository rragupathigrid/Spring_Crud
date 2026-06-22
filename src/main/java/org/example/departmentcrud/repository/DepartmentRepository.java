package org.example.departmentcrud.repository;

import org.example.departmentcrud.entity.Department;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

    Page<Department> findByDepartmentName(
            String departmentName,
            Pageable pageable
    );

    Page<Department> findByLocation(
            String location,
            Pageable pageable
    );

    Page<Department> findByDepartmentNameAndLocation(
            String departmentName,
            String location,
            Pageable pageable
    );
}