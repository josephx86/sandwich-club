package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        try {
            // Create an object to read from.
            // This throw an error if JSON is invalid, so this will also be a valid-JSON check :)
            JSONObject jsonObject = new JSONObject(json);

            // Get the name
            JSONObject sandwichNameObject = jsonObject.getJSONObject("name");
            String mainName = sandwichNameObject.getString("mainName").trim();

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

            // Get place of origin
            String origin = jsonObject.getString("placeOfOrigin").trim();

            // Get description
            String description = jsonObject.getString("description").trim();

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

            // Set the image url
            String imageUrl = jsonObject.getString("image");

            return new Sandwich(mainName, otherNames, origin, description, imageUrl, ingredients);
        } catch (JSONException jex) {
            return null;
        }
    }
}
