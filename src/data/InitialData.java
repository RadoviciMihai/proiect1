package data;

import christmas.Gift;
import person.Child;

import java.util.List;

final class InitialData {
    private final List<Child> children;
    private final List<Gift> santaGiftsList;

    InitialData(final List<Child> children,
                final List<Gift> santaGiftsList) {
        this.children = children;
        this.santaGiftsList = santaGiftsList;
    }

    public List<Child> getChildren() {
        return children;
    }

    public List<Gift> getSantaGiftsList() {
        return santaGiftsList;
    }



}
