package Deck;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public enum Card {
		AceS('\u2660',"A", 1), TwoS('\u2660',"2", 2), ThreeS('\u2660', "3", 3), FourS('\u2660', "4", 4), 
		FiveS('\u2660',"5", 5), SixS('\u2660',"6", 6), SevenS('\u2660', "7", 7), EightS('\u2660', "8", 8),
		NineS('\u2660',"9", 9), TenS('\u2660',"10", 10), JackS('\u2660', "J", 11), QueenS('\u2660', "Q", 12),
		KingS('\u2660', "K", 13), AceC('\u2663',"A", 1), TwoC('\u2663',"2", 2), ThreeC('\u2663', "3", 3), FourC('\u2663', "4", 4), 
		FiveC('\u2663',"5", 5), SixC('\u2663',"6", 6), SevenC('\u2663', "7", 7), EightC('\u2663', "8", 8),
		NineC('\u2663',"9", 9), TenC('\u2663',"10", 10), JackC('\u2663', "J", 11), QueenC('\u2663', "Q", 12),
		KingC('\u2663', "K", 13), AceH('\u2661',"A", 1), TwoH('\u2661',"2", 2), ThreeH('\u2661', "3", 3), FourH('\u2661', "4", 4), 
		FiveH('\u2661',"5", 5), SixH('\u2661',"6", 6), SevenH('\u2661', "7", 7), EightH('\u2661', "8", 8),
		NineH('\u2661',"9", 9), TenH('\u2661',"10", 10), JackH('\u2661', "J", 11), QueenH('\u2661', "Q", 12),
		KingH('\u2661', "K", 13), AceD('\u2662',"A", 1), TwoD('\u2662',"2", 2), ThreeD('\u2662', "3", 3), FourD('\u2662', "4", 4), 
		FiveD('\u2662',"5", 5), SixD('\u2662',"6", 6), SevenD('\u2662', "7", 7), EightD('\u2662', "8", 8),
		NineD('\u2662',"9", 9), TenD('\u2662',"10", 10), JackD('\u2662', "J", 11), QueenD('\u2662', "Q", 12),
		KingD('\u2662', "K", 13), BLANK (' ', " ", 0);
		
		private final static char SPADE = '\u2660';
		private final static char CLUB = '\u2663';
		private final static char HEART = '\u2661';
		private final static char DIAMOND = '\u2662';
		private final static char NOSUIT = ' ';
	
		private int cardID;
		private char suit;
		private String value;
		private int Weight;
		private BufferedImage front;
		
		
		private Card(char S, String V, int w) {
			suit = S;
			value = V;
			Weight = w;
			cardID = Card.getID(suit, Weight);
			
			String filename = "C:\\Users\\akamins7\\Desktop\\workspace\\Black Jack\\cardPhoto\\";
			try {
				
				switch (suit) {
				case SPADE:
					filename += value + "S.png";
					break;
				case CLUB:
					filename += value + "C.png";
					break;
				case HEART:
					filename += value + "H.png";
					break;
				case DIAMOND:
					filename += value + "D.png";
					break;
				case NOSUIT:
					filename += "0.png";
					break;
				default:
					throw new IOException();	
				}
				front = ImageIO.read(new File(filename));
			} catch ( IOException e ) {
				
			}
		}
		public static int getID(char s, int w) {
			String CID = "";
			switch (s) {
			case SPADE:
				CID += "1";
				break;
			case CLUB:
				CID += "2";
				break;
			case HEART:
				CID += "3";
				break;
			case DIAMOND:
				CID += "4";
				break;
			}
			if( w < 10) {
				CID += "0" + w;
			} else {
				CID += w;
			}
			return Integer.parseInt(CID);
		}
		
		public JLabel paintBack(int cardW, int cardH) throws IOException {
			BufferedImage back = ImageIO.read(new File("C:\\Users\\akamins7\\Desktop\\workspace\\Black Jack\\cardPhoto\\gray_back.png"));
			back = resize(back, cardW, cardH);
			JLabel ret = new JLabel(new ImageIcon(back));
        	ret.setOpaque(true);
        	ret.setBorder(BorderFactory.createLineBorder(Color.black));
			return ret;
		}
		
		public static BufferedImage resize(BufferedImage img, int newW, int newH) { 
		    Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
		    BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

		    Graphics2D g2d = dimg.createGraphics();
		    g2d.drawImage(tmp, 0, 0, null);
		    g2d.dispose();

		    return dimg;
		}  
		
		public JLabel paint(int cardW, int cardH) throws IOException {
			front = resize(front, cardW, cardH);
			JLabel ret = new JLabel(new ImageIcon(front));
        	ret.setOpaque(true);
        	ret.setBorder(BorderFactory.createLineBorder(Color.black));
			return ret;
		}
		
		public static int getID(Card c) {
			return c.cardID;
		}
		public static char getSuit(Card c) {
			return c.suit;
		}
		public static String getValue(Card c) {
			return c.value;
		}
		public static int getWeight(Card c) {
			return c.Weight;
		}
		public String toString() {
			return "CardID: " + cardID + "\nsuit: " + suit + "\nValue :" + value +
					"\nWeight: " + Weight;
		}
		public int compTo(Card c) {
			if( c.Weight > this.Weight) {
				return 1;
			} else if( c.Weight < this.Weight) {
				return -1;
			}
			return 0;
		}
		public String print() {
			return this.suit + this.value;
		}
	}