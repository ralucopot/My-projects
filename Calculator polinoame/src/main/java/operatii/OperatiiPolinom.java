package operatii;

import model.Monom;
import model.Polinom;

public class OperatiiPolinom {

    public static void rest(Polinom rez, Polinom p1, int index) {
        while (index < p1.getTermeni().size()) {
            Monom m1 = p1.getTermeni().get(index);
            rez.adaugare(m1);
            index++;
        }
    }

    public static void restNegativ(Polinom rez, Polinom p1, int index) {
        while (index < p1.getTermeni().size()) {
            Monom m1 = p1.getTermeni().get(index);
            Monom aux = new Monom(-m1.getCoeficient(), m1.getPutere());
            rez.adaugare(aux);
            index++;
        }
    }

    public static Polinom adunare(Polinom p1, Polinom p2) {
        p1.sortare();
        p2.sortare();
        Polinom rez = new Polinom();
        int i = 0, j = 0;
        while (i < p1.getTermeni().size() && j < p2.getTermeni().size()) {
            Monom m1 = p1.getTermeni().get(i);
            Monom m2 = p2.getTermeni().get(j);
            if (m1.getPutere() == m2.getPutere()) {
                rez.adaugare(OperatiiMonom.adunare(m1, m2));
                i++;
                j++;
            } else {
                if (m1.getPutere() > m2.getPutere()) {
                    rez.adaugare(m1);
                    i++;
                } else {
                    rez.adaugare(m2);
                    j++;
                }
            }
        }
        rest(rez, p1, i);
        rest(rez, p2, j);
        return rez;
    }

    public static Polinom scadere(Polinom p1, Polinom p2) {
        p1.sortare();
        p2.sortare();
        Polinom rez = new Polinom();
        int i = 0, j = 0;
        while (i < p1.getTermeni().size() && j < p2.getTermeni().size()) {
            Monom m1 = p1.getTermeni().get(i);
            Monom m2 = p2.getTermeni().get(j);
            if (m1.getPutere() == m2.getPutere()) {
                rez.adaugare(OperatiiMonom.scadere(m1, m2));
                i++;
                j++;
            } else {
                if (m1.getPutere() > m2.getPutere()) {
                    rez.adaugare(m1);
                    i++;
                } else {
                    Monom minus = new Monom(-m2.getCoeficient(), m2.getPutere());
                    rez.adaugare(minus);
                    j++;
                }
            }
        }
        rest(rez, p1, i);
        restNegativ(rez, p2, j);
        return rez;
    }

    public static Polinom inmultire(Polinom p1, Polinom p2) {
        Polinom rez = new Polinom();
        Monom aux = new Monom(0, 0);
        Polinom a = new Polinom();
        for (Monom m1 : p1.getTermeni()) {
            for (Monom m2 : p2.getTermeni()) {
                aux = OperatiiMonom.inmultire(m1, m2);
                a.adaugare(aux);
                rez = OperatiiPolinom.adunare(rez, a);
                a.stergere(aux);
            }
        }
        rez.sortare();

        return rez;
    }

    public static Polinom derivare(Polinom p1) {
        Polinom rez = new Polinom();
        for (Monom m1 : p1.getTermeni()) {
            rez.adaugare(OperatiiMonom.derivare(m1));
        }
        rez.sortare();
        return rez;
    }

    public static Polinom integrare(Polinom p1) {
        Polinom rez = new Polinom();
        for (Monom m1 : p1.getTermeni()) {
            rez.adaugare(OperatiiMonom.integrare(m1));
        }
        rez.sortare();
        return rez;
    }

    public static Polinom[] auxImpartire(Polinom p1, Polinom p2) {
        p1.sortare();
        p2.sortare();
        Polinom[] result = new Polinom[2];

        if (p1.getTermeni().get(0).getPutere() > p2.getTermeni().get(0).getPutere()) {
            result = impartire(p1, p2);
        } else {
            result = impartire(p2, p1);
        }
        return result;
    }

    public static Polinom[] impartire(Polinom p1, Polinom p2) {
        if (p2.getTermeni().size() == 1 && p2.getTermeni().get(0).getCoeficient() == 0) {
            return null;
        }
        int i = 0, j = 0;
        Polinom q = new Polinom();
        Polinom r = new Polinom();
        r = p1;
        while (r.getTermeni().size() != 0 && r.getTermeni().get(i).getPutere() >= p2.getTermeni().get(j).getPutere()) {
            Monom t = OperatiiMonom.impartire(r.getTermeni().get(i), p2.getTermeni().get(j));
            q.adaugare(t);
            Polinom tp = new Polinom();
            tp.adaugare(t);
            Polinom aux = OperatiiPolinom.inmultire(tp, p2);
            r = OperatiiPolinom.scadere(r, aux);
            i++;
            //j++;
            tp.stergere(t);
        }
        Polinom[] result = new Polinom[2];
        result[0] = q;
        result[1] = r;
        return result;
    }

}
