package ListaDwukierunkowa;

class Value {
	String value;
	Value next;
	Value prev;

	Value() {
		next = null;
		prev = null;
	}

	Value(String x, Value Next, Value Prev) {
		value = x;
		next = Next;
		prev = Prev;
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

	Value getPrev() {
		return prev;
	}

	void setPrev(Value Prev) {
		next = Prev;
	}

	void setValue(String s) {
		value = s;
	}
}

class List {
	Value head;
	Value tail;

	List() {
		head = null;
		tail=null;
	};

	List(Value Head) {
		head = Head;
		tail=null;
	}

	void wstaw(String value) {
		Value tmp = new Value(value,null,null);
		tmp.next=head;// head jako 2 element
		if(head == null) {
			head=tmp;
			tail=tmp;
		} else { //jezeli sa elementy
			head.prev=tmp;//value jako 1 element
			head=tmp;
		}
	}

	void usun(String x) {
		Value tmp = head;
		Value next = null;

		while (tmp != null && !tmp.value.equals(x)) {
			next = tmp;
			tmp = tmp.next;
		}

		if (tmp != null)//jezeli znaleziono element
		{
			if (tmp.prev != null) {
				tmp.prev.next = tmp.next;
			}
			else {
				head=tmp.next;
			}
			if(tmp.next != null) {
				tmp.next.prev = tmp.prev;
			}
		}
	}

	void kasuj() {
		Value tmp = head;
		Value next = null;
		while (tmp != null) {
			usun(tmp.value);
			tmp = tmp.next;
		}
		
		head = null; // Usun wskaznik na 1 element
		tail = null; // Usun wskaznik na 1 element
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
	void setTail(List x) {
		Value tmp = x.head;
		while (tmp != null) {
			x.tail=tmp;
			tmp = tmp.next;
		}
	}
	
	Value scal(List x) {
		List tmpList = new List();
		Value tmp = head;
		Value tmpX = x.head;


		tmpList.head = head;
		tmpList.setTail(tmpList);
		System.out.println("sss: " + x.head.getValue());
		tmpList.tail = x.head;
		
//		while (tmpX != null) {
//			tmpList.wstaw(tmpX.value);
//			tmpX = tmpX.next;
//		}
		return tmpList.head;
	}
}

class ListaDwukieronkowa {

	public static void main(String[] args) {

		List lista = new List(); // tworzymy listę

		for (int i = 0; i < 5; i++) {
			lista.wstaw("A" + i); // dopisujemy do listy elementy od A0 do A5
		}


		System.out.println("Lista:");
		lista.drukuj();
		
		System.out.println("Szukamy elementu A2: "+lista.szukaj("A2"));
		System.out.println("Usuwamy element A2");
		lista.usun("A2");
		System.out.println("Szukamy elementu A2: "+lista.szukaj("A2"));
		

//		lista.kasuj();
//		System.out.println("Lista po funckji kasuj():");
//		
//		System.out.println("Lista:");
//		lista.drukuj();
		
		Value wskListy = lista.bezpowtorzen();
		List lista_kopia = new List(wskListy);
		lista_kopia.setTail(lista_kopia);


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