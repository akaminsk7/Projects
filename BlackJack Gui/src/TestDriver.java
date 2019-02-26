import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class TestDriver {

	public static void main(String[] args) throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		ArrayList<Player> play = new ArrayList<Player>();
		play.add(new Player("Aaron"));
		play.add(new Player());
		play.add(new Player());
		play.add(new Player());
		play.add(new Player());
		//BlackJack.Play(play);
		BlackJackGUI game = new BlackJackGUI(play.toArray(new Player[play.size()]));
		game.display();
	}

}

/*
 * Texas Holdem CARD SETUP
 * 		private int cardH = HEIGHT / 6;
		private int cardW = HEIGHT / 10;
  		board = new JPanel[5];
        int cardX = (int) ((WIDTH / 2) - (cardW * 2.5) - 30);
        int cardY = (HEIGHT / 2) - (cardH / 2);
        for( int index = 0; index < board.length; index++) {
        	board[index] = new JPanel();
        	board[index].setOpaque(false);
        	board[index].setBounds(cardX,cardY,cardW,cardH);
        	board[index].setBorder(BorderFactory.createLineBorder(Color.black));
        	cardX += 10 + cardW;
        	Frame.getContentPane().add(board[index]);
        }
 * 
 */
