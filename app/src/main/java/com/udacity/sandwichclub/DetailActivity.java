package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @BindView(R.id.also_known_tv)
    TextView alsoKnownAsTextView;

    @BindView(R.id.origin_tv)
    TextView originTextView;

    @BindView(R.id.description_tv)
    TextView descriptionTextView;

    @BindView(R.id.ingredients_tv)
    TextView ingredientsTextView;

    Sandwich sandwich;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);
        ButterKnife.bind(this);

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
        if (sandwich == null) {
            closeOnError();
        }

        if (alsoKnownAsTextView != null) {
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
                otherNames = Sandwich.NOT_AVAILABLE;
            }
            alsoKnownAsTextView.setText(otherNames);
        }

        if (originTextView != null) {
            String origin = sandwich.getPlaceOfOrigin().trim();
            if (origin.isEmpty()) {
                origin = Sandwich.NOT_AVAILABLE;
            }
            originTextView.setText(origin);
        }

        if (ingredientsTextView != null) {
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
                ingredients = Sandwich.NOT_AVAILABLE;
            }
            ingredientsTextView.setText(ingredients);
        }

        if (descriptionTextView != null) {
            String description = sandwich.getDescription();
            if (description.isEmpty()) {
                description = Sandwich.NOT_AVAILABLE;
            }
            descriptionTextView.setText(description);
        }
    }
}
