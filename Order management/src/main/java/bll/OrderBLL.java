package bll;

import bll.validators.OrderValidator;
import bll.validators.QuantityValidator;
import bll.validators.Validator;
import dao.OrderDAO;
import model.Orders;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * Implementeaza business logic pentru clasa si tabelul Orders
 */
public class OrderBLL {

    private OrderDAO orderDAO;
    private ArrayList<Validator<Orders>> validators;

    /**
     * Constructor
     */
    public OrderBLL() {
        orderDAO = new OrderDAO();
        validators = new ArrayList<Validator<Orders>>();
        validators.add(new OrderValidator());
        validators.add(new QuantityValidator());
    }

    /**
     * Cautarea unei comenzi in functie de id; arunca exceptie daca nu este gasita
     *
     * @param id id-ul comenzii cautate
     * @return comanda, daca este gasita
     */
    public Orders findOrderById(int id) {
        Orders order = orderDAO.findByField(id, "order_id");
        if (order == null) {
            throw new NoSuchElementException("The order with the id " + id + " was not found!");
        }
        return order;
    }

    /**
     * Cautarea unei comenzi in functie de numele clientului; arunca exceptie daca nu este gasita nicio comanda
     *
     * @param name numele clientului
     * @return comanda, daca este gasita
     */
    public Orders findByClient(String name) {
        Orders order = orderDAO.findByField(name, "client_name");
        if (order == null) {
            throw new NoSuchElementException("The order made by the client  " + name + " was not found!");
        }
        return order;
    }

    /**
     * Cautarea unei comenzi in functie de id-ul clientului; arunca exceptie daca nu este gasita nicio comanda
     *
     * @param id id-ul clientului
     * @return comanda, daca este gasita
     */
    public Orders findByClientId(int id) {
        Orders order = orderDAO.findByField(id, "client_id");
        if (order == null) {
            throw new NoSuchElementException("The order made by the client with the id " + id + " was not found!");
        }
        return order;
    }

    /**
     * Inserarea unei comenzi in tabelul de comenzi
     *
     * @param order elementul pe care dorim sa il inseram
     * @throws Exception in cazul in care comanda nu poate fi plasata
     */
    public void insertOrder(Orders order) throws Exception {
        validators.get(0).validate(order);
        validators.get(1).validate(order);
        Orders order1 = orderDAO.insert(order);

        if (order1 == null) {
            throw new Exception("Could not place the order!");
        }
    }

    /**
     * Editarea unei comezi existente
     *
     * @param order datele noii comenzi; trebuie sa existe deja o comanda cu acel id
     * @throws IllegalAccessException in cazul in care id-ul comenzii nu exista
     */
    public void updateOrder(Orders order) throws IllegalAccessException {
        int id = order.getOrder_id();
        System.out.println(id);
        Orders order1 = findOrderById(id);
        if (order1 == null) {
            throw new NoSuchElementException("The order with the id = " + order.getOrder_id() + " was not found!");
        } else {
            String id2 = Integer.toString(order.getOrder_id());
            orderDAO.update(order, id2);
        }
    }

    /**
     * Stergerea unui element in functie de id
     *
     * @param id id-ul comenzii
     */
    public void deleteById(int id) {
        orderDAO.deleteByField(id, "order_id");
    }

    /**
     * Stergerea unui element in functie de numele clientului
     *
     * @param name numele clientului
     */
    public void deleteByClient(String name) {
        orderDAO.deleteByField(name, "client_name");
    }

    /**
     * Genereaza tabelul de comenzi care va fi afisat in UI
     *
     * @return tabelul de comenzi
     */
    public JTable findAll() {

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Quantity");
        model.addColumn("Client ID");
        model.addColumn("Client Name");
        model.addColumn("Product Name");
        model.addColumn("Price");

        JTable tabel = new JTable(model);

        ArrayList<Orders> orders = orderDAO.findAll((Orders) null);
        for (Orders obj : orders) {
            model.addRow(new Object[]{obj.getOrder_id(), obj.getQuantity(), obj.getClient_id(), obj.getClient_name(), obj.getProduct_name(), obj.getTotal_price()});
        }

        return tabel;
    }
}
