package SortLiczby;

import java.io.*;
//A - tablica do sortowania                                                                                 
//B - wynik sortowania                                                                                      
//k - zakres waroœci w tablicy A                                                                            
//C - pomocnicza lablica "liczników"                                                                        
import java.util.Arrays;

class CountingSortNumbers {

	public static void countingSort(Integer A[], Integer B[], int k) {
		int C[] = new int[999];
		int i, j;

		Arrays.fill(C, 0);

		for (j = 0; j < A.length; j++) {
			C[A[j]]++;
		}

		for (i = 1; i <= k; i++) {
			C[i]+=C[i - 1];
		}

		for (j = A.length - 1; j >= 0; j--) {
			int indeks = A[j];
			B[C[indeks]-1] = A[j];
			C[indeks] -= 1;
		}

	}

	public static void main(String args[]) {
		Integer A[] = { 19, 2, 7, 8, 3, 5, 4, 8, 9, 10, 14, 16, 13, 5, 2, 5 };
		
		System.out.println();
		System.out.print(Arrays.toString(A));

		Integer B[] = new Integer[A.length];
		int maxValue = 20;

		countingSort(A, B, maxValue);
		System.out.println();
		System.out.print(Arrays.toString(B));

	}
}