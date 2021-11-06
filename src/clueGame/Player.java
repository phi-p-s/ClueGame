package clueGame;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Player {
	private String playerName;
	private ArrayList<Card> hand;
	private String color;
	private int playerId;
	protected Set<Card> seenCards;
	public Player(String playerName, String color, int playerId) {
		super();
		this.playerName = playerName;
		this.color = color;
		this.playerId = playerId;
		hand = new ArrayList<Card>();
		seenCards = new HashSet<Card>();
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
	
	public int getPlayerId() {
		return playerId;
	}
	public void addToHand(Card card) {
		hand.add(card);
	}
	public void setHand(ArrayList<Card> cards) {
		hand.clear();
		hand = cards;
	}
	public void resetHand() {
		hand.clear();
	}
	public Card disproveSuggestion(ArrayList<Card> suggestion) {
		for(Card suggestionCard: suggestion) {
			for(Card disproveCard: hand) {
				if(disproveCard.equals(suggestionCard)){
					return disproveCard;
				}
			}
		}
		return null;
	}
	public void startSeen() {
		for(Card card: hand) {
			seenCards.add(card);
		}
	}
	public void updateSeen(Card card) {
		seenCards.add(card);
	}
	//reset seen list for testing
	public void resetSeen() {
		seenCards.clear();
	}
	public ArrayList<Card> createSuggestion(Board board, Room room){
		return null;
	}
	//hard set solution for tests
	public ArrayList<Card> setSuggestion(Card player, Card room, Card weapon){
		ArrayList<Card> suggestion = new ArrayList<Card>();
		suggestion.add(player);
		suggestion.add(room);
		suggestion.add(weapon);
		return suggestion;
	}
	public BoardCell selectTargets(Board board, Set<BoardCell> targetSet) {
		return null;
	}
	
}
