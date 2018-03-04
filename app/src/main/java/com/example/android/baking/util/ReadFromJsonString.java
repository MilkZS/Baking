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

    public static String DIVIDE_TYPE = "_TYPE_";
    public static String DIVIDE_CONTENT = "_CONTENT_";

    private static String QUERY_INGREDIENTS = "ingredients";

    public static ContentValues[] buildContentValuesForJson(String jsonString) {
        try {
            Log.d(TAG, "jsonString is : == > " + jsonString);
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
                        queryInfo(jsonObject, RecipeContract.QUERY_INGREDIENTS,
                                BaseInfo.RECIPE_INGREDIENTS));
                contentValues.put(RecipeContract.RecipeInfo.COLUMN_STEP,
                        queryInfo(jsonObject,RecipeContract.QUERY_STEP,
                                BaseInfo.RECIPE_STEP));
                contentValues.put(RecipeContract.RecipeInfo.COLUMN_JUDGE, "1");
                contentValuesArray[i] = contentValues;
            }
            return contentValuesArray;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String queryInfo(JSONObject jsonObject, String[] QUERY_ARRAY,String query) {

        try {
            JSONArray jsonArray = jsonObject.getJSONArray(query);
            String sRe = "";
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject sObject = jsonArray.getJSONObject(i);
                for (int j = 0; j < QUERY_ARRAY.length; j++) {
                    if (j == 0) {
                        sRe = sRe + sObject.getString(QUERY_ARRAY[j]);
                    } else {
                        sRe = sRe + DIVIDE_CONTENT + sObject.getString(QUERY_ARRAY[j]);
                    }
                }
                if(i != jsonArray.length() - 1){
                    sRe = sRe + DIVIDE_TYPE;
                }
            }
            Log.d(TAG, "query info string array is " + sRe);
            return sRe;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
