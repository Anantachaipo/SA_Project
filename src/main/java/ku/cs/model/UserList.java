package ku.cs.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class UserList {

    private ArrayList<User> users;

    public UserList(){
        users = new ArrayList<>();
    }
    public void addUser(User user){
        users.add(user);
    }

    public void sort(Comparator<User> userComparator) {
        Collections.sort(users, userComparator);
    }
    public ArrayList<User> getUsers(){
        return this.users;
    }

    public boolean searchByUsername(String username){
        for(User user: users){
            if (user.getUsername().equals(username)) {
                // ถ้าซ้ากับใน list
                return true;
            }
        }
        return false;
    }
    @Override
    public String toString() {
        return "UserList{" +
                "users=" + users +
                '}';
    }

}
