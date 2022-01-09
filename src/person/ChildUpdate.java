package person;

import enums.Category;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public final class ChildUpdate {
    private int id;
    private double niceScore;
    private List<Category> giftsPreferences;

    ChildUpdate(final int id,
                final double niceScore,
                final List<Category> giftsPreferences) {
        this.id = id;
        this.niceScore = niceScore;
        this.giftsPreferences = giftsPreferences;
    }

    public ChildUpdate(final JSONObject json) {
        this.id = ((Long) json.get("id")).intValue();
        if (json.get("niceScore") != null)
            this.niceScore = ((Long) json.get("niceScore")).doubleValue();
        else
            this.niceScore = 0;
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
