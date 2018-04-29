package com.udacity.sandwichclub.model;

import java.util.ArrayList;
import java.util.List;

public class Sandwich {

    private String mainName;
    private List<String> alsoKnownAs = null;
    private String placeOfOrigin;
    private String description;
    private String image;
    private List<String> ingredients = null;

    public static final String NOT_AVAILABLE = "Not available";

    /**
     * No args constructor for use in serialization
     */
    public Sandwich() {

    }

    public Sandwich(String mainName, List<String> alsoKnownAs, String placeOfOrigin, String description, String image, List<String> ingredients) {
        this.mainName = mainName;
        this.alsoKnownAs = alsoKnownAs;
        this.placeOfOrigin = placeOfOrigin;
        this.description = description;
        this.image = image;
        this.ingredients = ingredients;
    }

    public String getMainName() {
        if ((mainName == null) || (mainName.isEmpty())) {
            mainName = NOT_AVAILABLE;
        }
        return mainName;
    }

    public void setMainName(String mainName) {
        this.mainName = mainName;
    }

    public List<String> getAlsoKnownAs() {
        if (alsoKnownAs == null) {
            alsoKnownAs = new ArrayList<>();
        }
        return alsoKnownAs;
    }

    public void setAlsoKnownAs(List<String> alsoKnownAs) {
        this.alsoKnownAs = alsoKnownAs;
    }

    public String getPlaceOfOrigin() {
        if ((placeOfOrigin == null) || placeOfOrigin.isEmpty()) {
            placeOfOrigin = NOT_AVAILABLE;
        }
        return placeOfOrigin;
    }

    public void setPlaceOfOrigin(String placeOfOrigin) {
        this.placeOfOrigin = placeOfOrigin;
    }

    public String getDescription() {
        if ((description ==null)||description.isEmpty()) {
            description = NOT_AVAILABLE;
        }
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<String> getIngredients() {
        if (ingredients == null){
            ingredients = new ArrayList<>();
        }
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }
}
