package org.example.ecommerceapp.Features.Address.Service;


import org.example.ecommerceapp.Features.Address.Entity.Address;
import org.example.ecommerceapp.Features.Customer.Entity.Customer;
import org.example.ecommerceapp.Features.User.Entity.UserEntity;

import java.util.Set;

public interface AddressServiceInterface {

    public Address addAddress(Address address);

    public Set<UserEntity> listAllUserByCity(String city);

    public Set<UserEntity> listAllUserByState(String state);

    public Set<UserEntity> listAllUserByPincode(String pincode);

    public Address persistCustomer(Customer customer, Address address);

    //-------------------------------------------------------------------------//
    //  1. To save the address to table
    //	2. To provide Bi-directional linking
    //-------------------------------------------------------------------------//

    public String deleteAddress(Integer addressId);

    public boolean checkAddressId(int userId);

    public Address getAddressById(int addressId);
}
