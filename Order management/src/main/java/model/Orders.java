package model;

/**
 * Clasa care reprezinta obiectul de tip order, care are ca atribute: id-ul, cantitatea, id-ul si numele clientului,
 * numele produsului si pretul total.
 * Contine constructori, metode de get, de set si toString.
 */
public class Orders {

    public int order_id;
    private int quantity;
    private int client_id;
    private String client_name;
    private String product_name;
    private double total_price;

    public Orders() {

    }

    public Orders(int order_id, int quantity, int client_id, String client_name, String product_name, double total_price) {
        this.order_id = order_id;
        this.quantity = quantity;
        this.client_id = client_id;
        this.client_name = client_name;
        this.product_name = product_name;
        this.total_price = total_price;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


    public int getClient_id() {
        return client_id;
    }

    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }

    public String getClient_name() {
        return client_name;
    }

    public void setClient_name(String client_name) {
        this.client_name = client_name;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public double getTotal_price() {
        return total_price;
    }

    public void setTotal_price(double total_price) {
        this.total_price = total_price;
    }

    /**
     * Metoda care ajuta la afisarea obiectului.
     *
     * @return String-ul care va fi afisat, cu detaliile despre obiect.
     */
    @Override
    public String toString() {
        return "Order: " + order_id + ", client: " + client_name + ", product: " + product_name + ", quantity: " + quantity + " total: " + total_price + "\n";
    }
}
