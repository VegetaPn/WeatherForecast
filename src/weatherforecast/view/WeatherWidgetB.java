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

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

import weatherforecast.dao.CityDao;
import weatherforecast.dao.JsonDao;
import weatherforecast.dao.JsonDaoPro;
import weatherforecast.model.CityWeather;
import weatherforecast.model.City_ID;
import weatherforecast.service.MyLocationListener;
import android.R.integer;
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
	private LocationClient mLocationClient;
	private MyLocationListener myListener;
	private boolean iflocate;
	@SuppressLint("SimpleDateFormat") @Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		// TODO Auto-generated method stub
		super.onUpdate(context, appWidgetManager, appWidgetIds);
		this.context=context;
		
		myListener = new MyLocationListener();
		LocationClientOption option = new LocationClientOption(); 
		option.setIsNeedAddress(true);
		mLocationClient=new LocationClient(context);
		mLocationClient.setLocOption(option);
		mLocationClient.registerLocationListener( myListener );				//ע���������
		mLocationClient.start(); 
		mLocationClient.requestLocation();
		iflocate=false;	
		
		//����romoteviews
		RemoteViews  rViews=new RemoteViews(context.getPackageName(), R.layout.widget_layout_big);
		
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
		
		
		dateFormat= new SimpleDateFormat("yyyy-MM-dd");
		String scheduleString=dateFormat.format( now );
		int scheduledate=ScheduleActivity.getTotalSchedule(context, scheduleString);
		
		if(scheduledate!=0)
		{
		     rViews.setTextViewText(R.id.bigwidgetTextviewschedule, "��������"+scheduledate+"���г�");
		}else
		{
			rViews.setTextViewText(R.id.bigwidgetTextviewschedule, "�����������г�");
		}
		
		//�������������
		
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 2);
		String week2 = new DateFormatSymbols().getShortWeekdays()[cal.get(Calendar.DAY_OF_WEEK)];
		rViews.setTextViewText(R.id.bigwidgetTextviewtomorrow2, week2);
		
		cal.add(Calendar.DATE, 1);
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
		
		intent.setClass(context, ScheduleActivity.class);
		System.out.println(scheduleString);
		pIntent=PendingIntent.getActivity(context, 0, intent, 0);
		rViews.setOnClickPendingIntent(R.id.bigwidgetTextviewschedule, pIntent);
				
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
				RemoteViews rViews=new RemoteViews(WeatherWidgetB.this.context.getPackageName(), R.layout.widget_layout_big);
				Date now = new Date(); 
				
				SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd");
				String scheduleString=dateFormat.format( now );
				
				int scheduledate=ScheduleActivity.getTotalSchedule(WeatherWidgetB.this.context, scheduleString);
				if(scheduledate!=0)
				{
				    rViews.setTextViewText(R.id.bigwidgetTextviewschedule, "��������"+scheduledate+"���г�");
				}else
				{
					rViews.setTextViewText(R.id.bigwidgetTextviewschedule, "�����������г�");
				}
				if(iflocate==false&&myListener.getErrcode()==161)
				{
					// 
					CityWeather cityWeather = null;
					System.out.println(myListener.getDistrictName());
					System.out.println(myListener.getDistrictName());
					String name=myListener.getDistrictName();
					City_ID list=CityDao.getCurrentCityID(name);					
					if(list == null){
						System.out.println("��ȡ����λ��");
						name=myListener.getCityName();
						list=CityDao.getCurrentCityID(name);
					}
					String jsonString=JsonDaoPro.getWeatherJSON(list.getId()+"");
					if(jsonString!=null)
					{
						cityWeather = JsonDaoPro.parseJson(jsonString);
						
					}
					if(cityWeather!=null)
					{
						Calendar cal = Calendar.getInstance();
						int nowID,tomorrowID,tomorrowID2,tomorrowID3;
						//System.out.println("Сʱ����"+cal.get(Calendar.HOUR_OF_DAY));
						if(5<cal.get(Calendar.HOUR_OF_DAY)&&cal.get(Calendar.HOUR_OF_DAY)<21)
						{
							nowID=WeatherWidgetB.this.context.getResources().getIdentifier("d"+cityWeather.getCode_d1(),"drawable", WeatherWidgetB.this.context.getPackageName());
							tomorrowID=WeatherWidgetB.this.context.getResources().getIdentifier("d"+cityWeather.getCode_d2(),"drawable", WeatherWidgetB.this.context.getPackageName());
							tomorrowID2=WeatherWidgetB.this.context.getResources().getIdentifier("d"+cityWeather.getCode_d3(),"drawable", WeatherWidgetB.this.context.getPackageName());
							tomorrowID3=WeatherWidgetB.this.context.getResources().getIdentifier("d"+cityWeather.getCode_d4(),"drawable", WeatherWidgetB.this.context.getPackageName());
						}else
						{
							
							nowID=WeatherWidgetB.this.context.getResources().getIdentifier("n"+cityWeather.getCode_n1(),"drawable", WeatherWidgetB.this.context.getPackageName());
							tomorrowID=WeatherWidgetB.this.context.getResources().getIdentifier("n"+cityWeather.getCode_n2(),"drawable", WeatherWidgetB.this.context.getPackageName());
							tomorrowID2=WeatherWidgetB.this.context.getResources().getIdentifier("n"+cityWeather.getCode_n3(),"drawable", WeatherWidgetB.this.context.getPackageName());
							tomorrowID3=WeatherWidgetB.this.context.getResources().getIdentifier("n"+cityWeather.getCode_n4(),"drawable", WeatherWidgetB.this.context.getPackageName());
					
						}
						rViews.setImageViewResource(R.id.imageViewwidgetbigtoday, nowID);
						rViews.setImageViewResource(R.id.imageViewwidgetbigtomorrow, tomorrowID);
						rViews.setImageViewResource(R.id.imageViewwidgetbigtomorrow2, tomorrowID2);
						rViews.setImageViewResource(R.id.imageViewwidgetbigtomorrow3, tomorrowID3);
						
						rViews.setTextViewText(R.id.bigwidgetTextviewtempNow, cityWeather.getNtmp()+"�� ");
						rViews.setTextViewText(R.id.bigwidgetTextviewtemp, cityWeather.getMax1()+"��/"+cityWeather.getMin1()+"��");
						rViews.setTextViewText(R.id.bigwidgetTextviewtempdes, cityWeather.getNtxt());
						rViews.setTextViewText(R.id.bigwidgetTextviewlocation, cityWeather.getCity());
						rViews.setTextViewText(R.id.bigwidgetTextviewtomorrowtemp, cityWeather.getMax2()+"��/"+cityWeather.getMin2()+"��");
						rViews.setTextViewText(R.id.bigwidgetTextviewtomorrowtemp2, cityWeather.getMax3()+"��/"+cityWeather.getMin3()+"��");
						rViews.setTextViewText(R.id.bigwidgetTextviewtomorrowtemp3, cityWeather.getMax4()+"��/"+cityWeather.getMin4()+"��");
						iflocate=true;
					}
					
				}
				if(msg.what==0x123)
				{
					
					
					dateFormat = new SimpleDateFormat("HH:mm");//��õ�ǰʱ��
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
					
					cal.add(Calendar.DATE, 1);
					String week3 = new DateFormatSymbols().getShortWeekdays()[cal.get(Calendar.DAY_OF_WEEK)];
					rViews.setTextViewText(R.id.bigwidgetTextviewtomorrow3, week3);
					
					
				}
				
				AppWidgetManager aManager=AppWidgetManager.getInstance(WeatherWidgetB.this.context);
				ComponentName cName=new ComponentName(WeatherWidgetB.this.context, WeatherWidgetB.class);
				aManager.updateAppWidget(cName, rViews);
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
				}else
				{
					handler.sendEmptyMessage(0);
					
				}
				
				if(myListener.getErrcode()!=161&&iflocate==false)
				{
					mLocationClient.start(); 
					mLocationClient.requestLocation();
				}
			}
		}, 0, 1000);
		
	}

}
