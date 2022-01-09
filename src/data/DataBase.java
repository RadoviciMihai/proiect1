package data;

import christmas.Gift;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
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

    public DataBase(final JSONObject json){
        this.numberOfYears = (int) json.get("numberOfYears");
        this.santaBudget = (double) json.get("santaBudget");
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
