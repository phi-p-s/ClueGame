package clueGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class GameControlPanel extends JPanel {
	private String guess;
	private String guessResult;
	private Player currentPlayer;
	private int roll;
	private ButtonPanel buttons;
	private GuessPanel guessPanel;
	private GuessPanel result;
	private TurnPanel turn;
	/**
	 * Constructor for the panel, it does 90% of the work
	 */
	
	public GameControlPanel()  {
		this.currentPlayer = new Player("", "White", "0", "0", 0);
		buttons = new ButtonPanel();
		guessPanel = new GuessPanel(this.guess, "Guess");
		result = new GuessPanel(this.guessResult,"Result");
		turn = new TurnPanel(currentPlayer.getPlayerName(), roll);
		setLayout(new GridLayout(2,2));
		add(turn, BorderLayout.CENTER);
		add(buttons, BorderLayout.CENTER);
		add(guessPanel, BorderLayout.CENTER);
		add(result, BorderLayout.CENTER);	
	}
	
	//private class for the button panel, sets up 2 buttons
	private class ButtonPanel extends JPanel {
		public ButtonPanel() {
			JButton accusationButton = new JButton("Make Accusation");
			JButton nextButton = new JButton("NEXT!");
			setLayout(new GridLayout(1, 2));
			add(accusationButton);
			accusationButton.addActionListener(new AccusationListener());
			add(nextButton);
			nextButton.addActionListener(new NextListener());
		}
	}
	
	//private class for the guess and guess result panels since they have the same format
	private class GuessPanel extends JPanel{
		private JTextField field;
		public GuessPanel(String text, String name) {
			field = new JTextField(text);
			setBorder(new TitledBorder(new EtchedBorder(), name));
			field.setColumns(35);
			field.setEditable(false);
			add(field, BorderLayout.CENTER);
		}
		//set the JTextField to new text
		public void setField(String text) {
			field.setText(text);
		}
	}
	
	//Panel that displays the turn info
	private class TurnPanel extends JPanel{
		private JLabel turn;
		private JTextField player;
		private JLabel rollLabel;
		private JTextField rollText;
		public TurnPanel(String playerName, int roll) {
			setLayout(new GridLayout(1, 2));
			turn = new JLabel(playerName);
			setBorder(new TitledBorder(new EtchedBorder(), "Whose Turn?"));
			player = new JTextField(playerName);
			player.setEditable(false);
			player.setFont(new Font("Serif", Font.BOLD, 14));
			player.setBackground(Color.WHITE);
			add(player, BorderLayout.CENTER);
			rollLabel = new JLabel("Roll");
			String rollString = Integer.toString(roll);
			rollText = new JTextField(rollString);
			rollText.setEditable(false);
			add(rollLabel, BorderLayout.CENTER);
			add(rollText, BorderLayout.EAST);
			
		}
		//Set the text field for current player and corresponding color
		public void setPlayer(String playerName, String color) {
			player.setText(playerName);
			switch(color) {
			case("Blue"):
				player.setBackground(Color.CYAN);
				break;
			case("Red"):
				player.setBackground(Color.RED);
				break;
			case("Green"):
				player.setBackground(Color.GREEN);
				break;
			case("Yellow"):
				player.setBackground(Color.YELLOW);
				break;
			case("Pink"):
				player.setBackground(Color.PINK);
				break;
			case("Purple"):
				player.setBackground(Color.MAGENTA);
				break;
			default:
				player.setBackground(Color.WHITE);
			}
		}
		//update the roll info
		public void setRoll(int roll) {
			String rollString = Integer.toString(roll);
			rollText.setText(rollString);
		}
	}
	/**
	 * Main to test the panel
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		GameControlPanel panel = new GameControlPanel();  // create the panel
		JFrame frame = new JFrame();  // create the frame 
		frame.add(panel); // put the panel in the frame
		frame.setSize(750, 180);  // size the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		frame.setVisible(true); // make it visible
		//setting things
		panel.setTurn(new Player("Penny Robinson","Pink", "0", "0", 4), 5);
		panel.setGuess( "I have no guess!");
		panel.setGuessResult( "So you have nothing?");
	}
	
	//for the accusation button, no real functionality yet
	private class AccusationListener implements ActionListener {
		private Board board = Board.getInstance();
		private ClueGame clueGame = ClueGame.getInstance();
		public void actionPerformed(ActionEvent e) {
			Player currentPlayer = board.getCurrentPlayer();
			if(!board.isPlayerMoved() && currentPlayer.isHuman()) {
				currentPlayer.createAccusation();
			}
			else {
				clueGame.createErrorPane("You can only make an accusation at the beginning of your turn");
			}
		}
	}
	
	//for the NEXT button
	private class NextListener implements ActionListener{
		private Board board = Board.getInstance();
		private ClueGame clueGame = ClueGame.getInstance();
		public void actionPerformed(ActionEvent e) {
			if(board.isPlayerDone()) {
				board.setIsPlayerDone(false);
				board.updatePlayer();
				clueGame.doTurn();
			}
			else {
				clueGame.createErrorPane("Player has not yet finished their turn");
			}
		}
	}
	
	//setters for different fields within the different panels
	public void setGuess(String text) {
		guessPanel.setField(text);
	}
	
	public void setGuessResult(String guessResult) {
		result.setField(guessResult);
	}
	
	public void setTurn(Player player, int roll) {
		turn.setPlayer(player.getPlayerName(), player.getColor());
		turn.setRoll(roll);
	}

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
	}

}
