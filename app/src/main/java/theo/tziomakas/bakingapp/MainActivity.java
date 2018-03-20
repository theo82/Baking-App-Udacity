package theo.tziomakas.bakingapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;

import theo.tziomakas.bakingapp.IdlingResource.SimpleIdlingResource;
import theo.tziomakas.bakingapp.adapters.RecipeAdapter;
import theo.tziomakas.bakingapp.fragments.RecipeDetailFragment;
import theo.tziomakas.bakingapp.model.Recipe;

public class MainActivity extends AppCompatActivity implements RecipeAdapter.RecipeAdapterOnClickHandler{

    @Nullable
    private SimpleIdlingResource mIdlingResource;

    /**
     * Only called from test, creates and returns a new {@link SimpleIdlingResource}.
     */
    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new SimpleIdlingResource();
        }
        return mIdlingResource;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setHomeButtonEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setTitle("Baking App");

        // Get the IdlingResource instance
        getIdlingResource();
    }


    @Override
    public void onClick(Recipe clickedItemIndex) {
        Bundle selectedRecipeBundle = new Bundle();
        ArrayList<Recipe> selectedRecipe = new ArrayList<>();
        selectedRecipe.add(clickedItemIndex);
        selectedRecipeBundle.putParcelableArrayList("recipe",selectedRecipe);

        final Intent intent = new Intent(this, RecipeDetailActivity.class);
        intent.putExtras(selectedRecipeBundle);
        startActivity(intent);
    }
}
