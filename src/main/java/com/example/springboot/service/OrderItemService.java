package com.example.springboot.service;

import com.example.springboot.converter.OrderItemConverter;
import com.example.springboot.dto.OrderItemDTO;
import com.example.springboot.model.CartItemEntity;
import com.example.springboot.model.OrderEntity;
import com.example.springboot.model.OrderItemEntity;
import com.example.springboot.model.ProductEntity;
import com.example.springboot.repository.OrderEntityRepository;
import com.example.springboot.repository.OrderItemEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderItemService {
    @Autowired
    private OrderItemEntityRepository orderItemRepository;
    @Autowired
    private OrderItemConverter orderItemConverter;

    public List<OrderItemEntity> saveAll(List<OrderItemDTO> orderItems, OrderEntity orderEntity) {
        List<OrderItemEntity> orderItemEntities = orderItemConverter.toEntityList(orderItems, orderEntity);
        // VALIDATION: check stock (this can also be made in the conversion DTO)
        List<Long> productsOutOfStock = productsOutOfStock(orderItemEntities);
        if (productsOutOfStock.size() > 0)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The products with id " + productsOutOfStock.toString() + " are out of stock");
        // ACTION: delete products from stock
        orderItemEntities.forEach(
                orderItemEntity ->
                        orderItemEntity
                                .getProduct()
                                .addToQuantity(-orderItemEntity.getQuantity())
        );
        // save in the db
        return orderItemRepository.saveAll(orderItemEntities);
    }

    public List<Long> productsOutOfStock(List<OrderItemEntity> productsToCheck) {
        // iterate over each CartItemDTO and check if its corresponding ProductEntity is out of stock
        List<Long> outOfStock = new ArrayList<>();
        for (OrderItemEntity item : productsToCheck) {
            ProductEntity product = item.getProduct();
            if (product.getQuantity() < item.getQuantity()) {
                outOfStock.add(product.getId());
            }
        }
        return outOfStock;
    }

    public List<OrderItemEntity> getAll() {
        return orderItemRepository.findAll();
    }

    public Page<OrderItemEntity> findAllPageable(Pageable pageable) {
        return orderItemRepository.findAll(pageable);
    }

    public Optional<OrderItemEntity> findById(Long id) {
        return orderItemRepository.findById(id);
    }

    public OrderItemEntity save(OrderItemEntity productEntity) {
        return orderItemRepository.save(productEntity);
    }

    public void delete(Long id) {
        orderItemRepository.deleteById(id);
    }
}
