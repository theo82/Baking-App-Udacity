package theo.tziomakas.bakingapp;

import android.content.Intent;
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

public class RecipeDetailActivity extends AppCompatActivity implements RecipeDetailAdapter.ListItemClickListener{

    static String STACK_RECIPE_STEP_DETAIL="STACK_RECIPE_STEP_DETAIL";

    private ArrayList<Steps> steps;


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
    public void onListItemClick(Steps steps) {

        final RecipeStepDetailFragment fragment = new RecipeStepDetailFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        //Toast.makeText(getActivity(),"clicked: " + clickedItemIndex,Toast.LENGTH_SHORT).show();
        Bundle selectedStepBundle = new Bundle();
        ArrayList<Steps> selectedStep = new ArrayList<>();
        selectedStep.add(steps);
        selectedStepBundle.putParcelable("steps",steps);
        fragment.setArguments(selectedStepBundle);

        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment).addToBackStack("STACK_RECIPE_STEP_DETAIL")
                .commit();
    }
}
