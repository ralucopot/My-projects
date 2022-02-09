package presentation;

import business.DeliveryService;
import business.MenuItem;
import model.Model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ClientUI extends JFrame {

    private Model m_model;

    //panou principal
    private String[] crit = {"keyword", "rating", "calories", "proteins", "fats", "sodium", "price"};
    private JButton m_viewBtn = new JButton("View menu");
    private JButton m_addBtn = new JButton("Add to cart");
    private JButton m_orderBtn = new JButton("Place order");
    private JButton m_searchBtn = new JButton("Search");
    private JButton m_filterBtn = new JButton("Add filter");
    private JButton m_logOutBtn = new JButton("Log out");
    private JLabel search = new JLabel("Search by: ");
    private JLabel cart = new JLabel("Your cart: ");
    private JLabel totalPrice = new JLabel("Total order price: ");
    private JTextField m_searchTf = new JTextField();
    private JTextField m_priceTf = new JTextField(7);
    private JTextArea m_orderTa = new JTextArea();
    private JComboBox m_menuCb;
    private JComboBox m_searchCb;

    //rez cautare
    private JFrame result = new JFrame();
    private JTable m_productsTb;
    private JScrollPane tabelProduse = new JScrollPane(m_productsTb);

    public ClientUI(Model model) {
        m_model = model;
        DeliveryService d = new DeliveryService();
        List<MenuItem> menu = d.getMenu();
        String[] products = m_model.getNames(false);
        m_menuCb = new JComboBox(products);
        m_menuCb.setPrototypeDisplayValue("Chared Squid and Conch with Soused Green Figs and Tomato                                ");
        m_searchCb = new JComboBox(crit);
        m_orderTa.setEditable(false);
        m_searchTf.setColumns(7);

        JPanel panouOp = new JPanel();
        JPanel panouClient = new JPanel();
        GroupLayout clientLayout = new GroupLayout(panouOp);
        panouOp.setLayout(clientLayout);
        clientLayout.setAutoCreateGaps(true);
        clientLayout.setAutoCreateContainerGaps(true);
        Font font = new Font("Times new roman", Font.PLAIN, 14);
        m_viewBtn.setFont(font);
        m_addBtn.setFont(font);
        m_orderBtn.setFont(font);
        m_searchBtn.setFont(font);
        search.setFont(font);
        cart.setFont(font);
        totalPrice.setFont(font);
        m_searchTf.setFont(font);
        m_priceTf.setFont(font);
        m_orderTa.setFont(font);
        m_menuCb.setFont(font);
        m_searchCb.setFont(font);

        clientLayout.setHorizontalGroup(clientLayout.createSequentialGroup()
                .addGroup(clientLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(search)
                        .addComponent(m_menuCb)
                        .addComponent(m_viewBtn)
                        .addComponent(cart))
                .addGroup(clientLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(m_searchCb)
                        .addComponent(m_addBtn))
                .addGroup(clientLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(m_searchTf))
                .addGroup(clientLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(m_searchBtn))
                .addGroup(clientLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(m_filterBtn)));

        clientLayout.setVerticalGroup(clientLayout.createSequentialGroup()
                .addGroup(clientLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(search)
                        .addComponent(m_searchCb)
                        .addComponent(m_searchTf)
                        .addComponent(m_searchBtn)
                        .addComponent(m_filterBtn)
                )
                .addGroup(clientLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(m_menuCb)
                        .addComponent(m_addBtn))
                .addGroup(clientLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(m_viewBtn))
                .addGroup(clientLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(cart)));

        JPanel price = new JPanel();
        price.setLayout(new FlowLayout());
        price.add(m_orderBtn);
        price.add(totalPrice);
        price.add(m_priceTf);
        price.add(m_logOutBtn);
        m_priceTf.setEditable(false);

        //frame tabel
        result.setLayout(new GridLayout(0, 1));
        // m_productsTb = m_model.findAll((ArrayList<MenuItem>) m_model.d.getMenu());
        m_productsTb = m_model.findAll(new ArrayList<MenuItem>(), false);
        m_productsTb.setFont(font);
        m_filterBtn.setFont(font);
        m_logOutBtn.setFont(font);
        m_productsTb.setBounds(3, 4, 300, 300);
        tabelProduse.setViewportView(m_productsTb);
        result.add(tabelProduse);
        // result.setVisible(true);
        result.setSize(980, 500);

        panouClient.setLayout(new GridLayout(0, 1));
        panouClient.add(panouOp);
        panouClient.add(m_orderTa);
        panouClient.add(price);
        this.setContentPane(panouClient);
        panouClient.setVisible(true);
        this.setSize(960, 420);
        this.setTitle("Client ");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    void showError(String errMessage) {
        JOptionPane.showMessageDialog(this, errMessage);
    }

    String getSearchInput() {
        return m_searchTf.getText();
    }

    void setTotalPrice(String price) {
        m_priceTf.setText(price);
    }

    void setOrder(String order) {
        m_orderTa.append(order + "\n");
    }

    void emptyOrder() {
        m_orderTa.setText("");
    }

    String getSearchCrit() {
        String search = m_searchCb.getSelectedItem().toString();
        return search;
    }

    String getMenuItem() {
        String item = m_menuCb.getSelectedItem().toString();
        return item;
    }

    void addViewListener(ActionListener aal) {
        m_viewBtn.addActionListener(aal);
    }

    void addAddItemListener(ActionListener aal) {
        m_addBtn.addActionListener(aal);
    }

    void addOrderListener(ActionListener aal) {
        m_orderBtn.addActionListener(aal);
    }

    void addSearchListener(ActionListener aal) {
        m_searchBtn.addActionListener(aal);
    }

    void addFilterListener(ActionListener aal) {
        m_filterBtn.addActionListener(aal);
    }

    void addLogOutListener(ActionListener aal) {
        m_logOutBtn.addActionListener(aal);
    }


    void setAllTable(JTable table) {
        tabelProduse.remove(m_productsTb);
        m_productsTb.removeAll();
        Font font = new Font("Times new roman", Font.PLAIN, 14);
        m_productsTb = table;//metoda pt search
        tabelProduse.add(m_productsTb);
        m_productsTb.setFont(font);
        tabelProduse.setViewportView(m_productsTb);
    }

    void setSearchTable(String[] search) {
        Font font = new Font("Times new roman", Font.PLAIN, 14);
        m_productsTb = m_model.findAll((ArrayList<MenuItem>) m_model.d.searchBy(search), true);//metoda pt search
        m_productsTb.setFont(font);
        tabelProduse.setViewportView(m_productsTb);
    }

    void showRes(boolean visible) {
        result.setVisible(visible);
    }

    void setMenuCb() {
        String[] products = m_model.getNames(true);
        m_menuCb.removeAllItems();
        for (String s : products) {
            m_menuCb.addItem(s);
        }
        m_menuCb.setPrototypeDisplayValue("Chared Squid and Conch with Soused Green Figs and Tomato                                ");
    }

    void showSuccessMessage(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Success", JOptionPane.INFORMATION_MESSAGE);
    }
}
