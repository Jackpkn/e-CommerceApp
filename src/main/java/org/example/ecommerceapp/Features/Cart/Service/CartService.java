package org.example.ecommerceapp.Features.Cart.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.example.ecommerceapp.Exception.ProductAlreadyFoundException;
import org.example.ecommerceapp.Exception.ProductNotAvailableException;
import org.example.ecommerceapp.Exception.ProductNotFoundException;
import org.example.ecommerceapp.Exception.ProductQuantityNotEnoughException;
import org.example.ecommerceapp.Features.Cart.Entity.Cart;
import org.example.ecommerceapp.Features.Cart.Repository.CartCrudRepo;
import org.example.ecommerceapp.Features.Customer.Entity.Customer;
import org.example.ecommerceapp.Features.Customer.Repository.CustomerCrudRepo;
import org.example.ecommerceapp.Features.Item.Entity.Item;
import org.example.ecommerceapp.Features.Item.Entity.ItemDTO;
import org.example.ecommerceapp.Features.Item.Repository.ItemCrudRepo;
import org.example.ecommerceapp.Features.Product.Entity.Product;
import org.example.ecommerceapp.Features.Product.Entity.ProductStatus;
import org.example.ecommerceapp.Features.Product.Service.ProductServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CartService implements CartServiceInterface {

	private CartCrudRepo cartCrudRepo;

	private CustomerCrudRepo customerCrudRepo;

	private ItemCrudRepo itemCrudRepo;

	private ProductServiceInterface productService;
	
	@Override
	public Cart saveCart(Customer customer, Item item) {
			
		
		Integer cartId=(customer.getCart()).getCartId();	
			

		//Checking id the cart has the same product ? if yes ? throw exception.
		List<Item> listOfItems = getAllItem(customer.getCart());
			
		for (Item itemCheck : listOfItems) {
			if (itemCheck.getProduct().getProductId() == item.getProduct().getProductId()) {
				throw new ProductAlreadyFoundException("Product already present in cart, please try to update quantity");
			}
		}
			
		Cart cart=cartCrudRepo.findByCartId(cartId);
		cart.getItems().add(item);

		cart.setCartTotal((cart.getCartTotal()==null) ? 0+(double)item.getItemPrice():cart.getCartTotal().doubleValue() +(double)item.getItemPrice());


		return cartCrudRepo.save(cart);
	}


	@Override
	public List<Item> getAllItem(Cart cart) {
		// TODO Auto-generated method stub
		Optional<Cart> optCart=cartCrudRepo.findById(cart.getCartId());
		
		return optCart.get().getItems();	
	}




	//WORKING FINE
	//WHAT IT DOES IS..  IT REMOVES THE ITEM FROM THE USER CART BUT KEEPS IT IN THE 
	//DATABASE.
	//Returns the list of items deleted from the cart
	@Override
	public List<Item> sendToOrder(int customerId) throws ProductQuantityNotEnoughException, ProductNotAvailableException{
		
		Integer cartId = customerCrudRepo.findByUserId(customerId).getCart().getCartId();
		
		Cart cart = cartCrudRepo.findByCartId(cartId);
		
		List<Item> orders = new ArrayList<>(cart.getItems());
		
		//Checking if the quantity of ALL the products is enough to proceed.
		//If not, throw Not enough quantity exception with proper message.
		for(Item item: orders) {
			if(item.getProduct().getQuantity() < item.getRequiredQuantity()) {
				throw new ProductQuantityNotEnoughException("Only " + item.getProduct().getQuantity() + " units of " + item.getProduct().getProductName() + " are available. Required quantity is: " + item.getRequiredQuantity() + ". Cannot Proceed with purchase.");
			} 
			
			if(item.getProduct().getProductStatus() != ProductStatus.AVAILABLE) {
				throw new ProductNotAvailableException("Product " + item.getProduct().getProductName() + " is not available any more.");
			}
		}
		
		//If the quantity if enough, we reduce the quantity
		for(Item item: orders) {
			productService.reduceQuantity(item.getProduct().getProductId(), item.getRequiredQuantity());
		}

		cart.getItems().clear();
		
		cart.setCartTotal(0.0);
		
		cartCrudRepo.save(cart);
		
		return orders;
	}


	@Override
	public Cart alterCart(Customer customer, ItemDTO newItem) {
		// TODO Auto-generated method stub
		
		List<Item> items = customer.getCart().getItems();
		
		Cart customerCart = customer.getCart();
		
		Product product = null;
		
		if(!items.isEmpty()) {
			
			for(Item element : items) {
				
				if(element.getProduct().getProductId() == newItem.getProductId()) {
					
					product = element.getProduct();
					
					if(newItem.getRequiredQuantity() <= product.getQuantity()) {
						
						double priceDecrease = element.getItemPrice() - (product.getPrice() * element.getRequiredQuantity());
						customerCart.setCartTotal(customerCart.getCartTotal() - element.getItemPrice());
						element.setItemPrice(priceDecrease);	
						

						element.setRequiredQuantity(newItem.getRequiredQuantity()); 
						
						
						double priceIncrease = element.getItemPrice() + (product.getPrice() * element.getRequiredQuantity());
						element.setItemPrice(priceIncrease);
						customerCart.setCartTotal(customerCart.getCartTotal() + element.getItemPrice());
						
					}else {
						throw new ProductQuantityNotEnoughException("Required quantity of specified product not available at the moment");
					}
					
					break;
				}
				
			}
			
			
			if(product == null)
				throw new ProductNotFoundException("No product with the given id is present in this cart");
			
		}else {
			throw new ProductNotFoundException("Product not found in your cart");
		}
		
		
		return cartCrudRepo.save(customer.getCart());
		
	}
	
	
	
}
