package ku.cs.model;

import java.util.ArrayList;

public class ProductList {

    private ArrayList<Product> products;

    public ProductList() {
        products = new ArrayList<>();
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public ArrayList<Product> getProducts() {
        return this.products;
    }

    public Product getProductByName(String name) {
        for (Product product : products) {
            System.out.println("Checking product : " + product.getName() + " |with| " + name);
            if (product.getName().equals(name)) {
                return product;
            }
        }
        return null;
    }
    @Override
    public String toString() {
        return "ProductList{" +
                "products=" +
                products +
                "}";
    }
}
