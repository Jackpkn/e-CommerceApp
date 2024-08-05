package org.example.ecommerceapp.Features.Customer.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.ecommerceapp.Features.Card.Entity.Card;
import org.example.ecommerceapp.Features.Cart.Entity.Cart;
import org.example.ecommerceapp.Features.User.Entity.UserEntity;
import org.springframework.core.Ordered;


import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="customers")
@PrimaryKeyJoinColumn(name = "customer_id")
public class Customer extends UserEntity {

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cart_id")
//	@JsonIgnore
    private Cart cart;

    @OneToMany(cascade=CascadeType.ALL)
    private List<Ordered> orders = new ArrayList<Ordered>();

    @OneToOne
    @JoinColumn(name = "card_Number")
    private Card cardDetails;

    @JsonIgnore
    public Card getCardDetails() {
        return this.cardDetails;
    }

}
