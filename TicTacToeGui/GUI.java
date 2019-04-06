import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
/**
 * This is the GUI for a Tic Tac Toe Game
 * @author akami_000
 * @version 8/29/2017
 */
public class GUI {
	private int BORDER = 10; //Border around the GUI
	private int WIDTH = 1275 + (3 * BORDER); //Width of the screen
	private int HEIGHT = 975 + (2 * BORDER); //Height of the screen

	private int[] symbols; //Move counter 
	private int[] score; //Score counter
	private Font SYMBOL_FONT; //Font for the Xs and Os
	private String summary; //Message inside of the output panel
	private JFrame frame; //The frame that holds the GUI
	private JPanel[] panel; //The white panel that holds the Xs and Os
	private JLabel[] sym; //The Array thar holds the Xs Os and _s
	private JPanel[] board; //The Array holding the border
	private JPanel IO; //The Output panel
	private JTextArea input; //The Text area displaying the text
	private JButton again; //The play again button
	private boolean lockClick; //Locks the user from clicking after the game ends
	private int turnNum; //Counter for the turn number

	/**
	 * This is the default constructor for the GUI class
	 */
	public GUI() {
		// Sets up the JFrame
		frame = new JFrame("Tic Tac Toe");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		frame.setBackground(Color.CYAN);
		frame.setResizable(false);

		// This adds all of the mouse listeners
		frame.addMouseListener(new MouseTrack());

		// Creates the border for the outside
		board = new JPanel[5];
		for (int index = 0; index < board.length; index++) {
			board[index] = new JPanel();
			board[index].setBackground(Color.BLACK);
		}
		board[0].setBounds(0, 0, BORDER, HEIGHT);
		board[1].setBounds(0, 0, WIDTH, BORDER);
		board[2].setBounds(1280 + BORDER, 0, BORDER, HEIGHT);
		board[3].setBounds(0, 950, WIDTH, BORDER);
		board[4].setBounds((950 + BORDER), 0, BORDER, HEIGHT);
		for (int index = 0; index < board.length; index++) {
			frame.getContentPane().add(board[index]);
		}
		
		//This creates the font for the Xs and Os
		SYMBOL_FONT = new Font("Symbol Font", Font.BOLD, 300);

		// Sets up the Squares
		int xBound = BORDER;
		int yBound = BORDER;
		panel = new JPanel[9];
		sym = new JLabel[9];
		for (int index = 0; index < panel.length; index++) {
			panel[index] = new JPanel();
			panel[index].setBackground(Color.WHITE);
			panel[index].setBounds(xBound, yBound, 300, 300);
			sym[index] = new JLabel();
			sym[index].setText(" ");
			sym[index].setBounds(xBound + 50, yBound, 300, 300);
			sym[index].setFont(SYMBOL_FONT);
			sym[index].setForeground(Color.BLUE);
			xBound += 325;
			if (xBound >= 950 + BORDER) {
				xBound = BORDER;
				yBound += 320;
			}
		}
		//Adds the Squares to the JFrame
		for (int index = 0; index < panel.length; index++) {
			frame.getContentPane().add(sym[index]);
			frame.getContentPane().add(panel[index]);
		}
		
		// Sets up the summary box
		score = new int[3];
		score[0] = 0;
		score[1]= 0;
		score[2] = 0;
		summary = "Welcome to Big Red Tic Tac Toe!!\n\nScoreboard: " + score[0] + " | " + score[1] + " | " + score[2] + "\n";
		input = new JTextArea();
		input.setEditable(false);
		input.setText(summary);
		input.setFont(new Font("Classic", Font.PLAIN, 15));
		input.setBounds((950 + (2 * BORDER)), 250, 320, 450);
		frame.getContentPane().add(input);

		//Sets up the dissapearing PLay Again Button
		again = new JButton();
		again.setText("Play Again?");
		again.setFont(new Font("Again Button", Font.PLAIN, 35));
		again.setForeground(Color.WHITE);
		again.setBackground(Color.BLACK);
		again.setBounds((970 + (2 * BORDER)), 800, 280, 75);
		again.setVisible(false);
		again.addActionListener(new bAction());
		frame.getContentPane().add(again);

		// Creates the IO Panel
		IO = new JPanel();
		IO.setBackground(Color.red);
		IO.setBounds((950 + (2 * BORDER)), BORDER, 320, 950);
		frame.getContentPane().add(IO);
		
		//sets up the move tracker
		symbols = new int[9];
		for (int index = 0; index < symbols.length; index++) {
			symbols[index] = 0;
		}
		//This sets instance data to default values
		lockClick = false;
		turnNum = 0;
	}
	
