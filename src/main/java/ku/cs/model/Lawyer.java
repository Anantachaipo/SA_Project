//package ku.cs.model;
//
//
//import java.time.LocalDateTime;
//
//public class Lawyer extends User{
//
//    //officer
//    private String organizationName;
//
//    public Lawyer(String name,String surname, String username, String password, String pathProfile, String organizationName) {
//        this(accountName, username, password,pathProfile, LocalDateTime.of(0,1,1,0,0), organizationName);
//    }
//    public Lawyer(String accountName, String username, String password, String pathProfile, LocalDateTime lastLogin, String organizationName) {
//        super(accountName, username, password, pathProfile,lastLogin,0, false);
//        this.organizationName = organizationName;
//
//    }
//    @Override
//    public String getAccountName() {
//        return super.getAccountName();
//    }
//    @Override
//    public String getUsername() {
//        return super.getUsername();
//    }
//    @Override
//    public String getPassword() {
//        return super.getPassword();
//    }
//
//
//    public String getOrganizationName() {
//        return organizationName;
//    }
//
//    public void setOrganizationName(String organizationName) {
//        this.organizationName = organizationName;
//    }
//
//    @Override
//    public String getPathProfile() {return super.getPathProfile();}
//    public String toCsv(){
//        return "Officer" + "," + getAccountName() + "," + getUsername() + "," + getPassword() + "," + getPathProfile() + "," + getLastLogin() + "," + organizationName+ "," + isBoo();
//    }
//}
