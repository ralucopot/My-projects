package interfata;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class View extends JFrame {

    private JTextField m_pol1Tf = new JTextField("");
    private JTextField m_pol2Tf = new JTextField("");
    private JTextField m_resultTf = new JTextField("");
    private JButton m_additionBtn = new JButton("Addition");
    private JButton m_subtractionBtn = new JButton("Subtraction");
    private JButton m_multiplicationBtn = new JButton("Multiplication");
    private JButton m_divisionBtn = new JButton("Division");
    private JButton m_derivativeBtn = new JButton("Derivative");
    private JButton m_integrationBtn = new JButton("Integration");
    private JButton m_clearBtn = new JButton("Clear");

    private model.Model m_model;

    public View(model.Model model) {
        m_model = model;

        //panou principal
        JPanel panou = new JPanel();
        panou.setBackground(new Color(174, 234, 234));
        panou.setLayout(new BoxLayout(panou, BoxLayout.Y_AXIS));

        //panou polinoame
        JPanel poli = new JPanel();
        poli.setBackground(new Color(174, 234, 234));
        //layout scris
        GroupLayout scris = new GroupLayout(poli);
        poli.setLayout(scris);
        scris.setAutoCreateGaps(true);
        scris.setAutoCreateContainerGaps(true);

        JLabel P1 = new JLabel("Polynomial 1: ");
        JLabel P2 = new JLabel("Polynomial 2: ");
        JLabel R = new JLabel("Result: ");
        m_resultTf.setEditable(false);

        //font
        Font font = new Font("Arial", Font.PLAIN, 16);
        Font font2 = new Font("Arial", Font.PLAIN, 15);
        P1.setFont(font);
        P2.setFont(font);
        R.setFont(font);
        m_pol1Tf.setFont(font2);
        m_resultTf.setBackground(new Color(174, 234, 234));
        m_pol2Tf.setFont(font2);
        m_resultTf.setFont(font2);
        m_additionBtn.setFont(font);
        m_subtractionBtn.setFont(font);
        m_multiplicationBtn.setFont(font);
        m_divisionBtn.setFont(font);
        m_derivativeBtn.setFont(font);
        m_integrationBtn.setFont(font);
        m_clearBtn.setFont(font);


        scris.setHorizontalGroup(scris.createSequentialGroup()
                .addGroup(scris.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(P1)
                        .addComponent(P2)
                        .addComponent(R))
                .addGroup(scris.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(m_pol1Tf)
                        .addComponent(m_pol2Tf)
                        .addComponent(m_resultTf))
        );
        scris.setVerticalGroup(scris.createSequentialGroup()
                .addGroup(scris.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(P1)
                        .addComponent(m_pol1Tf))
                .addGroup(scris.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(P2)
                        .addComponent(m_pol2Tf))
                .addGroup(scris.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(R)
                        .addComponent(m_resultTf))

        );

        //panou butoane
        JPanel butoane = new JPanel();
        butoane.setBackground(new Color(174, 234, 234));
        //layout butoane
        GroupLayout layout = new GroupLayout(butoane);
        butoane.setLayout(layout);
        layout.linkSize(SwingConstants.HORIZONTAL, m_multiplicationBtn, m_additionBtn);
        layout.linkSize(SwingConstants.HORIZONTAL, m_multiplicationBtn, m_subtractionBtn);
        layout.linkSize(SwingConstants.HORIZONTAL, m_multiplicationBtn, m_divisionBtn);
        layout.linkSize(SwingConstants.HORIZONTAL, m_multiplicationBtn, m_derivativeBtn);
        layout.linkSize(SwingConstants.HORIZONTAL, m_multiplicationBtn, m_integrationBtn);
        layout.linkSize(SwingConstants.HORIZONTAL, m_multiplicationBtn, m_clearBtn);

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(m_additionBtn)
                        .addComponent(m_divisionBtn))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(m_subtractionBtn)
                        .addComponent(m_derivativeBtn)
                        .addComponent(m_clearBtn))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(m_multiplicationBtn)
                        .addComponent(m_integrationBtn))

        );

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(m_additionBtn)
                        .addComponent(m_subtractionBtn)
                        .addComponent(m_multiplicationBtn))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(m_divisionBtn)
                        .addComponent(m_derivativeBtn)
                        .addComponent(m_integrationBtn))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(m_clearBtn))
        );

        panou.add(poli);
        panou.add(butoane);


        this.setContentPane(panou);
        panou.setVisible(true);
        this.setSize(590, 280);
        this.setTitle("Calculator polinoame");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    String getPol1Input() {
        return m_pol1Tf.getText();
    }

    void setPol1Input(String text) {
        m_pol1Tf.setText(text);
    }

    String getPol2Input() {
        return m_pol2Tf.getText();
    }

    void setPol2Input(String text) {
        m_pol2Tf.setText(text);
    }

    void showError(String errMessage) {
        JOptionPane.showMessageDialog(this, errMessage);
    }

    void addAdditionListener(ActionListener aal) {
        m_additionBtn.addActionListener(aal);
    }

    void addSubtractionListener(ActionListener sal) {
        m_subtractionBtn.addActionListener(sal);
    }

    void addMultiplicationListener(ActionListener mal) {

        m_multiplicationBtn.addActionListener(mal);
    }

    void addDivisionListener(ActionListener dal) {

        m_divisionBtn.addActionListener(dal);
    }

    void addDerivativeListener(ActionListener dral) {

        m_derivativeBtn.addActionListener(dral);
    }

    void addIntegrationListener(ActionListener ial) {

        m_integrationBtn.addActionListener(ial);
    }

    void addClearListener(ActionListener cal) {

        m_clearBtn.addActionListener(cal);
    }

    void setResult(String result) {

        m_resultTf.setText(result);
    }
}
