package clueGame;

public class Room {
	
	//instance variables
	private String name;
	BoardCell centerCell;
	BoardCell labelCell;
	
	public Room() {
		super();
		this.name = "-";
	}
	
	//GETTERS AND SETTERS
	public String getName() {
		return this.name;
	}
	public BoardCell getCenterCell() {
		return this.centerCell;
	}
	public BoardCell getLabelCell() {
		return this.labelCell;
	}
}
