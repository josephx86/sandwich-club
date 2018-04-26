package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    TextView alsoKnownAsTextView, originTextView, descriptionTextView, ingredientsTextView;
    TextView alsoKnownAsLabel, originLabel, descriptionLabel, ingredientsLabel;
    Sandwich sandwich;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);
        alsoKnownAsTextView = findViewById(R.id.also_known_tv);
        originTextView = findViewById(R.id.origin_tv);
        descriptionTextView = findViewById(R.id.description_tv);
        ingredientsTextView = findViewById(R.id.ingredients_tv);
        alsoKnownAsLabel = findViewById(R.id.textView);
        originLabel = findViewById(R.id.textView2);
        ingredientsLabel = findViewById(R.id.textView3);
        descriptionLabel = findViewById(R.id.textView4);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI();
        try {
            // There might be an exception loading the image
            Picasso.with(this)
                    .load(sandwich.getImage())
                    .into(ingredientsIv);
        } catch (Exception ex) {
            Toast.makeText(this, ex.getLocalizedMessage(), Toast.LENGTH_LONG);
        }

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {
        // So this looks 'hacky' but it works, lol.
        // I didn't want to show the textviews for empty fields (like when place of origin is not available)
        // Setting visibility to GONE will mess up my RelativeLayout. Setting INVISIBLE will create awkward spaces
        // So I decided to set height to 1dp

        if (sandwich == null) {
            closeOnError();
        }

        if ((alsoKnownAsLabel != null) && (alsoKnownAsTextView != null)) {
            StringBuilder buffer = new StringBuilder();
            for (String name : sandwich.getAlsoKnownAs()) {
                buffer.append(name + ", ");
            }
            String otherNames = buffer.toString().trim();

            // Remove trailing comma
            if (otherNames.endsWith(",")) {
                otherNames = otherNames.substring(0, otherNames.lastIndexOf(','));
            }
            if (otherNames.isEmpty()) {
                alsoKnownAsTextView.setHeight(1);
                alsoKnownAsLabel.setHeight(1);
            } else {
                alsoKnownAsTextView.setText(otherNames);
            }
        }

        if ((originTextView != null) && (originLabel != null)) { // Set non-null default values
            String origin = sandwich.getPlaceOfOrigin();
            if (origin.isEmpty()) {
                originTextView.setHeight(1);
                originLabel.setHeight(1);
            } else {
                originTextView.setText(origin);
            }
        }

        if ((ingredientsTextView != null) && (ingredientsLabel != null)) {
            StringBuilder buffer = new StringBuilder();
            for (String ingredient : sandwich.getIngredients()) {
                buffer.append(ingredient + ", ");
            }
            String ingredients = buffer.toString().trim();

            // Remove trailing comma
            if (ingredients.endsWith(",")) {
                ingredients = ingredients.substring(0, ingredients.lastIndexOf(','));
            }
            if (ingredients.isEmpty()) {
                ingredientsLabel.setHeight(1);
                ingredientsTextView.setHeight(1);
            } else {
                ingredientsTextView.setText(ingredients);
            }
        }

        if ((descriptionTextView != null) && (descriptionLabel != null)) {
            String description = sandwich.getDescription();
            if (description.isEmpty()) {
                descriptionLabel.setHeight(1);
                descriptionTextView.setHeight(1);
            } else {
                descriptionTextView.setText(description);
            }
        }
    }
}
