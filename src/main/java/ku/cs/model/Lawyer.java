package ku.cs.model;


import java.time.LocalDate;
import java.time.LocalDateTime;

public class Lawyer extends User{


    //officer
    private String dateOfBirth;
    private String attorneyLicensenumber;
    private String lawerTicket;
    private String cardIssueDate;
    private String cardReplacementDate;
    private String lawOffice;
    private String county;
    private String idCard;

//    public Lawyer(String username, String name, String surname, String password, String dateOfBirth, String attorneyLicensenumber, String idCard, String lawyerTicket,String cardIssueDate, String cardReplacementDate, String number, String email , String pathProfile, String lawOffice , String county) {
//        this(username,name, surname,password, dateOfBirth,attorneyLicensenumber,idCard,lawyerTicket,cardIssueDate,cardReplacementDate,number,email,pathProfile, lawOffice,county,LocalDateTime.of(0,1,1,0,0));
//    }



    public Lawyer(String username,String name, String surname,String password, String dateOfBirth, String attorneyLicensenumber, String idCard,String lawyerTicket, String cardIssueDate, String cardReplacementDate, String number, String email, String pathProfile, String lawOffice,String county) {
        super(username,name,surname,password,pathProfile,email,number);
        this.dateOfBirth = String.valueOf(dateOfBirth);
        this.attorneyLicensenumber = attorneyLicensenumber;
        this.idCard = idCard;
        this.lawerTicket = lawyerTicket;
        this.cardIssueDate = String.valueOf(cardIssueDate);
        this.cardReplacementDate =String.valueOf(cardReplacementDate);
        this.lawOffice = lawOffice;
        this.county = county;


    }



    @Override
    public String getAccountName() {
        return super.getAccountName();
    }
    @Override
    public String getSurname() {return super.getSurname();
    }
    @Override
    public String getUsername() {
        return super.getUsername();
    }
    @Override
    public String getPassword() {
        return super.getPassword();
    }
    @Override
    public String getPathProfile() {return super.getPathProfile();}

    public String getDateOfBirth() {
        return dateOfBirth;
    }
    public String getAttorneyLicensenumber() {
        return attorneyLicensenumber;
    }

    public String getLawerTicket() {
        return lawerTicket;
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

    public String toCsv(){
        return "Lawyer" + "," +getUsername() + "," + getAccountName()  +"," + getSurname()  + "," + getPassword() + "," + getNumber() + ","+ getEmail() +","+ dateOfBirth + "," + attorneyLicensenumber  + "," + idCard + "," +lawerTicket+","+ cardIssueDate + "," +cardReplacementDate+ "," +lawOffice+ "," + county +"," + getPathProfile()  ;
    }
}
