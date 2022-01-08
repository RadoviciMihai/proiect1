package data;

import christmas.Gift;
import person.Child;
import person.ChildUpdate;

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
