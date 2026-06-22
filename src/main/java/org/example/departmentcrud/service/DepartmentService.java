package org.example.departmentcrud.service;

import org.example.departmentcrud.dto.DepartmentRequest;
import org.example.departmentcrud.dto.DepartmentResponse;
import org.example.departmentcrud.dto.PageResponse;
import org.example.departmentcrud.entity.Department;
import org.example.departmentcrud.exception.DepartmentNotFoundException;
import org.example.departmentcrud.repository.DepartmentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class DepartmentService {

    private static final Set<String> sortData = Set.of(
            "id",
            "departmentName",
            "location"
    );

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

    public PageResponse<DepartmentResponse> getAllDepartments(
            String departmentName,
            String location,
            int page,
            int size,
            String sortBy,
            String sortDirection
    ) {

        if (!sortData.contains(sortBy)) {
            throw new IllegalArgumentException("Invalid sort field: " + sortBy);
        }

        Sort.Direction direction = Sort.Direction.fromString(sortDirection);

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(direction, sortBy)
        );

        Page<Department> departmentPage;

        boolean hasDepartmentName = departmentName != null && !departmentName.isBlank();
        boolean hasLocation = location != null && !location.isBlank();

        if (hasDepartmentName && hasLocation) {
            departmentPage = repository.findByDepartmentNameAndLocation(
                            departmentName,
                            location,
                            pageable
                    );
        }
        else if (hasDepartmentName) {
            departmentPage = repository.findByDepartmentName(
                            departmentName,
                            pageable
                    );
        }
        else if (hasLocation) {
            departmentPage = repository.findByLocation(
                            location,
                            pageable
                    );
        }
        else {
            departmentPage = repository.findAll(pageable);
        }

        Page<DepartmentResponse> responsePage = departmentPage.map(this::mapToResponse);

        return PageResponse.from(responsePage, sortBy, direction.name());
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