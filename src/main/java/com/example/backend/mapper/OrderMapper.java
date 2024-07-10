package com.example.backend.mapper;
import com.example.backend.dto.DeliveryDTO;
import com.example.backend.dto.OrderDTO;
import com.example.backend.dto.OrderItemDTO;
import com.example.backend.entity.Delivery;
import com.example.backend.entity.Order;
import com.example.backend.entity.OrderItem;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;


@Component
public class OrderMapper {

    public OrderDTO toOrderDTO(Order order) {
        if (order == null) {
            return null;
        }

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(Long.valueOf(order.getIdOrder()));
        orderDTO.setUserId(order.getUserId());
        orderDTO.setOrderDate(order.getOrderDate());
        orderDTO.setTotal(order.getTotal());
        orderDTO.setStatus(order.getStatus().name());

        if (order.getItems() != null) {
            orderDTO.setItems(order.getItems().stream()
                    .map(this::toOrderItemDTO)
                    .collect(Collectors.toList()));
        }

        // Map Delivery, verificați dacă există
        if (order.getDelivery() != null) {
            orderDTO.setDelivery(toDeliveryDTO(order.getDelivery()));
        }

        return orderDTO;
    }

    public OrderItemDTO toOrderItemDTO(OrderItem orderItem) {
        if (orderItem == null) {
            return null;
        }

        OrderItemDTO orderItemDTO = new OrderItemDTO();
        orderItemDTO.setId(orderItem.getId());
        orderItemDTO.setProductId(orderItem.getProduct() != null ? orderItem.getProduct().getId().intValue() : null);
        orderItemDTO.setSpecialBouquetId(orderItem.getSpecialBouquet() != null ? orderItem.getSpecialBouquet().getId().intValue() : null);
        orderItemDTO.setSpecialBouquetImage(orderItem.getSpecialBouquet() != null ? orderItem.getSpecialBouquet().getImageBouquet() : null);  // Setează imaginea aici
        orderItemDTO.setProductImage(orderItem.getProduct() != null ? orderItem.getProduct().getImage() : null);
        orderItemDTO.setQuantity(orderItem.getQuantity());
        orderItemDTO.setPrice(orderItem.getPrice());

        return orderItemDTO;
    }


    public DeliveryDTO toDeliveryDTO(Delivery delivery) {
        if (delivery == null) {
            return null;
        }

        DeliveryDTO deliveryDTO = new DeliveryDTO();
        deliveryDTO.setEmail(delivery.getEmail());
        deliveryDTO.setName(delivery.getName());
        deliveryDTO.setPhone(delivery.getPhone());
        deliveryDTO.setCity(delivery.getCity());
        deliveryDTO.setCountry(delivery.getCountry());
        deliveryDTO.setStreet(delivery.getStreet());
        deliveryDTO.setDeliveryMethod(delivery.getDeliveryMethod().name());

        return deliveryDTO;
    }


}



