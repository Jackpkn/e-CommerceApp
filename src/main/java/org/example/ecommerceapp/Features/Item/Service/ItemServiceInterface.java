package org.example.ecommerceapp.Features.Item.Service;


import org.example.ecommerceapp.Features.Item.Entity.Item;
import org.example.ecommerceapp.Features.Product.Entity.Product;

public interface ItemServiceInterface {
	
	public Item addItem(Item item);
	
	public String removeItem(Item item);
	
	public Item updateItem(Item item);
	
	public Item addItem(Product product, int quantity);
}
