import java.util.*;
import java.io.Console;

public class AlgorytmN {

  void naiwny(String pattern, String text){
    int m, n, i, j;
    n = text.length();
    m = pattern.length();
    i = 0;
    while (i <= n - m) {
        j = 0;
        while ((j < m) && (pattern.charAt(j) == text.charAt(i + j))) j++;
        if (j == m) System.out.println("Znaleziono wzorzec, indeks: " + i);;
        i++;
    }
  }

    public static void main(String[] args) {
      AlgorytmN n = new AlgorytmN();

        String text = "aaabbb";
        String pattern = "bb";

        n.naiwny(pattern,text);
    }
}
