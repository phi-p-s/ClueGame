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
	private Scanner layoutIn2;
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
		
		visited = new HashSet<BoardCell>();
		targets = new HashSet<BoardCell>();
		roomMap = new HashMap<Character, Room>();
		roomMap2 = new HashMap<BoardCell, Room>();

		loadSetupConfig();
		loadLayoutConfig();
		
		int i = 0;
		int j = 0;
		while(layoutIn.hasNextLine()) {
			String line = layoutIn.nextLine();
			String[] parts = line.split(",");
			j = parts.length;
			i++;
			
		}
		
		
		this.rows = i;
		this.columns = j;
		grid = new BoardCell[rows][columns];
		layoutGrid = new String[rows][columns];
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

		//temporary iterator to keep track of rows
		i = 0;
		while(layoutIn2.hasNextLine()) {
			String line = layoutIn2.nextLine();
			String[] value = line.split(",");
			//start at j = 1 to skip the column that lists which row it is
			for(j = 0; j < value.length; j++) {
				layoutGrid[i][j] = value[j];
			}
			i++; //increment to next row
		}

		//creates a grid of cells, size r x c
		for(int r = 0; r < rows; r++) {
			for(int c = 0; c < columns; c++) {
				//create cell at current location
				grid[r][c] = new BoardCell(r, c);
				BoardCell currentCell = grid[r][c];
				String currentLayout = layoutGrid[r][c];
				//set letter of the new cell to be the first letter given by the layout file
				currentCell.setLetter(currentLayout.charAt(0));
				if((layoutGrid[r][c].length() == 2) && (currentLayout.charAt(0) == 'W')) {
					if(currentLayout.charAt(1) == 'v' || currentLayout.charAt(1) == '>' || currentLayout.charAt(1) == '<' ||  currentLayout.charAt(1) == '^') {
						currentCell.setIsDoorway(true);
						currentCell.setDoorDirection(currentLayout.charAt(1));
					}
				}
				//checks for whether its of length 2, then sends second character to check what it means
				if(layoutGrid[r][c].length() == 2) {
					currentCell.setLabelCenterSecret(currentLayout.charAt(1));
					//check whether we set it to label/center cell, and then set it to that for the room
					if(currentCell.isLabel()) {
						getRoom(currentLayout.charAt(0)).setLabelCell(currentCell);
					}
					if(currentCell.isRoomCenter()) {
						getRoom(currentLayout.charAt(0)).setCenterCell(currentCell);
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
		//load setup file, catch exceptions
				try {
					setupReader = new File(setupConfigFile);
				}
				catch (Exception e) {
					System.out.println(e);
				}
				

				//setup scanner, catch exceptions
				try {
					setupIn = new Scanner(setupReader);
				} catch (Exception e) {
					System.out.println(e);
				}
				
	}

	public void loadLayoutConfig() {
		//load layout file, catch exceptions
		try {
			layoutReader = new File(layoutConfigFile);

		}
		catch (Exception e) {
			System.out.println(e);
		}
		//layout scanner, catch exceptions
		try {
			layoutIn = new Scanner(layoutReader);
		} catch (Exception e) {
			System.out.println(e);
		}
		try {
			layoutIn2 = new Scanner(layoutReader);
		} catch (Exception e) {
			System.out.println(e);
		}
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
	public int getNumRows() {
		return rows;
	}
	public int getNumColumns() {
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

}
