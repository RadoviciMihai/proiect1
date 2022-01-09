package data;

import christmas.Gift;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import person.Child;
import person.ChildUpdate;

import java.util.ArrayList;
import java.util.List;

public final class AnnualChange {
    private final double newSantaBudget;
    private final List<Gift> newGifts;
    private final List<Child> newChildren;
    private final List<ChildUpdate> childrenUpdates;

    AnnualChange(final double newSantaBudget,
                 final List<Gift> newGifts,
                 final List<Child> newChildren,
                 final List<ChildUpdate> childrenUpdates) {
        this.newSantaBudget = newSantaBudget;
        this.newGifts = newGifts;
        this.newChildren = newChildren;
        this.childrenUpdates = childrenUpdates;
    }

    AnnualChange(final JSONObject json) {
        this.newSantaBudget = (double) json.get("newSantaBudget");

        List<Gift> newGifts = new ArrayList<>();
        JSONArray jsonArrayGifts = (JSONArray) json.get("newGifts");
        for (Object jsonArrayGift : jsonArrayGifts) {
            newGifts.add(new Gift(
                    (JSONObject) jsonArrayGift
            ));
        }

        List<Child> newChildren = new ArrayList<>();
        JSONArray jsonArrayChildren = (JSONArray) json.get("newChildren");
        for (Object jsonArrayChild : jsonArrayChildren) {
            newChildren.add(new Child(
                    (JSONObject) jsonArrayChild
            ));
        }

        List<ChildUpdate> childrenUpdates = new ArrayList<>();
        JSONArray jsonArrayUpdates = (JSONArray) json.get("childrenUpdates");
        for (Object jsonArrayUpdate : jsonArrayUpdates) {
            childrenUpdates.add(new ChildUpdate(
                    (JSONObject) jsonArrayUpdate
            ));
        }

        this.newGifts = newGifts;
        this.childrenUpdates = childrenUpdates;
        this.newChildren = newChildren;

    }

    public double getNewSantaBudget() {
        return newSantaBudget;
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
