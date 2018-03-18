package theo.tziomakas.bakingapp.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import theo.tziomakas.bakingapp.MyDividerItemDecoration;
import theo.tziomakas.bakingapp.R;
import theo.tziomakas.bakingapp.RecipeDetailActivity;
import theo.tziomakas.bakingapp.adapters.RecipeAdapter;
import theo.tziomakas.bakingapp.model.Ingredients;
import theo.tziomakas.bakingapp.model.Recipe;
import theo.tziomakas.bakingapp.network.RecipeLoader;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainActivityFragment extends Fragment
 implements LoaderManager.LoaderCallbacks<Object>, RecipeAdapter.RecipeAdapterOnClickHandler{

    public static final String LOG_TAG = "MainActivityFragment";
    public static final int RECIPE_LOADER_ID = 0;
    private String recipeUrl;

    private List<Recipe> mRecipeList;
    private RecyclerView mRecyclerView;
    private RecipeAdapter mRecipeAdapter;

    public MainActivityFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.main_fragment, container, false);
        getLoaderManager().initLoader(RECIPE_LOADER_ID,null,this);

        mRecipeList = new ArrayList<>();

        mRecyclerView = view.findViewById(R.id.recipe_recycler_view);

        MyDividerItemDecoration myDividerItemDecoration = new MyDividerItemDecoration(getActivity(),RecyclerView.VERTICAL,16);


        mRecyclerView.addItemDecoration(myDividerItemDecoration);
        mRecyclerView.setHasFixedSize(true);

        mRecipeAdapter = new RecipeAdapter(getActivity(),mRecipeList,  this);
        mRecyclerView.setAdapter(mRecipeAdapter);


        return view;
    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {

        if(id == RECIPE_LOADER_ID){
            recipeUrl = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";

            return new RecipeLoader(getActivity(),recipeUrl);

        }

        return null;
    }

    @Override
    public void onLoadFinished(Loader<Object> loader, Object data) {
        int id = loader.getId();

        if(id == RECIPE_LOADER_ID){
            mRecipeList = (ArrayList<Recipe>)data;
            if(mRecipeList != null && !mRecipeList.isEmpty()){
                mRecipeAdapter.setRecipeData(mRecipeList);
            }else{
                Toast.makeText(getActivity(),"No data retrieved from the server",Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<Object> loader) {

    }

    @Override
    public void onClick(Recipe clickedItemIndex) {
        Bundle selectedRecipeBundle = new Bundle();
        ArrayList<Recipe> selectedRecipe = new ArrayList<>();
        selectedRecipe.add(clickedItemIndex);
        selectedRecipeBundle.putParcelableArrayList("recipe",selectedRecipe);

        final Intent intent = new Intent(getActivity(), RecipeDetailActivity.class);
        intent.putExtras(selectedRecipeBundle);
        startActivity(intent);

    }
}
