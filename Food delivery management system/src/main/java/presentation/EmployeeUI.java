package presentation;

import business.Order;
import model.Model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class EmployeeUI extends JFrame implements PropertyChangeListener {

    private Model m_model;

    private JTextArea m_orderTa = new JTextArea();
    private JButton m_logOutBtn = new JButton("Log out");
    private JLabel orders = new JLabel("Pending orders: ");
    private Order order;

    public EmployeeUI(Model model) {
        m_model = model;

        Font font = new Font("Times new roman", Font.PLAIN, 16);
        Font font2 = new Font("Times new roman", Font.PLAIN, 26);

        JPanel panouPrinc = new JPanel();
        GridLayout gridLayout = new GridLayout(0, 1);
        gridLayout.setHgap(5);
        gridLayout.setVgap(10);
        panouPrinc.setLayout(new FlowLayout());
        m_orderTa.setEditable(false);
        m_orderTa.setPreferredSize(new Dimension(500, 200));

        JPanel label = new JPanel();
        label.setLayout(new FlowLayout());
        // label.add(title);
        label.add(orders);
        JPanel tArea = new JPanel();
        tArea.setLayout(new FlowLayout());
        tArea.add(m_orderTa);
        JPanel button = new JPanel();
        button.setLayout(new FlowLayout());
        button.add(m_logOutBtn);

        m_orderTa.setFont(font);
        m_orderTa.setLineWrap(true);
        m_orderTa.setWrapStyleWord(true);

        m_logOutBtn.setFont(font);
        orders.setFont(font2);

        panouPrinc.add(label);
        panouPrinc.add(tArea);
        panouPrinc.add(button);

        this.setContentPane(panouPrinc);
        panouPrinc.setVisible(true);
        this.setSize(520, 390);
        this.setTitle("Employeee ");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    void addLogOutListener(ActionListener aal) {
        m_logOutBtn.addActionListener(aal);
    }

    void setOrdertxt(String msg) {
        m_orderTa.setText("");
        m_orderTa.setText(msg);
        System.out.println(msg);
    }

    private void setOrder(Order newOrder) {
        order = null;
        order = newOrder;
    }

    public void propertyChange(PropertyChangeEvent evt) {
        this.setOrder((Order) evt.getNewValue());
        setOrdertxt(order.toString());
    }

}
