package org.example.departmentcrud.service;

import org.example.departmentcrud.dto.DepartmentRequest;
import org.example.departmentcrud.dto.DepartmentResponse;
import org.example.departmentcrud.entity.Department;
import org.example.departmentcrud.repository.DepartmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;


    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public DepartmentResponse createDepartment(DepartmentRequest departmentRequest){

        Department department = new Department();

        department.setDepartmentName(departmentRequest.getDepartmentName());
        department.setLocation(departmentRequest.getLocation());

        Department savedDepartment = departmentRepository.save(department);

        return new DepartmentResponse(savedDepartment.getId(), savedDepartment.getDepartmentName(), savedDepartment.getLocation());
    }

    public List<DepartmentResponse> getAllDepartments() {

        return departmentRepository.findAll().stream()
                .map(department -> new DepartmentResponse(department.getId(), department.getDepartmentName(), department.getLocation()))
                .toList();
    }

    public DepartmentResponse getDepartmentById(Long id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found with id: " + id));

        return new DepartmentResponse(department.getId(), department.getDepartmentName(), department.getLocation());
    }

    public DepartmentResponse updateDepartment(Long id , DepartmentRequest departmentRequest){

        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found with id: " + id));

        department.setDepartmentName(departmentRequest.getDepartmentName());
        department.setLocation(departmentRequest.getLocation());

        Department updatedDepartment = departmentRepository.save(department);

        return new DepartmentResponse(updatedDepartment.getId(), updatedDepartment.getDepartmentName(), updatedDepartment.getLocation());
    }

    public void deleteDepartment(Long id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found with id: " + id));

        departmentRepository.delete(department);
    }

}
