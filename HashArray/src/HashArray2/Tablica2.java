package HashArray2;

import java.io.*;                                                                                            

import java.util.Arrays;                                                                                     
import java.util.stream.Collectors;                                                                          
                                                                                                             
public class Tablica2 {                                                                                         
        static int m = 4013; // wielkosc Tablicicy                                                           
                                                                                                             
        // haszowanie modularne                                                                              
        static int h(int k) {                                                                                
                return k % m;                                                                                
        }                                                                                                    
                                                                                                             
        // adresowanie liniowe                                                                               
        // static int H(Integer A[], int k, int index) {                                                     
        //      return (h1(k) + index) % m;                                                                  
        // }                                                                                                 
  //                                                                                                         
        // static void funkcja(Integer A[], int k) {                                                         
        //      int index = 0;                                                                               
        //      int adres = H(A, k, index);                                                                  
        //      // System.out.print("Liczba: " + k);                                                         
        //      // System.out.print(" ,adres: " + adres);                                                    
        //      // while (A[adres] != null) {                                                                
        //      //      index++;                                                                             
        //      //      adres = H(A, k, index);                                                              
        //      //      System.out.print(" ,adres: " + adres);                                               
        //      // }                                                                                         
        //      A[adres]++;                                                                                  
        //      // System.out.println();                                                                     
        // }                                                                                                 
                                                                                                             
        public static void main(String[] args) {                                                             
                                                                                                             
    Integer T[] = new Integer[m];                                                                            
    String slowa[] = new String[m];                                                                          
                                                                                                             
    String fileName = "3700.txt";                                                                            
    String line = null;                                                                                      
    int index=0;                                                                                             
                                                                                                             
    int value=0;                                                                                             
                int ilosc_slow=0;                                                                            
                int ile_zer=0;                                                                               
                int max_wart=0;                                                                              
                int ile_razem=0;                                                                             
                int ile_dodalem=0;                                                                           
                                                                                                             
                // wczytywanie wartosci                                                                      
    try {                                                                                                    
        FileReader fileReader = new FileReader(fileName);                                                    
        BufferedReader bufferedReader = new BufferedReader(fileReader);                                      
                                                                                                             
        while((line = bufferedReader.readLine()) != null) {                                                  
          slowa[index]=line;                                                                                 
          index++;                                                                                           
        }                                                                                                    
                                                                                                             
        bufferedReader.close();                                                                              
    }                                                                                                        
    catch(FileNotFoundException ex) {                                                                        
        System.out.println("Unable to open file '" + fileName + "'");                                        
    }                                                                                                        
    catch(IOException ex) {                                                                                  
        System.out.println("Error reading file '" + fileName + "'");                                         
    }                                                                                                        
                                                                                                             
                // liczenie ilosci slow                                                                      
                for(int j = 0; j < slowa.length; j++) {                                                      
                                if(slowa[j] != null && slowa[j].length() > 0) {                              
                                        ilosc_slow++;                                                        
                                }                                                                            
                 }                                                                                           
                                                                                                             
    Integer wartosci[] = new Integer[ilosc_slow];                                                            
                                                                                                             
                                                                                                             
                                                                                                             
                // System.out.println("slowa: ");                                                            
    // System.out.println(Arrays.toString(slowa));                                                           
    for(int i=0;i<ilosc_slow;i++){                                                                           
      for(int j=0;j<slowa[i].length();j++){                                                                  
        value = (value+(int) slowa[i].charAt(j)) * 2;                                                        
      }                                                                                                      
      wartosci[i]= value;                                                                                    
                        value=0;                                                                             
    }                                                                                                        
                                                                                                             
                System.out.println("wartosci: ");                                                            
                System.out.println(Arrays.toString(wartosci));                                               
                                                                                                             
                // zerowanie T                                                                               
                for(int i=0;i<m;i++){                                                                        
                        T[i]=0;                                                                              
                }                                                                                            
                                                                                                             
                for(int i=0;i<wartosci.length;i++){                                                          
                        T[h(wartosci[i])]++;                                                                 
                }                                                                                            
                                                                                                             
                System.out.println("T: ");                                                                   
                System.out.println(Arrays.toString(T));                                                      
                                                                                                             
                                                                                                             
                for(int i=0;i<T.length;i++){                                                                 
                        if(T[i] == 0){                                                                       
                                ile_zer++;                                                                   
                        }                                                                                    
                }                                                                                            
                                                                                                             
                for(int i=0;i<T.length;i++){                                                                 
                        if(T[i] > max_wart){                                                                 
                                max_wart=T[i];                                                               
                        }                                                                                    
                }                                                                                            
                                                                                                             
                for(int i=0;i<T.length;i++){                                                                 
                        if(T[i] != 0){                                                                       
                                ile_razem+=T[i];                                                             
                                ile_dodalem++;                                                               
                        }                                                                                    
                }                                                                                            
                                                                                                             
                double srednia = (double) ile_razem/(double) ile_dodalem;                                    
                                                                                                             
                                                                                                             
                System.out.println("Ilosc zerowych pozycji: " + ile_zer);                                    
                System.out.println("Maksymalna wartosc: " + max_wart);                                       
                System.out.println("Srednia: " + srednia);                                                   
                                                                                                             
                                                                                                             
        }                                                                                                    
}     