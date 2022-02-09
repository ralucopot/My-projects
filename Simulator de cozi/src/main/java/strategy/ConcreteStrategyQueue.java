package strategy;

import model.Client;
import model.Scheduler;

public class ConcreteStrategyQueue implements Strategy {

    @Override
    public int addClient(Scheduler scheduler, Client client) {
        int minElements = Integer.MAX_VALUE;
        int index = -1;
        for (int i = 0; i < scheduler.getServers().size(); i++) {
            int elems = scheduler.getServers().get(i).getClients().size();
            if (scheduler.getServers().get(i).getClients().size() < scheduler.getServers().get(i).getCapacity()) {
                if (minElements > elems) {
                    minElements = elems;
                    index = i;
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
