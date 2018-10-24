
// Autor : Sebastian Piaścik
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.Scanner;

import javax.swing.*;

// Klasa punkt - wykorzystuje w tablicy histori
class Punkt implements Serializable {
	int i, j;

	Punkt() {
	}

	Punkt(int i, int j) {
		this.i = i;
		this.j = j;
	}
}

// Historia gry
class Historia implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int index = 0;
	Punkt[] historia = new Punkt[99];

	Historia() {
	}

	Historia(Punkt[] historia_v1) {
		historia = historia_v1;
	}

	public void wstawListe(Punkt[] historia_v1) {
		historia = historia_v1;
	}

	void dodajHistoria(int i, int j) {
		historia[index] = new Punkt(i, j);
		index++;
	}

	boolean szukaj(int a, int b) {
		for (int z = 0; z < index; z++) {
			if (historia[z].i == a && historia[z].j == b) {
				return true;
			}
		}
		return false;
	}

	void wypisz() {
		System.out.println("----");
		for (int z = 0; z < index; z++) {
			System.out.println("(" + historia[z].i + "," + historia[z].j + ")");
		}
		System.out.println("----");
	}

	void wyczysc() {
		historia = null;
		index = 0;
	}

	String wyswietl() {
		String x = "Historia: ";
		for (int z = 0; z < index; z++) {
			x += "(";
			x += Integer.toString(historia[z].i);
			x += ",";
			x += Integer.toString(historia[z].j);
			x += ")";
		}
		return x;
	}
}

// Model planszy
class Model {
	char tab[][] = new char[9][9];
}

