package person;

import enums.Category;

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
