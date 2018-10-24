package konto;


//Graficznego Interfejsu                                                                                     

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
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

public class GIdoObl2 extends JFrame {
	/**                                                                                                  
	 *                                                                                                   
	 */
	private static final long serialVersionUID = 633935003539591961L;

	Konto konto = new Konto();
	
	List<Integer> historia = new ArrayList<Integer>();
	List<Integer> historia2 = new ArrayList<Integer>();

	String serializeFileName = "historiaOperacji2.ser";

	JTextField stan = new JTextField(20), dzialanie = new JTextField(20), wynik = new JTextField(20);

	JButton obliczenia = new JButton("Wplata/Wyplata"), odblokuj = new JButton("Odblokuj"),
			cofnij = new JButton("Cofnij"), odczyt = new JButton("Odczyt serialize"), zapisz = new JButton("Zapisz dane");

	GIdoObl2() {
		dodajHistoria(0);
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
		odczyt.addActionListener(new Zapisz());
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

	void dodajHistoria(int stan) {
		historia.add(stan);
	}

	public void wypiszHistoria() {
		System.out.println("----");
		for (int j = 0; j < historia.size(); j++) {
			System.out.println(historia.get(j));
		}
		System.out.println("----");
	}

	public void wypiszHistoria2() {
		System.out.println("2----");
		for (int j = 0; j < historia2.size(); j++) {
			System.out.println(historia2.get(j));
		}
		System.out.println("2----");
	}

	void serializuj() {
		try {
			FileOutputStream f = new FileOutputStream(serializeFileName);
			ObjectOutputStream os = new ObjectOutputStream(f);
			// create ArrayList and inserts values
	        List<String> leadersOfHistory = new ArrayList<String>();
	 
	        // add values to ArrayList
	        leadersOfHistory.add("Joseph Stalin");
	        leadersOfHistory.add("Adolf Hitler");
	        leadersOfHistory.add("Benito Mussolini");
	        leadersOfHistory.add("Napoléon Bonaparte");
	        leadersOfHistory.add("Vladimir Putin");
	        leadersOfHistory.add("Fidel Castro");
	        leadersOfHistory.add("Robert Mugabe");
			
			os.writeObject(leadersOfHistory);
			os.close();
			f.close();
	         System.out.printf("Serialized data is saved in "+serializeFileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	void odczytSerializacji() {
		System.out.println("####");
		
		try {
			List<String> leadersOfHistory2 = null;
			
			FileInputStream f2 = new FileInputStream(serializeFileName);
			ObjectInputStream os2 = new ObjectInputStream(f2);
//			ObjectInputStream is = new ObjectInputStream(new FileInputStream(serializeFileName));
			leadersOfHistory2 = (ArrayList<String>) os2.readObject();
			konto.setStan(historia.get(historia.size() - 1));
			stan.setText(Integer.toString(konto.dajStan()));
			os2.close();
			f2.close();
			historia=historia2;

	         System.out.printf("Serialized data is loaded "+serializeFileName);
			for(String leader : leadersOfHistory2){
	            System.out.println(leader);
	        }
			wypiszHistoria();
			wypiszHistoria2();
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
				dodajHistoria(konto.dajStan());
				cofnij.setEnabled(true);
				wypiszHistoria();
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
			konto.setStan(historia.get(historia.size() - 2));
			historia.remove(historia.size() - 1);
			if(historia.size() <= 1) {
				cofnij.setEnabled(false);
			} else {
				cofnij.setEnabled(true);
			}
			wypiszHistoria();

			stan.setText(Integer.toString(konto.dajStan()));
			stan.setEnabled(false);
			dzialanie.setEnabled(false);
			wynik.setEnabled(false);
			obliczenia.setEnabled(false);
		}
	}

	class Odczyt implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			odczytSerializacji();
			stan.setEnabled(false);
			dzialanie.setEnabled(false);
			wynik.setEnabled(false);
			obliczenia.setEnabled(false);
			if(historia.size() <= 1) {
				cofnij.setEnabled(false);
			} else {
				cofnij.setEnabled(true);
			}
		}
	}

	class Zapisz implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			serializuj();
			wynik.setText("Serializacja");
		}
	}

	public static void main(String[] arg) {
		JFrame gi = new GIdoObl2();
		gi.setSize(800, 250);
	}
}
