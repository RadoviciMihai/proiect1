package data;

import java.util.List;

public final class DataBase {
    private final int numberOfYears;
    private final double santaBudget;
    private final InitialData initialData;
    private final List<AnnualChange> annualChanges;

    DataBase(final int numberOfYears,
             final double santaBudget,
             final InitialData initialData,
             final List<AnnualChange> annualChanges) {
        this.numberOfYears = numberOfYears;
        this.santaBudget = santaBudget;
        this.initialData = initialData;
        this.annualChanges = annualChanges;
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
