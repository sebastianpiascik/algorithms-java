
//Graficznego Interfejsu                                                                                     

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.ArrayList;
import java.util.List;

class DebetException extends Exception {
	/**                                                                                                  
	 *                                                                                                   
	 */
	private static final long serialVersionUID = 6235187224586774715L;
	int i;

	DebetException() {
		super();
	}

	DebetException(String msg) {
		super(msg);
	}

	DebetException(int i) {
		this.i = i;
	}
}

class Konto {
	private int stan;

	Konto() {
		stan = 0;
	}

	public void operacja(int ile) throws DebetException {
		if (stan + ile >= 0)
			stan += ile;
		else {
			int roznica = -stan - ile;
			throw new DebetException(": o " + roznica + " za duzo");
		}
	}

	public int dajStan() {
		return stan;
	}

	public void setStan(int n) {
		stan = n;
	}
}

class Historia implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int liczba;
	ArrayList<Integer> historia = new ArrayList<Integer>();

	// transient int x;
	Historia() {
		historia.add(0);
	}

	Historia(ArrayList<Integer> historia_v1) {
		historia = historia_v1;
	}

	public void wstawListe(ArrayList<Integer> historia_v1) {
		historia = historia_v1;
	}
	
	void dodajHistoria(int stan) {
		historia.add(stan);
	}

	public void wypisz() {
		System.out.println("----");
		for (int j = 0; j < historia.size(); j++) {
			System.out.println(historia.get(j));
		}
		System.out.println("----");
	}
}

public class GIdoObl extends JFrame {
	/**                                                                                                  
	 *                                                                                                   
	 */
	private static final long serialVersionUID = 633935003539591961L;

	Konto konto = new Konto();

	Historia historia = new Historia();

	String serializeFileName = "historiaOperacji.ser";

	JTextField stan = new JTextField(20), dzialanie = new JTextField(20), wynik = new JTextField(20);

	JButton obliczenia = new JButton("Wplata/Wyplata"), odblokuj = new JButton("Odblokuj"),
			cofnij = new JButton("Cofnij"), odczyt = new JButton("Odczyt serialize"), zapisz = new JButton("Zapisz dane");

	GIdoObl() {
		setTitle("Konto");
		Container cp = getContentPane();
		cp.setLayout(new GridLayout(3, 4, 15, 15));// liczba wierszy X liczba kolumn
		cp.add(new JLabel("Stan", SwingConstants.CENTER));
		cp.add(stan);
		stan.setText(Integer.toString(konto.dajStan()));
		obliczenia.addActionListener(new Oblicz());
		odblokuj.addActionListener(new Odblokuj());
		cofnij.addActionListener(new Cofnij());
		odczyt.addActionListener(new Odczyt());
		zapisz.addActionListener(new Zapisz());
		cp.add(wynik);
		cp.add(dzialanie);
		cp.add(obliczenia);
		wynik.setText("Rezultat");
		cp.add(odblokuj);
		cp.add(cofnij);
		cp.add(zapisz);
		cp.add(odczyt);
		cofnij.setEnabled(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
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
			konto.setStan(historia.historia.get(historia.historia.size() - 1));
			stan.setText(Integer.toString(konto.dajStan()));
			os.close();
			f.close();
			historia.wypisz();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		System.out.println("####");
	}

	int dajLiczbe(JTextField tf) {
		try {
			return Integer.parseInt(tf.getText());
		} catch (NumberFormatException e) {
			wynik.setText("Wprowadz liczbę");
			return 0;
		}
	}

	class Oblicz implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// przechwycenie i obsluga wyjatku
			try {
				wynik.setText("OK");
				konto.operacja(dajLiczbe(dzialanie));
				historia.dodajHistoria(konto.dajStan());
				cofnij.setEnabled(true);
				historia.wypisz();
			} catch (DebetException ev) {
				wynik.setText("BLAD" + ev.getMessage());
			}

			// wynik.setText(Integer.toString(konto.dajStan()));
			stan.setText(Integer.toString(konto.dajStan()));
			stan.setEnabled(false);
			dzialanie.setEnabled(false);
			wynik.setEnabled(false);
			obliczenia.setEnabled(false);
		}
	}

	class Odblokuj implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			stan.setEnabled(true);
			dzialanie.setEnabled(true);
			wynik.setEnabled(true);
			dzialanie.setText("");
			obliczenia.setEnabled(true);
		}
	}

	class Cofnij implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// przechwycenie i obsluga wyjatku
			wynik.setText("Cofniete");
			konto.setStan(historia.historia.get(historia.historia.size() - 2));
			historia.historia.remove(historia.historia.size() - 1);
//			historia.dodajHistoria(konto.dajStan());
			if(historia.historia.size() <= 1) {
				cofnij.setEnabled(false);
			} else {
				cofnij.setEnabled(true);
			}
			historia.wypisz();

			stan.setText(Integer.toString(konto.dajStan()));
			stan.setEnabled(false);
			dzialanie.setEnabled(false);
			wynik.setEnabled(false);
			obliczenia.setEnabled(false);
		}
	}

	class Odczyt implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// przechwycenie i obsluga wyjatku
			odczytSerializacji();
			stan.setEnabled(false);
			dzialanie.setEnabled(false);
			wynik.setEnabled(false);
			obliczenia.setEnabled(false);
			cofnij.setEnabled(false);
		}
	}

	class Zapisz implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			serializuj();
			wynik.setText("Serializacja");
		}
	}

	public static void main(String[] arg) {
		JFrame gi = new GIdoObl();
		gi.setSize(800, 250);
	}
}