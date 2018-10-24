package hashArray3;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

class Pole {
	String slowo;
	int liczba;

	Pole() {
	}

	Pole(String slowo, int liczba) {
		this.slowo = slowo;
		this.liczba = liczba;
	}
}

public class Tablica3 {
	static int m = 2100; // wielkosc Tablicicy
	static int kolizje = 1;


	static int wartoscSlowa(String slowo) {
		int value = 0;
		for (int j = 0; j < slowo.length(); j++) {
			value = Math.abs((value + (int) slowo.charAt(j)) * 2);
		}
		return value;
	}
	// haszowanie modularne
	static int h1(int k) {
		return k % m;
	}

	static int h2(int k) {
		return 1 + (k % (m - 2));
	}

	// adresowanie liniowe
	static int H(Pole A[], int k, int index) {
		return (h1(k) + index) % m;
	}

	static int H2(Pole A[], int k, int index) {
		return (h1(k) + index * index) % m;
	}

	// haszowanie dwukrotne
	static int H3(Pole A[], int k, int index) {
		return (h1(k) + index * h2(k)) % m;
	}

	static int wstaw(Pole A[], int k, Pole x) {
		int index = 0;
		int adres = H(A, k, index);
		while (A[adres] != null && A[adres].slowo != "null") {
			index++;
			adres = H(A, k, index);
			kolizje++;
			if (index >= m) {
//				System.out.println("Blad, brak miejsca");
				return 0;
			}
		}
		A[adres] = x;
		return 1;
	}
	
	static int wstaw2(Pole A[], int k, Pole x) {
		int index = 0;
		int adres = H2(A, k, index);
		while (A[adres] != null && A[adres].slowo != "null") {
			index++;
			adres = H2(A, k, index);
			kolizje++;
			if (index >= m) {
//				System.out.println("Blad, brak miejsca");
				return 0;
			}
		}
		A[adres] = x;
		return 1;
		
//		int index =0;
//		int adres = H2(A, k, index);
//		do {
//			adres = H2(A, k, index);
//			if((A[adres] == null || A[adres].slowo == "null")) {
//				A[adres]=x;
//				return adres;
//			}
//			index++;
//			kolizje++;
//			
//		} while(index < m);
//		return 0;
	}

	static int wstaw3(Pole A[], int k, Pole x) {
		int index = 0;
		int adres = H3(A, k, index);
//		System.out.println("k: " + k + ", adres: " + adres);
		while (A[adres] != null && A[adres].slowo != "null") {
			index++;
			adres = H3(A, k, index);
//			System.out.print(", adres: " + adres);
			kolizje++;
			if (index >= m) {
//				System.out.println("Blad, brak miejsca");
				return 0;
			}
		}
		A[adres] = x;
		return 1;
	}

	static int szukaj(Pole A[], String x) {
		kolizje = 1;
		int k = wartoscSlowa(x);
		int index = 0;
		int adres = H(A, k, index);
		while (A[adres] != null && A[adres].slowo != x) {
			index++;
			adres = H(A, k, index);
			kolizje++;
		}
		if (A[adres] == null) {
			System.out.println("Szukam: " + x + " , nie znalazlem, ilosc prob: " + kolizje);
			return -1;
		} else {
			System.out.println("Szukam: " + x + " , znalazlem, adres: " + adres + ", ilosc prob: " + kolizje);
			return adres;
		}
	}

	static int szukaj2(Pole A[], String x) {
		kolizje = 1;
		int k = wartoscSlowa(x);
		int index = 0;
		int adres = H2(A, k, index);
		while (A[adres] != null && A[adres].slowo != x) {
			index++;
			adres = H2(A, k, index);
			kolizje++;
		}
		if (A[adres] == null) {
			System.out.println("Szukam: " + x + " , nie znalazlem, ilosc prob: " + kolizje);
			return -1;
		} else {
			System.out.println("Szukam: " + x + " , znalazlem, adres: " + adres + ", ilosc prob: " + kolizje);
			return adres;
		}
	}

