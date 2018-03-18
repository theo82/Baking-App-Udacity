package theo.tziomakas.bakingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

import theo.tziomakas.bakingapp.fragments.RecipeDetailFragment;
import theo.tziomakas.bakingapp.model.Recipe;

public class RecipeDetailActivity extends AppCompatActivity {
    private   ArrayList<Recipe> recipe;
    String recipeName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        if(savedInstanceState == null){

            getSupportFragmentManager().beginTransaction()
                 .add(R.id.container,new RecipeDetailFragment()).commit();

        }
    }
}
