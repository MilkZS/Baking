package com.example.android.baking;

import android.app.FragmentTransaction;
import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.example.android.baking.adapter.StepRecycleAdapter;
import com.example.android.baking.base.BaseInfo;
import com.example.android.baking.base.RecipeStep;
import com.example.android.baking.base.RecipeSteps;
import com.example.android.baking.base.TakeValues;
import com.example.android.baking.fragment.StepFragment;
import com.example.android.baking.fragment.VideoFragment;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;

import java.util.ArrayList;

public class StepsActivity extends AppCompatActivity implements StepRecycleAdapter.VideoClick, ExoPlayer.EventListener, StepFragment.ChangePosition {

    private String TAG = "StepsActivity";
    private ArrayList<RecipeStep> recipeStepArrayList;
    private RecipeSteps recipeSteps;
    private String label;
    private TextView prepareTextView;

    private int listPosition = 0;
    private FragmentTransaction FT;
    private StepFragment stepFragment;
    private VideoFragment videoFragment;
    private boolean ifUseFragment;
    private long videoPosition = 0;
    private boolean ifPlayer = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ifUseFragment = TakeValues.ifUseFragment;
        if (ifUseFragment) {
            setContentView(R.layout.activity_step_video_pad);
        } else {
            setContentView(R.layout.activity_steps);
            prepareTextView = findViewById(R.id.prepare_text_view);
        }

        TakeValues.eventListener = this;
        if (savedInstanceState != null && ifUseFragment) {
            videoPosition = savedInstanceState.getLong(BaseInfo.ACTIVITY_STEP_VIDEO_POSITION);
            ifPlayer = savedInstanceState.getBoolean(BaseInfo.ACTIVITY_STEP_VIDEO_PLAY);
            recipeStepArrayList = ((RecipeSteps) savedInstanceState
                    .getSerializable(BaseInfo.ACTIVITY_STEP_STATUS_RECIPE)).getRecipeStepArrayList();
            listPosition = savedInstanceState.getInt(BaseInfo.ACTIVITY_STEP_LIST_POSITION);
            label = savedInstanceState.getString(BaseInfo.ACTIVITY_STEP_LABEL);
        }
        /** get info from intent */
        Intent intent = getIntent();
        if (intent.hasExtra(BaseInfo.INTENT_PREPARE)) {
            String s = intent.getStringExtra(BaseInfo.INTENT_PREPARE);
            Log.d(TAG, "go into intent and s =" + s);
            if (!ifUseFragment) {
                prepareTextView.setText(s);
            }
            TakeValues.prepareText = s;
        }

        if (intent.hasExtra(BaseInfo.INTENT_TITLE)) {
            label = intent.getStringExtra(BaseInfo.INTENT_TITLE);
            Log.d(TAG, "go into intent and label = " + label);
            setTitle(label);
        } else {
            setTitle(label);
        }

        if (intent.hasExtra(BaseInfo.INTENT_LIST)) {

            recipeSteps = (RecipeSteps) intent.getSerializableExtra(BaseInfo.INTENT_LIST);
            recipeStepArrayList = recipeSteps.getRecipeStepArrayList();
            if (!ifUseFragment) {
                RecyclerView recyclerView = findViewById(R.id.recipe_step_recycle);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setHasFixedSize(true);
                StepRecycleAdapter stepRecycleAdapter = new StepRecycleAdapter(recipeStepArrayList, this);
                recyclerView.setAdapter(stepRecycleAdapter);
            }
        }

        /** work for tablet */
        if (ifUseFragment) {
            stepFragment = new StepFragment();
            stepFragment.setRecipeStepsArrayList(recipeStepArrayList);
            stepFragment.setChangePosition(this);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_step, stepFragment).commit();


            Log.e(TAG, "it is going to create VideoFragment");
            Log.i(TAG, "the recipe arraylist size = " + recipeStepArrayList.size());
            TakeValues.recipeStepArrayList = recipeStepArrayList;
            videoFragment = new VideoFragment();
            videoFragment.setRecipeStepArrayList(recipeStepArrayList);
            videoFragment.setIfPlayVideo(ifPlayer);
            videoFragment.setVideoPosition(videoPosition);
            videoFragment.setPosition(listPosition);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_video, videoFragment).commit();
        }

    }


    @Override
    public void onClick(int position) {
        Log.d(TAG, "it is click ");

        if (!ifUseFragment) {
            Intent intent = new Intent(this, VideoActivity.class);
            intent.putExtra(BaseInfo.INTENT_RECIPE, recipeSteps);
            intent.putExtra(BaseInfo.INTENT_LIST_INDEX, position);
            intent.putExtra(BaseInfo.INTENT_TITLE, label);
            this.startActivity(intent);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        ifPlayer = playWhenReady;
    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onPositionDiscontinuity() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void change(int position) {

        if(TakeValues.ifChange){
            ifPlayer = false;
        }
        TakeValues.ifChange = false;
        listPosition = position;
        VideoFragment videoFragment = new VideoFragment();
        videoFragment.setRecipeStepArrayList(recipeStepArrayList);
        videoFragment.setPosition(position);
        videoFragment.setIfPlayVideo(ifPlayer);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_video, videoFragment).commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        outState.putInt(BaseInfo.ACTIVITY_STEP_LIST_POSITION, listPosition);
        outState.putBoolean(BaseInfo.ACTIVITY_STEP_VIDEO_PLAY, ifPlayer);
        outState.putLong(BaseInfo.ACTIVITY_STEP_VIDEO_POSITION, TakeValues.videoPosition);
        outState.putString(BaseInfo.ACTIVITY_STEP_LABEL, label);
        outState.putSerializable(BaseInfo.ACTIVITY_STEP_STATUS_RECIPE, recipeSteps);
        super.onSaveInstanceState(outState);
    }
}
