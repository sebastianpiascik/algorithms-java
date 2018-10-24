class Value {
	String value;
	Value next;

	Value(String x) {
		value = x;
		next = null;
	}

	Value(String x, Value Next) {
		value = x;
		next = Next;
	}

	Object getValue() {
		return value;
	}

	Value getNext() {
		return next;
	}

	void setNext(Value Next) {
		next = Next;
	}

	void setValue(String s) {
		value = s;
	}
}

class List {
	Value head;

	List() {
		head = null;
	}

	List(Value new_head) {
		head = new_head;
	}

	void wstawNaKoniec(String value) {
		if (head == null) {
			head = new Value(value);
		} else {
			Value tmp = head;
			while (tmp.getNext() != null) // szukamy ostatniego el
				tmp = tmp.getNext();
			tmp.setNext(new Value(value)); // ustawiamy nastepny element
		}
	}

	void wstaw(String value) {
		if (head == null) {
			head = new Value(value);
		} else {
			Value tmp = new Value(value);
			tmp.next = head;
			head = tmp;
		}
		
	}

	boolean usun(String x) {
		Value tmp = head;
		Value next = null;

		while (tmp != null && !tmp.value.equals(x)) {
			next = tmp;
			tmp = tmp.next;
		}

		if (tmp != null)//jezeli znaleziono element
			if (next == null) {
				head = tmp.next;
				return true;
			}
			else {
				next.next = tmp.next;
				return true;
			}
		return false;
	}

	void kasuj() {
		Value tmp = head;
		Value next = null;

		while (tmp != null) {
			next = tmp.next;
			tmp.next=null; // Usun wskaznik na nastepny element
			tmp=next;
		}
		head = null; // Usun wskaznik na 1 element
	}

	Value szukaj(String x) {
		Value tmp = head;

		while (tmp != null) {
			if (tmp.value.equals(x)) {
				return tmp;
			}
			tmp = tmp.next;
		}

		return null;
	}

	void drukuj() {
		Value tmp = head;
		System.out.println("----");
		while (tmp != null) {
			System.out.println(" " + tmp.value + ""); // wypisujemy listę
			tmp = tmp.next;
		}
		System.out.println("----");
	}
	
	Value bezpowtorzen() {
		Value tmp = head;
		List tmpList = new List();
		while (tmp != null) {
			if(tmpList.szukaj(tmp.value) == null) {
				tmpList.wstaw(tmp.value);
			}
				tmp = tmp.next;
			
		}
		return tmpList.head;
	}
	
	Value scal(List x) {
		List tmpList = new List();
		Value tmp = head;
		Value tmpX = x.head;
		
		while (tmp != null) {
			tmpList.wstaw(tmp.value);
			tmp = tmp.next;
		}
		
		while (tmpX != null) {
			tmpList.wstaw(tmpX.value);
			tmpX = tmpX.next;
		}
		return tmpList.head;
	}
}

class ListaJednokieronkowa {

	public static void main(String[] args) {

		List lista = new List(); // tworzymy listę

		for (int i = 0; i < 6; i++) {
			lista.wstaw("A" + i); // dopisujemy do listy elementy od A0 do A5
		}
		lista.wstaw("A5");

		System.out.println("Szukamy elementu A2: "+lista.szukaj("A2"));
		System.out.println("Usuwamy element A2");
		lista.usun("A2");
		System.out.println("Szukamy elementu A2: "+lista.szukaj("A2"));

//		System.out.println("Lista:");
//		lista.drukuj();
		
//		lista.kasuj();
//		System.out.println("Lista po funckji kasuj():");
//		lista.drukuj();
		
		Value wskListy = lista.bezpowtorzen();
		List lista_kopia = new List(wskListy);

		System.out.println("Lista:");
		lista.drukuj();

		System.out.println("Lista_kopia:");
		lista_kopia.drukuj();
		
		Value wskListyScal = lista.scal(lista_kopia);
		List lista_scal = new List(wskListyScal);

		System.out.println("Lista_scal:");
		lista_scal.drukuj();
	}
}