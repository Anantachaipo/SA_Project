package ku.cs.model;

public class Contract {
    private int con_Id;
    private int con_Length;
    private int con_Deposit;
    private String con_Status;
    private int c_Id;

    public Contract(int con_Id, int con_Length, int con_Deposit, String con_Status, int c_Id) {
        this.con_Id = con_Id;
        this.con_Length = con_Length;
        this.con_Deposit = con_Deposit;
        this.con_Status = con_Status;
        this.c_Id = c_Id;
    }
    public Contract(int con_Id, int con_Length, int con_Deposit, int c_Id) {
        this(con_Id, con_Length, con_Deposit, "V", c_Id);
    }
    public int getCon_Id() {
        return con_Id;
    }
    public int getCon_Length() {
        return con_Length;
    }
    public int getCon_Deposit() {
        return con_Deposit;
    }
    public String getCon_Status() {
        return con_Status;
    }
    public void terminateContract() {
        this.con_Status = "T";
    }
    public int getC_Id() {
        return c_Id;
    }
    public static String showStatus(String status) {
        return switch (status) {
            case "V" -> "Valid";
            case "T" -> "Terminated";
            default -> "-";
        };
    }
    @Override
    public String toString() {
        return "Contract ID: " + con_Id + " - Status: " + showStatus(con_Status);
    }
}
