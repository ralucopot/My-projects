package model;

import java.util.concurrent.atomic.AtomicInteger;

public class Client implements Comparable<Client> {

    private int ID;
    private int arrivalTime;
    private int serviceTime;

    public Client(int ID, int arrivalTime, int serviceTime) {
        this.ID = ID;
        this.arrivalTime = arrivalTime;
        this.serviceTime = serviceTime;
    }

    public int getID() {

        return ID;
    }

    public void setID(int ID) {

        this.ID = ID;
    }

    public int getArrivalTime() {

        return arrivalTime;
    }

    public void setArrivalTime(int arrivalTime) {

        this.arrivalTime = arrivalTime;
    }

    public int getServiceTime() {

        return serviceTime;
    }

    public void setServiceTime(int serviceTime) {

        this.serviceTime = serviceTime;
    }

    public void decreaseServiceTime() {
        this.serviceTime--;
    }

    public int finishTime(AtomicInteger waitingPeriod) {
        int wp = waitingPeriod.get();
        int finish = arrivalTime + serviceTime + wp;
        return finish;
    }

    @Override
    public String toString() {
        int timp = getServiceTime();
        return "(" + getID() + ", " + getArrivalTime() + ", " + getServiceTime() + ")";
    }

    @Override
    public int compareTo(Client c) {
        if (this.arrivalTime < c.arrivalTime) {
            return -1;
        }
        if (this.arrivalTime > c.arrivalTime) {
            return 1;
        }
        return 0;
    }
}
