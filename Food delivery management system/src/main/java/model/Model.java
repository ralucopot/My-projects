package model;

import business.*;
import data.Admin;
import data.Client;
import data.Employee;
import data.User;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Model {

    public DeliveryService d;
    public ArrayList<MenuItem> item = new ArrayList<MenuItem>();
    private List<MenuItem> menu;
    private CompositeProduct compositeProduct = new CompositeProduct(null, 0, null);
    public String[] search = new String[]{"", "-1", "-1", "-1", "-1", "-1", "-1"};


    public Model(DeliveryService d) {
        this.d = d;
        menu = new ArrayList<MenuItem>();
        menu = d.getMenu();
    }

    public CompositeProduct getCompositeProduct() {
        return compositeProduct;
    }

    public void setCompositeProduct(CompositeProduct compositeProduct) {
        this.compositeProduct = compositeProduct;
    }

    public String[] getNames(boolean imported) {
        if (imported) {
            menu = d.getMenu();
        }
        int nr = menu.size();
        String[] names = new String[nr];
        for (int i = 0; i < nr; i++) {
            if (menu.get(i) instanceof BaseProduct) {
                BaseProduct prod = (BaseProduct) menu.get(i);
                names[i] = prod.getName();
            } else {
                if (menu.get(i) instanceof CompositeProduct) {
                    CompositeProduct prod = (CompositeProduct) menu.get(i);
                    names[i] = prod.getName();
                }
            }
        }
        Arrays.sort(names);
        return names;
    }

    public JTable findAll(ArrayList<MenuItem> menuItems, boolean imported) {
        DefaultTableModel model = new DefaultTableModel();

        model.addColumn("Name");
        model.addColumn("Rating");
        model.addColumn("Calories");
        model.addColumn("Proteins");
        model.addColumn("Fats");
        model.addColumn("Sodium");
        model.addColumn("Price");

        JTable tabel = new JTable(model);
        ArrayList<MenuItem> products;
        if (imported) {
            products = menuItems;
        } else {
            products = new ArrayList<>();
        }
        if (products.size() > 0) {
            for (MenuItem m : products) {
                if (m instanceof BaseProduct) {
                    //base product
                    BaseProduct baseProduct = (BaseProduct) m;
                    model.addRow(new Object[]{baseProduct.getName(), baseProduct.getRating(), baseProduct.getCalories(), baseProduct.getProteins(), baseProduct.getFats(), baseProduct.getSodium(), baseProduct.computePrice()});
                } else {
                    //composite product
                    CompositeProduct compProduct = (CompositeProduct) m;
                    model.addRow(new Object[]{compProduct.getName(), compProduct.getRating(), compProduct.getCalories(), compProduct.getProteins(), compProduct.getFats(), compProduct.getSodium(), compProduct.computePrice()});
                }
            }
        }

        return tabel;
    }

    public ArrayList<User> importUsers(String fileName) {
        File file = new File(fileName);
        Pattern pattern = Pattern.compile(" ");
        ArrayList<User> users = new ArrayList<User>();
        try {
            file.createNewFile();
            Scanner reader = new Scanner(file);
            while (reader.hasNextLine()) {
                String data = reader.nextLine();
                String[] attrib = pattern.split(data);
                switch (attrib[2]) {
                    case "administrator":
                        Admin a = new Admin(attrib[0], attrib[1]);
                        users.add(a);
                        break;
                    case "employee":
                        Employee employee = new Employee(attrib[0], attrib[1]);
                        users.add(employee);
                        break;
                    default:
                        Client client = new Client(attrib[0], attrib[1]);
                        users.add(client);
                        break;
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        d.users = users;
        return users;
    }

    public void addUser(User user) {
        File file = new File("users.txt");
        FileWriter writer = null;
        try {
            writer = new FileWriter(file, true);
            writer.write("\n");
            writer.write(user.getUsername() + " " + user.getPassword() + " " + user.getRole().getName());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeReport1(String fileName, ArrayList<Order> rez, int start, int end) {
        File file = new File(fileName);
        FileWriter writer = null;
        try {
            writer = new FileWriter(file, false);
            writer.write("Orders placed between " + start + " and " + end + ": \n");
            for (Order m : rez) {
                writer.write(m.toString() + "\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeReport2(String fileName, List<MenuItem> rez, int nrOfTimes) {
        File file = new File(fileName);
        FileWriter writer = null;
        try {
            writer = new FileWriter(file, false);
            writer.write("Products ordered more than " + nrOfTimes + " times : \n");
            for (MenuItem m : rez) {
                writer.write(m.toString() + " x" + m.getTimesOrd() + "\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeReport3(String fileName, List<User> rez, int nrOfOrd, int value) {
        File file = new File(fileName);
        FileWriter writer = null;
        try {
            writer = new FileWriter(file, false);
            writer.write("Clients that ordered more than " + nrOfOrd + " times and over the value of " + value + "\n");
            for (User m : rez) {
                writer.write(m.getUsername() + " x" + (m.getNrOfOrders()) + "\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeReport4(String fileName, List<MenuItem> rez, String day) {
        File file = new File(fileName);
        FileWriter writer = null;
        try {
            writer = new FileWriter(file, false);
            writer.write("Menu items ordered on " + day + ": \n");
            for (MenuItem m : rez) {
                writer.write(m.getName() + " purchased " + (m.getTimesOrd()) + " times\n");
            }
            if (rez.size() == 0) {
                writer.write("No products purchased on this day");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
