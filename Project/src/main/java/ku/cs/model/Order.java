package ku.cs.model;

public class Order {
    private int O_ID;
    private int OL_ID;
    private int P_ID;
    private int qty;
    private int bid;
    private String detail;
    private String time;

    public Order(int O_ID, int OL_ID, int P_ID, int qty, int bid, String detail, String time) {
        this.O_ID = O_ID;
        this.OL_ID = OL_ID;
        this.P_ID = P_ID;
        this.qty = qty;
        this.bid = bid;
        this.detail = detail;
        this.time = time;
    }

    public Order(int O_ID, int OL_ID, int P_ID, int qty, int bid, String detail) {
        this(O_ID, OL_ID, P_ID, qty, bid, detail, "-");
    }

    public Order(int P_ID, int qty, int bid, String detail) {
        this(-1, -1, P_ID, qty, bid, detail);
    }

    public int getO_ID() {
        return O_ID;
    }

    public int getOL_ID() {
        return OL_ID;
    }

    public int getP_ID() {
        return P_ID;
    }

    public int getQty() {
        return qty;
    }

    public int getBid() {
        return bid;
    }

    public String getDetail() {
        return detail;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void addBid(int bid) {
        this.bid += bid;
    }

    public void addQty(int qty) {
        this.qty += qty;
    }

    public void changeDetail(String detail) {
        this.detail = detail;
    }

    // TODO แก้ toString ให้แสดงผลถูกต้อง
    @Override
    public String toString() {
        return "Product ID[" + P_ID + "] " + "qty: " + qty + " bid: " + bid ;
    }
}
