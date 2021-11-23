package clueGame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.Random;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.swing.JPanel;

import java.util.Scanner;
import java.io.Reader;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;



public class Board extends JPanel{
	private BoardCell[][] grid;
	private Set<BoardCell> targets;
	private Set<BoardCell> visited;
	private String layoutConfigFile;
	private String setupConfigFile;
	private Map<Character, Room> roomMap;
	private Set<BoardCell> centers;
	private int columns;
	private int rows;
	private int roll;
	private boolean isPlayerDone;
	private boolean isPlayerMoved;
	private ArrayList<Player> players;
	private ArrayList<Player> activePlayers;
	private ArrayList<Card> deck;
	private ArrayList<Card> allDeck;
	private ArrayList<Card> playerCards;
	private ArrayList<Card> weaponCards;
	private ArrayList<Card> roomCards;
	private ArrayList<Card> solution;
	private Player currentPlayer;
	//File IO
	private File setupReader;
	private File layoutReader;
	private Scanner setupIn;
	private Scanner layoutIn;
	private Scanner layoutIn2;
	private String[][] layoutGrid;
	private CellListener cellListener;
	//GETTERS AND SETTERS
	//CONFIG
	public void setIsPlayerDone(boolean done) {
		isPlayerDone = done;
	}
	public boolean isPlayerDone() {
		return isPlayerDone;
	}
	public boolean isPlayerMoved() {
		return isPlayerMoved;
	}
	public void rollDie() {
		Random rng = new Random();
		roll = rng.nextInt(6) + 1;
	}
	public int getRoll() {
		return roll;
	}
	public ArrayList<Player> getPlayers() {
		return players;
	}
	public Player getPlayer(String name) {
		for(Player player: players) {
			if(player.getPlayerName().equals(name)) {
				return player;
			}
		}
		//in theory should work
		return null;
	}
	public Player getPlayer(int id) {
		for(Player player: players) {
			if(player.getPlayerId() == id) {
				return player;
			}
		}
		return null;
	}
	public Player getCurrentPlayer() {
		return currentPlayer;
	}
	public ArrayList<Card> getDeck(){
		return deck;
	}
	public ArrayList<Card> getPlayerCards(){
		return playerCards;
	}
	public ArrayList<Card> getWeaponCards(){
		return weaponCards;
	}
	public ArrayList<Card> getRoomCards(){
		return roomCards;
	}
	public Card getCard(String name) {
		for(Card card: allDeck) {
			if(card.getName().equals(name)) {
				return card;
			}
		}
		return null;
	}
	public ArrayList<Card> getSolution(){
		return solution;
	}
	public void setSolution(Card player, Card room, Card weapon) {
		solution.clear();
		solution.add(player);
		solution.add(room);
		solution.add(weapon);
	}
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
	public CellListener getCellListener() {
		return cellListener;
	}
	
