package RadixFile;

import java.io.*;

import java.util.Arrays; 
import java.util.stream.Collectors;   


public class RadixFile {
	
	static int largestLength = -1;
	
	
	static String[] radixSort(String A[], String B[], int k) {
		
		
//		System.out.println("Najdluzsza dlugosc: "+largestLength);
		
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
		
//		System.out.println("Wejscie: "+Arrays.toString(A));

		// zerowanie tablicy ilosci wystapien
		Arrays.fill(C, 0);

		// liczenie ilosci wystapien
		for (j = 0; j < A.length; j++) {
//			System.out.println("Indeks: "+getIndeks(A,j,digit)+" j: "+j+" znak: "+A[j].charAt(digit)+" digit: "+digit);
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

//		System.out.println("Wyjscie: "+Arrays.toString(B));
//		System.out.println();
		return B;
	}
	
	static void wyrownajDlugosci(String A[]) {
		for(String s : A) {
				int length = s.length();
				if (length >= largestLength) {
		            largestLength = length;
				}
		}
		for(int j = 0; j < A.length; j++) {
				int length = A[j].length();
				if (length < largestLength) {
					A[j]=String.format("%1$"+largestLength+ "s", A[j]);
				}
		}
	}

	
	
    public static void main(String [] args) {
    	
    	String A[] = new String[20000];
    	int index=0;

        String fileName = "nazwiska.txt";
        String line = null;
        int lineLength=0;
        String name = null;
        int nameStartIndex=0;
        
//      System.getProperty("user.dir");

        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
            	lineLength=line.length();
            	for(int i=0;i<=lineLength;i++) {
            		if(line.charAt(i) == ' ') {
            			nameStartIndex=i+1;
            			break;
            		}
            	}
            	name = line.substring(nameStartIndex);
            	A[index]=name;
            	index++;
            }   

            bufferedReader.close();         
        }
        catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '" + fileName + "'");                
        }
        catch(IOException ex) {
            System.out.println("Error reading file '" + fileName + "'");
        }

//        A = Arrays.stream(A).filter(s -> (s != null && s.length() > 0)).toArray(String[]::new); 
        int i=0;
        for(int j = 0; j < A.length; j++) {
            if(A[j] != null && A[j].length() > 0) {
            	A[i]=A[j];
            	A[i]=A[i].replaceAll("[^a-zA-Z0-9]", " ");
            	i++;
            }
         }

		wyrownajDlugosci(A);
//		System.out.print(Arrays.toString(A));
		
		int countLetters = 37; // ile liter, cyfr i spacja
		
		String B[] = new String[A.length];
		Arrays.fill(B, "0");

        System.out.println(A[15032].charAt(21));
		radixSort(A, B, countLetters);
		System.out.print(Arrays.toString(B));
        
    }
}