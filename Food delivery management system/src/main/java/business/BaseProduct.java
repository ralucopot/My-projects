package business;

import java.util.Objects;

public class BaseProduct extends MenuItem {

    public BaseProduct(String name, double rating, int calories, int proteins, int fats, int sodium, double price) {
        super(name, rating, calories, proteins, fats, sodium, price);
    }

    @Override
    public double computePrice() {
        return this.getPrice();
    }

    @Override
    public String toString() {
        return this.getName() + ": " + this.getRating() + " rating, " + this.getCalories() + " calories, " + this.getProteins() + " proteins, " + this.getFats() + " fats, " + this.getSodium() + " sodium, " + this.getPrice() + " price";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseProduct that = (BaseProduct) o;
        return that.getName().equals(this.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getName());
    }
}
