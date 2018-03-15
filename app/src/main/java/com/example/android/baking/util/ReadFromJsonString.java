package com.example.android.baking.util;

import android.content.ContentValues;
import android.util.Log;

import com.example.android.baking.base.BaseInfo;
import com.example.android.baking.db.RecipeContract;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by milkdz on 2018/2/23.
 */

public class ReadFromJsonString {

    private static String TAG = "ReadFromJsonString";
    private static boolean DBG = true;

    public static String DIVIDE_TYPE = "_TYPE_";
    public static String DIVIDE_CONTENT = "_CONTENT_";


    public static ContentValues[] buildContentValuesForJson(String jsonString) {
        try {
            Log.d(TAG, "jsonString is : == > " + jsonString);
            JSONArray recipeArray = new JSONArray(jsonString);
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
                        queryInfo(jsonObject, RecipeContract.QUERY_STEP,
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

    public static ContentValues[] getContenValuesForMaterial(String jsonS) {
        try {
            JSONArray recipeArray = new JSONArray(jsonS);
            ArrayList<ContentValues> arrayList = new ArrayList<>();
            if (DBG) Log.d(TAG, "recipeArray = " + recipeArray);
            JSONObject jsonObject;
            for (int i = 0; i < recipeArray.length(); i++) {
                jsonObject = recipeArray.getJSONObject(i);
                String id = jsonObject.getString(BaseInfo.RECIPE_ID);
                String name = jsonObject.getString(BaseInfo.RECIPE_NAME);
                JSONArray jsonArray = jsonObject.getJSONArray(BaseInfo.RECIPE_INGREDIENTS);
                for (int j = 0; j < jsonArray.length(); j++) {
                    JSONObject sObject = jsonArray.getJSONObject(j);
                    String s = "";

                    for (int a = 0; a < RecipeContract.QUERY_INGREDIENTS.length; a++) {
                        s = s + sObject.get(RecipeContract.QUERY_INGREDIENTS[a]) + " ";
                    }
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(RecipeContract.RecipeMaterial.COLUMN_ID, id);
                    Log.i(TAG,"material id is " + id);
                    Log.i(TAG,"material string is " + s);
                    contentValues.put(RecipeContract.RecipeMaterial.COLUMN_MATERIAL, s);
                    contentValues.put(RecipeContract.RecipeMaterial.COLUMN_NAME,name);
                    arrayList.add(contentValues);
                }
            }
            ContentValues[] contentValuesArray = new ContentValues[arrayList.size()];
            for (int i = 0; i < arrayList.size(); i++) {
                contentValuesArray[i] = arrayList.get(i);
            }
            return contentValuesArray;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String queryInfo(JSONObject jsonObject, String[] QUERY_ARRAY, String query) {

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
                if (i != jsonArray.length() - 1) {
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
