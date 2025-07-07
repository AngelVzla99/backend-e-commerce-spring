package com.example.springboot.Product.entities;

import com.example.springboot.User.entities.CartItemEntity;
import com.example.springboot.Order.entities.OrderItemEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.util.*;

@Entity
@Table(name = "products")
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "discount_id", referencedColumnName = "id")
    @JsonIgnoreProperties("products")
    private ProductDiscountEntity productDiscount;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "price", nullable = false)
    private Long price;

    @Column(name = "tax_percentage", nullable = false)
    private Integer taxPercentage;

    @Column(name = "quantity", nullable = false)
    private Long quantity;

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(name = "photo_url_small", length = 100)
    private String photoUrlSmall;

    @Column(name = "photo_url_medium", length = 100)
    private String photoUrlMedium;

    @Column(name = "photo_url_big", length = 100)
    private String photoUrlBig;

    @Column(name = "amount", length = 10)
    private String amount;

    @Column(name = "weigh", precision = 18, scale = 2)
    private BigDecimal weight;

    @Column(name = "height", precision = 18, scale = 2)
    private BigDecimal height;

    @CreationTimestamp
    @Column(name = "created_at")
    private Date createdAt = new Date();

    @Column(name = "brand", nullable = false, length = 100)
    private String brand;

    // add other columns

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<ProductInventoryEntity> productInventory = Collections.emptyList();

    @ManyToMany(mappedBy = "products", fetch = FetchType.LAZY)
    private Set<CategoryEntity> categories = new HashSet<>();

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<CartItemEntity> cartItems;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<OrderItemEntity> orders;

    @Override
    public String toString() {
        return "ProductEntity{" +
                "id=" + id +
                ", productDiscount=" + productDiscount +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", taxPercentage=" + taxPercentage +
                ", quantity=" + quantity +
                ", description='" + description + '\'' +
                ", photoUrlSmall='" + photoUrlSmall + '\'' +
                ", photoUrlMedium='" + photoUrlMedium + '\'' +
                ", photoUrlBig='" + photoUrlBig + '\'' +
                ", amount='" + amount + '\'' +
                ", weight=" + weight +
                ", height=" + height +
                ", createdAt=" + createdAt +
                ", productInventory=" + productInventory +
                ", categories=" + categories +
                ", cartItems=" + cartItems +
                ", orders=" + orders +
                '}';
    }

    // constructors
    public ProductEntity() { }

    public ProductEntity(Long id, ProductDiscountEntity productDiscount, String name, Long price, Integer taxPercentage, Long quantity, String description, String photoUrlSmall, String photoUrlMedium, String photoUrlBig, String amount, BigDecimal weight, BigDecimal height) {
        this.id = id;
        this.productDiscount = productDiscount;
        this.name = name;
        this.price = price;
        this.taxPercentage = taxPercentage;
        this.quantity = quantity;
        this.description = description;
        this.photoUrlSmall = photoUrlSmall;
        this.photoUrlMedium = photoUrlMedium;
        this.photoUrlBig = photoUrlBig;
        this.amount = amount;
        this.weight = weight;
        this.height = height;
    }

    // getters ans setters


    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void addToQuantity(Integer value ){ this.quantity += value; }

    public List<ProductInventoryEntity> getProductInventory() {
        return productInventory;
    }

    public void setProductInventory(List<ProductInventoryEntity> productInventory) {
        this.productInventory = productInventory;
    }

    public Set<CategoryEntity> getCategories() {
        return categories;
    }

    public void setCategories(Set<CategoryEntity> categories) {
        this.categories = categories;
    }

    public List<CartItemEntity> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItemEntity> cartItems) {
        this.cartItems = cartItems;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProductDiscountEntity getProductDiscount() {
        return productDiscount;
    }

    public void setProductDiscount(ProductDiscountEntity productDiscount) {
        this.productDiscount = productDiscount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Integer getTaxPercentage() {
        return taxPercentage;
    }

    public void setTaxPercentage(Integer taxPercentage) {
        this.taxPercentage = taxPercentage;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhotoUrlSmall() {
        return photoUrlSmall;
    }

    public void setPhotoUrlSmall(String photoUrlSmall) {
        this.photoUrlSmall = photoUrlSmall;
    }

    public String getPhotoUrlMedium() {
        return photoUrlMedium;
    }

    public void setPhotoUrlMedium(String photoUrlMedium) {
        this.photoUrlMedium = photoUrlMedium;
    }

    public String getPhotoUrlBig() {
        return photoUrlBig;
    }

    public void setPhotoUrlBig(String photoUrlBig) {
        this.photoUrlBig = photoUrlBig;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public BigDecimal getHeight() {
        return height;
    }

    public void setHeight(BigDecimal height) {
        this.height = height;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}