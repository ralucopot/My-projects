package bll.validators;

/**
 * Interfata care va fi implementata de validatori
 *
 * @param <T> tipul de obiect din clasele Model pentru care folosim validatorul
 */
public interface Validator<T> {

    /**
     * Valideaza un obiect in functie de anumite criterii
     *
     * @param t obiectul care va fi validat
     * @throws IllegalAccessException in cazul in care obiectul nu este valid
     */
    public void validate(T t) throws IllegalAccessException;
}
