package com.example.android.baking;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.baking.base.BaseInfo;
import com.example.android.baking.base.RecipeStep;
import com.example.android.baking.base.RecipeSteps;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;

/**
 * Created by milkz on 18-3-6.
 */

public class VideoActivity extends AppCompatActivity implements ExoPlayer.EventListener{

    private String TAG = "VideoActivity";
    private int position = 0;
    private RecipeStep recipeStep;
    private RecipeSteps recipeSteps;
    private SimpleExoPlayer mExoPlayer;
    private SimpleExoPlayerView mPlayerView;
    private ArrayList<RecipeStep> recipeStepArrayList;
    private int len = 0;
    private TextView videoTextView;
    private boolean bool_land = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        Toast.makeText(this,"this is onCreate",Toast.LENGTH_SHORT).show();


        Intent intent = getIntent();
        if (intent.hasExtra(BaseInfo.INTENT_TITLE)){
            String label = intent.getStringExtra(BaseInfo.INTENT_TITLE);
            setTitle(label);
        }

        if(intent.hasExtra(BaseInfo.INTENT_RECIPE)){
            recipeSteps = (RecipeSteps) intent.getSerializableExtra(BaseInfo.INTENT_RECIPE);
            recipeStepArrayList = recipeSteps.getRecipeStepArrayList();
            len = recipeStepArrayList.size();
        }

        if(savedInstanceState != null){
            if (savedInstanceState.containsKey(BaseInfo.ACTIVITY_POSITION)){
                position = savedInstanceState.getInt(BaseInfo.ACTIVITY_POSITION);
            }
        }else {

            if (intent.hasExtra(BaseInfo.INTENT_LIST_INDEX)) {
                position = intent.getIntExtra(BaseInfo.INTENT_LIST_INDEX, 0);
                Log.d(TAG, "video position is " + position);
            }
        }
        bool_land = getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE;

        if(!bool_land){
            videoTextView = findViewById(R.id.video_description);
        }
        mPlayerView = findViewById(R.id.video_step);
        getToStartPlayer();

    }

    /**
     * Initialize ExoPlayer.
     * @param mediaUri The URI of the sample to play.
     */
    private void initializePlayer(Uri mediaUri) {
        if (mExoPlayer == null) {
            // Create an instance of the ExoPlayer.
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(this, trackSelector, loadControl);
            mPlayerView.setPlayer(mExoPlayer);

            // Set the ExoPlayer.EventListener to this activity.
            mExoPlayer.addListener(this);

            // Prepare the MediaSource.
            String userAgent = Util.getUserAgent(this, "ClassicalMusicQuiz");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    this, userAgent), new DefaultExtractorsFactory(), null, null);
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(true);
        }
    }

    /**
     * Used for last button to play last video
     *
     * @param view activity.xml
     */
    public void jumpToLast(View view){
        position --;
        if(position < 0){
            position = 0;
            String dialog = getResources().getString(R.string.show_wrong_dialog_last);
            Toast.makeText(this,dialog,Toast.LENGTH_SHORT).show();
            return;
        }
        deletePlayer();
        getToStartPlayer();
    }

    /**
     * Used for next button to play next video
     *
     * @param view activity.xml
     */
    public void jumpToNext(View view){
        position ++;
        if(position >= len ){
            position = len - 1;
            String dialog = getResources().getString(R.string.show_wrong_dialog_next);
            Toast.makeText(this,dialog,Toast.LENGTH_SHORT).show();
            return;
        }
        deletePlayer();
        getToStartPlayer();
    }

    /**
     * start to play video by uri
     *
     */
    private void getToStartPlayer(){
        recipeStep = recipeStepArrayList.get(position);
        Uri uri = Uri.parse(recipeStep.getsVideo());
        initializePlayer(uri);
        if (!bool_land){
            setVideoText();
        }
    }

    private void setVideoText(){
        videoTextView.setText("");
        videoTextView.setText(recipeStep.getsDescription());
    }

    /**
     * Release the video player
     *
     */
    private void deletePlayer(){
        mExoPlayer.stop();
        mExoPlayer.release();
        mExoPlayer = null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        deletePlayer();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        outState.putInt(BaseInfo.ACTIVITY_POSITION,position);

        super.onSaveInstanceState(outState);
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
}
