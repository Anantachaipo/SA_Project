package ku.cs.service;

import ku.cs.controllers.UserLoginController;
import ku.cs.controllers.LawyerLoginController;
import ku.cs.model.Lawyer;
import ku.cs.model.LawyerList;
import ku.cs.model.User;
import ku.cs.model.UserList;

public class Account {

    //account

    public String recordAccount(String username, String name, String surname, String number ,String email,String password, String confirmPassword, String pathProfile) {

        //ใช้ตรวจสอบข้อความที่กรอกเข้ามา
        if (name.equals("") || surname.equals("")) {
            return "ยังไม่ได้กรอก ชื่อ หรือ นามสกุล ";
        } else if (!checkPassword(password, confirmPassword)) {
            return "กรอก Password ไม่ตรงกัน";
        } else if (number.equals("")) {
            return "ยังไม่ได้กรอก หมายเลขโทรศัพท์ ";
        } else if (email.equals("")){
            return "ยังไม่ได้กรอก Email";
        } else if (username.equals("")){
            return "ยังไม่ได้กรอก Username";
        }


        User user = new User(username, name, surname, password,email,number, pathProfile);

        DataSource<UserList> dataSource;
        dataSource = new UserListFileDataSource();
        UserList userList = dataSource.readData();

        if (userList.searchByUsername(username))
            return "Username นี้ถูกใช้ไปแล้ว";

        userList.addUser(user);

        dataSource.writeData(userList);

        return "P";
    }
    public String recordAccountLawyer(String username, String name, String surname,String sex, String password,String confirmPassword, String dateOfBirth, String attorneyLicensenumber,String idcard, String cardIssueDate,String cardReplacementDate,String number,String email, String lawOffice, String county,String caseAptitude,String pathProfile) {
        //ใช้ตรวจสอบข้อความที่กรอกเข้ามา
        if (username.equals("")) {
            return "ยังไม่ได้กรอก Username ";
        } else if (name.equals("") || surname.equals("")) {
            return "ยังไม่ได้กรอก Name หรือ Surname ";
        } else if (idcard.equals("")) {
            return "ยังไม่ได้กรอก หมายเลขบัตรประชาชน";
        } else if (dateOfBirth.equals("")) {
            return "ยังไม่ได้กรอก วันเดือนปี เกิด";
        } else if (attorneyLicensenumber.equals("")) {
            return "ยังไม่ได้กรอก ทนายความหมายเลขใบอนุญาตทนายความ";
        } else if (cardIssueDate.equals("")||cardReplacementDate.equals("")) {
            return "ยังไม่ได้กรอก วันเดือนที่ออกบัตร/เปลี่ยนบัตร";
        } else if (lawOffice.equals("")) {
            return "ยังไม่ได้กรอก สังกัดทนาย";
        } else if (county.equals("")) {
            return "ยังไม่ได้กรอก จังหวัด";
        } else if (email.equals("") || number.equals("")) {
            return "ยังไม่ได้กรอก email หรือ เบอร์โทรศัพท์";
        } else if (!checkPassword(password, confirmPassword)) {
            return "กรอก Password ไม่ตรงกัน";
        }
        //String username,String name,String surname,String password,String dateOfBirth, String attorneyLicensenumber,String idCard, String lawyerTicket,String cardIssueDate,String cardReplacementDate,String number, String email ,String pathProfile, String lawOffice ,String county
        Lawyer lawyer = new Lawyer(username,name,surname,sex,password,number,email,dateOfBirth,attorneyLicensenumber,idcard ,cardIssueDate,cardReplacementDate,lawOffice,county,caseAptitude,pathProfile);

        DataSourceLawyer<LawyerList> dataSource;
        dataSource = new LawyerListFileDataSource();
        LawyerList lawyerList = dataSource.readDataLawyer();

        if (lawyerList.searchByUsername(username))
            return "Username นี้ถูกใช้ไปแล้ว";
        lawyerList.addLawyer(lawyer);

        dataSource.writeDataLawyer(lawyerList);

        return "P";
    }


    //หน้า login
    public User checkUserName(String username, String password) {

        DataSource<UserList> dataSource;
        dataSource = new UserListFileDataSource();
        UserList userList = dataSource.readData();

        for (User user : userList.getUsers()) {
            if (user.getUsername().equals(username)) {
                if (user.getPassword().equals(password)) {
                    UserLoginController.user = user;
                    dataSource.writeData(userList);
                    return user;
                }
            }
        }

        return null;


    }
    public Lawyer checkUserNameLawyer(String username, String password) {

        DataSourceLawyer<LawyerList> dataSource;
        dataSource = new LawyerListFileDataSource();
        LawyerList lawyerList = dataSource.readDataLawyer();

        for (Lawyer lawyer : lawyerList.getLawyers()) {
            if (lawyer.getUsernameLawyer().equals(username)) {
                if (lawyer.getPassword().equals(password)) {
                    LawyerLoginController.lawyer = lawyer;
                    dataSource.writeDataLawyer(lawyerList);
                    return lawyer;
                }
            }
        }

        return null;


    }
    public boolean checkPassword(String password, String confirmPassword) {
        if (password.equals("") || confirmPassword.equals("")) {
            System.out.println("ยังไม่ได้กรอก Password");
            return false;
        } else if (!password.equals(confirmPassword)) {
            System.out.println("กรอก Password ไม่ตรงกัน");
            return false;
        }
        return true;
    }
    //แบนผู้ใช้งาน
//    public boolean checkBan(String username) {
//
//        DataSource<UserList> dataSource;
//        dataSource = new UserListFileDataSource();
//        UserList userList = dataSource.readData();
//
//        for (User user : userList.getUsers()) {
//            if (user.getUsername().equals(username)&& user.isBoo() == true ) {
//                user.setBan(user.getBan()+1);
//                dataSource.writeData(userList);
//                return false;
//
//            }
//        }
//
//        return true;
//
//
//    }

    public String changePassword(User otherMember, String password, String newPassword, String confirmNewPassword) {
        if (password.equals("") || newPassword.equals("") || confirmNewPassword.equals("")) {
            return "ยังกรอกข้อมูลไม่ครบ";
        } else if (!checkPassword(newPassword, confirmNewPassword)) {
            return "กรอก NewPassword ไม่ตรงกัน";
        } else if (!checkPassword(password, otherMember.getPassword())) {
            return "กรอก Password ไม่ถูกต้อง";
        } else if(password.equals(newPassword)){
            return  "Password ซ้ำกับ Password อันเดิม";
        }


        DataSource<UserList> dataSource;
        dataSource = new UserListFileDataSource();
        UserList userList = dataSource.readData();


        for (User user : userList.getUsers()) {
            if (user.getUsername().equals(otherMember.getUsername())) {
                user.setPassword(newPassword);

            }

        }
        dataSource.writeData(userList);
        return "P";

    }

    public void changeImage(String pathProfile){
        DataSource<UserList> dataSource;
        dataSource = new UserListFileDataSource();
        UserList userList = dataSource.readData();
        for(User user:userList.getUsers()){
            if(user.getUsername().equals(UserLoginController.user.getUsername())){
                user.setPathProfile(pathProfile);
            }
        }
        dataSource.writeData(userList);
    }



}