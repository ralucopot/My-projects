package model;

import java.sql.SQLOutput;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Model {

    private static final Monom zero = new Monom(0, 0);
    private Monom total = new Monom(0, 0);
    private String poliPattern = "([+-]?[^-+]+)";
    private Polinom pol1;

    public Model() {
        reset();
    }

    public void reset() {

        total = zero;
    }

    public Polinom matching(String poli) throws Exception {
        Polinom result = new Polinom();
        Pattern pattern = Pattern.compile(poliPattern);
        Matcher m = pattern.matcher(poli);
        while (m.find()) {
            int coef = 0, exp = 0;
            boolean match = true;
            String curent = "";
            for (char c : m.group(0).toCharArray()) {
                if ((c >= '0' && c <= '9') || c == '-' || c == '+') {
                    curent = curent + c;
                } else {
                    if (c == 'x') {
                        if (curent.equals("-") || curent.equals("+") || curent.equals("")) {
                            curent = curent + "1";
                        }
                        coef = Integer.parseInt(curent);
                        curent = "";
                    } else {
                        if (c != '^') {
                            match = false;
                            break;
                        }
                    }
                }
            }
            if (!match) {
                throw new Exception("model.Polinom invalid!");
            }
            exp = Integer.parseInt(curent);
            if (coef == 0) {
                result.adaugare(new Monom(exp, 0));
            } else {
                result.adaugare(new Monom(coef, exp));
            }
        }
        return result;
    }


}
