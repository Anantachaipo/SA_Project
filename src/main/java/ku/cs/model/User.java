package ku.cs.model;



public class User {

    // user
    private String name;
    private String surname;
    private String username;
    private String password;
    private String pathProfile;
    private String email;
    private String number;


//    public User(String username, String name, String surname, String number,String email,String password ,String pathProfile) {
//        this(username, name,surname, number,email,password,pathProfile));
//    }

    public User(String username, String name,String surname, String number,String email,String password, String pathProfile) {
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.email = email;
        this.number = number;
        this.pathProfile = pathProfile;

    }


    public String getAccountName() {
        return name;
    }
    public String getSurname() {
        return surname;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getNumber() {
        return number;
    }

    public String getPathProfile() {
        return pathProfile;
    }


    public void setAccountName(String accountName) {
         this.name = name;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPathProfile(String pathProfile) {
        this.pathProfile = pathProfile;
    }


    public String toCsv(){
        return "User" + "," + username + "," + name + "," + surname + "," + number+ "," + email+  "," + password +","+ pathProfile ;
    }
}
