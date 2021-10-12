package clueGame;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class BoardCell {

	//instance variables and constructor
	//row and column
	private int row;
	private int column;
	
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
	public void setLabelCenterSecret(Character letter) {
		if(letter == '#') {
			this.isLabel = true;
		}
		else if(letter == '*') {
			this.isCenter = true;
		}
		else {
			this.secretPassage = letter;
			this.isSecretPassage = true;
		}
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
	

	
}
