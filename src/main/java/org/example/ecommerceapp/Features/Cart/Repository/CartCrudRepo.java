package org.example.ecommerceapp.Features.Cart.Repository;

import org.example.ecommerceapp.Features.Cart.Entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface CartCrudRepo extends JpaRepository<Cart, Integer> {
	
	public Cart findByCartId(int cartId);
}
