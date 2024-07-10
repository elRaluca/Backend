package com.example.backend.service;
import com.example.backend.entity.Supplier;
import com.example.backend.repository.SupplierRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierService {

    @Autowired
    private SupplierRepo supplierRepo;

    public List<Supplier> getAllSuppliers() {
        return supplierRepo.findAll();
    }


    public Supplier saveSupplier(Supplier supplier) {
        return supplierRepo.save(supplier);
    }


    public boolean deleteSupplier(Integer id) {
        if (supplierRepo.existsById(id)) {
            supplierRepo.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
