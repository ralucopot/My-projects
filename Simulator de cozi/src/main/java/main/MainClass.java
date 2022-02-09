package main;

import model.Client;
import model.QueueModel;
import model.SimulationManager;
import view.QueueController;
import view.QueueView;

public class MainClass {

    public static void main(String[] args) {

        QueueModel model = new QueueModel("fisier.txt");
        QueueView view = new QueueView(model);
        QueueController controler = new QueueController(model, view);

        view.setVisible(true);

    }
}
