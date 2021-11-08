package clueGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class CardPanel extends JPanel {
	private JPanel peopleCards;
	private JPanel roomCards;
	private JPanel weaponCards;
	private SeenPanel seenPeopleCards;
	private SeenPanel seenRoomCards;
	private SeenPanel seenWeaponCards;
	private ArrayList<Card> people= new ArrayList<Card>();;
	private ArrayList<Card> rooms= new ArrayList<Card>();;
	private ArrayList<Card> weapons= new ArrayList<Card>();;
	private Player humanPlayer;
	//constructor
	public CardPanel(Player humanPlayer){
		this.humanPlayer = humanPlayer;
		setBorder(new TitledBorder(new EtchedBorder(), "Known Cards"));
		setLayout(new GridLayout(3, 1));
		peopleCards = new JPanel();
		roomCards = new JPanel();
		weaponCards = new JPanel();
		separateCards();
		makePeople();
		makeRooms();
		makeWeapons();
		add(peopleCards, BorderLayout.CENTER);
		add(roomCards, BorderLayout.CENTER);
		add(weaponCards, BorderLayout.CENTER);	
	}
	
	public void makePeople() {
		peopleCards.setBorder(new TitledBorder(new EtchedBorder(), "People"));
		peopleCards.setLayout(new GridLayout(2,1));
		SeenPanel handPeopleCards = new SeenPanel("In Hand:");
		seenPeopleCards = new SeenPanel("Seen:");
		peopleCards.add(handPeopleCards);
		peopleCards.add(seenPeopleCards);
		handPeopleCards.startHand(people);
	}
	
	public void makeRooms() {
		roomCards.setBorder(new TitledBorder(new EtchedBorder(), "Rooms"));
		roomCards.setLayout(new GridLayout(2,1));
		SeenPanel handRoomCards = new SeenPanel("In Hand:");
		seenRoomCards = new SeenPanel("Seen:");
		roomCards.add(handRoomCards);
		roomCards.add(seenRoomCards);
		handRoomCards.startHand(rooms);
	}
	
	public void makeWeapons() {
		weaponCards.setBorder(new TitledBorder(new EtchedBorder(), "Weapons"));	
		weaponCards.setLayout(new GridLayout(2,1));
		SeenPanel handWeaponCards = new SeenPanel("In Hand:");
		seenWeaponCards = new SeenPanel("Seen:");
		weaponCards.add(handWeaponCards);
		weaponCards.add(seenWeaponCards);
		handWeaponCards.startHand(weapons);
	}
	
	//separate card by type and send it to the correct panel
	public void addCard(Card card) {
		switch(card.getCardType()) {
		case PLAYER:
			seenPeopleCards.addCard(card);
			break;
		case ROOM:
			seenRoomCards.addCard(card);
			break;
		case WEAPON:
			seenWeaponCards.addCard(card);
			break;
		default:
			break;	
		}
	}
	
	//create array list of separate card types
	public void separateCards() {
		ArrayList<Card> hand = humanPlayer.getHand();
		for(Card card: hand) {
			switch(card.getCardType()) {
			case PLAYER:
				people.add(card);
				break;
			case ROOM:
				rooms.add(card);
				break;
			case WEAPON:
				weapons.add(card);
				break;
			default:
				break;
			}
		}
	}
	
	private class SeenPanel extends JPanel {
		private int gridSize;
		private JTextField firstCard;
		public SeenPanel(String label) {
			//start gridsize as 2
			gridSize = 2; 
			JLabel title = new JLabel(label);
			firstCard = new JTextField("None");
			setLayout(new GridLayout(gridSize, 1));
			firstCard.setEditable(false);
			add(title);
			add(firstCard);
		}
		public void addCard(Card card) {
			if(gridSize == 2) {
				firstCard.setText(card.getName());
			}
			else {
				setLayout(new GridLayout(gridSize,1));
				JTextField newCard = new JTextField(card.getName());
				newCard.setEditable(false);
				add(newCard);
			}
			gridSize++;
		}
		//create the starting hand for each type
		public void startHand(ArrayList<Card> hand) {
			for(Card card: hand) {
				if(gridSize == 2) {
					firstCard.setText(card.getName());
				}
				else {
					setLayout(new GridLayout(gridSize, 1));
					JTextField newCard = new JTextField(card.getName());
					newCard.setEditable(false);
					add(newCard);
				}
				gridSize++;
			}
		}
	}
	public static void main(String[] args) {
		Player humanPlayer =  new Player("The Robot", "Blue", 0);
		humanPlayer.addToHand(new Card(CardType.ROOM, "Jupiter 02"));
		humanPlayer.addToHand(new Card(CardType.ROOM, "Storage"));
		humanPlayer.addToHand(new Card(CardType.PLAYER, "Penny Robinson"));
		CardPanel cardPanel = new CardPanel(humanPlayer);  // create the panel
		JFrame frame = new JFrame();  // create the frame 
		frame.add(cardPanel); // put the panel in the frame
		frame.setSize(180, 750);  // size the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		frame.setVisible(true); // make it visible
		//setting things
		cardPanel.addCard(new Card(CardType.WEAPON, "Crowbar"));
		cardPanel.addCard(new Card(CardType.WEAPON, "Plasma Gun"));
		cardPanel.addCard(new Card(CardType.ROOM, "Bridge"));
		cardPanel.addCard(new Card(CardType.PLAYER, "The Robot"));
		cardPanel.addCard(new Card(CardType.ROOM, "Classroom"));
	}
	
}
