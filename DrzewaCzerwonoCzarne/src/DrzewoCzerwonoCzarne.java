public class DrzewoCzerwonoCzarne {

	public final int RED = 0;
	public final int BLACK = 1;

	/*
	 * Class containing left and right child of current node and key value
	 */
	class Node {
		int key = -1, color = BLACK;
		Node left = nil, right = nil, parent = nil;

		Node(int item) {
			key = item;
		}
	}

	// Root of Binary Tree
//	Node root = null;
	final Node nil = new Node(-1);                                                                   
    Node root = nil; 

	// Constructors
	DrzewoCzerwonoCzarne() {
	}

	DrzewoCzerwonoCzarne(int key) {
		root = new Node(key);
	}

	void insert(int k) {
		Node node = new Node(k);
		Node temp = root;
		if (root == nil) {
			root = node;
			node.color = BLACK;
			node.parent = nil;
		} else {
			node.color = RED;
			while (true) {
				if (node.key < temp.key) {
					if (temp.left == nil) {
						temp.left = node;
						node.parent = temp;
						break;
					} else {
						temp = temp.left;
					}
				} else if (node.key >= temp.key) {
					if (temp.right == nil) {
						temp.right = node;
						node.parent = temp;
						break;
					} else {
						temp = temp.right;
					}
				}
			}
			fixTree(node);
		}
	}

	void fixTree(Node node) {
		while (node.parent.color == RED) {
			Node uncle = nil;
			if (node.parent == node.parent.parent.left) {
				uncle = node.parent.parent.right;

				if (uncle != nil && uncle.color == RED) {
					node.parent.color = BLACK;
					uncle.color = BLACK;
					node.parent.parent.color = RED;
					node = node.parent.parent;
					continue;
				}
				if (node == node.parent.right) {
					// Double rotation needed
					node = node.parent;
					rotateLeft(node);
				}
				node.parent.color = BLACK;
				node.parent.parent.color = RED;
				// if the "else if" code hasn't executed, this
				// is a case where we only need a single rotation
				rotateRight(node.parent.parent);
			} else {
				uncle = node.parent.parent.left;
				if (uncle != nil && uncle.color == RED) {
					node.parent.color = BLACK;
					uncle.color = BLACK;
					node.parent.parent.color = RED;
					node = node.parent.parent;
					continue;
				}
				if (node == node.parent.left) {
					// Double rotation needed
					node = node.parent;
					rotateRight(node);
				}
				node.parent.color = BLACK;
				node.parent.parent.color = RED;
				// if the "else if" code hasn't executed, this
				// is a case where we only need a single rotation
				rotateLeft(node.parent.parent);
			}
		}
		root.color = BLACK;
	}

	void rotateLeft(Node node) {
		if (node.parent != nil) {
			if (node == node.parent.left) {
				node.parent.left = node.right;
			} else {
				node.parent.right = node.right;
			}
			node.right.parent = node.parent;
			node.parent = node.right;
			if (node.right.left != nil) {
				node.right.left.parent = node;
			}
			node.right = node.right.left;
			node.parent.left = node;
		} else {// Need to rotate root
			Node right = root.right;
			root.right = right.left;
			right.left.parent = root;
			root.parent = right;
			right.left = root;
			right.parent = nil;
			root = right;
		}
	}

	void rotateRight(Node node) {
		if (node.parent != nil) {
			if (node == node.parent.left) {
				node.parent.left = node.left;
			} else {
				node.parent.right = node.left;
			}

			node.left.parent = node.parent;
			node.parent = node.left;
			if (node.left.right != nil) {
				node.left.right.parent = node;
			}
			node.left = node.left.right;
			node.parent.right = node;
		} else {// Need to rotate root
			Node left = root.left;
			root.left = root.left.right;
			left.right.parent = root;
			root.parent = left;
			left.right = root;
			left.parent = nil;
			root = left;
		}
	}

	void print(Node node) {
		if (node == nil) {
			return;
		}
		System.out.print(node.key + "(" + node.color + ")" + " ,");
		if (node.left == nil && node.right == nil) {
			System.out.println();
		}
		print(node.left);
		print(node.right);
	}

	void printTree(Node node) {
		if (node == nil) {
			return;
		}
		printTree(node.left);
		if (node.parent != nil) {
			System.out.print(((node.color == RED) ? "Color: Red " : "Color: Black ") + "Key: " + node.key + " Parent: "
					+ node.parent.key + "\n");
		} else {
			System.out.print(((node.color == RED) ? "Color: Red " : "Color: Black ") + "Key: " + node.key + "\n");
		}
		printTree(node.right);
	}

}

class Main {

	public static void main(String[] args) {
		DrzewoCzerwonoCzarne tree = new DrzewoCzerwonoCzarne();

		/* create root */
		tree.insert(38);
		tree.insert(31);
		tree.insert(22);
		tree.insert(8);
		tree.insert(20);
		tree.insert(5);
		tree.insert(10);
		// tree.insert(9);
		// tree.insert(21);
		// tree.insert(27);
		// tree.insert(29);
		// tree.insert(25);
		// tree.insert(28);
		tree.printTree(tree.root);
		// tree.print(tree.root);
	}

}
