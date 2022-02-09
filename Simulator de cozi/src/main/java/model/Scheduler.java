package model;

import strategy.ConcreteStrategyQueue;
import strategy.ConcreteStrategyTime;
import strategy.SelectionPolicy;
import strategy.Strategy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Scheduler {

    private List<Server> queues;
    private int maxNoServer;
    private int maxClientsPerServer;
    private int totalWaitedTime;
    private Strategy strategy;

    public Scheduler(int maxNoServer, int maxClientsPerServer) {

        queues = Collections.synchronizedList(new ArrayList<>(maxClientsPerServer));
        strategy = new ConcreteStrategyTime();

        for (int i = 0; i < maxNoServer; i++) {

            Server serv = new Server(maxClientsPerServer, i + 1);
            queues.add(serv);
            Thread thread = new Thread(null, null, String.valueOf(i));
        }
    }

    public void changeStrategy(SelectionPolicy policy) {

        if (policy == SelectionPolicy.SHORTEST_QUEUE) {
            strategy = new ConcreteStrategyQueue();
        }

        if (policy == SelectionPolicy.SHORTEST_TIME) {
            strategy = new ConcreteStrategyTime();
        }
    }

    public int totalWaitingTime() {
        int time = 0;
        for (Server s : queues) {
            time += s.queueWaitingTime();
        }
        return time;
    }

    public void dispatchClient(Client client) throws Exception {

        int index = strategy.addClient(this, client);

        if (index == -1) {
            throw new Exception("Could not add to queue!");
        }
        index++;
    }

    public List<Server> getServers() {

        return queues;
    }


    public void decrementServiceTime(int time) {
        for (Server s : queues) {
            if (s.getClients() != null) {
                int index = 0;
               for (Client c : s.getClients()) {

                    if (index == 0) {
                        if (c.getServiceTime() <= 1) {
                            s.getClients().remove(c);
                        } else {
                            if (c.getArrivalTime() <= time) {
                                c.decreaseServiceTime();
                            }
                        }
                    }
                    index++;
                }
                int timp = s.getWaitingPeriod().getAcquire();
                if (timp > 0)
                    s.decrementWaitingPeriod();
            }
        }
    }

    public void stop() {
        for (Server s : queues) {
            s.stop();
        }
    }

    @Override
    public String toString() {

        String result = "";
        for (Server s : this.getServers()) {
            result += "Queue " + s.getIndex() + ": " + s.toString() + "\n";
        }
        return result;
    }

}
