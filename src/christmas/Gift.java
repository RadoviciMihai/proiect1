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
        this.price = ((Long) json.get("price")).doubleValue();
        String categoryString = (String) json.get("category");
        this.category = Category.retrieveByCategory(categoryString);
    }

    public JSONObject getJsonObject() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("productName", productName);
        jsonObject.put("price", price);
        jsonObject.put("category", category.getValue());
        return jsonObject;
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
