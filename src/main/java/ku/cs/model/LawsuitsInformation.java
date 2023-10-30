package ku.cs.model;

public class LawsuitsInformation {

    private String name;

    private String type;
    private String information;
    private String date;

    public LawsuitsInformation(String name, String type, String information, String date) {
        this.name = name;
        this.type = type;
        this.information = information;
        this.date = date;
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

    public String toCsv(){
        return "LawsuitsInformation" + "," +getName() +","+ getType() + "," + getInformation() +"," + getDate();
    }
}
