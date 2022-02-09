package model;

/**
 * Clasa care reprezinta obiectul de tip produs, care are ca atribute un id, numele, cantitatea si pretul.
 * Contine constructori, metode de get, de set si toString.
 */
public class Product {

    private int product_id;
    private String product_name;
    private int quantity;
    private double price;

    public Product(int product_id, String product_name, int quantity, double price) {
        this.product_id = product_id;
        this.product_name = product_name;
        this.quantity = quantity;
        this.price = price;
    }

    public Product() {

    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Metoda care ajuta la afisarea obiectului
     *
     * @return String-ul care va fi afisat, cu detaliile despre obiect
     */
    @Override
    public String toString() {
        return "Product: " + product_id + ", " + product_name + " quantity: " + quantity + ", price: " + price + "\n";
    }
}
