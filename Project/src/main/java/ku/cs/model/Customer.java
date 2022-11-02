package ku.cs.model;

public class Customer {
    private int id;
    private int con_id;
    private String name;
    private String username;
    private String password;

    public Customer(int id, String name, String username, String password) {
        this(id, -1, name, username, password);
    }

    public Customer(int id, int con_id, String name, String username, String password) {
        this.id = id;
        this.con_id = con_id;
        this.name = name;
        this.username = username;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public int getCon_id() {
        return con_id;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

}
