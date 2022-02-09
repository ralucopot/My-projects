package business;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class EmpObserver implements PropertyChangeListener, java.io.Serializable {
    private Order order;

    public void propertyChange(PropertyChangeEvent evt) {
        this.setOrder((Order) evt.getNewValue());
    }

    public Order getOrder() {
        return order;
    }

    private void setOrder(Order newOrder) {
        order = newOrder;
    }
}