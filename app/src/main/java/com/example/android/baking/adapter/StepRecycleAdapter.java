package com.example.android.baking.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.baking.R;
import com.example.android.baking.base.RecipeStep;

import java.util.ArrayList;

/**
 * Created by milkz on 18-3-6.
 */

public class StepRecycleAdapter extends RecyclerView.Adapter<StepRecycleAdapter.MyStepRecycleHolder> {

    private ArrayList<RecipeStep> recipeStepArrayList;

    private Context context;

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
        }

        @Override
        public void onClick(View view) {

        }
    }

    public void swapData(ArrayList<RecipeStep> arrayList){
        recipeStepArrayList = arrayList;
        notifyDataSetChanged();
    }
}
