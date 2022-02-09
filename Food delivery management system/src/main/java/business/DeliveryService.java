package business;

import data.DeserClass;
import data.User;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DeliveryService implements IDeliveryServiceProcessing, java.io.Serializable {

    private List<MenuItem> menu = new ArrayList<MenuItem>();
    public int imported = 0;
    public List<User> users = new ArrayList<User>();
    public HashMap<Order, ArrayList<MenuItem>> ordersList = new HashMap<Order, ArrayList<MenuItem>>();
    public ArrayList<Order> orders = new ArrayList<>();
    public EmpObserver subb = new EmpObserver();
    public Order lastOrder = null;
    private transient PropertyChangeSupport support = new PropertyChangeSupport(this);

    /**
     * Constructorul clasei, are rolul de a initializa atributele
     *
     * @pre none
     * @post atributele sa fie initializate
     */
    public DeliveryService() {
        DeserClass des = new DeserClass();
        DeliveryService aux = (DeliveryService) des.deserial();
        if (aux != null) {
            imported = 1;
            this.users = aux.users;
            this.ordersList = aux.ordersList;
            this.orders = aux.orders;
        }
        //assert isWellFormed() : "There is no registered admin";
    }

    /**
     * Adauga un listener pentru cazul in cate este plasata o noua comanda
     *
     * @param pcl listener in cazul in care o "proprietate" se modifica
     * @pre pcl != null
     * @post pcl a fost adaugat in lista de PropertyChangeListener
     */
    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
        assert isWellFormed() : "There is no registered admin";
    }

    /**
     * Elimina un listener
     *
     * @param pcl listener in cazul in care o "proprietate" se modifica
     * @pre pcl != null
     * @post pcl a fost eliminat din lista de PropertyChangeListener
     */
    public void removePropertyChangeListener(PropertyChangeListener pcl) {
        support.removePropertyChangeListener(pcl);
        assert isWellFormed() : "There is no registered admin";
    }

    /**
     * Anunta observer-ul de faptul ca proprietatea "order" s-a modificat
     *
     * @param order comanda plasata de client
     * @pre order != null
     * @post lastOrder = order
     */
    public void setOrder(Order order) {
        assert order != null : "Invalid order";
        support.firePropertyChange("order", lastOrder, order);
        lastOrder = order;
    }

    /**
     * Metoda invariant, verifica daca exista un admin in lista de users
     *
     * @return valoarea de adevar a propozitiei "exista un admin in lista de users"
     * @pre existenta unui admin
     * @post existenta unui admin
     */
    private boolean isWellFormed() {
        for (User u : users) {
            if (u.getRole().getName().equals("administrator")) return true;
        }
        return false;
    }

    /**
     * Metoda importa produselei din fisierul products.csv in lista de MenuItems
     *
     * @param fileName numele fisierului din care sunt importate
     * @return lista cu toate produsele din meniu
     * @pre fileName != null
     * @post menu != null si returneaza meniul
     */
    @Override
    public List<MenuItem> importProducts(String fileName) {
        Pattern pattern = Pattern.compile(",");
        try (Stream<String> line = Files.lines(Path.of(fileName))) {
            menu = line.skip(1).map(line1 -> {
                String[] attrib = pattern.split(line1);
                return new BaseProduct(
                        attrib[0],
                        Double.parseDouble(attrib[1]),
                        Integer.parseInt(attrib[2]),
                        Integer.parseInt(attrib[3]),
                        Integer.parseInt(attrib[4]),
                        Integer.parseInt(attrib[5]),
                        Double.parseDouble(attrib[6]));
            }).distinct().collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }assert isWellFormed() : "There is no registered admin";
        return menu;
    }

    /**
     * Returneaza lista de MenuItems
     *
     * @return lista de MenuItems
     * @pre menu != null
     * @post returneaza menu
     */
    public List<MenuItem> getMenu() {
        return menu;
    }

    /**
     * Adauga un nou BaseProduct in lista de produse
     *
     * @param name     numele produsului
     * @param rating   rating-ul produsului
     * @param calories numarul de calorii al produsului
     * @param proteins numarul de proteine
     * @param fats     numarul de grasimi
     * @param sodium   valoarea sodiului din produs
     * @param price    pretul produsului
     * @throws Exception in cazul in care deja exista un produs cu numele respectiv
     * @pre name != null
     * @pre rating >= 0
     * @pre calories >= 0
     * @pre proteins >= 0
     * @pre fats >= 0
     * @pre sodium >= 0
     * @pre price > 0
     * @post baseProduct != null
     * @post baseProduct adaugat in menu
     */
    @Override
    public void addBaseMenuItem(String name, double rating, int calories, int proteins, int fats, int sodium, double price) throws Exception {
        assert name != null && rating >= 0 && calories >= 0 && proteins >= 0 && fats >= 0 && sodium >= 0 && price > 0 : "Invalid input!";
        BaseProduct baseProduct = new BaseProduct(name, rating, calories, proteins, fats, sodium, price);
        for (MenuItem m : menu) {
            BaseProduct prod = (BaseProduct) m;
            if (prod.getName().equals(name))
                throw new Exception("A product with the name " + name + " already exists!");
        }
        menu.add(baseProduct);
        assert isWellFormed() : "There is no registered admin to perform the operation";
    }

    /**
     * Adauga un BaseProduct intr-un CompositeProduct
     *
     * @param baseItem      numele produsului adaugat
     * @param compositeItem numele "meniului" in care e adaugat produsul
     * @pre compositeItem != null
     * @pre baseItem != null
     * @post cp.size() > 0
     * @post adauga baseProduct-ul in compositeProduct-ul respectiv
     */
    @Override
    public void addMenuItem(String baseItem, String compositeItem) {
        assert !baseItem.equals("") && !compositeItem.equals("") : "No input!";
        BaseProduct bp = null;
        int prevSize = 0, newSize = 0;
        for (MenuItem m : menu) {
            if (m instanceof BaseProduct) {
                if (m.getName().equals(baseItem)) bp = (BaseProduct) m;
            }
        }
        for (MenuItem m : menu) {
            if (m instanceof CompositeProduct) {
                if (m.getName().equals(compositeItem)) {
                    CompositeProduct cp = (CompositeProduct) m;
                    prevSize = cp.getItems().size();
                    cp.addProduct(bp);
                    newSize = cp.getItems().size();
                }
            }
        }
        assert isWellFormed() : "There is no registered admin to perform the operation";
        assert prevSize + 1 == newSize : "The product could not be added!";
    }

    /**
     * Metoda elimina un produs din meniu
     *
     * @param name numele produsului pe care dorim sa il stergem
     * @throws Exception in cazul in care nu exista un produs cu numele respectiv
     * @pre name != null
     * @post item-ul cu numele name a fost eliminat din menu
     */
    @Override
    public void deleteMenuItem(String name) throws Exception {
        if (name.equals("")) throw new Exception("Please provide de menu item name.");
        MenuItem item1 = null;
        int found = 0;
        for (MenuItem m : menu) {
            if (m.getName().equals(name)) {
                item1 = m;
                found = 1;
            }
        }
        if (found == 0) throw new Exception("The product with the name " + name + " doesn't exist");
        int prevSize = menu.size();
        menu.remove(item1);
        assert prevSize > menu.size() : "The item couldn't be removed!";
        assert isWellFormed() : "There is no registered admin to perform the operation";
    }

    /**
     * Metoda modifica un MenuItem existent
     *
     * @param name    numele produsului modificat
     * @param newItem un produs "dummy" care contine valorile modificate, sau -1 pentru cele care raman neschimbate
     * @throws Exception in cazul in care numele produsului nu este valid sau nu exista un produsc cu numele respectiv
     * @pre newItem != null
     * @post found = 1
     */
    @Override
    public void modifyMenuItem(String name, MenuItem newItem) throws Exception {
        if (name.equals("")) {
            throw new Exception("Please provide a product name.");
        }
        int found = 0;
        for (MenuItem m : menu) {
            if (m.getName().equals(name)) {
                found = 1;
                if (newItem.getRating() != -1) m.setRating(newItem.getRating());
                if (newItem.getCalories() != -1) m.setCalories(newItem.getCalories());
                if (newItem.getProteins() != -1) m.setProteins(newItem.getProteins());
                if (newItem.getFats() != -1) m.setFats(newItem.getFats());
                if (newItem.getSodium() != -1) m.setSodium(newItem.getSodium());
                if (newItem.getPrice() != -1) m.setPrice(newItem.getPrice());
            }
        }
        assert found != 0 : "The product with the name " + name + " doesn't exist.";
        assert isWellFormed() : "There is no registered admin to perform the operation";
    }

    /**
     * Creaza un nou produs de tipul CompositeItem
     *
     * @param items  lista de BaseProducts care formeaza CompositeItem-ul
     * @param name   numele CompositeItem-ului
     * @param rating rating-ul CompositeItem-ului
     * @pre items != null
     * @pre name != null
     * @pre rating > 0
     * @post compositeProduct != null
     */
    @Override
    public void addCompositeItem(ArrayList<MenuItem> items, String name, double rating) {
        assert items != null && name != null && rating > 0 : "Invalid input!";
        CompositeProduct compositeProduct = new CompositeProduct(name, rating, items);
        menu.add(compositeProduct);
        assert compositeProduct != null : "Could not create composite product";
        assert isWellFormed() : "There is no registered admin to perform the operation";
    }

    /**
     * Genereaza factura
     *
     * @param order comanda efectuata de client pentru care se genereaza factura
     * @pre order != null
     * @post s-a scris in fisier
     */
    @Override
    public void generateBill(Order order) {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd_MMM_yyyy_HH_mm_ss");
        String fileName = "Bill_" + format.format(date) + ".txt";
        File file = new File(fileName);
        FileWriter writer = null;
        try {
            List<MenuItem> items = new ArrayList<>(order.getOrder());
            writer = new FileWriter(file, false);
            writer.write("---- Bill ----\n");
            writer.write("\nOrder " + order.getOrderId() + " placed on " + order.getOrderDate() + " by client " + order.getClientName() + "\n");
            for (MenuItem m : items) {
                writer.write(m.getName() + "......" + m.getPrice() + "\n");
            }
            writer.write("\nTotal price: " + order.getPrice());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException ex) {
        }
        assert file != null : "Nu exista fisierul";
        assert isWellFormed() : "There is no registered admin to perform the operation";
    }

    /**
     * Adauga un nou utilizator
     *
     * @param user noul utilizator care va fi adaugat
     * @pre user != null
     * @post utilizatorul adaugat in lista de useri
     */
    @Override
    public void register(User user) {
        assert user != null : "The user is invalid!";
        users.add(user);
        assert users != null : "The users list is empty";
        assert isWellFormed() : "There is no registered admin to perform the operation";
    }

    /**
     * Metoda pentru logarea in cont; in fucntie de rolul utilizatorului va returna un anumit rezultat
     *
     * @param username numele de utilizator (unic)
     * @param password parola necesara pentru a intra in cont
     * @return un parametru specific in functie de rol: "0" pentru admin, "1" pentru angajat si numele de utilizator pentru client
     * @throws Exception in cazul in care nu exista un utilizator cu username-ul introdus
     * @pre username != null
     * @pre password != null
     * @post un rezultat este returnat
     */
    @Override
    public String logIntoAccount(String username, String password) throws Exception {
        assert !username.equals("") && !password.equals("") : "Invalid number of arguments!";
        int found = 0;
        String role = null;
        for (User u : users) {
            if (u.getUsername().equals(username)) {
                found = 1;
                role = u.getRole().getName();
                if (!u.getPassword().equals(password)) {
                    throw new Exception("Wrong password");
                }
            }
        }
        assert isWellFormed() : "There is no registered admin to perform the operation";
        if (found == 0)
            throw new Exception("User doesn't exist! Try creating a new account.");
        if (role.equals("administrator"))
            return "0";
         else if (role.equals("employee"))
            return "1";
         else
            return username;
    }

    /**
     * Metoda folosita pentru a plasa o comanda
     *
     * @param items  ArrayList care contine produsele cumparate
     * @param client numele clientului care plaseaza comanda
     * @return comanda de tipul Order
     * @throws Exception in cazul in care lista este goala
     * @pre client != null
     * @post order != null (s-a creat o comanda)
     */
    @Override
    public Order placeOrder(ArrayList<MenuItem> items, String client) throws Exception {
        assert !client.equals("") : "Invalid client!";
        if (items.size() == 0) throw new Exception("Cart is empty!");
        int id = ordersList.size() + 1;
        double price = 0;
        Date date = new Date();
        Order order = new Order(id, client, date);
        for (User u : users) {
            if (u.getRole().getName().equals("client") && u.getUsername().equals(client))
                u.setNrOfOrders(u.getNrOfOrders() + 1);
        }
        for (MenuItem m : items) {
            price += m.getPrice();
            m.setTimesOrd(m.getTimesOrd() + 1);
        }
        order.setPrice(price);
        order.setOrder(items);
        ordersList.put(order, items);
        orders.add(order);
        setOrder(order);
        assert isWellFormed() : "There is no registered admin to perform the operation";
        assert order.getOrder().size() != 0 : "Empty order";
        return order;
    }

    /**
     * Metoda folosita pentru a cauta in fucntie de anumite criterii
     *
     * @param crit lista de criterii in functie de care este efectuara cautarea
     * @return produsele care indeplinecs cerintele impuse
     *
     * @pre crit != null
     * @post none
     */
    @Override
    public List<MenuItem> searchBy(String[] crit) {
        assert crit != null :"No search criteria selected!";
        List<MenuItem> items = menu;
        ArrayList<MenuItem> res = new ArrayList<MenuItem>();
        items.stream()
                .filter(s -> { return s.getName().contains(crit[0]); })
                .filter(s -> { return s.getRating() == Double.parseDouble(crit[1]) || crit[1].equals("-1"); })
                .filter(s -> { return s.getCalories() == Integer.parseInt(crit[2]) || crit[2].equals("-1"); })
                .filter(s -> { return s.getProteins() == Integer.parseInt(crit[3]) || crit[3].equals("-1"); })
                .filter(s -> { return s.getFats() == Integer.parseInt(crit[4]) || crit[4].equals("-1");})
                .filter(s -> { return s.getSodium() == Integer.parseInt(crit[5]) || crit[5].equals("-1"); })
                .filter(s -> { return s.getPrice() == Double.parseDouble(crit[6]) || crit[6].equals("-1"); })
                .forEach(m -> res.add(m));
        assert isWellFormed() : "There is no registered admin to perform the operation";
        return res;
    }

    /**
     * Genereaza primul raport, pentru comenzi plasate intr-un anumit interval orar
     *
     * @param startHour limita inferioara a intervalului (ora minima)
     * @param endHour limita superioara a intervalului (ora maxima)
     * @return comenzile plasate in intervalul orar dat
     *
     * @pre startHour >= 0
     * @pre endHour < 24
     * @post returneaza produsele sau null daca niciunul nu indeplineste conditia
     */
    @Override
    public ArrayList<Order> generateReportOne(int startHour, int endHour) {
        assert startHour >= 0 && endHour < 24 : " Time interval is invalid! ";
        HashMap<Order, ArrayList<MenuItem>> orders = new HashMap<>();
        orders = ordersList;
        ArrayList<Order> result = new ArrayList<Order>();
        orders.keySet().stream()
                .filter(o -> { return o.getOrderHour() >= startHour && o.getOrderHour() <= endHour; })
                .forEach(o -> result.add(o));
        assert isWellFormed() : "There is no registered admin to perform the operation";
        return result;
    }

    /**
     * Metoda genereaza al doilea raport, pentru produse comandate mai mult de un anumit numar de ori
     *
     * @param nrOfTimes numarul minim de comenzi al produselor
     * @return produsele care indeplinesc cerintele sau o lista goala daca niciunul nu indeplineste cerintele
     *
     * @pre nrOfTimes > 0
     * @post noDuplicates.size() >= 0 (metoda returneaza ceva)
     */
    @Override
    public List<MenuItem> generateReportTwo(int nrOfTimes) {
        assert nrOfTimes > 0 : " Invalid input! ";
        assert ordersList != null : "There are no orders";
        List<MenuItem> orderedItems = new ArrayList<>();
        ordersList.values().stream()
                .forEach(o -> orderedItems.addAll(o));
        List<MenuItem> noDuplicates = new ArrayList<>();
        noDuplicates = orderedItems.stream()
                .filter(o -> { return o.getTimesOrd() >= nrOfTimes; })
                .distinct().collect(Collectors.toList());
        assert isWellFormed() : "There is no registered admin to perform the operation";
        return noDuplicates;
    }

    /**
     * Metoda genereaza al treilea raport, clienti care au comandat de un anumit numar de ori peste o anumita valoare
     *
     * @param nrOfOrders numarul minim de comenzi plasat de clienti
     * @param value valoarea minima pentru comenzi
     * @return clientii care indeplinesc cerintele impuse
     *
     * @pre nrOfOrders > 0
     * @pre value > 0
     * @post metoda returneaza ceva
     */
    @Override
    public ArrayList<User> generateReportThree(int nrOfOrders, double value) {
        assert nrOfOrders > 0 : "Invalid number of orders!";
        assert value > 0 : "Please specify a minimum value for the orders!";
        List<Order> valuableOrders = new ArrayList<>();
        ordersList.keySet().stream()
                .filter(o -> { return o.getPrice() >= value;
                }).forEach(i -> valuableOrders.add(i));
        List<User> collect = users.stream()
                .filter(two -> valuableOrders.stream()
                        .anyMatch(one -> one.getClientName().equals(two.getUsername()) && two.getNrOfOrders() >= nrOfOrders)
                ).distinct().collect(Collectors.toList());
        assert isWellFormed() : "There is no registered admin to perform the operation";
        return (ArrayList<User>) collect;
    }

    /**
     * Metoda genereaza al patrulea raport, produsele comandate intr-o zi data
     *
     * @param day ziua in care au fost comandate produsele
     * @return lista de produse care indeplinesc cerinta (poate fi o lista goala)
     *
     * @pre day < nextDay (valoarea maxima a zilei este ziua curenta)
     * @post metoda returneaza o lista (poate fi goala)
     */
    @Override
    public ArrayList<MenuItem> generateReportFour(String day) {
        assert isWellFormed() : " There are no products in the menu! ";
        SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
        Date today = new Date();
        Date upTo = null;
        try {
            upTo = format.parse(day);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        assert upTo.before(today) || upTo.equals(today) : "Given date is after current date!";
        Map<Order, ArrayList<MenuItem>> specOrders = ordersList.entrySet().stream()
                .filter(map -> { String data = format.format(map.getKey().getOrderDate());
                    return data.equals(day); })
                .collect(Collectors.toMap(map -> map.getKey(), map -> map.getValue()));
        List<MenuItem> itemsDuplicate = new ArrayList<>();
        specOrders.values().stream().forEach(i -> itemsDuplicate.addAll(i));
        List<MenuItem> rez = itemsDuplicate.stream().distinct().collect(Collectors.toList());
        assert isWellFormed() : "There is no registered admin to perform the operation";
        return (ArrayList<MenuItem>) rez;
    }

    /**
     * Metoda returneaza Map-ul de comenzi
     *
     * @return HashMap-ul de comenzi
     *
     * @pre none
     * @post returneaza ceva
     */
    public HashMap<Order, ArrayList<MenuItem>> getOrdersList() {
        assert isWellFormed() : "There is no registered admin to perform the operation";
        return ordersList;
    }
}
