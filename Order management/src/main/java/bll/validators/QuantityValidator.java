package bll.validators;

import dao.ProductDAO;
import model.Orders;
import model.Product;

import java.util.NoSuchElementException;

/**
 * Implementeaza interfata Validator pentru obiecte de tipul Orders
 */
public class QuantityValidator implements Validator<Orders> {
    /**
     * Verifica existenta produsului si daca mai este in stoc
     *
     * @param orders comanda pe care clientul doreste sa o plaseze
     * @throws IllegalAccessException in cazul in care produsul nu exista sau nu mai este in stoc
     */
    @Override
    public void validate(Orders orders) throws IllegalAccessException {
        ProductDAO productDAO = new ProductDAO();
        Product product = productDAO.findByField(orders.getProduct_name(), "product_name");
        if (product == null)
            throw new NoSuchElementException("No such product exists.");
        if (0 >= product.getQuantity())
            throw new IllegalArgumentException("Not enough stock left.");
    }
}
