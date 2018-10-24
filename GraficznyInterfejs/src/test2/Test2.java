package test2;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

public class Test2 {
	public static void main(String[] args) {
		 
        // creating input stream variables
        FileInputStream fis = null;
        ObjectInputStream ois = null;
 
        // creating List reference to hold AL values after de-serialization 
        List<String> leadersOfHistory = null;
 
        try {
            // reading binary data
            fis = new FileInputStream("SaveArrayList.ser");
 
            // converting binary-data to java-object
            ois = new ObjectInputStream(fis);
 
            // reading object's value and casting ArrayList<String>
            leadersOfHistory = (ArrayList<String>) ois.readObject();
        } 
        catch (FileNotFoundException fnfex) {
            fnfex.printStackTrace();
        }
        catch (IOException ioex) {
            ioex.printStackTrace();
        } 
        catch (ClassNotFoundException ccex) {
            ccex.printStackTrace();
        }
 
        System.out.println("ArrayList object de-serialized from SaveArrayList.ser file\n");
 
        // iterating & printing ArrayList values to console
        for(String leader : leadersOfHistory){
            System.out.println(leader);
        }
    }
}
