package clueGame;

import java.util.ArrayList;

public class Player {
	private String playerName;
	private ArrayList<Card> hand;
	public Player(String playerName) {
		super();
		this.playerName = playerName;
		hand = new ArrayList<Card>();
	}
	
	public String getPlayerName() {
		return playerName;
	}
	
	public boolean isHuman() {
		return false;
	}
	
	public ArrayList<Card> getHand(){
		return hand;
	}
	
	public void addToHand(Card card) {
		hand.add(card);
	}
}
