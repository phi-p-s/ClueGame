package clueGame;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class Board {
	
	private BoardCell[][] grid;
	private Set<BoardCell> targets;
	private Set<BoardCell> visited;
	private String layoutConfigFile;
	private String setupConfigFile;
	private Map<Character, Room> roomMap;
	final int columns;
	final int rows;
	//constructor
	
	private static Board theInstance = new Board();
	
	private Board() {
		//add TestBoardCell to each spot in the grid
		this.rows = 0;
		this.columns = 0;
		grid = new BoardCell[rows][columns];
		visited = new HashSet<BoardCell>();
		targets = new HashSet<BoardCell>();
		for(int r = 0; r < rows; r++) {
			for(int c = 0; c < columns; c++) {
				grid[r][c] = new BoardCell(r, c);
			}
		}
		
		//create adjacency list for each cell
		for(int r = 0; r < rows; r++) {
			for(int c = 0; c < columns; c++) {
				if(r + 1 < rows) grid[r][c].addAdjacency(grid[r+1][c]);
				if(r - 1 >= 0) grid[r][c].addAdjacency(grid[r-1][c]);
				if(c + 1 < columns) grid[r][c].addAdjacency(grid[r][c+1]);
				if(c - 1 >= 0) grid[r][c].addAdjacency(grid[r][c-1]);
			}
		}
		
	}
	
	public void calcTargets(BoardCell startCell, int pathlength) {
		visited.add(startCell);
		for(BoardCell cell: startCell.getAdjList()) {
			if(!visited.contains(cell)) {
				visited.add(cell);
				if(((pathlength == 1) && !cell.isOccupied()) || cell.isRoom()) {
					targets.add(cell);
				}
				else if(!cell.isOccupied()){
					calcTargets(cell, pathlength-1);
				}
					visited.remove(cell);
			}
		}
		
	}
	
	
	public void initialize() {
		
	}
	
	public void loadSetupConfig() {
		
	}
	
	public void loadLayoutConfig() {
		
	}
	
	
	
	//GETTERS AND SETTERS
	//CONFIG
	public void setConfigFiles(String layoutFile, String setupFile) {
		this.layoutConfigFile = layoutFile;
		this.setupConfigFile = setupFile;
	}
	//CELLS & ROOMS
	public Room getRoom(Character letter) {
		return null;
	}
	public Room getRoom(BoardCell cell) {
		return null;
	}
	public BoardCell getCell(int row, int col) {
		return grid[row][col];
	}
	//COLUMNS AND ROWS
	public int getRows() {
		return rows;
	}
	public int getColumns() {
		return columns;
	}
	//OTHER
	public Set<BoardCell> getTargets(){
		return targets;
	}
	public static Board getInstance() {
		return theInstance;
	}
	
	
	/*
	public static void main(String[] args) {
		Board board = new Board();
		board.getCell(3, 0).setIsOccupied(true);
		board.getCell(1, 2).setIsRoom(true);
		BoardCell cell = board.getCell(3,2);
		board.calcTargets(cell, 4);
		for(BoardCell cellCheck: board.getTargets()) {
			System.out.println(cellCheck.getRow() + " " + cellCheck.getColumn());
		}
	}*/
}
