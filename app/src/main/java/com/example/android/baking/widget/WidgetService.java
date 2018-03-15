package com.example.android.baking.widget;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.android.baking.R;
import com.example.android.baking.base.BaseInfo;
import com.example.android.baking.db.RecipeContract;
import com.example.android.baking.db.SQLBaseInfo;


/**
 * Created by milkdz on 2018/3/12.
 */

public class WidgetService extends RemoteViewsService {


    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        Log.e("widget", "onGetViewFactory");
        return new RemoteViewFactory(this.getApplicationContext());
    }
}

class RemoteViewFactory implements RemoteViewsService.RemoteViewsFactory {

    private String TAG = "Widget-RemoteViewFactory";
    private Context context;
    private Cursor cursor;
    private int po = 1;
    SharedPreferences sharedPreferences;

    public RemoteViewFactory(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(BaseInfo.PREFERENCE_WIDGET, Context.MODE_PRIVATE);
        po = sharedPreferences.getInt(BaseInfo.PREFERENCE_WIDGET_POSITION, 1);
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        if (cursor != null) {
            cursor.close();
        }

        Uri uri = RecipeContract.RecipeMaterial.CONTENT_BASE;
        String order = RecipeContract.RecipeMaterial._ID + SQLBaseInfo.SORT_AES;
        cursor = context.getContentResolver().query(
                uri,
                RecipeContract.RecipeMaterial.QUERY_MATERIAL,
                RecipeContract.buildSelectForMaterial(),
                new String[]{po + ""},
                order);
    }

    @Override
    public void onDestroy() {
        if (cursor != null) {
            cursor.close();
        }
    }

    @Override
    public int getCount() {
        Log.d(TAG, "getCount");
        if (cursor == null) {
            Log.e(TAG, "getCount cursor is null");
            return 0;
        }
        return cursor.getCount();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        if (cursor == null){
            return null;
        }
        cursor.moveToPosition(position);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        String name = cursor.getString(cursor.getColumnIndex(RecipeContract.RecipeMaterial.COLUMN_NAME));
        editor.putString(BaseInfo.PREFERENCE_WIDGET_NAME,name);
        editor.apply();
        editor.commit();

        Log.d(TAG, "bind is here");
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_item_list);
        views.setTextViewText(R.id.item_text_view, cursor.getString(cursor.getColumnIndex(RecipeContract.RecipeMaterial.COLUMN_MATERIAL)));///prepareList.get(position));
        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {

        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
