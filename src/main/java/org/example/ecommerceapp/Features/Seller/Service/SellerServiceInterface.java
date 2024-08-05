package org.example.ecommerceapp.Features.Seller.Service;


import org.example.ecommerceapp.Features.Address.Entity.Address;
import org.example.ecommerceapp.Features.Authentication.Entity.Login;
import org.example.ecommerceapp.Features.Product.Entity.Product;
import org.example.ecommerceapp.Features.Product.Entity.ProductDTO;
import org.example.ecommerceapp.Features.Seller.Entity.Seller;
import org.example.ecommerceapp.Features.User.DTOs.UserDTO;
import org.example.ecommerceapp.Features.User.Entity.UserEntity;

import java.util.List;

public interface SellerServiceInterface {
	
	public Seller addSeller(Seller seller);
	
	public String removeSeller(UserDTO userInfo);
	
	public Seller updateSeller(UserDTO sellerInfo, Integer id);
	
	public Seller addProducts(Integer sellerId, Product product);
	
	public Seller findByUsernameAndPassword(String username, String password);
	
	public Seller persistSeller(Integer sellerId, Login login);
	
	public Seller addSellerAddress(Integer sellerId, Address address);
	
	public Seller updateProducts(Integer sellerId, Integer productId, ProductDTO product);
	
	public Seller updateProductStatus(Integer sellerId, Integer productId);
	
	public Seller removeSellerAddress(Integer sellerId, Integer addressId);
	
	public List<Product> viewAllProductsBySeller(Integer sellerId);
	
	public List<Seller> viewAllSeller();
	
}
