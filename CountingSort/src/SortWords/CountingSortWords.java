package SortWords;

import java.io.*;
import java.util.*;
//A - tablica do sortowania                                                                                 
//B - wynik sortowania                                                                                      
//k - zakres waroœci w tablicy A                                                                            
//C - pomocnicza lablica "liczników"                                                                        

class CountingSortWords {

	static void countingSort(String A[], String B[], int k) {
		int C[] = new int[26];
		int i, j;
		int decA = 97;

		// zerowanie tablicy ilosci wystapien
		Arrays.fill(C, 0);

		// liczenie ilosci wystapien
		for (j = 0; j < A.length; j++) {
			int indeks = (int) A[j].charAt(0) - decA;
			C[indeks]++;
		}

		// stworzenie tablicy wynikow
		for (i = 1; i < k; i++) {
			C[i] += C[i - 1];
		}

		// stworzenie wynikowej tablicy stringow
		for (j = A.length - 1; j >= 0; j--) {
			int indeks = (int) A[j].charAt(0) - decA;
			B[C[indeks] - 1] = A[j];
			C[indeks] -= 1;
		}

	}

	public static void main(String args[]) {
		String A[] = { "synowa", "mama", "abrat", "córka", "dziadek", "babcia", "syn", "bratowa", "ciocia", "dziecko",
				"teœciowa", "stryjek", "szwagier", "tata", "wujek", "teœæ", "siostra", "ziêæ" };
		System.out.print(Arrays.toString(A));

		int countLetters = 26; // ile liter
		String B[] = new String[A.length];
		Arrays.fill(B, "0");

		countingSort(A, B, countLetters);
		System.out.println();
		System.out.print(Arrays.toString(B));

	}
}