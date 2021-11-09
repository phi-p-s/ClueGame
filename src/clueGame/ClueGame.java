package clueGame;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JFrame;

public class ClueGame extends JFrame {
	private CardPanel cardPanel;
	private GameControlPanel gameControlPanel;
	private static Board board;
	//Constructor for the main frame
	public ClueGame(Player startPlayer) {
		cardPanel = new CardPanel(startPlayer);
		gameControlPanel = new GameControlPanel();
		setSize(800,600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		setVisible(true);
		add(gameControlPanel, BorderLayout.SOUTH);
		add(cardPanel, BorderLayout.EAST);
		add(board);
	}
	
	
	
	public static void main(String[] Args) {
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("data\\ClueLayout.csv", "data\\ClueSetup.txt");		
		// Initialize will load config files 
		board.initialize();
		ClueGame frame = new ClueGame(board.getPlayer("The Robot"));
	}
}
