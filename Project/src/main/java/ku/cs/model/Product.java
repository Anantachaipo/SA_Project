package ku.cs.model;
public class Product {
    private int pid;
    private String name;
    private String type;
    private int qty;
    private int PPU;

    public Product(int pid, String name, String type, int qty, int PPU) {
        this.pid = pid;
        this.name = name;
        this.type = type;
        this.qty = qty;
        this.PPU = PPU;
    }

    public int getPid() {
        return pid;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getQty() {
        return qty;
    }

    public int getPPU() {
        return PPU;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public void addQty(int qty) {
        this.qty += qty;
    }

    public void setPPU(int PPU) {
        this.PPU = PPU;
    }

    @Override
    public String toString() {
        return "pid[" + pid + "] " +
                name +
                "(" + type + ")" +
                " QTY : " + qty +
                " PPU : " + PPU;
    }
}
