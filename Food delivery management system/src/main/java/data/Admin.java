package data;

public class Admin extends User {

    public Admin(String username, String password) {
        super(username, password);
        this.setRole(Role.ADMIN);
    }

    @Override
    public void setRole(Role role) {
        super.setRole(Role.ADMIN);
    }
}
