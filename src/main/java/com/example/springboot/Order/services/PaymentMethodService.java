package com.example.springboot.Order.services;

import com.example.springboot.Order.converters.PaymentMethodConverter;
import com.example.springboot.Order.dtos.PaymentMethodDTO;
import com.example.springboot.Order.entities.OrderEntity;
import com.example.springboot.Order.entities.PaymentMethodEntity;
import com.example.springboot.Order.repositories.PaymentMethodEntityRepository;
import com.example.springboot.User.entities.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentMethodService {
    @Autowired
    private PaymentMethodEntityRepository paymentMethodRepository;
    @Autowired
    private PaymentMethodConverter paymentMethodConverter;

    public List<PaymentMethodDTO> saveAll(List<PaymentMethodDTO> orderItems, UserEntity user, OrderEntity orderEntity ){
        List<PaymentMethodEntity> orderItemEntities = paymentMethodConverter
                .toEntityList( orderItems, user, orderEntity );
        List<PaymentMethodEntity> savedOrderItems = paymentMethodRepository.saveAll(orderItemEntities);
        return paymentMethodConverter.toDtoList(savedOrderItems);
    }

    public List<PaymentMethodEntity> getAll() {
        return paymentMethodRepository.findAll();
    }

    public Page<PaymentMethodEntity> findAllPageable(Pageable pageable) {
        return paymentMethodRepository.findAll(pageable);
    }

    public Optional<PaymentMethodEntity> findById(Long id) {
        return paymentMethodRepository.findById(id);
    }

    public PaymentMethodEntity save(PaymentMethodEntity productEntity) {
        return paymentMethodRepository.save(productEntity);
    }

    public void delete(Long id) {
        paymentMethodRepository.deleteById(id);
    }
}
