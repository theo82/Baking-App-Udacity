package theo.tziomakas.bakingapp;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import theo.tziomakas.bakingapp.adapters.RecipeDetailAdapter;
import theo.tziomakas.bakingapp.fragments.RecipeDetailFragment;
import theo.tziomakas.bakingapp.fragments.RecipeStepDetailFragment;
import theo.tziomakas.bakingapp.model.Recipe;
import theo.tziomakas.bakingapp.model.Steps;

public class RecipeDetailActivity extends AppCompatActivity implements RecipeDetailAdapter.ListItemClickListener,RecipeStepDetailFragment.ListItemClickListener{

    public static String SELECTED_STEPS="Selected_Steps";
    public static String SELECTED_INDEX="Selected_Index";
    static String STACK_RECIPE_DETAIL="STACK_RECIPE_DETAIL";
    public static String STACK_RECIPE_STEP_DETAIL="STACK_RECIPE_STEP_DETAIL";

    Toolbar myToolbar;
    private   ArrayList<Recipe> recipe;
    String recipeName;

    //private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        if(savedInstanceState == null){

            Bundle b = getIntent().getExtras();

            recipe = new ArrayList<>();
            recipe = b.getParcelableArrayList("recipe");
            recipeName = recipe.get(0).getRecipeName();

            final RecipeDetailFragment recipeDetailFragment = new RecipeDetailFragment();
            recipeDetailFragment.setArguments(b);
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container,recipeDetailFragment)
                    .commit();


            if(findViewById(R.id.recipe_detail_layout) != null){

                //mTwoPane = true;
                final RecipeStepDetailFragment recipeStepDetailFragmentTablet = new RecipeStepDetailFragment();
                recipeStepDetailFragmentTablet.setArguments(b);
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.recipe_step_detail_container,recipeStepDetailFragmentTablet)
                        .commit();


            }

            myToolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(myToolbar);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(recipeName);

        }else{

            //mTwoPane = false;
            recipeName = savedInstanceState.getString("recipeName");

            myToolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(myToolbar);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(recipeName);
        }

    }

    @Override
    public void onListItemClick(List<Steps> stepsOut, int clickedItemIndex) {

        final RecipeStepDetailFragment fragment = new RecipeStepDetailFragment();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        Bundle stepBundle = new Bundle();
        stepBundle.putParcelableArrayList(SELECTED_STEPS,(ArrayList<Steps>) stepsOut);
        stepBundle.putInt(SELECTED_INDEX,clickedItemIndex);
        fragment.setArguments(stepBundle);


        if(findViewById(R.id.recipe_detail_layout) != null){
            ft.replace(R.id.recipe_step_detail_container,fragment);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.addToBackStack(STACK_RECIPE_STEP_DETAIL);
            ft.commit();

        }else {
            ft.replace(R.id.container, fragment);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.addToBackStack(STACK_RECIPE_STEP_DETAIL);
            ft.commit();
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("recipeName",recipeName);
    }


}
