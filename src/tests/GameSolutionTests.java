package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


import clueGame.Player;
import clueGame.Board;
import clueGame.BoardCell;
import clueGame.Card;
import clueGame.CardType;

public class GameSolutionTests {
	private static Board board;
	private Card robot = new Card(CardType.PLAYER, "The Robot");
	private Card penny = new Card(CardType.PLAYER, "Penny Robinson");
	private	Card judy = new Card(CardType.PLAYER, "Judy Robinson");
	private	Card john = new Card(CardType.PLAYER, "John Robinson");
	private	Card will = new Card(CardType.PLAYER, "Will Robinson");
	private	Card maureen = new Card(CardType.PLAYER, "Maureen Robinson");
	
	
	private	Card classroom = new Card(CardType.ROOM, "Classroom");
	private Card jupiter = new Card(CardType.ROOM, "Jupiter 02");
	private	Card bridge = new Card(CardType.ROOM, "Bridge");	
	
	private Card plasmaGun = new Card(CardType.WEAPON, "Plasma Gun");
	private	Card screwdriver = new Card(CardType.WEAPON, "Screwdriver");
	private Card crowbar = new Card(CardType.WEAPON, "Crowbar");
	private Card arm = new Card(CardType.WEAPON, "Robot Arm");
	private Card bat = new Card(CardType.WEAPON, "Baseball Bat");
	private Card knife = new Card(CardType.WEAPON, "Lazer Knife");
	@BeforeAll
	public static void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("data\\ClueLayout.csv", "data\\ClueSetup.txt");		
		// Initialize will load config files 
		board.initialize();
		
	}
	
	@Test
	public void accusationTests() {
		board.setSolution(robot, plasmaGun, jupiter);
		ArrayList<Card> testSolution = board.getSolution();
		assertTrue(board.checkSolution(robot, plasmaGun, jupiter));
		assertFalse(board.checkSolution(robot, plasmaGun, bridge));
		assertFalse(board.checkSolution(penny, plasmaGun, jupiter));
		assertFalse(board.checkSolution(robot, crowbar, jupiter));
	}
	
	@Test
	public void suggestionTests() {
		Player currentPlayer = board.getPlayer("Penny Robinson");
		Player nextPlayer = board.getPlayer("Judy Robinson");
		Card playerCard = board.getCard("The Robot");
		Card weaponCard = board.getCard("Crowbar");
		Card roomCard = board.getCard("Storage");
		board.resetDeck();
		board.setSolution(playerCard, roomCard, weaponCard);
		board.dealHands();
		ArrayList<Card> suggestion = currentPlayer.setSuggestion(playerCard, roomCard, weaponCard);
		ArrayList<Card> noMatches = new ArrayList<Card>();
		noMatches.add(bridge);
		nextPlayer.setHand(suggestion);
		
		//if a player has a card that disproves the suggestion, return the first card
		//(first card is normally randomly generated, so it will return a random card)
		Card suggestionCard = nextPlayer.disproveSuggestion(suggestion);
		assertEquals(playerCard, suggestionCard);
		//tests that if the player has no matching cards, return null for the card
		nextPlayer.setHand(noMatches);
		suggestionCard = nextPlayer.disproveSuggestion(suggestion);
		assertEquals(null, suggestionCard);
	}
	
	@Test
	public void handleSuggestionTests() {
		ArrayList<Player> players = board.getPlayers();
		Player penny = players.get(4);
		board.resetDeck();
		board.setSolution(robot, bridge, crowbar);
		board.dealHands();
		//set players hands
		players.get(0).resetHand();
		players.get(0).addToHand(classroom);
		players.get(1).resetHand();
		players.get(1).addToHand(screwdriver);
		players.get(2).resetHand();
		players.get(2).addToHand(jupiter);
		players.get(3).resetHand();
		players.get(3).addToHand(plasmaGun);
		players.get(5).resetHand();
		players.get(5).addToHand(judy);
		//create suggestion of robot bridge and crowbar, no other player has these cards, so it should return null
		ArrayList<Card> suggestion = penny.setSuggestion(robot, bridge, crowbar);
		Card suggestionCard = board.handleSuggestion(penny, suggestion);
		assertEquals(null, suggestionCard);
		//penny now has robot, but since she makes suggestion, should return null
		penny.resetHand();
		penny.addToHand(crowbar);
		suggestionCard = board.handleSuggestion(penny, suggestion);
		assertEquals(null, suggestionCard);
		//suggestion only human can disprove, should return bridge
		players.get(0).addToHand(bridge);
		suggestionCard = board.handleSuggestion(penny, suggestion);
		assertEquals(bridge, suggestionCard);
		//judy is next player after penny, she can disprove before robot, therefore returning the robot card
		players.get(5).resetHand();
		players.get(5).addToHand(robot);
		suggestionCard = board.handleSuggestion(penny, suggestion);
		assertEquals(robot, suggestionCard);
	}
	
	@Test
	public void createComputerSuggestion() {
		ArrayList<Player> players = board.getPlayers();
		ArrayList<Card> playerCards = board.getPlayerCards();
		ArrayList<Card> weaponCards = board.getWeaponCards();
		Player penny = players.get(4);
		board.resetDeck();
		board.setSolution(robot, bridge, crowbar);
		penny.resetSeen();
		//add all players but robot to seen list
		for(Card playerCard: playerCards) {
			if(!playerCard.getName().equals("The Robot")) {
				penny.updateSeen(playerCard);
			}
		}
		//add all weapons but crowbar to seen list
		for(Card weaponCard: weaponCards) {
			if(!weaponCard.getName().equals("Crowbar")) {
				penny.updateSeen(weaponCard);
			}
		}
		//test that robot, bridge, and crowbar are the cards for the suggestion
		ArrayList<Card> suggestion = penny.createSuggestion(board, board.getRoom('B'));
		assertTrue(robot.getName().equals(suggestion.get(0).getName()));
		assertTrue(bridge.getName().equals(suggestion.get(1).getName()));
		assertTrue(crowbar.getName().equals(suggestion.get(2).getName()));
		penny.resetSeen();
		//add every card but robot and judy
		for(Card playerCard: playerCards) {
			if(!(playerCard.getName().equals("The Robot") || playerCard.getName().equals("Judy Robinson"))) {
				penny.updateSeen(playerCard);
			}
		}
		//add every card but crowbar and bat
		for(Card weaponCard: weaponCards) {
			if(!(weaponCard.getName().equals("Crowbar") || weaponCard.getName().equals("Baseball Bat"))) {
				penny.updateSeen(weaponCard);
			}
		}
		int robotCount = 0;
		int crowbarCount = 0;
		//check that it is randomly selecting a card
		for(int i = 0; i < 200; i++) {
			suggestion = penny.createSuggestion(board, board.getRoom('Y'));
			if(suggestion.get(0).getName().equals(robot.getName())) {
				robotCount++;
			}
			if(suggestion.get(2).getName().equals(crowbar.getName())){
				crowbarCount++;
			}
		}
		assertTrue(robotCount >= 50);
		assertTrue(crowbarCount >= 50);
	}
	
	@Test
	public void computerTargetTests() {
		Player penny = board.getPlayer("Penny Robinson");
		board.calcTargets(board.getCell(2,6), 1);
		Set<BoardCell> targets = board.getTargets();
		//targets should include 2,2 1,6 2,7 3,6
		BoardCell selectedTarget = penny.selectTargets(board, targets);
		//should select 2,2 as that is the classroom and has not been seen
		assertEquals(selectedTarget, board.getCell(2, 2));
		//add classroom to seen list, should select a target randomly
		penny.updateSeen(board.getCard("Classroom"));
		int classCounter = 0;
		for(int i = 0; i < 400; i++) {
			selectedTarget = penny.selectTargets(board, targets);
			if(selectedTarget == board.getCell(2, 2)) {
				classCounter++;
			}
		}
		assertTrue(classCounter >= 50);
		//calc targets that have no rooms attached
		board.calcTargets(board.getCell(9, 4), 1);
		//run several tests
		int counter = 0;
		for(int i = 0; i < 400; i++) {
			selectedTarget = penny.selectTargets(board, targets);
			if(selectedTarget == board.getCell(9, 5)) {
				counter++;
			}
		}
		assertTrue(counter >= 50);
	}
	
}
