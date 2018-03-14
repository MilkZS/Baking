package com.example.android.baking.sync;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.android.baking.base.BaseInfo;
import com.example.android.baking.db.RecipeContract;
import com.example.android.baking.util.NetWorkUtil;
import com.example.android.baking.util.ReadFromJsonString;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by milkdz on 2018/2/25.
 */

public class RecipeSyncTask {

    private static String TAG = "RecipeSyncTask";

    synchronized public static  void syncRecipe(Context context){
        try {
           // URL url = NetWorkUtil.buildUrlFromHttp(BaseInfo.REMOTE_HTTP);
            URL url = new URL(BaseInfo.REMOTE_HTTP);
            Log.d(TAG,"url + ==> " + url);
            String sJson = NetWorkUtil.getResponseFromHttpUrl(url);
            Log.d(TAG,"after url is json string == > " + sJson);
            ContentValues[] contentValues = ReadFromJsonString.buildContentValuesForJson(sJson);
            SharedPreferences sharedPreferences = context.getSharedPreferences(BaseInfo.PREFERENCE_WIDGET,Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(BaseInfo.PREFERENCE_WIDGET_LENGTH,contentValues.length);
            editor.apply();
            editor.commit();
            if(contentValues != null){
                context.getContentResolver().delete(RecipeContract.CONTENT_BASE,null,null);
                context.getContentResolver().bulkInsert(RecipeContract.CONTENT_BASE,contentValues);
            }

            ContentValues[] contentValuesMaterial = ReadFromJsonString.getContenValuesForMaterial(sJson);
            if(contentValuesMaterial != null){
                context.getContentResolver().delete(RecipeContract.RecipeMaterial.CONTENT_BASE,null,null);
                context.getContentResolver().bulkInsert(RecipeContract.RecipeMaterial.CONTENT_BASE,contentValuesMaterial);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
