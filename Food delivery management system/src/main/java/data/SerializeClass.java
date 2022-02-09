package data;

import business.DeliveryService;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class SerializeClass {

    public DeliveryService d;

    public SerializeClass(DeliveryService d) {
        this.d = d;
    }

    public void ser() {
        try {
            FileOutputStream fileout = new FileOutputStream("project.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileout);
            out.writeObject(d);
            out.close();
            fileout.close();
            System.out.print("Serialized data is saved in project.ser");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