	static int szukaj3(Pole A[], String x) {
		kolizje = 1;
		int k = wartoscSlowa(x);
		int index = 0;
		int adres = H3(A, k, index);
		while (A[adres] != null && A[adres].slowo != x) {
			index++;
			adres = H3(A, k, index);
			kolizje++;
		}
		if (A[adres] == null) {
			System.out.println("Szukam: " + x + " , nie znalazlem, ilosc prob: " + kolizje);
			return -1;
		} else {
			System.out.println("Szukam: " + x + " , znalazlem, adres: " + adres + ", ilosc prob: " + kolizje);
			return adres;
		}
	}

	static int usunszukaj(Pole A[], String x) {
		kolizje = 1;
		int k = wartoscSlowa(x);
		int index = 0;
		int adres = H(A, k, index);
		while (A[adres] != null && A[adres].slowo != x) {
			index++;
			adres = H(A, k, index);
			kolizje++;
		}
		if (A[adres] == null) {
//			System.out.println("Szukam: " + x + " , nie znalazlem, ilosc prob: " + kolizje);
			return -1;
		} else {
//			System.out.println("Szukam: " + x + " , znalazlem, adres: " + adres + ", ilosc prob: " + kolizje);
			return adres;
		}
	}

	static int usunszukaj2(Pole A[], String x) {
		kolizje = 1;
		int k = wartoscSlowa(x);
		int index = 0;
		int adres = H2(A, k, index);
		while (A[adres] != null && A[adres].slowo != x) {
			index++;
			adres = H2(A, k, index);
			kolizje++;
		}
		if (A[adres] == null) {
//			System.out.println("Szukam: " + x + " , nie znalazlem, ilosc prob: " + kolizje);
			return -1;
		} else {
//			System.out.println("Szukam: " + x + " , znalazlem, adres: " + adres + ", ilosc prob: " + kolizje);
			return adres;
		}
	}

	static int usunszukaj3(Pole A[], String x) {
		kolizje = 1;
		int k = wartoscSlowa(x);
		int index = 0;
		int adres = H3(A, k, index);
		while (A[adres] != null && A[adres].slowo != x) {
			index++;
			adres = H3(A, k, index);
			kolizje++;
		}
		if (A[adres] == null) {
//			System.out.println("Szukam: " + x + " , nie znalazlem, ilosc prob: " + kolizje);
			return -1;
		} else {
//			System.out.println("Szukam: " + x + " , znalazlem, adres: " + adres + ", ilosc prob: " + kolizje);
			return adres;
		}
	}

	static void usun(Pole A[], String x) {
		int value = usunszukaj(A, x);
		if (value != -1) {
			A[value] = new Pole("null", -1);
//			System.out.println("Usunalem " + x);
		} else {
//			System.out.println("Nie usunalem " + x);
		}
	}
	
	static void usun2(Pole A[], String x) {
		int value = usunszukaj2(A, x);
		if (value != -1) {
			A[value] = new Pole("null", -1);
//			System.out.println("Usunalem " + x);
		} else {
//			System.out.println("Nie usunalem " + x);
		}
	}

	static void usun3(Pole A[], String x) {
		int value = usunszukaj3(A, x);
		if (value != -1) {
			A[value] = new Pole("null", -1);
//			System.out.println("Usunalem " + x);
		} else {
//			System.out.println("Nie usunalem " + x);
		}
	}
	
	
	
	
	

