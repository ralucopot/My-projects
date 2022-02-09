package data;

public enum Role {
    ADMIN ("administrator"),
    CLIENT ("client"),
    EMPLOYEE("employee");

    private String name;
    Role(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
