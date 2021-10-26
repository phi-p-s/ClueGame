package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import clueGame.Board;
import clueGame.BoardCell;

@TestMethodOrder(OrderAnnotation.class)
public class BoardAdjTargetTest {
	// We make the Board static because we can load it one time and 
	// then do all the tests. 
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

	// Ensure that player does not move around within room
	// These cells are PURPLE on the planning spreadsheet
	@Test
	@Order(1)
	public void testAdjacenciesRooms()
	{
		// we want to test a couple of different rooms.
		// Check classroom, has secret entrance, and 2 doors
		Set<BoardCell> testList = board.getAdjList(2, 2);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCell(5, 3)));
		assertTrue(testList.contains(board.getCell(2, 6)));
		assertTrue(testList.contains(board.getCell(16, 18)));
		
		// test cafeteria, 6 doors
		testList = board.getAdjList(9, 14);
		assertEquals(6, testList.size());
		assertTrue(testList.contains(board.getCell(9, 7)));
		assertTrue(testList.contains(board.getCell(6, 13)));
		assertTrue(testList.contains(board.getCell(6, 15)));
		assertTrue(testList.contains(board.getCell(9, 21)));
		assertTrue(testList.contains(board.getCell(12, 13)));
		assertTrue(testList.contains(board.getCell(12, 15)));
		
		// one more room, Jupiter 02, 2 doors
		testList = board.getAdjList(16, 26);
		assertEquals(2, testList.size());
		assertTrue(testList.contains(board.getCell(13, 26)));
		assertTrue(testList.contains(board.getCell(16, 22)));
	}

	
	// Ensure door locations include their rooms and also additional walkways
	// These cells are PURPLE on the planning spreadsheet
	@Test
	@Order(2)
	public void testAdjacencyDoor()
	{
		//test door beneath Classroom
		Set<BoardCell> testList = board.getAdjList(5, 3);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCell(2, 2)));
		assertTrue(testList.contains(board.getCell(5, 2)));
		assertTrue(testList.contains(board.getCell(5, 4)));
		assertTrue(testList.contains(board.getCell(6, 3)));

		//test door left of Bridge
		testList = board.getAdjList(2, 22);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCell(2, 21)));
		assertTrue(testList.contains(board.getCell(1, 22)));
		assertTrue(testList.contains(board.getCell(3, 22)));
		assertTrue(testList.contains(board.getCell(2, 26)));
		
		//test door right of engine room
		testList = board.getAdjList(16, 6);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCell(16, 7)));
		assertTrue(testList.contains(board.getCell(15, 6)));
		assertTrue(testList.contains(board.getCell(17, 6)));
		assertTrue(testList.contains(board.getCell(16, 2)));
	}
	
	// Test a variety of walkway scenarios
	// These tests are WHITE on the planning spreadsheet
	@Test
	@Order(3)
	public void testAdjacencyWalkways()
	{
		// Test on bottom edge of board, just one walkway piece
		Set<BoardCell> testList = board.getAdjList(18, 21);
		assertEquals(2, testList.size());
		assertTrue(testList.contains(board.getCell(17, 21)));
		assertTrue(testList.contains(board.getCell(18, 22)));
		
		// Test near a door but not adjacent
		testList = board.getAdjList(13, 9);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCell(12, 9)));
		assertTrue(testList.contains(board.getCell(13, 8)));
		assertTrue(testList.contains(board.getCell(13, 10)));

		// Test adjacent to walkways
		testList = board.getAdjList(9, 4);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCell(9, 5)));
		assertTrue(testList.contains(board.getCell(9, 3)));
		assertTrue(testList.contains(board.getCell(10, 4)));
		assertTrue(testList.contains(board.getCell(8, 4)));

		// Test next to closet
		testList = board.getAdjList(6,28);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCell(5, 28)));
		assertTrue(testList.contains(board.getCell(6, 27)));
		assertTrue(testList.contains(board.getCell(6, 29)));
	
	}
	
	
	// Tests out of room center, 1, 3 and 4
	// These are BROWN on the planning spreadsheet
	@Test
	@Order(4)
	public void testTargetsInCafeteria() {
		// test a roll of 1 at a door next to the Cafeteria
		board.calcTargets(board.getCell(6, 13), 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCell(9, 14)));
		assertTrue(targets.contains(board.getCell(6, 14)));
		assertTrue(targets.contains(board.getCell(6, 12)));
		assertTrue(targets.contains(board.getCell(5, 13)));	
		
		// test a roll of 3 
		board.calcTargets(board.getCell(6, 13), 3);
		targets= board.getTargets();
		assertEquals(10, targets.size());
		assertTrue(targets.contains(board.getCell(6, 10)));
		assertTrue(targets.contains(board.getCell(5, 11)));	
		assertTrue(targets.contains(board.getCell(9, 14)));
		assertTrue(targets.contains(board.getCell(6, 12)));	
	}
	
	@Test
	@Order(5)
	public void testTargetsInBridge() {
		// test a roll of 1
		board.calcTargets(board.getCell(5, 24), 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCell(5, 25)));
		assertTrue(targets.contains(board.getCell(5, 23)));
		assertTrue(targets.contains(board.getCell(6, 24)));
		
		// test a roll of 3
		board.calcTargets(board.getCell(5, 24), 3);
		targets= board.getTargets();
		assertEquals(10, targets.size());
		assertTrue(targets.contains(board.getCell(2, 26)));
		assertTrue(targets.contains(board.getCell(5, 23)));	
		assertTrue(targets.contains(board.getCell(5, 25)));
		assertTrue(targets.contains(board.getCell(4, 22)));	
		
		// test a roll of 4
		board.calcTargets(board.getCell(5, 24), 4);
		targets= board.getTargets();
		assertEquals(13, targets.size());
		assertTrue(targets.contains(board.getCell(2, 26)));
		assertTrue(targets.contains(board.getCell(5, 28)));	
		assertTrue(targets.contains(board.getCell(5, 20)));
		assertTrue(targets.contains(board.getCell(4, 21)));	
	}

	// Tests out of room center, 1, 3 and 4
	// These are BROWN on the planning spreadsheet
	@Test
	@Order(6)
	public void testTargetsAtDoor() {
		// test a roll of 1, at door
		board.calcTargets(board.getCell(13, 3), 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCell(16, 2)));
		assertTrue(targets.contains(board.getCell(13, 2)));	
		assertTrue(targets.contains(board.getCell(12, 3)));	
		
		// test a roll of 3
		board.calcTargets(board.getCell(13, 3), 3);
		targets= board.getTargets();
		assertEquals(10, targets.size());
		assertTrue(targets.contains(board.getCell(16, 2)));
		assertTrue(targets.contains(board.getCell(13, 0)));
		assertTrue(targets.contains(board.getCell(13, 6)));	
		assertTrue(targets.contains(board.getCell(10, 3)));
		assertTrue(targets.contains(board.getCell(11, 4)));	
		
	}

	@Test
	@Order(7)
	public void testTargetsInWalkway1() {
		// test a roll of 1
		board.calcTargets(board.getCell(12, 27), 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCell(12, 28)));
		assertTrue(targets.contains(board.getCell(12, 26)));	
		
		// test a roll of 3
		board.calcTargets(board.getCell(12, 27), 3);
		targets= board.getTargets();
		assertEquals(7, targets.size());
		assertTrue(targets.contains(board.getCell(16, 26)));
		assertTrue(targets.contains(board.getCell(13, 29)));
		assertTrue(targets.contains(board.getCell(12, 24)));	
		
		// test a roll of 4
		board.calcTargets(board.getCell(12, 27), 4);
		targets= board.getTargets();
		assertEquals(7, targets.size());
		assertTrue(targets.contains(board.getCell(16, 26)));
		assertTrue(targets.contains(board.getCell(12, 23)));
		assertTrue(targets.contains(board.getCell(13, 28)));	
	}

	@Test
	@Order(8)
	public void testTargetsInWalkway2() {
		// test a roll of 1
		board.calcTargets(board.getCell(7, 22), 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCell(6, 22)));
		assertTrue(targets.contains(board.getCell(7, 23)));	
		
		
		// test a roll of 4
		board.calcTargets(board.getCell(7, 22), 4);
		targets= board.getTargets();
		assertEquals(16, targets.size());
		assertTrue(targets.contains(board.getCell(8, 23)));
		assertTrue(targets.contains(board.getCell(6, 23)));
		assertTrue(targets.contains(board.getCell(6, 19)));	
	}

	@Test
	@Order(9)
	// test to make sure occupied locations do not cause problems
	public void testTargetsOccupied() {
		// test a roll of 4 blocked 2 down
		board.getCell(2, 6).setOccupied(true);
		board.calcTargets(board.getCell(0, 6), 4);
		board.getCell(15, 7).setOccupied(false);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCell(3,7)));
		assertTrue(targets.contains(board.getCell(2, 9)));
	
		// we want to make sure we can get into a room, even if flagged as occupied
		board.getCell(16, 11).setOccupied(true);
		board.getCell(15, 15).setOccupied(true);
		board.calcTargets(board.getCell(16, 15), 1);
		board.getCell(12, 20).setOccupied(false);
		board.getCell(8, 18).setOccupied(false);
		targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCell(16, 11)));	
		assertTrue(targets.contains(board.getCell(16, 16)));	
		assertTrue(targets.contains(board.getCell(17, 15)));	
		
		// check leaving a room with a blocked doorway & a secret passage
		board.getCell(5, 26).setOccupied(true);
		board.calcTargets(board.getCell(2, 26), 2);
		board.getCell(12, 15).setOccupied(false);
		targets= board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCell(2, 21)));
		assertTrue(targets.contains(board.getCell(1, 22)));	
		assertTrue(targets.contains(board.getCell(3, 22)));
		assertTrue(targets.contains(board.getCell(16, 2)));
		

	}
}
