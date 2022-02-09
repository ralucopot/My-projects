package bll;

import dao.ProductDAO;
import model.Product;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * Implementeaza business logic pentru clasa si tabelul Product
 */
public class ProductBLL {

    private ProductDAO productDAO;

    /**
     * Constructor
     */
    public ProductBLL() {
        productDAO = new ProductDAO();
    }

    /**
     * Cautarea unui produs in functie de id; arunca exceptie daca nu este gasit
     *
     * @param id id-ul produsului cautat
     * @return produsul, daca este gasit
     */
    public Product findByProductId(int id) {
        Product product = productDAO.findByField(id, "product_id");
        if (product == null) {
            throw new NoSuchElementException("The product with the id = " + id + " was not found!");
        }
        return product;
    }

    /**
     * autarea unui produs in functie de nume; arunca exceptie daca nu este gasit
     *
     * @param name numele produsului cautat
     * @return produsul, daca este gasit
     */
    public Product findProductByName(String name) {
        Product product = productDAO.findByField(name, "product_name");
        if (product == null) {
            throw new NoSuchElementException("The product with the name = " + name + " was not found!");
        }
        return product;
    }

    /**
     * Editarea unui produs existent
     *
     * @param product datele noului produs, care trebuie sa existe deja
     */
    public void updateProduct(Product product) {
        Product product1 = productDAO.findByField(product.getProduct_id(), "product_id");
        if (product1 == null) {
            throw new NoSuchElementException("The client with the id = " + product.getProduct_id() + " was not found!");
        } else {
            String id = Integer.toString(product.getProduct_id());
            productDAO.update(product, id);
        }
    }

    /**
     * Inserarea unui produs in tabelul de produse
     *
     * @param product elementul pe care dorim sa il inseram
     * @throws IllegalAccessException in cazul in care deja exista un produs cu id-ul respectiv sau daca nu poate fi inserat
     */
    public void insertProduct(Product product) throws IllegalAccessException {
        Product product2 = null;
        try {
            product2 = findByProductId(product.getProduct_id());
        } catch (NoSuchElementException ex) {
        }
        if (product2 != null) {
            throw new IllegalAccessException("The product with this id already exists!");
        }
        Product product1 = productDAO.insert(product);
        if (product1 == null) {
            throw new NoSuchElementException("Could not insert the product!");
        }
    }

    /**
     * Stergerea unui produs in functie de id
     *
     * @param id id-ul produsului pe care il stergem
     */
    public void deleteById(int id) {
        productDAO.deleteByField(id, "product_id");
    }

    /**
     * Stergerea unui produs in functie de numele acestuia
     *
     * @param name numele produsului pe care il stergem
     */
    public void deleteByName(String name) {
        productDAO.deleteByField(name, "product_name");
    }

    /**
     * Genereaza tabelul de produse care va fi afisat in UI
     *
     * @return tabelul de produse
     */
    public JTable findAll() {

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Name");
        model.addColumn("Quantity");
        model.addColumn("Price");

        JTable tabel = new JTable(model);

        ArrayList<Product> products = productDAO.findAll((Product) null);
        for (Product obj : products) {
            model.addRow(new Object[]{obj.getProduct_id(), obj.getProduct_name(), obj.getQuantity(), obj.getPrice()});
        }

        return tabel;
    }

}
