package com.example.android.baking.widget;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.android.baking.R;
import com.example.android.baking.StepsActivity;
import com.example.android.baking.base.BaseInfo;
import com.example.android.baking.db.RecipeContract;
import com.example.android.baking.util.FormRecipe;

/**
 * Created by milkdz on 2018/3/12.
 */

public class WidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new RemoteViewFactory(this.getApplicationContext());
    }
}

class RemoteViewFactory implements RemoteViewsService.RemoteViewsFactory {

    Context context;
    Cursor cursor;

    public RemoteViewFactory(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        Uri foodUri = RecipeContract.CONTENT_BASE;
        if (cursor != null) {
            cursor.close();
        }
        cursor = context.getContentResolver().query(foodUri, null, null, null, null);
    }

    @Override
    public void onDestroy() {
        cursor.close();
    }

    @Override
    public int getCount() {
       if(cursor == null){
           return 0;
       }
        return cursor.getCount();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        if (cursor == null || cursor.getCount() == 0) {
            return null;
        }

        cursor.moveToPosition(position);

        String label = cursor.getString(
                cursor.getColumnIndex(RecipeContract.RecipeInfo.COLUMN_NAME));
        String steps = cursor.getString(
                cursor.getColumnIndex(RecipeContract.RecipeInfo.COLUMN_STEP));
        String prepareText = cursor.getString(
                cursor.getColumnIndex(RecipeContract.RecipeInfo.COLUMN_INGREDIENTS));


        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_app_widget);

        views.setTextViewText(R.id.name_widget, label);
        views.setTextViewText(R.id.show_widget, prepareText);


        Intent intent = new Intent(context,StepsActivity.class);
        intent.putExtra(BaseInfo.INTENT_LIST, FormRecipe.addArrayList(steps));
        intent.putExtra(BaseInfo.INTENT_TITLE,label);
        intent.putExtra(BaseInfo.INTENT_PREPARE,prepareText);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,intent,0);
        views.setOnClickPendingIntent(R.id.name_widget,pendingIntent);
        views.setOnClickPendingIntent(R.id.show_widget,pendingIntent    );
        return views;
    }
    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
