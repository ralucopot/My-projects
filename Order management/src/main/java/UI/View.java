package UI;

import bll.ClientBLL;
import model.Model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class View extends JFrame {

    private Model m_model;
    ClientBLL clientBLL = new ClientBLL();

    //jframe pentru tabele
    protected JFrame clients = new JFrame();
    protected JFrame products = new JFrame();
    protected JFrame orders = new JFrame();

    //butoane client
    private JButton m_insertClientBtn = new JButton("Insert");
    private JButton m_findClientBtn = new JButton(" Find by id ");
    private JButton m_updateClientBtn = new JButton("  Update  ");
    private JButton m_deleteClientBtn = new JButton("Delete");
    private JButton m_viewClientBtn = new JButton("View");
    private JLabel m_backClBtn = new JLabel(" ");
    private JTextField m_clientIdTf = new JTextField(8);
    private JTextField m_clientNameTf = new JTextField(8);
    private JTextField m_clientAddressTf = new JTextField(8);
    private JTable m_clientsTb = clientBLL.findAll();
    private JScrollPane tabelClienti = new JScrollPane(m_clientsTb);

    //butoane produse
    private JButton m_insertProductBtn = new JButton("Insert");
    private JButton m_findProductBtn = new JButton(" Find by id ");
    private JButton m_updateProductBtn = new JButton("  Update  ");
    private JButton m_deleteProductBtn = new JButton("Delete");
    private JButton m_viewProductBtn = new JButton("View");
    private JLabel m_backProdBtn = new JLabel(" ");
    private JTextField m_productIdTf = new JTextField(8);
    private JTextField m_productNameTf = new JTextField(8);
    private JTextField m_productQuantityTf = new JTextField(8);
    private JTextField m_productpriceTf = new JTextField(8);
    private JTable m_productsTb = new JTable();
    private JScrollPane tabelProduse = new JScrollPane(m_productsTb);

    //butoane comenzi
    private JButton m_insertOrderBtn = new JButton("Insert");
    private JButton m_findOrderBtn = new JButton(" Find by id ");
    private JButton m_updateOrderBtn = new JButton("  Update  ");
    private JButton m_deleteOrderBtn = new JButton("Delete");
    private JButton m_viewOrderBtn = new JButton("View");
    private JLabel m_backOrdBtn = new JLabel(" ");
    private JTextField m_orderIdTf = new JTextField(8);
    private JTextField m_quantityTf = new JTextField(8);
    private JTextField m_clOrdIdTf = new JTextField(8);
    private JTextField m_clOrdNameTf = new JTextField(8);
    private JTextField m_prodOrdNameTf = new JTextField(8);
    private JTable m_ordersTb = new JTable();
    private JScrollPane tabelComenzi = new JScrollPane(m_ordersTb);

    //initial
    private JButton m_clientBtn = new JButton("Clients");
    private JButton m_productsBtn = new JButton("Products");
    private JButton m_ordersBtn = new JButton("Orders");

    public View(Model model) {
        m_model = model;

        //panou principal
        JPanel panouPrinc = new JPanel();
        panouPrinc.setBackground(Color.decode("#f9dec3"));

        //layout princ
        FlowLayout fLyout = new FlowLayout();
        fLyout.setVgap(10);
        fLyout.setHgap(40);
        panouPrinc.setLayout(fLyout);

        JLabel title = new JLabel("Chose a table:");
        panouPrinc.add(title);
        panouPrinc.add(m_clientBtn);
        panouPrinc.add(m_productsBtn);
        panouPrinc.add(m_ordersBtn);

        //font
        Font font = new Font("Arial", Font.BOLD, 16);
        Font font2 = new Font("Arial", Font.BOLD, 14);
        title.setFont(font2);
        m_clientBtn.setFont(font);
        m_clientBtn.setPreferredSize(new Dimension(11 * 10, 3 * 10));
        m_productsBtn.setFont(font);
        m_productsBtn.setPreferredSize(new Dimension(11 * 10, 3 * 10));
        m_ordersBtn.setFont(font);
        m_ordersBtn.setPreferredSize(new Dimension(11 * 10, 3 * 10));

        //panou clienti
        JPanel client = new JPanel();
        client.setBackground(Color.decode("#f9dec3"));
        GroupLayout clientLayout = new GroupLayout((client));
        client.setLayout(clientLayout);
        clientLayout.setAutoCreateGaps(true);
        clientLayout.setAutoCreateContainerGaps(true);
        JLabel id_client = new JLabel("       Id: ");
        JLabel name_client = new JLabel("    Name: ");
        JLabel addr_client = new JLabel(" Address: ");
        JLabel empty_client = new JLabel("  ");

        clientLayout.linkSize(SwingConstants.HORIZONTAL, m_updateClientBtn, m_insertClientBtn);
        clientLayout.linkSize(SwingConstants.HORIZONTAL, m_updateClientBtn, m_deleteClientBtn);
        clientLayout.linkSize(SwingConstants.HORIZONTAL, m_updateClientBtn, m_updateClientBtn);
        clientLayout.linkSize(SwingConstants.HORIZONTAL, m_updateClientBtn, m_viewClientBtn);
        m_viewClientBtn.setFont(font2);
        m_deleteClientBtn.setFont(font2);
        m_insertClientBtn.setFont(font2);
        m_updateClientBtn.setFont(font2);
        id_client.setFont(font2);
        name_client.setFont(font2);
        addr_client.setFont(font2);

        clientLayout.setHorizontalGroup(clientLayout.createSequentialGroup()
                .addGroup(clientLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(m_insertClientBtn)
                        .addComponent(id_client))
                .addGroup(clientLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(m_viewClientBtn)
                        .addComponent(m_clientIdTf)
                        .addComponent(addr_client))
                .addGroup(clientLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(m_updateClientBtn)
                        .addComponent(m_backClBtn)
                        .addComponent(name_client)
                        .addComponent(m_clientAddressTf)
                        .addComponent(empty_client))
                .addGroup(clientLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(m_deleteClientBtn)
                        .addComponent(m_clientNameTf)));

        clientLayout.setVerticalGroup(clientLayout.createSequentialGroup()
                .addGroup(clientLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(m_insertClientBtn)
                        .addComponent(m_viewClientBtn)
                        .addComponent(m_updateClientBtn)
                        .addComponent(m_deleteClientBtn))
                .addGroup(clientLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(m_backClBtn))
                .addGroup(clientLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(id_client)
                        .addComponent(m_clientIdTf)
                        .addComponent(name_client)
                        .addComponent(m_clientNameTf))
                .addGroup(clientLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(addr_client)
                        .addComponent(m_clientAddressTf))
                .addGroup(clientLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(empty_client)));

        //panou tabel
        m_clientsTb.setBounds(30, 40, 200, 300);
        clients.setBackground(Color.decode("#f9dec3"));
        tabelClienti.setBackground(Color.decode("#f9dec3"));
        clients.setLayout(new GridLayout(0, 1));
        clients.add(client);
        client.setBackground(Color.decode("#f9dec3"));
        clients.add(tabelClienti);
        clients.setSize(520, 400);
        clients.setTitle("Clients");

        //panou produse
        JPanel product = new JPanel();
        product.setBackground(Color.decode("#f9dec3"));
        GroupLayout productLayout = new GroupLayout((product));
        product.setLayout(productLayout);
        productLayout.setAutoCreateGaps(true);
        productLayout.setAutoCreateContainerGaps(true);
        JLabel id_prod = new JLabel("       Id: ");
        JLabel name_prod = new JLabel("    Name: ");
        JLabel quantity_prod = new JLabel(" Quantity: ");
        JLabel price_prod = new JLabel("   Price: ");
        JLabel empty_prod = new JLabel("  ");

        productLayout.linkSize(SwingConstants.HORIZONTAL, m_updateProductBtn, m_insertProductBtn);

        productLayout.linkSize(SwingConstants.HORIZONTAL, m_updateProductBtn, m_deleteProductBtn);
        productLayout.linkSize(SwingConstants.HORIZONTAL, m_updateProductBtn, m_viewProductBtn);
        m_insertProductBtn.setFont(font2);
        m_updateProductBtn.setFont(font2);
        m_deleteProductBtn.setFont(font2);
        m_viewProductBtn.setFont(font2);
        id_prod.setFont(font2);
        name_prod.setFont(font2);
        quantity_prod.setFont(font2);
        price_prod.setFont(font2);

        productLayout.setHorizontalGroup(productLayout.createSequentialGroup()
                .addGroup(productLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(m_insertProductBtn)
                        .addComponent(id_prod)
                        .addComponent(quantity_prod))
                .addGroup(productLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(m_viewProductBtn)
                        .addComponent(m_productIdTf)
                        .addComponent(m_productQuantityTf)
                        .addComponent(empty_prod))
                .addGroup(productLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(m_updateProductBtn)
                        .addComponent(m_backProdBtn)
                        .addComponent(name_prod)
                        .addComponent(price_prod))
                .addGroup(productLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(m_deleteProductBtn)
                        .addComponent(m_productNameTf)
                        .addComponent(m_productpriceTf)));

        productLayout.setVerticalGroup(productLayout.createSequentialGroup()
                .addGroup(productLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(m_insertProductBtn)
                        .addComponent(m_viewProductBtn)
                        .addComponent(m_updateProductBtn)
                        .addComponent(m_deleteProductBtn))
                .addGroup(productLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(m_backProdBtn))
                .addGroup(productLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(id_prod)
                        .addComponent(m_productIdTf)
                        .addComponent(name_prod)
                        .addComponent(m_productNameTf))
                .addGroup(productLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(quantity_prod)
                        .addComponent(m_productQuantityTf)
                        .addComponent(price_prod)
                        .addComponent(m_productpriceTf))
                .addGroup(productLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(empty_prod)));

        //tabel produse
        products.setLayout(new GridLayout(0, 1));
        products.setBackground(Color.decode("#f9dec3"));
        tabelProduse.setBackground(Color.decode("#f9dec3"));
        products.add(product);
        products.add(tabelProduse);
        products.setSize(520, 400);
        products.setVisible(false);
        products.setTitle("Products");

        //panou comenzi
        JPanel order = new JPanel();
        order.setBackground(Color.decode("#f9dec3"));
        GroupLayout orderLayout = new GroupLayout(order);
        order.setLayout(orderLayout);
        orderLayout.setAutoCreateGaps(true);
        orderLayout.setAutoCreateContainerGaps(true);
        JLabel id_ord = new JLabel("       Id: ");
        JLabel id_ord_client = new JLabel("    Client id: ");
        JLabel name_ord_client = new JLabel(" Name: ");
        JLabel prod_name = new JLabel("   Product: ");
        JLabel prod_quantity = new JLabel("   Quantity: ");

        orderLayout.linkSize(SwingConstants.HORIZONTAL, m_updateOrderBtn, m_insertOrderBtn);
        orderLayout.linkSize(SwingConstants.HORIZONTAL, m_updateOrderBtn, m_deleteOrderBtn);
        orderLayout.linkSize(SwingConstants.HORIZONTAL, m_updateOrderBtn, m_viewOrderBtn);
        m_insertOrderBtn.setFont(font2);
        m_updateOrderBtn.setFont(font2);
        m_deleteOrderBtn.setFont(font2);
        m_viewOrderBtn.setFont(font2);
        id_ord.setFont(font2);
        id_ord_client.setFont(font2);
        name_ord_client.setFont(font2);
        prod_name.setFont(font2);
        prod_quantity.setFont(font2);

        orderLayout.setHorizontalGroup(orderLayout.createSequentialGroup()
                .addGroup(orderLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(m_insertOrderBtn)
                        .addComponent(id_ord)
                        .addComponent(id_ord_client))
                .addGroup(orderLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(m_viewOrderBtn)
                        .addComponent(m_orderIdTf)
                        .addComponent(m_clOrdIdTf)
                        .addComponent(prod_quantity))
                .addGroup(orderLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(m_updateOrderBtn)
                        .addComponent(m_backOrdBtn)
                        .addComponent(name_ord_client)
                        .addComponent(prod_name)
                        .addComponent(m_quantityTf))
                .addGroup(orderLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(m_deleteOrderBtn)
                        .addComponent(m_clOrdNameTf)
                        .addComponent(m_prodOrdNameTf)));

        orderLayout.setVerticalGroup(orderLayout.createSequentialGroup()
                .addGroup(orderLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(m_insertOrderBtn)
                        .addComponent(m_viewOrderBtn)
                        .addComponent(m_updateOrderBtn)
                        .addComponent(m_deleteOrderBtn))
                .addGroup(orderLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(m_backOrdBtn))
                .addGroup(orderLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(id_ord)
                        .addComponent(m_orderIdTf)
                        .addComponent(name_ord_client)
                        .addComponent(m_clOrdNameTf))
                .addGroup(orderLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(id_ord_client)
                        .addComponent(m_clOrdIdTf)
                        .addComponent(prod_name)
                        .addComponent(m_prodOrdNameTf))
                .addGroup(orderLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(prod_quantity)
                        .addComponent(m_quantityTf)));


        orders.setLayout(new GridLayout(0, 1));
        orders.setBackground(Color.decode("#f9dec3"));
        tabelComenzi.setBackground(Color.decode("#f9dec3"));
        orders.add(order);
        orders.add(tabelComenzi);
        orders.setSize(520, 400);
        orders.setVisible(false);
        orders.setTitle("Orders");


        this.setContentPane(panouPrinc);
        panouPrinc.setVisible(true);
        this.setSize(260, 200);
        this.setTitle("Warehouse ");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    void setClientsTable(JTable cTable) {
        m_clientsTb = cTable;
    }

    void setProductsTable(JTable pTable) {
        m_productsTb = pTable;

    }

    void setOrdersTable(JTable oTable) {
        m_ordersTb = oTable;
    }

    String getClientId() {
        return m_clientIdTf.getText();
    }

    String getClientName() {
        return m_clientNameTf.getText();
    }

    String getClientAddr() {
        return m_clientAddressTf.getText();
    }

    String getProdId() {
        return m_productIdTf.getText();
    }

    String getProdName() {
        return m_productNameTf.getText();
    }

    String getProdQuantity() {
        return m_productQuantityTf.getText();
    }

    String getProdPrice() {
        return m_productpriceTf.getText();
    }

    String getOrdId() {
        return m_orderIdTf.getText();
    }

    String getOrdQuantity() {
        return m_quantityTf.getText();
    }

    String getOrdClientId() {
        return m_clOrdIdTf.getText();
    }

    String getOrdClientName() {
        return m_clOrdNameTf.getText();
    }

    String getOrdProdName() {
        return m_prodOrdNameTf.getText();
    }

    void refreshClients(JTable cTable) {
        clients.setVisible(false);
        clients.dispose();
        clients.remove(tabelClienti);
        m_clientsTb = cTable;
        tabelClienti = new JScrollPane(m_clientsTb);
        m_clientsTb.setBounds(30, 40, 200, 300);
        clients.add(tabelClienti);
        clients.setVisible(true);
    }

    void refreshProducts(JTable pTable) {
        products.setVisible(false);
        products.dispose();
        products.remove(tabelProduse);
        m_productsTb = pTable;
        tabelProduse = new JScrollPane(m_productsTb);
        m_productsTb.setBounds(30, 40, 200, 300);
        products.add(tabelProduse);
        products.setVisible(true);
    }

    void refreshOrders(JTable oTable) {
        orders.setVisible(false);
        orders.dispose();
        orders.remove(tabelComenzi);
        m_ordersTb = oTable;
        tabelComenzi = new JScrollPane(m_ordersTb);
        m_ordersTb.setBounds(30, 40, 200, 300);
        orders.add(tabelComenzi);
        orders.setVisible(true);
    }

    void setClientsVisible(boolean visible) {
        clients.setVisible(visible);
    }

    void addInsertClientListener(ActionListener ial) {
        m_insertClientBtn.addActionListener(ial);
    }

    void addUpdateClientListener(ActionListener ual) {
        m_updateClientBtn.addActionListener(ual);
    }

    void addDeleteClientListener(ActionListener dal) {
        m_deleteClientBtn.addActionListener(dal);
    }

    void addViewClientListener(ActionListener val) {
        m_viewClientBtn.addActionListener(val);
    }

    void addInsertProdListener(ActionListener ial) {
        m_insertProductBtn.addActionListener(ial);
    }

    void addUpdateProdListener(ActionListener ual) {
        m_updateProductBtn.addActionListener(ual);
    }

    void addDeleteProdListener(ActionListener dal) {
        m_deleteProductBtn.addActionListener(dal);
    }

    void addViewProdListener(ActionListener val) {
        m_viewProductBtn.addActionListener(val);
    }

    void addInsertOrdListener(ActionListener ial) {
        m_insertOrderBtn.addActionListener(ial);
    }

    void addUpdateOrdListener(ActionListener ual) {
        m_updateOrderBtn.addActionListener(ual);
    }

    void addDeleteOrdListener(ActionListener dal) {
        m_deleteOrderBtn.addActionListener(dal);
    }

    void addViewOrdListener(ActionListener val) {
        m_viewOrderBtn.addActionListener(val);
    }

    void addClientListener(ActionListener cal) {
        m_clientBtn.addActionListener(cal);
    }

    void addProdListener(ActionListener pal) {
        m_productsBtn.addActionListener(pal);
    }

    void addOrdListener(ActionListener bal) {
        m_ordersBtn.addActionListener(bal);
    }

    void showError(String errMessage) {
        JOptionPane.showMessageDialog(this, errMessage);
    }

}
