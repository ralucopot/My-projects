package view;

import model.QueueModel;
import model.SimulationManager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class QueueController {

    private QueueModel m_model;
    private QueueView m_view;

    public QueueController(QueueModel model, QueueView view) {
        m_model = model;
        m_view = view;

        m_view.addSubmitListener(new SubmitListener());
        m_view.addClearListener(new ClearListener());
    }

    class SubmitListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int simulationTime = Integer.parseInt(m_view.getSimInput());
                int nrOfClients = Integer.parseInt(m_view.getClientsInput());
                int nrOfQueues = Integer.parseInt(m_view.getQueuesInput());
                int maxProcTime = Integer.parseInt(m_view.getMaxServInput());
                int minProcTime = Integer.parseInt(m_view.getMinServInput());
                int maxArrivTime = Integer.parseInt(m_view.getMaxArrivInput());
                int minArrivTime = Integer.parseInt(m_view.getMinArrivInput());
                if(maxProcTime <= minProcTime || maxArrivTime <= minArrivTime){
                    m_view.showError("Invalid time interval!");
                    return;
                }
                SimulationManager simulationManager = new SimulationManager(simulationTime, nrOfClients, nrOfQueues, maxProcTime, minProcTime, maxArrivTime, minArrivTime, m_model, m_view);
                Thread t = new Thread(simulationManager);
                t.start();
                m_view.setSimulationVisible(true);
            } catch (NumberFormatException ex) {
                m_view.showError("Invalid input!");
            }
        }

    }

    class ClearListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            m_view.setClientsInput();
            m_view.setSimInput();
            m_view.setQueuesInput();
            m_view.setMinArrivInput();
            m_view.setMaxArrivInput();
            m_view.setMinServInput();
            m_view.setMaxServInput();
            m_view.setSimulationVisible(false);
        }
    }

}
