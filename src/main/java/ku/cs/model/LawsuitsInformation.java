package ku.cs.model;

import javafx.beans.value.ObservableValue;

public class LawsuitsInformation {

    private Integer id;
    private String name;

    private String type;
    private String information;
    private String date;
    private String status;
    private Integer uID;
    private Integer lID;

    public LawsuitsInformation(Integer id, String name,String type, String information, String date,String status,Integer uID,Integer lID) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.information = information;
        this.date = date;
        this.status = status;
        this.uID = uID;
        this.lID = lID;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getuID() {
        return uID;
    }

    public void setuID(Integer uID) {
        this.uID = uID;
    }

    public Integer getlID() {
        return lID;
    }

    public void setlID(Integer lID) {
        this.lID = lID;
    }

    public String toCsv(){
        return "LawsuitsInformation" + "," +getName() +","+ getType() + "," + getInformation() +"," + getDate();
    }


}
