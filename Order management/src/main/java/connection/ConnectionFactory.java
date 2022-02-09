package connection;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Aceasta clasa realizeaza conexiunea cu baza de date, implementand singleton design patterns pentru conexiune
 */
public class ConnectionFactory {

    private static final Logger LOGGER = Logger.getLogger(ConnectionFactory.class.getName());
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DBURL = "jdbc:mysql://localhost:3306/warehousedb";
    private static final String USER = "root";
    private static final String PASS = "root";

    private static ConnectionFactory singleInstance = new ConnectionFactory();

    /**
     * Constructorul clasei
     */
    private ConnectionFactory() {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Metoda genereaza conexiunea catre baza de date. Afiseaza un warning in cazul in care ea nu poate fi realizata
     *
     * @return conexiunea catre baza de date
     */
    private Connection createConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DBURL, USER, PASS);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Cannot connect to the database");
            e.printStackTrace();
        }
        return connection;
    }

    /**
     * Getter pentru baza de date
     *
     * @return conexiunea cu baza de date
     */
    public static Connection getConnection() {
        return singleInstance.createConnection();
    }

    /**
     * Metoda care inchide conexiunea catre baza de date
     *
     * @param connection conexiunea pe care dorim sa o inchidem
     */
    public static void close(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "An error occured while trying to close the connection");
            }
        }
    }

    /**
     * Metoda care inchide statementul
     *
     * @param statement statementul pe care dorim sa il inchidem
     */
    public static void close(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "An error occured while trying to close the statement");
            }
        }
    }

    /**
     * Metoda care inchide resultSet-ul
     *
     * @param resultSet ResultSet-ul pe care dorim sa il inchidem
     */
    public static void close(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "An error occured while trying to close the ResultSet");
            }
        }
    }
}
