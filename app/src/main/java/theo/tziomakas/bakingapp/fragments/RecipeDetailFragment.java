package theo.tziomakas.bakingapp.fragments;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.FragmentManager;

import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import theo.tziomakas.bakingapp.R;
import theo.tziomakas.bakingapp.RecipeDetailActivity;
import theo.tziomakas.bakingapp.adapters.RecipeDetailAdapter;
import theo.tziomakas.bakingapp.model.Ingredients;
import theo.tziomakas.bakingapp.model.Recipe;
import theo.tziomakas.bakingapp.model.Steps;


/**
 * A simple {@link Fragment} subclass.
 */
public class RecipeDetailFragment extends Fragment{

    ArrayList<Recipe> recipeArrayList;
    RecyclerView mRecyclerView;

    RecipeDetailAdapter stepsAdapter;

    List<Ingredients> ingredientsList;
    List<Steps> stepsList;

    TextView textView;
    public RecipeDetailFragment() {
        // Required empty public constructor
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_recipe_detail, container, false);

        Intent intent = getActivity().getIntent();

        recipeArrayList = intent.getParcelableArrayListExtra("recipe");

        ingredientsList = recipeArrayList.get(0).getIngredients();

        stepsList = recipeArrayList.get(0).getSteps();

        textView = view.findViewById(R.id.recipe_detail_text);



        //mIngredientTextView.append("\u2022 " +   "\n");
        ingredientsList.forEach((a) ->
        {
            textView.append("\u2022 "+ a.getIngredient()+"\n");
            textView.append("\t\t\t Quantity: "+a.getQuantity()+"\n");
            textView.append("\t\t\t Measure: "+a.getMeasure()+"\n\n");


        });

        mRecyclerView = view.findViewById(R.id.steps_recycler_view);

        stepsAdapter = new RecipeDetailAdapter((RecipeDetailActivity)getActivity(),stepsList);
        mRecyclerView.setAdapter(stepsAdapter);

        return view;
    }



}
