package data;

import christmas.Gift;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import person.Child;
import person.ChildUpdate;

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

        List<Child> children = new ArrayList<>();
        JSONArray jsonArrayChildren = (JSONArray) json.get("children");
        for (Object jsonArrayChild : jsonArrayChildren) {
            children.add(new Child(
                    (JSONObject) jsonArrayChild
            ));
        }

        List<Gift> santaGiftsList = new ArrayList<>();
        JSONArray jsonArrayGifts = (JSONArray) json.get("santaGiftsList");
        for (Object jsonArrayGift : jsonArrayGifts) {
            santaGiftsList.add(new Gift(
                    (JSONObject) jsonArrayGift
            ));
        }

        this.santaGiftsList = santaGiftsList;
        this.children = children;
    }

    public void ageAllChildren() {
        for(Child child : children) {
            child.setAge(child.getAge()+1);
        }
    }

    public List<Child> getChildren() {
        return children;
    }

    public List<Gift> getSantaGiftsList() {
        return santaGiftsList;
    }


}
