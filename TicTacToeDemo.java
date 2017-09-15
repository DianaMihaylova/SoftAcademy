package projectTicTacToe;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class TicTacToeDemo {

	public static void main(String[] args) {
		
		String namePlayer1 = JOptionPane.showInputDialog(null, 
							"Please enter player 1 name", 
							"Name confirm", 
							JOptionPane.QUESTION_MESSAGE);
		
		String namePlayer2 = JOptionPane.showInputDialog(null, 
							"Please enter player 2 name", 
							"Name confirm", 
							JOptionPane.QUESTION_MESSAGE);
		
		Player player1 = new Player(namePlayer1, 'X');
		Player player2 = new Player(namePlayer2, 'O');
		
		JFrame frame = new JFrame("TIC TAC TOE");
		frame.setSize(600, 700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.add(new TicTacToePanel(player1, player2));
		frame.setVisible(true);
	}
}
