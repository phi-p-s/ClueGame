package clueGame;

public class HumanPlayer extends Player {

	public HumanPlayer(String playerName, String color, int playerId) {
		super(playerName, color, playerId);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public boolean isHuman(){
		return true;
	}

}
