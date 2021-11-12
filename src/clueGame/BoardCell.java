package clueGame;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class BoardCell {

	//instance variables and constructor
	//row and column
	private int row;
	private int column;
	private int width;
	private int height;
	private int border;
	private int drawCol;
	private int drawRow;
	//layout related booleans
	private boolean isRoom;
	private boolean isOccupied;
	private boolean isDoorway;
	private boolean isSecretPassage;
	private boolean isLabel;
	private boolean isCenter;
	//layout related names
	private Character letter;
	private Character secretPassage;
	//door direction enum
	private DoorDirection doorDirection;
	//Adjacent tiles
	Set<BoardCell> adjList;
	public BoardCell(int row, int column) {
		super();
		this.adjList = new HashSet<BoardCell>();
		this.row = row;
		this.column = column;
	}
	
	//adds to adjacency list
	public void addAdjacency(BoardCell cell) {
		adjList.add(cell);
	}
	
	/*
	 * GETTERS AND SETTERS
	 * GETTERS AND SETTERS
	 * GETTERS AND SETTERS
	 * GETTERS AND SETTERS
	 * GETTERS AND SETTERS
	 * GETTERS AND SETTERS
	 * GETTERS AND SETTERS
	 * GETTERS AND SETTERS
	 */
	
	//GET ADJACENCY LIST
	public Set<BoardCell> getAdjList() {
		return adjList;
	}
	
	//GETTERS FOR ROOM STATUSES, IS ROOM, IS LABEL, IS CENTER
	public void setIsRoom(boolean isRoom) {
		this.isRoom = isRoom;
	}
	public boolean isRoom() {
		return this.isRoom;
	}
	public boolean isLabel() {
		return isLabel;
	}
	public boolean isRoomCenter() {
		return isCenter;
	}
	//sets multiple things, since it takes in the second character from the layout file. 
	//Possible values indicate, label, center, or secret passage location
	public void setLabel() {
		this.isLabel = true;
	}
	public void setCenter() {
		this.isCenter = true;
	}
	public void setSecretPassageCell(Character letter) {
		this.secretPassage = letter;
		this.isSecretPassage = true;
	}

	//SECRET PASSAGE
	public boolean isSecretPassage() {
		return isSecretPassage;
	}
	public Character getSecretPassage() {
		return secretPassage;
	}
	
	
	//ISOCCUPIED
	public void setOccupied(boolean isOccupied) {
		this.isOccupied = isOccupied;
	}
	public boolean isOccupied() {
		return this.isOccupied;
	}
	
	//DOORWAY GETTERS AND SETTERS, DIRECTION AND BOOLEANS
	public void setIsDoorway(boolean isDoorway) {
		this.isDoorway = isDoorway;
	}
	public boolean isDoorway() {
		return this.isDoorway;
	}
	
	//takes character and sets door direction based on which character is given
	public void setDoorDirection(char direction) {
		if(direction == 'v') {
			this.doorDirection = doorDirection.DOWN;
		}
		else if(direction == '^') {
			this.doorDirection = doorDirection.UP;
		}
		else if(direction == '<') {
			this.doorDirection = doorDirection.LEFT;
		}
		else {
			this.doorDirection = doorDirection.RIGHT;
		}
	}
	
	public void setDoorDirection() {
		this.doorDirection = doorDirection.NONE;
	}
	public DoorDirection getDoorDirection() {
		return doorDirection;
	}
	
	//ROWS & COLUMNS
	public int getRow() {
		return row;
	}
	public int getColumn() {
		return column;
	}
	
	//LETTERS IN LAYOUT FILE GIVEN TO CELL
	public void setLetter(Character letter) {
		this.letter = letter;
	}
	public Character getLetter() {
		return letter;
	}
	//sets variables for drawing
	public void getDrawSizes() {
		Board board = Board.getInstance();
		int rows = board.getNumRows();
		int columns = board.getNumColumns();
		//set the width and height according to the panel size
		width = board.getWidth()/columns;
		height = board.getHeight()/rows;
		drawCol = column*width;
		drawRow = row*height;
		border = 4;
	}
	public void drawHallWall(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		getDrawSizes();
		//If hallway draw green
		if(!isRoom && letter != 'X') {
			g.setColor(Color.ORANGE);
			g.fillRect(drawCol, drawRow, width, height);
			g2.setColor(Color.BLACK);
			g2.setStroke(new BasicStroke(border));
			g2.drawRect(drawCol, drawRow, width, height);
			switch(this.doorDirection) {
			case DOWN:
				break;
			case LEFT:
				break;
			case RIGHT:
				break;
			case UP:
				break;
			default:
				break;
			}
		}
		else if(letter == 'X') {
			g.setColor(Color.BLACK);
			g.fillRect(drawCol, drawRow, width, height);
		}
	}
	//draws rooms
	public void drawRoom(Graphics g) {
		Board board = Board.getInstance();
		getDrawSizes();
		if(isRoom) {
			g.setColor(Color.LIGHT_GRAY);
			g.fillRect(drawCol+border/2, drawRow+border/2, width, height);
			if(isLabel) {
				String roomName = board.getRoom(this).getName();
				g.setColor(Color.BLACK);
				g.setFont(new Font("Serif", Font.PLAIN, 15));
				g.drawString(roomName, drawCol, drawRow);
			}
		}
	}
	//draws doors
	public void drawDoor(Graphics g) {
		getDrawSizes();
		//door offset
		int doorAngle = 2;
		g.setColor(Color.DARK_GRAY);
		//check door direction and draw accordingly
		switch(this.doorDirection) {
		case DOWN:
			g.drawLine(drawCol, drawRow+height, drawCol+width, drawRow+height+(height/doorAngle));
			break;
		case LEFT:
			g.drawLine(drawCol, drawRow+height, drawCol-width/doorAngle, drawRow);
			break;
		case RIGHT:
			g.drawLine(drawCol+width, drawRow+height, drawCol+width+width/doorAngle, drawRow);
			break;
		case UP:
			g.drawLine(drawCol, drawRow, drawCol+width, drawRow-(height/doorAngle));
			break;
		//Default is not a door (Just in case)
		default:
			break;
		
		}
	}
	
}
