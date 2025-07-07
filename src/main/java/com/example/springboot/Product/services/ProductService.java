package com.example.springboot.Product.services;

import com.example.springboot.Product.converters.ProductConverter;
import com.example.springboot.Product.dtos.ProductDTO;
import com.example.springboot.Product.entities.ProductEntity;
import com.example.springboot.Product.repositories.ProductEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductEntityRepository productRepository;
    @Autowired
    private ProductConverter productConverter;

    public List<ProductDTO> findFirstProducts(int x) {
        Pageable pageable = PageRequest.of(0, x);
        List<ProductEntity> products = productRepository.findAll(pageable).getContent();
        return productConverter.toDtoList(products);
    }

    public Long countProducts(){
        return productRepository.count();
    }

    public Page<ProductDTO> paginatedSearch( String queryText, Pageable pageable ){
        Page<ProductEntity> products = productRepository.paginatedSearch(queryText,pageable);
        return products.map(productConverter::toDTO);
    }

    public Page<ProductDTO> mostPopulars( Pageable pageable ){
        Page<ProductEntity> products = productRepository.mostPopular(pageable);
        return products.map(productConverter::toDTO);
    }

    public Page<ProductDTO> findAllPageable(Pageable pageable) {
        Page<ProductEntity> responsePage = productRepository.findAll(pageable);
        if (!responsePage.hasContent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        // convert the resulting list to dto
        return responsePage.map(productConverter::toDTO);
    }

    public long count(){
        return productRepository.count();
    }

    public ProductDTO findById(Long id) {
        Optional<ProductEntity> product = productRepository.findById(id);
        if (product.isPresent()) return productConverter.toDTO(product.get());
        else throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @Transactional
    public ProductDTO save(ProductDTO productDTO) {
        ProductEntity productEntity = productConverter.toEntity(productDTO, null);
        ProductEntity product = productRepository.save(productEntity);
        // add product to the category set
        productEntity.getCategories().forEach(categoryEntity -> categoryEntity.addProduct(product) );
        // save product in the database
        return productConverter.toDTO(product);
    }

    public void delete(Long id) {
        if( productRepository.findById(id).isEmpty() )
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"This discount doesn't exist");
        productRepository.deleteById(id);
    }
}
