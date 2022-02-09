package business;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class Order implements java.io.Serializable {

    private int orderId;
    private String clientName;
    private Date orderDate;
    private double price;

    private transient ArrayList<MenuItem> order;

    public Order(int orderId, String clientName, Date date) {
        this.orderId = orderId;
        this.clientName = clientName;
        this.orderDate = date;
        order = new ArrayList<MenuItem>();
    }

    public ArrayList<MenuItem> getOrder() {
        return this.order;
    }

    public void setOrder(ArrayList<MenuItem> order) {
        for (MenuItem m : this.order) {
            this.order.remove(m);
        }
        this.order = order;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public int getOrderHour() {
        SimpleDateFormat formatHours = new SimpleDateFormat("HH");
        String getHours = formatHours.format(orderDate);

        return Integer.parseInt(getHours);
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order1 = (Order) o;
        return orderId == order1.orderId && Double.compare(order1.price, price) == 0 && Objects.equals(clientName, order1.clientName) && Objects.equals(orderDate, order1.orderDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, clientName, orderDate, price);
    }

    @Override
    public String toString() {
        String items = "";
        if (order != null) {
            for (MenuItem m : order) {
                items += " " + m.toString() + "\n";
            }
        }
        return "Order " + orderId + " made by client: " + clientName + " at: " + orderDate + " value: " + price + "\n" + items;
    }
}
