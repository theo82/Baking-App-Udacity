package theo.tziomakas.bakingapp.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;
import java.util.List;

import theo.tziomakas.bakingapp.R;
import theo.tziomakas.bakingapp.RecipeDetailActivity;

import theo.tziomakas.bakingapp.model.Steps;

import static theo.tziomakas.bakingapp.RecipeDetailActivity.SELECTED_INDEX;
import static theo.tziomakas.bakingapp.RecipeDetailActivity.SELECTED_STEPS;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecipeStepDetailFragment extends Fragment {

    //private ArrayList<Steps> stepsArrayList = new ArrayList<>();
    Steps steps;

    private ArrayList<Steps> stepsArrayList = new ArrayList<>();
    private SimpleExoPlayer mExoPlayer;
    private SimpleExoPlayerView mPlayerView;
    private TextView recipeDescriptionTextView;
    private String recipeDesciption;
    private int selectedIndex;

    private Button mPrevStep;
    private Button mNextstep;
    private String videoUrl;

    public RecipeStepDetailFragment() {
        // Required empty public constructor
    }

    private ListItemClickListener itemClickListener;

    public interface ListItemClickListener {
        void onListItemClick(List<Steps> steps,int clickedItemIndex);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_recipe_step_detail, container, false);

        itemClickListener = (RecipeDetailActivity) getActivity();

        if(savedInstanceState != null) {
            stepsArrayList = savedInstanceState.getParcelableArrayList(SELECTED_STEPS);
            selectedIndex = savedInstanceState.getInt(SELECTED_INDEX);
        }else {
            stepsArrayList = getArguments().getParcelableArrayList(SELECTED_STEPS);

            if (stepsArrayList != null) {
                stepsArrayList = getArguments().getParcelableArrayList(SELECTED_STEPS);
                selectedIndex = getArguments().getInt(SELECTED_INDEX);
            }


            videoUrl = stepsArrayList.get(selectedIndex).getVideoUrl();

            recipeDesciption = stepsArrayList.get(selectedIndex).getDescription();

            mPlayerView = v.findViewById(R.id.playerView);

            if (!videoUrl.isEmpty()) {
                initialize(Uri.parse(videoUrl));
            } else {
                mExoPlayer = null;
                mPlayerView.setForeground(ContextCompat.getDrawable(getContext(), R.drawable.ic_visibility_off_white_36dp));
                mPlayerView.setLayoutParams(new LinearLayout.LayoutParams(300, 300));
            }

            recipeDescriptionTextView = v.findViewById(R.id.recipe_step_detail_text);

            recipeDescriptionTextView.setText(recipeDesciption);

            mPrevStep = v.findViewById(R.id.previousStep);
            mNextstep = v.findViewById(R.id.nextStep);

            mPrevStep.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (stepsArrayList.get(selectedIndex).getStepId() > 0) {
                        if (mExoPlayer != null) {
                            mExoPlayer.stop();
                        }
                        itemClickListener.onListItemClick(stepsArrayList, stepsArrayList.get(selectedIndex).getStepId() - 1);

                        //getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new RecipeStepDetailFragment()).commit();
                    } else {
                        Toast.makeText(getActivity(), "You already are in the First step of the recipe", Toast.LENGTH_SHORT).show();
                    }

                }
            });

        }
        return v;

    }

    private void initialize(Uri mediaUri){
        if(mExoPlayer == null){
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector, loadControl);
            mPlayerView.setPlayer(mExoPlayer);
            // Prepare the MediaSource.
            String userAgent = Util.getUserAgent(getActivity(), "Baking App");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    getActivity(), userAgent), new DefaultExtractorsFactory(), null, null);
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(true);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle currentState) {
        super.onSaveInstanceState(currentState);
        currentState.putParcelableArrayList(SELECTED_STEPS,stepsArrayList);
        currentState.putInt(SELECTED_INDEX,selectedIndex);

    }
/*
    @Override
    public void onDetach() {
        super.onDetach();
        if (mExoPlayer!=null) {
            mExoPlayer.stop();
            mExoPlayer.release();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mExoPlayer!=null) {
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer=null;
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mExoPlayer!=null) {
            mExoPlayer.stop();
            mExoPlayer.release();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mExoPlayer!=null) {
            mExoPlayer.stop();
            mExoPlayer.release();
        }
    }
    */
}
