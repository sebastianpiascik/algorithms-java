
//import java.io.*;
import java.util.*;                                                                     

class RadixSort {
	
	
	static String[] radixSort(String A[], String B[], int k) {
		
		int largestLength = -1;
		for(String s : A) {
			int length = s.length();
			if (length >= largestLength) {
	            largestLength = length;
			}
		}
		System.out.println("Najdluzsza dlugosc: "+largestLength);
		
		for(int i=largestLength-1;i>=0;i--) {
			countingSort(A, B, k, i);
			A=B.clone();
		}
	
		return B;
		
	}
	
	
	static int getIndeks(String A[],int j,int digit) {
		int indeks;
		int decA = 97; //dec 'a' - zostawiamy miejsce na spacje na pozycji 0
		if(A[j].charAt(digit) >= 'a' && A[j].charAt(digit) <= 'z') {
			indeks = (int) A[j].charAt(digit) - decA + 11; // litery od 11 do 36
		} else if(A[j].charAt(digit) >= 'A' && A[j].charAt(digit) <= 'Z') {
			indeks = (int) A[j].charAt(digit) - decA + 43; // litery od 11 do 36
		} else if(A[j].charAt(digit) == ' ') {
			indeks = 0; // spacja w tablicy na pozycji 0
		} else {
			indeks = (int) A[j].charAt(digit) - 47;//0-asci(48) cyfry w tablicy na pozycjach od 1 do 10
		}
		return indeks;
	}
	

	static String[] countingSort(String A[], String B[], int k, int digit) {
		int C[] = new int[k];
		int i, j;
		
		System.out.println("Wejscie: "+Arrays.toString(A));

		// zerowanie tablicy ilosci wystapien
		Arrays.fill(C, 0);

		// liczenie ilosci wystapien
		for (j = 0; j < A.length; j++) {
			C[getIndeks(A,j,digit)]++;
		}

		// stworzenie tablicy wynikow
		for (i = 1; i < k; i++) {
			C[i] += C[i - 1];
		}

		// stworzenie wynikowej tablicy stringow
		for (j = A.length - 1; j >= 0; j--) {
			B[C[getIndeks(A,j,digit)] - 1] = A[j];
			C[getIndeks(A,j,digit)] -= 1;
		}

		System.out.println("Wyjscie: "+Arrays.toString(B));
		System.out.println();
		return B;
	}

	public static void main(String args[]) {
		String A[] = { "yn1owaa", " bmamab", "dzIadek", "Zratowa", "stryjek", "siostra", };
		System.out.print(Arrays.toString(A));
		
		int countLetters = 37; // ile liter, cyfr i spacja
		
		String B[] = new String[A.length];
		Arrays.fill(B, "0");

		System.out.println();
		radixSort(A, B, countLetters);
//		System.out.print(Arrays.toString(B));

	}
}