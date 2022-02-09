package bll;

import bll.validators.ClientIdValidator;
import bll.validators.Validator;
import dao.ClientDAO;
import model.Client;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * Implementeaza business logic pentru clasa si tabelul Client
 */
public class ClientBLL {

    private ClientDAO clientDAO;
    private Validator<Client> validator;

    /**
     * Constructor
     */
    public ClientBLL() {
        validator = new ClientIdValidator();
        clientDAO = new ClientDAO();
    }

    /**
     * Cautarea unui client in functie de id; arunca exceptie daca nu este gasit
     *
     * @param id id-ul clientului cautat
     * @return clientul, daca este gasit
     */
    public Client findClientById(int id) {
        Client client = clientDAO.findByField(id, "client_id");
        if (client == null) {
            throw new NoSuchElementException("The client with the id = " + id + " was not found!");
        }
        return client;
    }

    /**
     * Cautarea unui client in functie de nume; arunca exceptie daca nu este gasit
     *
     * @param name numele clientului cautat
     * @return clientul, daca este gasit
     */
    public Client findClientByName(String name) {
        Client client = clientDAO.findByField(name, "client_name");
        if (client == null) {
            throw new NoSuchElementException("The client with the name = " + name + " was not found!");
        }
        return client;
    }

    /**
     * Inserarea unui client in tabelul de clienti
     *
     * @param client elementul pe care dorim sa il inseram
     * @throws IllegalAccessException in cazul in care deja exista un client cu id-ul respectiv sau daca nu poate fi inserat
     */
    public void insertClient(Client client) throws IllegalAccessException {
        Client client2 = null;
        try {
            client2 = findClientById(client.getClient_id());
        } catch (NoSuchElementException ex) {
        }

        if (client2 != null) {
            throw new IllegalAccessException("The client with this id already exists!");
        }
        Client client1 = clientDAO.insert(client);
        if (client1 == null) {
            throw new NoSuchElementException("Could not insert the client!");
        }
    }

    /**
     * Stergerea unui client in functie de id
     *
     * @param id    id-ul clientului pe care il stergem
     */
    public void deleteById(int id) {
        clientDAO.deleteByField(id, "client_id");
    }

    /**
     * Stergerea unui client in functie de nume
     *
     * @param name numele clientului pe care il stergem
     */
    public void deleteByName(String name) {
        clientDAO.deleteByField(name, "client_name");
    }

    /**
     * Editarea unui client existent
     *
     * @param client datele noului client, care trebuie sa existe deja
     */
    public void updateClient(Client client) {
        Client client1 = clientDAO.findByField(client.getClient_id(), "client_id");
        if (client1 == null) {
            throw new NoSuchElementException("The client with the id = " + client.getClient_id() + " was not found!");
        } else {
            String id = Integer.toString(client.getClient_id());
            clientDAO.update(client, id);
        }
    }

    /**
     * Genereaza tabelul de clienti care va fi afisat in UI
     *
     * @return tabelul de clienti
     */
    public JTable findAll() {

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Name");
        model.addColumn("Address");

        JTable tabel = new JTable(model);

        ArrayList<Client> clients = clientDAO.findAll((Client) null);
        for (Client obj : clients) {
            model.addRow(new Object[]{obj.getClient_id(), obj.getClient_name(), obj.getAddress()});
        }
        return tabel;
    }

}
