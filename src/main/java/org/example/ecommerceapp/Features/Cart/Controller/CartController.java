package org.example.ecommerceapp.Features.Cart.Controller;


import org.example.ecommerceapp.Exception.ProductNotFoundException;

import org.example.ecommerceapp.Features.Cart.Entity.Cart;
import org.example.ecommerceapp.Features.Cart.Repository.CartCrudRepo;
import org.example.ecommerceapp.Features.Cart.Service.CartService;
import org.example.ecommerceapp.Features.Customer.Entity.Customer;
import org.example.ecommerceapp.Features.Customer.Repository.CustomerCrudRepo;
import org.example.ecommerceapp.Features.Item.Entity.Item;
import org.example.ecommerceapp.Features.Item.Entity.ItemDTO;
import org.example.ecommerceapp.Features.Item.Service.ItemServiceInterface;
import org.example.ecommerceapp.Features.Product.Entity.Product;
import org.example.ecommerceapp.Features.Product.Repository.ProductCrudRepo;
import org.example.ecommerceapp.JwtService.JwtService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/ecommerce/customersPortal")
public class CartController {


	private CustomerCrudRepo customerCrudRepo;


	private JwtService loginService;


	private CartService cartService;


	private CartCrudRepo cartCrudRepo;


	private ItemServiceInterface itemService;

	private ProductCrudRepo productCrudRepo;

	// Handle --> / e-commerce/customersPortal/cart
	// What is does? --> Adds the given product to the cart with the specified
	// quantity
	// Request Type? --> POST request
	// Input --> API key as request parameter, and item with valid product id and
	// quantity
	@PostMapping(value = "/cart")
	public ResponseEntity<Cart> addToCart(@RequestParam String key, @RequestBody ItemDTO item) throws Exception {

		boolean loggedUser = loginService.validateToken(key);

		Customer customer = customerCrudRepo.findByUserId(Integer.parseInt(String.valueOf(loggedUser)));
		Integer productId = item.getProductId();
		// System.out.println(productId);

		Optional<Product> optProduct = productCrudRepo.findById(productId);

		if (optProduct.isPresent()) {
			Product product = optProduct.get();

			Item savedItem = itemService.addItem(product, item.getRequiredQuantity());
			Cart savedCart = cartService.saveCart(customer, savedItem);
			return new ResponseEntity<>(savedCart, HttpStatus.ACCEPTED);
		} else {
			throw new ProductNotFoundException("Product with this Id does not exist");
		}
	}

	// Handle --> /ecommerce/customersPortal/cart/view
	// What is does? --> Shows all the products with quantity in the cart
	// Request Type? --> POST request
	// Input --> API key as request parameter, and item with valid product id and
	// quantity
	@GetMapping(value = "/cart/view")
	public ResponseEntity<List<Item>> viewCart(@RequestParam String key) throws Exception {

		boolean loggedUser = loginService.validateToken(key);

		Customer customer = customerCrudRepo.findByUserId(Integer.parseInt(String.valueOf(loggedUser)));

		Optional<Cart> optloggedCustomerCart = cartCrudRepo.findById(customer.getCart().getCartId());

		Cart loggedCustomerCart = optloggedCustomerCart.get();
		List<Item> items = cartService.getAllItem(loggedCustomerCart);

		return new ResponseEntity<>(items, HttpStatus.OK);
	}

	@PostMapping(value = "/cart/alter")
	public ResponseEntity<Cart> alterCart(@RequestParam String key, @RequestBody ItemDTO item) throws Exception {

		boolean loggedUser = loginService.validateToken(key);

		Customer customer = customerCrudRepo.findByUserId(Integer.parseInt(String.valueOf(loggedUser)));


		Cart cart = cartService.alterCart(customer, item);

		return new ResponseEntity<Cart>(cart, HttpStatus.ACCEPTED);
	}
}
