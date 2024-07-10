package com.example.backend.mapper;
import com.example.backend.dto.SupplierDTO;
import com.example.backend.entity.Supplier;

public class SupplierMapper {
    public static SupplierDTO toDTO(Supplier supplier) {
        if (supplier == null) {
            return null;
        }


        return new SupplierDTO(
                supplier.getId(),
                supplier.getName(),
                supplier.getContact(),
                supplier.getPhone(),
                supplier.getEmail(),
                supplier.getAddress(),
                supplier.getCity(),
                supplier.getCountry(),
                supplier.getZipCode()

        );
    }



    public static Supplier toEntity(SupplierDTO supplierDTO) {
        if (supplierDTO == null) {
            return null;
        }


        Supplier supplier = new Supplier(
                supplierDTO.getId(),
                supplierDTO.getName(),
                supplierDTO.getContact(),
                supplierDTO.getPhone(),
                supplierDTO.getEmail(),
                supplierDTO.getAddress(),
                supplierDTO.getCity(),
                supplierDTO.getCountry(),
                supplierDTO.getZipCode()


        );

        return supplier;
    }


}
