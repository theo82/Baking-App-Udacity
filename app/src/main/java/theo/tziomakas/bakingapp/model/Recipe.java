package theo.tziomakas.bakingapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by theodosiostziomakas on 15/03/2018.
 */

public class Recipe implements Parcelable{

    private int recipeId;
    private String recipeName;



    public Recipe(int recipeId, String recipeName, int servings, String image, List<Ingredients> ingredients, List<Steps> steps) {
        this.recipeId = recipeId;
        this.recipeName = recipeName;
        this.servings = servings;
        this.image = image;
        this.ingredients = ingredients;
        this.steps = steps;
    }

    private int servings;
    private String image;

    private List<Ingredients> ingredients;
    private List<Steps> steps;

    public int getRecipeId() {
        return recipeId;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public int getServings() {
        return servings;
    }

    public String getImage() {
        return image;
    }

    public List<Ingredients> getIngredients() {
        return ingredients;
    }

    public List<Steps> getSteps() {
        return steps;
    }
    public Recipe(Parcel in) {
        recipeId = in.readInt();
        recipeName = in.readString();
        servings = in.readInt();
        image = in.readString();
        ingredients = in.createTypedArrayList(Ingredients.CREATOR);
        steps = in.createTypedArrayList(Steps.CREATOR);
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(recipeId);
        dest.writeString(recipeName);
        dest.writeInt(servings);
        dest.writeString(image);
        dest.writeTypedList(ingredients);
        dest.writeTypedList(steps);
    }
}
