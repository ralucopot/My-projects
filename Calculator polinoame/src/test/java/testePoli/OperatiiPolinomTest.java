package testePoli;

import model.Monom;
import model.Polinom;
import operatii.OperatiiPolinom;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class OperatiiPolinomTest {
    @Test
    public void adunareTest() {
        Polinom p1 = new Polinom();
        p1.adaugare(new Monom(4, 5));
        p1.adaugare(new Monom(-3, 4));
        p1.adaugare(new Monom(-8, 1));
        p1.adaugare(new Monom(5, 0));

        Polinom p2 = new Polinom();
        p2.adaugare(new Monom(4, 4));
        p2.adaugare(new Monom(12, 0));

        Polinom result = new Polinom();
        result.adaugare(new Monom(4, 5));
        result.adaugare(new Monom(1, 4));
        result.adaugare(new Monom(-8, 1));
        result.adaugare(new Monom(17, 0));

        assertEquals(OperatiiPolinom.adunare(p1, p2).toString(), result.toString());
    }

    @Test
    public void scadereTest(){
        Polinom p1 = new Polinom();
        p1.adaugare(new Monom(4, 5));
        p1.adaugare(new Monom(-3, 4));
        p1.adaugare(new Monom(-8, 1));
        p1.adaugare(new Monom(5, 0));

        Polinom p2 = new Polinom();
        p2.adaugare(new Monom(4, 3));
        p2.adaugare(new Monom(12, 0));

        Polinom result = new Polinom();
        result.adaugare(new Monom(4, 5));
        result.adaugare(new Monom(-3, 4));
        result.adaugare(new Monom(-4, 3));
        result.adaugare(new Monom(-8, 1));
        result.adaugare(new Monom(-7, 0));

        assertEquals(OperatiiPolinom.scadere(p1, p2).toString(), result.toString());
    }

    @Test
    public void inmultireTest(){
        Polinom p1 = new Polinom();
        p1.adaugare(new Monom(4, 5));
        p1.adaugare(new Monom(-3, 4));
        p1.adaugare(new Monom(-8, 1));
        p1.adaugare(new Monom(5, 0));

        Polinom p2 = new Polinom();
        p2.adaugare(new Monom(4, 3));
        p2.adaugare(new Monom(12, 0));

        Polinom result = new Polinom();
        result.adaugare(new Monom(16, 8));
        result.adaugare(new Monom(-12, 7));
        result.adaugare(new Monom(48, 5));
        result.adaugare(new Monom(-68, 4));
        result.adaugare(new Monom(20, 3));
        result.adaugare(new Monom(-96, 1));
        result.adaugare(new Monom(60, 0));

        assertEquals(OperatiiPolinom.inmultire(p1, p2).toString(), result.toString());
    }

    @Test
    public void impartireTest(){
        Polinom p1 = new Polinom();
        p1.adaugare(new Monom(4, 5));
        p1.adaugare(new Monom(-3, 4));
        p1.adaugare(new Monom(-8, 1));
        p1.adaugare(new Monom(5, 0));

        Polinom p2 = new Polinom();
        p2.adaugare(new Monom(4, 3));
        p2.adaugare(new Monom(12, 0));

        Polinom cat = new Polinom();
        cat.adaugare(new Monom(1, 2));
        cat.adaugare(new Monom(-0.75, 1));

        Polinom rest = new Polinom();
        rest.adaugare(new Monom(-12, 2));
        rest.adaugare(new Monom(1, 1));
        rest.adaugare(new Monom(5, 0));

        Polinom[] result = OperatiiPolinom.impartire(p1, p2);

        assertEquals("Cat: " +result[0].toString() + " Rest:" + result[1].toString(), "Cat: " + cat.toString() +" Rest: " + rest.toString());
    }

    @Test
    public void derivareTest(){
        Polinom p1 = new Polinom();
        p1.adaugare(new Monom(4, 5));
        p1.adaugare(new Monom(-3, 4));
        p1.adaugare(new Monom(-8, 1));
        p1.adaugare(new Monom(5, 0));

        Polinom result = new Polinom();
        result.adaugare(new Monom(20, 4));
        result.adaugare(new Monom(-12, 3));
        result.adaugare(new Monom(-8, 0));

        assertEquals(OperatiiPolinom.derivare(p1).toString(), result.toString());
    }

    @Test
    public void integrareTest(){
        Polinom p1 = new Polinom();
        p1.adaugare(new Monom(4, 4));
        p1.adaugare(new Monom(-3, 3));
        p1.adaugare(new Monom(-8, 1));
        p1.adaugare(new Monom(5, 0));

        Polinom result = new Polinom();
        result.adaugare(new Monom(0.8, 5));
        result.adaugare(new Monom(-0.75, 4));
        result.adaugare(new Monom(-4, 2));
        result.adaugare(new Monom(5, 1));

        assertEquals(OperatiiPolinom.integrare(p1).toString(), result.toString());
    }
}
