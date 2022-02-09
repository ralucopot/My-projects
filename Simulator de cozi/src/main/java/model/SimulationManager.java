package model;

import strategy.SelectionPolicy;
import view.QueueView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class SimulationManager implements Runnable {

    private int timeLimit;
    private int maxProcessingTime;
    private int minProcessingTime;
    private int maxArrivingTime;
    private int minArrivingTime;
    private int numberOfQueues;
    private int numberOfClients;
    private SelectionPolicy selectionPolicy = SelectionPolicy.SHORTEST_TIME;
    private QueueModel model;
    private QueueView view;


    //entity responsible with queue management and client distribution
    private Scheduler scheduler;

    //pool of tasks (client shopping in the store)
    private ArrayList<Client> generatedClients;

    public SimulationManager(int timeLimit, int numberOfClients, int numberOfQueues, int maxProcessingTime, int minProcessingTime, int maxArrivingTime, int minArrivingTime, QueueModel model, QueueView view) {

        generatedClients = new ArrayList<Client>();
        this.model = model;
        this.view = view;
        this.timeLimit = timeLimit;
        this.minProcessingTime = minProcessingTime;
        this.maxProcessingTime = maxProcessingTime;
        this.minArrivingTime = minArrivingTime;
        this.maxArrivingTime = maxArrivingTime;
        this.numberOfQueues = numberOfQueues;
        this.numberOfClients = numberOfClients;
        generatedNRandomTasks(numberOfClients, maxProcessingTime, minProcessingTime, maxArrivingTime, minArrivingTime);
    }


    private void generatedNRandomTasks(int numberOfClients, int maxProcessingTime, int minProcessingTime, int maxArrivingTime, int minArrivingTime) {

        for (int i = 0; i < numberOfClients; i++) {
            Random rand = new Random();
            int ariv = rand.nextInt(maxArrivingTime - minArrivingTime) + minArrivingTime;
            int procc = rand.nextInt(maxProcessingTime - minProcessingTime) + minProcessingTime;
            Client c = new Client(i + 1, ariv, procc);
            generatedClients.add(c);
        }
        Collections.sort(generatedClients);
    }

    public float avgServiceTime() {
        float time = 0;
        for (Client c : generatedClients) {
            time += c.getServiceTime();
        }
        time = (float) (time / numberOfClients);
        return time;
    }

    @Override
    public String toString() {
        String result = "";
        result = model.waitingClientsString(generatedClients) + "\n" + scheduler.toString();
        return result;
    }

    @Override
    public void run() {
        int currentTime = 0, peakHour = 0, maxClients = 0;
        float avgTime = 0, serviceTime = avgServiceTime();
        scheduler = new Scheduler(numberOfQueues, numberOfClients);
        model.golireFisier(model.getFileName());
        while (currentTime <= timeLimit) {
            view.setTime(String.valueOf(currentTime));
            view.setSimulationText(this.toString());
            if (generatedClients.isEmpty()) {
                int emptyQueues = 0;
                for (Server s : scheduler.getServers()) {
                    if (s.getClients().isEmpty())
                        emptyQueues++;
                }
                if (emptyQueues == numberOfQueues) {
                    model.afisare(scheduler, currentTime, generatedClients);
                    break;
                }
            } else {
                for (int i = 0; i < numberOfQueues; i++) {
                    if (i < generatedClients.size() && currentTime >= generatedClients.get(0).getArrivalTime()) {
                        try {
                            scheduler.dispatchClient(generatedClients.get(0));
                            generatedClients.remove(0);
                        } catch (Exception e) {
                            System.out.println("Could not add to queue");
                        }
                        if (i >= generatedClients.size()) {
                            break;
                        }
                    }
                }
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("Interrupted");
            }
            model.afisare(scheduler, currentTime, generatedClients);
            if (maxClients < model.peakHour(scheduler)) {
                maxClients = model.peakHour(scheduler);
                peakHour = currentTime;
            }
            avgTime += (float) scheduler.totalWaitingTime() / numberOfQueues;
            scheduler.decrementServiceTime(currentTime);
            currentTime++;
        }
        model.afisareAnaliza(peakHour, avgTime / currentTime, serviceTime);
        view.setTime(String.valueOf(currentTime));
        view.setSimulationText(this.toString() + "Peak hour " + peakHour + "\n" + "Average waiting time: " + String.format("%.2f", avgTime / currentTime) + "\n" + "Average serving time: " + String.format("%.2f", serviceTime) + "\n");
        scheduler.stop();
    }
}
