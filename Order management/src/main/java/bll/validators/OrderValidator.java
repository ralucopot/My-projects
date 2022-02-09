package bll.validators;

import bll.ClientBLL;
import model.Client;
import model.Orders;

/**
 * Implementeaza interfata Validator pentru obiecte de tipul Orders
 */
public class OrderValidator implements Validator<Orders> {
    /**
     * Verifica existenta clientului care plaseaza comanda in baza de date
     *
     * @param order comanda plasata care va fi verificata
     * @throws IllegalAccessException in cazul in care clientul nu exista
     */
    @Override
    public void validate(Orders order) throws IllegalAccessException {
        ClientBLL clientBLL = new ClientBLL();
        Client client = null;

        try {
            client = clientBLL.findClientById(order.getClient_id());

        } catch (Exception ex) {
            throw new IllegalAccessException("There is no client with the given id!");
        }
    }
}
