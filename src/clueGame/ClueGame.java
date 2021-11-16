package clueGame;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class ClueGame extends JFrame {
	private CardPanel cardPanel;
	private GameControlPanel gameControlPanel;
	private StartSplash startSplash;
	private static Board board;
	//Constructor for the main frame
	public ClueGame(Player startPlayer) {
		Board board = Board.getInstance();
		cardPanel = new CardPanel(startPlayer);
		gameControlPanel = new GameControlPanel();
		startSplash = new StartSplash("You are The Robot. \nCan you find the solution \nbefore the Computer Players?");
		setSize(1000,600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		setVisible(true);
		setTitle("Clue Game :)");
		add(gameControlPanel, BorderLayout.SOUTH);
		add(cardPanel, BorderLayout.EAST);
		add(board);
	}
	
	public GameControlPanel getControlPanel() {
		return gameControlPanel;
	}
	
	public CardPanel getCardPanel() {
		return cardPanel;
	}
	
	private class StartSplash extends JOptionPane {
		public StartSplash(String message) {
			JOptionPane.showMessageDialog(rootPane, message);
		}
	}
	
	public static void main(String[] Args) {
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("data\\ClueLayout.csv", "data\\ClueSetup.txt");		
		// Initialize will load config files 
		board.initialize();
		ClueGame frame = new ClueGame(board.getPlayer("The Robot"));
		Player currentPlayer = board.getPlayer("The Robot");
		//loop while game has not ended\
		board.setCurrentPlayer(currentPlayer);
		board.handleTurn();
		frame.getControlPanel().setTurn(currentPlayer, board.getRoll());
	}
}
