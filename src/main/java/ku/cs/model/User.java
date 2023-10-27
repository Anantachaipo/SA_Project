package ku.cs.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class User {

    // user
    private String name;
    private String surname;
    private String username;
    private String password;
    private String pathProfile;
    private String email;
    private String number;
    private LocalDateTime lastLogin;


    public User(String username, String name, String surname, String password,String email,String number ,String pathProfile) {
        this(username, name,surname, password,email,number,pathProfile ,LocalDateTime.of(0,1,1,0,0));
    }

    public User(String username, String name,String surname, String password,String email,String number, String pathProfile, LocalDateTime lastLogin) {
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.email = email;
        this.number = number;
        this.pathProfile = pathProfile;
        this.lastLogin = lastLogin;
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

    public LocalDateTime getLastLogin() {
        return lastLogin;
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


    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }

    public void setPathProfile(String pathProfile) {
        this.pathProfile = pathProfile;
    }



    @Override
    public String toString() {
        if(lastLogin.equals(LocalDateTime.of(0,1,1,0,0))){
            return "-------------------"  + "  |  " + "Username :  " + username ;
        }

        return  lastLogin.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + "  |  " + "Username :  " + username ;
    }



    public String toCsv(){
        return "User" + "," + username + "," + name + "," + surname + "," + password+ "," + number+  "," + email +","+ pathProfile +  "," +lastLogin ;
    }
}
