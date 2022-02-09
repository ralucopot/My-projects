package operatii;

import model.Monom;

public class OperatiiMonom {

    public static Monom adunare(Monom m1, Monom m2) {
        Monom rez = new Monom(0, 0);
        if (m1.getPutere() == m2.getPutere()) {
            double coef = m1.getCoeficient() + m2.getCoeficient();
            rez.setCoeficient(coef);
            rez.setPutere(m1.getPutere());
            return rez;
        } else
            return null;
    }

    public static Monom scadere(Monom m1, Monom m2) {
        Monom rez = new Monom(0, 0);
        if (m1.getPutere() == m2.getPutere()) {
            double coef = m1.getCoeficient() - m2.getCoeficient();
            rez.setCoeficient(coef);
            rez.setPutere(m1.getPutere());
            return rez;
        } else {
            return null;
        }
    }

    public static Monom inmultire(Monom m1, Monom m2) {
        double coef = m1.getCoeficient() * m2.getCoeficient();
        int putere = m1.getPutere() + m2.getPutere();
        Monom rez = new Monom(coef, putere);
        return rez;
    }

    public static Monom derivare(Monom m1) {
        double coef = m1.getPutere() * m1.getCoeficient();
        int putere = m1.getPutere() - 1;
        Monom rez = new Monom(coef, putere);
        return rez;
    }

    public static Monom integrare(Monom m1) {
        double coef = m1.getCoeficient() / (m1.getPutere() + 1);
        int putere = m1.getPutere() + 1;
        Monom rez = new Monom(coef, putere);
        return rez;
    }

    public static Monom impartire(Monom m1, Monom m2) {
        if (m2.getCoeficient() == 0) {
            return null;
        }
        double coef = m1.getCoeficient() / m2.getCoeficient();
        int putere = m1.getPutere() - m2.getPutere();
        Monom rez = new Monom(coef, putere);
        return rez;
    }

}
