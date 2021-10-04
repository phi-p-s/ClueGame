package experiment;

import java.util.Collections;
import java.util.Set;

public class TestBoardCell {

	//instance variables and constructor
	private int row;
	private int column;
	private boolean isRoom;
	public TestBoardCell(int row, int column) {
		super();
		this.row = row;
		this.column = column;
	}
	
	//method stubs
	public void addAdjacency(TestBoardCell cell) {
		
	}
	
	public Set<TestBoardCell> getAdjList() {
		
		return Collections.emptySet();
	}
	
	public void setRoom(boolean isRoom) {
		
	}
	
	public void setOccupied(boolean isOccupied) {
		
	}
	
	public boolean getOccupied() {
		return false;
	}
	
	public boolean setIsRoom(boolean isRoom) {
		return false;
	}
	
}
