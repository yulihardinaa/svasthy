package sv.ugm.komsi.svasthy;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

/**
 * Implementation of App Widget functionality.
 */
public class Mood_widget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.activity_main);
//        views.setTextViewText(R.id.appwidget_text, widgetText);

//        Intent intentUpdate = new Intent(context, Mood_widget.class);
//        intentUpdate.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
//        int[] idArray = new int[]{appWidgetId};
//        intentUpdate.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,idArray);
//
//
//        Intent intent = new Intent(context,MainActivity.class);
//        PendingIntent pendingIntent=PendingIntent.getActivity(context,0,intent,0);
//        views.setOnClickPendingIntent(R.id.mood_1,pendingIntent);
//        views.setOnClickPendingIntent(R.id.mood_2,pendingIntent);
//        views.setOnClickPendingIntent(R.id.mood_3,pendingIntent);
//        views.setOnClickPendingIntent(R.id.mood_4,pendingIntent);
//        views.setOnClickPendingIntent(R.id.mood_5,pendingIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
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

