package theo.tziomakas.bakingapp.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
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
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
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

    private ArrayList<Steps> stepsArrayList = new ArrayList<>();
    private SimpleExoPlayer mExoPlayer;
    private SimpleExoPlayerView mPlayerView;
    private TextView recipeDescriptionTextView;
    private String recipeDesciption;
    private int selectedIndex;

    private long playbackPosition;
    private int currentWindow;
    private boolean playWhenReady = true;

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

        recipeDescriptionTextView = v.findViewById(R.id.recipe_step_detail_text);

        itemClickListener = (RecipeDetailActivity) getActivity();

        if(savedInstanceState != null) {
            stepsArrayList = savedInstanceState.getParcelableArrayList(SELECTED_STEPS);
            selectedIndex = savedInstanceState.getInt(SELECTED_INDEX);
            videoUrl = savedInstanceState.getString("videoUrl");
            playbackPosition = savedInstanceState.getLong("playbackPosition");
            mPlayerView = v.findViewById(R.id.playerView);
            playWhenReady = savedInstanceState.getBoolean("playWhenReady");
            recipeDesciption = savedInstanceState.getString("description");

            recipeDescriptionTextView.setText(recipeDesciption);



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
                initializePlayer();

                if (v.findViewWithTag("sw600dp-land-recipe_step_detail")!=null) {
                    getActivity().findViewById(R.id.recipe_step_text_view).setLayoutParams(new LinearLayout.LayoutParams(-1,-2));
                    mPlayerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIXED_WIDTH);
                }
                else if (isInLandscapeMode(getContext())){
                    recipeDescriptionTextView.setVisibility(View.GONE);
                }
            } else {
                mExoPlayer = null;
                mPlayerView.setForeground(ContextCompat.getDrawable(getContext(), R.drawable.ic_visibility_off_white_36dp));
                mPlayerView.setLayoutParams(new LinearLayout.LayoutParams(300, 300));
            }

        }

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

                } else {
                    Toast.makeText(getActivity(), "You already are in the First step of the recipe", Toast.LENGTH_SHORT).show();
                }

            }
        });

        mNextstep.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                int lastIndex = stepsArrayList.size()-1;
                if (stepsArrayList.get(selectedIndex).getStepId() < stepsArrayList.get(lastIndex).getStepId()) {
                    if (mExoPlayer!=null){
                        mExoPlayer.stop();
                    }
                    itemClickListener.onListItemClick(stepsArrayList,stepsArrayList.get(selectedIndex).getStepId() + 1);
                }
                else {
                    Toast.makeText(getContext(),"You already are in the Last step of the recipe", Toast.LENGTH_SHORT).show();

                }
            }});


        return v;

    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            initializePlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        hideSystemUi();
        if ((Util.SDK_INT <= 23 || mExoPlayer == null)) {
            initializePlayer();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }

    private void initializePlayer() {
        if (mExoPlayer == null) {
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(new DefaultRenderersFactory(getActivity()),
                    new DefaultTrackSelector(), new DefaultLoadControl());
            mPlayerView.setPlayer(mExoPlayer);
            mExoPlayer.setPlayWhenReady(playWhenReady);
            mExoPlayer.seekTo(currentWindow, playbackPosition);
        }
        MediaSource mediaSource = buildMediaSource(Uri.parse(videoUrl));
        mExoPlayer.prepare(mediaSource, true, false);
    }

    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource.Factory(new DefaultHttpDataSourceFactory("baking app"))
                .createMediaSource(uri);
    }

    private void releasePlayer() {
        if (mExoPlayer != null) {

            mExoPlayer.release();
            mExoPlayer = null;
        }
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (mExoPlayer != null) {
            playbackPosition = mExoPlayer.getCurrentPosition();
            currentWindow = mExoPlayer.getCurrentWindowIndex();
            playWhenReady = mExoPlayer.getPlayWhenReady();
        }

        outState.putParcelableArrayList(SELECTED_STEPS,stepsArrayList);
        outState.putInt(SELECTED_INDEX,selectedIndex);
        outState.putString("videoUrl",videoUrl);
        outState.putString("description",recipeDesciption);
        outState.putLong("playbackPosition",playbackPosition);
        outState.putBoolean("playWhenReady",playWhenReady);


    }

    @SuppressLint("InlinedApi")
    private void hideSystemUi() {
        mPlayerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }
    public boolean isInLandscapeMode( Context context ) {
        return (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE);
    }

}
