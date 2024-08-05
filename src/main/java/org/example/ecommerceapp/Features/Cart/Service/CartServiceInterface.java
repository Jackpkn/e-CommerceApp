package org.example.ecommerceapp.Features.Cart.Service;


import org.example.ecommerceapp.Features.Cart.Entity.Cart;
import org.example.ecommerceapp.Features.Customer.Entity.Customer;
import org.example.ecommerceapp.Features.Item.Entity.Item;
import org.example.ecommerceapp.Features.Item.Entity.ItemDTO;

import java.util.List;

public interface CartServiceInterface {
	
	public Cart saveCart(Customer customer, Item item);
	
	public List<Item> getAllItem(Cart cart);
	
	//Test
	public List<Item> sendToOrder(int customerId);

	public Cart alterCart(Customer customer, ItemDTO newItem);
}
