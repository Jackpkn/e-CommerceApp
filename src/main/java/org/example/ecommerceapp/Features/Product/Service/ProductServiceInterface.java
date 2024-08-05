package org.example.ecommerceapp.Features.Product.Service;

import org.example.ecommerceapp.Features.Product.Entity.Product;
import org.example.ecommerceapp.Features.Product.Entity.ProductCategory;
import org.example.ecommerceapp.Features.Product.Entity.ProductDTO;
import org.example.ecommerceapp.Features.Seller.Entity.Seller;

import java.util.List;

public interface ProductServiceInterface {

	public Product addProduct(Seller seller, Product product);
	
	public Product addProduct(Product product);
	
	public Product getProductById(Integer id);
	
	public Product reduceQuantity(Integer id, int quantityToReduce);
	
	public String updateProductStatus(Integer productId);
	
	public String updateProduct(Integer productId, ProductDTO product);
	
	public Product updateProductStatusToOutOfStock(Integer productId);
	
	public Product updateProductStatusToUnAvaillable(Integer productId);
	
	public Product updateProductStatusToAvaillable(Integer productId);
	
	public List<Product> getAllProdcuts();
	
	public List<Product> getProductsByCategory(ProductCategory cate);
	
}
