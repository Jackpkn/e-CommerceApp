package org.example.ecommerceapp.Features.Item.Repository;


import org.example.ecommerceapp.Features.Item.Entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ItemCrudRepo extends JpaRepository<Item, Integer> {

}
