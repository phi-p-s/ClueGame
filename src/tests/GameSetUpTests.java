package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
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
		//check that the size is 21, and that the different card types are loaded
		assertEquals(21, testDeck.size());
		assertEquals(CardType.ROOM, testDeck.get(0).getCardType());
		assertEquals(CardType.WEAPON, testDeck.get(9).getCardType());
		assertEquals(CardType.PLAYER, testDeck.get(20).getCardType());
		assertEquals("Jupiter 02", testDeck.get(0).getName());
		assertEquals("Plasma Gun", testDeck.get(9).getName());
		assertEquals("Judy Robinson", testDeck.get(20).getName());
		
	}
	
	  
	
	
	
	
}
