package com.example.android.baking;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.android.baking.base.BaseInfo;
import com.example.android.baking.base.RecipeStep;
import com.example.android.baking.base.RecipeSteps;

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
    }
}
