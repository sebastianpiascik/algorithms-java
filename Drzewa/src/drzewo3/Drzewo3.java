package drzewo3;

/* Class containing left and right child of current                                                          
node and key value*/
class Node {
	int key,ileKluczy=0;
	Node left, right;
	Node parent;

	public Node(int item) {
		key = item;
		left = right = null;
		parent = null;
	}

	public Node(int item, Node l, Node r, Node p) {
		key = item;
		left = l;
		right = r;
		parent = p;
	}
}

//A Java program to introduce Binary Tree
class BinaryTree {
	// Root of Binary Tree
	Node root = null;

	// Constructors
	BinaryTree(int key) {
		root = new Node(key);
	}

	BinaryTree() {
	}

	void insert(Node z) {
		Node x = root;
		Node y = null;
		while (x != null) {
			//zliczamy ilosc wystapien klucza
			if(z.key == x.key) {
				z.ileKluczy++;
			}
			y = x;
			if (z.key < x.key) {
				x = x.left;
			} else {
				x = x.right;
			}
		}
		z.parent = y;
		if (y == null) {
			root = z;
		} else if (z.key < y.key) {
			y.left = z;
		} else {
			y.right = z;
		}
	}

	Node search(int k) {
		Node x = root;
		while (x != null && k != x.key) {
			if (k < x.key) {
				x = x.left;
			} else {
				x = x.right;
			}
		}
		return x;
	}

	Node searchLast(int k) {
		Node x = root;
		Node y = null;
		while (x != null) {
			if (k < x.key) {
				x = x.left;
			} else {
				x = x.right;
			}
			if(x != null)
				if(k == x.key) {
//					System.out.println(x);
					y=x;
				}
		}
		return y;
	}

	void printLeafNodes(Node node) {
		if (node == null) {
			return;
		}
		System.out.print(node.key +"("+ node.ileKluczy +") ,");
		System.out.print(node.key + " ,");
		if (node.left == null && node.right == null) {
			System.out.println();
		}
		printLeafNodes(node.left);
		printLeafNodes(node.right);
	}

	void printPost(Node node) {
		if (node == null) {
			return;
		}
		if (node.left != null) {
			printPost(node.left);
		}
		if (node.right != null) {
			printPost(node.right);
		}
		System.out.print(node.key + " ,");
	}
	void printPreorder(Node node)
    {
        if (node == null)
            return;
 
        /* first print data of node */
        System.out.print(node.key + " ");
 
        /* then recur on left sutree */
        printPreorder(node.left);
 
        /* now recur on right subtree */
        printPreorder(node.right);
    }

	void delete(Node z) {
		if (z.left == null) {
			transplant(z, z.right);
		} else if (z.right == null) {
			transplant(z, z.left);
		} else {
			Node y = minimum(z.right);
			if (y.parent != z) {
				transplant(y, y.right);
				y.right = z.right;
				y.right.parent = y;
			}
			transplant(z, y);
			y.left = z.left;
			y.left.parent = y;
		}
	}

	void deleteMoreThan1(Node z) {
		if(z.ileKluczy > 0) {
			if (z.left == null) {
				transplant(z, z.right);
			} else if (z.right == null) {
				transplant(z, z.left);
			} else {
				Node y = minimum(z.right);
				if (y.parent != z) {
					transplant(y, y.right);
					y.right = z.right;
					y.right.parent = y;
				}
				transplant(z, y);
				y.left = z.left;
				y.left.parent = y;
			}
		}
	}

	Node minimum(Node x) {
		// zwraca skrajny lewy węzeł w poddrzewie o korzeniu x
		// czyli węzeł o najmniejszym kluczu w tym poddrzewie
		while (x.left != null) {
			x = x.left;
		}
		return x;
	}

