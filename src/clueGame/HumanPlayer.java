package clueGame;

public class HumanPlayer extends Player {

	public HumanPlayer(String playerName, String color, String column, String row, int playerId) {
		super(playerName, color, column, row, playerId);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public boolean isHuman(){
		return true;
	}

}
