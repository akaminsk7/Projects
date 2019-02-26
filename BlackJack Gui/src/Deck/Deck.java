package Deck;

public class Deck {
	private final int numCard = 52;
	private Card[] deck;
	private int count;
	
	public Deck() {
		deck = new Card[numCard];
		count = 0;
		fillDeck();
		
	}
	public void shuffle() {
		count = 0;
		int rot = (int) ((Math.random() * 100) + 75);
		int pos1, pos2;
		Card temp;
		
		for( int index = 0; index < rot; index++) {
			pos1 = (int) (Math.random() * 52);
			do {
			pos2 = (int) (Math.random() * 52);
			} while( pos2 == pos1 );
			temp = deck[pos1];
			deck[pos1] = deck[pos2];
			deck[pos2] = temp;
		}
	}
	public void fillDeck() {
		deck[0] = Card.AceS;
		deck[1] = Card.TwoS;
		deck[2] = Card.ThreeS;
		deck[3] = Card.FourS;
		deck[4] = Card.FiveS;
		deck[5] = Card.SixS;
		deck[6] = Card.SevenS;
		deck[7] = Card.EightS;
		deck[8] = Card.NineS;
		deck[9] = Card.TenS;
		deck[10] = Card.JackS;
		deck[11] = Card.QueenS;
		deck[12] = Card.KingS;
		deck[13] = Card.AceC;
		deck[14] = Card.TwoC;
		deck[15] = Card.ThreeC;
		deck[16] = Card.FourC;
		deck[17] = Card.FiveC;
		deck[18] = Card.SixC;
		deck[19] = Card.SevenC;
		deck[20] = Card.EightC;
		deck[21] = Card.NineC;
		deck[22] = Card.TenC;
		deck[23] = Card.JackC;
		deck[24] = Card.QueenC;
		deck[25] = Card.KingC;
		deck[26] = Card.AceH;
		deck[27] = Card.TwoH;
		deck[28] = Card.ThreeH;
		deck[29] = Card.FourH;
		deck[30] = Card.FiveH;
		deck[31] = Card.SixH;
		deck[32] = Card.SevenH;
		deck[33] = Card.EightH;
		deck[34] = Card.NineH;
		deck[35] = Card.TenH;
		deck[36] = Card.JackH;
		deck[37] = Card.QueenH;
		deck[38] = Card.KingH;
		deck[39] = Card.AceD;
		deck[40] = Card.TwoD;
		deck[41] = Card.ThreeD;
		deck[42] = Card.FourD;
		deck[43] = Card.FiveD;
		deck[44] = Card.SixD;
		deck[45] = Card.SevenD;
		deck[46] = Card.EightD;
		deck[47] = Card.NineD;
		deck[48] = Card.TenD;
		deck[49] = Card.JackD;
		deck[50] = Card.QueenD;
		deck[51] = Card.KingD;
	}
	public boolean cardsLeft() {
		return count < numCard;
	}
	public Card deal() {
		if(cardsLeft()) {
			count++;
			return deck[count-1];
		}
		return null;
	}
	public String print() {
		String ret = "";
		for( int index = 1; index <= numCard; index++) {
			if(index % 13 == 0)
			{
				ret += deck[index-1].print() + "\n";
			} else {
				ret += deck[index-1].print() + ",";
			}
			
		}
		return ret;
	}
	public String toString() {
		String ret = "Value: " + count;
		ret += "\nCards:\n========================\n" + print();
		return ret;
	}
}
