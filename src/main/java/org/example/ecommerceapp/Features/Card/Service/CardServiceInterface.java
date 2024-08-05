package org.example.ecommerceapp.Features.Card.Service;


import org.example.ecommerceapp.Features.Card.Entity.Card;

public interface CardServiceInterface {
	
	public Card addCard(Card card);
	
	public String removeCard(Card card);
	
	public Card updateCard(Card card, Card updatedCard);
	
}
