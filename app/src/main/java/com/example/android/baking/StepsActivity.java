package com.example.android.baking;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.android.baking.adapter.StepRecycleAdapter;
import com.example.android.baking.base.BaseInfo;
import com.example.android.baking.base.RecipeStep;
import com.example.android.baking.base.RecipeSteps;

import java.util.ArrayList;

public class StepsActivity extends AppCompatActivity implements StepRecycleAdapter.VideoClick{

    private String TAG = "StepsActivity";
    private ArrayList<RecipeStep> recipeStepArrayList;
    private RecipeSteps recipeSteps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps);

        RecyclerView recyclerView = findViewById(R.id.recipe_step_recycle);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        Intent intent = getIntent();
        if(intent.hasExtra(BaseInfo.INTENT_LIST)) {

            recipeSteps = (RecipeSteps) intent.getSerializableExtra(BaseInfo.INTENT_LIST);
            recipeStepArrayList = recipeSteps.getRecipeStepArrayList();
            StepRecycleAdapter stepRecycleAdapter = new StepRecycleAdapter(recipeStepArrayList, this);

            recyclerView.setAdapter(stepRecycleAdapter);
        }
    }

    @Override
    public void onClick(int position) {
        Log.d(TAG,"it is click ");
        Intent intent = new Intent(this, VideoActivity.class);
        intent.putExtra(BaseInfo.INTENT_RECIPE,recipeSteps);
        intent.putExtra(BaseInfo.INTENT_LIST_INDEX,position);
        startActivity(intent);
    }
}
