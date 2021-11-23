package clueGame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ClueGame extends JFrame {
	private CardPanel cardPanel;
	private GameControlPanel gameControlPanel;
	private StartSplash startSplash;
	private static Board board;
	//Constructor for the main frame
	private static ClueGame theInstance = new ClueGame();
	private ClueGame() {
		super();
	}
	public void initialize(Player startPlayer) {
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
	
	public static ClueGame getInstance() {
		return theInstance;
	}
	
	public void doTurn() {
		board.handleTurn();
		gameControlPanel.setTurn(board.getCurrentPlayer(), board.getRoll());
	}
	
	public GameControlPanel getControlPanel() {
		return gameControlPanel;
	}
	
	public CardPanel getCardPanel() {
		return cardPanel;
	}
	
	private class StartSplash extends JOptionPane {
		public StartSplash(String message) {
			super();
			JOptionPane.showMessageDialog(rootPane, message);
		}
	}
	
	private class ErrorPane extends JOptionPane{
		public ErrorPane(String error) {
			super();
			JOptionPane.showMessageDialog(rootPane, error);
		}
	}
	
	
	public void createErrorPane(String error) {
		JOptionPane errorPane = new ErrorPane(error);
	}
	
	public static void main(String[] Args) {
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("data\\ClueLayout.csv", "data\\ClueSetup.txt");		
		// Initialize will load config files 
		board.initialize();
		ClueGame clueGame = ClueGame.getInstance();
		Player currentPlayer = board.getPlayer("The Robot");
		clueGame.initialize(currentPlayer);
		clueGame.getControlPanel().setGuessResult("----------------- No suggestion has been made ------------------");
		clueGame.getControlPanel().setGuess("------------------ No suggestion has been made ------------------");
		//loop while game has not ended
		board.setCurrentPlayer(currentPlayer);
		clueGame.doTurn();
	}

}
