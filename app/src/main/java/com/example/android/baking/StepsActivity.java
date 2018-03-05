package com.example.android.baking;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.android.baking.adapter.PicRecycleAdapter;
import com.example.android.baking.base.RecipeStep;
import com.example.android.baking.base.RecipeSteps;

import java.util.ArrayList;

public class StepsActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps);

        Intent intent = getIntent();
        RecipeSteps recipeSteps = (RecipeSteps) intent.getSerializableExtra(PicRecycleAdapter.INTENT_LIST);
        ArrayList<RecipeStep> recipeStepArrayList = recipeSteps.getRecipeStepArrayList();
    }

    private void fixChildUI(ArrayList<RecipeStep> recipeStepArrayList){

    }
}
