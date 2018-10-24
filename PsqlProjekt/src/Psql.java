import java.io.*;

import java.util.Arrays;
import java.util.stream.Collectors;



//1. [1 pkt] Utwórz wirtualną maszynę i zainstaluj na niej dowolną dystrybucję systemu linux.
//2. [1 pkt] Zainstaluj i skonfiguruj PostgreSQL.
//3. [1 pkt] Zainstaluj niezbędne programy i biblioteki, aby móc pisać programy (w języku C lub innym przez siebie wybranych języku programowania) komunikujące się z bazą danych PostgreSQL.
//
//Jeśli chcesz to możesz punkty 1-3 pominąć i skorzystać z PostgreSQL uczelnianego.
//
//4. Napisz program odczytujący dane z pliku tekstowego zapisanego w formacie CSV (pierwszy wiersz zawiera nagłówek, pozostałe wiersze to dane, ";" to separator kolumny, "\n" to separator wiersza).
//Przykładowy plik CSV mógłby wyglądać następująco:
//imie;nazwisko;ulica;numer;kod;miejscowosc;telefon;email;data_ur
//Jan;Kowalski;ul. Nowa;1a;11-234;Budry;123-123-456;jan@go.xxx;1980.05.13
//Jerzy;Nowak;ul. Konopnicka;13a/3;00-900;Lichowice;(55)333-44-55;jer@wu.to;1990.03.23
//
//5. [2 pkt] Program na odczytać wiersz nagłówkowy i na jego podstawie utworzyć tabelę w bazie danych (PostgreSQL):
//a) nazwa tabeli ma być taka sama jak nazwa pliku CSV (bez rozszerzenia), jeśli tabela istnieje, to powinna zostać usunięta i na nowo utworzona,
//b) tabela powinna posiadać klucz główny o nazwie "id" z autoinkrementacją,
//c) nazwy pozostałych kolumn tabeli powinny być odczytane z nagłówka pliku CSV, kolumn w tabeli (nie licząc klucza głównego) powinno być tyle ile jest pól w nagłówku pliku CSV, przyjmijmy, że dodawane kolumny będą typu VARCHAR(20),
//
//6. [2 pkt] Program powinien dane odczytane z pliku CSV wpisać do utworzonej tabeli:
//a) jeżeli dane się nie mieszczą w kolumnie, to program powinien wykonać polecenie alter table ... i zmienić typ kolumny np. na  VARCHAR(wieksza_dlugosc_danych_ktore_chcemy_wpisac),
//b) jeżeli danych (pól w wierszu pliku CSV) jest za dużo w stosunku do nagłówka, to dane nadmiarowe powinny zostać zignorowane,
//c) jeżeli danych (pól w wierszu pliku CSV) jest za mało w stosunku do nagłówka, to jako wartości brakujących kolumn powinno się przyjąć NULL,
//
//7. [1 pkt] Program powinien poprawnie działać dla pliku CSV dostarczonego przez osobę sprawdzającą:
//a) dopiero po analizie CSV wiadomo będzie ile będzie kolumn,
//b) może nie być żadnego wiersza z danymi lub może być danych bardzo dużo.
//
//8. [1 pkt] Program ma reagować na wywołanie: ./program nazwa.csv
//
//9. [1 pkt] Na życzenia użytkownika program powinien stworzyć plik w standardzie HTML5 z zawartością utworzonej tabeli (dane mają być pobrane z bazy danych nie z pliku!).
//
//
//Jako rozwiązanie zadania należy terminowo przesłać:
//a) Spis kolejnych poleceń systemu linux jakie należy wykonać, aby zrealizować punkty 2 i 3 (nie musicie opisywać jak opisywaliście linuxa, napiszcie tylko jaką wybraliście dystrybucję),
//b) Program (w dowolnym języku programowania, np. w języku C, nie może być to język skryptowy) posiadający implementację punktów 4-9.
//
//Maszynę wirtualną należy przynieść na zajęcia i zademonstrować jej działanie.




public class Psql {

	public static void main(String[] args) {

		// ================ Read file

		String lines[] = new String[99];
		int index = 0;

		String fileName = "plik.csv";
		String line = null;

		try {
			FileReader fileReader = new FileReader(fileName);
			BufferedReader bufferedReader = new BufferedReader(fileReader);

			while ((line = bufferedReader.readLine()) != null) {
				lines[index]=line;
				index++;
			}

			bufferedReader.close();
		} catch (FileNotFoundException ex) {
			System.out.println("Unable to open file '" + fileName + "'");
		} catch (IOException ex) {
			System.out.println("Error reading file '" + fileName + "'");
		}

		System.out.println(Arrays.toString(lines));

		// ================ Create array

		int rowsAmount=0,columnsAmount=1;

		for(int i=0;i<lines.length;i++)
			if(lines[i] != null)
				rowsAmount++;

		for(int i=0;i<lines[0].length();i++)
			if(lines[0].charAt(i) == ';')
				columnsAmount++;

		System.out.println("Rows: "+rowsAmount+", Columns: "+columnsAmount);
		String tablica[][] = new String[rowsAmount][columnsAmount];

		int startIndex=0;
		int endIndex=0;
		int tabRowIndex=0;
		int tabColumnIndex=0;

		for(int i=0;i<lines.length;i++) {
			if(lines[i] != null) {
				for(int j=0;j<lines[i].length();j++) {
					if(lines[i].charAt(j) == ';') {
						endIndex=j;
						tablica[tabRowIndex][tabColumnIndex]=lines[i].substring(startIndex, endIndex);
						tabColumnIndex++;
						startIndex=j+1;
					}
					if(j == lines[i].length()-1){
						endIndex=j+1;
						tablica[tabRowIndex][tabColumnIndex]=lines[i].substring(startIndex, endIndex);
						tabColumnIndex++;
						startIndex=j+1;
					}
				}
			}
			startIndex=0;
			endIndex=0;
			tabColumnIndex=0;
			tabRowIndex++;
		}


		for(int i=0;i<rowsAmount;i++) {
			for(int j=0;j<columnsAmount;j++) {
					System.out.print(String.format("%15s |",tablica[i][j]));
			}
			System.out.println();
		}


	}

}
