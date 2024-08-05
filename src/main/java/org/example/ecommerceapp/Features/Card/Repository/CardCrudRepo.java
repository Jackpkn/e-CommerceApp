package org.example.ecommerceapp.Features.Card.Repository;

import java.util.Optional;

import org.example.ecommerceapp.Features.Card.Entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardCrudRepo extends JpaRepository<Card, Integer> {

	public Optional<Card> findByCardNumber(String string);
}
