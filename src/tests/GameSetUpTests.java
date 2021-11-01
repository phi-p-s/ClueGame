package tests;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Set;

import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;

import clueGame.Player;
import clueGame.Board;
import clueGame.BoardCell;

public class GameSetUpTests {
	
	private static Board board;
	
	@BeforeAll
	public static void gameSetUp() {
		board = Board.getInstance();
		board.setConfigFiles("data\\ClueLayout.csv", "data\\ClueSetup.txt");
		board.initialize();
	}
	
	@Test
	public void readInPlayer() {
		ArrayList<Player> testPlayer = board.getPlayers();
		
		assertEquals(6, testPlayer.size());
		assertEquals("The Robot", testPlayer.get(0).getPlayerName());
		assertEquals("Will Robinson", testPlayer.get(2).getPlayerName());
		assertEquals("Judy Robinson", testPlayer.get(5).getPlayerName());
		
	}
	
	
	
	
	
}
