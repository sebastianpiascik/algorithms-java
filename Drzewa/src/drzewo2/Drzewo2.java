package drzewo2;

/* Class containing left and right child of current                                                          
node and key value*/
class Node {
	int key;
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

	void printLeafNodes(Node node) {
		if (node == null) {
			return;
		}
		System.out.print(node.key + " ,");
		if (node.left == null && node.right == null) {
			System.out.println();
		}
		printLeafNodes(node.left);
		printLeafNodes(node.right);
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

public class Drzewo2 {

	public static void main(String[] args) {
		BinaryTree tree = new BinaryTree();

		/* create root */
		tree.insert(new Node(17));
		tree.insert(new Node(18));
		tree.insert(new Node(11));
		tree.insert(new Node(6));
		tree.insert(new Node(30));
		tree.insert(new Node(18));
		tree.insert(new Node(19));
		tree.insert(new Node(18));
		tree.insert(new Node(17));
		tree.insert(new Node(22));
		tree.insert(new Node(17));
		tree.insert(new Node(23));
		tree.insert(new Node(18));
		tree.insert(new Node(18));
		tree.insert(new Node(26));
		tree.insert(new Node(17));

		System.out.println("==============");
		tree.printLeafNodes(tree.root);
		System.out.println("==============");
		Node usun = tree.search(8);
		System.out.println("Usuwam: "+ usun.key);
		tree.delete(usun);
		System.out.println("==============");
		tree.printLeafNodes(tree.root);
		System.out.println("==============");
	}

}

