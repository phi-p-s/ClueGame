package clueGame;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Player {
	private String playerName;
	private ArrayList<Card> hand;
	private Color colorDraw;
	private String color;
	private int playerId;
	protected Set<Card> seenCards;
	private int row;
	private int column;
	public Player(String playerName, String color, String column, String row, int playerId) {
		super();
		this.playerName = playerName;
		this.color = color;
		this.playerId = playerId;
		this.column = Integer.parseInt(column);
		this.row = Integer.parseInt(row);
		hand = new ArrayList<Card>();
		seenCards = new HashSet<Card>();
		setDrawColor();
	}
	
	public void setDrawColor() {
		//set color based on the string
		switch(color) {
		case("Blue"):
			colorDraw = Color.BLUE;
		break;
		case("Red"):
			colorDraw = Color.RED;
		break;
		case("Green"):
			colorDraw = Color.GREEN;
		break;
		case("Yellow"):
			colorDraw = Color.YELLOW;
		break;
		case("Pink"):
			colorDraw = Color.PINK;
		break;
		case("Purple"):
			colorDraw = Color.MAGENTA;
		break;
		default:
			colorDraw = Color.WHITE;
		}
	}
	public String getPlayerName() {
		return playerName;
	}
	public String getColor() {
		return color;
	}
	public boolean isHuman() {
		return false;
	}
	
	public ArrayList<Card> getHand(){
		return hand;
	}
	
	public int getPlayerId() {
		return playerId;
	}
	
	public void setRowCol(int row, int col) {
		this.row = row;
		this.column = col;
	}
	
	public int getRow(){
		return row;
	}
	public int getColumn() {
		return column;
	}
	public void addToHand(Card card) {
		hand.add(card);
	}
	public void setHand(ArrayList<Card> cards) {
		hand.clear();
		hand = cards;
	}
	public void resetHand() {
		hand.clear();
	}
	public Card disproveSuggestion(ArrayList<Card> suggestion) {
		for(Card suggestionCard: suggestion) {
			for(Card disproveCard: hand) {
				if(disproveCard.equals(suggestionCard)){
					return disproveCard;
				}
			}
		}
		return null;
	}
	public void startSeen() {
		for(Card card: hand) {
			seenCards.add(card);
		}
	}
	public void updateSeen(Card card) {
		seenCards.add(card);
	}
	//reset seen list for testing
	public void resetSeen() {
		seenCards.clear();
	}
	public ArrayList<Card> createSuggestion(Board board, Room room){
		return null;
	}
	//hard set solution for tests
	public ArrayList<Card> setSuggestion(Card player, Card room, Card weapon){
		ArrayList<Card> suggestion = new ArrayList<Card>();
		suggestion.add(player);
		suggestion.add(room);
		suggestion.add(weapon);
		return suggestion;
	}
	public BoardCell selectTargets(Set<BoardCell> targetSet) {
		return null;
	}
	public void draw(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		Board board = Board.getInstance();
		int rows = board.getNumRows();
		int columns = board.getNumColumns();
		//set the width and height according to the panel size
		int width = board.getWidth()/columns;
		int height = board.getHeight()/rows;
		int drawCol = column*width;
		int drawRow = row*height;
		int border = 4;
		g.setColor(colorDraw);
		//the "Magic Numbers" are there for centering the circle
		g.fillOval(drawCol+(2*border/3), drawRow+(border/2), width-(border), height-(border));
		g2.setStroke(new BasicStroke(border/2));
		g2.setColor(Color.DARK_GRAY);
		g.drawOval(drawCol+(2*border/3), drawRow+(border/2), width-(border), height-(border));
		
	}
}
	
