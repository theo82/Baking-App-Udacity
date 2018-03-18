package theo.tziomakas.bakingapp.network;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import java.util.List;

import theo.tziomakas.bakingapp.model.Recipe;

/**
 * Created by theodosiostziomakas on 15/03/2018.
 */

public class RecipeLoader extends AsyncTaskLoader<List<Recipe>> {

    private static final String LOG_TAG = RecipeLoader.class.getName();

    String url;

    public RecipeLoader(Context context,String url) {
        super(context);
        this.url = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Recipe> loadInBackground() {

        Log.v(LOG_TAG,"RecipeLoader loadInBackground");

        if(url == null){
            return null;
        }

        List<Recipe> result = NetworkUtils.fetchMoviesData(url);

        return result;
    }
}
