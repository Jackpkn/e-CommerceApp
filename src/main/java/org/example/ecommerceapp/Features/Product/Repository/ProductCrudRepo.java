package org.example.ecommerceapp.Features.Product.Repository;

import org.example.ecommerceapp.Features.Product.Entity.Product;
import org.example.ecommerceapp.Features.Product.Entity.ProductCategory;
import org.example.ecommerceapp.Features.Product.Entity.ProductStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductCrudRepo extends JpaRepository<Product, Integer> {

	
	public List<Product> findByCategory(ProductCategory category);
	
	public List<Product> findAllByProductStatus(ProductStatus productStatus);
	
	public Product findByProductId(int productId);
}
