package clueGame;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Scanner;
import java.io.Reader;
import java.io.File;
import java.io.FileNotFoundException;


public class Board {

	private BoardCell[][] grid;
	private Set<BoardCell> targets;
	private Room temporaryRoom;
	private Set<BoardCell> visited;
	private String layoutConfigFile;
	private String setupConfigFile;
	private Map<Character, Room> roomMap;
	private Map<BoardCell, Room> roomMap2;
	private int columns;
	private int rows;
	//File IO
	private File setupReader;
	private File layoutReader;
	private Scanner setupIn;
	private Scanner layoutIn;
	private String[][] layoutGrid;
	//constructor

	private static Board theInstance = new Board();

	//Private so only one gets made
	private Board() {
		super();
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
		//add TestBoardCell to each spot in the grid
		this.rows = 18;
		this.columns = 29;
		grid = new BoardCell[rows+1][columns+1];
		layoutGrid = new String[rows+1][columns+1];
		visited = new HashSet<BoardCell>();
		targets = new HashSet<BoardCell>();
		roomMap = new HashMap<Character, Room>();
		roomMap2 = new HashMap<BoardCell, Room>();


		//load setup and layout files, catch file not found errors

		try {
			setupReader = new File(setupConfigFile);

		}
		catch (Exception e) {
			System.out.println(e);
		}
		try {
			layoutReader = new File(layoutConfigFile);

		}
		catch (Exception e) {
			System.out.println(e);
		}


		try {
			setupIn = new Scanner(setupReader);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			layoutIn = new Scanner(layoutReader);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		//Create Map for Character to Room
		while (setupIn.hasNextLine()) {
			String line = setupIn.nextLine();
			//check if its a comment
			if(line.charAt(0) != '/') {
				String[] parts = line.split(", ");
				//if specified room, add to map
				roomMap.put(parts[2].charAt(0), new Room(parts[1]));							
			}
		}

		//temporary iterator
		int i = 0;
		String temp = layoutIn.nextLine(); //skip first row
		while(layoutIn.hasNextLine()) {
			String line = layoutIn.nextLine();
			String[] value = line.split(",");
			for(int j = 1; j < value.length; j++) {
				layoutGrid[i][j-1] = value[j];
			}
			i++;
		}

		//creates a grid of cells, size r x c
		for(int r = 0; r < rows+1; r++) {
			for(int c = 0; c < columns+1; c++) {
				grid[r][c] = new BoardCell(r, c);
				grid[r][c].setLetter(layoutGrid[r][c].charAt(0));
				if((layoutGrid[r][c].length() == 2) && (layoutGrid[r][c].charAt(0) == 'W')) {
					if(layoutGrid[r][c].charAt(1) == 'v' || layoutGrid[r][c].charAt(1) == '>' || layoutGrid[r][c].charAt(1) == '<' ||  layoutGrid[r][c].charAt(1) == '^') {
						grid[r][c].setIsDoorway(true);
						grid[r][c].setDoorDirection(layoutGrid[r][c].charAt(1));
					}
				}
				//checks for whether its of length 2, then sends second character to check what it means
				if(layoutGrid[r][c].length() == 2) {
					grid[r][c].setLabelCenterSecret(layoutGrid[r][c].charAt(1));
					//check whether we set it to label/center cell, and then set it to that for the room
					if(grid[r][c].isLabel()) {
						getRoom(layoutGrid[r][c].charAt(0)).setLabelCell(grid[r][c]);
					}
					if(grid[r][c].isCenter()) {
						getRoom(layoutGrid[r][c].charAt(0)).setCenterCell(grid[r][c]);
					}
					
				}
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
		return roomMap.get(letter);
	}
	public Room getRoom(BoardCell cell) {
		return roomMap.get(cell.getLetter());
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
	public String[][] getLayoutGrid(){
		return layoutGrid;
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
