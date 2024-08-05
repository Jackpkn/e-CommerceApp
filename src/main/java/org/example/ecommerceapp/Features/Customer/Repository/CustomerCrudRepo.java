package org.example.ecommerceapp.Features.Customer.Repository;

import org.example.ecommerceapp.Features.Customer.Entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface CustomerCrudRepo extends JpaRepository<Customer, Integer> {
	
	public Optional<Customer> findByUserName(String username);
	
	public Optional<Customer> findByUserNameAndUserPassword(String userName, String password);
	
	public Customer findByUserId(int customerId);
	
}
