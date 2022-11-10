package ku.cs.model;

public class Customer {
    private int id;
    private String name;
    private String username;
    private String password;
    private String tel;

    public Customer(int id, String name, String username, String password, String tel) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.tel = tel;
    }

    public Customer(int id, String name, String username, String password) {
        this(id, name, username, password, "-");
    }

    public Customer(int id, String name, String tel) {
        this(id, name, "-", "-", tel);
    }
    public Customer(int id, String name) {
        this(id, name, "-");
    }

    public int getId() {
        return id;
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
    public String getTel() {
        return tel;
    }
}
