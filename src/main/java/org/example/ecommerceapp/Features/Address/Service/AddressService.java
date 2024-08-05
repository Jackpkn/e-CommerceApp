package org.example.ecommerceapp.Features.Address.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;


import org.example.ecommerceapp.Exception.AddressNotFoundException;
import org.example.ecommerceapp.Exception.UserNotFoundException;
import org.example.ecommerceapp.Features.Address.Entity.Address;
import org.example.ecommerceapp.Features.Address.Repository.AddressCrudRepo;
import org.example.ecommerceapp.Features.Customer.Entity.Customer;
import org.example.ecommerceapp.Features.User.Entity.UserEntity;

import org.springframework.stereotype.Service;



@Service
public class AddressService implements AddressServiceInterface {


    private AddressCrudRepo addressCrudRepo;


    //-------------------------------------------------------------------------//
    //	1. To add Address to table
    //-------------------------------------------------------------------------//
    @Override
    public Address addAddress(Address address) {


        Address savedAddress = null;

        savedAddress = addressCrudRepo.save(address);

        return savedAddress;
    }


    //-------------------------------------------------------------------------//
    //	1. To list all the user by city
    //-------------------------------------------------------------------------//
    @Override
    public Set<UserEntity> listAllUserByCity(String city) {

        List<Address> listAddress = addressCrudRepo.findByCity(city);

        //Use hash set to remove the duplicates
        Set<UserEntity> user = new HashSet<>();

        if (!listAddress.isEmpty()) {

            //Iterating to the list and adding the user
            for (Address address : listAddress) {
                user.add(address.getUser());
            }

        } else {
            throw new UserNotFoundException("No user Found in " + city);
        }
        return user;
    }


    //-------------------------------------------------------------------------//
    //	1. To list all the user by state
    //-------------------------------------------------------------------------//
    @Override
    public Set<UserEntity> listAllUserByState(String state) {

        List<Address> listAddress = addressCrudRepo.findByState(state);

        //Use hash set to remove the duplicates
        return getUserEntities(state, listAddress);
    }


    //-------------------------------------------------------------------------//
    //	1. To list all the user by pincode
    //-------------------------------------------------------------------------//
    @Override
    public Set<UserEntity> listAllUserByPincode(String pincode) {

        List<Address> listAddress = addressCrudRepo.findByPincode(pincode);

        //Use hash set to remove the duplicates
        return getUserEntities(pincode, listAddress);
    }

    private Set<UserEntity> getUserEntities(String pincode, List<Address> listAddress) {
        Set<UserEntity> user = new HashSet<>();

        if (!listAddress.isEmpty()) {
            //Iterating to the list and adding the user
            for (Address address : listAddress) {
                user.add(address.getUser());
            }

        } else {
            throw new AddressNotFoundException("No user Found in " + pincode);
        }
        return user;
    }


    //-------------------------------------------------------------------------//
    //  1. To save the address to table
    //	2. To provide Bi-directional linking
    //-------------------------------------------------------------------------//
    @Override
    public Address persistCustomer(Customer customer, Address address) {

        //set the customer reference to the address
        address.setUser(customer);

        //save the address and return

        return addressCrudRepo.save(address);
    }


    //-------------------------------------------------------------------------//
    //  1. To delete the address to table
    //	2. To remove the Bi-directional linking
    //-------------------------------------------------------------------------//
    @Override
    public String deleteAddress(Integer addressId) {
        // TODO Auto-generated method stub
        Optional<Address> findAddress = addressCrudRepo.findById(addressId);

        //Change the user reference to Null to remove the bi-directional link
        findAddress.ifPresent(address -> address.setUser(null));


        if (findAddress.isPresent()) {

            //delete the address
            addressCrudRepo.deleteById(addressId);

            return "Address with ID : " + addressId + " deleted.";
        } else {
            throw new AddressNotFoundException("No Address Found");
        }

    }


    //-------------------------------------------------------------------------//
    //  1. To check the address with given Id
    //-------------------------------------------------------------------------//
    @Override
    public boolean checkAddressId(int addressId) {
        Optional<Address> opt = addressCrudRepo.findById(addressId);
        return opt.isPresent();
    }


    //-------------------------------------------------------------------------//
    //  1. To get the address with given Id
    //-------------------------------------------------------------------------//
    @Override
    public Address getAddressById(int addressId) {
        Optional<Address> address = addressCrudRepo.findById(addressId);
        if(address.isEmpty()) {
            throw new AddressNotFoundException("Invalid address ID");
        }
        return address.get();
    }



}
