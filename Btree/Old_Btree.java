import java.io.*;

public class Old_Btree {

	int t = 3; // stopien B-drzewa
	Wezel root;

	public Old_Btree(){
		root=new Wezel();
	}

	class Wezel {
		int n; // ilosc kluczy (-1 oznacza wezel usuniety)
		boolean leaf; // czy lisc
		int[] k = new int[2 * t - 1]; // klucze
		Wezel[] c = new Wezel[2 * t]; // wskazniki do synow (pozycje w pliku: 0,1,2 ...)

		public Wezel(){
			this.n=0;
			this.leaf=false;
		}

		void printKeys(int maxKeySize){
			int i;
			int color = 0;
			for (i = 0; i < n; i++) {
				if (color == 1) {
					System.out.print("\u001B[34m"+ k[i] + "\033[0m");
					color = 0;
				} else {
					System.out.print(k[i]);
					color = 1;
				}
			}
			for (; i < maxKeySize; i++)
				System.out.print(" ");
			System.out.print(" \n");
		}

	}

	// szuka klucza k w poddrzewie o korzeniu x
	Wezel BtreeSearch(Wezel x,int k){
		int i=0;
		while (i<=x.n && k>x.k[i]){
			i++;
		}
		if (i<=x.n && k==x.k[i]){
			return x;
		}
		if (x.leaf)
			return null;
			// DISK-READ(x.c[i]);
			return BtreeSearch(x.c[i],k);
	}

	void BtreeSplitChild(Wezel x, int i, Wezel y){
		Wezel z = new Wezel();
		z.leaf=y.leaf; // z jest liściem, jeżeli y był liściem
		z.n=t-1;

		for (int j = 0; j < t-1; j++)
		    z.k[j] = y.k[j+t];

		if (y.leaf == false)
		{
		    for (int j = 0; j < t; j++)
		        z.c[j] = y.c[j+t];
		}

		y.n = t - 1;

 		// robimy miejsce na nowego syna w x
		for (int j = x.n; j >= i+1; j--)
		    x.c[j+1] = x.c[j];

		// z jest tym synem
		x.c[i+1] = z;

		// robimy miejsce na nowy klucz w x
		for (int j = x.n-1; j >= i; j--)
		    x.k[j+1] = x.k[j];

		x.k[i] = y.k[t-1];  // środkowy klucz węzła y jest tym nowym kluczem
		x.n++;
// 		DISK-WRITE(y)
// DISK-WRITE(z)
// DISK-WRITE(x)

	}

	void BtreeInsertNonFull(Wezel x, int k){
		int i = x.n-1;

    if (x.leaf == true)
    {
        while (i >= 0 && k < x.k[i])
        {
            x.k[i+1] = x.k[i];
            i--;
        }

        x.k[i+1] = k;
        x.n = x.n+1;
				//DISK-WRITE(x)
    }
    else
    {
        while (i >= 0 && x.k[i] > k){
					i--;
				}
				i++;
				//DISK-WRITE(x.c[i])
        if (x.c[i+1].n == 2*t-1)
        {
            BtreeSplitChild(x,i,x.c[i]);
            if (x.k[i] < k)
                i++;
        }
        BtreeInsertNonFull(x.c[i],k);
    }
	}

	// wstawia klucz k do drzewa T
	void BtreeInsert(int k)
	{
		Wezel r=root;
		if (r.n == 2*t-1)
	  {
				Wezel s = new Wezel();
				s=root;
				s.leaf=false;
				s.n=0;
				s.c[0]=r;
				BtreeSplitChild(s,0,r);
				BtreeInsertNonFull(s,k);
	  }else{
				BtreeInsertNonFull(r,k);
		}
	}


	void printTree(){
		if (root == null)
			System.out.println("Drzewo jest puste");
		else
			printNodes(root, 0);
	}

	void printNodes(Wezel node, int level){
		for (int i = 1; i <= level; i++) {
			for (int spaces = t*2+1; spaces > 0; spaces--)
				System.out.print(" ");
		}
		node.printKeys(t);
		for (Wezel i: node.c) {
			if (i != null)
				printNodes(i, level+1);
		}
	}




	public static void main(String args[]) {
		Old_Btree btree = new Old_Btree();


		btree.printTree();

		btree.BtreeInsert(5);
		btree.BtreeInsert(6);
		btree.BtreeInsert(7);
		btree.BtreeInsert(8);
		btree.BtreeInsert(9);

		btree.printTree();

		// int i;
		// double sp;

		// try {
		// 	File file = new File("bdrzewo");
		//
		// 	// if file doesnt exists, then create it
		// 	if (!file.exists()) {
		// 		file.createNewFile();
		// 	}
		//
		// 	FileWriter fw = new FileWriter(file.getAbsoluteFile());
		// 	BufferedWriter bw = new BufferedWriter(fw);
		//
		//
		// 	// bw.write("cos");
		// 	bw.close();
		// 	System.out.println("Done writing to file"); // For testing
		// } catch (IOException e) {
		// 	System.out.println("Error: " + e);
		// 	e.printStackTrace();
		// }

	}

}
