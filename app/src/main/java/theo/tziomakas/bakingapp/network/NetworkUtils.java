package theo.tziomakas.bakingapp.network;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import theo.tziomakas.bakingapp.model.Ingredients;
import theo.tziomakas.bakingapp.model.Recipe;
import theo.tziomakas.bakingapp.model.Steps;

/**
 * Created by theodosiostziomakas on 15/03/2018.
 */

public class NetworkUtils {
    private static final String LOG_TAG = NetworkUtils.class.getSimpleName();

    public NetworkUtils(){

    }

    /**
     * Returns new URL object from the given string URL.
     */
    private static URL createURL(String stringURL){

        URL url = null;

        try {
            url = new URL(stringURL);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.
                inputStream.close();
            }
        }
        //Log.v(LOG_TAG,jsonResponse);
        return jsonResponse;
    }


    private static String readFromStream(InputStream inputStream) throws IOException{
        StringBuilder output = new StringBuilder();

        if(inputStream != null){
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();

            while(line != null){
                output.append(line);
                line = reader.readLine();
            }
        }

        return output.toString();
    }

    private static List<Recipe> getRecipeFromJson(String recipeJson){
        if(TextUtils.isEmpty(recipeJson)){
            return null;
        }

        List<Recipe> recipeList = new ArrayList<>();


        int recipeId;
        String recipeName;

        double quantity;
        String measure;
        String ingredient;

        int stepId;
        String stepShortDescription;

        String stepDescription;
        String videoUrl;
        String thumbnailUrl;

        int servings;
        String image;

        try {

            JSONArray recipeJsonArray = new JSONArray(recipeJson);

            for(int i = 0; i<recipeJsonArray.length(); i++){
                Log.v(LOG_TAG,"name array");

                List<Ingredients> ingredientsList = new ArrayList<>();

                JSONObject recipeJsonObject = recipeJsonArray.getJSONObject(i);

                recipeId  = Integer.parseInt(recipeJsonObject.getString("id"));
                recipeName = recipeJsonObject.getString("name");

                Log.v(LOG_TAG, String.valueOf(recipeId));
                Log.v(LOG_TAG,recipeName);

                JSONArray ingredientsJsonArray = recipeJsonObject.getJSONArray("ingredients");

                for(int k = 0; k<ingredientsJsonArray.length(); k ++){
                    Log.v(LOG_TAG,"ingredients array");

                    JSONObject ingredientsObject = ingredientsJsonArray.getJSONObject(k);

                    quantity = Double.parseDouble(ingredientsObject.getString("quantity"));
                    measure = ingredientsObject.getString("measure");
                    ingredient = ingredientsObject.getString("ingredient");

                    Log.v(LOG_TAG, String.valueOf(quantity));
                    Log.v(LOG_TAG,measure);
                    Log.v(LOG_TAG,ingredient);

                    Ingredients ingredients = new Ingredients(quantity,measure,ingredient);

                    ingredientsList.add(ingredients);

                }

                JSONArray stepsJsonArray = recipeJsonObject.getJSONArray("steps");
                ArrayList<Steps> stepsList = new ArrayList<>();


                for(int j = 0; j<stepsJsonArray.length(); j++){

                    Log.v(LOG_TAG,"steps array");

                    JSONObject stepsObject = stepsJsonArray.getJSONObject(j);


                    stepId = Integer.parseInt(stepsObject.getString("id"));
                    stepShortDescription = stepsObject.getString("shortDescription");
                    stepDescription = stepsObject.getString("description");
                    videoUrl = stepsObject.getString("videoURL");
                    thumbnailUrl = stepsObject.getString("thumbnailURL");

                    Log.v(LOG_TAG, String.valueOf(stepId));
                    Log.v(LOG_TAG, stepShortDescription);
                    Log.v(LOG_TAG,stepDescription);
                    Log.v(LOG_TAG, videoUrl);
                    Log.v(LOG_TAG,thumbnailUrl);

                    Steps steps = new Steps(stepId,stepShortDescription,stepDescription,videoUrl,thumbnailUrl);

                    stepsList.add(steps);

                }

                servings = Integer.parseInt(recipeJsonObject.getString("servings"));
                image = recipeJsonObject.getString("image");

                Log.v(LOG_TAG, String.valueOf(servings));
                Log.v(LOG_TAG,image);

                Recipe recipe = new Recipe(recipeId,recipeName,servings,image,ingredientsList,stepsList);

                recipeList.add(recipe);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return recipeList;
    }


    /**
     * Query the dataset and return a list of {@link Recipe} objects.
     */
    public static List<Recipe> fetchMoviesData(String requestUrl){
        URL url = createURL(requestUrl);

        String jsonResponse = null;

        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Recipe> movies = getRecipeFromJson(jsonResponse);

        return movies;
    }


}
