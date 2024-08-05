package org.example.ecommerceapp.Features.Seller.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.example.ecommerceapp.Features.Product.Entity.Product;
import org.example.ecommerceapp.Features.User.Entity.UserEntity;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@Entity
@PrimaryKeyJoinColumn(name = "seller_id")
public class Seller extends UserEntity {

    @OneToMany(cascade = CascadeType.ALL)
    @JsonIgnoreProperties("seller")
    private List<Product> products = new ArrayList<>();

}
