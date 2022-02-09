package main;

import interfata.Controller;

import interfata.View;
import model.Model;

public class MainClass {

    public static void main(String[] args) {
        Model model = new Model();
        View view = new interfata.View(model);
        Controller controller = new interfata.Controller(model, view);

        view.setVisible(true);

    }
}
