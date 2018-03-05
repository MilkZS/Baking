package com.example.android.baking.util;

import android.util.Log;
import android.widget.TextView;

import com.example.android.baking.base.RecipeStep;
import com.example.android.baking.base.RecipeSteps;
import com.example.android.baking.db.RecipeContract;

import java.util.ArrayList;

/**
 * Created by milkdz on 2018/3/4.
 */

public class FormRecipe {

    private static String TAG = "FormRecipe";

    public static String formIngredients(String sArray) {

        Log.d(TAG, "input Ingredients array string is " + sArray);
        String[] s = sArray.split(ReadFromJsonString.DIVIDE_TYPE);
        String showData = "\t\t\t\t";
        for (int i = 0; i < s.length; i++) {
            String[] ss = s[i].split(ReadFromJsonString.DIVIDE_CONTENT);
            Log.d(TAG, "ss[i] content is " + s[i]);
            Log.d(TAG, "ss length is " + ss.length);
            if (i != s.length - 1) {
                showData = showData + ss[0] + "\t" + ss[1] + "\t" + ss[2] + ",\t";
            } else {
                showData = showData + ss[0] + "\t" + ss[1] + "\t" + ss[2] + ".\t\n";
            }
        }
        return showData;
    }

    public static RecipeSteps addArrayList(String sArray) {
        Log.d(TAG, "input steps array string is " + sArray);
        RecipeSteps recipeSteps = new RecipeSteps();
        ArrayList<RecipeStep> recipeStepArrayList = new ArrayList<>();
        String[] s = sArray.split(ReadFromJsonString.DIVIDE_TYPE);
        for (int i = 0; i < s.length; i++) {
            String[] ss = s[i].split(ReadFromJsonString.DIVIDE_CONTENT);
            Log.d(TAG, "ss[i] content is " + s[i]);
            Log.d(TAG, "ss length is " + ss.length);
            RecipeStep recipeStep = new RecipeStep();
            recipeStep.setId(ss[0]);
            recipeStep.setsTitle(ss[1]);
            recipeStep.setsDescription(ss[2]);
            if( ss.length>3 && !ss[3].equals("")){
                recipeStep.setsVideo(ss[3]);
            }else if (ss.length>4 && !ss[4].equals("")){
                recipeStep.setsVideo(ss[4]);
            }else {
                recipeStep.setsVideo("");
            }

            recipeStepArrayList.add(recipeStep);
        }
        recipeSteps.setRecipeStepArrayList(recipeStepArrayList);
        return recipeSteps;
    }
}
