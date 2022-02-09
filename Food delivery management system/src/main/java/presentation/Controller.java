package presentation;

import business.BaseProduct;
import business.MenuItem;
import business.Order;
import data.*;
import model.Model;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Controller {

    private Model m_model;
    private LogInView m_loginView;
    private AdminUI m_adminView;
    private ClientUI m_clientView;
    private EmployeeUI m_employeeView;
    private String client = null;
    private boolean imported = false;
    private SerializeClass s;


    public Controller(Model model, LogInView view, AdminUI adminView, ClientUI clientView, EmployeeUI employeeView) {
        m_model = model;
        m_loginView = view;
        m_adminView = adminView;
        m_clientView = clientView;
        m_employeeView = employeeView;

        if (m_model.d.imported == 0) {
            model.importUsers("users.txt");
            System.out.println("Import users");
        }
        s = new SerializeClass(m_model.d);

        //logIn
        m_loginView.addSubmitListener(new SubmitListener());
        m_loginView.addRegisterListener(new RegisterListener());
        m_loginView.addNewAccListener(new NewAccListener());
        //admin
        m_adminView.addImportListener(new ImportListener());
        m_adminView.addAddListener(new AddListener());
        m_adminView.addDeleteListener(new DeleteListener());
        m_adminView.addEditListener(new EditListener());
        m_adminView.addCompositeListener(new CompositeListener());
        m_adminView.addMenuListener(new MenuListener());
        m_adminView.addInsertProdListener(new InsertProdListener());
        m_adminView.addReturnListener(new ReturnListener());
        m_adminView.addRep1Listener(new Rep1Listener());
        m_adminView.addRep2Listener(new Rep2Listener());
        m_adminView.addRep3Listener(new Rep3Listener());
        m_adminView.addRep4Listener(new Rep4Listener());
        m_adminView.addLogOutListener(new LogOutListener());
        m_adminView.addSaveListener(new SaveListener());
        //client
        m_clientView.addViewListener(new ViewListener());
        m_clientView.addAddItemListener(new AddItemListener());
        m_clientView.addOrderListener(new OrderListener());
        m_clientView.addSearchListener(new SearchListener());
        m_clientView.addFilterListener(new FilterListener());
        m_clientView.addLogOutListener(new LogOutClientListener());
        //employee
        m_employeeView.addLogOutListener(new LogOutEmpListener());


    }

    class SubmitListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String usename = m_loginView.getUsernameInput();
            String password = m_loginView.getPasswordInput();
            try {
                String role = m_model.d.logIntoAccount(usename, password);
                m_loginView.setVisible(false);
                switch (role) {
                    case "0": //admin
                        m_adminView.setVisible(true);
                        break;
                    case "1": //employee
                        m_employeeView.setVisible(true);
                        m_model.d.addPropertyChangeListener(m_employeeView);
                        break;
                    default: //client
                        m_clientView.setVisible(true);
                        client = usename;
                        break;

                }
            } catch (Exception exception) {
                m_loginView.showError(exception.getMessage());
            }
        }
    }

    class RegisterListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            m_loginView.showAcc(true);
        }
    }

    class NewAccListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = m_loginView.getRegisterUsernameInput();
            String password = m_loginView.getRegisterPasswordInput();
            if (username.equals("")) {
                m_loginView.showError("Username field cannot be left empty");
                return;
            }
            if (password.equals("")) {
                m_loginView.showError("Password field cannot be left empty");
                return;
            }
            String code = m_loginView.getCodeInput();
            User user = null;
            if (code.equals("admin")) {
                user = new Admin(username, password);
            } else {
                if (code.equals("emp")) {
                    user = new Employee(username, password);
                } else {
                    if (code.equals("")) {
                        user = new Client(username, password);
                    }
                }
            }
            m_model.d.register(user);
            assert user != null;
            m_model.addUser(user);
            m_loginView.showSuccessMessage("Account has been created!");
            m_loginView.showAcc(false);

        }
    }

    class ImportListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            m_model.d.importProducts("products.csv");
            m_clientView.setMenuCb();
            imported = true;
        }
    }

    class LogOutListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            m_adminView.setVisible(false);
            m_loginView.setVisible(true);
        }
    }

    class SaveListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            s.ser();
        }
    }

    class AddListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            try {
                String name = m_adminView.getNameInput();
                double rating = Double.parseDouble(m_adminView.getRatingInput());
                int calories = Integer.parseInt(m_adminView.getCaloriesInput());
                int proteins = Integer.parseInt(m_adminView.getProteinsInput());
                int fats = Integer.parseInt(m_adminView.getFatsInput());
                int sodium = Integer.parseInt(m_adminView.getSodiumInput());
                double price = Double.parseDouble(m_adminView.getPriceInput());
                m_model.d.addBaseMenuItem(name, rating, calories, proteins, fats, sodium, price);
                m_clientView.setMenuCb();
            } catch (NumberFormatException ex) {
                m_adminView.showError("Invalid input");
            } catch (Exception exception) {
                m_adminView.showError(exception.getMessage());
            }
        }
    }

    class DeleteListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String name = m_adminView.getNameInput();
            try {
                m_model.d.deleteMenuItem(name);
                m_clientView.setMenuCb();
            } catch (Exception exception) {
                m_adminView.showError(exception.getMessage());
            }
        }
    }

    class EditListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String name = m_adminView.getNameInput();
            try {
                double rating, price;
                int calories, proteins, fats, sodium;
                String aux = m_adminView.getRatingInput();
                if (aux.equals("")) {
                    rating = -1;
                } else {
                    rating = Double.parseDouble(aux);
                }
                aux = m_adminView.getCaloriesInput();
                if (aux.equals("")) {
                    calories = -1;
                } else {
                    calories = Integer.parseInt(aux);
                }
                aux = m_adminView.getProteinsInput();
                if (aux.equals("")) {
                    proteins = -1;
                } else {
                    proteins = Integer.parseInt(aux);
                }
                aux = m_adminView.getFatsInput();
                if (aux.equals("")) {
                    fats = -1;
                } else {
                    fats = Integer.parseInt(aux);
                }
                aux = m_adminView.getSodiumInput();
                if (aux.equals("")) {
                    sodium = -1;
                } else {
                    sodium = Integer.parseInt(aux);
                }
                aux = m_adminView.getPriceInput();
                if (aux.equals("")) {
                    price = -1;
                } else {
                    price = Double.parseDouble(aux);
                }
                BaseProduct bp = new BaseProduct(name, rating, calories, proteins, fats, sodium, price);
                m_model.d.modifyMenuItem(name, bp);
                m_clientView.setMenuCb();
            } catch (NumberFormatException ex) {
                m_adminView.showError("Invalid input");
            } catch (Exception exception) {
                m_adminView.showError(exception.getMessage());
            }
        }
    }

    class CompositeListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            m_adminView.showCompProd(true);
            m_model.getCompositeProduct().removeAllProducts();
            m_model.getCompositeProduct().setName("");
            m_adminView.emptyMenu();
        }
    }

    class MenuListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String name = m_adminView.getMenuNameInput();
            m_model.getCompositeProduct().setName(name);

        }
    }

    class InsertProdListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String name = m_adminView.getProductInput();
            int found = 0;
            for (MenuItem m : m_model.d.getMenu()) {
                if (m.getName().equals(name)) {
                    m_model.getCompositeProduct().addProduct(m);
                    m_adminView.setMenu(m.toString());
                    found = 1;
                }
            }
            if (found == 0) {
                m_adminView.showError("the product with the name " + name + " doesn't exist!");
            }
        }
    }

    class ReturnListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            double rating = 0;
            try {
                rating = Double.parseDouble(m_adminView.getPriceTf());
            } catch (NumberFormatException ex) {
                m_adminView.showError("Invalid input for rating");
            }

            if (m_adminView.getMenuNameInput().equals("")) {
                m_adminView.showError("Please provide a name!");
            } else {
                if (m_model.getCompositeProduct().getItems().size() <= 0) {
                    m_adminView.showError("The menu is empty");
                } else {
                    m_model.d.addCompositeItem(m_model.getCompositeProduct().getItems(), m_model.getCompositeProduct().getName(), rating);
                    m_clientView.setMenuCb();
                    m_adminView.showCompProd(false);
                }
            }
        }
    }

    class Rep1Listener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int startHour = Integer.parseInt(m_adminView.getStartHourInput());
            int endHour = Integer.parseInt(m_adminView.getEndHourInput());
            ArrayList<Order> rez1 = m_model.d.generateReportOne(startHour, endHour);
            m_model.writeReport1("report1.txt", rez1, startHour, endHour);
        }
    }

    class Rep2Listener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int nrOfTimes = Integer.parseInt(m_adminView.getNrOfTimesInput());
            List<MenuItem> rez2 = m_model.d.generateReportTwo(nrOfTimes);
            m_model.writeReport2("report2.txt", rez2, nrOfTimes);
        }
    }

    class Rep3Listener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int nrOfOrd = Integer.parseInt(m_adminView.getNrOrdersInput());
            int value = Integer.parseInt(m_adminView.getValueInput());
            List<User> rez3 = m_model.d.generateReportThree(nrOfOrd, value);
            m_model.writeReport3("report3.txt", rez3, nrOfOrd, value);
        }
    }

    class Rep4Listener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String data = m_adminView.getDayInput();
            SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
            String orderDate = format.format(new Date(data));
            List<MenuItem> rez4 = m_model.d.generateReportFour(orderDate);
            m_model.writeReport4("report4.txt", rez4, orderDate);
        }
    }

    class ViewListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            m_clientView.setAllTable(m_model.findAll((ArrayList<MenuItem>) m_model.d.getMenu(), imported));
            m_clientView.showRes(true);
        }
    }

    class LogOutClientListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            m_clientView.setVisible(false);
            m_loginView.setVisible(true);
        }
    }

    class AddItemListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String name = m_clientView.getMenuItem();
            MenuItem item = null;
            for (MenuItem m : m_model.d.getMenu()) {
                if (m.getName().equals(name)) {
                    item = m;
                }
            }
            m_clientView.setOrder(item.toString());
            m_model.item.add(item);
            double price = 0;
            for (MenuItem m : m_model.item) {
                price += m.getPrice();
            }
            m_clientView.setTotalPrice(String.valueOf(price));
            //System.out.println(item.toString());
        }
    }

    class OrderListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            ArrayList<MenuItem> order = m_model.item;
            m_model.item = new ArrayList<MenuItem>();
            Order ord = null;
            try {
                ord = m_model.d.placeOrder(order, client);
                m_clientView.setTotalPrice("");
            } catch (Exception exception) {
                m_clientView.showError(exception.getMessage());
            }
            m_model.d.generateBill(ord);
            m_clientView.emptyOrder();
        }
    }

    class SearchListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String[] search = m_model.search;
            m_model.search = new String[]{"", "-1", "-1", "-1", "-1", "-1", "-1"};
            m_clientView.setSearchTable(search);
            m_clientView.showRes(true);
        }
    }

    class FilterListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String word = m_clientView.getSearchCrit();
            String search = m_clientView.getSearchInput();
            switch (word) {
                case "keyword":
                    m_model.search[0] = search;
                    m_clientView.showSuccessMessage("Added search criteria keyword = " + search);
                    break;
                case "rating":
                    m_model.search[1] = search;
                    m_clientView.showSuccessMessage("Added search criteria rating = " + search);
                    break;
                case "calories":
                    m_model.search[2] = search;
                    m_clientView.showSuccessMessage("Added search criteria calories = " + search);
                    break;
                case "proteins":
                    m_model.search[3] = search;
                    m_clientView.showSuccessMessage("Added search criteria proteins = " + search);
                    break;
                case "fats":
                    m_model.search[4] = search;
                    m_clientView.showSuccessMessage("Added search criteria fats = " + search);
                    break;
                case "sodium":
                    m_model.search[5] = search;
                    m_clientView.showSuccessMessage("Added search criteria sodium = " + search);
                    break;
                case "price":
                    m_model.search[6] = search;
                    m_clientView.showSuccessMessage("Added search criteria price = " + search);
                    break;
            }

        }
    }

    class LogOutEmpListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            m_employeeView.setVisible(false);
            m_loginView.setVisible(true);
        }
    }

}
