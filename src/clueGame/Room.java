package clueGame;

public class Room {
	
	//instance variables
	private String name;
	BoardCell centerCell;
	BoardCell labelCell;
	
	public Room() {
		super();
	}
	public Room(String name) {
		super();
		this.name = name;
	}
	
	//GETTERS AND SETTERS
	
	//Name related getters & setters. 
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
