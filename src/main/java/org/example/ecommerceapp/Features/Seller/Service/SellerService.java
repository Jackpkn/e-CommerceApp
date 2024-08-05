package org.example.ecommerceapp.Features.Seller.Service;

import org.example.ecommerceapp.Exception.AddressNotFoundException;
import org.example.ecommerceapp.Exception.SellerAlreadyExistException;
import org.example.ecommerceapp.Exception.SellerNotFoundException;
import org.example.ecommerceapp.Features.Address.Entity.Address;
import org.example.ecommerceapp.Features.Address.Service.AddressServiceInterface;
import org.example.ecommerceapp.Features.Authentication.Entity.Login;
import org.example.ecommerceapp.Features.Product.Entity.Product;
import org.example.ecommerceapp.Features.Product.Entity.ProductDTO;
import org.example.ecommerceapp.Features.Product.Entity.ProductStatus;
import org.example.ecommerceapp.Features.Product.Service.ProductService;
import org.example.ecommerceapp.Features.Seller.Entity.Seller;
import org.example.ecommerceapp.Features.Seller.Repository.SellerCrudRepo;
import org.example.ecommerceapp.Features.User.DTOs.UserDTO;
import org.springframework.stereotype.Service;
import lombok.Data;
import java.util.List;
import java.util.Optional;

@Service
@Data
public class SellerService implements SellerServiceInterface {
	

	private SellerCrudRepo sellerCrudRepo;
	

	private ProductService productService;
	

	private AddressServiceInterface addressService;
	
	
	
	//-------------------------------------------------------------------------//
	//	Adding seller into the database
	//-------------------------------------------------------------------------//
	@Override
	public Seller addSeller(Seller seller) {

		
		Optional<Seller> checkSeller = sellerCrudRepo.findByUserName(seller.getUsername());
		
		Seller savedSeller = null;
		
		if (!checkSeller.isPresent()) {
			savedSeller = sellerCrudRepo.save(seller);
		} else {
			throw new SellerAlreadyExistException("Seller Already Exist, try different username & password");
		}
		
		return savedSeller;
	}
	
	
	//-------------------------------------------------------------------------//
	//	1. Passing user details as a DTO
	//	2. Validating the user DTO fields
	//	3. remove the seller if validation is successfull (or) throw SellerNotFoundException
	//-------------------------------------------------------------------------//
	@Override
	public String removeSeller(UserDTO userInfo) {
		
		Optional<Seller> seller = sellerCrudRepo.findByUserName(userInfo.getUserName());
		
		//checing the username & password
		if(seller.isPresent() && seller.get().getUserPassword().equals(userInfo.getUserPassword())) {
			sellerCrudRepo.delete(seller.get());
		} else {
			throw new SellerNotFoundException("username/password is wrong. Please provide the correct details to perform this operation");
		}
		
		return "Successfully deleted " + userInfo.getUserName() + "'s Account from the database";
	}
	
	
	//-------------------------------------------------------------------------//
	//	1. View All seller in the system
	//	2. if not seller is added before, throw SellerNotFoundException
	//-------------------------------------------------------------------------//
	@Override
	public List<Seller> viewAllSeller() {
		// TODO Auto-generated method stub
		
		List<Seller> sellers = sellerCrudRepo.findAll();
		
		//if no seller found in database
		if (sellers.size() <= 0) {
			throw new SellerNotFoundException("No seller added");
		}
		
		return sellers;
	}
	
	
	//-------------------------------------------------------------------------//
	//	1. TO update the seller detials
	// 	2. Get the seller info as a DTO with sellerId
	//	3. checking & updating the sellerInfo only if the DTO fields are not null
	//-------------------------------------------------------------------------//
	@Override
	public Seller updateSeller(UserDTO sellerInfo, Integer sellerId) {
		
		Optional<Seller> opt = sellerCrudRepo.findById(sellerId);
		
		if(opt.isPresent()) {
			Seller seller = opt.get();
			
			//Updating the email
			if(sellerInfo.getEmail() != null) {
				seller.setEmail(sellerInfo.getEmail());
			}
			
			//Updating the First Name
			if(sellerInfo.getFirstName() != null) {
				seller.setFirstName(sellerInfo.getFirstName());
			}
			
			//Updating the Last Name
			if(sellerInfo.getLastName() != null) {
				seller.setLastName(sellerInfo.getLastName());
			}
			
			//Updating the Mobile Number
			if(sellerInfo.getMobileNumber() != null) {
				seller.setMobileNumber(sellerInfo.getMobileNumber());
			}
			
			//Updating the User Name
			if(sellerInfo.getUserName() != null) {
				seller.setUserName(sellerInfo.getUserName());
			}
			
			//Updating the User Password
			if(sellerInfo.getUserPassword() != null) {
				seller.setUserPassword(sellerInfo.getUserPassword());
			}
			
			sellerCrudRepo.save(seller);
			return seller;
		} else {
			throw new SellerNotFoundException("No seller exists with the given id!");
		}
	}

	
	
