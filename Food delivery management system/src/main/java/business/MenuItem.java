package business;

public abstract class MenuItem implements java.io.Serializable {

    protected String name;
    public double rating;
    protected int calories;
    protected int proteins;
    protected int fats;
    protected int sodium;
    protected double price;
    protected int timesOrd;

    public MenuItem(String name, double rating) {
        this.name = name;
        this.rating = rating;
        this.timesOrd = 0;
    }

    public MenuItem(String name, double rating, int calories, int proteins, int fats, int sodium, double price) {
        this.name = name;
        this.rating = rating;
        this.calories = calories;
        this.proteins = proteins;
        this.fats = fats;
        this.sodium = sodium;
        this.price = price;
        this.timesOrd = 0;
    }

    public int getTimesOrd() {
        return timesOrd;
    }

    public void setTimesOrd(int timesOrd) {
        this.timesOrd = timesOrd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public int getProteins() {
        return proteins;
    }

    public void setProteins(int proteins) {
        this.proteins = proteins;
    }

    public int getFats() {
        return fats;
    }

    public void setFats(int fats) {
        this.fats = fats;
    }

    public int getSodium() {
        return sodium;
    }

    public void setSodium(int sodium) {
        this.sodium = sodium;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPrice() {
        return this.price;
    }

    abstract double computePrice();

}
