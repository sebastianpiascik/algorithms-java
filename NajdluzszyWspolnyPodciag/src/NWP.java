import java.io.*;

import java.util.Arrays;
import java.util.stream.Collectors;

public class NWP {

    // Do not instantiate.
    public NWP() { }

    static void computeLCS(String x, String y,int m, int n,int[][] c,char[][] b) {

//		System.out.println("m: "+m+", n: "+n);
 
		for (int i = 0; i <= m; i++) {
			for (int j = 0; j <= n; j++) {
				c[i][j]=0;
			}
		}
		for (int i = 1; i <= m; i++) {
			for (int j = 1; j <= n; j++) {
				if(x.charAt(i-1) == y.charAt(j-1)) {
					c[i][j]=c[i-1][j-1]+1;
					b[i][j]='/';
				}
				else if(c[i-1][j] >= c[i][j-1]) {
					c[i][j]=c[i-1][j];
					b[i][j]='|';
				}
				else {
					c[i][j]=c[i][j-1];
					b[i][j]='-';
				}
			}
		}
//		for (int i = 0; i <= m; i++) {
//			for (int j = 0; j <= n; j++) {
//				System.out.print(c[i][j]);
//			}
//			System.out.println();
//		}
 
	}
    
    static void print(String x, String y,char[][] b,int i,int j) {
    	if(i==0 || j==0) {
    		return;
    	}
    	if(b[i][j] == '/') {
    		print(x,y,b,i-1,j-1);
    		System.out.print(x.charAt(i-1));
    	}
    	else if(b[i][j] == '|') {
    		print(x,y,b,i-1,j);
    	}
    	else {
    		print(x,y,b,i,j-1);
    	}
    }
    
    public static void main(String[] args) {
    	// Inicjalizacja napisow
        String string1 = "100101001";
        String string2 = "010110110";
        // Sprawdzenie dlugosci napisow
		int m = string1.length();
		int n = string2.length();
		// Inicjalizacja tablic 
		int[][] c = new int[m + 1][n + 1];
		char[][] b = new char[m + 1][n + 1];
		//Wywolanie funkcji
        computeLCS(string1,string2,m,n,c,b);
        print(string1,string2,b,m,n);
    }
}