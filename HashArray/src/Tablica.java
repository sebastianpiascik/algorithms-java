import java.io.*;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Tablica {
	static int m = 13; // wielkosc Tablicicy

	// haszowanie modularne
	static int h1(int k) {
		return k % m;
	}

	static int h2(int k) {
		return 1 + (k % (m - 2));
	}

	// adresowanie liniowe
	static int H(Integer A[], int k, int index) {
		return (h1(k) + index) % m;
	}

	// haszowanie dwukrotne
	static int H2(Integer A[], int k, int index) {
		return (h1(k) + index * index) % m;
	}

	static int H3(Integer A[], int k, int index) {
		return (h1(k) + index * h2(k)) % m;
	}

	static void funkcja(Integer A[], int k) {
		int index = 0;
		int adres = H(A, k, index);
		System.out.print("Liczba: " + k);
		System.out.print(" ,adres: " + adres);
		while (A[adres] != null) {
			index++;
			adres = H(A, k, index);
			System.out.print(" ,adres: " + adres);
		}
		A[adres] = k;
		System.out.println();
	}

	static int szukajFunkcja(Integer A[], int k) {
		int index = 0;
		int adres = H(A, k, index);
		while (A[adres] != null && A[adres] != k) {
			index++;
			adres = H(A, k, index);
		}
		if(A[adres] == null) {
			System.out.println("Szukam: " + k + " , nie znalazlem");
			return -1;
		} else {
			System.out.println("Szukam: " + k + " , znalazlem, adres: " + adres);
			return adres;
		}
	}

	static void usunFunkcja(Integer A[], int k) {
		int value = szukajFunkcja(A, k);
		if(value != -1) {
			A[value] = -1;
			System.out.println("Usunalem "+ k);
		} else {
			System.out.println("Nie usunalem " + k);
		}
	}

	static void funkcja2(Integer A[], int k) {
		int index = 0;
		int adres = H2(A, k, index);
		System.out.print("Liczba: " + k);
		System.out.print(" ,adres: " + adres);
		while (A[adres] != null) {
			index++;
			adres = H2(A, k, index);
			System.out.print(" ,adres: " + adres);
		}
		A[adres] = k;
		System.out.println();
	}

	static int szukajFunkcja2(Integer A[], int k) {
		int index = 0;
		int adres = H2(A, k, index);
		while (A[adres] != null && A[adres] != k) {
			index++;
			adres = H2(A, k, index);
		}
		if(A[adres] == null) {
			System.out.println("Szukam: " + k + " , nie znalazlem");
			return -1;
		} else {
			System.out.println("Szukam: " + k + " , znalazlem, adres: " + adres);
			return adres;
		}
	}

	static void usunFunkcja2(Integer A[], int k) {
		int value = szukajFunkcja2(A, k);
		if(value != -1) {
			A[value] = -1;
			System.out.println("Usunalem "+ k);
		} else {
			System.out.println("Nie usunalem " + k);
		}
	}

	static void funkcja3(Integer A[], int k) {
		int index = 0;
		int adres = H3(A, k, index);
		System.out.print("Liczba: " + k);
		System.out.print(" ,adres: " + adres);
		while (A[adres] != null) {
			index++;
			adres = H3(A, k, index);
			System.out.print(" ,adres: " + adres);
		}
		A[adres] = k;
		System.out.println();
	}

	static int szukajFunkcja3(Integer A[], int k) {
		int index = 0;
		int adres = H3(A, k, index);
		while (A[adres] != null && A[adres] != k) {
			index++;
			adres = H3(A, k, index);
		}
		if(A[adres] == null) {
			System.out.println("Szukam: " + k + " , nie znalazlem");
			return -1;
		} else {
			System.out.println("Szukam: " + k + " , znalazlem, adres: " + adres);
			return adres;
		}
	}

	static void usunFunkcja3(Integer A[], int k) {
		int value = szukajFunkcja3(A, k);
		if(value != -1) {
			A[value] = -1;
			System.out.println("Usunalem "+ k);
		} else {
			System.out.println("Nie usunalem " + k);
		}
	}

	public static void main(String[] args) {

		Integer A[] = new Integer[m];
		funkcja(A, 6);
		funkcja(A, 19);
		funkcja(A, 28);
		funkcja(A, 41);
		funkcja(A, 54);
		
		usunFunkcja(A, 41);
		szukajFunkcja(A, 54);

		System.out.println(Arrays.toString(A));

		Integer B[] = new Integer[m];
		funkcja2(B, 6);
		funkcja2(B, 19);
		funkcja2(B, 28);
		funkcja2(B, 41);
		funkcja2(B, 54);
		funkcja2(B, 67);
		
		usunFunkcja2(B, 54);
		szukajFunkcja2(B, 67);

		System.out.println(Arrays.toString(B));

		Integer C[] = new Integer[m];
		C[11]=50;
		C[4]=69;
		C[7]=72;
		C[1]=79;
		C[5]=98;
		funkcja3(C, 14);
		
		usunFunkcja3(C, 98);
		szukajFunkcja3(C, 14);

		System.out.println(Arrays.toString(C));
	}
}