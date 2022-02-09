package business;

import data.User;
import java.util.ArrayList;
import java.util.List;

public interface IDeliveryServiceProcessing {

    ///operatii admin
    List<MenuItem> importProducts(String fileName);

    //adaug un baseproduct in meniu
    void addBaseMenuItem(String name, double rating, int calories, int proteins, int fats, int sodium, double price) throws Exception;

    //adaug un BaseItem intr-un CompositeItem
    void addMenuItem(String baseItem, String compositeItem);

    void deleteMenuItem(String name) throws Exception;

    void modifyMenuItem(String name, MenuItem newItem) throws Exception;

    void addCompositeItem(ArrayList<MenuItem> items, String name, double rating);

    ArrayList<Order> generateReportOne(int startHour, int endHour);

    List<MenuItem> generateReportTwo(int nrOfTimes);

    ArrayList<User> generateReportThree(int nrOfOrders, double value);

    ArrayList<MenuItem> generateReportFour(String day);


    ///operatii client
    void register(User user);

    void generateBill(Order order);

    String logIntoAccount(String username, String password) throws Exception;

    Order placeOrder(ArrayList<MenuItem> items, String client) throws Exception;

    List<MenuItem> searchBy(String[] crit);




}
