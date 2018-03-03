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

    public static String DIVIDE_TYPE = "-";
    public static String DIVIDE_CONTENT = "#";

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
                        queryInfo(jsonObject, RecipeContract.QUERY_INGREDIENTS));
                //contentValues.put(RecipeContract.RecipeInfo.COLUMN_STEP,
                 //       queryInfo(jsonObject.getString(BaseInfo.RECIPE_STEP),
                   //             BaseInfo.RECIPE_MODE_STEP));
                contentValuesArray[i] = contentValues;
            }
            return contentValuesArray;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String queryInfo(JSONObject jsonObject,String[] QUERY_ARRAY) {

       // Log.d(TAG,"query info jsonString is " + jsonString);
        try {
            JSONArray jsonArray = jsonObject.getJSONArray(BaseInfo.RECIPE_INGREDIENTS);//new JSONArray(jsonString);
            String sRe = "";
            for(int i=0;i<jsonArray.length();i++){
                JSONObject sObject = jsonArray.getJSONObject(i);
                sRe = sRe + sObject.getString(QUERY_ARRAY[0]);
                for (int j=1;j<QUERY_ARRAY.length;j++){
                    sRe = sRe + DIVIDE_CONTENT + sObject.getString(QUERY_ARRAY[j]);
                }
                if (i > 0){
                    sRe = sRe + DIVIDE_TYPE ;
                }
            }
            Log.d(TAG,"query info string array is " + sRe);
            return sRe;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
/*
    public static String queryInfo(String jsonString, int mode) {
        switch (mode) {
            case BaseInfo.RECIPE_MODE_INGREDIENTS: {
               return queryInfo(jsonString,RecipeContract.QUERY_INGREDIENTS);
            }
            case BaseInfo.RECIPE_MODE_STEP: {
                return queryInfo(jsonString,RecipeContract.QUERY_STEP);
            }
        }
        return null;
    }
*/
}
