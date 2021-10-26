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
	private Set<BoardCell> visited;
	private String layoutConfigFile;
	private String setupConfigFile;
	private Map<Character, Room> roomMap;
	private Set<BoardCell> centers;
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



	public void initialize() {
		//add TestBoardCell to each spot in the grid

		visited = new HashSet<BoardCell>();
		targets = new HashSet<BoardCell>();
		centers = new HashSet<BoardCell>();
		roomMap = new HashMap<Character, Room>();
		
		try {
		loadSetupConfig();
		loadLayoutConfig();
		}
		catch(Exception e) {
			System.out.println(e);
		}
		
		grid = new BoardCell[rows][columns];
		layoutGrid = new String[rows][columns];
		//creates a grid of cells, size r x c
		createGrid();
		createDoorLists();
		calcAdjacencies();
	}
	public void createGrid() {
		for(int r = 0; r < rows; r++) {
			for(int c = 0; c < columns; c++) {
				//create cell at current location
				grid[r][c] = new BoardCell(r, c);
				BoardCell currentCell = grid[r][c];
				String currentLayout = layoutGrid[r][c];
				//set letter of the new cell to be the first letter given by the layout file
				currentCell.setLetter(currentLayout.charAt(0));
				//initialize Door Direction to NONE
				currentCell.setDoorDirection();
				//Sets isRoom if not a walkway or a wall
				if(currentLayout.charAt(0) != 'W' && currentLayout.charAt(0) != 'X') {
					currentCell.setIsRoom(true);
				}
				//Checks second character of walkway tiles to get doorDirection
				if((layoutGrid[r][c].length() == 2)){
					//get second character
					Character secondChar = currentLayout.charAt(1);
					//get room
					Room currentRoom = getRoom(currentCell);
					//checks second character and sets room accordingly, defaults to secret passage
					switch(secondChar) {
					case('v'):
						currentCell.setIsDoorway(true);
						currentCell.setDoorDirection(secondChar);
					case('<'):
						currentCell.setIsDoorway(true);
						currentCell.setDoorDirection(secondChar);
					case('>'):
						currentCell.setIsDoorway(true);
						currentCell.setDoorDirection(secondChar);
					case('^'):
						currentCell.setIsDoorway(true);
						currentCell.setDoorDirection(secondChar);
					case('#'):
						currentCell.setLabel();
						currentRoom.setLabelCell(currentCell);
					case('*'):
						currentCell.setCenter();
						currentRoom.setCenterCell(currentCell);
					default:
						currentCell.setSecretPassageCell(secondChar);
						currentRoom.setSecretPassage(secondChar);
					}//switch
				}//if length 2
			}//nested for loop
		}//for loop
	}
	public void createDoorLists() {
		for(int r = 0; r < rows; r++) {
			for(int c = 0; c < columns; c++) {
				//add cell to list of doors adjacent to a given room stored in room class
				BoardCell currentCell = grid[r][c];
				switch(currentCell.getDoorDirection()) {
				case UP:
					getRoom(grid[r-1][c]).addDoor(currentCell);
					break;
				case DOWN:
					getRoom(grid[r+1][c]).addDoor(currentCell);
					break;
				case RIGHT:
					getRoom(grid[r][c+1]).addDoor(currentCell);
					break;
				case LEFT:
					getRoom(grid[r][c-1]).addDoor(currentCell);
					break;
				case NONE:
					break;
				}
			}
		}
	}
	public void calcAdjacencies() {
		//create adjacency list for each cell
		Room temp;
		Room currentRoom;
		Room secretRoom;
		//separate loop to check rooms
		for(BoardCell currentCell: centers) {
			currentRoom = getRoom(currentCell);
			//loop through list of doorCells in the current room
			for(BoardCell doorCell: currentRoom.getAdjDoors()) {
				currentCell.addAdjacency(doorCell);
			}
			//if room is a secret passage, add the centerCell of the room its connected to
			if(currentRoom.hasSecretPassage()) {
				secretRoom = getRoom(currentRoom.getSecretPassage());
				currentCell.addAdjacency(secretRoom.getCenterCell());
			}
		}
		//loop through each cell
		for(int r = 0; r < rows; r++) {
			for(int c = 0; c < columns; c++) {
				//for cells that are not rooms
				if(!grid[r][c].isRoom() && grid[r][c].getLetter() != 'X') {
					//check that above cell exists
					if(r + 1 < rows) {
						//check if above cell is room
						if(!grid[r+1][c].isRoom() && grid[r+1][c].getLetter() != 'X') {
							grid[r][c].addAdjacency(grid[r+1][c]);
						}
						//add center of room
						else if(grid[r][c].getDoorDirection() == DoorDirection.DOWN){
							temp = getRoom(grid[r+1][c]);
							grid[r][c].addAdjacency(temp.getCenterCell());
						}
					}
					if(r - 1 >= 0) {
						if(!grid[r-1][c].isRoom() && grid[r-1][c].getLetter() != 'X') {
							grid[r][c].addAdjacency(grid[r-1][c]);
						}
						else if(grid[r][c].getDoorDirection() == DoorDirection.UP){
							temp = getRoom(grid[r-1][c]);
							grid[r][c].addAdjacency(temp.getCenterCell());
						}
					}
					if(c + 1 < columns) {
						if(!grid[r][c+1].isRoom() && grid[r][c+1].getLetter() != 'X') {
							grid[r][c].addAdjacency(grid[r][c+1]);
						}
						else if(grid[r][c].getDoorDirection() == DoorDirection.RIGHT){
							temp = getRoom(grid[r][c+1]);
							grid[r][c].addAdjacency(temp.getCenterCell());
						}
					}
					if(c - 1 >= 0) {
						if(!grid[r][c-1].isRoom() && grid[r][c-1].getLetter() != 'X') {
							grid[r][c].addAdjacency(grid[r][c-1]);
						}
						else if(grid[r][c].getDoorDirection() == DoorDirection.LEFT){
							temp = getRoom(grid[r][c-1]);
							grid[r][c].addAdjacency(temp.getCenterCell());
						}
					} //End of if(c - 1 >= 0)
				} //End of if(!grid[r][c].isRoom())
			} // End of for(column)
		} // End of for(row)
	}
	//find targets given a cell and pathlength (recursive)
	//this function calls the recursive function, in order to clear targets/visited each time a new target list is made
	public void calcTargets(BoardCell startCell, int pathlength) {
		targets.clear();
		visited.clear();
		calcTargetsRecursive(startCell, pathlength);
	}
	//recursive function that calculates targets
	public void calcTargetsRecursive(BoardCell startCell, int pathlength) {
		visited.add(startCell);
		for(BoardCell cell: startCell.getAdjList()) {
			if(!visited.contains(cell)) {
				visited.add(cell);
				if(((pathlength == 1) && !cell.isOccupied()) || cell.isRoom()) {
					targets.add(cell);
				}
				else if(!cell.isOccupied()){
					calcTargetsRecursive(cell, pathlength-1);
				}
				visited.remove(cell);
			}
		}
	}

	public void loadSetupConfig() throws Exception{
		//setup reader, throw exceptions
		setupReader = new File(setupConfigFile);
		//setup scanner, throw exceptions
		setupIn = new Scanner(setupReader);

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

	}

	public void loadLayoutConfig() throws Exception{
		//load layout file, throw exceptions
			layoutReader = new File(layoutConfigFile);
		//layout scanner, throw exceptions
			layoutIn = new Scanner(layoutReader);

			layoutIn2 = new Scanner(layoutReader);

		
		//get number of rows and columns
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
		//create map of letters using the csv
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

	}




	//GETTERS AND SETTERS
	//CONFIG
	public void setConfigFiles(String layoutFile, String setupFile) {
		this.layoutConfigFile = layoutFile;
		this.setupConfigFile = setupFile;
	}
	//ADJACENCY LISTS
	public Set<BoardCell> getAdjList(int row, int col){
		BoardCell cell = grid[row][col];
		return cell.getAdjList();
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
