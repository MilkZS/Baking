package com.example.android.baking.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.baking.R;
import com.example.android.baking.adapter.StepRecycleAdapter;
import com.example.android.baking.base.BaseInfo;
import com.example.android.baking.base.RecipeStep;
import com.example.android.baking.base.TakeValues;
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

/**
 * Created by milkdz on 2018/3/10.
 */

public class VideoFragment extends Fragment implements StepRecycleAdapter.VideoClick {

    private String TAG = "VideoFragment";


    private SimpleExoPlayer mExoPlayer;
    private SimpleExoPlayerView mPlayerView;
    private TextView videoTextView;
    private ArrayList<RecipeStep> recipeStepArrayList;
    private int position = 0;
    private RecipeStep recipeStep;
    private Context context;
    private boolean bool_land = false;
    private long playerPosition = 0;
    private boolean ifPlayVideo = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_video,container,false);

        context = view.getContext();
        mPlayerView = view.findViewById(R.id.video_step);
        videoTextView = view.findViewById(R.id.video_description);
        view.findViewById(R.id.video_next_but).setVisibility(View.GONE);
        view.findViewById(R.id.video_last_but).setVisibility(View.GONE);

        if(savedInstanceState != null){
            if(savedInstanceState.containsKey(BaseInfo.ACTIVITY_VIDEO_POSITION)){
                playerPosition = savedInstanceState.getLong(BaseInfo.ACTIVITY_VIDEO_POSITION);
            }
        }

        Log.d(TAG,"position is " + position);
        if(recipeStepArrayList != null && recipeStepArrayList.size() != 0){
            Log.d(TAG,"arrayList size is " + recipeStepArrayList.size());
            getToStartPlayer();
        }
        return view;
    }

    public void setRecipeStepArrayList(ArrayList<RecipeStep> recipeStepArrayList) {
        this.recipeStepArrayList = recipeStepArrayList;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setIfPlayVideo(boolean ifPlayer){
        this.ifPlayVideo = ifPlayer;
    }

    /**
     * start to play video by uri
     *
     */
    private void getToStartPlayer(){
        if(position < recipeStepArrayList.size() && position >= 0){
            recipeStep = recipeStepArrayList.get(position);
            Uri uri = Uri.parse(recipeStep.getsVideo());
            Log.d(TAG,"video url is ==> " + uri);
            initializePlayer(uri,context);
            if (!bool_land){
                setVideoText();
            }
        }
    }

    private void setVideoText(){
        videoTextView.setText("");
        videoTextView.setText(recipeStep.getsDescription());
    }

    /**
     * Initialize ExoPlayer.
     * @param mediaUri The URI of the sample to play.
     */
    private void initializePlayer(Uri mediaUri, Context context) {
        if (mExoPlayer == null) {
            // Create an instance of the ExoPlayer.
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(context, trackSelector, loadControl);
            mExoPlayer.seekTo(playerPosition);
            mPlayerView.setPlayer(mExoPlayer);

            // Set the ExoPlayer.EventListener to this activity.

            mExoPlayer.addListener(TakeValues.eventListener);

            // Prepare the MediaSource.
           String userAgent = Util.getUserAgent(context, "Baking");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    context, userAgent), new DefaultExtractorsFactory(), null, null);
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(ifPlayVideo);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        releasePlayer();
    }

    @Override
    public void onStart() {
        super.onStart();
        if(Util.SDK_INT > 23){
            getToStartPlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(Util.SDK_INT <= 23 || mExoPlayer == null){
            getToStartPlayer();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if(Util.SDK_INT <= 23){
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if(Util.SDK_INT > 23){
            releasePlayer();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }

    /**
     * Release the video player
     *
     */
    private void releasePlayer(){
        if(mExoPlayer != null){
            playerPosition = mExoPlayer.getCurrentPosition();
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }

    @Override
    public void onClick(int position) {
        this.position = position;
        getToStartPlayer();
        notify();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if(mExoPlayer != null){
            playerPosition = mExoPlayer.getCurrentPosition();
        }
        outState.putLong(BaseInfo.ACTIVITY_VIDEO_POSITION,playerPosition);
        super.onSaveInstanceState(outState);
    }
}
