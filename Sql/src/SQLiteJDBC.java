import java.sql.*;

public class SQLiteJDBC {

   public static void main( String args[] ) {
      Connection c = null;
      Statement stmt = null;
      
      try {
    	 // === connect to database
         Class.forName("org.sqlite.JDBC");
         c = DriverManager.getConnection("jdbc:sqlite:test.db");
         System.out.println("Opened database successfully");
         // usawiam autocommit
         c.setAutoCommit(false); // c.commit() uzywane do update delete

         // === tworzenie tabeli
         stmt = c.createStatement();
//         String sql = "CREATE TABLE POSTACI " +
//                        "(NAZWISKO           TEXT    NOT NULL, " + 
//                        " ROCZNIK           INT     NOT NULL, " + 
//                        " PENSJA         DOUBLE)"; 
//         stmt.executeUpdate(sql);

         // === sql
         
//         String sql = "INSERT INTO POSTACI (NAZWISKO,ROCZNIK,PENSJA) " +
//                 "VALUES ('Allen', 2004, 15000.00 );"; 
//         stmt.executeUpdate(sql);
//
//         // === sql
//         sql = "INSERT INTO POSTACI (NAZWISKO,ROCZNIK,PENSJA) " +
//                 "VALUES ('Allen2', 1996, 10000.00 );"; 
//         stmt.executeUpdate(sql);
//
//         // === sql insert
//         sql = "INSERT INTO POSTACI (NAZWISKO,ROCZNIK,PENSJA) " +
//                 "VALUES ('Allen3', 2001, 9000.00 );"; 
//         stmt.executeUpdate(sql);
         
         // === select from table
         ResultSet rs = stmt.executeQuery( "SELECT * FROM POSTACI;" );
         
         while ( rs.next() ) {
            String  nazwisko = rs.getString("nazwisko");
            int rocznik  = rs.getInt("rocznik");
            double pensja = rs.getFloat("pensja");
            
            System.out.println( "NAME = " + nazwisko );
            System.out.println( "Yearbook = " + rocznik );
            System.out.println( "SALARY = " + pensja );
            System.out.println();
         }
         rs.close();
         

         System.out.println("Druga:");
         
         // === sql update
//         String sql = "UPDATE POSTACI set PENSJA = ? where ROCZNIK>2000;";
//         PreparedStatement statement = c.prepareStatement(sql);
//         statement.setDouble(1, 500.00);
//         statement.executeUpdate();
//         c.commit();

         // === select from table
         rs = stmt.executeQuery( "SELECT * FROM POSTACI where ROCZNIK>2000;" );
         
         while ( rs.next() ) {
            String  nazwisko = rs.getString("nazwisko");
            int rocznik  = rs.getInt("rocznik");
            double pensja = rs.getFloat("pensja");
            
            String sql = "UPDATE POSTACI set PENSJA = ? where ROCZNIK>2000;";
            PreparedStatement statement = c.prepareStatement(sql);
            statement.setDouble(1, 500.00);
            statement.executeUpdate();
            c.commit();
            
            System.out.println( "NAME = " + nazwisko );
            System.out.println( "Yearbook = " + rocznik );
            System.out.println( "SALARY = " + pensja );
            System.out.println();
         }
         rs.close();
         
         
         stmt.close();
         c.close();
      } catch ( Exception e ) {
         System.err.println( e.getClass().getName() + ": " + e.getMessage() );
         System.exit(0);
      }
      System.out.println("Table created successfully");
   }
}