package clueGame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;



public class AccusationFrame extends JFrame {
	private AccusationPanel accusationPanel;
	public AccusationFrame(ArrayList<Card> rooms, ArrayList<Card> people, ArrayList<Card> weapons) {
		Board board = Board.getInstance();
		board.setIsPlayerDone(false);
		setSize(new Dimension(400, 250));
		this.setVisible(true);
		setTitle("Make a suggestion");
		accusationPanel = new AccusationPanel(rooms, people, weapons);
		add(accusationPanel);
	}
	public AccusationPanel getAccusationPanel() {
		return accusationPanel;
	}
	
	private class AccusationPanel extends JPanel{
		private JComboBox<String> person, weapon, room;
		public AccusationPanel(ArrayList<Card> rooms, ArrayList<Card> people, ArrayList<Card> weapons) {
			person = new JComboBox<String>();
			weapon = new JComboBox<String>();
			room = new JComboBox<String>();
			setLayout(new GridLayout(4, 2));
			for(Card cardPerson: people) {
				person.addItem(cardPerson.getName());
			}
			for(Card cardWeapon: weapons) {
				weapon.addItem(cardWeapon.getName());
			}
			for(Card cardRoom: rooms) {
				room.addItem(cardRoom.getName());
			}
			add(new JLabel("Person:"), BorderLayout.CENTER);
			add(person, BorderLayout.EAST);
			add(new JLabel("Weapon:"), BorderLayout.CENTER);
			add(weapon, BorderLayout.EAST);
			add(new JLabel("Current Room:"), BorderLayout.CENTER);
			add(room, BorderLayout.EAST);
			JButton submitButton = new JButton("Submit");
			add(submitButton);
			submitButton.addActionListener(new SubmitListener());
			JButton cancelButton = new JButton("Cancel");
			add(cancelButton);
			cancelButton.addActionListener(new CancelListener());
		}
		
		public JComboBox<String> getPersonBox(){
			return person;
		}
		public JComboBox<String> getWeaponBox(){
			return weapon;
		}
		public JComboBox<String> getRoomBox() {
			return room;
		}
	}
	private class SubmitListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			ClueGame clueGame = ClueGame.getInstance();
			Board board = Board.getInstance();
			JComboBox<String> comboPerson = getAccusationPanel().getPersonBox();
			String personStr = (String) comboPerson.getSelectedItem();
			Card personCard = board.getCard(personStr);
			JComboBox<String> comboWeapon = getAccusationPanel().getWeaponBox();
			String weaponStr = (String) comboWeapon.getSelectedItem();
			Card weaponCard = board.getCard(weaponStr);
			JComboBox<String> comboRoom = getAccusationPanel().getRoomBox();
			String roomStr = (String) comboRoom.getSelectedItem();
			Card roomCard = board.getCard(roomStr);
			board.checkSolution(personCard, roomCard, weaponCard);
			setVisible(false);
		} 
	}
	
	private class CancelListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			setVisible(false);
		}	
	}
}
