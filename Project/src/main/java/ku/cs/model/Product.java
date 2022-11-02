package ku.cs.model;
public class Product {
    private int id;
    private String name;
    private String type;
    private int qty;
    private int PPU;

    public Product(int id, String name, String type, int qty, int PPU) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.qty = qty;
        this.PPU = PPU;
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

    public void setPPU(int PPU) {
        this.PPU = PPU;
    }

    @Override
    public String toString() {
        return "id[" + id + "] " +
                name +
                "(" + type + ")" +
                " QTY : " + qty +
                " PPU : " + PPU;
    }
}
