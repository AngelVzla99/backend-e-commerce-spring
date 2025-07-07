package com.example.springboot.Order.services;


import com.example.springboot.Order.dtos.OrderAdminDto;
import com.example.springboot.Order.dtos.PaymentMethodDTO;
import com.example.springboot.Order.converters.OrderConverter;
import com.example.springboot.Order.dtos.OrderDTO;
import com.example.springboot.Order.entities.OrderEntity;
import com.example.springboot.Order.entities.OrderItemEntity;
import com.example.springboot.Order.repositories.OrderEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderEntityRepository orderRepository;
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private PaymentMethodService paymentMethodService;
    @Autowired
    private OrderConverter orderConverter;

    public Page<OrderAdminDto> findAllPageable(Pageable pageable) {
        return orderRepository.findAll(pageable).map(orderConverter::toAdminDto);
    }

    public OrderDTO findById(Long id) {
        OrderEntity order = orderRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "The product with id " + id + " was not found"));
        return orderConverter.toDto(order);
    }

    public void delete(Long id) {
        orderRepository.deleteById(id);
    }

    // =============================
    //    method to get entities  //
    // =============================

    private OrderEntity findOrderById(Long orderId) {
        return orderRepository
                .findById(orderId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "The order with id " + orderId + " was not found"));
    }

    // ==================================
    //  change of state in the orders  //
    // ==================================

    @Transactional
    public OrderDTO createOrder(OrderDTO orderDTO) {
        // === convert DTO to Entity and save ===
        OrderEntity orderEntity = orderConverter.toEntity(orderDTO);
        OrderEntity order = orderRepository.save(orderEntity);

        // === save order items ===
        // STEP: reduce inventory of the purchased products
        List<OrderItemEntity> items = orderItemService.saveAll(orderDTO.getOrderItems(), order);

        // === save order payments ===
        paymentMethodService.saveAll(orderDTO.getPaymentMethods(), order.getUser(), order);

        // VALIDATION: ENOUGH MONEY TO PAY
        Long sumPayments = 0L, sumCosts = 0L;
        for (PaymentMethodDTO payment : orderDTO.getPaymentMethods()) sumPayments += payment.getAmount();
        for (OrderItemEntity item : items) sumCosts += item.getQuantity() * item.getProduct().getPrice();
        if (sumPayments < sumCosts)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not enough money to pay all products");

        // order to DTO
        return orderConverter.toDto(order);
    }

    public void paymentChecked(Long orderId) {
        OrderEntity order = findOrderById(orderId);
        order.setStatusCode(2);
        orderRepository.save(order);
    }

    public void riderComing(Long orderId) {
        OrderEntity order = findOrderById(orderId);
        order.setStatusCode(3);
        orderRepository.save(order);
    }

    public void orderCompleted(Long orderId) {
        OrderEntity order = findOrderById(orderId);
        order.setStatusCode(4);
        orderRepository.save(order);
    }

    public void orderCancelled(Long orderId) {
        OrderEntity order = findOrderById(orderId);
        order.setStatusCode(5);
        orderRepository.save(order);
    }
}
