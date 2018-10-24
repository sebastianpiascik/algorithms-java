import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.File;
import java.util.*;
import java.util.PriorityQueue;


class Node {
    Node left, right;
    int occurence;
    char character;

    public Node(char character, int occurence) {
        this.character = character;
        this.occurence = occurence;
        left = null;
        right = null;
    }

    public Node(Node left, Node right) {
        this.occurence = left.occurence + right.occurence;
        if (left.occurence < right.occurence) {
            this.right = right;
            this.left = left;
        } else {
            this.right = left;
            this.left = right;
        }
    }
}

class Huffman {
  	int length;
  	TreeMap<Character, Integer> occurence = new TreeMap<Character, Integer>();
  	TreeMap<Character, String> codes = new TreeMap<>();
	  PriorityQueue<Node> queue = new PriorityQueue<>((o1, o2) -> (o1.occurence < o2.occurence) ? -1 : 1);

  Huffman(String filePath){
		this.length = 0;
	    	readFile(filePath);
	    	addToQueue();
	    	buildTree();
	    	generateCodes(queue.peek(), "");
	}

  void readFile(String filePath) {
    try {
      Scanner scanner;
      if (filePath == null) {
        System.out.println("Nie podano pliku do odczytu. Podaj nazwe pliku: ");
        scanner = new Scanner(System.in);
        filePath = scanner.nextLine();
      }
  	scanner = new Scanner(new File(filePath));
      countOccurence(scanner);

      scanner.close();
       }catch (FileNotFoundException f) {
        System.out.println("Nie znaleziono pliku");
        System.exit(0);}
    }

    void countOccurence (Scanner scanner){
	      while(scanner.hasNextLine()) {
	      	int amount;
      	  	char []line = scanner.nextLine().toCharArray();
      	  	for (int i = 0; i<line.length; i++){
      	  		if (this.occurence.containsKey(line[i])) {
      	    			amount = this.occurence.get(line[i]);
      	    			amount++;
      	    			this.occurence.put(line[i], amount);
      	  		}
      	  		else
      	  			this.occurence.put(line[i], 1);
      	  		this.length++;
      	 	 }
      	}
    }

	void addToQueue(){
		this.occurence.forEach((Key, Value) ->
			this.queue.add(new Node(Key, Value)));
	}

    void buildTree() {
        while (queue.size() > 1) {
            queue.add(new Node(queue.poll(), queue.poll()));
            }
    }

    void generateCodes(Node node, String s){
    		if (node != null) {
		      if (node.right != null)
		          generateCodes(node.right, s + "1");
		      if (node.left != null)
		          generateCodes(node.left, s + "0");
		      if (node.left == null && node.right == null)
		          codes.put(node.character, s);
        	}
    }

    void checkSize() {
      int sizeHuffman = 0;
      int sizeOriginal = 0;
      for (char key: occurence.keySet()) {
      sizeOriginal += occurence.get(key);
      sizeHuffman += (occurence.get(key) * this.codes.get(key).length());
      }
      System.out.println("\nRozmiar oryginalny:\t"+ sizeOriginal +" znakow"+
                        "\nRozmiar zakodowany:\t"+ sizeHuffman+" bitow\n");
    }

    void printCodes(){
    	System.out.println("Znak:\tWyst.:\tKod:");
        this.codes.forEach((Key, Value) ->
        	System.out.println(Key+ ":\t" +this.occurence.get(Key)+ ":\t"+ Value));
     }


	public static void main (String []args) {
	     Huffman h;
		if (args.length == 0) {
		  	h = new Huffman(null);
	     	}
	     	else
		  	h = new Huffman(args[0]);
		h.printCodes();
    h.checkSize();
	}
}
