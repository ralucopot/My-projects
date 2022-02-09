package data;

public class Client extends User {

    public Client(String username, String password) {
        super(username, password);
        this.setRole(Role.CLIENT);
    }

    @Override
    public void setRole(Role role) {
        super.setRole(Role.CLIENT);
    }
}
