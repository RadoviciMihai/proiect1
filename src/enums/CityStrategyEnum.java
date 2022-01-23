package enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum CityStrategyEnum {
    @JsonProperty("niceScoreCity")
    NICE_SCORE_CITY("niceScoreCity"),

    @JsonProperty("id")
    ID("id"),

    @JsonProperty("niceScore")
    NICE_SCORE("niceScore");

    private String value;

    CityStrategyEnum(final String value) {
        this.value = value;
    }

    /**
     * @param strategy
     * @return strategy enum from string
     */
    public static CityStrategyEnum retrieveByStrategy(final String strategy) {
        return switch (strategy) {
            case "niceScoreCity" -> CityStrategyEnum.NICE_SCORE_CITY;
            case "id" -> CityStrategyEnum.ID;
            case "niceScore" -> CityStrategyEnum.NICE_SCORE;
            default -> null;
        };
    }
}
