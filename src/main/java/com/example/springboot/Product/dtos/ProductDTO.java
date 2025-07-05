package com.example.springboot.Product.dtos;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
public class ProductDTO {
    private Long id;
    private Long discountId;
    @NotNull(message = "name is required")
    @Size(min = 2, message = "the name should have at least 2 characters")
    private String name;
    @DecimalMin(value = "0", message = "price must be greater than or equal to 0")
    private Long price;
    @DecimalMin(value = "0", message = "taxPercentage must be greater than or equal to 0")
    @DecimalMax(value = "100", message = "taxPercentage must be less than or equal to 100")
    private Integer taxPercentage;
    @NotNull(message = "quantity is required")
    @DecimalMin(value = "0", message = "quantity must be 0")
    private Long quantity = 0L;
    @NotNull(message = "description is required")
    private String description;
    @Size(max = 100, message = "photoUrlSmall must be at most 100 characters")
    private String photoUrlSmall;
    @Size(max = 100, message = "photoUrlMedium must be at most 100 characters")
    private String photoUrlMedium;
    @Size(max = 100, message = "photoUrlBig must be at most 100 characters")
    private String photoUrlBig;
    private String amount;
    private BigDecimal weight;
    private BigDecimal height;
    private String brand;
    @NotNull(message = "categories is required")
//    @Size(min = 1, message = "at least one category is required")
    private List<Long> categories;

    public ProductDTO() {}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductDTO that)) return false;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getDiscountId(), that.getDiscountId()) && Objects.equals(getName(), that.getName()) && Objects.equals(getPrice(), that.getPrice()) && Objects.equals(getTaxPercentage(), that.getTaxPercentage()) && Objects.equals(getQuantity(), that.getQuantity()) && Objects.equals(getDescription(), that.getDescription()) && Objects.equals(getPhotoUrlSmall(), that.getPhotoUrlSmall()) && Objects.equals(getPhotoUrlMedium(), that.getPhotoUrlMedium()) && Objects.equals(getPhotoUrlBig(), that.getPhotoUrlBig()) && Objects.equals(getAmount(), that.getAmount()) && Objects.equals(getWeight(), that.getWeight()) && Objects.equals(getHeight(), that.getHeight()) && Objects.equals(getCategories(), that.getCategories());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getDiscountId(), getName(), getPrice(), getTaxPercentage(), getQuantity(), getDescription(), getPhotoUrlSmall(), getPhotoUrlMedium(), getPhotoUrlBig(), getAmount(), getWeight(), getHeight(), getCategories());
    }

    // toString() method

    @Override
    public String toString() {
        return "ProductDTO{" +
                "id=" + id +
                ", discountId=" + discountId +
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
                '}';
    }
}