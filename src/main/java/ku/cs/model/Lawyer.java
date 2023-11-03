package ku.cs.model;


public class Lawyer  {


    //lawyer
    private Integer lawyerID;
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

    private Integer countConsult;
    private Integer countProgress;
    private Integer countSuccess;

//    public Lawyer(String username, String name, String surname, String password, String dateOfBirth, String attorneyLicensenumber, String idCard, String lawyerTicket,String cardIssueDate, String cardReplacementDate, String number, String email , String pathProfile, String lawOffice , String county) {
//        this(username,name, surname,password, dateOfBirth,attorneyLicensenumber,idCard,lawyerTicket,cardIssueDate,cardReplacementDate,number,email,pathProfile, lawOffice,county,LocalDateTime.of(0,1,1,0,0));
//    }



    public Lawyer(Integer lawyerID,String usernameLawyer,String nameLawyer, String surnameLawyer,String sex,String password,String number, String email, String dateOfBirth, String attorneyLicensenumber, String idCard, String cardIssueDate, String cardReplacementDate, String lawOffice,String county,String caseAptitude,Integer countConsult,Integer countProgress,Integer countSuccess,String pathProfile) {
        this.lawyerID = lawyerID;
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
        this.countConsult = countConsult;
        this.countProgress = countProgress;
        this.countSuccess = countSuccess;
    }
    public Lawyer(String nameLawyer,String surnameLawyer){
        this.nameLawyer = nameLawyer;
        this.surnameLawyer = surnameLawyer;
    }

    public Lawyer(String lName, String lSurname, String lAttorneyLicensenumber, String lLawOffice, String lCounty) {
    }

    public Integer getLawyerID() {
        return lawyerID;
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

    public Integer getCountConsult() {
        return countConsult;
    }

    public void setCountConsult(Integer countConsult) {
        this.countConsult = countConsult;
    }

    public Integer getCountProgress() {
        return countProgress;
    }

    public void setCountProgress(Integer countProgress) {
        this.countProgress = countProgress;
    }

    public Integer getCountSuccess() {
        return countSuccess;
    }

    public void setCountSuccess(Integer countSuccess) {
        this.countSuccess = countSuccess;
    }

    @Override
    public String toString() {
        return "Lawyer --> " +
                "usernameLawyer = '" + nameLawyer + " - " + surnameLawyer ;
    }
}
