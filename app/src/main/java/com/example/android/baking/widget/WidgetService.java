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
        Log.e("widget","onGetViewFactory");
        ArrayList<String> arrayList = TakeValues.widgetArr.get(position);

        /*
        Uri foodUri = RecipeContract.CONTENT_BASE;
        Cursor cursor = this.getApplicationContext().getContentResolver().query(foodUri, null, null, null, null);
        cursor.moveToPosition(position);
        String prepareText = cursor.getString(
                cursor.getColumnIndex(RecipeContract.RecipeInfo.COLUMN_INGREDIENTS));
        String label = cursor.getString(
                cursor.getColumnIndex(RecipeContract.RecipeInfo.COLUMN_NAME));
        TakeValues.label = label;
        String[] s = prepareText.split(",");
        for(int i=0;i<s.length;i++){
            this.prepareText.add(s[i]);
        }*/

        return new RemoteViewFactory(this.getApplicationContext(),arrayList);
    }
}

class RemoteViewFactory implements RemoteViewsService.RemoteViewsFactory {

    private String TAG = "Widget-RemoteViewFactory";
    private Context context;
    private ArrayList<String> prepareList;


    public RemoteViewFactory(Context context,ArrayList<String> arrayList) {
        this.context = context;
        this.prepareList = arrayList;
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
       if(prepareList == null){
           return 0;
       }
        return prepareList.size()-2;
    }

    @Override
    public RemoteViews getViewAt(int position) {
       // if (prepareList == null || prepareList.size() == 0) {
       //     return null;
      //  }
        Log.e("widget","bind is here");
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_item_list);
        views.setTextViewText(R.id.item_text_view,prepareList.get(position+2));///prepareList.get(position));
/*

        Intent intent = new Intent(context,StepsActivity.class);
        intent.putExtra(BaseInfo.INTENT_LIST, FormRecipe.addArrayList(steps));
        intent.putExtra(BaseInfo.INTENT_TITLE,label);
        intent.putExtra(BaseInfo.INTENT_PREPARE,prepareText);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,intent,0);
        views.setOnClickPendingIntent(R.id.name_widget,pendingIntent);
        views.setOnClickPendingIntent(R.id.show_widget,pendingIntent    );
  */      return views;
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
