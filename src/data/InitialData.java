package data;

import christmas.Gift;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import person.Child;

import java.util.ArrayList;
import java.util.List;

final class InitialData {
    private final List<Child> children;
    private final List<Gift> santaGiftsList;

    InitialData(final List<Child> children,
                final List<Gift> santaGiftsList) {
        this.children = children;
        this.santaGiftsList = santaGiftsList;
    }

    InitialData(final JSONObject json) {

        List<Child> childrenAux = new ArrayList<>();
        JSONArray jsonArrayChildren = (JSONArray) json.get("children");
        for (Object jsonArrayChild : jsonArrayChildren) {
            childrenAux.add(new Child(
                    (JSONObject) jsonArrayChild
            ));
        }

        List<Gift> santaGiftsListAux = new ArrayList<>();
        JSONArray jsonArrayGifts = (JSONArray) json.get("santaGiftsList");
        for (Object jsonArrayGift : jsonArrayGifts) {
            santaGiftsListAux.add(new Gift(
                    (JSONObject) jsonArrayGift
            ));
        }

        this.santaGiftsList = santaGiftsListAux;
        this.children = childrenAux;
    }

    public void ageAllChildren() {
        for (Child child : children) {
            child.setAge(child.getAge() + 1);
        }
    }

    public Child getChildById(final int id) {
        for (Child child : getChildren()) {
            if (child.getId() == id) {
                return child;
            }
        }
        return null;
    }

    public List<Child> getChildren() {
        return children;
    }

    public List<Gift> getSantaGiftsList() {
        return santaGiftsList;
    }


}
