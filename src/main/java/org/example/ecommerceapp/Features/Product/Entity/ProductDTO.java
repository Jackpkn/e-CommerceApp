package org.example.ecommerceapp.Features.Product.Entity;


import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
	
		private String productName;
		
		private String description;
		
		@Min(value = 1, message = "Product price should not be 0")
		private Double price;
		
		@Min(value = 1, message = "Minimum quantity should be 1")
		private Integer quantity;
		
//		@NotNull(message = "Only ELECTRONICS, MOBILE, CLOTHES categories allowed")
		private ProductCategory category;
}
