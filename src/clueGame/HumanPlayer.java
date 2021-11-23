package clueGame;

import java.util.ArrayList;

public class HumanPlayer extends Player {

	private ArrayList<Card> suggestion = new ArrayList<Card>();
	
	public HumanPlayer(String playerName, String color, String column, String row, int playerId) {
		super(playerName, color, column, row, playerId);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public boolean isHuman(){
		return true;
	}

	@Override
	public ArrayList<Card> createSuggestion(Room room){
		ClueGame clueGame = ClueGame.getInstance();
		suggestion.clear();
		ArrayList<Card> unseenPeople = makeUnseenPeople();
		ArrayList<Card> unseenWeapons = makeUnseenWeapons();
		SuggestionFrame suggestionFrame = new SuggestionFrame(room, unseenPeople, unseenWeapons);
		return suggestion;
	}
	public ArrayList<Card> makeUnseenPeople(){
		Board board = Board.getInstance();
		ArrayList<Card> playerCards = board.getPlayerCards();
		ArrayList<Card> unseenPeople = new ArrayList<Card>();
		for(Card card: playerCards) {
			if(!seenCards.contains(card)) {
				unseenPeople.add(card);
			}
		}
		return unseenPeople;
	}
	public ArrayList<Card> makeUnseenWeapons(){
		Board board = Board.getInstance();
		ArrayList<Card> weaponCards = board.getWeaponCards();
		ArrayList<Card> unseenWeapons = new ArrayList<Card>();
		for(Card card: weaponCards) {
			if(!seenCards.contains(card)) {
				unseenWeapons.add(card);
			}
		}
		return unseenWeapons;
	}
}
