package theo.tziomakas.bakingapp;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

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
    public static String STACK_RECIPE_STEP_DETAIL="STACK_RECIPE_STEP_DETAIL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        if(savedInstanceState == null){

            getSupportFragmentManager().beginTransaction()
                 .add(R.id.container,new RecipeDetailFragment()).commit();


        }
    }

    @Override
    public void onListItemClick(List<Steps> stepsOut, int clickedItemIndex) {

        final RecipeStepDetailFragment fragment = new RecipeStepDetailFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();

        Bundle stepBundle = new Bundle();
        stepBundle.putParcelableArrayList(SELECTED_STEPS,(ArrayList<Steps>) stepsOut);
        stepBundle.putInt(SELECTED_INDEX,clickedItemIndex);
        fragment.setArguments(stepBundle);

        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment).addToBackStack(fragment.getClass().getName())
                .commit();
    }


}
