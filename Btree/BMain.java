import java.util.Scanner;

class BNode {

    static int t;
    int count; // ilosc kluczy w wezle
    int key[]; // wartosci klucza
    BNode child[]; // tablica referecji do dzieci
    boolean leaf;
    BNode parent;

    public BNode() {}
    public BNode(int t, BNode parent) {

        this.t = t;
        this.parent = parent;
        key = new int[2 * t - 1];
        child = new BNode[2 * t];
        leaf = true; // na poczatku jest lisciem
        count = 0;
    }

    // pobierz wartosc klucza
    public int getValue(int index) {
        return key[index];
    }

    // pobierz dzieci
    public BNode getChild(int index) {
        return child[index];
    }


}

class BTree {

    static int order; // wielkosc drzewa
    BNode root;

    public BTree(int order) {
        this.order = order;

        root = new BNode(order, null);

    }

    public BNode search(BNode root, int key) {
        int i = 0;

        while (i < root.count && key > root.key[i]){
            i++;
        }

        if (i <= root.count && key == root.key[i]){
            return root;
        }

        if (root.leaf){
            return null;

        } else{
            return search(root.getChild(i), key);
        }
    }

    public void SearchPrintNode(BTree T, int x) {
        BNode temp = new BNode(order, null);

        temp = search(T.root, x);

        if (temp == null) {

            System.out.println("Klucz nie istnieje");
        } else {
            System.out.println("Klucz istnieje");
            // print(temp);
        }


    }

    public void split(BNode x, int i, BNode y) {
        BNode z = new BNode(order, null);

        z.leaf = y.leaf;
        z.count = order - 1;

        for (int j = 0; j < order - 1; j++) {
            z.key[j] = y.key[j + order];

        }
        if (!y.leaf) // Jezeli nie jest lisciem to musimy przypisac dzieci
        {
            for (int k = 0; k < order; k++) {
                z.child[k] = y.child[k + order];
            }
        }

        y.count = order - 1; // nowa wielkosc wezla y

        for (int j = x.count; j > i; j--) // zmieniamy indeksy
        {
            x.child[j + 1] = x.child[j];

        }
        x.child[i + 1] = z;

        for (int j = x.count; j > i; j--) {
            x.key[j + 1] = x.key[j];
        }
        x.key[i] = y.key[order - 1];

        y.key[order - 1] = 0;

        for (int j = 0; j < order - 1; j++) {
            y.key[j + order] = 0;
        }

        x.count++;
    }

    public void nonfullInsert(BNode x, int key) {
        int i = x.count;

        if (x.leaf) {
            while (i >= 1 && key < x.key[i - 1])
            {
                x.key[i] = x.key[i - 1];
                i--;
            }

            x.key[i] = key;
            x.count++;

        } else {
            int j = 0;
            while (j < x.count && key > x.key[j])
            {
                j++;
            }

            //	i++;

            if (x.child[j].count == order * 2 - 1) {
                split(x, j, x.child[j]);

                if (key > x.key[j]) {
                    j++;
                }
            }

            nonfullInsert(x.child[j], key);
        }
    }

    public void insert(BTree t, int key) {
        BNode r = t.root;

        if (r.count == 2 * order - 1) // jezeli pelny
        {
            BNode s = new BNode(order, null);

            t.root = s;
            s.leaf = false;
            s.count = 0;
            s.child[0] = r;
            split(s, 0, r);
            nonfullInsert(s, key);
        } else
            nonfullInsert(r, key);
    }

    public void print(BNode n) {
        for (int i = 0; i < n.count; i++) {
            System.out.print(n.getValue(i) + " "); //drukuje root
        }

        if (!n.leaf) // kiedy rootnie jest lisciem
        {

            for (int j = 0; j <= n.count; j++)
            {
                if (n.getChild(j) != null)
                {
                    System.out.println();
                    print(n.getChild(j));
                }
            }
        }
        System.out.println();
    }

    public void deleteKey(BTree t, int key) {

        BNode temp = new BNode(order, null);

        temp = search(t.root, key); 
        System.out.println("znalazlem: "+ temp);

        if (temp.leaf && temp.count > order - 1) {
            int i = 0;

            while (key > temp.getValue(i)) {
                i++;
            }
            for (int j = i; j < 2 * order - 2; j++) {
                temp.key[j] = temp.getValue(j + 1);
            }
            temp.count--;

        } else {
            System.out.println("Nie moge usunac");
        }
    }


}

public class BMain {

    public static void main(String[] args) {

        int n = 3; // T drzewa
        int key;

        BTree tree = new BTree(n);

        tree.insert(tree, 3);
        tree.insert(tree, 4);
        tree.insert(tree, 5);

        tree.print(tree.root);
        System.out.println();

        key = 3;
        System.out.println("Szukam klucza " + key);
        tree.SearchPrintNode(tree, key);
        key = 5;
        System.out.println("Szukam klucza " + key);
        tree.SearchPrintNode(tree, key);

        key = 4;
        System.out.println("Usuwam klucz " + key);
        tree.deleteKey(tree, key);
        System.out.println("Drzewo po usunieciu:");
        tree.print(tree.root);


    }
}
