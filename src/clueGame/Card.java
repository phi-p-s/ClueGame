package clueGame;

public class Card {
	private CardType cardType;
	private String name;
	public Card(CardType cardtype, String name) {
		this.name = name;
		this.cardType = cardType;
	}
	
	public String getName() {
		return name;
	}
	public CardType getCardType() {
		return cardType;
	}
}
