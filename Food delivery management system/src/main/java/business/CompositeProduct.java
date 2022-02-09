package business;

import java.util.ArrayList;

public class CompositeProduct extends MenuItem {

    private ArrayList<MenuItem> items;

    public CompositeProduct(String name, double rating, ArrayList<MenuItem> menu) {
        super(name, rating);
        items = new ArrayList<MenuItem>();
        items = menu;
        if (menu != null) {
            for (MenuItem m : items) {
                calories += m.getCalories();
                proteins += m.getProteins();
                sodium += m.getSodium();
                fats += m.getFats();
            }
        }
    }

    public ArrayList<MenuItem> getItems() {
        return items;
    }

    @Override
    public double computePrice() {
        double price = 0;
        for (MenuItem m : items) {
            price += m.computePrice();
        }
        return price;
    }

    public void addProduct(MenuItem menuItem) {
        items.add(menuItem);
    }

    public void removeAllProducts() {
        items = new ArrayList<MenuItem>();
    }

    public void removeProduct(MenuItem menuItem) {
        items.remove(menuItem);
    }

    @Override
    public String toString() {
        return "CompositeProduct{" +
                "items=" + items +
                ", name='" + name + '\'' +
                ", rating=" + rating +
                ", calories=" + calories +
                ", proteins=" + proteins +
                ", fats=" + fats +
                ", sodium=" + sodium +
                ", price=" + price +
                '}';
    }
}
