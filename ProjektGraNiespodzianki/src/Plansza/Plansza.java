package Plansza;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class Model {
	char tab[][] = new char[8][8];
}

public class Plansza extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Model model = new Model();
	JButton tab[][] = new JButton[8][8];
	JPanel plansza = new JPanel();
	JPanel sterowanie = new JPanel();
	JTextField t = new JTextField(10);

	public Plansza() {
		int i, j;
		Container cp = getContentPane();
		cp.setLayout(new GridLayout(1, 2));
		cp.add(plansza);
		cp.add(sterowanie);
		sterowanie.setLayout(new GridLayout(8, 1));
		sterowanie.add(t);
		t.setFont(t.getFont().deriveFont(30.0f));
		plansza.setLayout(new GridLayout(8, 8));
		for (i = 0; i < 8; i++)
			for (j = 0; j < 8; j++) {
				tab[i][j] = new JButton("");
				if ((i + j) % 2 == 0)
					(tab[i][j]).setBackground(Color.black);
				plansza.add(tab[i][j]);
				(tab[i][j]).addActionListener(new B(i, j));
			}
		ImageIcon queen = new ImageIcon("queen.jpg");
		tab[1][1].setIcon(queen);
		model.tab[1][1] = 'Q';
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	class B implements ActionListener {
		int i, j;

		B(int i, int j) {
			this.i = i;
			this.j = j;
		}

		public void actionPerformed(ActionEvent e) {
			if (model.tab[i][j] == 'Q')
				t.setText(i + "," + j + " Hetman");
			else
				t.setText(i + "," + j);

		}
	}

	public static void main(String[] args) {
		JFrame f = new Plansza();
		f.setSize(600, 400);
		f.setLocation(100, 100);
		f.setVisible(true);
	}
}