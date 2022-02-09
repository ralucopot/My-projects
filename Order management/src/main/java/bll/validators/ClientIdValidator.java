package bll.validators;

import dao.ClientDAO;
import model.Client;

/**
 * Implementeaza interfata Validator pentru obiecte de tipul Client
 */
public class ClientIdValidator implements Validator<Client> {

    private static final int MIN_ID = 0;
    private static final int MAX_ID = 400;

    /**
     * Verifica daca id-ul clientului se afla in intervalul (MIN_ID, MAX_ID)
     *
     * @param client obiectul verificat
     * @throws IllegalAccessException exceptie in cazul in care nu este respectata limita
     */
    @Override
    public void validate(Client client) throws IllegalAccessException {
        ClientDAO clientDAO = new ClientDAO();
        if (client.getClient_id() < MIN_ID || client.getClient_id() > MAX_ID) {
            throw new IllegalAccessException("The client id limit is not respected!");
        }


    }
}
