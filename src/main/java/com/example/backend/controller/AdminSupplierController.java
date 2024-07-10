package com.example.backend.controller;
import com.example.backend.dto.SupplierDTO;
import com.example.backend.entity.Supplier;
import com.example.backend.service.SupplierService;
import com.example.backend.mapper.SupplierMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin")
public class AdminSupplierController {

    @Autowired
    private SupplierService supplierService;

    @GetMapping("/allsupp")
    public List<SupplierDTO> getAllSuppliers() {
        return supplierService.getAllSuppliers()
                .stream()
                .map(SupplierMapper::toDTO)
                .collect(Collectors.toList());
    }

    @PostMapping("/createSupplie")
    public ResponseEntity<SupplierDTO> createSupplier(@RequestBody SupplierDTO supplierDTO) {
        Supplier supplier = SupplierMapper.toEntity(supplierDTO);
        Supplier savedSupplier = supplierService.saveSupplier(supplier);
        return ResponseEntity.ok(SupplierMapper.toDTO(savedSupplier));
    }


    @DeleteMapping("/deleteSupplie/{id}")
    public ResponseEntity<Void> deleteSupplier(@PathVariable Integer id) {
        if (supplierService.deleteSupplier(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
