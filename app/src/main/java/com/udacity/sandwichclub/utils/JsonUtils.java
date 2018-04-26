package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        JSONObject jsonObject = null;

        // Create an object to read from.
        // This throw an error if JSON is invalid, so this will also be a valid-JSON check :)
        try {
            jsonObject = new JSONObject(json);
        } catch (JSONException e) {
            return null;
        }

        // For some reason, if our object is null, we cannot proceed.
        if (jsonObject == null) {
            return null;
        }

        Sandwich sandwich = new Sandwich();
        try {
            // Get the name
            JSONObject sandwichNameObject = jsonObject.getJSONObject("name");
            String mainName = sandwichNameObject.getString("mainName").trim();
            sandwich.setMainName(mainName);

            // Get other names
            List<String> otherNames = new ArrayList<>();
            JSONArray otherNamesArray = sandwichNameObject.getJSONArray("alsoKnownAs");
            if (otherNamesArray.length() > 0) {
                for (int i = 0; i < otherNamesArray.length(); i++) {
                    String name = (String) otherNamesArray.get(i);
                    if ((name != null) && (!name.isEmpty())) {
                        otherNames.add(name);
                    }
                }
            }
            sandwich.setAlsoKnownAs(otherNames);

            // Get place of origin
            String origin = jsonObject.getString("placeOfOrigin").trim();
            sandwich.setPlaceOfOrigin(origin);

            // Get description
            String description = jsonObject.getString("description").trim();
            sandwich.setDescription(description);

            // Get the ingredients
            List<String> ingredients = new ArrayList<>();
            JSONArray ingredientsArray = jsonObject.getJSONArray("ingredients");
            if (ingredientsArray.length() > 0) {
                for (int i = 0; i < ingredientsArray.length(); i++) {
                    String entry = (String) ingredientsArray.get(i);
                    if ((entry != null) && (!entry.isEmpty())) {
                        ingredients.add(entry);
                    }
                }
            }
            sandwich.setIngredients(ingredients);

            // Set the image url
            String imageUrl = jsonObject.getString("image");
            sandwich.setImage(imageUrl);
        } catch (JSONException jex) {
            return null;
        }
        return sandwich;
    }
}
