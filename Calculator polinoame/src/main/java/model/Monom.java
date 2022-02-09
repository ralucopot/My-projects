package model;

public class Monom implements Comparable<Monom> {

    private double coeficient;
    private int putere;

    public Monom(double coeficient, int putere) {
        this.coeficient = coeficient;
        this.putere = putere;
    }

    public int getPutere() {
        return this.putere;
    }

    public void setPutere(int putere) {

        this.putere = putere;
    }

    public double getCoeficient() {

        return this.coeficient;
    }

    public void setCoeficient(double coeficient) {

        this.coeficient = coeficient;
    }

    public String toString() {
        if (coeficient == 0.0) {
            return "";
        }
        if (coeficient == 1.0) {
            if (putere == 0) {
                return "1";
            }
            if (putere == 1) {
                return "x";
            }
            return "x^" + putere;
        }

        if (putere == 0) {
            return coeficient + "";
        }
        if (putere == 1) {
            return coeficient + "*x";
        }
        return coeficient + "*x^" + putere;
    }

    @Override
    public int compareTo(Monom m) {
        if (this.getPutere() > m.getPutere()) {
            return -1;
        }
        if (this.getPutere() < m.getPutere()) {
            return 1;
        }
        return 0;
    }
}
