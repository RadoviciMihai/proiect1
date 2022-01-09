package person;

import enums.Category;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public final class Child {
    private int id;
    private String lastName;
    private int age;
    private String city;
    private double niceScore;
    private List<Category> giftsPreferences;

    Child(final int id,
          final String lastName,
          final int age,
          final String city,
          final double niceScore,
          final List<Category> giftsPreferences) {
        this.id = id;
        this.lastName = lastName;
        this.age = age;
        this.city = city;
        this.niceScore = niceScore;
        this.giftsPreferences = giftsPreferences;
    }

    public Child(final JSONObject json) {
        this.id = ((Long) json.get("id")).intValue();
        this.lastName = (String) json.get("lastName");
        this.age = ((Long) json.get("age")).intValue();
        this.city = (String) json.get("city");
        this.niceScore = ((Long) json.get("niceScore")).doubleValue();
        List<Category> giftsPreferences = new ArrayList<>();
        JSONArray jsonArray = (JSONArray) json.get("giftsPreferences");
        for (Object o : jsonArray) {
            String categoryString = (String) o;
            Category preference = Category.retrieveByCategory(categoryString);
            giftsPreferences.add(preference);
        }
    }

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(final int age) {
        this.age = age;
    }

    public String getCity() {
        return city;
    }

    public void setCity(final String city) {
        this.city = city;
    }

    public double getNiceScore() {
        return niceScore;
    }

    public void setNiceScore(final double niceScore) {
        this.niceScore = niceScore;
    }

    public List<Category> getGiftsPreferences() {
        return giftsPreferences;
    }

    public void setGiftsPreferences(final List<Category> giftsPreferences) {
        this.giftsPreferences = giftsPreferences;
    }
}
