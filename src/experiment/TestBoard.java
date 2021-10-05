package experiment;

import java.util.Collections;
import java.util.Set;

public class TestBoard {
	
	private TestBoardCell[][] grid;
	private Set<TestBoardCell> targets;
	private Set<TestBoardCell> visited;
	
	final static int COLUMNS = 4;
	final static int ROWS = 4;
	//constructor
	
	public TestBoard() {
		//add TestBoardCell to each spot in the grid
		grid = new TestBoardCell[ROWS][COLUMNS];
		for(int r = 0; r < ROWS; r++) {
			for(int c = 0; c < COLUMNS; c++) {
				grid[r][c] = new TestBoardCell(r, c);
			}
		}
		
		//create adjacency list for each cell
		for(int r = 0; r < ROWS; r++) {
			for(int c = 0; c < COLUMNS; c++) {
				if(r + 1 < ROWS) grid[r][c].addAdjacency(grid[r+1][c]);
				if(r - 1 >= 0) grid[r][c].addAdjacency(grid[r-1][c]);
				if(c + 1 < COLUMNS) grid[r][c].addAdjacency(grid[r][c+1]);
				if(c - 1 >= 0) grid[r][c].addAdjacency(grid[r][c-1]);
			}
		}
		
	}
	
	public void calcTargets(TestBoardCell startCell, int pathlength) {
		
	}
	
	public Set<TestBoardCell> getTargets(){
		return Collections.emptySet();
	}
	
	public TestBoardCell getCell(int row, int col) {
		return grid[row][col];
	}
	
}
