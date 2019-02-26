import java.util.ArrayList;
import Deck.Card;

public class Player {
	private static int comp = 1;
	private String name;
	private double handWeight;
	private ArrayList<Card> hand;
	private int chips;
	private int bet;
	private boolean playing;
	private boolean cpu;
	
	public Player() {
		name = "CPU" + comp++;
		handWeight = 0;
		cpu = true;
		hand = new ArrayList<Card>();
		chips = 200;
		bet = 0;
		playing = true;
	}
	public Player(String N) {
		name = N;
		handWeight = 0;
		hand = new ArrayList<Card>();
		chips = 200;
		bet = 0;
		playing = true;
		cpu = false;
	}
	public Player(String N, boolean d) {
		name = N;
		handWeight = 0;
		hand = new ArrayList<Card>();
		chips = 200;
		bet = 0;
		playing = true;
		cpu = d;
	}
	
	public boolean bet (int num) {
		if( num <= chips && num > 0 ) {
			bet = num;
			chips -= bet;
			return true;
		}
		return false;
	}
	public void importHand (Card[] c) {
		hand = new ArrayList<Card>();
		for( int index = 0; index < c.length; index++) {
			this.addCard(c[index]);
		}
	}
	public void hWght (double x) {
		handWeight = x;
	}
	public double getHW() {
		return handWeight;
	}
	public int getBet() {
		return bet;
	}
	public String name() {
		return name;
	}
	public int getChips () {
		return chips;
	}
	public void lose() {
		bet = 0;
	}
	public void win(int x) {
		bet = 0;
		chips += x;
	}
	public void setActive( boolean a) {
		playing = a;
	}
	public void addCard(Card c) {
		hand.add(c);
	}
	public String toString() {
		return "Name: " + name + "\nChips: " + chips + "\nCurrent Bet: " + bet + 
				"\nPlaying: " + playing + "\nHand: " + print();
	}
	public String print() {
		String ret = "";
		for( int index = 0; index < hand.size(); index++) {
			ret += hand.get(index).print() + " ";
		}
		return ret;
	}
	public boolean isCPU() {
		return cpu;
	}
	public Card[] getHand() {
		Card[] card = new Card[hand.size()];
		for( int x = 0; x < hand.size(); x++) {
			card[x] = hand.get(x);
		}
		return card;
	}
	public void resetHand() {
		handWeight = 0;
		hand = new ArrayList<Card>();
	}
	public boolean isPlaying() {
		return chips > 0 && playing;
	}
}
