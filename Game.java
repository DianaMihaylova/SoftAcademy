package projectTicTacToe;

import java.io.Serializable;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class Game implements Serializable {
	
	private Player currentPlayer = null;
	private int scorePlayer1 = 0;
	private int scorePlayer2 = 0;
	private boolean gameOver = false;
	private Player player1;
	private Player player2;
	
	public Player getCurrentPlayer() {
		return currentPlayer;
	}
	
	public void setCurrentPlayer(Player currentPlayer) {
		this.currentPlayer = currentPlayer;
	}
	
	public int getScorePlayer1() {
		return scorePlayer1;
	}
	
	public void setScorePlayer1(int scorePlayer1) {
		this.scorePlayer1 = scorePlayer1;
	}
	
	public int getScorePlayer2() {
		return scorePlayer2;
	}
	
	public void setScorePlayer2(int scorePlayer2) {
		this.scorePlayer2 = scorePlayer2;
	}
	
	public boolean isGameOver() {
		return gameOver;
	}
	
	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}
	
	public Player getPlayer1() {
		return player1;
	}
	
	public void setPlayer1(Player player1) {
		this.player1 = player1;
	}
	
	public Player getPlayer2() {
		return player2;
	}
	
	public void setPlayer2(Player player2) {
		this.player2 = player2;
	}
}
