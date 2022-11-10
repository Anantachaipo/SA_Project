package ku.cs.model;

public class Receipt {
    int R_No;
    int C_ID;
    int OL_ID;

    public Receipt(int R_No, int C_ID, int OL_ID) {
        this.R_No = R_No;
        this.C_ID = C_ID;
        this.OL_ID = OL_ID;
    }

    public Receipt(int C_ID, int OL_ID) {
        this(-1, C_ID, OL_ID);
    }

    public int getR_No() {
        return R_No;
    }

    public int getC_ID() {
        return C_ID;
    }

    public int getOL_ID() {
        return OL_ID;
    }

    @Override
    public String toString() {
        return "No." + R_No + " Order ID: " + OL_ID + " Customer ID: " + C_ID;
    }
}
