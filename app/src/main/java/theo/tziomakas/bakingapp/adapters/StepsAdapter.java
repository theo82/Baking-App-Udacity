package theo.tziomakas.bakingapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import theo.tziomakas.bakingapp.R;
import theo.tziomakas.bakingapp.model.Ingredients;
import theo.tziomakas.bakingapp.model.Recipe;
import theo.tziomakas.bakingapp.model.Steps;

/**
 * Created by theodosiostziomakas on 16/03/2018.
 */

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.StepsViewHolder> {
    private static final String LOG_TAG = RecipeAdapter.class.getSimpleName() ;
    private Context context;
    private List<Steps> stepsList;

    public StepsAdapter(Context context,List<Steps> stepsList){
        this.context = context;
        this.stepsList = stepsList;
    }

    @Override
    public StepsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.steps_row;
        LayoutInflater inflater = LayoutInflater.from(context);

        boolean shouldAttactToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem,parent,shouldAttactToParentImmediately);

        StepsViewHolder stepsViewHolder = new StepsViewHolder(view);

        return stepsViewHolder;
    }

    @Override
    public void onBindViewHolder(StepsViewHolder holder, int position) {
        Steps steps = stepsList.get(position);
        holder.mStepTextView.setText(steps.getShortDescription());
    }

    @Override
    public int getItemCount() {
        return stepsList.size();
    }

    public class StepsViewHolder extends RecyclerView.ViewHolder {
        TextView mStepTextView;
        public StepsViewHolder(View itemView) {
            super(itemView);

            mStepTextView = itemView.findViewById(R.id.recipe_step_text_view);
        }
    }
}
