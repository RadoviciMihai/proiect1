package data;

import christmas.Gift;
import common.Constants;
import enums.Category;
import enums.Cities;
import enums.CityStrategyEnum;
import enums.ElvesType;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import person.Child;
import person.ChildUpdate;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

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

        List<AnnualChange> annualChangesAux = new ArrayList<>();
        JSONArray jsonArrayChanges = (JSONArray) json.get("annualChanges");
        for (Object jsonArrayChange : jsonArrayChanges) {
            annualChangesAux.add(new AnnualChange(
                    (JSONObject) jsonArrayChange
            ));
        }

        this.annualChanges = annualChangesAux;
    }

    public JSONObject getOutputChildren(final CityStrategyEnum strategyEnum) {
        double averageScoreSum = 0;
        List<Child> children = new ArrayList<>(initialData.getChildren());
        for (Child child : children) {
            if (child.getAge() < Constants.TEEN_LIMIT) {
                averageScoreSum += child.getAverageScore();
            }
        }
        double budgetUnit = getSantaBudget() / averageScoreSum;

        if (strategyEnum == CityStrategyEnum.NICE_SCORE) {
            children.sort(Comparator.comparing(Child::getAverageScore).reversed());
        }

        if (strategyEnum == CityStrategyEnum.NICE_SCORE_CITY) {
            children.sort(Comparator.comparing(Child::getCityString));
            children.sort(Comparator.comparing(Child::getCityScore).reversed());
        }

        HashMap<Integer, JSONObject> listaCopii = new HashMap<Integer, JSONObject>();
        for (Child child : children) {
            if (child.getAge() < Constants.TEEN_LIMIT) {
                Double assignedBudget = child.getAverageScore() * budgetUnit;
                if (child.getElf() == ElvesType.BLACK) {
                    assignedBudget -= assignedBudget * Constants.ELF_MOD
                            / Constants.ONE_HUNDRED;
                }
                if (child.getElf() == ElvesType.PINK) {
                    assignedBudget += assignedBudget * Constants.ELF_MOD
                            / Constants.ONE_HUNDRED;
                }

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
                for (Category category : child.getGiftsPreferences()) {
                    Gift cheapestGift = getCheapestGift(category);
                    if (cheapestGift != null) {
                        if (assignedBudget
                                >= cheapestGift.getPrice()) {
                            receivedGifts.add(
                                    cheapestGift.getJsonObject());
                            cheapestGift.reduceQuantity();
                            assignedBudget -= cheapestGift.getPrice();
                        }
                    }
                }

                if (receivedGifts.isEmpty()
                        && child.getElf() == ElvesType.YELLOW) {
                    Category category = child.getGiftsPreferences().get(0);
                    Gift cheapestGift = getCheapestGiftYellow(category);
                    if (cheapestGift != null) {
                        receivedGifts.add(
                                cheapestGift.getJsonObject());
                        cheapestGift.reduceQuantity();
                    }
                }

                copilJson.put("receivedGifts", receivedGifts);
                listaCopii.put(child.getId(), copilJson);
            }
        }

        JSONArray outListaCopii = new JSONArray();
        SortedSet<Integer> keys = new TreeSet<>(listaCopii.keySet());
        for (Integer key : keys) {
            JSONObject value = listaCopii.get(key);
            outListaCopii.add(value);
        }
        JSONObject outputChildren = new JSONObject();
        outputChildren.put("children", outListaCopii);
        return outputChildren;
    }

    public JSONObject run() {
        JSONObject result = new JSONObject();
        JSONArray annualChildren = new JSONArray();
        annualChildren.add(getOutputChildren(CityStrategyEnum.ID));
        for (int i = 0; i < numberOfYears; i++) {
            CityStrategyEnum strategyEnum = update(i);
            annualChildren.add(getOutputChildren(strategyEnum));
        }
        result.put("annualChildren", annualChildren);
        return result;
    }

    public Gift getCheapestGift(final Category category) {
        List<Gift> giftList = initialData.getSantaGiftsList();
        double minPrice = Double.MAX_VALUE;
        Gift cheapestGift = null;
        for (Gift gift : giftList) {
            if (gift.getCategory() == category
                    && gift.getPrice() < minPrice
                    && gift.getQuantity() > 0) {
                minPrice = gift.getPrice();
                cheapestGift = gift;
            }
        }
        return cheapestGift;
    }

    private Gift getCheapestGiftYellow(final Category category) {
        List<Gift> giftList = initialData.getSantaGiftsList();
        double minPrice = Double.MAX_VALUE;
        Gift cheapestGift = null;
        for (Gift gift : giftList) {
            if (gift.getCategory() == category
                    && gift.getPrice() < minPrice) {
                minPrice = gift.getPrice();
                cheapestGift = gift;
            }
        }
        if (cheapestGift.getQuantity() == 0) {
            return null;
        }
        return cheapestGift;
    }

    public CityStrategyEnum update(final int i) {
        initialData.ageAllChildren();
        for (Child child : annualChanges.get(i).getNewChildren()) {
            initialData.getChildren().add(child);
        }
        for (ChildUpdate childUpdate
                : annualChanges.get(i).getChildrenUpdates()) {
            if (initialData.getChildById(childUpdate.getId()) != null) {
                initialData.getChildById(
                        childUpdate.getId()).update(childUpdate);
            }
        }
        santaBudget = annualChanges.get(i).getNewSantaBudget();
        for (Gift gift : annualChanges.get(i).getNewGifts()) {
            initialData.getSantaGiftsList().add(gift);
        }
        HashMap<Cities, Double> totalCityScore = new HashMap<>();
        HashMap<Cities, Integer> childrenInCity = new HashMap<>();
        HashMap<Cities, Double> averageCityScore = new HashMap<>();
        for (Child child : initialData.getChildren()) {
            if (child.getAge() < Constants.TEEN_LIMIT) {
                if (totalCityScore.containsKey(child.getCity())) {
                    totalCityScore.put(child.getCity(),
                            totalCityScore.get(child.getCity())
                                    + child.getAverageScore());
                    childrenInCity.put(child.getCity(),
                            childrenInCity.get(child.getCity()) + 1);
                } else {
                    totalCityScore.put(child.getCity(), child.getAverageScore());
                    childrenInCity.put(child.getCity(), 1);
                }
            }
        }
        for (Cities city : totalCityScore.keySet()) {
            averageCityScore.put(city,
                    totalCityScore.get(city) / childrenInCity.get(city));
        }
        for (Child child : initialData.getChildren()) {
            if (child.getAge() < Constants.TEEN_LIMIT) {
                child.setCityScore(averageCityScore.get(child.getCity()));
            }
        }
        return annualChanges.get(i).getStrategy();
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
