import java.awt.Color;
import java.awt.Dimension;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Deck.Card;
import Deck.Deck;

public class BlackJackGUI extends JFrame {
	private static int WIDTH = 1400;
    private static int HEIGHT = 1000;
	private static JFrame Frame;
	private static JPanel Table;
	private static JPanel UI;
	private static JLabel pName;
	private static JButton Stay;
	private static JButton Hit;
	private static JButton DoubleDown;
	private static JButton Split;
	private static Player[] players;
	private static Player dealer;
	private static boolean dealerFlip;
	private static Deck deck;
	private static int cardH = HEIGHT / 6;
	private static int cardW = HEIGHT / 10;
	/*
	 * 0: No Answer
	 * 1: Hit
	 * 2: Stay
	 * 3: Double Down
	 * 4: Split
	 */
	private static int hitStay;
	private static JTextField betInput;
	private static JButton betButton;
	/*
	 * 0: no bet found
	 */
	private static int currBet;
	
	public BlackJackGUI(Player[] p) throws IOException {
		/*========================
		 * Initializes variables
		  ========================*/
		players = p;
		dealerFlip = false;
		
		/*========================
		 * Creates the JFrame
		  ========================*/
		Frame = new JFrame("Table");
        Frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Frame.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        Frame.getContentPane().setLayout(null);
        Frame.getContentPane().setBackground(Color.green);
        Frame.setVisible(true);
        Frame.setResizable(false); // User cannot change the size of the window
        
        /*========================
		 * Creates the User Interface Panel
		  ========================*/
        UI = new JPanel();
        UI.setBackground(Color.LIGHT_GRAY);
        int wUI = WIDTH / 4;
        int hUI = HEIGHT / 4;
        UI.setBounds(WIDTH - wUI,HEIGHT - hUI,wUI,hUI);
        UI.setLayout(null);
        
        /*========================
		 * Creates the Green Background
		  ========================*/
        Table = new JPanel();
        Table.setBackground(Color.GREEN);
        Table.setBounds(0,0,WIDTH,HEIGHT);
        Table.setLayout(null);
 
        /*========================
		 * Creates the Stay Button
		  ========================*/
        Stay = new JButton("Stay");
        Stay.setBounds(0, 40, wUI, 30);
        Stay.setVisible(false);
        
        /*========================
		 * Creates the Hit Button
		  ========================*/
    	Hit = new JButton("Hit");
    	Hit.setBounds(0, 80, wUI, 30);
    	Hit.setVisible(false);
    	
    	/*========================
		 * Creates the DoubleDown Button
		  ========================*/
    	DoubleDown = new JButton("Double Down");
    	DoubleDown.setBounds(0, 120, wUI, 30);
    	DoubleDown.setVisible(false);
    	
    	/*========================
		 * Creates the Split Button
		  ========================*/
    	Split = new JButton("Split");
    	Split.setBounds(0, 160, wUI, 30);
        Split.setVisible(false);
       
        /*========================
		 * Temp XY Coords
		  ========================*/
        pName = new JLabel("Player Name");
        pName.setText("");
        pName.setBounds(0,0,wUI,30);
        
        /*========================
		 * Creates the JTextField for bet input
		  ========================*/
        betInput = new JTextField();
        betInput.setBounds(0, 40, wUI, 30);
        betInput.setVisible(false);
        
        /*========================
		 * Creates the JButton to submit bet
		  ========================*/
        betButton = new JButton("Bet");
        betButton.setBounds(0, 80, wUI, 30);
        betButton.setVisible(false);
        
        /*========================
		 * Adds the Action and Motion Listeners
		  ========================*/
        Stay.addActionListener(new stayL());
        Hit.addActionListener(new hitL() );
        DoubleDown.addActionListener(new doubleDownL() );
        Split.addActionListener(new splitL() );
        betButton.addActionListener(new betB());
        Frame.addMouseMotionListener(new mList());
        Frame.addMouseListener(new mList());
        
        /*========================
		 * Adds Everything to the JFrame
		  ========================*/
        UI.add(betInput);
        UI.add(betButton);
        UI.add(Stay);
        UI.add(Hit);
        UI.add(DoubleDown);
        UI.add(Split);
        UI.add(pName);
        Frame.getContentPane().add(UI); 
        Frame.getContentPane().add(Table);
	}
	
