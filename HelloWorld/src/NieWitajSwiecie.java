
 import javax.swing.*;
 import java.awt.*;
 
 public class NieWitajSwiecie
 {  
    public static void main(String[] args)
    {  
       RamkaNieWitajSwiecie ramka = new RamkaNieWitajSwiecie();
       ramka.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       ramka.setVisible(true);
       //ramka.show();
    }
 }
 
 /**
    Ramka , zawierajaca panel wiadomosci
 */
 class RamkaNieWitajSwiecie extends JFrame
 {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public RamkaNieWitajSwiecie()
    {
       setTitle("NieWitajSwiecie");
       setSize(SZEROKOSC, WYSOKOSC);
 
       // dolacz panel do ramki
 
       PanelNieWitajSwiecie panel = new PanelNieWitajSwiecie();
       Container powZawartosci = getContentPane();
       powZawartosci.add(panel);
    }
 
    public static final int SZEROKOSC = 300;
    public static final int WYSOKOSC = 200;  
 }
 
 /**
    Panel, wyswietlajacy wiadomosc
 */
 class PanelNieWitajSwiecie extends JPanel
 {  
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public void paintComponent(Graphics g)
    {  
       super.paintComponent(g);
       	
       g.drawString("To nie jest program 'Witaj, Swiecie'", 
          WIADOMOSC_X, WIADOMOSC_Y);   
    }
 
    public static final int WIADOMOSC_X = 55;
    public static final int WIADOMOSC_Y = 75;
 }
