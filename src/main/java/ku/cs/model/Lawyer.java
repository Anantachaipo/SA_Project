package ku.cs.model;


public class Lawyer {


    //officer
    private String nameLawyer;
    private String surnameLawyer;
    private String usernameLawyer;
    private String password;
    private String email;
    private String number;
    private String dateOfBirth;
    private String attorneyLicensenumber;

    private String cardIssueDate;
    private String cardReplacementDate;
    private String lawOffice;
    private String county;
    private String idCard;
    private String pathProfile;
    private String caseAptitude;
    private String sex;

//    public Lawyer(String username, String name, String surname, String password, String dateOfBirth, String attorneyLicensenumber, String idCard, String lawyerTicket,String cardIssueDate, String cardReplacementDate, String number, String email , String pathProfile, String lawOffice , String county) {
//        this(username,name, surname,password, dateOfBirth,attorneyLicensenumber,idCard,lawyerTicket,cardIssueDate,cardReplacementDate,number,email,pathProfile, lawOffice,county,LocalDateTime.of(0,1,1,0,0));
//    }



    public Lawyer(String usernameLawyer,String nameLawyer, String surnameLawyer,String sex,String password,String number, String email, String dateOfBirth, String attorneyLicensenumber, String idCard, String cardIssueDate, String cardReplacementDate, String lawOffice,String county,String caseAptitude,String pathProfile) {
//        super(username,name,surname,password,pathProfile,email,number);
        this.usernameLawyer = usernameLawyer;
        this.nameLawyer = nameLawyer;
        this.surnameLawyer = surnameLawyer;
        this.password = password;
        this.number = number;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.attorneyLicensenumber = attorneyLicensenumber;
        this.idCard = idCard;
        this.cardIssueDate = cardIssueDate;
        this.cardReplacementDate = cardReplacementDate;
        this.lawOffice = lawOffice;
        this.county = county;
        this.pathProfile = pathProfile;
        this.caseAptitude = caseAptitude;
        this.sex = sex;
    }

    public String getNameLawyer() {
        return nameLawyer;
    }

    public String getSurnameLawyer() {
        return surnameLawyer;
    }

    public String getUsernameLawyer() {
        return usernameLawyer;
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

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getAttorneyLicensenumber() {
        return attorneyLicensenumber;
    }

    public String getCardIssueDate() {
        return cardIssueDate;
    }

    public String getCardReplacementDate() {
        return cardReplacementDate;
    }

    public String getLawOffice() {
        return lawOffice;
    }

    public String getCounty() {
        return county;
    }

    public String getIdCard() {
        return idCard;
    }
    public String getPathProfile() {
        return pathProfile;
    }

    public String getCaseAptitude() {
        return caseAptitude;
    }

    public String getSex() {
        return sex;
    }

    public String toCsv(){
        return "Lawyer" + "," +getUsernameLawyer() + "," + getNameLawyer()  +"," + getSurnameLawyer()+","+ getSex() + "," + getNumber() + ","+ getEmail() + "," + getPassword()+","+ getDateOfBirth() + "," + getAttorneyLicensenumber()  + "," + getIdCard() + ","+ getCardIssueDate() + "," +getCardReplacementDate()+ "," +getLawOffice()+ "," + getCounty() +","+getCaseAptitude()+"," + getPathProfile()  ;
    }
}
