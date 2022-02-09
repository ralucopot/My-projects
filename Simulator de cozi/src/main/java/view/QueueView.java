package view;

import model.QueueModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;


public class QueueView extends JFrame {

    //simulare
    private JTextField m_timeTf = new JTextField("");
    private JTextArea m_simulationTa = new JTextArea("");

    //initial
    private JTextField m_clientsTf = new JTextField("");
    private JTextField m_queuesTf = new JTextField("");
    private JTextField m_simTimeTf = new JTextField("");
    private JTextField m_minArrivTf = new JTextField("");
    private JTextField m_maxArrivTf = new JTextField("");
    private JTextField m_minServTf = new JTextField("");
    private JTextField m_maxServTf = new JTextField("");
    private JButton m_clearBtn = new JButton("Clear");
    private JButton m_submitBtn = new JButton("Submit");
    JFrame simulation = new JFrame();

    private QueueModel m_model;

    public QueueView(QueueModel model) {
        m_model = model;

        //panou initial
        JPanel panouPrinc = new JPanel();
        panouPrinc.setLayout(new BoxLayout(panouPrinc, BoxLayout.Y_AXIS));
        panouPrinc.setBackground(Color.decode("#9a57ff"));

        //panou input
        JPanel input = new JPanel();
        input.setBackground(Color.decode("#9a57ff"));
        //layout input
        GroupLayout inputLayout = new GroupLayout((input));
        input.setLayout(inputLayout);
        inputLayout.setAutoCreateGaps(true);
        inputLayout.setAutoCreateContainerGaps(true);

        //frame initial
        JLabel nrClienti = new JLabel("Clients: ");
        JLabel nrCozi = new JLabel("Queues: ");
        JLabel simTime = new JLabel("Simulation time: ");
        JLabel minArrival = new JLabel("Minimum arrival time: ");
        JLabel maxArrival = new JLabel("Maximum arrival time: ");
        JLabel minServe = new JLabel("Minimum serving time: ");
        JLabel maxServe = new JLabel("Maximum serving time: ");

        //font
        Font font = new Font("Arial", Font.BOLD, 16);
        nrClienti.setFont(font);
        nrCozi.setFont(font);
        simTime.setFont(font);
        minArrival.setFont(font);
        maxArrival.setFont(font);
        minServe.setFont(font);
        maxServe.setFont(font);
        m_clientsTf.setFont(font);
        m_queuesTf.setFont(font);
        m_simTimeTf.setFont(font);
        m_minArrivTf.setFont(font);
        m_maxArrivTf.setFont(font);
        m_minServTf.setFont(font);
        m_maxServTf.setFont(font);
        m_clearBtn.setFont(font);
        m_submitBtn.setFont(font);


        //size
        m_clientsTf.setColumns(7);
        m_clearBtn.setSize(40, 12);
        inputLayout.linkSize(SwingConstants.HORIZONTAL, m_clientsTf, m_queuesTf);
        inputLayout.linkSize(SwingConstants.HORIZONTAL, m_clientsTf, m_simTimeTf);
        inputLayout.linkSize(SwingConstants.HORIZONTAL, m_clientsTf, m_minArrivTf);
        inputLayout.linkSize(SwingConstants.HORIZONTAL, m_clientsTf, m_maxArrivTf);
        inputLayout.linkSize(SwingConstants.HORIZONTAL, m_clientsTf, m_minServTf);
        inputLayout.linkSize(SwingConstants.HORIZONTAL, m_clientsTf, m_maxServTf);


        inputLayout.setHorizontalGroup(inputLayout.createSequentialGroup()
                .addGroup(inputLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(nrClienti)
                        .addComponent(minArrival)
                        .addComponent(minServe))
                .addGroup(inputLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(m_clientsTf)
                        .addComponent(m_minArrivTf)
                        .addComponent(m_minServTf)
                        .addComponent(simTime))
                .addGroup(inputLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(nrCozi)
                        .addComponent(maxArrival)
                        .addComponent(maxServe)
                        .addComponent(m_simTimeTf))
                .addGroup(inputLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(m_queuesTf)
                        .addComponent(m_maxArrivTf)
                        .addComponent(m_maxServTf)));

        inputLayout.setVerticalGroup(inputLayout.createSequentialGroup()
                .addGroup(inputLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(nrClienti)
                        .addComponent(m_clientsTf)
                        .addComponent(nrCozi)
                        .addComponent(m_queuesTf))
                .addGroup(inputLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(minArrival)
                        .addComponent(m_minArrivTf)
                        .addComponent(maxArrival)
                        .addComponent(m_maxArrivTf))
                .addGroup(inputLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(minServe)
                        .addComponent(m_minServTf)
                        .addComponent(maxServe)
                        .addComponent(m_maxServTf))
                .addGroup(inputLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(simTime)
                        .addComponent(m_simTimeTf)));

        //panou butoane
        JPanel buton = new JPanel();
        buton.setBackground(Color.decode("#9a57ff"));
        //layout butoane
        GroupLayout butonLayout = new GroupLayout((buton));
        buton.setLayout(butonLayout);
        butonLayout.setAutoCreateGaps(true);
        butonLayout.setAutoCreateContainerGaps(true);

        butonLayout.setVerticalGroup(butonLayout.createSequentialGroup()
                .addGroup(butonLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(m_clearBtn)
                        .addComponent(m_submitBtn)));

        butonLayout.setHorizontalGroup(butonLayout.createSequentialGroup()
                .addGroup(butonLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(m_clearBtn))
                .addGroup(butonLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(m_submitBtn)));


        //frame simulare
        JPanel sim = new JPanel();


        JPanel time = new JPanel();
        time.setLayout(new FlowLayout());
        time.setSize(700, 50);
        JLabel timp = new JLabel("Time: ");

        m_timeTf.setFont(font);
        m_timeTf.setColumns(30);
        m_timeTf.setEditable(false);
        timp.setFont(font);
        m_simulationTa.setFont(font);
        m_simulationTa.setColumns(51);
        m_simulationTa.setEditable(false);
        m_simulationTa.setLineWrap(true);
        m_simulationTa.setWrapStyleWord(true);

        time.add(timp);
        time.add(m_timeTf);
        sim.add(m_simulationTa);

        sim.setSize(700, 350);
        SpringLayout springLayout = new SpringLayout();
        simulation.setLayout(springLayout);
        springLayout.putConstraint(SpringLayout.NORTH, sim,
                30,
                SpringLayout.NORTH, time);

        simulation.setSize(700, 350);
        simulation.setBackground(Color.decode("#03d7fc"));
        simulation.add(time);
        simulation.add(sim);
        simulation.setTitle("Simulation");


        this.setContentPane(panouPrinc);
        panouPrinc.add(input);
        panouPrinc.add(buton);
        panouPrinc.setVisible(true);
        this.setSize(700, 230);
        this.setTitle("Queues simulator");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    public void setTime(String time) {
        m_timeTf.setText(time);
    }

    public void setSimulationText(String text) {
        m_simulationTa.setText(text);
    }

    void setSimulationVisible(boolean visible) {
        simulation.setVisible(visible);
    }

    String getClientsInput() {
        return m_clientsTf.getText();
    }

    String getQueuesInput() {
        return m_queuesTf.getText();
    }

    String getSimInput() {
        return m_simTimeTf.getText();
    }

    String getMinArrivInput() {
        return m_minArrivTf.getText();
    }

    String getMaxArrivInput() {
        return m_maxArrivTf.getText();
    }

    String getMinServInput() {
        return m_minServTf.getText();
    }

    String getMaxServInput() {
        return m_maxServTf.getText();
    }

    void setClientsInput() {
        m_clientsTf.setText("");
    }

    void setQueuesInput() {
        m_queuesTf.setText("");
    }

    void setSimInput() {
        m_simTimeTf.setText("");
    }

    void setMinArrivInput() {
        m_minArrivTf.setText("");
    }

    void setMaxArrivInput() {
        m_maxArrivTf.setText("");
    }

    void setMinServInput() {
        m_minServTf.setText("");
    }

    void setMaxServInput() {
        m_maxServTf.setText("");
    }

    void showError(String errMessage) {
        JOptionPane.showMessageDialog(this, errMessage);
    }

    void addSubmitListener(ActionListener sal) {
        m_submitBtn.addActionListener(sal);
    }

    void addClearListener(ActionListener cal) {
        m_clearBtn.addActionListener(cal);
    }

}
