package clueGame;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class GameControlPanel extends JPanel {
	private String guess;
	private String guessResult;
	private Player currentPlayer;
	private int roll;
	/**
	 * Constructor for the panel, it does 90% of the work
	 */
	public GameControlPanel()  {
		ButtonPanel buttons = new ButtonPanel();
		GuessPanel guess = new GuessPanel("Ooga booga", "Guess");
		GuessPanel result = new GuessPanel("Booga ooga","Result");
		TurnPanel turn = new TurnPanel("The Robot");
		setLayout(new GridLayout(2,2));
		add(turn, BorderLayout.CENTER);
		add(buttons, BorderLayout.CENTER);
		add(guess, BorderLayout.CENTER);
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
		public GuessPanel(String text, String name) {
			JLabel guessLabel = new JLabel(text);
			setBorder(new TitledBorder(new EtchedBorder(), name));
			add(guessLabel, BorderLayout.CENTER);
		}
	}
	
	private class TurnPanel extends JPanel{
		public TurnPanel(String player) {
			JLabel turn = new JLabel(player);
			setBorder(new TitledBorder(new EtchedBorder(), "Whose Turn?"));
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
		
		//Button stuff
		
		panel.setGuess( "I have no guess!");
		panel.setGuessResult( "So you have nothing?");
	}
	
	private static class AccusationListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			System.out.println("hi");
		}
	}
	
	private static class NextListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			System.out.println("easibhasegfr");
		}
	}
	
	public void setGuess(String guess) {
		
	}
	
	public void setGuessResult(String guessResult) {
		
	}
	
	public void setTurn(ComputerPlayer player, int roll) {
		
	}

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