	public void display() throws IOException, InterruptedException {
		 Frame.pack();
	     Frame.setVisible(true);
	     /*========================
	      * Starts the BlackJack Game
		   ========================*/
	     BlackJack.Play();
	}
	
	private static void displayCards() throws IOException {
		/*========================
		 * Creates a Temp JPanel
		  ========================*/
		Table.setVisible(true);
		JPanel tTab = new JPanel();
		tTab.setBackground(Color.GREEN);
	    tTab.setBounds(0,0,WIDTH,HEIGHT);
	    tTab.setLayout(null);
	    
	    //Gets the Dealer's hand
	    Card[] c = dealer.getHand();
		
	    /*========================
		 * Displays the Dealer's hand
		  ========================*/
		int cardX = (int) (WIDTH / 2) + ((cardW / 4) * (c.length - 2));
	    int cardY = (HEIGHT / 8) - (cardH /2) - 38;
	    for( int index = c.length - 1; index >= 0; index--) {
	    	JLabel temp;
	    	if( index == c.length - 1 && !dealerFlip) {
	    		temp = c[index].paintBack(cardW, cardH);
	    	} else {
	    		temp = c[index].paint(cardW,  cardH);
	    	}
	    	temp.setBounds(cardX, cardY, cardW, cardH);
	        tTab.add(temp);
	        cardX -= cardW/2;
	     }
	    
	    
	    /*========================
		 * Displays the hands of the other players
		  ========================*/
	    for( int index = 0; index < players.length; index++) {
	    	c = players[index].getHand();
	    	switch (index) {
	    	case 0: //Bottom
	    		cardX = (int) (WIDTH / 2) + ((cardW / 4) * (c.length - 2));
		    	cardY = HEIGHT - cardH - 45;
		    	break;
	    	case 1: //Far Right
	    		cardX = WIDTH - cardW - 45;
	    		cardY = (HEIGHT/3) - (cardH/2);
	    		break;
	    	case 2: //Far Left
	    		cardX = cardW + ((cardW / 2) * (c.length - 2));
	    		cardY = (HEIGHT/3) - (cardH/2);
	    		break;
	    	case 3: //Middle Right
	    		cardX = WIDTH - cardW - 90;
	    		cardY = (HEIGHT/2) + (cardH/4);
	    		break;
	    	case 4: //Middle Left
	    		cardX = cardW + ((cardW / 2) * (c.length - 2)) + 45;
	    		cardY = (HEIGHT/2) + (cardH/4);
	    		break;
	    	}
	    	
	    	/*========================
			 * 
			  ========================*/
		    for( int x = c.length - 1; x >= 0; x--) {
			    JLabel temp = c[x].paint(cardW, cardH);
			    temp.setBounds(cardX, cardY, cardW, cardH);
			       tTab.add(temp);
			       cardX -= cardW/2;
			  }
	    	
	    	
	    }

	    
	     Frame.getContentPane().remove(Table);
	     Frame.getContentPane().add(tTab);
	     Frame.repaint();
	     Table = tTab;
	}	
	private void setButtonVis(boolean hs, boolean dd, boolean sp) {
		Stay.setVisible(hs);
		Hit.setVisible(hs);
		DoubleDown.setVisible(dd);
		Split.setVisible(sp);
		Frame.repaint();
	}
	private void displayCards(int x, int y, Card[] c) throws IOException {
		JPanel tTab = new JPanel();
		 tTab.setBackground(Color.GREEN);
	     tTab.setBounds(0,0,WIDTH,HEIGHT);
	     tTab.setLayout(null);
		
		int cardX = (int) (WIDTH / 2) + ((cardW / 4) * (c.length - 2));
	    int cardY = (HEIGHT / 8) - (cardH / 2);
	     for( int index = c.length - 1; index >= 0; index--) {
	    	 JLabel temp = c[index].paint(cardW, cardH);
	    	temp.setBounds(cardX, cardY, cardW, cardH);
	        tTab.add(temp);
	        cardX -= cardW/2;
	     }
	     Frame.getContentPane().remove(Table);
	     Frame.getContentPane().add(tTab);
	     Frame.repaint();
	     Table = tTab;
	}
	private class betB implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			String data = betInput.getText();
			int bet = 0;
			try {
				bet = Integer.parseInt(data);
			} catch( Exception e) {
				bet = 0;
			}
			
