import java.util.ArrayList;
import java.util.Scanner;
import Deck.Card;
import Deck.Deck;

public class BlackJack {
	private static Player[] plays;
	private static int actPlayers;
	private static Player dealer;

	public static void Play(ArrayList<Player> play) {
		convert(play);
		for (int x = 0; x < plays.length; x++) {
			if (!plays[x].isCPU())
				actPlayers++;
		}
		while (actPlayers > 0) {
			dealer = new Player("Dealer", true);
			Deck deck = new Deck();
			deck.shuffle();
			for (int index = 0; index < plays.length; index++) {
				getBet(plays[index]);
			}
			for (int x = 0; x < 2; x++) {
				dealer.addCard(deck.deal());
				for (int index = 0; index < plays.length; index++) {
					plays[index].addCard(deck.deal());
				}
			}
			System.out.println("Dealer: " + dealer.getHand()[0].print());
			for (int index = 0; index < plays.length; index++) {
				if (plays[index].isPlaying()) {
				System.out.println(plays[index].name() + ": " + plays[index].print());
					if (checkBJ(plays[index], dealer)) {
						plays[index].setActive(false);
						plays[index].resetHand();
					System.out.println("You got BlackJack! Enjoy your winnings");
					} else {
						plays[index] = getAction(plays[index], deck);
					}
				}
			}			
			dealer = finishDeal(dealer, deck);
			int dealVal = (int) dealer.getHW();
			System.out.println("\n\n\n");
			for (int index = 0; index < plays.length; index++) {
				if (plays[index].isPlaying()) {
					System.out.println("\nDealer's Cards: " + printHand(dealer.getHand()));
					System.out.println(plays[index].name() + ": " + printHand(plays[index].getHand()));
					String decimal = "" + plays[index].getHW();
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
								plays[index].win(plays[index].getBet() * 2);
								System.out.println(plays[index].name() + ", you won both hands!");
							} else if (h2 == dealVal) {
								// h1 win and h2 tie
								plays[index].win(plays[index].getBet() * (3 / 2));
								System.out.println(plays[index].name() + ", you tied one hand and won the other!");
							} else {
								// h1 win and h2 loss
								plays[index].win(plays[index].getBet());
								System.out.println(plays[index].name() + ", you lost one hand and won the other!");
							}
						} else if (h1 == dealVal) {
							if (h2 < 22 && (h2 > dealVal || dealVal > 21)) {
								// h2 win and h1 tie
								plays[index].win(plays[index].getBet() * (3 / 2));
								System.out.println(plays[index].name() + ", you tied one hand and won the other!");
							} else if (h2 == dealVal) {
								// h1 tie and h2 tie
								plays[index].win(plays[index].getBet());
								System.out.println(plays[index].name() + ", you tied both hands!");
							} else {
								// h1 tie and h2 loss
								plays[index].win(plays[index].getBet() * (1 / 2));
							}
						} else {
							if (h2 < 22 && (h2 > dealVal || dealVal > 21)) {
								// h1 loss and h2 win
								plays[index].win(plays[index].getBet());
								System.out.println(plays[index].name() + ", you lost one hand and won the other!");
							} else if (h2 == dealVal) {
								// h1 loss and h2 tie
								plays[index].win(plays[index].getBet() * (1 / 2));
								System.out.println(plays[index].name() + ", you lost one hand and tied the other!");
							} else {
								// h1 loss and h2 loss
								plays[index].lose();
								System.out.println(plays[index].name() + ", you lost both hands!");
							}
						}
					} else {
						int h2 = (int) plays[index].getHW();
						if (h2 < 22 && (h2 > dealVal || dealVal > 21)) {
							// h2 win
							plays[index].win(plays[index].getBet() * 2);
							System.out.println(plays[index].name() + ", you won your hand!");
						} else if (h2 == dealVal) {
							System.out.println(plays[index].name() + ", you tied the dealer");
							plays[index].win(plays[index].getBet());
						} else {
							System.out.println(plays[index].name() + ", you lost your hand!");
							plays[index].lose();
						}
					}
				}
			}
			Scanner cin = new Scanner(System.in);
			String yuh = cin.nextLine();
			actPlayers = activePlayers();
			actPlayers = 1;
			System.out.println("\n\n\n\n\n\n\n\n\n");
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

	public static Player userAction(Player play, Deck deck) {
		boolean split = false;
		boolean dd = false;
		boolean answV = false;
		Card[] curr = play.getHand();
		ArrayList<Card[]> hands = new ArrayList<Card[]>();
		hands.add(play.getHand());
		if (play.isPlaying() && !play.isCPU()) {
			do {
				curr = play.getHand();
				System.out.print(play.name() + ": " + printHand(curr) + "\nWould you like to hit(h),");
				if (play.getHand().length == 2 && compareTo(curr[0], curr[1]) == 0
						&& play.getBet() <= play.getChips()) {
					System.out.print(" split(sp),");
				} 
				if (play.getHand().length == 2 && play.getBet() <= play.getChips()) {
					System.out.print(" double down(d),");
				}
				System.out.print(" or stay(s): ");
				Scanner cin = new Scanner(System.in);
				String answer = cin.next();
				answer = answer.toLowerCase();
				switch (answer) {
				case "stay":
				case "s":
					play.hWght(value(play.getHand()));
					answV = true;
					break;
				case "hit":
				case "h":
					play.addCard(deck.deal());
					if (value(play.getHand()) < 22) {
						answV = false;
					} else {
						answV = true;
					}
					System.out.println("\n");
					break;
				case "split":
				case "sp":
					Card[] one = new Card[2];
					Card[] two = new Card[2];
					one[0] = curr[0];
					one[1] = deck.deal();
					two[0] = curr[1];
					two[1] = deck.deal();
					int bet = play.getBet();
					play.win(bet);
					play.bet(2 * bet);
					play.addCard(one[1]);
					play.addCard(two[1]);
					hands.add(one);
					hands.add(two);
					split = true;
					answV = true;
					break;
				case "double":
				case "double down":
				case "d":
				case "dd":
					play.addCard(deck.deal());
					bet = play.getBet();
					play.win(bet);
					play.bet(2 * bet);
					answV = true;
					dd = true;
					System.out.println(play.name() + ": " + printHand(play.getHand()));
					play.hWght(value(play.getHand()));
					break;
				}
			} while (!answV && !dd && value(play.getHand()) < 22);

			if (split) {
				int[] w = new int[2];
				Card[] tAll;
				Card[] all = new Card[1];
				for (int index = 0; index < hands.size(); index++) {
					curr = hands.get(index);
					do {
						System.out.print(play.name() + ", Hand" + (index + 1) + ": " + printHand(curr) + "\nWould you like to hit(h), or stay(s): ");
						Scanner cin = new Scanner(System.in);
						String answer = (cin.next()).toLowerCase();
						switch (answer) {
						case "stay":
						case "s":
							answV = true;
							w[index] = value(curr);
							break;
						case "hit":
						case "h":
							Card[] temp = new Card[curr.length + 1];
							for (int x = 0; x < curr.length; x++) {
								temp[x] = curr[x];
							}
							Card c = deck.deal();
							temp[temp.length - 1] = c;
							play.addCard(c);
							if (value(temp) < 22) {
								answV = false;
							} else {
								answV = true;
							}
							curr = temp;
							System.out.println("\n\n\n");
						}
						tAll = curr;
					} while (!answV && value(curr) < 22);
					if( index == 0) {
						all = new Card[tAll.length + 1];
						for( int x = 0; x < tAll.length; x++) {
							all[x]= tAll[x];
						}
						all[all.length-1] = Card.BLANK;
					} else {
						Card[] temp = all.clone();
						all = new Card[temp.length + tAll.length];
						for( int x = 0; x < temp.length; x++) {
							all[x] = temp[x];
						}
						for( int x = 0; x < tAll.length; x++) {
							all[x] = temp[x + temp.length];
						}
					}
				}
				play.importHand(all);
				play.hWght(w[0] + (.01 * w[1]));
			}
		}
		return play;
	}

	public static Player getAction(Player play, Deck deck) {
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
						System.out.println(play.name() + ": " + printHand(play.getHand()));
					} else if ((int) (Math.random() * 75) < 1) {
						System.out.println("Double Down");
						active = false;
						Card next = deck.deal();
						play.addCard(next);
						sum = value(play.getHand());
						System.out.println(play.name() + ": " + printHand(play.getHand()));
					}
				} 
				if (handVal < 15 && active && !split) {
					System.out.println("Hit");
					Card next = deck.deal();
					play.addCard(next);
					sum = value(play.getHand());
					System.out.println(play.name() + ": " + printHand(play.getHand()));
				} else if (handVal < 19 && (int) (Math.random() * 4) < 1 && !split) {
					System.out.println("Hit");
					Card next = deck.deal();
					play.addCard(next);
					sum = value(play.getHand());
					System.out.println(play.name() + ": " + printHand(play.getHand()));
				} else if ( !split ){
					System.out.println("Stay");
					active = false;
				}

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
		if ((Card.getWeight(hand[0]) > 10 && Card.getWeight(hand[1]) == 1)
				|| (Card.getWeight(hand[1]) > 10 && Card.getWeight(hand[0]) == 1)) {
			hand = dealer.getHand();
			if ((Card.getWeight(hand[0]) > 10 && Card.getWeight(hand[1]) == 1)
					|| (Card.getWeight(hand[1]) > 10 && Card.getWeight(hand[0]) == 1)) {
				play.win(play.getBet());
			} else {
				play.win((int) (play.getBet() * (3 / 2)));
			}
			return true;
		}

		return false;
	}

	public static void getBet(Player cpu) {
		int chip;
		if (cpu.isCPU()) {
			chip = cpu.getChips();
			if (chip < 10) {
				cpu.bet(chip);
			} else {
				cpu.bet((int) (chip * .1));
			}
		} else {
			do {
				System.out.print( cpu.name() + ", you have " + cpu.getChips() + " chips.\nHow much would you like to bet: ");
				Scanner cin = new Scanner(System.in);
				chip = Integer.parseInt(cin.next());
				System.out.println("\n\n");
			} while (!cpu.bet(chip));
		}
		System.out.println(cpu.name() + " bet: " + cpu.getBet());
	}

	public static int activePlayers() {
		int count = 0;
		for (int index = 0; index < plays.length; index++) {
			plays[index].resetHand();
			if (plays[index].getChips() > 0 && !plays[index].isCPU()) {
				boolean vAnsw = false;
				while (!vAnsw) {
					System.out.print(plays[index].name() + ", would you like to play again: ");
					Scanner cin = new Scanner(System.in);
					String answer = cin.next();
					answer = answer.toLowerCase();
					switch (answer) {
					case "yes":
					case "y":
						plays[index].setActive(true);
						vAnsw = true;
						count++;
						break;
					case "no":
					case "n":
						plays[index].setActive(false);
						vAnsw = true;
						break;
					}
				}
			} else if( plays[index].isCPU() && plays[index].getBet() > 0) {
				plays[index].setActive(true);
			}
		}
		return count;
	}

	public static void convert(ArrayList<Player> play) {
		plays = new Player[play.size()];
		for (int index = 0; index < play.size(); index++) {
			plays[index] = play.get(index);
		}
	}
}
