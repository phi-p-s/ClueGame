package clueGame;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class BoardCell {

	//instance variables and constructor
	private int row;
	private int column;
	private boolean isRoom;
	private boolean isOccupied;
	private boolean isDoorway;
	private boolean isSecretPassage;
	private Character letter;
	private Character secretPassage;
	private DoorDirection doorDirection;
	private boolean isLabel;
	private boolean isCenter;
	Set<BoardCell> adjList;
	public BoardCell(int row, int column) {
		super();
		this.adjList = new HashSet<BoardCell>();
		this.row = row;
		this.column = column;
	}
	
	//method stubs
	public void addAdjacency(BoardCell cell) {
		adjList.add(cell);
	}
	
	
	
	//GETTERS AND SETTERS
	
	//ADJ LIST
	public Set<BoardCell> getAdjList() {
		return adjList;
	}
	//ISROOM
	public void setIsRoom(boolean isRoom) {
		this.isRoom = isRoom;
	}
	public boolean isRoom() {
		return this.isRoom;
	}
	public boolean isLabel() {
		return isLabel;
	}
	//sets multiple things, since it takes in the second character from the layout file. Possible values indicate, label, center, or secret passage location
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
	public boolean isCenter() {
		return isCenter;
	}
	
	//ISOCCUPIED
	public void setIsOccupied(boolean isOccupied) {
		this.isOccupied = isOccupied;
	}
	public boolean isOccupied() {
		return this.isOccupied;
	}
	//IS DOORWAY
	public void setIsDoorway(boolean isDoorway) {
		this.isDoorway = isDoorway;
	}
	public boolean isDoorway() {
		return this.isDoorway;
	}
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
	//NAME
	public void setLetter(Character letter) {
		this.letter = letter;
	}
	public Character getLetter() {
		return letter;
	}
	//SECRET PASSAGE
	public boolean isSecretPassage() {
		return isSecretPassage;
	}
	public Character getSecretPassage() {
		return secretPassage;
	}
	
}
