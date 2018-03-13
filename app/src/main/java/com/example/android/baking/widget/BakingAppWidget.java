package com.example.android.baking.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
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

    public static final String BUTTON_ACTION = "com.example.android.baking.click";

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
            String name = TakeValues.widgetArr.get(po).get(0);
            views.setTextViewText(R.id.name_widget,name);
            views.setRemoteAdapter(R.id.prepare_list_view_widget, intent);

            Intent intentName = new Intent(context,StepsActivity.class);
            intentName.putExtra(BaseInfo.INTENT_TITLE,name);
            intentName.putExtra(BaseInfo.INTENT_LIST,TakeValues.recipeArr.get(po));
            intentName.putExtra(BaseInfo.INTENT_PREPARE,TakeValues.widgetArr.get(po).get(2));
            PendingIntent pendingIntent = PendingIntent.getActivity(context,0,intentName,0);
            views.setOnClickPendingIntent(R.id.name_widget,pendingIntent);

           // Intent intentBut = new Intent(BUTTON_ACTION);
            //PendingIntent pendingIntentBut = PendingIntent.getBroadcast(context, R.id.change_but, intentBut, PendingIntent.FLAG_UPDATE_CURRENT);
            //views.setOnClickPendingIntent(R.id.change_but,pendingIntentBut);

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


    public static void sendRefreshBroadcast(Context context) {
        Intent intent = new Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        intent.setComponent(new ComponentName(context, BakingAppWidget.class));
        context.sendBroadcast(intent);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
    }
}

