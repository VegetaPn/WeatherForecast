package weatherforecast.view;
/*
 * 4*3С���
 * 
 * by�Ͻ���
 * 
 */

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import weatherforecast.dao.CityDao;
import weatherforecast.dao.JsonDao;
import weatherforecast.model.CityWeather;
import weatherforecast.model.City_ID;
import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.provider.AlarmClock;
import android.widget.RemoteViews;

public class WeatherWidgetB extends AppWidgetProvider{
	
	private  Timer timer;
	private Context context;//����handler
	private String oldtime;
	@SuppressLint("SimpleDateFormat") @Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		// TODO Auto-generated method stub
		super.onUpdate(context, appWidgetManager, appWidgetIds);
		this.context=context;
		
		//����romoteviews
		RemoteViews  rViews=new RemoteViews(context.getPackageName(), R.layout.widget_layout_big);
		
		ArrayList<City_ID> list=CityDao.getIDByName("����");
		CityWeather cityWeather = JsonDao.getCityWeatherbyCityID(list.get(0).getId()+"");
		
		if(cityWeather!=null)
		{
			rViews.setTextViewText(R.id.bigwidgetTextviewtemp, cityWeather.getTemp1());
			rViews.setTextViewText(R.id.bigwidgetTextviewtempdes, cityWeather.getWeather1());
			rViews.setTextViewText(R.id.bigwidgetTextviewlocation, cityWeather.getCity());
			rViews.setTextViewText(R.id.bigwidgetTextviewtomorrowtemp, cityWeather.getTemp2());
			rViews.setTextViewText(R.id.bigwidgetTextviewtomorrowtemp2, cityWeather.getTemp3());
			rViews.setTextViewText(R.id.bigwidgetTextviewtomorrowtemp3, cityWeather.getTemp4());
			
		}
		
		Date now = new Date(); 
		SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");//��õ�ǰʱ��
		String time = dateFormat.format( now ); 
		oldtime=new String(time);
		rViews.setTextViewText(R.id.bigwidgetTextviewtime,time);
		
		dateFormat= new SimpleDateFormat("MM");
		String month = dateFormat.format( now );
		dateFormat= new SimpleDateFormat("dd");
		String day = dateFormat.format( now );
		
		dateFormat= new SimpleDateFormat("EEEE");//���ڼ�
		String date = month+"��"+day+"��  "+dateFormat.format( now );
		rViews.setTextViewText(R.id.bigwidgetTextviewdate,date);
		
		
		//�������������
		
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 2);
		String week2 = new DateFormatSymbols().getShortWeekdays()[cal.get(Calendar.DAY_OF_WEEK)];
		rViews.setTextViewText(R.id.bigwidgetTextviewtomorrow2, week2);
		
		cal.add(Calendar.DATE, 3);
		String week3 = new DateFormatSymbols().getShortWeekdays()[cal.get(Calendar.DAY_OF_WEEK)];
		rViews.setTextViewText(R.id.bigwidgetTextviewtomorrow3, week3);
		
		//����AppWidgetManager  ��������ؼ�
		AppWidgetManager aManager=AppWidgetManager.getInstance(context);
		ComponentName cName=new ComponentName(context, WeatherWidgetB.class);
		
		//��������¼�
		Intent intent=new Intent( );
		intent.setClass(context, WeatherMainActivity.class);
		PendingIntent pIntent=PendingIntent.getActivity(context, 0, intent, 0);
		rViews.setOnClickPendingIntent(R.id.imageViewwidgetbigtoday, pIntent);
		rViews.setOnClickPendingIntent(R.id.imageViewwidgetbigtomorrow, pIntent);
		rViews.setOnClickPendingIntent(R.id.imageViewwidgetbigtomorrow2, pIntent);
		rViews.setOnClickPendingIntent(R.id.imageViewwidgetbigtomorrow3, pIntent);
		rViews.setOnClickPendingIntent(R.id.bigwidgetTextviewtemp, pIntent);
		rViews.setOnClickPendingIntent(R.id.bigwidgetTextviewtempdes, pIntent);
		rViews.setOnClickPendingIntent(R.id.bigwidgetTextviewtomorrow, pIntent);
		rViews.setOnClickPendingIntent(R.id.bigwidgetTextviewtomorrowtemp, pIntent);
		rViews.setOnClickPendingIntent(R.id.bigwidgetTextviewtomorrow2, pIntent);
		rViews.setOnClickPendingIntent(R.id.bigwidgetTextviewtomorrowtemp2, pIntent);
		rViews.setOnClickPendingIntent(R.id.bigwidgetTextviewtomorrow3, pIntent);
		rViews.setOnClickPendingIntent(R.id.bigwidgetTextviewtomorrowtemp3, pIntent);
				
		pIntent=PendingIntent.getActivity(context, 0, new Intent(AlarmClock.ACTION_SET_ALARM), 0);
		rViews.setOnClickPendingIntent(R.id.bigwidgetTextviewtime, pIntent);
		rViews.setOnClickPendingIntent(R.id.bigwidgetTextviewdate, pIntent);
		
		aManager.updateAppWidget(cName, rViews);
		
		final Handler handler =new Handler()
		{
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				if(msg.what==0x123)
				{
					RemoteViews rViews=new RemoteViews(WeatherWidgetB.this.context.getPackageName(), R.layout.widget_layout_big);
					Date now = new Date(); 
					SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");//��õ�ǰʱ��
					String time = dateFormat.format( now ); 
					oldtime=new String(time);
					rViews.setTextViewText(R.id.bigwidgetTextviewtime,time);
					
					dateFormat= new SimpleDateFormat("MM");
					String month = dateFormat.format( now );
					dateFormat= new SimpleDateFormat("dd");
					String day = dateFormat.format( now );
					
					dateFormat= new SimpleDateFormat("EEEE");//���ڼ�
					String date = month+"��"+day+"��  "+dateFormat.format( now );
					rViews.setTextViewText(R.id.bigwidgetTextviewdate,date);
										
					//�������������					
					Calendar cal = Calendar.getInstance();
					cal.add(Calendar.DATE, 2);
					String week2 = new DateFormatSymbols().getShortWeekdays()[cal.get(Calendar.DAY_OF_WEEK)];
					rViews.setTextViewText(R.id.bigwidgetTextviewtomorrow2, week2);
					
					cal.add(Calendar.DATE, 3);
					String week3 = new DateFormatSymbols().getShortWeekdays()[cal.get(Calendar.DAY_OF_WEEK)];
					rViews.setTextViewText(R.id.bigwidgetTextviewtomorrow3, week3);
					AppWidgetManager aManager=AppWidgetManager.getInstance(WeatherWidgetB.this.context);
					ComponentName cName=new ComponentName(WeatherWidgetB.this.context, WeatherWidgetB.class);
					aManager.updateAppWidget(cName, rViews);
					
				}
				
			}
			
		};
		
		timer=new Timer();
		timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Date now = new Date(); 
				SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");//���Է�����޸����ڸ�ʽ
				String time = dateFormat.format( now ); 
				
				if(!oldtime.equals(time))
				{
					
					handler.sendEmptyMessage(0x123);
				}
				
				
			}
		}, 0, 1000);
		
	}

}
