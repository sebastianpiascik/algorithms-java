import java.util.*;

public class KMP2{
  int search(String pattern, String text) {
    int[] lsp = computeLspTable(pattern);

    int j = 0;  // Number of chars matched in pattern
    for (int i = 0; i < text.length(); i++) {
        if(text.charAt(i) != '?'){
          while (j > 0 && text.charAt(i) != pattern.charAt(j)) {
              // Fall back in the pattern
              j = lsp[j - 1];  // Strictly decreasing
          }
        }
        if (text.charAt(i) == pattern.charAt(j)) {
            // Next char matched, increment position
            j++;
            if (j == pattern.length())
                return i - (j - 1);
        } else if(text.charAt(i) != pattern.charAt(j) && text.charAt(i) == '?'){
            j++;
            if (j == pattern.length())
                return i - (j - 1);
        }
    }

    return -1;  // Not found
  }
  int[] computeLspTable(String pattern) {
      int[] lsp = new int[pattern.length()];
      lsp[0] = 0;  // Base case
      for (int i = 1; i < pattern.length(); i++) {
          // Start by assuming we're extending the previous LSP
          int j = lsp[i - 1];
          while (j > 0 && pattern.charAt(i) != pattern.charAt(j))
              j = lsp[j - 1];
          if (pattern.charAt(i) == pattern.charAt(j))
              j++;
          lsp[i] = j;
      }
      return lsp;
  }

  public static void main(String[] args) {
    KMP2 kmp = new KMP2();

    String text = "aaab?b";
    String pattern = "aba";

    Scanner in = new Scanner(System.in);

    // System.out.println("Podaj tekst");
    // text = in.nextLine();
    // System.out.println("Podaj wzorzec");
    // pattern = in.nextLine();

    System.out.println("Tekst => "+text);
    System.out.println("Wzorzec => "+pattern);

    int result = kmp.search(pattern,text);
    System.out.println(result);

  }
}
