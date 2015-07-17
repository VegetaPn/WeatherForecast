package weatherforecast.view;

import java.util.ArrayList;

import weatherforecast.dao.CityDao;
import weatherforecast.dao.JsonDao;
import weatherforecast.model.CityWeather;
import weatherforecast.model.City_ID;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

public class WeatherWidget extends AppWidgetProvider{

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		// TODO Auto-generated method stub
		super.onUpdate(context, appWidgetManager, appWidgetIds);
		
		
		ArrayList<City_ID> list=CityDao.getIDByName("º£µí");
		CityWeather cityWeather = JsonDao.getCityWeatherbyCityID(list.get(0).getId()+"");
		String result = cityWeather.getCity()+"\n"+cityWeather.getCityid()+"\n"
				+cityWeather.getDate_y()+"\n"+cityWeather.getIndex()+"\n"
				+cityWeather.getIndex_co()+"\n"+cityWeather.getIndex_d()+"\n"
				+cityWeather.getIndex_tr()+"\n"+cityWeather.getIndex_uv()+"\n"
				+cityWeather.getIndex_xc()+"\n"+cityWeather.getTemp1()+"\n"
				+cityWeather.getWeather1()+"\n"+cityWeather.getWeek()+"\n"
				+cityWeather.getWind1()+"\n";
		RemoteViews rViews=new RemoteViews(context.getPackageName(), R.layout.widget_layout);
		rViews.setTextViewText(R.id.widgetTextview, result);
		
		
		AppWidgetManager aManager=AppWidgetManager.getInstance(context);
		
		ComponentName cName=new ComponentName(context, WeatherWidget.class);
		
		Intent intent=new Intent( );
		intent.setClass(context, WeatherMainActivity.class);
		PendingIntent pIntent=PendingIntent.getActivity(context, 0, intent, 0);
		rViews.setOnClickPendingIntent(R.id.widgetTextview, pIntent);
		aManager.updateAppWidget(cName, rViews);
		
	}

	@Override
	public void onDeleted(Context context, int[] appWidgetIds) {
		// TODO Auto-generated method stub
		super.onDeleted(context, appWidgetIds);
	}

	@Override
	public void onDisabled(Context context) {
		// TODO Auto-generated method stub
		super.onDisabled(context);
	}

	@Override
	public void onEnabled(Context context) {
		// TODO Auto-generated method stub
		super.onEnabled(context);
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		super.onReceive(context, intent);
	}
	
	

}
