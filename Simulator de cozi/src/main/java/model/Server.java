package model;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Server implements Runnable {

    private ArrayBlockingQueue<Client> clients;
    private AtomicInteger waitingPeriod;
    private int totalWaitedPeriod;
    private int capacity;
    private int index;
    private boolean running = true;

    public Server(int capacity, int index) {

        this.capacity = capacity;
        this.index = index;
        clients = new ArrayBlockingQueue<Client>(capacity);
        waitingPeriod = new AtomicInteger(0);
        this.totalWaitedPeriod = 0;
    }

    public int getTotalWaitedPeriod() {
        return this.totalWaitedPeriod;
    }

    public int getSize(){
        return clients.size();
    }

    public void setTotalWaitedPeriod(int totalWaitedPeriod) {
        this.totalWaitedPeriod = totalWaitedPeriod;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void addClient(Client newClient) {
        clients.add(newClient);
        waitingPeriod.getAndAdd(newClient.getServiceTime());
    }

    @Override
    public void run() {
        Client temp = new Client(0, 0, 0);

        while (running) {
            if (!clients.isEmpty()) {
                try {
                    temp = clients.peek();
                    Thread.sleep(temp.getServiceTime() * 1000);
                    waitingPeriod.decrementAndGet();
                } catch (InterruptedException e) {
                    System.out.println("Interruption");
                }
            } else {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println("Interruption");
                }
            }
        }

    }

    public void stop() {

        this.running = false;
    }

    public int queueWaitingTime() {
        if (clients.size() <= 0) {
            return 0;
        }
        int time = 0;
        int index = 0;
        for (Client c : clients) {
            if (index < clients.size()) {
                time += c.getServiceTime();
            }
            index++;
        }
        return time;
    }

    public BlockingQueue<Client> getClients() {
        return clients;
    }

    public void decrementWaitingPeriod() {
        waitingPeriod.getAndDecrement();
    }


    @Override
    public String toString() {
        String result = "";
        if (this.clients.isEmpty()) {
            result = "closed";
            return result;
        }
        for (Client c : clients) {
            result = result + c.toString() + "; ";
        }
        return result;
    }

    public AtomicInteger getWaitingPeriod() {
        return waitingPeriod;
    }

    public void setWaitingPeriod(int waitingPeriod) {

        this.waitingPeriod = new AtomicInteger(waitingPeriod);
    }

}
