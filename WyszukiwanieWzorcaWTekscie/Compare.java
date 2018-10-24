import java.util.*;
import java.io.*;

public class Compare{
  void KMPSearch(String pat, String txt)
    {
        int M = pat.length();
        int N = txt.length();

        // lps[] - przechowuje najdluzszy prefix suffix
        int lps[] = new int[M];
        int j = 0;

        computeLPSArray(pat,M,lps);

        int i = 0;  // index txt[]
        while (i < N)
        {
            if (pat.charAt(j) == txt.charAt(i))
            {
                j++;
                i++;
            }
            if (j == M)
            {
                System.out.println("Znaleziono wzorzec, indeks: " + (i-j));
                j = lps[j-1];
            }

            // mismatch after j matches
            else if (i < N && pat.charAt(j) != txt.charAt(i))
            {
                if (j != 0)
                    j = lps[j-1];
                else
                    i = i+1;
            }
        }
    }

    void computeLPSArray(String pat, int M, int lps[])
    {
        // dlugosc prefix suffix
        int len = 0;
        int i = 1;
        lps[0] = 0;  // lps[0] is always 0

        // the loop calculates lps[i] for i = 1 to M-1
        while (i < M)
        {
            if (pat.charAt(i) == pat.charAt(len))
            {
                len++;
                lps[i] = len;
                i++;
            }
            else
            {
                if (len != 0)
                {
                    len = lps[len-1];
                }
                else
                {
                    lps[i] = len;
                    i++;
                }
            }
        }
    }

  public final static int d = 256;

  static void searchKR(String pat, String txt, int q)
  {
      int M = pat.length();
      int N = txt.length();
      int i, j;
      int p = 0; // hash value for pattern
      int t = 0; // hash value for txt
      int h = 1;

      // The value of h would be "pow(d, M-1)%q"
      for (i = 0; i < M-1; i++)
          h = (h*d)%q;

      // licz hashe
      for (i = 0; i < M; i++)
      {
          p = (d*p + pat.charAt(i))%q;
          t = (d*t + txt.charAt(i))%q;
      }

      // Sprawdzaj pattern
      for (i = 0; i <= N - M; i++)
      {
          // sprawdzam hash
          if ( p == t )
          {
              // sprawdzam znaki
              for (j = 0; j < M; j++)
              {
                  if (txt.charAt(i+j) != pat.charAt(j))
                      break;
              }

              if (j == M)
                  System.out.println("Znaleziono wzorzec, indeks: " + i);
          }

          if ( i < N-M )
          {
              t = (d*(t - txt.charAt(i)*h) + txt.charAt(i+M))%q;

              if (t < 0)
              t = (t + q);
          }
      }
  }

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

  private static boolean isLetterOrDigit(char c) {
    return (c >= 'a' && c <= 'z') ||
           (c >= 'A' && c <= 'Z') ||
           (c >= '0' && c <= '9');
  }

  public static void main(String[] args) {
    Compare cmp = new Compare();

    String text = "";
    String pattern = "";

    BufferedReader reader = null;
    try {
        File file = new File("wzorzec.txt");
        reader = new BufferedReader(new FileReader(file));

        String line;
        while ((line = reader.readLine()) != null) {
          for (int i = 0, n = line.length(); i < n; i++) {
              char c = line.charAt(i);
              if (isLetterOrDigit(c)){
                pattern+="" + c;
              }
          }
          // pattern+=line;
        }

    } catch (IOException e) {
        e.printStackTrace();
    }

    reader = null;
    try {
        File file = new File("tekst.txt");
        reader = new BufferedReader(new FileReader(file));

        String line=null;
        while ((line = reader.readLine()) != null) {
          for (int i = 0, n = line.length(); i < n; i++) {
              char c = line.charAt(i);
              if (isLetterOrDigit(c)){
                text+="" + c;
              }
          }
          // text+=line;
        }

    } catch (IOException e) {
        e.printStackTrace();
    }

    //text = "aaabbabababababbbbabaaaabababbababababbbabbababababbb";
    //pattern = "abbababab";


    // System.out.println(pattern);
        // System.out.println(text);

    System.out.println("==========================");
    System.out.println("======== KMP ========");
    System.out.println("==========================");

    long start = System.currentTimeMillis();
    cmp.KMPSearch(pattern,text);
    long elapsedTime = System.currentTimeMillis() - start;
    System.out.println("Czas: "+elapsedTime);

    System.out.println("==========================");
    System.out.println("======== KR ========");
    System.out.println("==========================");


    int q = 101; // A prime number
    start = System.currentTimeMillis();
    searchKR(pattern, text, q);
    elapsedTime = System.currentTimeMillis() - start;
    System.out.println("Czas: "+elapsedTime);

    System.out.println("==========================");
    System.out.println("======== Naiwny ========");
    System.out.println("==========================");

    start = System.currentTimeMillis();
    cmp.naiwny(pattern,text);
    elapsedTime = System.currentTimeMillis() - start;
    System.out.println("Czas: "+elapsedTime);

    System.out.println("==========================");

  }
}
