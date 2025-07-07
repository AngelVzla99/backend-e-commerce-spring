package com.example.springboot.Order.converters;

import com.example.springboot.Order.dtos.OrderItemAdminDto;
import com.example.springboot.Order.dtos.OrderItemDTO;
import com.example.springboot.Order.entities.OrderItemEntity;
import com.example.springboot.Order.entities.OrderEntity;
import com.example.springboot.Product.entities.ProductEntity;
import com.example.springboot.Product.repositories.ProductEntityRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Component
public class OrderItemConverter {
    private final ModelMapper modelMapper;
    private final ProductEntityRepository productEntityRepository;


    public OrderItemConverter(ModelMapper modelMapper, ProductEntityRepository productEntityRepository) {
        this.modelMapper = modelMapper;
        this.productEntityRepository = productEntityRepository;
    }

    public OrderItemDTO toDto(OrderItemEntity orderItemEntity) {
        return modelMapper.map(orderItemEntity, OrderItemDTO.class);
    }

    public OrderItemAdminDto toAdminDto(OrderItemEntity orderItemEntity) {
        OrderItemAdminDto dto = modelMapper.map(orderItemEntity, OrderItemAdminDto.class);
        return dto;
    }

    public OrderItemEntity toEntity(OrderItemDTO orderItemDTO, OrderEntity orderEntity) {
        OrderItemEntity entity = new OrderItemEntity();
        // relations
        ProductEntity product = productEntityRepository
                .findById( orderItemDTO.getProductId() )
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "The product with id "+orderItemDTO.getProductId() +" was not found"));
        entity.setProduct(product);
        entity.setOrder(orderEntity);
        entity.setPurchasePrice(orderItemDTO.getPurchasePrice());
        entity.setQuantity(orderItemDTO.getQuantity());
        return entity;
    }

    public List<OrderItemEntity> toEntityList(List<OrderItemDTO> DTOs, OrderEntity orderItemEntity){
        return DTOs.stream().map((orderItemDTO)->this.toEntity(orderItemDTO,orderItemEntity)).toList();
    }

    public List<OrderItemDTO> toDtoList(List<OrderItemEntity> DTOs){
        return DTOs.stream().map(this::toDto).toList();
    }

    public List<OrderItemAdminDto> toAdminDtoList(List<OrderItemEntity> DTOs){
        return DTOs.stream().map(this::toAdminDto).toList();
    }
}
