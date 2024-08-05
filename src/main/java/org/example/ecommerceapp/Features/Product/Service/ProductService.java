package org.example.ecommerceapp.Features.Product.Service;

import org.example.ecommerceapp.Exception.ProductNotFoundException;
import org.example.ecommerceapp.Features.Product.Entity.Product;
import org.example.ecommerceapp.Features.Product.Entity.ProductCategory;
import org.example.ecommerceapp.Features.Product.Entity.ProductDTO;
import org.example.ecommerceapp.Features.Product.Entity.ProductStatus;
import org.example.ecommerceapp.Features.Product.Repository.ProductCrudRepo;
import org.example.ecommerceapp.Features.Seller.Entity.Seller;

import org.springframework.stereotype.Service;

import lombok.Data;

import java.util.List;
import java.util.Optional;

@Data
@Service
public class ProductService implements ProductServiceInterface {


	private ProductCrudRepo productRepo;
	
	
	//TO ADD PRODUCT
		@Override
		public Product addProduct(Product product) {

            return productRepo.save(product);
		}
	
	
	//TO ADD PRODUCT
	@Override
	public Product addProduct(Seller seller, Product product) {
		
		product.setSeller(seller);
        return productRepo.save(product);
		
	}

	//TO GET ALL PRODUCTS
	@Override
	public List<Product> getAllProdcuts() {
	
		List<Product> allAvailableProducts = productRepo.findAllByProductStatus(ProductStatus.AVAILABLE);
		List<Product> allOutOfStockProducts = productRepo.findAllByProductStatus(ProductStatus.OUT_OF_STOCK);

		allAvailableProducts.addAll(allOutOfStockProducts);
		
		if(allAvailableProducts.isEmpty()) {
			throw new ProductNotFoundException("No product available");
		}
		return allAvailableProducts;
	}

	
	// TO GET PRODUCT BY ID
	@Override
	public Product getProductById(Integer id) {
		
		Product product = productRepo.findById(id).get();
		if(product == null)
			throw new ProductNotFoundException("No product found in the given id");
		return product;
	}

	
	//TO GET ALL PRODUCTS BY CATEGORY
	@Override
	public List<Product> getProductsByCategory(ProductCategory cate) {
		
		List<Product> productsCategory = productRepo.findByCategory(cate);
		if(productsCategory.isEmpty())
			throw new ProductNotFoundException("No product found in this category");
		
		return productsCategory;
	}

	
	//TO REDUCE QUANTITY AFTER PRODUCTS PURCHASED
	@Override
	public synchronized Product reduceQuantity(Integer id, int quantityToReduce) {
		
		Product updatedProduct = productRepo.findById(id).get();
		
		updatedProduct.setQuantity(updatedProduct.getQuantity() - quantityToReduce);
		
		if(updatedProduct.getQuantity() == 0) {
			updatedProduct.setProductStatus(ProductStatus.OUT_OF_STOCK);
		}
		
		productRepo.save(updatedProduct);
		return updatedProduct;
	}

	
	//TO update the product status if the seller dont want to post
	@Override
	public String updateProductStatus(Integer productId) {
		// TODO Auto-generated method stub
		Optional<Product> findProduct = productRepo.findById(productId);
		
		if (findProduct.isPresent()) {
			
			//checked if the product exist and change its status
			findProduct.get().setQuantity(0);
			findProduct.get().setProductStatus(ProductStatus.UNAVAILABLE);
			productRepo.save(findProduct.get());
			
			return "product with ID : " + productId + " product status updated.";
		} else {
			throw new ProductNotFoundException("No product found in the given id");
		}
		
	}

	
	//update the product detail here to maintain bi-directional mapping
	@Override
	public String updateProduct(Integer productId, ProductDTO product) {
		// TODO Auto-generated method stub
		Optional<Product> findProduct = productRepo.findById(productId);
		
		if(product.getProductName() != null) {
			findProduct.get().setProductName(product.getProductName());
		}
		
		//Updating the First Name
		if(product.getDescription() != null) {
			findProduct.get().setDescription(product.getDescription());
		}
		
		//Updating the Last Name
		if(product.getPrice() != null) {
			findProduct.get().setPrice(product.getPrice());
		}
		
		//Updating the Mobile Number
		if(product.getQuantity() != null) {
			findProduct.get().setQuantity(product.getQuantity());
		}
		
		//Updating the Mobile Number
		if(product.getCategory() != null) {
			findProduct.get().setCategory(product.getCategory());
		}
		
		if (findProduct.isPresent()) {
			productRepo.save(findProduct.get());
			return "product with ID : " + productId + " updated.";
		} else {
			throw new ProductNotFoundException("No product found with the given id");
		}
	}

	
	//To update the product status to Out Of Stock
	@Override
	public Product updateProductStatusToOutOfStock(Integer productId) {
		// TODO Auto-generated method stub
		Optional<Product> product = productRepo.findById(productId);
		
		if(product.isPresent()) {
			product.get().setProductStatus(ProductStatus.OUT_OF_STOCK);
			return product.get();
		} else {
			throw new ProductNotFoundException("No product found with the given id");
		}
			
		
	}

	
	//To update the product status to Un-Available
	@Override
	public Product updateProductStatusToUnAvaillable(Integer productId) {
		// TODO Auto-generated method stub
		Optional<Product> product = productRepo.findById(productId);
		
		if(product.isPresent()) {
			product.get().setProductStatus(ProductStatus.UNAVAILABLE);
			return product.get();
		} else {
			throw new ProductNotFoundException("No product found with the given id");
		}
	}

	
	//To update the product status to Available
	@Override
	public Product updateProductStatusToAvaillable(Integer productId) {
		// TODO Auto-generated method stub
		Optional<Product> product = productRepo.findById(productId);
		
		if(product.isPresent()) {
			product.get().setProductStatus(ProductStatus.AVAILABLE);
			return product.get();
		} else {
			throw new ProductNotFoundException("No product found with the given id");
		}
	}
		
}