	void transplant(Node u, Node v) {
		// podczepia poddrzewo o korzeniu "v" w miejsce węzła u
		// w drzewie T
		if (u.parent == null) { // u jest korzeniem
			root = v;
		} else if (u == u.parent.left) { // u jest lewym synem
			u.parent.left = v;
		} else {
			u.parent.right = v; // u jest prawym synem
		}
		if (v != null) {
			v.parent = u.parent;
		}
	}

}

public class Drzewo3 {

	public static void main(String[] args) {
		BinaryTree tree = new BinaryTree();
		

//      Zadanie 1
//		tree.insert(new Node(18));
//		tree.insert(new Node(11));
//		tree.insert(new Node(6));
//		tree.insert(new Node(30));
//		tree.insert(new Node(21));
//		tree.insert(new Node(19));
//		tree.insert(new Node(8));
//		tree.insert(new Node(22));
//		tree.insert(new Node(23));
//		tree.insert(new Node(5));
//		tree.insert(new Node(20));
//		tree.insert(new Node(26));
//		tree.insert(new Node(17));
//
		
//		System.out.println("==============");
//		tree.printLeafNodes(tree.root);
//		System.out.println("==============");
//		Node usun = tree.search(8);
//		System.out.println("Usuwam: "+ usun.key);
//		tree.delete(usun);
//		usun = tree.search(30);
//		System.out.println("Usuwam: "+ usun.key);
//		tree.delete(usun);
//		usun = tree.search(18);
//		System.out.println("Usuwam: "+ usun.key);
//		tree.delete(usun);
//		usun = tree.search(11);
//		System.out.println("Usuwam: "+ usun.key);
//		tree.delete(usun);
//		System.out.println("==============");
//		tree.printLeafNodes(tree.root);
//		System.out.println("==============");
		
		

//      Zadanie 2
//		tree.insert(new Node(17));
//		tree.insert(new Node(18));
//		tree.insert(new Node(11));
//		tree.insert(new Node(6));
//		tree.insert(new Node(30));
//		tree.insert(new Node(18));
//		tree.insert(new Node(19));
//		tree.insert(new Node(18));
//		tree.insert(new Node(17));
//		tree.insert(new Node(22));
//		tree.insert(new Node(17));
//		tree.insert(new Node(23));
//		tree.insert(new Node(18));
//		tree.insert(new Node(18));
//		tree.insert(new Node(26));
//		tree.insert(new Node(17));
//		System.out.println("==============");
//		tree.printLeafNodes(tree.root);
//		System.out.println("==============");
//		Node find = tree.searchLast(18);
//		System.out.println("Ostatni element:  ,Node:"+find);
//		System.out.println("==============");
//		tree.printPost(tree.root);
//		System.out.println("==============");
		

//      Zadanie 3
//		tree.insert(new Node(20));
//		tree.insert(new Node(10));
//		tree.insert(new Node(30));
//		tree.insert(new Node(5));
//		tree.insert(new Node(15));
//		tree.insert(new Node(40));
//		tree.insert(new Node(12));
//		tree.insert(new Node(16));
//		tree.insert(new Node(35));
//
//		System.out.println("==============");
//		tree.printLeafNodes(tree.root);
//		System.out.println("==============");
//		System.out.println("==============");
//		tree.printPost(tree.root);
//		System.out.println("==============");
		

//      Zadanie 4
		tree.insert(new Node(20));
		tree.insert(new Node(10));
		tree.insert(new Node(30));
		tree.insert(new Node(5));
		tree.insert(new Node(15));
		tree.insert(new Node(40));
		tree.insert(new Node(20));
		tree.insert(new Node(16));
		tree.insert(new Node(35));

		System.out.println("==============");
		tree.printPost(tree.root);
		System.out.println("==============");
		tree.printPreorder(tree.root);
		System.out.println("==============");
		Node usun = tree.searchLast(20);
		System.out.println("Usuwam: "+ usun.key);
		tree.deleteMoreThan1(usun);
		System.out.println("==============");
		tree.printPost(tree.root);
		System.out.println("==============");
		
	}

}

