import java.util.*;
import java.io.*;

class HuffmanNode {

    int data;
    char c;

    HuffmanNode left;
    HuffmanNode right;
}

class MyComparator implements Comparator<HuffmanNode> {
    public int compare(HuffmanNode x, HuffmanNode y)
    {

        return x.data - y.data;
    }
}

public class Huffman {

    public static void saveCode(HuffmanNode root, String s, String[] charCode, char[] charArray)
    {

        if (root.left == null && root.right == null && Character.isLetter(root.c)) {
            for(int i=0;i<charArray.length;i++){
              if(charArray[i] != '\u0000'){
                if(charArray[i] == root.c){
                  charCode[i] = s;
                }
              }
            }
            return;
        }

        saveCode(root.left, s + "0",charCode,charArray);
        saveCode(root.right, s + "1",charCode,charArray);
    }

    public static void main(String[] args) throws Exception
    {

        Scanner s = new Scanner(System.in);

        int index=0;
        int indexFound=0;
        boolean contains = false;
        char[] charArray = new char[99];
        int[] charfreq = new int[99];
        String[] charCode = new String[99];
        Arrays.fill(charfreq, 0);

        File file = new File(args[0]);
        BufferedReader br = new BufferedReader(new FileReader(file));
        String st = null;
        while ((st = br.readLine()) != null){
          for(int i=0;i<st.length();i++){

            contains = false;
            if(index > 0){
              for(int j=0;j<index;j++){
                if(charArray[j]==st.charAt(i)){
                  contains=true;
                  indexFound=j;
                }
              }
            }
            // System.out.println("index: "+index+" ,char:"+st.charAt(i)+" ,contains: "+contains);
            if(contains == true){
              charfreq[indexFound]++;
            } else{
              charArray[index]=st.charAt(i);
              charfreq[index]++;
              index++;
            }

          }
        }

        // Ilosc znakow
        int n = index;
        System.out.println("Ilosc roznych znakow: "+n);

        // Tworzenie kolejki HuffmanCode

        PriorityQueue<HuffmanNode> q
            = new PriorityQueue<HuffmanNode>(n, new MyComparator());

        for (int i = 0; i < n; i++) {
            HuffmanNode hn = new HuffmanNode();

            hn.c = charArray[i];
            hn.data = charfreq[i];

            hn.left = null;
            hn.right = null;
            q.add(hn);
        }

        HuffmanNode root = null;

        while (q.size() > 1) {
            HuffmanNode x = q.peek();
            q.poll();
            HuffmanNode y = q.peek();
            q.poll();

            // new node f which is equal
            HuffmanNode f = new HuffmanNode();
            f.data = x.data + y.data;
            f.c = '-';
            f.left = x;
            f.right = y;
            root = f;
            q.add(f);
        }

        // Zapisz kod do tabeli charCode

        saveCode(root, "",charCode,charArray);

        int huffmanCodeAmount = 0;
        int normalAmount=0;
        // Wyswietl wyniki

        for(int i=0;i<charArray.length;i++){
          if(charArray[i] != '\u0000'){
            System.out.println(charArray[i]+":"+charfreq[i]+" -- "+charCode[i]);
            normalAmount+=charfreq[i];
            huffmanCodeAmount+=charfreq[i]*charCode[i].length();
          }
        }
        System.out.println("Ilosc znakow: "+ normalAmount+ ", ilosc znakow w kodzie huffmana:"+huffmanCodeAmount);

    }
}
