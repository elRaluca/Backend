package com.example.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssignDeliveryDTO {
    private Integer orderId;
    private String deliveryAddress;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date deliveryDate;
}