			if( bet > 0 ) {
				currBet = bet;
			}
		}
		
	}
	private class stayL implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			hitStay = 2;
			setButtonVis(false, false, false);
			repaint();
		}
		
	}
	private class hitL implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			hitStay = 1;
			setButtonVis(false, false, false);
			repaint();
		}
		
	}
	private class doubleDownL implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			hitStay = 3;
			setButtonVis(false, false, false);
			repaint();
		}
		
	}
	private class splitL implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			hitStay = 4;
			setButtonVis(false, false, false);
			repaint();
		}
		
	}
	private class mList implements MouseMotionListener, MouseListener {
		@Override
		public void mouseClicked(MouseEvent arg0) {
			// TODO Auto-generated method stub
			/*
			setButtonVis(true, false, true);
			dealer.addCard(deck.deal());
			for( int index = 0; index < players.length; index++) {
				players[index].addCard(deck.deal());
			}
		    try {
				displayCards();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			*/
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseDragged(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseMoved(MouseEvent arg0) {
			/*PointerInfo d = MouseInfo.getPointerInfo();
	        Point e = d.getLocation();
	        int XVal = (int) e.getX();
	        int YVal = (int) e.getY();
	        pName.setText("X: " + XVal + " Y: " + YVal);
	        Frame.repaint();*/
		}
		
	}
	public static void getBet(Player cpu) throws InterruptedException {
		int chip = -1;
		if (cpu.isCPU()) {
			chip = cpu.getChips();
			if (chip < 10) {
				cpu.bet(chip);
			} else {
				cpu.bet((int) (chip * .1));
			}
		} else {
				currBet = 0;
				betInput.setVisible(true);
				betButton.setVisible(true);
				Frame.repaint();
				while( currBet <= 0 || !cpu.bet(currBet)) {
					Thread.sleep(2);
				}
				betInput.setVisible(false);
				betButton.setVisible(false);
		}
		System.out.println(cpu.name() + " bet: " + cpu.getBet());
	}
	
	private static class BlackJack {
		private static int actPlayers;

		public static void Play() throws IOException, InterruptedException {
			//Counts the number of non-CPU players
			for (int x = 0; x < players.length; x++) {
				if (!players[x].isCPU())
					actPlayers++;
			}
			//Runs through the game until there are no active users playing
			while (actPlayers > 0) {
				Table.setVisible(false);
				Frame.repaint();
				//Creates dealer player
				dealer = new Player("Dealer", true);
				dealerFlip = false;
				//Creates a new deck and shuffles it
				deck = new Deck();
				deck.shuffle();
				//Gets the bet for each player
				for (int index = 0; index < players.length; index++) {
					pName.setText(players[index].name() + ": " + players[index].getChips() + " chips");
					Frame.repaint();
					getBet(players[index]);
					Thread.sleep(200);
				}
				//Deals out cards to all the players
				for (int x = 0; x < 2; x++) {
					dealer.addCard(deck.deal());
					for (int index = 0; index < players.length; index++) {
						players[index].addCard(deck.deal());
					}
				}
				//Displays the cards
				displayCards();
				//Displays all the bets
				for (int index = 0; index < players.length; index++) {
					if (players[index].isPlaying()) {
						System.out.println(players[index].name() + ": " + players[index].print());
						pName.setText(players[index].name() + ": " + players[index].getChips() + " chips");
						Frame.repaint();
						if (!checkBJ(players[index], dealer)) {
							players[index] = getAction(players[index], deck);
						}
					}
					Thread.sleep(600);
				}			
				dealer = finishDeal(dealer, deck);
				dealerFlip = true;
				displayCards();
				int dealVal = (int) dealer.getHW();
				System.out.println("\n\n\n");
				for (int index = 0; index < players.length; index++) {
					if (players[index].isPlaying()) {
						if( checkBJ(players[index], dealer) ) {
							System.out.println(players[index] + " got blackjack!!");
						} else {
							String decimal = "" + players[index].getHW();
							System.out.println(decimal);
							if (!decimal.contains(".0")) {
								String hand = decimal.substring(0, decimal.indexOf("."));
								String hand1 = decimal.substring(decimal.indexOf(".") + 1);
								int h1 = Integer.parseInt(hand);
								int h2 = Integer.parseInt(hand1);
								if( h2 < 10) 
									h2 = h2 * 10;
								if (h1 < 22 && (h1 > dealVal || dealVal > 21)) {
									if (h2 < 22 && (h2 > dealVal || dealVal > 21)) {
										// h1 win and h2 win
										players[index].win(players[index].getBet() * 2);
										System.out.println(players[index].name() + ", you won both hands!");
									} else if (h2 == dealVal) {
										// h1 win and h2 tie
										players[index].win(players[index].getBet() * (3 / 2));
										System.out.println(players[index].name() + ", you tied one hand and won the other!");
									} else {
										// h1 win and h2 loss
										players[index].win(players[index].getBet());
										System.out.println(players[index].name() + ", you lost one hand and won the other!");
									}
								} else if (h1 == dealVal) {
									if (h2 < 22 && (h2 > dealVal || dealVal > 21)) {
										// h2 win and h1 tie
										players[index].win(players[index].getBet() * (3 / 2));
										System.out.println(players[index].name() + ", you tied one hand and won the other!");
									} else if (h2 == dealVal) {
										// h1 tie and h2 tie
										players[index].win(players[index].getBet());
										System.out.println(players[index].name() + ", you tied both hands!");
									} else {
										// h1 tie and h2 loss
										players[index].win(players[index].getBet() * (1 / 2));
									}
								} else {
									if (h2 < 22 && (h2 > dealVal || dealVal > 21)) {
										// h1 loss and h2 win
										players[index].win(players[index].getBet());
										System.out.println(players[index].name() + ", you lost one hand and won the other!");
									} else if (h2 == dealVal) {
										// h1 loss and h2 tie
										players[index].win(players[index].getBet() * (1 / 2));
										System.out.println(players[index].name() + ", you lost one hand and tied the other!");
									} else {
										// h1 loss and h2 loss
										players[index].lose();
										System.out.println(players[index].name() + ", you lost both hands!");
									}
								}
							} else {
								int h2 = (int) players[index].getHW();
								if (h2 < 22 && (h2 > dealVal || dealVal > 21)) {
									// h2 win
									players[index].win(players[index].getBet() * 2);
									System.out.println(players[index].name() + ", you won your hand!");
								} else if (h2 == dealVal) {
									System.out.println(players[index].name() + ", you tied the dealer");
									players[index].win(players[index].getBet());
								} else {
									System.out.println(players[index].name() + ", you lost your hand!");
									players[index].lose();
								}
							}
						}
					}
				}
				Thread.sleep(1200);
				for( int index = 0; index < players.length; index++) {
					players[index].resetHand();
				}
				
			}
		}
		public static int value(Card[] c) {
			int aceCount = 0;
			int count = 0;
			int sum = 0;
			for (int x = 0; x < c.length; x++) {
				if (Card.getWeight(c[x]) >= 10) {
					count += 10;
				} else if (Card.getWeight(c[x]) > 1) {
					count += Card.getWeight(c[x]);
				} else {
					aceCount++;
				}
			}
			if (aceCount == 1 && count + 11 <= 21) {
				sum = count + 11;
			} else if (count + 10 + aceCount <= 21) {
				sum = count + 10 + aceCount;
			} else {
				sum = count + aceCount;
			}
			return sum;
		}
		public static Player finishDeal(Player deal, Deck deck) {
			Card[] c = deal.getHand();
			int sum = value(deal.getHand());
			while (sum < 17) {
				Card next = deck.deal();
				deal.addCard(next);
				sum = value(deal.getHand());
			}
			deal.hWght(sum);
			return deal;
		}
		public static Player userAction(Player play, Deck deck) throws InterruptedException, IOException {
			boolean split = false;
			boolean dd = false;
			boolean answV = false;
			Card[] curr = play.getHand();
			ArrayList<Card[]> hands = new ArrayList<Card[]>();
			hands.add(play.getHand());
			if (play.isPlaying() && !play.isCPU()) {
				do {
					hitStay = 0;
					curr = play.getHand();
					Hit.setVisible(true);
					Stay.setVisible(true);
					if (play.getHand().length == 2 && compareTo(curr[0], curr[1]) == 0
							&& play.getBet() <= play.getChips()) {
						Split.setVisible(true);
					} 
					if (play.getHand().length == 2 && play.getBet() <= play.getChips()) {
						DoubleDown.setVisible(true);
					}
					while( hitStay <= 0 ) {
						Thread.sleep(2);
					}
					switch ( hitStay ) {
					case 0: 
						System.out.println("ERROR");
						break;
					case 1:
						play.addCard(deck.deal());
						if (value(play.getHand()) < 22) {
							answV = false;
						} else {
							answV = true;
						}
						displayCards();
						System.out.println("\n");
						break;
					case 2:
						play.hWght(value(play.getHand()));
						answV = true;
						break;
					case 3: 
						play.addCard(deck.deal());
						int bet = play.getBet();
						play.win(bet);
						play.bet(2 * bet);
						answV = true;
						dd = true;
						displayCards();
						play.hWght(value(play.getHand()));
						break;
					case 4:
						Card[] one = new Card[2];
						Card[] two = new Card[2];
						one[0] = curr[0];
						one[1] = deck.deal();
						two[0] = curr[1];
						two[1] = deck.deal();
						bet = play.getBet();
						play.win(bet);
						play.bet(2 * bet);
						play.resetHand();
						play.addCard(one[0]);
						play.addCard(one[1]);
						play.addCard(Card.BLANK);
						play.addCard(two[0]);
						play.addCard(two[1]);
						hands.clear();
						hands.add(one);
						hands.add(two);
						split = true;
						answV = true;
						displayCards();
						break;
					}
					Hit.setVisible(false);
					Stay.setVisible(false);
					Split.setVisible(false);
					DoubleDown.setVisible(false);
					hitStay = 0;
				} while (!answV && !dd && value(play.getHand()) < 22);

				if (split) {
					int[] w = new int[2];
					Card[] all = new Card[1];
					for (int index = 0; index < hands.size(); index++) {
						if( index == 0 ) {
							pName.setText(players[index].name() + ": bet: " + players[index].getBet() + " chips. Left hand");
							Frame.repaint();
						} else if( index == 1) {
							pName.setText(players[index].name() + ": bet: " + players[index].getBet() + " chips. Right hand");
							Frame.repaint();
						}
						curr = hands.get(index);
						do {
							hitStay = 0;
							Hit.setVisible(true);
							Stay.setVisible(true);
							Frame.repaint();
							while( hitStay <= 0 ) {
								Thread.sleep(2);
							}
							switch(hitStay) {
							case 1:
								Card[] temp = new Card[curr.length + 1];
								for (int x = 0; x < curr.length; x++) {
									temp[x] = curr[x];
								}
								Card c = deck.deal();
								temp[temp.length - 1] = c;
								if(index == 0) {
									Card[] newH = new Card[temp.length + hands.get(1).length + 1];
									for( int x = 0; x < newH.length; x++) {
										if( x < temp.length - 1 ) {
											newH[x] = temp[x];
										} else if( x > temp.length) {
											newH[x] = hands.get(1)[x - temp.length - 1];
										} else {
											newH[x] = Card.BLANK;
										}
									}
									all = newH.clone();
									play.importHand(all);
								} else {
									play.addCard(c);
								}
								if (value(temp) < 22) {
									answV = false;
								} else {
									answV = true;
								}
								curr = temp;
								displayCards();
								w[index] = value(curr);
								break;
							case 2:
								answV = true;
								w[index] = value(curr);
								break;
							}
							System.out.println("answV: " + answV + " value: " + value(curr));
						} while (!answV && value(curr) < 22);
					}
					play.hWght(w[0] + (.01 * w[1]));
					displayCards();
				}
			}
			System.out.println("Continue");
			return play;
		}
		public static Player getAction(Player play, Deck deck) throws InterruptedException, IOException {
			pName.setText(play.name() + ": bet: " + play.getBet() + " chips");
			Frame.repaint();
			if (play.isCPU() && play.isPlaying()) {
				Card[] c = play.getHand();
				int sum = value(play.getHand());
				boolean active = true;
				boolean split = false;
				while (sum < 22 && active && !split) {
					int handVal = value(play.getHand());
					if (play.getHand().length == 2 && compareTo(c[0], c[1]) == 0 && play.getBet() <= play.getChips()) {
						System.out.println("Split");
						int bet = play.getBet();
						play.win(bet);
						play.bet(2 * bet);
						split = true;
					} else if (play.getHand().length == 2 && play.getBet() <= play.getChips()) {
						if (handVal == 10 && (int) (Math.random() * 2) < 1) {
							System.out.println("Double Down");
							active = false;
							Card next = deck.deal();
							play.addCard(next);
							sum = value(play.getHand());
							int bet = play.getBet();
							play.win(bet);
							play.bet(2 * bet);
							displayCards();
						} else if ((int) (Math.random() * 75) < 1) {
							System.out.println("Double Down");
							active = false;
							Card next = deck.deal();
							play.addCard(next);
							sum = value(play.getHand());
							displayCards();
						}
					} 
					if (handVal < 15 && active && !split) {
						System.out.println("Hit");
						Card next = deck.deal();
						play.addCard(next);
						sum = value(play.getHand());
					} else if (handVal < 19 && (int) (Math.random() * 4) < 1 && !split) {
						System.out.println("Hit");
						Card next = deck.deal();
						play.addCard(next);
						sum = value(play.getHand());
					} else if ( !split ){
						System.out.println("Stay");
						active = false;
					}
					displayCards();
				}
				if (split) {
					ArrayList<Card[]> hands = new ArrayList<Card[]>();
					Card[] one = new Card[2];
					Card[] two = new Card[2];
					one[0] = play.getHand()[0];
					two[0] = play.getHand()[1];
					one[1] = deck.deal();
					two[1] = deck.deal();
					play.addCard(one[1]);
					play.addCard(two[1]);
					int[] w = new int[2];
					Card[] all = new Card[1];
					Card[] tAll = new Card[1];
					hands.add(one);
					hands.add(two);
					for (int x = 0; x < hands.size(); x++) {
						active = true;
						Card[] curr = hands.get(x);
						do { 
							int valHand = value(curr);
							if (valHand < 14) {
								Card next = deck.deal();
								Card[] temp = new Card[curr.length + 1];
								for (int index = 0; index < curr.length; index++) {
									temp[index] = curr[index];
								}
								temp[temp.length - 1] = next;
								play.addCard(next);
								curr = temp;
								sum = value(curr);
							} else if (value(play.getHand()) < 16 && (int) (Math.random() * 4) < 2) {
								Card next = deck.deal();
								Card[] temp = new Card[curr.length + 1];
								for (int index = 0; index < curr.length; index++) {
									temp[index] = curr[index];
								}
								temp[temp.length - 1] = next;
								play.addCard(next);
								curr = temp;
								sum = value(curr);
							} else if (value(play.getHand()) < 19 && (int) (Math.random() * 4) < 1) {
								Card next = deck.deal();
								Card[] temp = new Card[curr.length + 1];
								for (int index = 0; index < curr.length; index++) {
									temp[index] = curr[index];
								}
								temp[temp.length - 1] = next;
								play.addCard(next);
								curr = temp;
								sum = value(curr);
							} else {
								w[x] = value(curr);
								active = false;
							}
							tAll = curr;
						} while (active && value(curr) < 22);
						if( x == 0) {
							all = new Card[tAll.length + 1];
							for( int index = 0; index < tAll.length; index++) {
								all[index]= tAll[index];
							}
							all[all.length-1] = Card.BLANK;
						} else {
							Card[] temp = all.clone();
							all = new Card[temp.length + tAll.length];
							for( int index = 0; index < temp.length; index++) {
								all[index] = temp[index];
							}
							for( int index = 0; index < tAll.length; index++) {
								all[index + temp.length] = tAll[index];
							}
						}
					}
					play.importHand(all);
					play.hWght(w[0] + (.01 * w[1]));
				} else {
					play.hWght(value(play.getHand()));
				}
			} else if (!play.isCPU() && play.isPlaying()) {
				play = userAction(play, deck);
			}
			return play;
		}
		public static String printHand(Card[] c) {
			String ret = "";
			for (int index = 0; index < c.length; index++) {
				ret += c[index].print() + " ";
			}
			return ret;
		}
		public static int compareTo(Card one, Card two) {
			if (Card.getWeight(one) >= 10 && Card.getWeight(two) >= 10) {
				return 0;
			} else if (Card.getWeight(one) == Card.getWeight(two)) {
				return 0;
			} else if (Card.getWeight(one) > Card.getWeight(two)) {
				return 1;
			}
			return -1;
		}
		public static boolean checkBJ(Player play, Player dealer) {
			Card[] hand = play.getHand();
			if ((Card.getWeight(hand[0]) > 10 && Card.getWeight(hand[1]) == 1 && hand.length == 2)
					|| (Card.getWeight(hand[1]) > 10 && Card.getWeight(hand[0]) == 1 && hand.length == 2)) {
				hand = dealer.getHand();
				if ((Card.getWeight(hand[0]) > 10 && Card.getWeight(hand[1]) == 1 && hand.length == 2)
						|| (Card.getWeight(hand[1]) > 10 && Card.getWeight(hand[0]) == 1 && hand.length == 2)) {
					play.win(play.getBet());
				} else {
					play.win((int) (play.getBet() * (3 / 2)));
				}
				return true;
			}

			return false;
		}
		public static int activePlayers() {
			int count = 0;
			for (int index = 0; index < players.length; index++) {
				players[index].resetHand();
				if (players[index].getChips() > 0 && !players[index].isCPU()) {
					boolean vAnsw = false;
					while (!vAnsw) {
						System.out.print(players[index].name() + ", would you like to play again: ");
						Scanner cin = new Scanner(System.in);
						String answer = cin.next();
						answer = answer.toLowerCase();
						switch (answer) {
						case "yes":
						case "y":
							players[index].setActive(true);
							vAnsw = true;
							count++;
							break;
						case "no":
						case "n":
							players[index].setActive(false);
							vAnsw = true;
							break;
						}
					}
				} else if( players[index].isCPU() && players[index].getBet() > 0) {
					players[index].setActive(true);
				}
			}
			return count;
		}
		public static ArrayList<Player> convert(Player[] play) {
			ArrayList<Player> ret = new ArrayList<Player>();
			for( int index = 0; index < play.length; index++) {
				ret.add(play[index]);
			}
			return ret;
		}	
		public static void convert(ArrayList<Player> play) {
			players = new Player[play.size()];
			for (int index = 0; index < play.size(); index++) {
				players[index] = play.get(index);
			}
		}
	}
}
