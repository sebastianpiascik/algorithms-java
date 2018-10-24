import java.util.Arrays;
import java.util.Comparator;


class BTree {

//	int minKeySize;
	int maxKeySize;
//	int minChildenSize;
	int maxChildenSize;
	Node root;

	BTree (int min) {
	//	this.minKeySize = min;
		this.maxKeySize = min * 2 - 1;
	//	this.minChildenSize = min * 2;			// <== to konieczne?
		this.maxChildenSize = this.maxKeySize + 1;
		this.root = null;
	}

	void addKeyArray(int []array){
		for (int i: array){
			if (addKey(i) == false)
			System.out.println("[ Blad dodawania; Klucz: " +i+" ;Btree/addKeyArray ]");
		}
	}

	boolean addKey (int key) {
		if (root == null) {
			root = new Node (null, maxKeySize, maxChildenSize);
			root.addKey (key);
			return true;
		}
		Node node = root;
		while (node != null ) {
			if (node.numberOfKeys == maxKeySize ) {
				split (node, rightDirection(node,key));
				// node zostanie po prawej albo lewej stronie rozdzielenia
			}

			if (node.numberOfChildren == 0) {	// jest lisciem
				node.addKey(key);
				return true;
			} else {
				// przechodzimy do kolejnego wezla
				 node = node.children[ getIndexForKey(node, key)];
			}
		}
		return false;
	}

	boolean rightDirection (Node node, int key){
		if ( getIndexForKey (node, key) > node.getMiddleKeyIndex() )
			return true;
		return false;
	}

	int getIndexForKey (Node node, int key) {
		int i;
		for (i= 0; i < node.numberOfKeys; i++) {
			if ( key < node.keys[i])
				return i;
		}
		return i;
	}

	void split (Node node, boolean rightDirection ) {
		if (node == root) {
			node.parent = new Node(null, maxKeySize, maxChildenSize);
			root = node.parent;
			root.addChild(node);
		}

		Node sibling = new Node(node.parent, maxKeySize, maxChildenSize);
		node.parent.addKey( node.removeKey( node.getMiddleKeyIndex() ));

		int keysAmount = node.numberOfKeys;
		int childrenAmount = node.numberOfChildren;

		if (rightDirection  == true){
			for (int i = 0; i < keysAmount/2;  i++){
				sibling.addKey( node.removeKey(0));
			}

			if	(node.numberOfChildren != 0) {
				for (int i= 0 ; i < childrenAmount/2; i++) {
					sibling.addChild( node.removeChild(0));
				}
			}
		} else {
			for (int i = keysAmount/2; i < keysAmount; i++)
				sibling.addKey( node.removeKey(keysAmount/2));

			if	(node.numberOfChildren != 0) {
				for (int i= childrenAmount/2 ; i < childrenAmount; i++)
					sibling.addChild( node.removeChild(childrenAmount/2));
			}
		}
		node.parent.addChild(sibling);
	}

	void printPathForKey (int key){
		if (root == null ) {
			System.out.println("Drzewo jest puste");
		} else {
			int index, childIndex;
			System.out.print("\n\nScieżka dla klucza [" + key+"]: ");
			Node node = root;
			while (node != null ) {
				index = node.indexOf(key);
				if (index == -1) {
						if (node.numberOfChildren != 0) {
							childIndex = getIndexForKey(node, key);
							System.out.print(childIndex+ "/");
							node = node.children[ childIndex];
						} else{
							System.out.print("[ nie istnieje ]\n");
							return;
						}
				}else{
					System.out.print("*  index klucza w wezle:" +index+ "\n");
					return;
				}
			}
		}
	}

	void printTree(){
		if (root == null)
			System.out.println("Drzewo jest puste");
		else
			printNodes(root, 0);
	}

	void printNodes(Node node, int level){
		for (int i = 1; i <= level; i++) {
			for (int spaces = maxKeySize*2+1; spaces > 0; spaces--)
				System.out.print(" ");
		}
		node.printKeys(maxKeySize);
		for (Node i: node.children) {
			if (i != null)
				printNodes(i, level+1);
		}
	}


