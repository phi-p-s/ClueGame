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

public class GameSetUpTests {
	private static Board board;
	
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
	public void readInPlayer() {
		ArrayList<Player> testPlayer = board.getPlayers();
		//check that their is the correct amount of players, the names are loaded, and that the first player is set to human
		assertEquals(6, testPlayer.size());
		assertEquals("The Robot", testPlayer.get(0).getPlayerName());
		assertEquals("Will Robinson", testPlayer.get(2).getPlayerName());
		assertEquals("Judy Robinson", testPlayer.get(5).getPlayerName());
		assertTrue(testPlayer.get(0).isHuman());
		assertFalse(testPlayer.get(1).isHuman());
		
	}
	
	@Test
	public void deckOfCards() {
		ArrayList<Card> testDeck = board.getDeck();
		board.resetDeck();
		//check that the size is 21, and that the different card types are loaded
		assertEquals(21, testDeck.size());
		assertEquals(CardType.ROOM, testDeck.get(0).getCardType());
		assertEquals(CardType.WEAPON, testDeck.get(9).getCardType());
		assertEquals(CardType.PLAYER, testDeck.get(20).getCardType());
		assertEquals("Jupiter 02", testDeck.get(0).getName());
		assertEquals("Plasma Gun", testDeck.get(9).getName());
		assertEquals("Judy Robinson", testDeck.get(20).getName());
		
	}
	
	@Test
	public void solutionTest() {
		int rngCheck = 0;
		//tests to make sure that solution contains the right types of cards, and that it is random
		for(int i = 0; i < 600; i++) {
			board.generateSolution();
			ArrayList<Card> testSolution = board.getSolution();
			assertEquals(CardType.PLAYER, testSolution.get(0).getCardType());
			assertEquals(CardType.ROOM, testSolution.get(1).getCardType());
			assertEquals(CardType.WEAPON, testSolution.get(2).getCardType());
			if(testSolution.get(0).getName().equals("The Robot")) {
				rngCheck++;
			}
		}
		//check to see if its kinda random idk
		assertTrue(rngCheck >= 40);
	}
	
	@Test
	public void dealTest() {
		ArrayList<Card> testSolution = board.getSolution();
		ArrayList<Player> testPlayers = board.getPlayers();
		Set<Card> testDuplicates = new HashSet<Card>();
		assertEquals(3, testPlayers.get(0).getHand().size());
		for(Player player: testPlayers) {
			for(Card playerCard: player.getHand()) {
				for(Card solutionCard: testSolution) {
					//check that the players card is not in the solution
					assertNotEquals(solutionCard, playerCard);	
				}
				//check that player card is not a duplicate
				assertFalse(testDuplicates.contains(playerCard));
				//add current card to duplicate set
				testDuplicates.add(playerCard);
			}
		}
	}
	  
	
	
	
	
}
