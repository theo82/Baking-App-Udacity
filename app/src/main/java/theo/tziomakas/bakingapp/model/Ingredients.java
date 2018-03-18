package theo.tziomakas.bakingapp.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by theodosiostziomakas on 15/03/2018.
 */

public class Ingredients implements Parcelable{

    private Double quantity;
    private String measure;
    private String ingredient;

    public Ingredients(double quantity,String measure,String ingredient){
        this.quantity = quantity;
        this.measure = measure;
        this.ingredient = ingredient;
    }


    public double getQuantity() {
        return quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public String getIngredient() {
        return ingredient;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    protected Ingredients(Parcel in) {
        quantity = in.readDouble();
        measure = in.readString();
        ingredient = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(quantity);
        dest.writeString(measure);
        dest.writeString(ingredient);
    }

    public static final Creator<Ingredients> CREATOR = new Creator<Ingredients>() {
        @Override
        public Ingredients createFromParcel(Parcel in) {
            return new Ingredients(in);
        }

        @Override
        public Ingredients[] newArray(int size) {
            return new Ingredients[size];
        }
    };
}
