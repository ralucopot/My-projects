package dao;

import connection.ConnectionFactory;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implementeaza metodele CRUD pentru instante ale claselor Model si pentru elementele acestora, utilizand tehinca de "reflection"
 *
 * @param <T> tipul clasei Model: Client, Product sau Order
 */
public abstract class AbstractDAO<T> {

    protected static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());
    private final Class<T> type;

    @SuppressWarnings("unchecked")
    public AbstractDAO() {
        this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    /**
     * Creeaza query-ul pentru SELECT
     *
     * @param field coloana din tabel dupa care facem SELECT, de exemplu id-ul
     * @return query-ul
     */
    private String createSelectQuery(String field) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM ");
        sb.append(type.getSimpleName());
        sb.append(" WHERE " + field + " =?");
        return sb.toString();
    }

    /**
     * Creeaza query-ul pentru INSERT
     *
     * @param fields coloanele tabelului
     * @return query-ul
     */
    private String createInsertQuery(Field[] fields) {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO ");
        sb.append(type.getSimpleName());
        sb.append(" (");

        for (int i = 0; i < fields.length; i++) {
            sb.append(fields[i].getName());
            if (i < fields.length - 1) {
                sb.append(", ");
            }
        }
        sb.append(") \nVALUES (");
        for (int i = 0; i < fields.length; i++) {
            sb.append("?");
            if (i < fields.length - 1) {
                sb.append(", ");
            } else {
                sb.append(")");
            }
        }
        return sb.toString();
    }

    /**
     * Creeaza query-ul pentru DELETE
     *
     * @param field campul (coloana) in functie de care se realizeaza stergerea
     * @return query-ul
     */
    private String createDeleteQuery(String field) {
        StringBuilder sb = new StringBuilder();
        sb.append("DELETE FROM ");
        sb.append(type.getSimpleName());
        sb.append(" WHERE " + field + " =?");
        return sb.toString();
    }

    /**
     * query-ul pentru UPDATE
     *
     * @param fields coloanele tabelului
     * @param id     cheia primara, pentru a face update unui singur element
     * @return query-ul
     */
    private String createUpdateQuery(Field[] fields, String id) {
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE ");
        sb.append(type.getSimpleName());
        sb.append(" SET ");

        for (int i = 1; i < fields.length; i++) {
            sb.append(fields[i].getName());
            sb.append(" =?");
            if (i < fields.length - 1) {
                sb.append(", ");
            }
        }
        sb.append(" WHERE " + fields[0].getName() + " =?");
        return sb.toString();
    }

    /**
     * Cautarea in functie de o anumita coloana din tabel
     *
     * @param field     elementul in functie de care facem cautarea (ex: id)
     * @param fieldName numele coloanei in tabel
     * @return elementul/elementele gasite
     */
    public T findByField(Object field, String fieldName) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectQuery(fieldName);
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setObject(1, field);
            resultSet = statement.executeQuery();
            List<T> list = createObjects(resultSet);
            if (list.isEmpty()) {
                return null;
            }
            return list.get(0);

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findByField " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    /**
     * Insereaza un element nou in tabel
     *
     * @param t elementul care urmeaza sa fie inserat
     * @return elementul, in caz de success
     */
    public T insert(T t) {
        Connection connection = null;
        PreparedStatement statement = null;
        String query = createInsertQuery(type.getDeclaredFields());
        int ok = 0;
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            int index = 1;
            for (Field field : type.getDeclaredFields()) {
                field.setAccessible(true);
                statement.setObject(index, field.get(t));
                index++;
            }
            ok = statement.executeUpdate();
        } catch (SQLException | IllegalAccessException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:insert " + e.getMessage());
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return t;
    }

    /**
     * Stergerea in functie de o anumita coloana
     *
     * @param field     campul in functie de care stergem
     * @param fieldName numele campului in tabel
     */
    public void deleteByField(Object field, String fieldName) {
        Connection connection = null;
        PreparedStatement statement = null;
        String query = createDeleteQuery(fieldName);
        int ok = 0;
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setObject(1, field);
            ok = statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:deleteByField " + e.getMessage());
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
    }

    /**
     * Editarea unui element al tabelului
     *
     * @param t  noul element, contine modificarile aduse elementului initial
     * @param id id-ul elementului care va fi editat
     */
    public void update(T t, String id) {
        Connection connection = null;
        PreparedStatement statement = null;

        int ok;
        String query = createUpdateQuery(type.getDeclaredFields(), id);
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            int index = 1;

            Field[] fields = type.getDeclaredFields();

            for (int i = 1; i < fields.length; i++) {
                fields[i].setAccessible(true);
                statement.setObject(index, fields[i].get(t));
                index++;
            }
            fields[0].setAccessible(true);

            statement.setObject(index, fields[0].get(t));
            ok = statement.executeUpdate();

        } catch (SQLException | IllegalAccessException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:update " + e.getMessage());
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
    }

    /**
     * Selecteaza toate elementele din tabel
     *
     * @param t tipul elementului din clasa Model, Client, Product sau Order
     * @return rezultatul lui SELECT *
     */
    public ArrayList<T> findAll(T t) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "SELECT * FROM " + type.getSimpleName();
        try {
            connection = ConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            return (ArrayList<T>) createObjects(resultSet);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(preparedStatement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    /**
     * Converteste elementele din tipul resultSet in lista
     *
     * @param resultSet este resultSet-ul care urmeaza sa fie convertit
     * @return lista de obiecte de tipul T obtinute din resultSet
     */
    private List<T> createObjects(ResultSet resultSet) {
        List<T> list = new ArrayList<T>();
        Constructor[] ctors = type.getDeclaredConstructors();
        Constructor ctor = null;
        for (int i = 0; i < ctors.length; i++) {
            ctor = ctors[i];
            if (ctor.getGenericParameterTypes().length == 0)
                break;
        }
        try {
            while (resultSet.next()) {
                ctor.setAccessible(true);
                T instance = (T) ctor.newInstance();
                for (Field field : type.getDeclaredFields()) {
                    String fieldName = field.getName();
                    Object value = resultSet.getObject(fieldName);
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(fieldName, type);
                    Method method = propertyDescriptor.getWriteMethod();
                    method.invoke(instance, value);
                }
                list.add(instance);
            }
        } catch (InstantiationException | IllegalAccessException | SecurityException | IllegalArgumentException | InvocationTargetException | SQLException | IntrospectionException e) {
            e.printStackTrace();
        }
        return list;
    }


}
