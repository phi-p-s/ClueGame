package clueGame;

import javax.swing.JFrame;

public class CardPanel {

	//
	public CardPanel(){
		
	}
	public static void main(String[] args) {
		//CardPanel cardPanel = new CardPanel();  // create the panel
		JFrame frame = new JFrame();  // create the frame 
		//frame.add(cardPanel); // put the panel in the frame
		frame.setSize(180, 750);  // size the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		frame.setVisible(true); // make it visible
		//setting things
	}
}