	public static void main(String[] args) {

		String slowa[] = new String[9999];
		Integer liczby[] = new Integer[9999];

		String fileName = "nazw.txt";
		String line = null;

		int index = 0;
		int nameStartIndex = 0, lineLength, liczbaStartIndex = 0;
		int iloscKolizji = 0, iloscWstawionych=0;
		double sredniaIloscKolizji = 0;

		String name;
		String liczba;

		int value = 0;
		int iloscWartosci = 0, iloscUsunietych = 0;
		;

		// wczytywanie wartosci
		try {
			FileReader fileReader = new FileReader(fileName);
			BufferedReader bufferedReader = new BufferedReader(fileReader);

			while ((line = bufferedReader.readLine()) != null) {
				lineLength = line.length();
				for (int i = 0; i <= lineLength; i++) {
					if (line.charAt(i) == ' ') {
						nameStartIndex = i + 1;
						liczbaStartIndex = i - 1;
						break;
					}
				}
				name = line.substring(nameStartIndex);
				liczba = line.substring(0, liczbaStartIndex);
				slowa[index] = name;
				liczby[index] = Integer.parseInt(liczba);
				index++;
				iloscWartosci++;
			}

			bufferedReader.close();
		} catch (FileNotFoundException ex) {
			System.out.println("Unable to open file '" + fileName + "'");
		} catch (IOException ex) {
			System.out.println("Error reading file '" + fileName + "'");
		}

		// System.out.println(Arrays.toString(slowa));
		// System.out.println(Arrays.toString(liczby));

		Integer wartosci[] = new Integer[iloscWartosci];
		Pole[] tablica = new Pole[m];
		Pole[] tablica2 = new Pole[m];
		Pole[] tablica3 = new Pole[m];

		// tablica wartosci nazwisk-slow
		for (int i = 0; i < iloscWartosci; i++) {
			value = wartoscSlowa(slowa[i]);
			wartosci[i] = value;
			value = 0;
		}

		// System.out.println(Arrays.toString(wartosci));
		
		

		System.out.println();
		System.out.println();
		System.out.println("Adresowanie otwarte liniowe");
		System.out.println();
		System.out.println();
		for (int i = 0; i < m; i++) {
			tablica[i] = null;
		}
		
		

		System.out.println("Wstawiam 50% tablicy");
		// wstawiam do tablicy
		for (int i = 0; i < m / 2; i++) {
			wstaw(tablica, wartosci[i], new Pole(slowa[i], liczby[i]));
			iloscKolizji += kolizje;
			kolizje = 1;
			iloscWstawionych++;
		}

		sredniaIloscKolizji = (double) iloscKolizji / (double) iloscWstawionych;
		System.out.println("Srednia ilosc kolizji przy wstawianiu: " + sredniaIloscKolizji);
		iloscKolizji = 1;
		iloscWstawionych=0;

		// zeruje tablice
		for (int i = 0; i < m; i++) {
			tablica[i] = null;
		}
		
		System.out.println("Wstawiam 70% tablicy");
		// wstawiam do tablicy
		for (int i = 0; i < m*0.7; i++) {
			wstaw(tablica, wartosci[i], new Pole(slowa[i], liczby[i]));
			iloscKolizji += kolizje;
			kolizje = 1;
			iloscWstawionych++;
		}

		sredniaIloscKolizji = (double) iloscKolizji / (double) iloscWstawionych;
		System.out.println("Srednia ilosc kolizji przy wstawianiu: " + sredniaIloscKolizji);
		iloscKolizji = 1;
		iloscWstawionych=0;
		
		
		// zeruje tablice
		for (int i = 0; i < m; i++) {
			tablica[i] = null;
		}
		
		System.out.println("Wstawiam 90% tablicy");
		// wstawiam do tablicy
		for (int i = 0; i < m*0.9; i++) {
			wstaw(tablica, wartosci[i], new Pole(slowa[i], liczby[i]));
			iloscKolizji += kolizje;
			kolizje = 1;
			iloscWstawionych++;
		}

		sredniaIloscKolizji = (double) iloscKolizji / (double) iloscWstawionych;
		System.out.println("Srednia ilosc kolizji przy wstawianiu: " + sredniaIloscKolizji);
		iloscKolizji = 1;
		iloscWstawionych=0;
		
		
		
		System.out.println();
		System.out.println();
		
		
		// szukam
		for (int i = m - 1; i > m - 20; i--) {
			if (tablica[i] != null) {
				szukaj(tablica, tablica[i].slowo);
			} else {
				// System.out.println("Tu jest null");
			}
		}
		

		System.out.println();
		System.out.println();
		
		

		// zeruje tablice
		for (int i = 0; i < m; i++) {
			tablica[i] = null;
		}
		System.out.println("Wstawiam 80% tablicy");
		// wstawiam do tablicy
		for (int i = 0; i < m*0.8; i++) {
			wstaw(tablica, wartosci[i], new Pole(slowa[i], liczby[i]));
			kolizje = 1;
		}

		System.out.println("Usuwam polowe");
		for (int i = 0; i < m/2; i++) {
			if (tablica[i] != null) {
				usun(tablica,tablica[i].slowo);
			}
		}
		System.out.println("Wstawiam inne");
		for (int i =(int) ((int) m*0.8); i < m*1.2; i++) {
			wstaw(tablica, wartosci[i], new Pole(slowa[i], liczby[i]));
			kolizje = 1;
		}

		// licze ile usunietych
		for (int i = 0; i < m; i++) {
			if (tablica[i] != null) {
				if (tablica[i].slowo == "null") {
					iloscUsunietych++;
				}
			}
		}
		System.out.println("Ilosc usunietych: " + iloscUsunietych);
		iloscUsunietych = 0;
		
		
		
		
		
		

		System.out.println();
		System.out.println();
		System.out.println("Adresowanie otwarte kwadratowe");
		System.out.println();
		System.out.println();
		
		

		System.out.println("Wstawiam 50% tablicy");
		// wstawiam do tablicy
		for (int i = 0; i < m / 2; i++) {
			wstaw2(tablica2, wartosci[i], new Pole(slowa[i], liczby[i]));
			iloscKolizji += kolizje;
			kolizje = 1;
			iloscWstawionych++;
		}

		sredniaIloscKolizji = (double) iloscKolizji / (double) iloscWstawionych;
		System.out.println("Srednia ilosc kolizji przy wstawianiu: " + sredniaIloscKolizji);
		iloscKolizji = 1;
		iloscWstawionych=0;

		// zeruje tablice
		for (int i = 0; i < m; i++) {
			tablica2[i] = null;
		}
		
		System.out.println("Wstawiam 70% tablicy");
		// wstawiam do tablicy
		for (int i = 0; i < m*0.7; i++) {
			wstaw2(tablica2, wartosci[i], new Pole(slowa[i], liczby[i]));
			iloscKolizji += kolizje;
			kolizje = 1;
			iloscWstawionych++;
		}

		sredniaIloscKolizji = (double) iloscKolizji / (double) iloscWstawionych;
		System.out.println("Srednia ilosc kolizji przy wstawianiu: " + sredniaIloscKolizji);
		iloscKolizji = 1;
		iloscWstawionych=0;
		
		
		// zeruje tablice
		for (int i = 0; i < m; i++) {
			tablica2[i] = null;
		}
		
		System.out.println("Wstawiam 90% tablicy");
		// wstawiam do tablicy
		for (int i = 0; i < m*0.9; i++) {
			wstaw2(tablica2, wartosci[i], new Pole(slowa[i], liczby[i]));
			iloscKolizji += kolizje;
			kolizje = 1;
			iloscWstawionych++;
		}

		sredniaIloscKolizji = (double) iloscKolizji / (double) iloscWstawionych;
		System.out.println("Srednia ilosc kolizji przy wstawianiu: " + sredniaIloscKolizji);
		iloscKolizji = 1;
		iloscWstawionych=0;
		
		
		
		System.out.println();
		System.out.println();
		
		
		// szukam
		for (int i = m - 1; i > m - 20; i--) {
			if (tablica2[i] != null) {
				szukaj2(tablica2, tablica2[i].slowo);
			} else {
				// System.out.println("Tu jest null");
			}
		}
		

		System.out.println();
		System.out.println();
		
		

		// zeruje tablice
		for (int i = 0; i < m; i++) {
			tablica2[i] = null;
		}
		System.out.println("Wstawiam 80% tablicy");
		// wstawiam do tablicy
		for (int i = 0; i < m*0.8; i++) {
			wstaw2(tablica2, wartosci[i], new Pole(slowa[i], liczby[i]));
			kolizje = 1;
		}

		System.out.println("Usuwam polowe");
		for (int i = 0; i < m/2; i++) {
			if (tablica2[i] != null) {
				usun2(tablica2,tablica2[i].slowo);
			}
		}
		System.out.println("Wstawiam inne");
		for (int i =(int) ((int) m*0.8); i < m*1.2; i++) {
			wstaw2(tablica2, wartosci[i], new Pole(slowa[i], liczby[i]));
			kolizje = 1;
		}

		// licze ile usunietych
		for (int i = 0; i < m; i++) {
			if (tablica2[i] != null) {
				if (tablica2[i].slowo == "null") {
					iloscUsunietych++;
				}
			}
		}
		System.out.println("Ilosc usunietych: " + iloscUsunietych);
		iloscUsunietych = 0;
		
		
		
		

		
		
		

		System.out.println();
		System.out.println();
		System.out.println("Adresowanie otwarte dwukrotne");
		System.out.println();
		System.out.println();
		
		

		System.out.println("Wstawiam 50% tablicy");
		// wstawiam do tablicy
		for (int i = 0; i < m / 2; i++) {
			wstaw3(tablica3, wartosci[i], new Pole(slowa[i], liczby[i]));
			iloscKolizji += kolizje;
			kolizje = 1;
			iloscWstawionych++;
		}

		sredniaIloscKolizji = (double) iloscKolizji / (double) iloscWstawionych;
		System.out.println("Srednia ilosc kolizji przy wstawianiu: " + sredniaIloscKolizji);
		iloscKolizji = 1;
		iloscWstawionych=0;

		// zeruje tablice
		for (int i = 0; i < m; i++) {
			tablica3[i] = null;
		}
		
		System.out.println("Wstawiam 70% tablicy");
		// wstawiam do tablicy
		for (int i = 0; i < m*0.7; i++) {
			wstaw3(tablica3, wartosci[i], new Pole(slowa[i], liczby[i]));
			iloscKolizji += kolizje;
			kolizje = 1;
			iloscWstawionych++;
		}

		sredniaIloscKolizji = (double) iloscKolizji / (double) iloscWstawionych;
		System.out.println("Srednia ilosc kolizji przy wstawianiu: " + sredniaIloscKolizji);
		iloscKolizji = 1;
		iloscWstawionych=0;
		
		
		// zeruje tablice
		for (int i = 0; i < m; i++) {
			tablica3[i] = null;
		}
		
		System.out.println("Wstawiam 90% tablicy");
		// wstawiam do tablicy
		for (int i = 0; i < m*0.9; i++) {
			wstaw3(tablica3, wartosci[i], new Pole(slowa[i], liczby[i]));
			iloscKolizji += kolizje;
			kolizje = 1;
			iloscWstawionych++;
		}

		sredniaIloscKolizji = (double) iloscKolizji / (double) iloscWstawionych;
		System.out.println("Srednia ilosc kolizji przy wstawianiu: " + sredniaIloscKolizji);
		iloscKolizji = 1;
		iloscWstawionych=0;
		
		
		
		System.out.println();
		System.out.println();
		
		
		// szukam
		for (int i = m - 1; i > m - 20; i--) {
			if (tablica3[i] != null) {
				szukaj3(tablica3, tablica[i].slowo);
			} else {
				// System.out.println("Tu jest null");
			}
		}
		

		System.out.println();
		System.out.println();
		
		

		// zeruje tablice
		for (int i = 0; i < m; i++) {
			tablica3[i] = null;
		}
		System.out.println("Wstawiam 80% tablicy");
		// wstawiam do tablicy
		for (int i = 0; i < m*0.8; i++) {
			wstaw3(tablica3, wartosci[i], new Pole(slowa[i], liczby[i]));
			kolizje = 1;
		}

		System.out.println("Usuwam polowe");
		for (int i = 0; i < m/2; i++) {
			if (tablica3[i] != null) {
				usun3(tablica3,tablica3[i].slowo);
			}
		}
		System.out.println("Wstawiam inne");
		for (int i =(int) ((int) m*0.8); i < m*1.2; i++) {
			wstaw3(tablica3, wartosci[i], new Pole(slowa[i], liczby[i]));
			kolizje = 1;
		}

		// licze ile usunietych
		for (int i = 0; i < m; i++) {
			if (tablica3[i] != null) {
				if (tablica3[i].slowo == "null") {
					iloscUsunietych++;
				}
			}
		}
		System.out.println("Ilosc usunietych: " + iloscUsunietych);
		iloscUsunietych = 0;
		

	}
}
