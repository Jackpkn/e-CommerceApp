package org.example.ecommerceapp.Features.Product.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import lombok.NoArgsConstructor;
import org.example.ecommerceapp.Features.Seller.Entity.Seller;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer productId;

    @NotNull(message = "Product name cannot be empty")
    private String productName;

    @NotNull(message = "Product should have discription")
    private String description;

    @Min(value = 1, message = "Product price should not be 0")
    private Double price;

//	private Double rating;

    @Min(value = 0, message = "Minimum quantity should be 1")
    private Integer quantity;

    //	@NotNull(message = "Only ELECTRONICS, MOBILE, CLOTHES categories allowed")
    private ProductCategory category;

    private ProductStatus productStatus;

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIgnoreProperties("products")
    @JsonIgnore
    private Seller seller;


}
