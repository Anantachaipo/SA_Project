package ku.cs.model;

public class Order {
    private int oid;
    private int cid;
    private int pid;
    private int qty;
    private int bid;
    private String detail;
    private String status;
    private String time;

    public Order(int oid, int cid, int pid, int qty, int bid, String detail, String status, String time) {
        this.oid = oid;
        this.cid = cid;
        this.pid = pid;
        this.qty = qty;
        this.bid = bid;
        this.detail = detail;
        this.status = status;
        this.time = time;
    }

    public int getOid() {
        return oid;
    }

    public int getCid() {
        return cid;
    }

    public int getPid() {
        return pid;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Order ID[" + oid + "]" +
                " Status : " + status;
    }
}
