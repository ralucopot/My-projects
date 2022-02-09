package data;

public abstract class User implements java.io.Serializable {
    private Role role;
    private String username;
    private String password;
    private int nrOfOrders;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getNrOfOrders() {
        return nrOfOrders;
    }

    public void setNrOfOrders(int nrOfOrders) {
        this.nrOfOrders = nrOfOrders;
    }

    @Override
    public String toString() {
        return "User{" +
                "role=" + role +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
