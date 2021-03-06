import java.util.*;

public class KMP{
  int search(String pattern, String text) {
    int[] lsp = computeLspTable(pattern);

    int j = 0;  // Number of chars matched in pattern
    for (int i = 0; i < text.length(); i++) {
        while (j > 0 && text.charAt(i) != pattern.charAt(j)) {
            // Fall back in the pattern
            j = lsp[j - 1];  // Strictly decreasing
        }
        if (text.charAt(i) == pattern.charAt(j)) {
            // Next char matched, increment position
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
    KMP kmp = new KMP();

    String text = "aaabbb";
    String pattern = "cb";

    int result = kmp.search(pattern,text);
    System.out.println(result);

  }
}
