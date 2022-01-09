package data;

import christmas.Gift;
import enums.Category;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import person.Child;
import person.ChildUpdate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public final class DataBase {
    private int numberOfYears;
    private double santaBudget;
    private InitialData initialData;
    private List<AnnualChange> annualChanges;

    DataBase(final int numberOfYears,
             final double santaBudget,
             final InitialData initialData,
             final List<AnnualChange> annualChanges) {
        this.numberOfYears = numberOfYears;
        this.santaBudget = santaBudget;
        this.initialData = initialData;
        this.annualChanges = annualChanges;
    }

    public DataBase(final JSONObject json) {
        this.numberOfYears = ((Long) json.get("numberOfYears")).intValue();
        this.santaBudget = ((Long) json.get("santaBudget")).doubleValue();
        this.initialData = new InitialData(
                (JSONObject) json.get("initialData")
        );

        List<AnnualChange> annualChanges = new ArrayList<>();
        JSONArray jsonArrayChanges = (JSONArray) json.get("annualChanges");
        for (Object jsonArrayChange : jsonArrayChanges) {
            annualChanges.add(new AnnualChange(
                    (JSONObject) jsonArrayChange
            ));
        }

        this.annualChanges = annualChanges;
    }

    public JSONObject getOutputChildren() {
        double averageScoreSum = 0;
        List<Child> children = initialData.getChildren();
        for (Child child : children) {
            if (child.getAge() < 19) {
                averageScoreSum += child.getAverageScore();
            }
        }
        double budgetUnit = getSantaBudget() / averageScoreSum;
        JSONArray listaCopii = new JSONArray();
        for (Child child : children) {
            if (child.getAge() < 19) {
                Double assignedBudget = budgetUnit * child.getAverageScore();
                JSONObject copilJson = new JSONObject();
                copilJson.put("id", child.getId());
                copilJson.put("lastName", child.getLastName());
                copilJson.put("firstName", child.getFirstName());
                copilJson.put("city", child.getCity().getValue());
                copilJson.put("age", child.getAge());
                copilJson.put("giftsPreferences",
                        child.getGiftsPreferencesJson());
                copilJson.put("averageScore", child.getAverageScore());
                copilJson.put("niceScoreHistory",
                        child.getNiceScoreHistory());
                copilJson.put("assignedBudget", assignedBudget);

                JSONArray receivedGifts = new JSONArray();
                for(Category category : child.getGiftsPreferences()) {
                    if (getCheapestGift(category) != null) {
                        if (assignedBudget >= getCheapestGift(category).getPrice()) {
                            receivedGifts.add(
                                    getCheapestGift(
                                            category).getJsonObject());
                            assignedBudget -= getCheapestGift(category).getPrice();
                        }
                    }
                }
                //System.out.println(child.getId() + " | " + receivedGifts.toJSONString());
                copilJson.put("receivedGifts", receivedGifts);
                listaCopii.add(copilJson);
            }
        }
        JSONObject outputChildren = new JSONObject();
        outputChildren.put("children", listaCopii);
        return outputChildren;
    }

    public JSONObject run(){
        JSONObject result = new JSONObject();
        JSONArray annualChildren = new JSONArray();
        annualChildren.add(getOutputChildren());
        for (int i=0; i < numberOfYears; i++) {
            update(i);
            annualChildren.add(getOutputChildren());
        }
        result.put("annualChildren", annualChildren);
        return result;
    }

    public Gift getCheapestGift(Category category) {
        List<Gift> giftList = initialData.getSantaGiftsList();
        double minPrice = Double.MAX_VALUE;
        Gift cheapestGift = null;
        for (Gift gift : giftList) {
            if (gift.getCategory() == category &&
                    gift.getPrice() < minPrice) {
                minPrice = gift.getPrice();
                cheapestGift = gift;
            }
        }
        return cheapestGift;
    }

    public void update(int i) {
        initialData.ageAllChildren();
        for(Child child : annualChanges.get(i).getNewChildren()) {
            initialData.getChildren().add(child);
        }
        for(ChildUpdate childUpdate :
                annualChanges.get(i).getChildrenUpdates()) {
            if (initialData.getChildById(childUpdate.getId()) != null)
                initialData.getChildById(childUpdate.getId()).update(childUpdate);
        }
        santaBudget = annualChanges.get(i).getNewSantaBudget();
        for(Gift gift : annualChanges.get(i).getNewGifts()) {
            initialData.getSantaGiftsList().add(gift);
        }
    }

    public int getNumberOfYears() {
        return numberOfYears;
    }

    public double getSantaBudget() {
        return santaBudget;
    }

    public InitialData getInitialData() {
        return initialData;
    }

    public List<AnnualChange> getAnnualChanges() {
        return annualChanges;
    }
}
