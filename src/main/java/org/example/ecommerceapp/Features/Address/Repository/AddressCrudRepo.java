package org.example.ecommerceapp.Features.Address.Repository;

import org.example.ecommerceapp.Features.Address.Entity.Address;
import org.example.ecommerceapp.Features.User.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressCrudRepo extends JpaRepository<Address, Integer> {

    public List<Address> findByUser(UserEntity user);

    public List<Address> findByCity(String city);

    public List<Address> findByState(String city);

    public List<Address> findByPincode(String pincode);

    public List<Address> deleteAllByUser(UserEntity user);

}
