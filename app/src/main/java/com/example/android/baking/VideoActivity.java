package com.example.android.baking;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.android.baking.base.BaseInfo;
import com.example.android.baking.base.RecipeSteps;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;

/**
 * Created by milkz on 18-3-6.
 */

public class VideoActivity extends AppCompatActivity {

    private int position;
    private RecipeSteps recipeSteps ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        position = intent.getIntExtra(BaseInfo.INTENT_LIST_INDEX,0);
        recipeSteps = (RecipeSteps) intent.getSerializableExtra(BaseInfo.INTENT_LIST_INDEX);

    }

    private void videoImpl(){
        Handler videoHandler = new Handler();
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory =
                new AdaptiveTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector =
                new DefaultTrackSelector(videoTrackSelectionFactory);
        // 2. Create the player
        //SimpleExoPlayer player =
              //  ExoPlayerFactory.newSimpleInstance(this, trackSelector);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();


    }
}
