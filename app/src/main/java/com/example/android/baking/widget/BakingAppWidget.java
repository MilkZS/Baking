package com.example.android.baking.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.android.baking.R;
import com.example.android.baking.StepsActivity;
import com.example.android.baking.base.BaseInfo;
import com.example.android.baking.base.TakeValues;

/**
 * Implementation of App Widget functionality.
 */
public class BakingAppWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        String sName = TakeValues.label;//TakeValues.recipeStepsArrayList.get(TakeValues.widgetPosition).getsTitle();
        Log.d("widget ","widget name is ==> " + sName);

        String sParpre = "";

        String[] s = TakeValues.prepareText.split(",");
        for(int i=0;i<s.length-1;i++){
            sParpre = sParpre + s[i] + "," + "\n";
        }
        sParpre = sParpre + s[s.length - 1] + ".";
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_app_widget);
        //views.setTextViewText(R.id.appwidget_text, widgetText);

        views.setTextViewText(R.id.name_widget,sName);
        views.setTextViewText(R.id.show_widget,sParpre);

        Intent intent = new Intent(context,StepsActivity.class);
        intent.putExtra(BaseInfo.INTENT_LIST,TakeValues.recipeSteps);
        intent.putExtra(BaseInfo.INTENT_TITLE,sName);
        intent.putExtra(BaseInfo.INTENT_PREPARE,TakeValues.prepareText);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,intent,0);
        views.setOnClickPendingIntent(R.id.name_widget,pendingIntent);
        views.setOnClickPendingIntent(R.id.show_widget,pendingIntent);


        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            RemoteViews views = new RemoteViews(
                    context.getPackageName(),
                    R.layout.baking_app_widget
            );
            Intent intent = new Intent(context, WidgetService.class);
            views.setRemoteAdapter(R.id.widget_list, intent);
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

