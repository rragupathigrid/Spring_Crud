package org.example.departmentcrud.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.example.departmentcrud.dto.DepartmentRequest;
import org.example.departmentcrud.dto.DepartmentResponse;
import org.example.departmentcrud.service.DepartmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<List<DepartmentResponse>> getAllDepartments() {

        List<DepartmentResponse> departments = service.getAllDepartments();

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