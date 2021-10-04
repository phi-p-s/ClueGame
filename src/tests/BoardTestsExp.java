package tests;

import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import experiment.TestBoard;
import experiment.TestBoardCell;

public class BoardTestsExp {
	
	TestBoard board;
	
	@Before
	public void setUp() {
		//sets up the board
		board = new TestBoard();
	}
	
	@Test
	//tests adjacencies in different locations
	public void testAdjacencyTopLeft() {
		//top left corner
		TestBoardCell cell = board.getCell(0, 0);
		Set<TestBoardCell> testList = cell.getAdjList();
		Assert.assertTrue(testList.contains(board.getCell(1, 0)));
		Assert.assertTrue(testList.contains(board.getCell(0, 1)));
		Assert.assertEquals(2, testList.size());
		
	}
	
	@Test
	public void testAdjacencyBottomRight() {
		//bottom right
		TestBoardCell cell = board.getCell(3, 3);
		Set<TestBoardCell> testList = cell.getAdjList();
		Assert.assertTrue(testList.contains(board.getCell(2, 3)));
		Assert.assertTrue(testList.contains(board.getCell(3, 2)));
		Assert.assertEquals(2, testList.size());
	}
	
	@Test
	public void testAdjacencyRightEdge() {
		//right edge
		TestBoardCell cell = board.getCell(2, 3);
		Set<TestBoardCell> testList = cell.getAdjList();
		Assert.assertTrue(testList.contains(board.getCell(1, 3)));
		Assert.assertTrue(testList.contains(board.getCell(2, 2)));
		Assert.assertTrue(testList.contains(board.getCell(3, 3)));
		Assert.assertEquals(3, testList.size());
	}
	
	@Test
	public void testAdjacencyLeftEdge() {
		//left edge
		TestBoardCell cell = board.getCell(2, 0);
		Set<TestBoardCell> testList = cell.getAdjList();
		Assert.assertTrue(testList.contains(board.getCell(2, 1)));
		Assert.assertTrue(testList.contains(board.getCell(1, 0)));
		Assert.assertTrue(testList.contains(board.getCell(3, 0)));
		Assert.assertEquals(3, testList.size());
	}
	

}