	//-------------------------------------------------------------------------//
	//	1. Add product method with sellerId & 
	// 	2. Get the seller info as a DTO with sellerId
	//	3. checking if the updating the sellerInfo only if the DTO fields are not null
	//-------------------------------------------------------------------------//
	@Override
	public Seller addProducts(Integer sellerId, Product product) {
		// TODO Auto-generated method stub
		
		//We only accept product if it has quantity > 1, so changing the status to AVAILLABLE
		product.setProductStatus(ProductStatus.AVAILABLE);
		
		Optional<Seller> checkSeller = sellerCrudRepo.findById(sellerId);
		Seller updatedSeller = checkSeller.get();
		
		//we will need to let customer to add address only if he provides address
		if(updatedSeller.getAddresses().isEmpty())
			throw new AddressNotFoundException("Add the address first to add the products");
		
		//adds product in seller list
		updatedSeller.getProducts().add(product);
		
		//provides bi-directional relationship
		productService.addProduct(updatedSeller, product);

        sellerCrudRepo.save(updatedSeller);

        return updatedSeller;
	}
			
	
	
	//-------------------------------------------------------------------------//
	//	1. To find the seller by username & password
	//-------------------------------------------------------------------------//
	@Override
	public Seller findByUsernameAndPassword(String username, String password) {
		
		Optional<Seller> seller = sellerCrudRepo.findByUserNameAndUserPassword(username, password);
		
		if(seller.isPresent()) {
			return seller.get();
		} else {
			throw new SellerNotFoundException("No such seller. Please check the provided details.");
		}
	}
	
	
	
	//-------------------------------------------------------------------------//
	//	1. Used in login module to persist seller
	//-------------------------------------------------------------------------//
	@Override
	public Seller persistSeller(Integer sellerId, Login login) {
		// TODO Auto-generated method stub
		
		Optional<Seller> temp = sellerCrudRepo.findById(sellerId);
		Seller seller = temp.get();
		seller.setLogin(login);
		sellerCrudRepo.save(seller);
		return seller;
	}
	
	
	
	//-------------------------------------------------------------------------//
	//	1. Add address to the given sellerId
	// 	2. Adding address bi-directionally.
	//-------------------------------------------------------------------------//
	@Override
	public Seller addSellerAddress(Integer sellerId, Address address) {
		// TODO Auto-generated method stub
		Optional<Seller> getSeller = sellerCrudRepo.findById(sellerId);
		
		if (getSeller.isPresent()) {
			//setting the referece of seller in his address
			address.setUser(getSeller.get());
			
			//saving the address with seller reference
			Address savedAddress = addressService.addAddress(address);
			
			//adding the address in seller address to get bi-directional relationship
			getSeller.get().getAddresses().add(address);
			
			return sellerCrudRepo.save(getSeller.get());
		} else {
			throw new SellerAlreadyExistException("Seller with the given username already exists.");
		}
	}
	
	
	