// Klasa gry Niespodzianki
public class Niespodzianki extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int ileBombWszystkich = 0, ileBombWszystkich2 = 0;
	int wielkoscTablicy = 0;

	Model model = new Model();
	JButton tab[][] = new JButton[9][9];
	JPanel plansza = new JPanel();
	JPanel sterowanie = new JPanel();
	JTextField info = new JTextField(10), ruchy = new JTextField(10);
	JButton zapisz = new JButton("Zapisz historię"), start = new JButton("Restart gry"),
			cofnij = new JButton("Cofnij ruch"), wczytaj = new JButton("Wczytaj historię");
	JLabel label = new JLabel("Zaznaczone: ");

	Historia historia = new Historia();

	String serializeFileName = "historiaOperacji.ser";

	public Niespodzianki() {
		int i, j;
		Container cp = getContentPane();
		cp.setLayout(new GridLayout(1, 2));
		cp.add(plansza);
		cp.add(sterowanie);

		String fileName = "plansza1.txt";
		String line = null;
		int lineLength = 0;
		int lineAmount = 0;

//		Scanner scanner = new Scanner(System.in);
//		System.out.println("Wpisz nazwe pliku do wczytania: ");
//        	fileName=(scanner.next());

		try {
			FileReader fileReader = new FileReader(fileName);
			BufferedReader bufferedReader = new BufferedReader(fileReader);

			while ((line = bufferedReader.readLine()) != null) {
				lineLength = line.length();
				lineAmount++;
			}

			bufferedReader.close();
		} catch (FileNotFoundException ex) {
			System.out.println("Unable to open file '" + fileName + "'");
		} catch (IOException ex) {
			System.out.println("Error reading file '" + fileName + "'");
		}

		int szerokoscPlanszy = lineLength + 1;
		int wysokoscPlanszy = lineAmount + 1;

		System.out.println(szerokoscPlanszy + ", " + wysokoscPlanszy);

		sterowanie.setLayout(new GridLayout(wysokoscPlanszy,1));
		sterowanie.add(label);
		sterowanie.add(ruchy);
		sterowanie.add(info);
		start.addActionListener(new Start());
		sterowanie.add(start);
		cofnij.addActionListener(new Cofnij());
		sterowanie.add(cofnij);
		zapisz.addActionListener(new Zapisz());
		sterowanie.add(zapisz);
		wczytaj.addActionListener(new Wczytaj());
		sterowanie.add(wczytaj);
		info.setFont(info.getFont().deriveFont(18.0f));
		ruchy.setFont(ruchy.getFont().deriveFont(18.0f));
		label.setFont(ruchy.getFont().deriveFont(18.0f));
		plansza.setLayout(new GridLayout(szerokoscPlanszy, wysokoscPlanszy));

		int lineNumber = 1;
		int ileBomb = 0;
		char znak;
		char tablica[][];

		try {
			FileReader fileReader = new FileReader(fileName);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			line = bufferedReader.readLine();
			lineLength = line.length();
			wielkoscTablicy = lineLength + 1;
			System.out.println(lineLength);
			tablica = new char[lineLength + 1][lineLength + 1];
			for (j = 1; j < wielkoscTablicy; j++) {
				tablica[lineNumber][j] = line.charAt(j - 1);
			}
			lineNumber++;

			// Wczytuje plansze do tabeli
			while ((line = bufferedReader.readLine()) != null) {
				lineLength = line.length();
				for (j = 1; j < wielkoscTablicy; j++) {
					tablica[lineNumber][j] = line.charAt(j - 1);
				}
				lineNumber++;
			}

			// Licze bomby poziomo, tworze buttony
			for (i = 0; i < wielkoscTablicy; i++) {
				for (j = 0; j < wielkoscTablicy; j++) {
					znak = tablica[i][j];
					if (znak == '-') {
						tab[i][j] = new JButton("");
					} else if (znak == '0') {
						tab[i][j] = new JButton("");
						model.tab[i][j] = 'O';
						ileBomb++;
					} else if (znak == '1') {
						tab[i][j] = new JButton("" + Character.toString((char) 8601) + "");
						model.tab[i][j] = '-';
					} else if (znak == '2') {
						tab[i][j] = new JButton("" + Character.toString((char) 8595) + "");
						model.tab[i][j] = '-';
					} else if (znak == '3') {
						tab[i][j] = new JButton("" + Character.toString((char) 8600) + "");
						model.tab[i][j] = '-';
					} else if (znak == '4') {
						tab[i][j] = new JButton("" + Character.toString((char) 8592) + "");
						model.tab[i][j] = '-';
					} else if (znak == '6') {
						tab[i][j] = new JButton("" + Character.toString((char) 8594) + "");
						model.tab[i][j] = '-';
					} else if (znak == '7') {
						tab[i][j] = new JButton("" + Character.toString((char) 8598) + "");
						model.tab[i][j] = '-';
					} else if (znak == '8') {
						tab[i][j] = new JButton("" + Character.toString((char) 8593) + "");
						model.tab[i][j] = '-';
					} else if (znak == '9') {
						tab[i][j] = new JButton("" + Character.toString((char) 8599) + "");
						model.tab[i][j] = '-';
					} else {
						tab[i][j] = new JButton("=");
					}
				}
				tab[i][0].setText("" + ileBomb + "");
				ileBombWszystkich += ileBomb;
				ileBomb = 0;
			}
			// potrzebne do resetowania bomb
			ileBombWszystkich2 = ileBombWszystkich;
			info.setText("Ilosc bomb: " + ileBombWszystkich + "");

			// Licze bomby pionowo
			for (i = 0; i < wielkoscTablicy; i++) {
				for (j = 0; j < wielkoscTablicy; j++) {
					znak = tablica[j][i];
					if (znak == '0') {
						ileBomb++;
					}
				}
				tab[0][i].setText("" + ileBomb + "");
				ileBomb = 0;
			}

			// Dodaje buttony
			for (i = 0; i < wielkoscTablicy; i++) {
				for (j = 0; j < wielkoscTablicy; j++) {
					plansza.add(tab[i][j]);
					if (i > 0 && j > 0) {
						(tab[i][j]).addActionListener(new B(i, j));
					}
					if (i == 0 || j == 0) {
						(tab[i][j]).setBackground(Color.cyan);
						(tab[i][j]).setEnabled(false);
					}
					if(model.tab[i][j] == '-') {
						(tab[i][j]).setEnabled(false);
					}
				}
			}

			bufferedReader.close();
		} catch (FileNotFoundException ex) {
			System.out.println("Unable to open file '" + fileName + "'");
		} catch (IOException ex) {
			System.out.println("Error reading file '" + fileName + "'");
		}

		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	class B implements ActionListener {
		int i, j;
		int index = 0;

		B(int i, int j) {
			this.i = i;
			this.j = j;
		}

		public void actionPerformed(ActionEvent e) {
			if (historia.szukaj(i, j) != true) {
				historia.dodajHistoria(i, j);
				if (model.tab[i][j] == 'O') {
					ImageIcon bomb = new ImageIcon("saper.png");
					tab[i][j].setIcon(bomb);
					tab[i][j].setHorizontalAlignment(JButton.CENTER);
					tab[i][j].setVerticalAlignment(JButton.CENTER);
					ileBombWszystkich--;
					(tab[i][j]).setBackground(Color.green);
					if (ileBombWszystkich != 0) {
						info.setText("Brawo! Zostalo jeszcze " + ileBombWszystkich + " bomb");
					} else {
						info.setText("Wygrana!");
						for (i = 0; i < wielkoscTablicy; i++) {
							for (j = 0; j < wielkoscTablicy; j++) {
								if (i > 0 && j > 0) {
									(tab[i][j]).setEnabled(false);
								}
							}
						}
						ruchy.setEnabled(false);
					}
				} else {
					info.setText("Nie zgadles, zacznij od nowa");
					(tab[i][j]).setBackground(Color.red);
					for (i = 0; i < wielkoscTablicy; i++) {
						for (j = 0; j < wielkoscTablicy; j++) {
							if (i > 0 && j > 0) {
								(tab[i][j]).setEnabled(false);
							}
						}
					}
					ruchy.setEnabled(false);
				}
			}
			historia.wypisz();
			ruchy.setText(historia.wyswietl());
		}
	}

	void serializuj() {
		try {
			FileOutputStream f = new FileOutputStream(serializeFileName);
			ObjectOutputStream os = new ObjectOutputStream(f);
			os.writeObject(historia);
			os.flush();
			os.close();
			f.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	void odczytSerializacji() {
		System.out.println("####");
		try {
			FileInputStream f = new FileInputStream(serializeFileName);
			ObjectInputStream os = new ObjectInputStream(f);
			historia = (Historia) os.readObject();
			os.close();
			f.close();
			historia.wypisz();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		System.out.println("####");
		ileBombWszystkich = ileBombWszystkich2;
		for (int x = 0; x < historia.index; x++) {
			int i = historia.historia[x].i;
			int j = historia.historia[x].j;
			if (model.tab[i][j] == 'O') {
				ileBombWszystkich--;
				(tab[i][j]).setBackground(Color.green);
				ImageIcon bomb = new ImageIcon("saper.png");
				tab[i][j].setIcon(bomb);
				tab[i][j].setHorizontalAlignment(JButton.CENTER);
				tab[i][j].setVerticalAlignment(JButton.CENTER);
				if (ileBombWszystkich != 0) {
					info.setText("Brawo! Zostalo jeszcze " + ileBombWszystkich + " bomb");
				} else {
					info.setText("Wygrana!");
					for (i = 0; i < wielkoscTablicy; i++) {
						for (j = 0; j < wielkoscTablicy; j++) {
							if (i > 0 && j > 0) {
								// (tab[i][j]).setEnabled(false);
							}
						}
					}
					ruchy.setEnabled(false);
					historia.wyczysc();
				}
			} else {
				(tab[i][j]).setBackground(Color.red);
				for (i = 0; i < wielkoscTablicy; i++) {
					for (j = 0; j < wielkoscTablicy; j++) {
						if (i > 0 && j > 0) {
							(tab[i][j]).setEnabled(false);
						}
					}
				}
				ruchy.setEnabled(false);
				historia.wyczysc();
			}
		}
		ruchy.setText(historia.wyswietl());
	}

	class Cofnij implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (historia.index > 0) {
				info.setText("Cofniete");

				int i = historia.historia[historia.index - 1].i;
				int j = historia.historia[historia.index - 1].j;
				(tab[i][j]).setBackground(null);
				if (model.tab[i][j] == 'O') {
					ileBombWszystkich++;
					tab[i][j].setIcon(null);
				}

				historia.historia[historia.index - 1] = null;
				historia.index--;
			}
			historia.wypisz();
			ruchy.setText(historia.wyswietl());
			int i,j;
			for (i = 0; i < wielkoscTablicy; i++) {
				for (j = 0; j < wielkoscTablicy; j++) {
					if (i > 0 && j > 0) {
						(tab[i][j]).setEnabled(true);
					}
					if(model.tab[i][j] == '-') {
						(tab[i][j]).setEnabled(false);
					}
				}
			}
			ruchy.setEnabled(true);
		}
	}

	class Start implements ActionListener {
		int i, j;

		public void actionPerformed(ActionEvent e) {
			for (i = 0; i < wielkoscTablicy; i++) {
				for (j = 0; j < wielkoscTablicy; j++) {
					if (i > 0 && j > 0) {
						(tab[i][j]).setBackground(null);
						if(model.tab[i][j] == '-') {
							(tab[i][j]).setEnabled(false);
						} else {
							(tab[i][j]).setEnabled(true);
						}
					}
				}
			}
			ruchy.setEnabled(true);
			historia = new Historia();
			ileBombWszystkich = ileBombWszystkich2;
			info.setText("Ilosc bomb: " + ileBombWszystkich + "");
			ruchy.setText("");
		}
	}

	class Wczytaj implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// przechwycenie i obsluga wyjatku
			info.setText("Odczyt serializacji");
			odczytSerializacji();
		}
	}

	class Zapisz implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			serializuj();
			info.setText("Serializacja");
		}
	}

	public static void main(String[] args) {
		JFrame f = new Niespodzianki();
		f.setSize(900, 500);
		f.setLocation(100, 100);
		f.setVisible(true);
	}
}