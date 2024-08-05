package org.example.ecommerceapp.Features.Product.Controller;
import jakarta.validation.Valid;
import org.example.ecommerceapp.Exception.SellerNotFoundException;
import org.example.ecommerceapp.Features.Product.Entity.Product;
import org.example.ecommerceapp.Features.Product.Entity.ProductCategory;
import org.example.ecommerceapp.Features.Product.Service.ProductServiceInterface;
import org.example.ecommerceapp.Features.Seller.Entity.Seller;
import org.example.ecommerceapp.Features.Seller.Service.SellerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/ecommerce/productsPortal")
public class ProductController {

	private ProductServiceInterface productServ;
	

	private SellerService sellerServ;
	
	@PostMapping("/product")
	public ResponseEntity<Product> addProduct(@Valid @RequestBody Product product){
		
		
		  Optional<Seller> sellerr = sellerServ.getSellerCrudRepo().findById(product.getSeller().getUserId());
		  
		  if(sellerr.isPresent()) { 
			  Seller seller = sellerr.get();
			  
			  product.setSeller(seller);
			  
			  Product savedProduct = productServ.addProduct(product); 
			  

			  sellerServ.addProducts(product.getSeller().getUserId(), product);
		  
			  return new ResponseEntity<>(savedProduct, HttpStatus.OK); 
		  } 
		  else 
			  throw new SellerNotFoundException("Seller not found");

	}
	
	@GetMapping("/all")
	public ResponseEntity<List<Product>> getAllProducts(){
		
		List<Product> allProducts = productServ.getAllProdcuts();
		
		return new ResponseEntity<List<Product>>(allProducts,HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Product> getProductById(@PathVariable("id") Integer id ){
		
		Product product = productServ.getProductById(id);
		
		return new ResponseEntity<>(product,HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/category/{cate}")
	public ResponseEntity<List<Product>> getProductByCategory(@PathVariable("cate") ProductCategory cate ){
	
		List<Product> allProductsCategory = productServ.getProductsByCategory(cate);
		
		return new ResponseEntity<List<Product>>(allProductsCategory, HttpStatus.ACCEPTED);
	}
	
}
