package clueGame;

import java.util.HashSet;
import java.util.Set;

public class Room {
	
	//instance variables
	private String name;
	BoardCell centerCell;
	BoardCell labelCell;
	private Character secretPassage;
	private boolean hasSecretPassage;
	private Set<BoardCell> adjDoors;
	
	public Room() {
		super();
	}
	
	public Room(String name) {
		super();
		adjDoors = new HashSet<BoardCell>();
		this.name = name;
	}

	
	//GETTERS AND SETTERS
	//Name related getters & setters. 
	public void addDoor(BoardCell cell) {
		adjDoors.add(cell);
	}
	public void setSecretPassage(Character letter) {
		hasSecretPassage = true;
		secretPassage = letter;
	}
	public Character getSecretPassage() {
		return secretPassage;
	}
	public boolean hasSecretPassage() {
		return hasSecretPassage;
	}
	public Set<BoardCell> getAdjDoors() {
		return adjDoors;
	}
	public String getName() {
		return this.name;
	}
	public BoardCell getCenterCell() {
		return this.centerCell;
	}
	public BoardCell getLabelCell() {
		return this.labelCell;
	}
	public void setLabelCell(BoardCell cell) {
		this.labelCell = cell;
	}
	public void setCenterCell(BoardCell cell) {
		this.centerCell = cell;
	}
}
