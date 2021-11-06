package clueGame;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class ComputerPlayer extends Player {
	
	public ComputerPlayer(String playerName, String color, int playerId) {
		super(playerName, color, playerId);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public ArrayList<Card> createSuggestion(Board board, Room room){
		ArrayList<Card> playerCards = board.getPlayerCards();
		ArrayList<Card> weaponCards = board.getWeaponCards();
		ArrayList<Card> roomCards = board.getRoomCards();
		Random rng = new Random();
		ArrayList<Card> suggestion = new ArrayList<Card>();
		Card rngCard;
		int j;
		for(int i = 0; i < 3; i++) {
			//first, randomly generate unseen player card and add to suggestion
			j = 0;
			switch(i) {
			case(0):
				do {
					int randInt = rng.nextInt(playerCards.size());
					rngCard = playerCards.get(randInt);
					//if it loops for too long, most likely that all player cards have been seen, and no suggestion can be made
					if(j >= 500) {
						return null;
					}
					j++;
				}while(seenCards.contains(rngCard));
				suggestion.add(rngCard);
				break;
			//second, randomly generate unseen room card and add to suggestion
			case(1):
				Card roomCard = board.getCard(room.getName());
				suggestion.add(roomCard);
				break;
			//third, randomly generate unseen weapon card and add to suggestion
			case(2):
				do {
					int randInt = rng.nextInt(weaponCards.size());
					rngCard = weaponCards.get(randInt);
					//if it loops for too long, most likely all weapon cards are seen and no suggestion can be made
					if(j >= 500) {
						return null;
					}
					j++;
				}while(seenCards.contains(rngCard));
				suggestion.add(rngCard);
				break;
			}
		}
		return suggestion;
	}
	
	@Override
	public BoardCell selectTargets(Board board, Set<BoardCell> targetSet) {
		boolean isSeen = false;
		String roomName;
		//main loop checks set of targets for a room
		for(BoardCell target: targetSet) {
			if(target.isRoom()) {
				roomName = board.getRoom(target).getName();
				//checks list of seen cards and sees if the room is one of the seen cards
				for(Card card: seenCards) {
					if(card.getName().equals(roomName)) {
						isSeen = true;
					}
				}
				//if unseen return the current target
				if(!isSeen) {
					return target;
				}
			}
		}
		//randomly select targets otherwise
		Random rng = new Random();
		int randInt = rng.nextInt(targetSet.size());
		int i = 0;
		for(BoardCell target: targetSet) {
			if(randInt == i) {
				return target;
			}
			i++;
		}
		return null;
	}
	
}
