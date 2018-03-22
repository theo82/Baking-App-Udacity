package theo.tziomakas.bakingapp.widget;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;

/**
 * Created by theodosiostziomakas on 22/03/2018.
 */

public class RecipeService extends IntentService {

    public static final String ACTION_RECIPE =
            "android.appwidget.action.APPWIDGET_UPDATE";

    public RecipeService() {
        super("RecipeService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            // Read List<Ingredients> ...

        }
    }

    public static void startBakingService(Context context,ArrayList<String> fromActivityIngredientsList) {
        Intent intent = new Intent(context, RecipeService.class);
        intent.putExtra("ingredientsList",fromActivityIngredientsList);
        context.startService(intent);
    }

    private void handleRecipeUpateWidget(ArrayList<String> ingredientsList){
        Intent intent = new Intent(ACTION_RECIPE);
        intent.setAction(ACTION_RECIPE);
        intent.putExtra("ingredientsList",ingredientsList);
        sendBroadcast(intent);

    }
}
