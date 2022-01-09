package enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum Category {

    @JsonProperty("Board Games")
    BOARD_GAMES("Board Games"),

    @JsonProperty("Books")
    BOOKS("Books"),

    @JsonProperty("Clothes")
    CLOTHES("Clothes"),

    @JsonProperty("Sweets")
    SWEETS("Sweets"),

    @JsonProperty("Technology")
    TECHNOLOGY("Technology"),

    @JsonProperty("Toys")
    TOYS("Toys");

    private final String value;

    Category(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static Category retrieveByCategory(String str) {
        return switch (str) {
            case "Board Games" -> Category.BOARD_GAMES;
            case "Books" -> Category.BOOKS;
            case "Clothes" -> Category.CLOTHES;
            case "Sweets" -> Category.SWEETS;
            case "Technology" -> Category.TECHNOLOGY;
            case "Toys" -> Category.TOYS;
            default -> null;
        };
    }

}
