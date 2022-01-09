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
        this.id = (int) json.get("id");
        this.niceScore = (double) json.get("niceScore");
        List<Category> giftsPreferences = new ArrayList<>();
        JSONArray jsonArray = (JSONArray) json.get("giftsPreferences");
        for (Object o : jsonArray) {
            String categoryString = (String) o;
            Category preference = Category.valueOf(categoryString);
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
