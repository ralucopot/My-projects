package interfata;

import model.Polinom;
import operatii.OperatiiPolinom;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller {

    private model.Model m_model;
    private View m_view;

    public Controller(model.Model model, View view) {
        m_model = model;
        m_view = view;

        view.addAdditionListener(new AdditionListener());
        view.addSubtractionListener(new SubtractionListener());
        view.addClearListener(new ClearListener());
        view.addMultiplicationListener(new MultiplicationListener());
        view.addDerivativeListener(new DerivativeListener());
        view.addIntegrationListener(new IntegrationListener());
        view.addDivisionListener(new DivisionListener());

    }

    public Polinom citire(String input, String err) {
        Polinom rez = new model.Polinom();
        try {
            rez = m_model.matching(input);
        } catch (Exception ex) {
            m_view.showError(err);
            return null;
        }
        return rez;
    }

    class ClearListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            m_view.setPol1Input("");
            m_view.setPol2Input("");
            m_view.setResult("");
        }
    }

    class AdditionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Polinom poli1 = citire(m_view.getPol1Input(), "The first polynomial is invalid!");
            Polinom poli2 = citire(m_view.getPol2Input(), "The second polynomial is invalid!");
            if (poli1 != null && poli2 != null) {
                model.Polinom result = operatii.OperatiiPolinom.adunare(poli1, poli2);
                m_view.setResult(result.toString());
            }
        }
    }

    class SubtractionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Polinom poli1 = citire(m_view.getPol1Input(), "The first polynomial is invalid!");
            Polinom poli2 = citire(m_view.getPol2Input(), "The second polynomial is invalid!");
            if (poli1 != null && poli2 != null) {
                model.Polinom result = operatii.OperatiiPolinom.scadere(poli1, poli2);
                m_view.setResult(result.toString());
            }

        }
    }

    class MultiplicationListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Polinom poli1 = citire(m_view.getPol1Input(), "The first polynomial is invalid!");
            Polinom poli2 = citire(m_view.getPol2Input(), "The second polynomial is invalid!");
            if (poli1 != null && poli2 != null) {
                model.Polinom result = operatii.OperatiiPolinom.inmultire(poli1, poli2);
                m_view.setResult(result.toString());
            }
        }
    }

    class DerivativeListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Polinom poli1 = citire(m_view.getPol1Input(), "The polynomial is invalid!");
            if (poli1 != null) {
                model.Polinom result = operatii.OperatiiPolinom.derivare(poli1);
                m_view.setResult(result.toString());
            }
        }
    }

    class IntegrationListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Polinom poli1 = citire(m_view.getPol1Input(), "The polynomial is invalid!");
            if (poli1 != null) {
                Polinom result = operatii.OperatiiPolinom.integrare(poli1);
                m_view.setResult(result.toString() + "+ C");
            }

        }
    }

    class DivisionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Polinom poli1 = citire(m_view.getPol1Input(), "The first polynomial is invalid!");
            Polinom poli2 = citire(m_view.getPol2Input(), "The second polynomial is invalid!");
            if (poli1 != null && poli2 != null) {
                Polinom[] result = OperatiiPolinom.auxImpartire(poli1, poli2);
                if (result[0] == null) {
                    m_view.showError("The divisor is invalid!");
                } else {
                    m_view.setResult("Q: " + result[0].toString() + " R: " + result[1].toString());
                }
            }
        }
    }


}
