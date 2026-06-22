package org.example.departmentcrud.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import org.example.departmentcrud.dto.DepartmentRequest;
import org.example.departmentcrud.dto.DepartmentResponse;
import org.example.departmentcrud.dto.PageResponse;
import org.example.departmentcrud.service.DepartmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

    private final DepartmentService service;

    public DepartmentController(DepartmentService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<DepartmentResponse> createDepartment(
            @Valid @RequestBody DepartmentRequest request) {

        DepartmentResponse response = service.createDepartment(request);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepartmentResponse> getDepartmentById(
            @PathVariable
            @Positive(message = "Department id must be positive") Long id) {

        DepartmentResponse response = service.getDepartmentById(id);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<PageResponse<DepartmentResponse>> getAllDepartments(
            @RequestParam(required = false) String departmentName,
            @RequestParam(required = false) String location,

            @RequestParam(defaultValue = "0")
            @Min(value = 0, message = "Page number must be 0 or greater") int page,

            @RequestParam(defaultValue = "10")
            @Min(value = 1, message = "Page size must be at least 1")
            @Max(value = 100, message = "Page size must not be greater than 100") int size,

            @RequestParam(defaultValue = "id") String sortBy,

            @RequestParam(defaultValue = "ASC") String sortDirection
    ) {

        PageResponse<DepartmentResponse> departments = service.getAllDepartments(
                departmentName,
                location,
                page,
                size,
                sortBy,
                sortDirection
        );

        return ResponseEntity.ok(departments);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DepartmentResponse> updateDepartment(
            @PathVariable
            @Positive(message = "Department id must be positive") Long id,
            @Valid @RequestBody DepartmentRequest request) {

        DepartmentResponse response = service.updateDepartment(id, request);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepartment(
            @PathVariable
            @Positive(message = "Department id must be positive") Long id) {

        service.deleteDepartment(id);

        return ResponseEntity.noContent().build();
    }
}