package com.example.android.baking.sync;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;

import com.example.android.baking.db.RecipeContract;

/**
 * Created by milkdz on 2018/2/25.
 */

public class RecipeThread {

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

        Thread syncRecipe = new Thread(new Runnable() {
            @Override
            public void run() {
                Uri uri = RecipeContract.CONTENT_BASE;
                String[] projection = new String[]{RecipeContract.RecipeInfo.COLUMN_ID};
                Cursor cursor = context.getContentResolver().query(uri,projection,null,null,null);

                if(null == cursor || cursor.getCount() == 0){
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
