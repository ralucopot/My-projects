package presentation;

import model.Model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class AdminUI extends JFrame {

    private Model m_model;

    //main panel
    private JButton m_importBtn = new JButton("Import products");
    private JButton m_addBtn = new JButton("Add new products");
    private JButton m_deleteBtn = new JButton("Delete products");
    private JButton m_modifyBtn = new JButton("Modify products");
    private JButton m_compositeBtn = new JButton("New composite products");
    private JButton m_logOutBtn = new JButton("Log out");
    private JButton m_serBtn = new JButton("Save");
    private JTextField m_nameTf = new JTextField();
    private JTextField m_ratingTf = new JTextField();
    private JTextField m_caloriesTf = new JTextField();
    private JTextField m_proteinsTf = new JTextField();
    private JTextField m_fatsTf = new JTextField();
    private JTextField m_sodiumTf = new JTextField();
    private JTextField m_priceTf = new JTextField();
    private JLabel name = new JLabel("Product name: ");
    private JLabel rating = new JLabel("Rating: ");
    private JLabel calories = new JLabel("Calories: ");
    private JLabel proteins = new JLabel("Proteins: ");
    private JLabel fats = new JLabel("Fats: ");
    private JLabel sodium = new JLabel("Sodium: ");
    private JLabel price = new JLabel("Product price: ");

    //add composite product
    private JFrame product = new JFrame();
    private JLabel compName = new JLabel("Product name: ");
    private JLabel menuName = new JLabel("Menu name: ");
    private JLabel totalPrice = new JLabel("Rating: ");
    private JTextField m_productTf = new JTextField();
    private JTextArea m_menuTa = new JTextArea();
    private JTextField m_menuNameTf = new JTextField();
    private JTextField m_totalPriceTf = new JTextField();
    private JButton m_menuBtn = new JButton("Set menu name");
    private JButton m_addProdBtn = new JButton("Add product");
    private JButton m_backBtn = new JButton("Save & return");

    //report
    private JButton m_report1 = new JButton("Report 1");
    private JButton m_report2 = new JButton("Report 2");
    private JButton m_report3 = new JButton("Report 3");
    private JButton m_report4 = new JButton("Report 4");
    private JTextField m_startHourTf = new JTextField(7);
    private JTextField m_endHourTf = new JTextField(7);
    private JTextField m_nrOfTimesTf = new JTextField(7);
    private JTextField m_nrOrdersTf = new JTextField(7);
    private JTextField m_valueTf = new JTextField(7);
    private JTextField m_dayTf = new JTextField(7);
    private JLabel m_rap1 = new JLabel("Orders placed within the given time interval.");
    private JLabel m_rap2 = new JLabel("Products ordered more than a given number of times.");
    private JLabel m_rap3 = new JLabel("Clients that have ordered more than a number of times over the given value.");
    private JLabel m_rap4 = new JLabel("Orders within a specified day.");


    public AdminUI(Model model) {
        m_model = model;

        JPanel panouAdmin = new JPanel();
        JPanel panouPrinc = new JPanel();
        GroupLayout adminLayout = new GroupLayout(panouAdmin);
        panouAdmin.setLayout(adminLayout);
        adminLayout.setAutoCreateGaps(true);
        adminLayout.setAutoCreateContainerGaps(true);
        Font font = new Font("Times new roman", Font.PLAIN, 14);
        m_importBtn.setFont(font);
        m_addBtn.setFont(font);
        m_deleteBtn.setFont(font);
        m_modifyBtn.setFont(font);
        m_compositeBtn.setFont(font);
        m_nameTf.setFont(font);
        m_ratingTf.setFont(font);
        m_caloriesTf.setFont(font);
        m_proteinsTf.setFont(font);
        m_fatsTf.setFont(font);
        m_sodiumTf.setFont(font);
        m_priceTf.setFont(font);
        name.setFont(font);
        rating.setFont(font);
        calories.setFont(font);
        proteins.setFont(font);
        fats.setFont(font);
        sodium.setFont(font);
        price.setFont(font);


        m_nameTf.setColumns(15);
        adminLayout.linkSize(SwingConstants.HORIZONTAL, m_nameTf, m_ratingTf);
        adminLayout.linkSize(SwingConstants.HORIZONTAL, m_nameTf, m_caloriesTf);
        adminLayout.linkSize(SwingConstants.HORIZONTAL, m_nameTf, m_proteinsTf);
        adminLayout.linkSize(SwingConstants.HORIZONTAL, m_nameTf, m_fatsTf);
        adminLayout.linkSize(SwingConstants.HORIZONTAL, m_nameTf, m_sodiumTf);
        adminLayout.linkSize(SwingConstants.HORIZONTAL, m_nameTf, m_priceTf);


        adminLayout.setHorizontalGroup(adminLayout.createSequentialGroup()
                .addGroup(adminLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(name)
                        .addComponent(proteins))
                .addGroup(adminLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(m_nameTf)
                        .addComponent(m_proteinsTf)
                        .addComponent(m_importBtn))
                .addGroup(adminLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(rating)
                        .addComponent(fats)
                        .addComponent(price)
                        .addComponent(m_addBtn))
                .addGroup(adminLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(m_ratingTf)
                        .addComponent(m_fatsTf)
                        .addComponent(m_priceTf)
                        .addComponent(m_deleteBtn)
                        .addComponent(m_compositeBtn))
                .addGroup(adminLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(calories)
                        .addComponent(sodium)
                        .addComponent(m_modifyBtn))
                .addGroup(adminLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(m_caloriesTf)
                        .addComponent(m_sodiumTf)));

        adminLayout.setVerticalGroup(adminLayout.createSequentialGroup()
                .addGroup(adminLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(name)
                        .addComponent(m_nameTf)
                        .addComponent(rating)
                        .addComponent(m_ratingTf)
                        .addComponent(calories)
                        .addComponent(m_caloriesTf))
                .addGroup(adminLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(proteins)
                        .addComponent(m_proteinsTf)
                        .addComponent(fats)
                        .addComponent(m_fatsTf)
                        .addComponent(sodium)
                        .addComponent(m_sodiumTf))
                .addGroup(adminLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(price)
                        .addComponent(m_priceTf))
                .addGroup(adminLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(m_importBtn)
                        .addComponent(m_addBtn)
                        .addComponent(m_deleteBtn)
                        .addComponent(m_modifyBtn))
                .addGroup(adminLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(m_compositeBtn)));


        //panou compositeProd
        JPanel panouProd = new JPanel();
        GroupLayout prodLayout = new GroupLayout(panouProd);
        panouProd.setLayout(prodLayout);
        prodLayout.setAutoCreateGaps(true);
        prodLayout.setAutoCreateContainerGaps(true);
        compName.setFont(font);
        menuName.setFont(font);
        totalPrice.setFont(font);
        m_productTf.setFont(font);
        m_menuTa.setFont(font);
        m_menuNameTf.setFont(font);
        m_totalPriceTf.setFont(font);
        m_menuBtn.setFont(font);
        m_addProdBtn.setFont(font);
        m_backBtn.setFont(font);

        m_productTf.setColumns(10);
        prodLayout.linkSize(SwingConstants.HORIZONTAL, m_productTf, m_menuNameTf);
        prodLayout.linkSize(SwingConstants.HORIZONTAL, m_productTf, m_totalPriceTf);

        prodLayout.setHorizontalGroup(prodLayout.createSequentialGroup()
                .addGroup(prodLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(compName))
                .addGroup(prodLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(m_productTf)
                        .addComponent(m_menuBtn))
                .addGroup(prodLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(menuName)
                        .addComponent(m_addProdBtn))
                .addGroup(prodLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(m_menuNameTf)
                        .addComponent(m_backBtn))
                .addGroup(prodLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(totalPrice))
                .addGroup(prodLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(m_totalPriceTf)));

        prodLayout.setVerticalGroup(prodLayout.createSequentialGroup()
                .addGroup(prodLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(compName)
                        .addComponent(m_productTf)
                        .addComponent(menuName)
                        .addComponent(m_menuNameTf)
                        .addComponent(totalPrice)
                        .addComponent(m_totalPriceTf))
                .addGroup(prodLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(m_menuBtn)
                        .addComponent(m_addProdBtn)
                        .addComponent(m_backBtn)));

        product.setLayout(new GridLayout(0, 1));
        product.add(panouProd);
        product.add(m_menuTa);
        product.setTitle("New composite product");
        product.setVisible(false);
        product.setSize(700, 220);

        //panou rapoarte
        JPanel report1 = new JPanel();
        report1.setSize(new Dimension(700, 30));
        report1.setLayout(new FlowLayout());
        report1.add(m_rap1);
        report1.add(m_startHourTf);
        report1.add(m_endHourTf);
        report1.add(m_report1);
        JPanel report2 = new JPanel();
        report2.setSize(new Dimension(700, 30));
        report2.setLayout(new FlowLayout());
        report2.add(m_rap2);
        report2.add(m_nrOfTimesTf);
        report2.add(m_report2);
        JPanel report3 = new JPanel();
        report3.setSize(new Dimension(700, 30));
        report3.setLayout(new FlowLayout());
        report3.add(m_rap3);
        report3.add(m_nrOrdersTf);
        report3.add(m_valueTf);
        report3.add(m_report3);
        JPanel report4 = new JPanel();
        report4.setSize(new Dimension(700, 30));
        report4.setLayout(new FlowLayout());
        report4.add(m_rap4);
        report4.add(m_dayTf);
        report4.add(m_report4);
        JPanel back = new JPanel();
        back.setSize(new Dimension(700, 50));
        back.setLayout(new FlowLayout());
        back.add(m_logOutBtn);
        back.add(m_serBtn);

        m_report1.setFont(font);
        m_report2.setFont(font);
        m_report3.setFont(font);
        m_report4.setFont(font);
        m_startHourTf.setFont(font);
        m_endHourTf.setFont(font);
        m_nrOfTimesTf.setFont(font);
        m_nrOrdersTf.setFont(font);
        m_valueTf.setFont(font);
        m_dayTf.setFont(font);
        m_rap1.setFont(font);
        m_rap2.setFont(font);
        m_rap3.setFont(font);
        m_rap4.setFont(font);
        m_logOutBtn.setFont(font);
        m_serBtn.setFont(font);


        panouPrinc.setLayout(new FlowLayout());
        panouPrinc.add(panouAdmin);
        panouPrinc.add(report1);
        panouPrinc.add(report2);
        panouPrinc.add(report3);
        panouPrinc.add(report4);
        panouPrinc.add(back);

        this.setContentPane(panouPrinc);
        panouPrinc.setVisible(true);
        this.setSize(980, 410);
        this.setTitle("Administrator ");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    void showError(String errMessage) {
        JOptionPane.showMessageDialog(this, errMessage);
    }

    String getNameInput() {
        return m_nameTf.getText();
    }

    void showCompProd(boolean visible) {
        product.setVisible(visible);
    }

    String getRatingInput() {
        return m_ratingTf.getText();
    }

    String getCaloriesInput() {
        return m_caloriesTf.getText();
    }

    String getProteinsInput() {
        return m_proteinsTf.getText();
    }

    String getFatsInput() {
        return m_fatsTf.getText();
    }

    String getSodiumInput() {
        return m_sodiumTf.getText();
    }

    String getPriceInput() {
        return m_priceTf.getText();
    }

    String getMenuNameInput() {
        return m_menuNameTf.getText();
    }

    String getProductInput() {
        return m_productTf.getText();
    }

    String getStartHourInput() {
        return m_startHourTf.getText();
    }

    String getEndHourInput() {
        return m_endHourTf.getText();
    }

    String getNrOfTimesInput() {
        return m_nrOfTimesTf.getText();
    }

    String getNrOrdersInput() {
        return m_nrOrdersTf.getText();
    }

    String getValueInput() {
        return m_valueTf.getText();
    }

    String getDayInput() {
        return m_dayTf.getText();
    }

    void setMenu(String menu) {
        m_menuTa.append(menu + "\n");
    }

    void emptyMenu() {
        m_menuTa.setText("");
    }

    String getPriceTf() {
        return m_totalPriceTf.getText();
    }

    void addImportListener(ActionListener aal) {
        m_importBtn.addActionListener(aal);
    }

    void addAddListener(ActionListener aal) {
        m_addBtn.addActionListener(aal);
    }

    void addDeleteListener(ActionListener aal) {
        m_deleteBtn.addActionListener(aal);
    }

    void addEditListener(ActionListener aal) {
        m_modifyBtn.addActionListener(aal);
    }

    void addCompositeListener(ActionListener aal) {
        m_compositeBtn.addActionListener(aal);
    }

    void addMenuListener(ActionListener aal) {
        m_menuBtn.addActionListener(aal);
    }

    void addInsertProdListener(ActionListener aal) {
        m_addProdBtn.addActionListener(aal);
    }

    void addReturnListener(ActionListener aal) {
        m_backBtn.addActionListener(aal);
    }

    void addRep1Listener(ActionListener aal) {
        m_report1.addActionListener(aal);
    }

    void addRep2Listener(ActionListener aal) {
        m_report2.addActionListener(aal);
    }

    void addRep3Listener(ActionListener aal) {
        m_report3.addActionListener(aal);
    }

    void addRep4Listener(ActionListener aal) {
        m_report4.addActionListener(aal);
    }

    void addLogOutListener(ActionListener aal) {
        m_logOutBtn.addActionListener(aal);
    }

    void addSaveListener(ActionListener aal) {
        m_serBtn.addActionListener(aal);
    }

}


