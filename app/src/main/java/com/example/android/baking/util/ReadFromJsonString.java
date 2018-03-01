package com.example.android.baking.util;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import com.example.android.baking.base.BaseInfo;
import com.example.android.baking.db.RecipeContract;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by milkdz on 2018/2/23.
 */

public class ReadFromJsonString {

    private static String TAG = "ReadFromJsonString";
    private static boolean DBG = true;

    private static String QUERY_INGREDIENTS = "ingredients";

    public static ContentValues[] buildContentValuesForJson(String jsonString) {
        try {
            Log.d(TAG,"jsonString is : == > " + jsonString);
            //JSONArray jsonArray = new JSONArray(jsonString);
           // JSONObject recipeJson = new JSONObject(jsonString);
            JSONArray recipeArray = new JSONArray(jsonString);//recipeJson.getJSONArray("");
            if (DBG) Log.d(TAG, "recipeArray = " + recipeArray);
            ContentValues[] contentValuesArray = new ContentValues[recipeArray.length()];
            JSONObject jsonObject;
            for (int i = 0; i < recipeArray.length(); i++) {
                jsonObject = recipeArray.getJSONObject(i);
                ContentValues contentValues = new ContentValues();
                contentValues.put(RecipeContract.RecipeInfo.COLUMN_ID,
                        jsonObject.getString(BaseInfo.RECIPE_ID));
                contentValues.put(RecipeContract.RecipeInfo.COLUMN_NAME,
                        jsonObject.getString(BaseInfo.RECIPE_NAME));
                contentValues.put(RecipeContract.RecipeInfo.COLUMN_SERVINGS,
                        jsonObject.getString(BaseInfo.RECIPE_SERVINGS));
                contentValues.put(RecipeContract.RecipeInfo.COLUMN_INGREDIENTS,
                        queryIngredients(jsonObject.getString(BaseInfo.RECIPE_INGREDIENTS),
                                BaseInfo.RECIPE_MODE_INGREDIENTS));
                contentValues.put(RecipeContract.RecipeInfo.COLUMN_STEP,
                        queryIngredients(jsonObject.getString(BaseInfo.RECIPE_STEP),
                                BaseInfo.RECIPE_MODE_STEP));
                contentValuesArray[i] = contentValues;
            }
            return contentValuesArray;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String queryIngredients(String jsonString,String[] QUERY_ARRAY) {

        try {
            JSONObject jsonObjectJson = new JSONObject(jsonString);
            String s = QUERY_ARRAY[0];
            for (int i = 1; i < QUERY_ARRAY.length; i++) {
                s = s + "###" + jsonObjectJson.getString(QUERY_ARRAY[i]);
            }
            return s;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String queryIngredients(String jsonString, int mode) {
        switch (mode) {
            case BaseInfo.RECIPE_MODE_INGREDIENTS: {
               return queryIngredients(jsonString,RecipeContract.QUERY_INGREDIENTS);
            }
            case BaseInfo.RECIPE_MODE_STEP: {
                return queryIngredients(jsonString,RecipeContract.QUERY_STEP);
            }
        }
        return null;
    }

}
