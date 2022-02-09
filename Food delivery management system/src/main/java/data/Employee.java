package data;

public class Employee extends User {

    public Employee(String username, String password) {
        super(username, password);
        this.setRole(Role.EMPLOYEE);
    }

    @Override
    public void setRole(Role role) {
        super.setRole(Role.EMPLOYEE);
    }


}
