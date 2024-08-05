package org.example.ecommerceapp.Features.Item.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.ecommerceapp.Features.Product.Entity.Product;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Item {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer itemId;


    @OneToOne
    @JsonIgnoreProperties(value={
            "rating",
            "productId",
            "seller",
            "quantity"

    })

    private Product product;

    private Double itemPrice;

    private Integer requiredQuantity;
}
