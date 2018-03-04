package com.example.android.baking.base;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by milkdz on 2018/3/4.
 */

public class RecipeSteps implements Serializable{



    private ArrayList<RecipeStep> recipeStepArrayList ;

    public RecipeSteps(ArrayList<RecipeStep> recipeStepArrayList) {
        this.recipeStepArrayList = recipeStepArrayList;
    }

    public ArrayList<RecipeStep> getRecipeStepArrayList() {
        return recipeStepArrayList;
    }

    public void setRecipeStepArrayList(ArrayList<RecipeStep> recipeStepArrayList) {
        this.recipeStepArrayList = recipeStepArrayList;
    }

    public RecipeSteps() {

    }
}
