package start;

import UI.Controller;
import UI.View;
import model.Model;

import java.util.logging.Logger;

/**
 * Clasa main in care se va executa codul
 */
public class Start {

    protected static final Logger LOGGER = Logger.getLogger(Start.class.getName());

    /**
     * Main-ul programului
     *
     * @param args
     */
    public static void main(String[] args) {
        Model model = new Model();
        View view = new View(model);
        Controller controller = new Controller(model, view);
        view.setVisible(true);
    }
}
