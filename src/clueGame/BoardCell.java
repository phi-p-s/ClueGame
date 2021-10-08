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
	public void setLabel(boolean isLabel) {
		this.isLabel = isLabel;
	}
	public boolean isCenter() {
		return isCenter;
	}
	public void setCenter(boolean isCenter) {
		this.isCenter = isCenter;
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
	//SECRET PASSAGE
	public boolean isSecretPassage() {
		return isSecretPassage;
	}
	public Character getSecretPassage() {
		return null;
	}
}
