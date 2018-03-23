package theo.tziomakas.bakingapp.widget;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import theo.tziomakas.bakingapp.model.Ingredients;
import theo.tziomakas.bakingapp.model.Recipe;

/**
 * Created by theodosiostziomakas on 23/03/2018.
 */

public class UpdateRecipeWidgetService extends IntentService {

    ArrayList<Ingredients> ingredientsArrayList = new ArrayList<>();

    public UpdateRecipeWidgetService() {
        super("UpdateRecipeWidget");
    }

    public static void startBakingService(Context context, ArrayList<Recipe> ingredients) {
        Intent intent = new Intent(context, UpdateRecipeWidgetService.class);
        intent.putExtra("ingredients_list",ingredients);
        context.startService(intent);
    }
    @Override
    protected void onHandleIntent( Intent intent) {
        if (intent != null) {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            Gson gson = new Gson();

            String json = prefs.getString("ingredients", "");
            Type type = new TypeToken<ArrayList<Recipe>>(){}.getType();
            ingredientsArrayList = gson.fromJson(json, type);
            handleActionUpdateRecipeWidget(ingredientsArrayList);

        }
    }

    private void handleActionUpdateRecipeWidget(ArrayList<Ingredients> ingredientsList){
        Intent intent = new Intent("android.appwidget.action.APPWIDGET_UPDATE2");
        intent.setAction("android.appwidget.action.APPWIDGET_UPDATE2");
        intent.putExtra("ingredients_list", ingredientsList);
        sendBroadcast(intent);
    }
}
