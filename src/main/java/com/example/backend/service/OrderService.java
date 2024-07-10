package com.example.backend.service;

import com.example.backend.dto.*;
import com.example.backend.entity.*;
import com.example.backend.mapper.OrderMapper;
import com.example.backend.mapper.ProductMapper;
import com.example.backend.mapper.SpecialBouquetMapper;
import com.example.backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
public class OrderService {
    @Autowired
    private OrderRepo orderRepo;
    @Autowired
    private OrderItemRepo orderItemRepo;
    @Autowired
    private DeliveryRepo deliveryRepo;
    @Autowired
    private CartRepo cartRepo;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private SendEmailService sendEmailService;

    @Transactional
    public Order createOrderFromCartWithDelivery(Long userId, OrderDTO orderDetails) {
        Cart cart = cartRepo.findByUserId(userId.intValue())
                .orElseThrow(() -> new IllegalArgumentException("Cart not found"));
        Order order = new Order();
        order.setUserId(userId);
        order.setOrderDate(new Date());
        order.setStatus(OrderStatus.PENDING);
        order.setTotal(0.0);
        orderRepo.saveAndFlush(order);
        if (orderDetails.getDelivery() != null) {
            setupDelivery(order, orderDetails.getDelivery());
        }
        List<OrderItem> orderItems = cart.getItems().stream().map(cartItem -> createOrderItem(order, cartItem))
                .collect(Collectors.toList());
        orderItemRepo.saveAll(orderItems);
        double total = orderItems.stream().mapToDouble(OrderItem::getPrice).sum();
        order.setTotal(total);
        orderRepo.save(order);
        if (order.getDelivery() != null) {
            sendEmailService.sendEmail(
                    order.getDelivery().getEmail(),
                    "Order Placed",
                    "Your order #" + order.getIdOrder() + " has been placed successfully."
            );
        }
        clearCart(userId.intValue());
        return order;
    }

    @Transactional
    public void clearCart(Integer userId) {
        Cart cart = cartRepo.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Cart not found"));
        cart.getItems().clear(); // Golirea elementelor din coș
        cartRepo.save(cart); // Salvarea modificărilor
    }

    private void setupDelivery(Order order, DeliveryDTO deliveryDTO) {
        Delivery delivery = new Delivery();
        delivery.setStatus(DeliveryStatus.IN_TRANSIT);
        delivery.setOrder(order);
        delivery.setEmail(deliveryDTO.getEmail());
        delivery.setName(deliveryDTO.getName());
        delivery.setPhone(deliveryDTO.getPhone());
        delivery.setCity(deliveryDTO.getCity());
        delivery.setCountry(deliveryDTO.getCountry());
        delivery.setStreet(deliveryDTO.getStreet());
        delivery.setDeliveryMethod(DeliveryMethod.valueOf(deliveryDTO.getDeliveryMethod()));
        order.setDelivery(delivery);
        deliveryRepo.save(delivery);
    }

    private OrderItem createOrderItem(Order order, CartItem cartItem) {
        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(order);
        orderItem.setQuantity(cartItem.getQuantity());

        if (cartItem.getProduct() != null) {
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setPrice(cartItem.getProduct().getPrice() * cartItem.getQuantity());
        } else if (cartItem.getSpecialBouquet() != null) {
            orderItem.setSpecialBouquet(cartItem.getSpecialBouquet());
            orderItem.setPrice(cartItem.getSpecialBouquet().getPriceBouquet() * cartItem.getQuantity());
        }

        return orderItem;
    }

    public List<Object[]> getTopSellingProducts(int limit) {
        Pageable pageable = PageRequest.of(0, limit);
        return orderItemRepo.findTopSellingProducts(pageable);
    }





    public List<OrderDTO> getAllOrders() {
        List<Order> orders = orderRepo.findAllWithItems();
        return orders.stream()
                .map(orderMapper::toOrderDTO)
                .collect(Collectors.toList());
    }
    @Transactional
    public OrderDTO updateOrderStatus(Integer orderId, OrderStatus newStatus) {
        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new IllegalStateException("Order with id " + orderId + " not found"));

        order.setStatus(newStatus);
        order = orderRepo.save(order);

        if (newStatus == OrderStatus.COMPLETED) {
            sendEmailService.sendEmail(order.getDelivery().getEmail(),
                    "Order Completed",
                    "Your order #" + orderId + " has been completed.");
        } else if (newStatus == OrderStatus.CANCELLED) {
            sendEmailService.sendEmail(order.getDelivery().getEmail(),
                    "Order Cancelled",
                    "Your order #" + orderId + " has been cancelled.");
        }

        return orderMapper.toOrderDTO(order);
    }




    @Transactional
    public OrderDTO updateDeliveryStatus(Integer orderId,DeliveryStatus newDeliveryStatus) {
        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new IllegalStateException("Order with id " + orderId + " not found"));
        if (order.getDelivery() != null) {
            order.getDelivery().setStatus(newDeliveryStatus);
            deliveryRepo.save(order.getDelivery());
            if (newDeliveryStatus == DeliveryStatus.DELIVERED) {
                sendEmailService.sendEmail(order.getDelivery().getEmail(),
                        "Delivery Completed",
                        "Your delivery for order #" + orderId + " has been completed.");
            } else if (newDeliveryStatus == DeliveryStatus.DELAYED) {
                sendEmailService.sendEmail(order.getDelivery().getEmail(),
                        "Delivery Delayed",
                        "Your delivery for order #" + orderId + " has been delayed.");
            }
        }

        return orderMapper.toOrderDTO(order);
    }


    public List<OrderDTO> getOrdersByOrderId(Integer orderId) {
        List<Order> orders = orderRepo.findById(orderId).stream().collect(Collectors.toList());
        return orders.stream().map(orderMapper::toOrderDTO).collect(Collectors.toList());
    }
    public List<OrderDTO> getOrdersByEmail(String email) {
        List<Order> orders = orderRepo.findByDeliveryEmail(email);
        return orders.stream().map(orderMapper::toOrderDTO).collect(Collectors.toList());
    }
    public List<OrderDTO> getOrdersByStatus(String status) {
        OrderStatus orderStatus;
        try {
            orderStatus = OrderStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalStateException("Invalid order status value: " + status);
        }
        List<Order> orders = orderRepo.findByStatus(orderStatus);
        return orders.stream().map(orderMapper::toOrderDTO).collect(Collectors.toList());
    }

    public List<OrderDTO> getOrdersByName(String name) {
        List<Order> orders = orderRepo.findByDeliveryName(name);
        return orders.stream().map(orderMapper::toOrderDTO).collect(Collectors.toList());
    }

    public List<OrderDTO> getOrdersByPhone(String phone) {
        List<Order> orders = orderRepo.findByDeliveryPhone(phone);
        return orders.stream().map(orderMapper::toOrderDTO).collect(Collectors.toList());
    }

    public Optional<Double> getTotalForMonth(int year, int month) {
        return orderRepo.findTotalForMonth(year, month);
    }



    public List<OrderDTO> getAllOrdersWithDetails() {
        List<Order> orders = orderRepo.findAllWithItems();
        return orders.stream()
                .map(orderMapper::toOrderDTO)
                .collect(Collectors.toList());
    }




}
