package org.example.ecommerceapp.Features.Item.Entity;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemDTO {

	@NotNull(message = "Product Id must Not be Null")
	Integer productId;
	
	
	@Min(0)
	@NotNull(message = "Product Id must not be Null")
	Integer requiredQuantity;
}