	//constructor
	private static Board theInstance = new Board();
	//Private so only one gets made
	private Board() {
		super();
	}
	public void initialize() {
		visited = new HashSet<BoardCell>();
		targets = new HashSet<BoardCell>();
		centers = new HashSet<BoardCell>();
		roomMap = new HashMap<Character, Room>();
		players = new ArrayList<Player>();
		deck = new ArrayList<Card>();
		solution = new ArrayList<Card>();
		roomCards = new ArrayList<Card>();
		playerCards = new ArrayList<Card>();
		weaponCards = new ArrayList<Card>();
		allDeck = new ArrayList<Card>();
		activePlayers = new ArrayList<Player>();
		loadSetupConfig();
		loadLayoutConfig();
		grid = new BoardCell[rows][columns];
		addMouseListener(new CellListener());
		//creates a grid of cells, size r x c
		createGrid();
		createDoorLists();
		calcAdjacencies();
		generateSolution();
		dealHands();
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
						break;
					case('<'):
						currentCell.setIsDoorway(true);
						currentCell.setDoorDirection(secondChar);
						break;
					case('>'):
						currentCell.setIsDoorway(true);
						currentCell.setDoorDirection(secondChar);
						break;
					case('^'):
						currentCell.setIsDoorway(true);
						currentCell.setDoorDirection(secondChar);
						break;
					case('#'):
						currentCell.setLabel();
						currentRoom.setLabelCell(currentCell);
						break;
					case('*'):
						currentCell.setCenter();
						currentRoom.setCenterCell(currentCell);
						centers.add(currentCell);
						break;
					default:
						currentCell.setSecretPassageCell(secondChar);
						currentRoom.setSecretPassage(secondChar);
						break;
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
					//check that above cell is not out of bounds
					if(r + 1 < rows) {
						//if the cell above is not a room or a wall, add the adjacency
						if(!grid[r+1][c].isRoom() && grid[r+1][c].getLetter() != 'X') {
							grid[r][c].addAdjacency(grid[r+1][c]);
						}
						//if the current cell is a door that points down, add center of the room to the down
						else if(grid[r][c].getDoorDirection() == DoorDirection.DOWN){
							temp = getRoom(grid[r+1][c]);
							grid[r][c].addAdjacency(temp.getCenterCell());
						}
					}
					//check that below cell exists
					if(r - 1 >= 0) {
						//if the cell above is not a room or a wall, add the adjacency
						if(!grid[r-1][c].isRoom() && grid[r-1][c].getLetter() != 'X') {
							grid[r][c].addAdjacency(grid[r-1][c]);
						}
						//if the current cell is a door that points up, add center of the room to the up
						else if(grid[r][c].getDoorDirection() == DoorDirection.UP){
							temp = getRoom(grid[r-1][c]);
							grid[r][c].addAdjacency(temp.getCenterCell());
						}
					}
					//check that cell to the right is not out of bounds
					if(c + 1 < columns) {
						//if the cell above is not a room or a wall, add the adjacency
						if(!grid[r][c+1].isRoom() && grid[r][c+1].getLetter() != 'X') {
							grid[r][c].addAdjacency(grid[r][c+1]);
						}
						//if the current cell is a door that points right, add center of the room to the right
						else if(grid[r][c].getDoorDirection() == DoorDirection.RIGHT){
							temp = getRoom(grid[r][c+1]);
							grid[r][c].addAdjacency(temp.getCenterCell());
						}
					}
					//check that cell to the left is not out of bounds
					if(c - 1 >= 0) {
						//if the cell above is not a room or a wall, add the adjacency
						if(!grid[r][c-1].isRoom() && grid[r][c-1].getLetter() != 'X') {
							grid[r][c].addAdjacency(grid[r][c-1]);
						}
						//if the current cell is a door that points left, add center of the room to the left
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
	public void generateSolution() {
		Random rng = new Random();
		solution.clear();
		//generate indexes from the size of each list
		int roomInt = rng.nextInt(roomCards.size());
		int weaponInt = rng.nextInt(weaponCards.size());
		int playerInt = rng.nextInt(playerCards.size());
		//put the thing in the solution
		solution.add(playerCards.get(playerInt));
		solution.add(roomCards.get(roomInt));
		solution.add(weaponCards.get(weaponInt));
		deck.remove(playerCards.get(playerInt));
		deck.remove(roomCards.get(roomInt));
		deck.remove(weaponCards.get(weaponInt));
	}
	public boolean checkSolution(Card player, Card room, Card weapon) {
		ClueGame clueGame = ClueGame.getInstance();
		if(solution.get(0) == player && solution.get(1) == room && solution.get(2) == weapon) {
			clueGame.createEndSplash(currentPlayer.getPlayerName() + ", You are winner!\n" + "The solution was: " + solution.get(0).getName() + " in the " + solution.get(1).getName() + " with the " + solution.get(2).getName());
			clueGame.setVisible(false);
		}
		else if(currentPlayer.isHuman()){
			clueGame.createEndSplash(currentPlayer.getPlayerName() + ", that is not the correct solution.\nGAME OVER\n" + "The solution was: " + solution.get(0).getName() + " in the " + solution.get(1).getName() + " with the " + solution.get(2).getName());
			clueGame.setVisible(false);
		}
		else {
			//this should never happen
			clueGame.createEndSplash(currentPlayer.getPlayerName() + ", That is not the solution lmao");
		}
		return (solution.get(0) == player && solution.get(1) == room && solution.get(2) == weapon);
	}
	public void dealHands() {
		int j = 0;
		Card card;
		Random rng = new Random();
		int size = deck.size();
		//for each card in the deck, give the next player a card to add to their hand
		for(int i = 1; i <= size; i++) {
			int rand_int = rng.nextInt(deck.size());
			card = deck.get(rand_int);
			j = i % players.size();
			players.get(j).addToHand(card);
			deck.remove(rand_int);
		}
		//make each player add their hand to their list of seen cards
		for(Player player: players) {
			player.startSeen();
		}
	}
	public Card handleSuggestion(Player currentPlayer, ArrayList<Card> suggestion) {
		ClueGame clueGame = ClueGame.getInstance();
		int currentId = currentPlayer.getPlayerId();
		Player disprovePlayer;
		String personStr = suggestion.get(0).getName();
		String roomStr = suggestion.get(1).getName();
		String weaponStr = suggestion.get(2).getName();
		String combinedStr = personStr + " + " + roomStr + " + " + weaponStr;
		clueGame.getControlPanel().setGuess(combinedStr);
		Player suggestedPlayer = getPlayer(personStr);
		suggestedPlayer.setRowCol(currentPlayer.getRow(), currentPlayer.getColumn());
		for(int i = currentId+1; i < players.size()+currentId; i++) {
			int j = (i)% players.size();
			disprovePlayer = players.get(j);
			Card disproveCard = disprovePlayer.disproveSuggestion(suggestion);
			if(!Objects.isNull(disproveCard)) {
				currentPlayer.updateSeen(disproveCard);
				if(currentPlayer.isHuman()) {
					clueGame.getCardPanel().addCard(disproveCard);
					clueGame.getCardPanel().repaint();
					clueGame.getControlPanel().setGuessResult("Card Disproved: " + disproveCard.getName());
				}
				else {
					clueGame.getControlPanel().setGuessResult("Suggestion Disproved");
				}
				return disproveCard;
			}
		}
		clueGame.getControlPanel().setGuessResult("Suggestion was not Disproved");
		return null;
	}
	//reset deck in case it gets emptied for some reason and we need it to not be empty
	public void resetDeck() {
		deck.clear();
		for(Card room: roomCards) {
			deck.add(room);
		}
		for(Card weapon: weaponCards) {
			deck.add(weapon);
		}
		for(Card player: playerCards) {
			deck.add(player);
		}
		
	}
	//FILE IO METHODS
	public void loadSetupConfig(){
		//setup reader, throw exceptions
		try {
			setupReader = new File(setupConfigFile);
			//setup scanner, throw exceptions
			setupIn = new Scanner(setupReader);
			//Create Map for Character to Room
			boolean humanPlayer = true;
			int playerId = 0;
			//iterator to make sure that separate sets of cards still contain the same copy
			while (setupIn.hasNextLine()) {
				String line = setupIn.nextLine();
				//check if its a comment
				
				if(line.charAt(0) != '/') {
					String[] parts = line.split(", ");
					switch(parts[0]) {
					case("Room"):
						//if specified room, add to map & deck
						roomMap.put(parts[2].charAt(0), new Room(parts[1]));
						deck.add(new Card(CardType.ROOM, parts[1]));
						allDeck.add(new Card(CardType.ROOM, parts[1]));
						//add to separate set of rooms (for solution)
						roomCards.add(deck.get(deck.size()-1));
						break;
					case("Player"):
						if(humanPlayer) {
							players.add(new HumanPlayer(parts[1], parts[2], parts[3], parts[4], playerId));
							humanPlayer = false;
						}
						else {
							players.add(new ComputerPlayer(parts[1], parts[2], parts[3], parts[4], playerId));
						}
						playerId++; //next player has the next playerId
						//add to deck & separate set of players (for solution)
						deck.add(new Card(CardType.PLAYER, parts[1]));
						allDeck.add(new Card(CardType.PLAYER, parts[1]));
						playerCards.add(deck.get(deck.size()-1));
						break;
					case("Weapon"):
						//add to deck & separate set of weapons (for solution)
						deck.add(new Card(CardType.WEAPON, parts[1]));
						allDeck.add(new Card(CardType.WEAPON, parts[1]));
						weaponCards.add(deck.get(deck.size()-1));
						break;
					case("Space"):
						roomMap.put(parts[2].charAt(0), new Room(parts[1]));
					break;
					}	
				}
			}
			activePlayers = players;
		}
		catch(Exception e) {
			System.out.println(e);
		}

	}
	public void loadLayoutConfig(){
		//load layout file, throw exceptions
		try {
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
			layoutGrid = new String[rows][columns];
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
			//int size = layoutGrid[i].length;
			//for(i = 0; i < layoutGrid.length; i++) {
			//	if(layoutGrid[i].length != size) {
			//	throw new BadConfigFormatException("Layout File is not properly formatted");
			//}
			//}
		}
		catch(Exception e) {
			System.out.println(e);
		}
	}
	public void setCurrentPlayer(Player currentPlayer) {
		this.currentPlayer = currentPlayer;
	}
	public void handleTurn() {
		//roll die for current player, then calculate their targets
		isPlayerMoved = false;
		rollDie();
		BoardCell currentCell = getCell(currentPlayer.getRow(), currentPlayer.getColumn());
		calcTargets(currentCell, roll);	
		//update board with new targets
		repaint();
		if(!currentPlayer.isHuman()) {
			int remainingCards = allDeck.size() - currentPlayer.getSeenCards().size();
			if(remainingCards == 3) {
				ArrayList<Card> accusation = currentPlayer.createAccusation();
				checkSolution(accusation.get(0), accusation.get(1), accusation.get(2));
			}
			BoardCell moveCell = currentPlayer.selectTargets(targets);
			movePlayer(moveCell.getRow(), moveCell.getColumn());
		}
	}
	
	public void movePlayer(int row, int column) {
		currentPlayer.setRowCol(row, column);
		targets.clear();
		isPlayerDone = true;
		isPlayerMoved = true;
		repaint();
		if(grid[row][column].isRoom()) {
			ArrayList<Card> suggestion = currentPlayer.createSuggestion(getRoom(grid[row][column]));
			if(!currentPlayer.isHuman()) {
				handleSuggestion(currentPlayer, suggestion);
			}
		}
	}
	
	public void updatePlayer() {
		int currentId = currentPlayer.getPlayerId();
		do {
			currentId = (currentId+1)%players.size();
			currentPlayer = getPlayer(currentId);
		}while(!activePlayers.contains(currentPlayer));
		repaint();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for(int r = 0; r < rows; r++) {
			for(int c = 0; c < columns; c++) {
				if(grid[r][c].isRoom()) {
					if(targets.contains(grid[r][c])) {
						grid[r][c].drawRoom(g,true);
					}
					else {
						grid[r][c].drawRoom(g,false);
					}
				}
				else if(targets.contains(grid[r][c])){
					grid[r][c].drawHallWall(g, true);
				}
				else {
					grid[r][c].drawHallWall(g, false);
				}
			}
		}
		for(int r = 0; r < rows; r++) {
			for(int c = 0; c < columns; c++) {
				if(grid[r][c].isDoorway()) {
					grid[r][c].drawDoor(g);
				}
			}
		}
		for(Player player: players) {
			player.draw(g);
		}
		//Loop through players and get them to draw themselves
	}
	

	
	private class CellListener implements MouseListener{
		@Override
		public void mouseClicked(MouseEvent e) {
			int mouseCol = e.getX();
			int mouseRow = e.getY();
			int width = getWidth()/columns;
			int height = getHeight()/rows;
			boolean isFound = false;
			boolean humanPlayer = currentPlayer.isHuman();
			if(humanPlayer){
				//find which cell is clicked on
				if(isPlayerMoved) {
					ClueGame clueGame = ClueGame.getInstance();
					clueGame.createErrorPane("Player has already moved");
				}
				
				for(BoardCell currentCell: targets) {
					int drawCol = currentCell.getDrawCol();
					int drawRow = currentCell.getDrawRow();
					if((mouseCol > drawCol && mouseCol < (drawCol + width) && mouseRow > drawRow && mouseRow < (drawRow + height))) {
						isFound = true;
						movePlayer(currentCell.getRow(), currentCell.getColumn());
						break;
					}
				}
				if(!isFound && !isPlayerMoved) {
					ClueGame clueGame = ClueGame.getInstance();
					clueGame.createErrorPane("Please select a valid target (Cyan)");
				}
			}
		}
		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
	}
}
