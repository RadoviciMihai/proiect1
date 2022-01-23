package christmas;

import enums.Category;
import org.json.simple.JSONObject;

public final class Gift {
    private String productName;
    private double price;
    private Category category;
    private int quantity;

    Gift(final String productName,
         final double price,
         final Category category,
         final int quantity) {
        this.productName = productName;
        this.price = price;
        this.category = category;
        this.quantity = quantity;
    }

    public Gift(final JSONObject json) {
        this.productName = (String) json.get("productName");
        this.price = ((Long) json.get("price")).doubleValue();
        this.quantity = ((Long) json.get("quantity")).intValue();
        String categoryString = (String) json.get("category");
        this.category = Category.retrieveByCategory(categoryString);
    }

    /**
     * @return the gift in JSONObject format
     */
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

    /**
     * decrement the gift stock
     */
    public void reduceQuantity() {
        this.quantity--;
    }

    public int getQuantity() {
        return quantity;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(final Category category) {
        this.category = category;
    }
}
