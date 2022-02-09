package presentation;

import model.Model;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class LogInView extends JFrame {

    private Model m_model;

    private JTextField m_usernameTf = new JTextField();
    private JTextField m_passwordTf = new JTextField();
    private JButton m_submitBtn = new JButton("Submit");
    private JButton m_registerBtn = new JButton("Register");
    private JLabel username = new JLabel("Username: ");
    private JLabel pass = new JLabel("Password: ");
    private JLabel newAcc = new JLabel("Don't have an account? ");

    //new account
    private JFrame acc = new JFrame();
    private JTextField m_unameTf = new JTextField(7);
    private JTextField m_passTf = new JTextField(7);
    private JTextField m_specialTf = new JTextField(7);
    private JLabel uname = new JLabel("Username: ");
    private JLabel pwd = new JLabel("Password: ");
    private JLabel special = new JLabel("Worker code: ");
    private JButton m_backBtn = new JButton("Save & return");


    public LogInView(Model model) {
        m_model = model;

        JPanel panouPrinc = new JPanel();

        GroupLayout princLayout = new GroupLayout(panouPrinc);
        panouPrinc.setLayout(princLayout);
        princLayout.setAutoCreateGaps(true);
        princLayout.setAutoCreateContainerGaps(true);
        Font font = new Font("Times new roman", Font.PLAIN, 14);
        princLayout.linkSize(SwingConstants.HORIZONTAL, m_registerBtn, m_submitBtn);
        princLayout.linkSize(SwingConstants.HORIZONTAL, m_registerBtn, m_usernameTf);
        princLayout.linkSize(SwingConstants.HORIZONTAL, m_registerBtn, m_passwordTf);
        m_usernameTf.setFont(font);
        m_passwordTf.setFont(font);
        m_submitBtn.setFont(font);
        m_registerBtn.setFont(font);
        username.setFont(font);
        pass.setFont(font);
        newAcc.setFont(font);

        princLayout.setHorizontalGroup(princLayout.createSequentialGroup()
                .addGroup(princLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(username)
                        .addComponent(pass)
                        .addComponent(m_submitBtn)
                        .addComponent(newAcc)
                        .addComponent(m_registerBtn))
                .addGroup(princLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(m_usernameTf)
                        .addComponent(m_passwordTf)));

        princLayout.setVerticalGroup(princLayout.createSequentialGroup()
                .addGroup(princLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(username)
                        .addComponent(m_usernameTf))
                .addGroup(princLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(pass)
                        .addComponent(m_passwordTf))
                .addGroup(princLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(m_submitBtn))
                .addGroup(princLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(newAcc))
                .addGroup(princLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(m_registerBtn)));

        //register
        GridLayout groupLayout = new GridLayout(0, 2);
        groupLayout.setHgap(5);
        groupLayout.setVgap(5);
        acc.setLayout(groupLayout);
        uname.setFont(font);
        m_unameTf.setFont(font);
        pwd.setFont(font);
        m_passTf.setFont(font);
        special.setFont(font);
        m_specialTf.setFont(font);
        m_backBtn.setFont(font);

        acc.add(uname);
        acc.add(m_unameTf);
        acc.add(pwd);
        acc.add(m_passTf);
        acc.add(special);
        acc.add(m_specialTf);
        acc.add(m_backBtn);

        acc.setSize(300, 200);
        acc.setVisible(false);


        this.setContentPane(panouPrinc);
        panouPrinc.setVisible(true);
        this.setSize(260, 200);
        this.setTitle("Log In ");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    void showError(String errMessage) {
        JOptionPane.showMessageDialog(this, errMessage, "Error", JOptionPane.ERROR_MESSAGE);
    }

    String getUsernameInput() {
        return m_usernameTf.getText();
    }

    String getPasswordInput() {
        return m_passwordTf.getText();
    }

    String getRegisterUsernameInput() {
        return m_unameTf.getText();
    }

    String getRegisterPasswordInput() {
        return m_passTf.getText();
    }

    String getCodeInput() {
        return m_specialTf.getText();
    }

    void addSubmitListener(ActionListener sal) {
        m_submitBtn.addActionListener(sal);
    }

    void addRegisterListener(ActionListener ral) {
        m_registerBtn.addActionListener(ral);
    }

    void addNewAccListener(ActionListener ral) {
        m_backBtn.addActionListener(ral);
    }

    void showSuccessMessage(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    void showAcc(boolean visible) {
        acc.setVisible(visible);
    }


}
