package org.example.ecommerceapp.Features.Card.Service;

import org.example.ecommerceapp.Exception.CardAlreadyExistException;
import org.example.ecommerceapp.Exception.CardDetailsNotFoundException;

import org.example.ecommerceapp.Features.Card.Entity.Card;
import org.example.ecommerceapp.Features.Card.Repository.CardCrudRepo;

import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class CardService implements CardServiceInterface {
	

	private CardCrudRepo cardCrudRepo;
	
	@Override
	public Card addCard(Card card) {
		Optional<Card> checkCard = cardCrudRepo.findByCardNumber(card.getCardNumber());
		Card savedcard = null;
		
		if (checkCard.isEmpty()) {
			savedcard = cardCrudRepo.save(card);
		} else {
			throw new CardAlreadyExistException("Card details exist");
		}
		
		return savedcard;
	}

	@Override
	public String removeCard(Card card) {
		Optional<Card> checkSeller = cardCrudRepo.findByCardNumber(card.getCardNumber());
		
		if (checkSeller.isPresent()) {
			cardCrudRepo.delete(card);
			return "Card number " + card.getCardNumber() + " removed succesfully";
		} else {
			throw new CardDetailsNotFoundException("Card number " + card.getCardNumber() + " not Found");
		}
	}
	
	@Override
	public Card updateCard(Card card, Card updatedCard) {
		Optional<Card> checkSeller = cardCrudRepo.findByCardNumber(card.getCardNumber());
		
		if (checkSeller.isPresent()) {
			cardCrudRepo.delete(card);
			Card savedCard = cardCrudRepo.save(updatedCard);
			return savedCard;
		} else {
			throw new CardDetailsNotFoundException("Card number " + card.getCardNumber() + " not Found");
		}
	}
	

}
