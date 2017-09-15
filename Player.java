package projectTicTacToe;

import java.io.Serializable;

public class Player implements Serializable{
	
	private String name;
	private char markOfGame;
	
	public Player(String name, char markOfGame) {
		this.setName(name);
		this.setMarkOfGame(markOfGame);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public char getMarkOfGame() {
		return markOfGame;
	}

	public void setMarkOfGame(char markOfGame) {
		this.markOfGame = markOfGame;
	}
}