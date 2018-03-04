package com.example.android.baking.sync;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.example.android.baking.db.RecipeContract;

/**
 * Created by milkdz on 2018/2/25.
 */

public class RecipeThread {

    private static String TAG = "RecipeThread";
    private static boolean DBG = true;
    private static boolean DBG_THREAD = true;
    private static boolean bool = true;

    public static void startIntentService(final Context context){
        Intent intent = new Intent(context,RecipeIntentService.class);
        context.startService(intent);
    }

    synchronized public static void initialize(final Context context){
        if (!bool){
            return;
        }
        bool = false;

        Log.d(TAG,"RecipeThread initialize start");

        Thread syncRecipe = new Thread(new Runnable() {
            @Override
            public void run() {
                if (DBG && DBG_THREAD)Log.d(TAG,"RecipeThread initialize run into thread");
                Uri uri = RecipeContract.CONTENT_BASE;
                String[] projection = new String[]{RecipeContract.RecipeInfo.COLUMN_JUDGE};

                Cursor cursor = context.getContentResolver().query(uri,projection,RecipeContract.buildSelect(),null,null);
                if (DBG_THREAD) Log.d(TAG,"RecipeThread initialize get cursor count is " + cursor.getCount());
                if(null == cursor || cursor.getCount() == 0){
                    Log.d(TAG,"RecipeThread initialize start intent service");
                    startIntentService(context);
                }

                if (cursor != null){
                    cursor.close();
                }
            }
        });
        syncRecipe.start();
    }
}
