package theo.tziomakas.bakingapp.widget;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import theo.tziomakas.bakingapp.R;
import theo.tziomakas.bakingapp.model.Ingredients;

/**
 * Created by theodosiostziomakas on 22/03/2018.
 */


public class GridWidgetService extends RemoteViewsService{
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new IngredientsListRemoteViewFactory(this.getApplicationContext());
    }

}

class IngredientsListRemoteViewFactory implements RemoteViewsService.RemoteViewsFactory{

    private static final String TAG = "IngredientsListRemoteViewFactory";

    ArrayList<Ingredients> ingredientsArrayList = new ArrayList<>();
    Context mContext;

    public IngredientsListRemoteViewFactory(Context applicationContext){
        mContext = applicationContext;
    }

    private void readIngredients(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext.getApplicationContext());
        Gson gson = new Gson();

        String json = prefs.getString("ingredients", "");
        Type type = new TypeToken<ArrayList<Ingredients>>(){}.getType();
        ingredientsArrayList = gson.fromJson(json, type);
    }

    @Override
    public void onCreate() {
        readIngredients();
    }

    @Override
    public void onDataSetChanged() {
        readIngredients();

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return ingredientsArrayList.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.widget_grid_view_item);

        views.setTextViewText(R.id.widget_grid_view_item, "\u2022 " + ingredientsArrayList.get(position).getIngredient()
                + "\n" + ingredientsArrayList.get(position).getQuantity()
                + "\n" + ingredientsArrayList.get(position).getMeasure());

        Intent fillInIntent = new Intent();
        views.setOnClickFillInIntent(R.id.widget_grid_view_item, fillInIntent);

        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
