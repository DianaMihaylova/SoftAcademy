package projectTicTacToe;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import lesson20.ObjectToSerialize;

public class TicTacToePanel extends JPanel implements Serializable {

	private Game game;
	public Board[][] board = new Board[3][3];
	JLabel infoLabel;
	JPanel gamePanel;
	JLabel labelPlayer1 = new JLabel();
	JLabel labelPlayer2 = new JLabel();

	public TicTacToePanel(Player player1, Player player2) {
		game = new Game();
		this.game.setPlayer1(player1);
		this.game.setPlayer2(player2);
		game.setCurrentPlayer(player1);

		JPanel northPanel = new JPanel();
		northPanel.setBackground(Color.pink);
		northPanel.setPreferredSize(new Dimension(600, 50));
		JButton newGame = new JButton("NEW GAME");
		newGame.setFont(new Font("Serif", Font.PLAIN, 15));
		newGame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				createNewGame();
			}
		});
		JButton exit = new JButton("EXIT");
		exit.setFont(new Font("Serif", Font.PLAIN, 15));
		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (JOptionPane.showConfirmDialog(null, 
								"Do you want to exit Tic Tac Toe?", 
								"Warning!",
								JOptionPane.YES_NO_OPTION) == 
								JOptionPane.YES_NO_OPTION) {
					System.exit(0);
				}
			}
		});
		infoLabel = new JLabel(player1.getName() + "'s turn!");
		infoLabel.setFont(new Font("Serif", Font.PLAIN, 20));
		infoLabel.setPreferredSize(new Dimension(300, 30));
		northPanel.add(newGame);
		northPanel.add(Box.createRigidArea(new Dimension(60, 20)));
		northPanel.add(infoLabel);
		northPanel.add(Box.createRigidArea(new Dimension(30, 20)));
		northPanel.add(exit);

		gamePanel = new JPanel(new GridLayout(3, 3, 0, 0));
		gamePanel.setPreferredSize(new Dimension(600, 500));
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				gamePanel.add(board[i][j] = new Board(player1, player2));
			}
		}
		gamePanel.setBorder(new LineBorder(Color.pink, 10));

		JPanel southPanel = new JPanel();
		southPanel.setBackground(Color.pink);
		southPanel.setPreferredSize(new Dimension(600, 200));
		labelPlayer1.setText(player1.getName() + " SCORE: " + game.getScorePlayer1());
		labelPlayer1.setFont(new Font("Serif", Font.PLAIN, 20));
		labelPlayer1.setPreferredSize(new Dimension(160, 50));
		labelPlayer2.setText(player2.getName() + " SCORE: " + game.getScorePlayer1());
		labelPlayer2.setFont(new Font("Serif", Font.PLAIN, 20));
		labelPlayer2.setPreferredSize(new Dimension(260, 50));
		JButton resetScore = new JButton("RESET SCORE");
		resetScore.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (JOptionPane.showConfirmDialog(null, 
								"Do you want to delete scores?", 
								"Warning!",
								JOptionPane.YES_NO_OPTION) == 
								JOptionPane.YES_NO_OPTION) {
					game.setScorePlayer1(0);
					game.setScorePlayer2(0);
					labelPlayer1.setText(player1.getName() + " SCORE: " + game.getScorePlayer1());
					labelPlayer2.setText(player2.getName() + " SCORE: " + game.getScorePlayer2());
				}
			}
		});
		resetScore.setFont(new Font("Serif", Font.PLAIN, 15));
		JMenuBar menubar = new JMenuBar();
		JMenu file = new JMenu("File");
		file.setFont(new Font("Serif", Font.PLAIN, 15));
		JMenuItem save = new JMenuItem("Save Game");
		save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
					saveGame();
					JOptionPane.showConfirmDialog(null, 
							"Saving was successful.", 
							"Game Saved",   
							JOptionPane.DEFAULT_OPTION);
			}
		});
		JMenuItem load = new JMenuItem("Load Game");
		load.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				loadGame();
				JOptionPane.showConfirmDialog(null, 
						"Loading was successful.", 
						"Game Load",   
						JOptionPane.DEFAULT_OPTION);
			}
		});
		file.add(save);
		file.add(load);
		menubar.add(file);
		southPanel.add(labelPlayer1);
		southPanel.add(Box.createRigidArea(new Dimension(220, 20)));
		southPanel.add(resetScore);
		southPanel.add(Box.createRigidArea(new Dimension(100, 20)));
		southPanel.add(labelPlayer2);
		southPanel.add(Box.createRigidArea(new Dimension(180, 20)));
		southPanel.add(menubar);

		add(northPanel, BorderLayout.NORTH);
		add(gamePanel, BorderLayout.CENTER);
		add(southPanel, BorderLayout.SOUTH);
	}

	public void createNewGame() {
		board = new Board[3][3];
		gamePanel.removeAll();
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				gamePanel.add(board[i][j] = new Board(game.getPlayer1(), game.getPlayer2()));
			}
		}
		game.setCurrentPlayer(game.getPlayer1());
		game.setGameOver(false);
		gamePanel.repaint();
		gamePanel.revalidate();
	}
	
	 public void saveGame(){

	        Game b = game;
	        JFileChooser chooser = new JFileChooser();
			chooser.setDialogTitle("Specify a file to save");
			int result = chooser.showSaveDialog(null);
			if (result == JFileChooser.APPROVE_OPTION) {
				File selectedFile = chooser.getSelectedFile();
				try {
					FileOutputStream fileOut = new FileOutputStream(selectedFile);
					ObjectOutputStream out = new ObjectOutputStream(fileOut);
					out.writeObject(b);

					out.close();
					fileOut.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
	 	}
	 
	 public void loadGame()
	    {
	        Game b = null;
	        JFileChooser chooser = new JFileChooser();
			int result = chooser.showOpenDialog(null);
			if (result == JFileChooser.APPROVE_OPTION) {
				File selectedFile = chooser.getSelectedFile();
				try {
					FileInputStream fileIn = new FileInputStream(selectedFile);
					ObjectInputStream in = new ObjectInputStream(fileIn);
					b = (Game)in.readObject();
            
					in.close();
					fileIn.close();
				} catch (ClassNotFoundException e) {            
					e.printStackTrace();
				} catch(FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				game = b;
				repaint();
				revalidate();
			}
	    }


	public boolean isFullBoard() {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				if (board[i][j].getSymbol() == ' ') {
					return false;
				}
			}
		}
		return true;
	}

	public boolean checkWin(char symbol) {

		// check rows:
		for (int i = 0; i < board.length; i++) {
			if (board[i][0].getSymbol() == symbol && board[i][1].getSymbol() == symbol
					&& board[i][2].getSymbol() == symbol) {
				return true;
			}
		}

		// check cols:
		for (int j = 0; j < board.length; j++) {
			if (board[0][j].getSymbol() == symbol && board[1][j].getSymbol() == symbol
					&& board[2][j].getSymbol() == symbol) {
				return true;
			}
		}

		// check diagonals:
		if ((board[0][0].getSymbol() == symbol && board[1][1].getSymbol() == symbol
				&& board[2][2].getSymbol() == symbol)
				|| (board[0][2].getSymbol() == symbol) && board[1][1].getSymbol() == symbol
						&& board[2][0].getSymbol() == symbol) {
			return true;
		}
		return false;
	}

	private class Board extends JPanel {

		private char symbol = ' ';
		Player player1;
		Player player2;

		public Board(Player player1, Player player2) {
			this.player1 = player1;
			this.player2 = player2;
			setBackground(Color.black);
			setBorder(new LineBorder(Color.pink, 3));
			addMouseListener(new MyMouseListener());
		}

		public char getSymbol() {
			return symbol;
		}

		public void setSymbol(char c) {
			symbol = c;
			repaint();
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			if (symbol == 'X') {
				Graphics2D g2 = (Graphics2D) g;
				g2.setStroke(new BasicStroke(10));
				g.setColor(Color.pink);
				g.drawLine(30, 30, getWidth() - 30, getHeight() - 30);
				g.setColor(Color.pink);
				g.drawLine(getWidth() - 30, 30, 30, getHeight() - 30);
			} else if (symbol == 'O') {
				Graphics2D g2 = (Graphics2D) g;
				g2.setStroke(new BasicStroke(10));
				g.setColor(Color.magenta);
				g.drawOval(30, 30, getWidth() - 50, getHeight() - 50);
			}
		}

		private class MyMouseListener extends MouseAdapter {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (game.isGameOver()) {
					return;
				}

				// if the board is empty and game is not over
				if (symbol == ' ' && game.getCurrentPlayer() != null) {
					setSymbol(game.getCurrentPlayer().getMarkOfGame());
				}

				// check game status
				if (game.getCurrentPlayer() != null) {
					if (checkWin(game.getCurrentPlayer().getMarkOfGame())) {
						if (game.getCurrentPlayer() == player1) {
							game.setScorePlayer1(game.getScorePlayer1() + 1);
							infoLabel.setText("Game over! \n" + player1.getName() + " win!");
							labelPlayer1.setText(player1.getName() + " SCORE: " + game.getScorePlayer1());
							game.setCurrentPlayer(null);
							game.setGameOver(true);
						} else {
							game.setScorePlayer2(game.getScorePlayer2() + 1);
							infoLabel.setText("Game over! \n" + player2.getName() + " win!");
							labelPlayer2.setText(player2.getName() + " SCORE: " + game.getScorePlayer2());
							game.setCurrentPlayer(null);
							game.setGameOver(true);
						}
					} else if (isFullBoard()) {
						infoLabel.setText("Game over! Nobody's win!");
						game.setCurrentPlayer(null);
						game.setGameOver(true);
					} else {
						game.setCurrentPlayer(game.getCurrentPlayer() == game.getPlayer1() ? game.getPlayer2() : game.getPlayer1());
						infoLabel.setText(game.getCurrentPlayer().getName() + "'s turn.");
					}
				}
			}
		}
	}
}
