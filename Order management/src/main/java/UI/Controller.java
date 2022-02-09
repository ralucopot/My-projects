package UI;

import bll.ClientBLL;
import bll.OrderBLL;
import bll.ProductBLL;
import com.itextpdf.text.DocumentException;
import model.Client;
import model.Model;
import model.Orders;
import model.Product;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

/**
 * Aceasta clasa face legatura dintre View si Model, apeland metodele din clasele model si BLL, pentru a efectua operatiile
 * corespunzatoare atunci cand utilizatorul apasa pe butoane
 */
public class Controller {

    private Model m_model;
    private View m_view;
    private ClientBLL clientBLL = new ClientBLL();
    private ProductBLL productBLL = new ProductBLL();
    private OrderBLL orderBLL = new OrderBLL();

    public Controller(Model model, View view) {
        m_model = model;
        m_view = view;

        m_view.addClientListener(new ClientListener());
        m_view.addProdListener(new ProdListener());
        m_view.addOrdListener(new OrdListener());
        m_view.addInsertClientListener(new InsertClientListener());
        m_view.addInsertProdListener(new InsertProdListener());
        m_view.addInsertOrdListener(new InsertOrdListener());
        m_view.addUpdateClientListener(new UpdateClientListener());
        m_view.addUpdateProdListener(new UpdateProdListener());
        m_view.addUpdateOrdListener(new UpdateOrdListener());
        m_view.addDeleteClientListener(new DeleteClientListener());
        m_view.addDeleteProdListener(new DeleteProdListener());
        m_view.addDeleteOrdListener(new DeleteOrdListener());
        m_view.addViewClientListener(new ViewClientListener());
        m_view.addViewProdListener(new ViewProdListener());
        m_view.addViewOrdListener(new ViewOrdListener());
    }

