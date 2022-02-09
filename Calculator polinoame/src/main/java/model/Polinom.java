package model;

import model.Monom;

import java.util.ArrayList;
import java.util.Collections;

public class Polinom {

    private ArrayList<Monom> poli;

    public Polinom() {

        poli = new ArrayList<Monom>();
    }

    public void adaugare(Monom monom) {
        poli.add(monom);
    }

    public void stergere(Monom monom) {
        poli.remove(monom);
    }

    public ArrayList<Monom> getTermeni() {
        return poli;
    }

    public void sortare() {
        Collections.sort(poli);
    }

    public String toString() {
        String result = " ";
        if (poli.size() == 1) {
            if (poli.get(0).getCoeficient() == 0) {
                return "0";
            }
        }
        for (Monom p : poli) {
            if (result.equals(" ")) {
                result = p.toString();
            } else {
                if (p.getCoeficient() < 0) {
                    result = result + " " + p.toString();
                } else {
                    if (p.getCoeficient() != 0) {
                        result = result + " + " + p.toString();
                    }
                }
            }
        }
        return result;

    }
}
