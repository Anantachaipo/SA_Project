package ku.cs.model;

public class Order {
    private int oid;
    private int cid;
    private int qty;
    private double bid;
    private String detail;
    private String status;

    public Order(int oid, int cid, int qty, double bid, String detail, String status) {
        this.oid = oid;
        this.cid = cid;
        this.qty = qty;
        this.bid = bid;
        this.detail = detail;
        this.status = status;
    }

    public int getOid() {
        return oid;
    }

    public int getCid() {
        return cid;
    }

    public int getQty() {
        return qty;
    }

    public double getBid() {
        return bid;
    }

    public String getDetail() {
        return detail;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "Order ID[" + oid + "]" +
                " Status : " + status;
    }
}
