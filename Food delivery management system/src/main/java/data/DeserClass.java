package data;

import business.DeliveryService;
import java.io.*;

public class DeserClass {

    public DeliveryService deserial() {
        DeliveryService d = null;
        try {
            FileInputStream fileIn = new FileInputStream("project.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            d = (DeliveryService) in.readObject();
            in.close();
            fileIn.close();
           // System.out.println("\nDeserialized class");
            return d;
        } catch (EOFException exception) {
            return null;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }

    }

}
