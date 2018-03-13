package com.example.android.baking.widget;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.android.baking.R;
import com.example.android.baking.StepsActivity;
import com.example.android.baking.base.BaseInfo;
import com.example.android.baking.base.TakeValues;
import com.example.android.baking.db.RecipeContract;
import com.example.android.baking.util.FormRecipe;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by milkdz on 2018/3/12.
 */

public class WidgetService extends RemoteViewsService {

    private int position = 0;
    private SharedPreferences sharedPreferences;

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        sharedPreferences = getSharedPreferences(BaseInfo.PREFERENCE_WIDGET,MODE_PRIVATE);
        if(sharedPreferences.contains(BaseInfo.PREFERENCE_WIDGET_POSITION)){
            position = sharedPreferences.getInt(BaseInfo.PREFERENCE_WIDGET_POSITION,0);
        }else{
            position = 0;
        }
        Log.d("widget","onGetViewFactory");
        ArrayList<String> arrayList = TakeValues.widgetArr.get(position);
        return new RemoteViewFactory(this.getApplicationContext(),arrayList);
    }
}

class RemoteViewFactory implements RemoteViewsService.RemoteViewsFactory {

    private String TAG = "Widget-RemoteViewFactory";
    private Context context;
    private ArrayList<String> prepareList;
    private int lenArr = 3;
    private int po = 0;

    public RemoteViewFactory(Context context,ArrayList<String> arrayList) {
        this.context = context;
        this.prepareList = arrayList;
        SharedPreferences sharedPreferences = context.getSharedPreferences(BaseInfo.PREFERENCE_WIDGET,Context.MODE_PRIVATE);
        po = sharedPreferences.getInt(BaseInfo.PREFERENCE_WIDGET_POSITION,0);
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        if (prepareList != null) {
        }
    }

    @Override
    public void onDestroy() {
    }

    @Override
    public int getCount() {
        Log.e(TAG,"getCount");
       if(prepareList.size() == 0){
           return 0;
       }
        return prepareList.size()-lenArr;
    }

    @Override
    public RemoteViews getViewAt(int position) {
       // if (prepareList == null || prepareList.size() == 0) {
       //     return null;
      //  }
        Log.e("widget","bind is here");
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_item_list);
        views.setTextViewText(R.id.item_text_view,prepareList.get(position+lenArr));///prepareList.get(position));
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
