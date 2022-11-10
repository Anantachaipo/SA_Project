package ku.cs.model;

import java.util.ArrayList;

public class OrderList {
    private int OL_ID;
    private int C_ID;
    private String Status;
    private int Con_ID;
    private ArrayList<Order> orders;

    public OrderList(int OL_ID, int C_ID, String status, int Con_ID) {
        this.OL_ID = OL_ID;
        this.C_ID = C_ID;
        this.Status = status;
        this.Con_ID = Con_ID;
        orders = new ArrayList<>();
    }

    public OrderList(int OL_ID, int C_ID, String status) {
        this(OL_ID, C_ID, status, -1);
    }

    public OrderList(int OL_ID, String status) {
        this(OL_ID, -1, status);
    }
    public OrderList() {
        orders = new ArrayList<>();
    }

    public int getOL_ID() {
        return OL_ID;
    }

    public int getC_ID() {
        return C_ID;
    }

    public String getStatus() {
        return Status;
    }

    public int getCon_ID() {
        return Con_ID;
    }

    public int getTotalBid() {
        int total = 0;
        for (Order order : orders) {
            total += order.getBid();
        }
        return total;
    }

    public int getNumOrder() {
        return orders.size();
    }

    public int getTotalQty() {
        int total = 0;
        for (Order order : orders) {
            total += order.getQty();
        }
        return total;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }

    public void addOrder(Order order) {
        orders.add(order);
    }

    public ArrayList<Order> getOrders() {
        return orders;
    }

    public Order getOrderByID(int P_ID) {
        for (Order order: orders) {
            if (order.getP_ID() == P_ID) {
                return order;
            }
        }
        return null;
    }

    public void clearOrder() {
        orders.clear();
    }

    public static String showStatus(String status) {
        return switch (status) {
            case "P" -> "Pending";
            case "A" -> "Accepted";
            case "R" -> "Rejected";
            case "W" -> "Waiting";
            case "S" -> "Success";
            case "F" -> "Fail";
            default -> "-";
        };
    }
    @Override
    public String toString() {
        return "ID: " + OL_ID + " Status: " + showStatus(Status);
    }
}
