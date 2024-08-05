package org.example.ecommerceapp.Features.Seller.Repository;

import org.example.ecommerceapp.Features.Seller.Entity.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface SellerCrudRepo extends JpaRepository<Seller, Integer> {
	
	public Optional<Seller> findByUserName(String userName);
	
	public Optional<Seller> findByUserPassword(String userPassword);
	
	public Optional<Seller> findByUserNameAndUserPassword(String userName, String password);
}
