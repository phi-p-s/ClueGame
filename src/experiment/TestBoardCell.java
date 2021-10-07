package experiment;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class TestBoardCell {

	//instance variables and constructor
	private int row;
	private int column;
	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}

	private boolean isRoom;
	private boolean isOccupied;
	Set<TestBoardCell> adjList;
	
	public TestBoardCell(int row, int column) {
		super();
		this.adjList = new HashSet<TestBoardCell>();
		this.row = row;
		this.column = column;
	}
	
	//method stubs
	public void addAdjacency(TestBoardCell cell) {
		adjList.add(cell);
	}
	
	public Set<TestBoardCell> getAdjList() {
		return adjList;
	}
	
	
	public void setIsRoom(boolean isRoom) {
		this.isRoom = isRoom;
	}
	
	public void setIsOccupied(boolean isOccupied) {
		this.isOccupied = isOccupied;
	}
	
	public boolean getIsOccupied() {
		return this.isOccupied;
	}
	
	public boolean getIsRoom() {
		return this.isRoom;
	}
	
}
