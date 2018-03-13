package com.example.android.baking.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;

import com.example.android.baking.R;
import com.example.android.baking.StepsActivity;
import com.example.android.baking.base.BaseInfo;
import com.example.android.baking.base.TakeValues;

/**
 * Implementation of App Widget functionality.
 */
public class BakingAppWidget extends AppWidgetProvider {


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            RemoteViews views = new RemoteViews(
                    context.getPackageName(),
                    R.layout.baking_app_widget
            );
            Intent intent = new Intent(context, WidgetService.class);
           // views.setPendingIntentTemplate();
            SharedPreferences sharedPreferences = context.getSharedPreferences(BaseInfo.PREFERENCE_WIDGET,Context.MODE_PRIVATE);
            int po = sharedPreferences.getInt(BaseInfo.PREFERENCE_WIDGET_POSITION,0);
            views.setTextViewText(R.id.name_widget,TakeValues.widgetArr.get(po).get(0));
            views.setRemoteAdapter(R.id.prepare_list_view_widget, intent);
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