	//-------------------------------------------------------------------------//
	//	1. Updating the product status.
	//-------------------------------------------------------------------------//
	@Override
	public Seller updateProductStatus(Integer sellerId, Integer productId) {
		// TODO Auto-generated method stub
		Optional<Seller> seller = sellerCrudRepo.findById(sellerId);
		
		boolean flag = false;
		
		if (seller.isPresent()) {
			
			//if we find the product with given Id we are making the product status UNAVAILLABLE
			for (int i = 0; i < seller.get().getProducts().size(); i++) {
				if (seller.get().getProducts().get(i).getProductId() == productId)
					seller.get().getProducts().get(i).setProductStatus(ProductStatus.UNAVAILABLE);
			}
			
			//To persist the seller in database
			Seller saveSeller = sellerCrudRepo.save(seller.get());
			
			productService.updateProductStatus(productId);
			
			return saveSeller;
		} else {
			throw new SellerNotFoundException("No such seller. Please check the provided details.");
		}
	}

	
	
	//-------------------------------------------------------------------------//
	//	1. TO update the seller product details
	// 	2. Get the product info as a DTO with sellerId & productId
	//	3. Updating the seller product Info only if the DTO fields are not null
	//-------------------------------------------------------------------------//
	@Override
	public Seller updateProducts(Integer sellerId, Integer productId, ProductDTO product){

		Optional<Seller> seller = sellerCrudRepo.findById(sellerId);
		
		boolean flag = false;
		
		if (seller.isPresent()) {
			
			for (int i = 0; i < seller.get().getProducts().size(); i++) {
				if (seller.get().getProducts().get(i).getProductId() == productId) {
					
					//Updating the email
					if(product.getProductName() != null) {
						seller.get().getProducts().get(i).setProductName(product.getProductName());
					}
					
					//Updating the First Name
					if(product.getDescription() != null) {
						seller.get().getProducts().get(i).setDescription(product.getDescription());
					}
					
					//Updating the Last Name
					if(product.getPrice() != null) {
						seller.get().getProducts().get(i).setPrice(product.getPrice());
					}
					
					//Updating the Mobile Number
					if(product.getQuantity() != null) {
						seller.get().getProducts().get(i).setQuantity(product.getQuantity());
					}
					
					//Updating the Mobile Number
					if(product.getCategory() != null) {
						seller.get().getProducts().get(i).setCategory(product.getCategory());
					}
				}
			}
			
			Seller saveSeller = sellerCrudRepo.save(seller.get());
			
			productService.updateProduct(productId, product);
			
			return saveSeller;
		} else {
			throw new SellerNotFoundException("No such seller. Please check the provided details.");
		}

	}
	
	
	
	//-------------------------------------------------------------------------//
	//	1. TO remove the seller address
	// 	2. Get the seller Id & address Id that should be deleted
	//-------------------------------------------------------------------------//
	@Override
	public Seller removeSellerAddress(Integer sellerId, Integer addressId) {
		// TODO Auto-generated method stub
		Optional<Seller> seller = sellerCrudRepo.findById(sellerId);
		
		boolean flag = false;
		
		if (seller.isPresent()) {
			

			
			//if the address Id is not present throw exception
			if (!flag) {
				throw new AddressNotFoundException("Address Not Found");
			}
			
			Seller saveSeller = sellerCrudRepo.save(seller.get());
			
			addressService.deleteAddress(addressId);
			
			return saveSeller;
		} else {
			throw new SellerNotFoundException("No such seller. Please check the provided details.");
		}
	}
	
	
	//-------------------------------------------------------------------------//
	//	1. TO view all the products in posted by the seller
	//-------------------------------------------------------------------------//
	@Override
	public List<Product> viewAllProductsBySeller(Integer sellerId) {
		
		Optional<Seller> seller = sellerCrudRepo.findById(sellerId);
		
		if (seller.isPresent()) {
			
			return seller.get().getProducts();
		} else {
			throw new SellerNotFoundException("No such seller. Please check the provided details.");
		}
	}
	
	
}
