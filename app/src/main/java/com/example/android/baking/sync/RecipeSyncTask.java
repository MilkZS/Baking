package com.example.android.baking.sync;

import android.content.ContentValues;
import android.content.Context;
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
            URL url = NetWorkUtil.buildUrlFromHttp(BaseInfo.REMOTE_HTTP);
            Log.d(TAG,"url + ==> " + url);
            String sJson = NetWorkUtil.getResponseFromHttpUrl(url);
            ContentValues[] contentValues = ReadFromJsonString.buildContentValuesForJson(sJson);
            if(contentValues != null){
                context.getContentResolver().delete(RecipeContract.CONTENT_BASE,null,null);
                context.getContentResolver().bulkInsert(RecipeContract.CONTENT_BASE,contentValues);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
