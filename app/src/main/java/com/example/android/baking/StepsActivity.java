package com.example.android.baking;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.android.baking.adapter.PicRecycleAdapter;
import com.example.android.baking.adapter.StepRecycleAdapter;
import com.example.android.baking.base.BaseInfo;
import com.example.android.baking.base.RecipeStep;
import com.example.android.baking.base.RecipeSteps;

import java.util.ArrayList;

public class StepsActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps);

        RecyclerView recyclerView = findViewById(R.id.recipe_step_recycle);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        Intent intent = getIntent();
        RecipeSteps recipeSteps = (RecipeSteps) intent.getSerializableExtra(BaseInfo.INTENT_LIST);
        ArrayList<RecipeStep> recipeStepArrayList = recipeSteps.getRecipeStepArrayList();
        StepRecycleAdapter stepRecycleAdapter = new StepRecycleAdapter(recipeStepArrayList);

        recyclerView.setAdapter(stepRecycleAdapter);

    }
}
