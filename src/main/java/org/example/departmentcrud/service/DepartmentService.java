package org.example.departmentcrud.service;

import org.example.departmentcrud.dto.DepartmentRequest;
import org.example.departmentcrud.dto.DepartmentResponse;
import org.example.departmentcrud.entity.Department;
import org.example.departmentcrud.exception.DepartmentNotFoundException;
import org.example.departmentcrud.repository.DepartmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {

    private final DepartmentRepository repository;

    public DepartmentService(DepartmentRepository repository) {
        this.repository = repository;
    }

    public DepartmentResponse createDepartment(DepartmentRequest request) {

        Department department = new Department();

        department.setDepartmentName(request.getDepartmentName());
        department.setLocation(request.getLocation());

        Department savedDepartment = repository.save(department);

        return mapToResponse(savedDepartment);
    }

    public DepartmentResponse getDepartmentById(Long id) {

        Department department = repository.findById(id)
                .orElseThrow(() -> new DepartmentNotFoundException(id));

        return mapToResponse(department);
    }

    public List<DepartmentResponse> getAllDepartments() {

        return repository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public DepartmentResponse updateDepartment(Long id, DepartmentRequest request) {

        Department department = repository.findById(id)
                .orElseThrow(() -> new DepartmentNotFoundException(id));

        department.setDepartmentName(request.getDepartmentName());
        department.setLocation(request.getLocation());

        Department updatedDepartment = repository.save(department);

        return mapToResponse(updatedDepartment);
    }

    public void deleteDepartment(Long id) {

        Department department = repository.findById(id)
                .orElseThrow(() -> new DepartmentNotFoundException(id));

        repository.delete(department);
    }

    private DepartmentResponse mapToResponse(Department department) {

        DepartmentResponse response = new DepartmentResponse();

        response.setId(department.getId());
        response.setDepartmentName(department.getDepartmentName());
        response.setLocation(department.getLocation());

        return response;
    }
}