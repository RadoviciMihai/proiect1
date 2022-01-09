package christmas;

import enums.Category;
import org.json.simple.JSONObject;

public final class Gift {
    private String productName;
    private double price;
    private Category category;

    Gift(final String productName,
         final double price,
         final Category category) {
        this.productName = productName;
        this.price = price;
        this.category = category;
    }

    public Gift(final JSONObject json) {
        this.productName = (String) json.get("productName");
        this.price = (double) json.get("price");
        String categoryString = (String) json.get("category");
        this.category = Category.valueOf(categoryString);
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(final String productName) {
        this.productName = productName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(final double price) {
        this.price = price;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(final Category category) {
        this.category = category;
    }
}
