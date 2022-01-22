package person;

import common.Constants;
import enums.Category;
import enums.Cities;
import enums.ElvesType;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public final class Child {
    private int id;
    private String lastName;
    private String firstName;
    private int age;
    private Cities city;
    private double niceScore;
    private double niceScoreBonus;
    private List<Category> giftsPreferences;
    private List<Double> listaScoruri;
    private ElvesType elf;

    Child(final int id,
          final String lastName,
          final String firstName,
          final int age,
          final Cities city,
          final double niceScore,
          final List<Category> giftsPreferences,
          final double niceScoreBonus,
          final ElvesType elf) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.age = age;
        this.city = city;
        this.niceScore = niceScore;
        this.giftsPreferences = giftsPreferences;
        this.listaScoruri = new ArrayList<>();
        this.listaScoruri.add(this.niceScore);
        this.niceScoreBonus = niceScoreBonus;
        this.elf = elf;
    }

    public Child(final JSONObject json) {
        this.id = ((Long) json.get("id")).intValue();
        this.lastName = (String) json.get("lastName");
        this.firstName = (String) json.get("firstName");
        this.age = ((Long) json.get("age")).intValue();
        this.city = Cities.retrieveByCities((String) json.get("city"));
        this.niceScore = ((Long) json.get("niceScore")).doubleValue();
        List<Category> giftsPreferencesAux = new ArrayList<>();
        JSONArray jsonArray = (JSONArray) json.get("giftsPreferences");
        for (Object o : jsonArray) {
            String categoryString = (String) o;
            Category preference = Category.retrieveByCategory(categoryString);
            giftsPreferencesAux.add(preference);
        }
        this.giftsPreferences = giftsPreferencesAux;
        this.listaScoruri = new ArrayList<>();
        this.listaScoruri.add(this.niceScore);
        this.niceScoreBonus = ((Long) json.get("niceScoreBonus")).doubleValue();
        this.elf = ElvesType.retrieveByElf((String) json.get("elf"));
    }

    public void addToScoreList(final Double scor) {
        if (scor >= 0) {
            listaScoruri.add(scor);
        }
    }

    public void update(final ChildUpdate childUpdate) {
        addToScoreList(childUpdate.getNiceScore());
        for (Category category : childUpdate.getGiftsPreferences()) {
            giftsPreferences.remove(category);
        }

        int iter = 0;
        for (Category category : childUpdate.getGiftsPreferences()) {
            if (!giftsPreferences.contains(category)) {
                giftsPreferences.add(iter, category);
                iter++;
            }
        }
        elf = childUpdate.getElf();
    }

    public double getInitAverageScore() {

        if (age < Constants.BABY_LIMIT) {
            return Constants.KID_NICE_SCORE;
        }
        if (age < Constants.KID_LIMIT) {
            double total = 0;
            for (Double i : listaScoruri) {
                total += i;
            }
            return total / listaScoruri.size();
        }
        if (age < Constants.TEEN_LIMIT) {
            double total = 0;
            int pondere = 0;
            int n = listaScoruri.size();
            for (Double i : listaScoruri) {
                pondere++;
                total += i * pondere;
            }
            return total / (n * (n + 1) / 2);
        }
        return 0;
    }


    public double getAverageScore() {
        double averageScore = getInitAverageScore();
        averageScore += averageScore * niceScoreBonus / Constants.ONE_HUNDRED;
        if (averageScore > Constants.KID_NICE_SCORE) {
            return Constants.KID_NICE_SCORE;
        }
        return averageScore;
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(final int age) {
        this.age = age;
    }

    public Cities getCity() {
        return city;
    }

    public void setCity(final Cities city) {
        this.city = city;
    }

    public double getNiceScore() {
        return niceScore;
    }

    public JSONArray getNiceScoreHistory() {
        JSONArray niceScoreHistory = new JSONArray();
        for (Double scor : listaScoruri) {
            niceScoreHistory.add(scor);
        }
        return niceScoreHistory;
    }

    public void setNiceScore(final double niceScore) {
        this.niceScore = niceScore;
    }

    public List<Category> getGiftsPreferences() {
        return giftsPreferences;
    }

    public ElvesType getElf() {
        return elf;
    }

    public void setGiftsPreferences(final List<Category> giftsPreferences) {
        this.giftsPreferences = giftsPreferences;
    }

    public JSONArray getGiftsPreferencesJson() {
        JSONArray categoryList = new JSONArray();
        for (Category category : giftsPreferences) {
            categoryList.add(category.getValue());
        }
        return categoryList;
    }
}
