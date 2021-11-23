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

public class SuggestionFrame extends JFrame{
	private SuggestionPanel suggestionPanel;
	public SuggestionFrame(Room currentRoom, ArrayList<Card> unseenPeople, ArrayList<Card >unseenWeapons) {
		Board board = Board.getInstance();
		board.setIsPlayerDone(false);
		setSize(new Dimension(400, 250));
		this.setVisible(true);
		setTitle("Make a suggestion");
		suggestionPanel = new SuggestionPanel(currentRoom, unseenPeople, unseenWeapons);
		add(suggestionPanel);
	}
	public SuggestionPanel getSuggestionPanel() {
		return suggestionPanel;
	}
	
	
	private class SuggestionPanel extends JPanel{
		private JComboBox<String> person, weapon;
		private Room currentRoom;
		public SuggestionPanel(Room currentRoom, ArrayList<Card> unseenPeople, ArrayList<Card> unseenWeapons) {
			person = new JComboBox<String>();
			weapon = new JComboBox<String>();
			this.currentRoom = currentRoom;
			setLayout(new GridLayout(4, 2));
			for(Card cardPerson: unseenPeople) {
				person.addItem(cardPerson.getName());
			}
			for(Card cardWeapon: unseenWeapons) {
				weapon.addItem(cardWeapon.getName());
			}
			add(new JLabel("Person:"), BorderLayout.CENTER);
			add(person, BorderLayout.EAST);
			add(new JLabel("Weapon:"), BorderLayout.CENTER);
			add(weapon, BorderLayout.EAST);
			add(new JLabel("Current Room:"), BorderLayout.CENTER);
			add(new JLabel(currentRoom.getName()));
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
		public Room getCurrentRoom() {
			return currentRoom;
		}
	}
	
	private class SubmitListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			ClueGame clueGame = ClueGame.getInstance();
			Board board = Board.getInstance();
			JComboBox<String> comboPerson = getSuggestionPanel().getPersonBox();
			String personStr = (String) comboPerson.getSelectedItem();
			Card personCard = board.getCard(personStr);
			JComboBox<String> comboWeapon = getSuggestionPanel().getWeaponBox();
			String weaponStr = (String) comboWeapon.getSelectedItem();
			Card weaponCard = board.getCard(weaponStr);
			Room room = getSuggestionPanel().getCurrentRoom();
			String roomStr = room.getName();
			Card roomCard = board.getCard(room.getName());
			ArrayList<Card> suggestion = new ArrayList<Card>();
			suggestion.add(personCard);
			suggestion.add(weaponCard);
			suggestion.add(roomCard);
			String combinedStr = personStr + " + " + weaponStr + " + " + roomStr;
			clueGame.getControlPanel().setGuess(combinedStr);
			Card disproveCard = board.handleSuggestion(board.getCurrentPlayer(), suggestion);
			String cardName = disproveCard.getName();
			clueGame.getControlPanel().setGuessResult(cardName);
			
			board.setIsPlayerDone(true);
			setVisible(false);
		} 
	}
	
	private class CancelListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			Board board = Board.getInstance();
			board.setIsPlayerDone(true);
			setVisible(false);
			
		}	
	}
}


