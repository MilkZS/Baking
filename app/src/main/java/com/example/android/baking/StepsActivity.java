package com.example.android.baking;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.EventListener;

public class StepsActivity extends AppCompatActivity implements StepRecycleAdapter.VideoClick, ExoPlayer.EventListener,StepFragment.ChangePosition {

    private String TAG = "StepsActivity";
    private ArrayList<RecipeStep> recipeStepArrayList;
    private RecipeSteps recipeSteps;
    private String label;
    private TextView prepareText;
    private FragmentTransaction FT;
    private StepFragment stepFragment;
    private VideoFragment videoFragment;
    private boolean ifUseFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ifUseFragment = TakeValues.ifUseFragment;
        if (ifUseFragment) {
            setContentView(R.layout.activity_step_video_pad);
        } else {
            setContentView(R.layout.activity_steps);
            prepareText = findViewById(R.id.prepare_text_view);
        }

        Intent intent = getIntent();
        String s = intent.getStringExtra(BaseInfo.INTENT_PREPARE);
        Log.e(TAG,"intent s == > " + s);
        if (intent.hasExtra(BaseInfo.INTENT_PREPARE)) {
             s = intent.getStringExtra(BaseInfo.INTENT_PREPARE);
            Log.e(TAG,"go into intent and s =" + s);
            if (!ifUseFragment) {
                prepareText.setText(s);
            }
            TakeValues.prepareText = s;
        }

        TakeValues.eventListener = this;

        if (intent.hasExtra(BaseInfo.INTENT_TITLE)) {
            label = intent.getStringExtra(BaseInfo.INTENT_TITLE);
            Log.e(TAG,"go into intent and label = " + label);
            setTitle(label);
        } else {
            label = "video";
        }
        if (intent.hasExtra(BaseInfo.INTENT_LIST)) {

            recipeSteps = (RecipeSteps) intent.getSerializableExtra(BaseInfo.INTENT_LIST);
            recipeStepArrayList = recipeSteps.getRecipeStepArrayList();
           // TakeValues.recipeStepsArrayList = recipeStepArrayList;
            if (!ifUseFragment) {
                RecyclerView recyclerView = findViewById(R.id.recipe_step_recycle);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setHasFixedSize(true);
                StepRecycleAdapter stepRecycleAdapter = new StepRecycleAdapter(recipeStepArrayList, this);
                recyclerView.setAdapter(stepRecycleAdapter);
            }
        }

        if (ifUseFragment && savedInstanceState == null) {
            //if(stepFragment == null){
                stepFragment = new StepFragment();
                stepFragment.setRecipeStepsArrayList(recipeStepArrayList);
                stepFragment.setChangePosition(this);
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fragment_step, stepFragment).commit();


            //}

//            if(videoFragment == null){
            Log.e(TAG, "it is going to create VideoFragment");
            videoFragment = new VideoFragment();
            videoFragment.setRecipeStepArrayList(recipeStepArrayList);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_video, videoFragment).commit();
            //          }
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
        VideoFragment videoFragment = new VideoFragment();
        videoFragment.setRecipeStepArrayList(recipeStepArrayList);
        videoFragment.setPosition(position);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_video,videoFragment).commit();
    }
}