	/**
	 * Method that displays the 
	 */
	public void display() {
		frame.pack();
		frame.setVisible(true);
	}

	private class MouseTrack implements MouseListener {

		@Override
		public void mousePressed(MouseEvent arg0) {
		}

		@Override
		public void mouseClicked(MouseEvent arg0) {
			if (!lockClick)
				Process();
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
		}

		public void Process() {
			PointerInfo d = MouseInfo.getPointerInfo();
			Point e = d.getLocation();
			int x = (int) e.getX();
			int y = (int) e.getY();
			boolean move = false;

			if (x > 10 && x < 315 && y > 10 && y < 340 && sym[0].getText() == " ") { // 1
				sym[0].setText("X");
				sym[0].setForeground(Color.BLUE);
				symbols[0] = 1;
				summary += "User: (0,0)\n";
				move = true;
			} else if (x > 340 && x < 645 && y > 10 && y < 340 && sym[1].getText() == " ") { // 2
				sym[1].setText("X");
				sym[1].setForeground(Color.BLUE);
				symbols[1] = 1;
				summary += "User: (0,1)\n";
				move = true;
			} else if (x > 670 && x < 975 && y > 10 && y < 340 && sym[2].getText() == " ") { // 3
				sym[2].setText("X");
				sym[2].setForeground(Color.BLUE);
				symbols[2] = 1;
				summary += "User: (0,2)\n";
				move = true;
			} else if (x > 10 && x < 315 && y > 365 && y < 665 && sym[3].getText() == " ") { // 4
				sym[3].setText("X");
				sym[3].setForeground(Color.BLUE);
				symbols[3] = 1;
				summary += "User: (1,0)\n";
				move = true;
			} else if (x > 340 && x < 645 && y > 365 && y < 665 && sym[4].getText() == " ") { // 5
				sym[4].setText("X");
				sym[4].setForeground(Color.BLUE);
				symbols[4] = 1;
				summary += "User: (1,1)\n";
				move = true;
			} else if (x > 670 && x < 975 && y > 365 && y < 665 && sym[5].getText() == " ") { // 6
				sym[5].setText("X");
				sym[5].setForeground(Color.BLUE);
				symbols[5] = 1;
				summary += "User: (1,2)\n";
				move = true;
			} else if (x > 10 && x < 315 && y > 685 && y < 980 && sym[6].getText() == " ") { // 7
				sym[6].setText("X");
				sym[6].setForeground(Color.BLUE);
				symbols[6] = 1;
				summary += "User: (2,0)\n";
				move = true;
			} else if (x > 340 && x < 645 && y > 685 && y < 980 && sym[7].getText() == " ") { // 8
				sym[7].setText("X");
				sym[7].setForeground(Color.BLUE);
				symbols[7] = 1;
				summary += "User: (2,1)\n";
				move = true;
			} else if (x > 670 && x < 975 && y > 685 && y < 980 && sym[8].getText() == " ") { // 9
				sym[8].setText("X");
				sym[8].setForeground(Color.BLUE);
				symbols[8] = 1;
				summary += "User: (2,2)\n";
				move = true;
			}
			if( move) {
				if (testWin(3)) {
					summary += "Congratulations! The User Won!!\n";
					score[0]++;
					again.setVisible(true);
					lockClick = true;
				} else if (hasLeft()) {
					turnNum++;
					int cpu = CPUMove();
					sym[cpu].setText("O");
					sym[cpu].setForeground(Color.RED);
					symbols[cpu] = 4;
					summary += "Computer: ";
					switch(cpu) {
					case 0:
						summary += "(0,0)\n";
						break;
					case 1:
						summary += "(0,1)\n";
						break;
					case 2:
						summary += "(0,2)\n";
						break;
					case 3:
						summary += "(1,0)\n";
						break;
					case 4:
						summary += "(1,1)\n";
						break;
					case 5:
						summary += "(1,2)\n";
						break;
					case 6:
						summary += "(2,0)\n";
						break;
					case 7:
						summary += "(2,1)\n";
						break;
					case 8:
						summary += "(2,2)\n";
						break;
					}
					if (testWin(12)) {
						summary += "You Lost!!\n";
						score[1]++;
						again.setVisible(true);
						lockClick = true;
					}
				} else {
					summary += "You tied with the computer\n";
					score[2]++;
					again.setVisible(true);
					lockClick = true;
				}
			}

			input.setText(summary);
			frame.repaint();

		}

		public boolean hasLeft() {
			for (int index = 0; index < symbols.length; index++) {
				if (symbols[index] == 0)
					return true;
			}
			return false;
		}

