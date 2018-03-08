package com.example.android.baking.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.baking.R;
import com.example.android.baking.VideoActivity;
import com.example.android.baking.base.BaseInfo;
import com.example.android.baking.base.RecipeStep;

import java.util.ArrayList;

/**
 * Created by milkz on 18-3-6.
 */

public class StepRecycleAdapter extends RecyclerView.Adapter<StepRecycleAdapter.MyStepRecycleHolder> {

    private ArrayList<RecipeStep> recipeStepArrayList;
    private int position = 0;
    private Context context;
    VideoClick videoClick;

    public StepRecycleAdapter(ArrayList<RecipeStep> recipeStepArrayList,VideoClick videoClick) {
        this.recipeStepArrayList = recipeStepArrayList;
        this.videoClick = videoClick;
    }

    @Override
    public MyStepRecycleHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        int layoutList = R.layout.card_food_recipe_step;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutList,parent,false);
        return new MyStepRecycleHolder(view);
    }

    @Override
    public void onBindViewHolder(MyStepRecycleHolder holder, int position) {
        this.position = position;
        RecipeStep recipeStep = recipeStepArrayList.get(position);
        holder.short_description.setText(recipeStep.getsTitle());
    }

    @Override
    public int getItemCount() {
        if(recipeStepArrayList != null){
            return recipeStepArrayList.size();
        }
        return 0;
    }

    class MyStepRecycleHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView short_description;

        public MyStepRecycleHolder(View itemView) {
            super(itemView);
            short_description = itemView.findViewById(R.id.short_description);
            short_description.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            videoClick.onClick(position);
        }
    }

    public interface VideoClick{
       void  onClick(int position);
    }
}
