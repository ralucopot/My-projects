package strategy;

import model.Client;
import model.Scheduler;

public interface Strategy {
    public int addClient(Scheduler scheduler, Client client);
}
