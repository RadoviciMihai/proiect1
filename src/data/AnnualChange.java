package data;

import christmas.Gift;

import java.util.ArrayList;
import java.util.List;

import enums.CityStrategyEnum;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import person.Child;
import person.ChildUpdate;

public final class AnnualChange {
    private final double newSantaBudget;
    private final List<Gift> newGifts;
    private final List<Child> newChildren;
    private final List<ChildUpdate> childrenUpdates;
    private final CityStrategyEnum strategy;

    AnnualChange(final double newSantaBudget,
                 final List<Gift> newGifts,
                 final List<Child> newChildren,
                 final List<ChildUpdate> childrenUpdates,
                 final CityStrategyEnum strategy) {
        this.newSantaBudget = newSantaBudget;
        this.newGifts = newGifts;
        this.newChildren = newChildren;
        this.childrenUpdates = childrenUpdates;
        this.strategy = strategy;
    }

    AnnualChange(final JSONObject json) {
        this.newSantaBudget = ((Long) json.get("newSantaBudget")).doubleValue();
        List<Gift> newGiftsAux = new ArrayList<>();
        JSONArray jsonArrayGifts = (JSONArray) json.get("newGifts");
        for (Object jsonArrayGift : jsonArrayGifts) {
            newGiftsAux.add(new Gift(
                    (JSONObject) jsonArrayGift
            ));
        }

        List<Child> newChildrenAux = new ArrayList<>();
        JSONArray jsonArrayChildren = (JSONArray) json.get("newChildren");
        for (Object jsonArrayChild : jsonArrayChildren) {
            newChildrenAux.add(new Child(
                    (JSONObject) jsonArrayChild
            ));
        }

        List<ChildUpdate> childrenUpdatesAux = new ArrayList<>();
        JSONArray jsonArrayUpdates = (JSONArray) json.get("childrenUpdates");
        for (Object jsonArrayUpdate : jsonArrayUpdates) {
            childrenUpdatesAux.add(new ChildUpdate(
                    (JSONObject) jsonArrayUpdate
            ));
        }

        this.newGifts = newGiftsAux;
        this.childrenUpdates = childrenUpdatesAux;
        this.newChildren = newChildrenAux;
        this.strategy = CityStrategyEnum.retrieveByStrategy((String) json.get("strategy"));
    }

    public double getNewSantaBudget() {
        return newSantaBudget;
    }

    public CityStrategyEnum getStrategy() {
        return strategy;
    }

    public List<Gift> getNewGifts() {
        return newGifts;
    }

    public List<Child> getNewChildren() {
        return newChildren;
    }

    public List<ChildUpdate> getChildrenUpdates() {
        return childrenUpdates;
    }

}