		public boolean testWin(int num) {
			if (symbols[0] + symbols[1] + symbols[2] == num || symbols[3] + symbols[4] + symbols[5] == num
					|| symbols[6] + symbols[7] + symbols[8] == num || symbols[0] + symbols[3] + symbols[6] == num
					|| symbols[1] + symbols[4] + symbols[7] == num || symbols[2] + symbols[5] + symbols[8] == num
					|| symbols[0] + symbols[4] + symbols[8] == num || symbols[6] + symbols[4] + symbols[2] == num) {
				return true;
			}
			return false;
		}

		public int CPUMove() {
			/*
			 * THIS IS HOW THE MATRIX WILL WORK 1. The positions left will start
			 * with a value of 0 2. Then it will run through all the possible
			 * position combos and assign points to block a win (10 points) 3.
			 * After, it will run through and add points to winning stratagies
			 * (3 points) 4. It will then get the position of the most strategic
			 * move and return it as an int
			 * 
			 * 
			 */
			int[][] Matrix = new int[3][3];
			for (int[] arr : Matrix) {
				for (int index = 0; index < arr.length; index++) {
					arr[index] = 0;
				}
			}

			for (int index = 0; index < symbols.length; index++) {
				if (symbols[index] != 0) {
					switch (index) {
					case 0:
						Matrix[0][0] = -1;
						break;
					case 1:
						Matrix[0][1] = -1;
						break;
					case 2:
						Matrix[0][2] = -1;
						break;
					case 3:
						Matrix[1][0] = -1;
						break;
					case 4:
						Matrix[1][1] = -1;
						break;
					case 5:
						Matrix[1][2] = -1;
						break;
					case 6:
						Matrix[2][0] = -1;
						break;
					case 7:
						Matrix[2][1] = -1;
						break;
					case 8:
						Matrix[2][2] = -1;
						break;
					}
				}
			}
			if (turnNum == 1) {
				if (symbols[4] == 1) {
					Matrix[0][0] += 20;
					Matrix[0][2] += 20;
					Matrix[2][0] += 20;
					Matrix[2][2] += 20;
				} else {
					Matrix[1][1] += 20;
				}
			} else {
				if (Matrix[0][0] == 0) {
					if (symbols[1] + symbols[2] == 2 || symbols[3] + symbols[6] == 2 || symbols[4] + symbols[8] == 2)
						Matrix[0][0] += 20;
					if (symbols[1] + symbols[2] == 8 || symbols[3] + symbols[6] == 8 || symbols[4] + symbols[8] == 8)
						Matrix[0][0] += 100;
					if( symbols[1] == 1) 
						Matrix[0][0]++;
					if( symbols[3] == 1)
						Matrix[0][0]++;
					if(symbols[4] == 1)
						Matrix[0][0]++;
				}
				if (Matrix[0][1] == 0) {
					if (symbols[0] + symbols[2] == 2 || symbols[4] + symbols[7] == 2)
						Matrix[0][1] += 20;
					if (symbols[0] + symbols[2] == 8 || symbols[4] + symbols[7] == 8)
						Matrix[0][1] += 100;
					if( symbols[0] == 1) 
						Matrix[0][1]++;
					if( symbols[2] == 1)
						Matrix[0][1]++;
					if(symbols[4] == 1)
						Matrix[0][1]++;
				}
				if (Matrix[0][2] == 0) {
					if (symbols[1] + symbols[0] == 2 || symbols[5] + symbols[8] == 2 || symbols[4] + symbols[6] == 2) 
						Matrix[0][2] += 20;
					if (symbols[1] + symbols[0] == 8 || symbols[5] + symbols[8] == 8 || symbols[4] + symbols[6] == 8) 
						Matrix[0][2] += 100;
					if( symbols[1] == 1) 
						Matrix[0][2]++;
					if( symbols[5] == 1)
						Matrix[0][2]++;
					if(symbols[4] == 1)
						Matrix[0][2]++;
				}
				if (Matrix[1][0] == 0) {
					if (symbols[4] + symbols[5] == 2 || symbols[0] + symbols[6] == 2)
						Matrix[1][0] += 20;
					if (symbols[4] + symbols[5] == 8 || symbols[0] + symbols[6] == 8)
						Matrix[1][0] += 100;
					if( symbols[0] == 1) 
						Matrix[1][0]++;
					if( symbols[6] == 1)
						Matrix[1][0]++;
					if(symbols[4] == 1)
						Matrix[1][0]++;
				}
				if (Matrix[1][1] == 0) {
					if (symbols[3] + symbols[5] == 2 || symbols[1] + symbols[7] == 2 || symbols[0] + symbols[8] == 2
							|| symbols[6] + symbols[2] == 2)
						Matrix[1][1] += 20;
					if (symbols[3] + symbols[5] == 8 || symbols[1] + symbols[7] == 8 || symbols[0] + symbols[8] == 8
							|| symbols[6] + symbols[2] == 8)
						Matrix[1][1] += 100;
					if( symbols[0] == 1) 
						Matrix[1][1]++;
					if( symbols[1] == 1)
						Matrix[1][1]++;
					if(symbols[2] == 1)
						Matrix[1][1]++;
					if( symbols[3] == 1) 
						Matrix[1][1]++;
					if( symbols[5] == 1)
						Matrix[1][1]++;
					if(symbols[6] == 1)
						Matrix[1][1]++;
					if( symbols[7] == 1) 
						Matrix[1][1]++;
					if( symbols[8] == 1)
						Matrix[1][1]++;
				}
				if (Matrix[1][2] == 0) {
					if (symbols[2] + symbols[8] == 2 || symbols[3] + symbols[4] == 2)
						Matrix[1][2] += 20;
					else if (symbols[2] + symbols[8] == 8 || symbols[3] + symbols[4] == 8)
						Matrix[1][2] += 100;
					if( symbols[2] == 1) 
						Matrix[1][2]++;
					if( symbols[8] == 1)
						Matrix[1][2]++;
					if(symbols[4] == 1)
						Matrix[1][2]++;
				}
				if (Matrix[2][0] == 0) {
					if (symbols[0] + symbols[3] == 2 || symbols[7] + symbols[8] == 2 || symbols[4] + symbols[2] == 2)
						Matrix[2][0] += 20;
					else if (symbols[0] + symbols[3] == 8 || symbols[7] + symbols[8] == 8 || symbols[4] + symbols[2] == 8) 
						Matrix[2][0] += 100;
					if( symbols[3] == 1) 
						Matrix[2][0]++;
					if( symbols[7] == 1)
						Matrix[2][0]++;
					if(symbols[4] == 1)
						Matrix[2][0]++;
				}
				if (Matrix[2][1] == 0) {
					if (symbols[6] + symbols[8] == 2 || symbols[1] + symbols[4] == 2)
						Matrix[2][1] += 20;
					else if (symbols[6] + symbols[8] == 8 || symbols[1] + symbols[4] == 8)
						Matrix[2][1] += 100;
					if( symbols[6] == 1) 
						Matrix[2][1]++;
					if( symbols[8] == 1)
						Matrix[2][1]++;
					if(symbols[4] == 1)
						Matrix[2][1]++;
				}
				if (Matrix[2][2] == 0) {
					if (symbols[6] + symbols[7] == 2 || symbols[5] + symbols[2] == 2 || symbols[0] + symbols[4] == 2)
						Matrix[2][2] += 20;
					else if (symbols[6] + symbols[7] == 8 || symbols[5] + symbols[2] == 8 || symbols[0] + symbols[4] == 8)
						Matrix[2][2] += 100;
					if( symbols[5] == 1) 
						Matrix[2][2]++;
					if( symbols[7] == 1)
						Matrix[2][2]++;
					if(symbols[4] == 1)
						Matrix[2][2]++;
				}

			}
			int xVal = 0;
			int yVal = 0;
			for (int y = 0; y < 3; y++) {
				for (int x = 0; x < 3; x++) {
					if( Matrix[y][x] > Matrix[yVal][xVal]) {
						xVal = x;
						yVal = y;
					}
				}
			}
			switch(yVal) {
			case 0:
				if( xVal == 0)
					return 0;
				else if( xVal == 1 )
					return 1;
				else if ( xVal == 2)
					return 2;
				else
					return -1;
			case 1:
				if( xVal == 0)
					return 3;
				else if( xVal == 1 )
					return 4;
				else if ( xVal == 2)
					return 5;
				else
					return -1;
			case 2:
				if( xVal == 0)
					return 6;
				else if( xVal == 1 )
					return 7;
				else if ( xVal == 2)
					return 8;
				else
					return -1;
			default:
				return -1;
			}

		}
	}

	private class bAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			summary = "Welcome to Big Red Tic Tac Toe!!\n\nScoreboard: " + score[0] + " | " + score[1] + " | " + score[2] + "\n";
			for (int index = 0; index < sym.length; index++) {
				sym[index].setText(" ");
				symbols[index] = 0;
			}
			again.setVisible(false);
			lockClick = false;
			turnNum = 0;
			input.setText(summary);
			frame.repaint();
		}

	}
	public static void main(String[] args) {
		GUI game = new GUI();
		game.display();
	}
}
