package main;

import business.*;
import data.SerializeClass;
import model.Model;
import presentation.*;

public class MainClass {
    public static void main(String[] args) {

        DeliveryService d = new DeliveryService();
        //SerializeClass s = new SerializeClass(d);
        Model model = new Model(d);
        ClientUI view1 = new ClientUI(model);
        AdminUI view2 = new AdminUI(model);
        LogInView view3 = new LogInView(model);
        EmployeeUI view4 = new EmployeeUI(model);
        Controller controller = new Controller(model, view3, view2, view1, view4);
        view3.setVisible(true);
        // view4.setVisible(true);
        d.addPropertyChangeListener(view4);

    }
}