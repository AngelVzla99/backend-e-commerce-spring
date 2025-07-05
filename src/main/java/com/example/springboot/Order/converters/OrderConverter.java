package com.example.springboot.Order.converters;

import com.example.springboot.Order.dtos.OrderAdminDto;
import com.example.springboot.Order.dtos.OrderDTO;
import com.example.springboot.Order.entities.OrderEntity;
import com.example.springboot.User.entities.AddressEntity;
import com.example.springboot.User.entities.UserEntity;
import com.example.springboot.User.repositories.AddressEntityRepository;
import com.example.springboot.User.repositories.UserEntityRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Component
public class OrderConverter {
    private final ModelMapper modelMapper;
    private final OrderItemConverter orderItemConverter;
    private final PaymentMethodConverter paymentMethodConverter;
    private final UserEntityRepository userEntityRepository;
    private final AddressEntityRepository addressEntityRepository;

    public OrderConverter(ModelMapper modelMapper, OrderItemConverter orderItemConverter, PaymentMethodConverter paymentMethodConverter, UserEntityRepository userEntityRepository, AddressEntityRepository addressEntityRepository) {
        this.modelMapper = modelMapper;
        this.orderItemConverter = orderItemConverter;
        this.paymentMethodConverter = paymentMethodConverter;
        this.userEntityRepository = userEntityRepository;
        this.addressEntityRepository = addressEntityRepository;
    }

    public OrderDTO toDto(OrderEntity orderEntity) {
        OrderDTO dto = modelMapper.map(orderEntity, OrderDTO.class);
        // deep conversion
        dto.setOrderItems(orderItemConverter.toDtoList(orderEntity.getOrderItems()));
        dto.setPaymentMethods(paymentMethodConverter.toDtoList(orderEntity.getPaymentMethods()));
        return dto;
    }

    public OrderAdminDto toAdminDto(OrderEntity orderEntity) {
        OrderAdminDto dto = modelMapper.map(orderEntity, OrderAdminDto.class);
        // deep conversion
        dto.setOrderItems(orderItemConverter.toAdminDtoList(orderEntity.getOrderItems()));
        dto.setPaymentMethods(paymentMethodConverter.toDtoList(orderEntity.getPaymentMethods()));

        System.out.println("dto: " + dto);
        System.out.println("dto.getOrderItems(): " + dto.getOrderItems());

        return dto;
    }

    public OrderEntity toEntity(OrderDTO orderDTO) {
        OrderEntity order = modelMapper.map(orderDTO, OrderEntity.class);
        // relations
        UserEntity user = userEntityRepository
                .findById(orderDTO.getUserId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "The user with id " + orderDTO.getUserId() + " was not found"));
        order.setUser(user);
        AddressEntity address = addressEntityRepository
                .findById(orderDTO.getAddressId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "The address with id " + orderDTO.getAddressId() + " was not found"));
        order.setAddress(address);
        return order;
    }

    public List<OrderEntity> toEntityList(List<OrderDTO> DTOs) {
        return DTOs.stream().map(this::toEntity).toList();
    }

    public List<OrderDTO> toDtoList(List<OrderEntity> DTOs) {
        return DTOs.stream().map(this::toDto).toList();
    }
}
