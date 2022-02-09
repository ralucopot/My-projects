package strategy;

import model.Client;
import model.Scheduler;
import model.Server;

public class ConcreteStrategyTime implements Strategy {

    @Override
    public int addClient(Scheduler scheduler, Client client) {
        int minTime = 100;
        int index = -1;
        for (Server s : scheduler.getServers()) {
            int temp = s.getWaitingPeriod().intValue();
            if (s.getClients().size() < s.getCapacity()) {
                if (minTime > temp) {
                    minTime = temp;
                    index = scheduler.getServers().indexOf(s);
                }
            }
        }
        if (index != -1) {
            scheduler.getServers().get(index).addClient(client);
            return index;
        }
        return -1;
    }
}