	public static void main (String []args) {
		BTree btree = new BTree(3);
		btree.printTree();
		System.out.println("\nAdd: 5,6,7,8,9");
		btree.addKeyArray(new int[] {5,6,7,8,9});	// <== przekazywanie arraya bezposrednio :D
		btree.printTree();
		System.out.println("\nAdd: 1");
		btree.addKeyArray(new int[] {1});
		btree.printTree();
		System.out.println("\nAdd: 2,3,4");
		btree.addKeyArray(new int[] {2,3,4});
		btree.printTree();
		System.out.println("\nAdd: 15");
		btree.addKeyArray(new int[] {15});
		btree.printTree();
		System.out.println("\nAdd: 16,17,18,19,20,21,22,23,24,25,26");
		btree.addKeyArray(new int[] {16,17,18,19,20,21,22,23,24,25,26});
		btree.printTree();
		System.out.println("\nAdd: 21");
		btree.addKeyArray(new int[] {21});
		btree.printTree();
		btree.printPathForKey(19);
		btree.printPathForKey(6);
		btree.printPathForKey(15);
		btree.printPathForKey(21);
	}
}

class Node  {

	int [] keys;
	int numberOfKeys;
	Node [] children;
	int numberOfChildren;
	Node parent;

	private Comparator<Node> comparator = new Comparator<Node>() {
		@Override
		public int compare(Node node1, Node node2) {
	//		return node1.getKey(0).compareTo(node2.getKey(0));   // nie do int
			return(node1.getKey(0)>node2.getKey(0) ? 1 : node1.getKey(0)==node2.getKey(0) ? 0 : -1);
			// porównujemy wezly na podstawie ich pierwszego klucza
		}
	};

	Node (Node parent, int maxKeySize, int maxChildrenSize) {
		this.parent = parent;
		this.keys = new int [maxKeySize + 1];
		this.numberOfKeys = 0;
		this.children = new Node[maxChildrenSize + 1];
		this.numberOfChildren = 0;
	}

	int getKey(int i) {
		return keys[i];
	}

	int indexOf(int value) {
		for (int i = 0; i < numberOfKeys; i++) {
			// if (keys[i].equals(value))	// nie do int
			if (keys[i] == value)
			return i;
		}
		return -1;
	}

	int  removeKey(int index) {
		// przesuwa pozostale klucze w lewo i czysci ostatni
		int value = getKey(index);
		for (int i = index + 1; i < numberOfKeys; i++) {
			keys[i - 1] = keys[i];
		}
		numberOfKeys--;
		keys[numberOfKeys] = -1;
		return value;
	}

	int getMiddleKeyIndex() {
		 return numberOfKeys/2;
	}

	void addKey(int value) {
		keys[numberOfKeys++] = value;
		Arrays.sort(keys, 0, numberOfKeys);
			// Arrays.sort ( array, indeks_pocz (wlacznie), indeks_konc (exclusive), [comparator] );
	}

	void addChild (Node child) {
		child.parent = this;
		children[numberOfChildren++] = child;
		Arrays.sort(children, 0, numberOfChildren, comparator);
	}
	Node removeChild (int index){
		Node node = children[index];
		for (int i = index + 1; i < numberOfChildren; i++) {
			children[i - 1] = children[i];
		}
		numberOfChildren--;
		children[numberOfChildren] = null;
		return node;
	}

	void printKeys(int maxKeySize){
		int i;
		int color = 0;
		for (i = 0; i < numberOfKeys; i++) {
			if (color == 1) {
				System.out.print("\u001B[34m"+ keys[i] + "\033[0m");
				color = 0;
			} else {
				System.out.print(keys[i]);
				color = 1;
			}
		}
		for (; i < maxKeySize; i++)
			System.out.print(" ");
		System.out.print(" \n");
	}

}
