package com.example.android.baking.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.baking.R;
import com.example.android.baking.adapter.StepRecycleAdapter;
import com.example.android.baking.base.RecipeStep;
import com.example.android.baking.base.TakeValues;

import java.util.ArrayList;

/**
 * Created by milkdz on 2018/3/10.
 */

public class StepFragment extends Fragment implements StepRecycleAdapter.VideoClick{

    private String TAG = "StepFragment";

    private TextView prepareTextView;
    private String prepareText;
    private ArrayList<RecipeStep> recipeStepsArrayList;
    private ChangePosition changePosition;

    public StepFragment(){
    }

    private View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if(null != rootView){
            ViewGroup viewGroup = (ViewGroup) rootView.getParent();
            if (null != viewGroup){
                viewGroup.removeView(rootView);
            }
        }else {
            rootView = inflater.inflate(R.layout.activity_steps, container, false);


            RecyclerView recyclerView = rootView.findViewById(R.id.recipe_step_recycle);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(rootView.getContext());
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setHasFixedSize(true);

            prepareTextView = rootView.findViewById(R.id.prepare_text_view);

            prepareText = TakeValues.prepareText;

            Log.d(TAG, "it is run in the step fragment");

            if (recipeStepsArrayList != null) {
                prepareTextView.setText(prepareText);
                Log.d(TAG, "step left arrayList size is " + recipeStepsArrayList.size());
                StepRecycleAdapter stepRecycleAdapter = new StepRecycleAdapter(recipeStepsArrayList, this);

                recyclerView.setAdapter(stepRecycleAdapter);
            }
        }
        return rootView;
    }

    @Override
    public void onClick(int position) {
        Log.d(TAG,"it is click in fragment");
        TakeValues.ifChange = true;
        changePosition.change(position);

    }

    public void setChangePosition(ChangePosition changePosition) {
        this.changePosition = changePosition;
    }

    public interface ChangePosition{
        void change(int position);
    }

    public void setRecipeStepsArrayList(ArrayList<RecipeStep> recipeStepsArrayList) {
        this.recipeStepsArrayList = recipeStepsArrayList;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}
