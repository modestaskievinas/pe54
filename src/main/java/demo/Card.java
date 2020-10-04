package demo;

public class Card {
	
	public int rank;
	public char suit;
	
	
	public void setRank(int rank) {
		this.rank = rank;
	}


	public void setSuit(char suit) {
		this.suit = suit;
	}


	public int getRank() {
		return rank;
	}


	public char getSuit() {
		return suit;
	}
	
	public Card(String cardString) {
		char cardRank = cardString.charAt(0);
		char cardSuit = cardString.charAt(1);
		//adding dummy XX to match 2=2 
		this.setRank("XX23456789TJQKA".indexOf(cardRank));
		this.setSuit(cardSuit);
	}
}
