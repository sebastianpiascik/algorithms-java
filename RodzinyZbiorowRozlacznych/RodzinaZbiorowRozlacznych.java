import java.util.*;

class Node {
  int key;
  int rank;
  Node p;

  public Node(){
    key=-1;
  }
}

public class RodzinaZbiorowRozlacznych
{
  // utworzenie zbioru reprezentującego
  // jednoelementowy zbiór z kluczem k
  Node makeSet(int k){
    Node x = new Node();
    x.key=k;
    x.p=x;
    x.rank=0;

    return x;
  }

  // zwraca reprezentanta zbioru do którego należy element (czyli węzeł) x
  // Wersja z kompresją
  Node findSet(Node x){
    if(x != x.p)
      x.p = findSet(x.p);
    return x.p;
  }

  // zamienia zbiory o reprezentantach x,y w
  // jeden zbior będący ich sumą rozłączną
  // Wersja z rangą
  void union(Node x,Node y){
    x=findSet(x);
    y=findSet(y);
    if(x.rank > y.rank){
      y.p=x;
    }
    else{
      // System.out.println("tu: k:"+x.key+" r:"+x.rank+" k2:"+y.key+" r2:"+y.rank);
      x.p=y;
      if(x.rank==y.rank){
        y.rank = y.rank+1;
      }
    }
  }

  String printPath(Node x){
    String str="klucz:"+x.key+" => "+x.key+"["+x.rank+"]";
    while(x != x.p){
      str+=" - "+x.p.key+"["+x.p.rank+"]";
      x=x.p;
    }
    return str;
  }

  public static void main(String[] args)
  {
    Scanner in = new Scanner(System.in);
    RodzinaZbiorowRozlacznych rzr = new RodzinaZbiorowRozlacznych();

    int i=0;
    int arrayIndex=0;
    int arraySize=99;

    Node sets[] = new Node[arraySize];

    for(i=0;i<=9;i++){
      sets[i]=rzr.makeSet(arrayIndex++);
    }

    rzr.union(sets[2],sets[1]);
    rzr.union(sets[4],sets[3]);
    rzr.union(sets[4],sets[5]);
    rzr.union(sets[5],sets[1]);

    System.out.println("\n1. Pokaz klucze \n2. Dodaj wierzcholek \n3. Dodaj krawedz \n4. Sprawdz spojnosc \n5. Wyjdz\n");
    int choosenNumber = in.nextInt();
    int key1,key2;

    while(choosenNumber!=5){
      switch (choosenNumber) {
        case 1:
          for(i=0;i<arraySize;i++){
            if(sets[i] != null)
              System.out.println("["+i+"] "+rzr.printPath(sets[i]));
          }
          break;

        case 2:
          System.out.println("Dodaje wierzcholek");
          sets[arrayIndex]=rzr.makeSet(arrayIndex++);
          break;

        case 3:
          System.out.println("Podaj 2 liczby aby dodac krawedz");
          key1 = in.nextInt();
          key2 = in.nextInt();
          rzr.union(sets[key1],sets[key2]);
          break;

        case 4:
          System.out.println("Podaj 2 wartosci klucza do sprawdzenia spojnosci");
          key1 = in.nextInt();
          key2 = in.nextInt();
          if(rzr.findSet(sets[key1])==rzr.findSet(sets[key2])){
            System.out.println("Tak");
          } else{
            System.out.println("Nie");
          }
          break;

        default:
          break;
      }
      System.out.println("\n1. Pokaz klucze \n2. Dodaj wierzcholek \n3. Dodaj krawedz \n4. Sprawdz spojnosc \n5. Wyjdz \n");
      choosenNumber = in.nextInt();
    }

  }
}
