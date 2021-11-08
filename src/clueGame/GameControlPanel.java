package clueGame;

import java.awt.BorderLayout;
import java.awt.Color;
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
		this.currentPlayer = new Player("", "White", 0);
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
	
	private class GuessPanel extends JPanel{
		private JTextField field;
		public GuessPanel(String text, String name) {
			field = new JTextField(text);
			setBorder(new TitledBorder(new EtchedBorder(), name));
			field.setEditable(false);
			add(field, BorderLayout.CENTER);
		}
		public void setField(String text) {
			field.setText(text);
		}
	}
	
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
			player.setBackground(Color.WHITE);
			add(player, BorderLayout.CENTER);
			rollLabel = new JLabel("Roll");
			String rollString = Integer.toString(roll);
			rollText = new JTextField(rollString);
			rollText.setEditable(false);
			add(rollLabel, BorderLayout.CENTER);
			add(rollText, BorderLayout.EAST);
			
		}
		public void setPlayer(String playerName, String color) {
			player.setText(playerName);
			switch(color) {
			case("Blue"):
				player.setBackground(Color.BLUE);
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
		panel.setTurn(new Player("Penny Robinson","Pink", 4), 5);
		panel.setGuess( "I have no guess!");
		panel.setGuessResult( "So you have nothing?");
	}
	
	private class AccusationListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			System.out.println("hi");
		}
	}
	
	private class NextListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			System.out.println("easibhasegfr");
		}
	}
	
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