    /**
     * Clasa care contine metoda pentru a face panoul pentru clienti vizibil
     */
    class ClientListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            m_view.setClientsVisible(true);
        }
    }

    /**
     * Clasa care contine metoda pentru a face panoul pentru produse vizibil
     */
    class ProdListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            m_view.products.setVisible(true);
        }
    }

    /**
     * Clasa care contine metoda pentru a face panoul pentru comenzi vizibil
     */
    class OrdListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            m_view.orders.setVisible(true);
        }
    }

    /**
     * Clasa care implementeaza metoda de adaugarea a unui nou client, in functie de datele introduse de utilizator
     */
    class InsertClientListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int id = Integer.parseInt(m_view.getClientId());
                String name = m_view.getClientName();
                String address = m_view.getClientAddr();
                Client client = new Client(id, name, address);
                clientBLL.insertClient(client);
                m_view.setClientsTable(clientBLL.findAll());
            } catch (ArithmeticException | IllegalAccessException | NumberFormatException ex) {
                m_view.showError("Invalid input!");
            }
        }
    }

    /**
     * Clasa care implementeaza metoda de adaugarea a unui nou produs, in functie de datele introduse de utilizator
     */
    class InsertProdListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int id = Integer.parseInt(m_view.getProdId());
                String name = m_view.getProdName();
                int quantity = Integer.parseInt(m_view.getProdQuantity());
                double price = Double.parseDouble(m_view.getProdPrice());
                Product product = new Product(id, name, quantity, price);
                productBLL.insertProduct(product);
            } catch (ArithmeticException | IllegalAccessException | NumberFormatException ex) {
                m_view.showError("Invalid input!");
            }
        }
    }

    /**
     * Clasa care implementeaza metoda de adaugarea a unei noi comenzi, in functie de datele introduse de utilizator
     */
    class InsertOrdListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int id = Integer.parseInt(m_view.getOrdId());
                int quantity = Integer.parseInt(m_view.getOrdQuantity());
                int clientId = Integer.parseInt(m_view.getOrdClientId());
                String clientName = m_view.getOrdClientName();
                String productName = m_view.getOrdProdName();
                double price = m_model.placeOrder(0, productName, quantity);
                Orders orders = new Orders(id, quantity, clientId, clientName, productName, price);
                m_model.bill(orders);
                orderBLL.insertOrder(orders);
            } catch (ArithmeticException  ex) {
                m_view.showError("Invalid input!");
            } catch ( IllegalArgumentException ex2) {
                m_view.showError(ex2.getMessage());
            } catch (Exception  exception) {
                m_view.showError(exception.getMessage());
            }
        }
    }

    /**
     * Clasa care implementeaza metoda de editare a unui client, in functie de datele introduse de utilizator
     */
    class UpdateClientListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int id = Integer.parseInt(m_view.getClientId());
                String name = m_view.getClientName();
                String address = m_view.getClientAddr();
                Client client = new Client(id, name, address);
                clientBLL.updateClient(client);
            } catch (ArithmeticException | NumberFormatException ex) {
                m_view.showError("Invalid input!");
            }
        }
    }

    /**
     * Clasa care implementeaza metoda de editare a unui produs, in functie de datele introduse de utilizator
     */
    class UpdateProdListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int id = Integer.parseInt(m_view.getProdId());
                String productName = m_view.getProdName();
                int quantity = Integer.parseInt(m_view.getProdQuantity());
                double price = Double.parseDouble(m_view.getProdPrice());
                Product product = new Product(id, productName, quantity, price);
                productBLL.updateProduct(product);
            } catch (ArithmeticException | NumberFormatException ex) {
                m_view.showError("Invalid input!");
            }
        }
    }

    /**
     * Clasa care implementeaza metoda de editare a unei comenzi, in functie de datele introduse de utilizator
     */
    class UpdateOrdListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int id = Integer.parseInt(m_view.getOrdId());
                Orders ord = orderBLL.findOrderById(id);
                int quantity = Integer.parseInt(m_view.getOrdQuantity());
                int clientId = Integer.parseInt(m_view.getOrdClientId());
                String clientName = m_view.getOrdClientName();
                String productName = m_view.getOrdProdName();
                double totalPrice = m_model.placeOrder(ord.getQuantity(), productName, quantity);
                Orders orders = new Orders(id, quantity, clientId, clientName, productName, totalPrice);
                m_model.bill(orders);
                orderBLL.updateOrder(orders);
            } catch (ArithmeticException | NumberFormatException ex) {
                m_view.showError("Invalid input!");
            } catch (IllegalAccessException | IllegalArgumentException ex2) {
                m_view.showError(ex2.getMessage());
            } catch (FileNotFoundException | DocumentException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            }

        }
    }

    /**
     * Clasa care implementeaza metoda de stergere a unui client, in functie de id-ul introdus de utilizator
     */
    class DeleteClientListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int id = Integer.parseInt(m_view.getClientId());
                clientBLL.deleteById(id);
            } catch (NumberFormatException ex) {
                m_view.showError("Invalid input!");
            }
        }
    }

    /**
     * Clasa care implementeaza metoda de stergere a unui produs, in functie de id-ul introdus de utilizator
     */
    class DeleteProdListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int id = Integer.parseInt(m_view.getProdId());
                productBLL.deleteById(id);
            } catch (NumberFormatException ex) {
                m_view.showError("Invalid input!");
            }
        }
    }

    /**
     * Clasa care implementeaza metoda de stergere a unei comenzi, in functie de id-ul introdus de utilizator
     */
    class DeleteOrdListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int id = Integer.parseInt(m_view.getOrdId());
                orderBLL.deleteById(id);
            } catch (NumberFormatException ex) {
                m_view.showError("Invalid input!");
            }
        }
    }

    /**
     * Clasa care implementeaza metoda afisare a tabelului de clienti
     */
    class ViewClientListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            m_view.refreshClients(clientBLL.findAll());
        }
    }

    /**
     * Clasa care implementeaza metoda afisare a tabelului de produse
     */
    class ViewProdListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            m_view.refreshProducts(productBLL.findAll());
        }
    }

    /**
     * Clasa care implementeaza metoda afisare a tabelului de comenzi
     */
    class ViewOrdListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            m_view.refreshOrders(orderBLL.findAll());
        }
    }


}
