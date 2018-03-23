package theo.tziomakas.bakingapp.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import theo.tziomakas.bakingapp.R;
import theo.tziomakas.bakingapp.model.Ingredients;
import theo.tziomakas.bakingapp.model.Recipe;

/**
 * Created by theodosiostziomakas on 15/03/2018.
 */


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import theo.tziomakas.bakingapp.R;
import theo.tziomakas.bakingapp.model.Recipe;

/**
 * Created by theodosiostziomakas on 15/03/2018.
 */

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {
    private static final String LOG_TAG = RecipeAdapter.class.getSimpleName() ;
    private Context context;
    private List<Recipe> recipeList;

    final private RecipeAdapterOnClickHandler mClickHandler;

    public interface RecipeAdapterOnClickHandler{
        void onClick(Recipe clickedItemIndex);
    }

    public RecipeAdapter(Context context,RecipeAdapterOnClickHandler mClickHandler, List<Recipe> recipeList) {
        this.context = context;
        this.recipeList = recipeList;
        this.mClickHandler = mClickHandler;
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.recipe_row;
        LayoutInflater inflater = LayoutInflater.from(context);

        boolean shouldAttactToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem,parent,shouldAttactToParentImmediately);

        RecipeViewHolder moviesViewHolder = new RecipeViewHolder(view);

        return moviesViewHolder;
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {

        Recipe recipe = recipeList.get(position);
        holder.mRecipeName.setText(recipe.getRecipeName());
        if(recipe.getImage() != null){
            Uri builtUri = Uri.parse(recipe.getImage()).buildUpon().build();
            Picasso.with(context)
                    .load(builtUri)
                    .placeholder(R.drawable.ic_broken_image_black_24dp)
                    .into(holder.mImageView);
        }
        Log.v(LOG_TAG,recipe.getRecipeName());

    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    public void setRecipeData(List<Recipe> recipeList){
        this.recipeList = recipeList;
        notifyDataSetChanged();
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView mRecipeName;
        ImageView mImageView;

        public RecipeViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mRecipeName = itemView.findViewById(R.id.recipe_name_text_view);
            mImageView = itemView.findViewById(R.id.recipeImage);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            mClickHandler.onClick(recipeList.get(adapterPosition));

        }
    }
}

