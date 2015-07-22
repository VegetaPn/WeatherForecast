package weatherforecast.view;
/*
 * 4*1 widget插件
 * 
 * by邢金涛
 * 
 */
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Handler;

import javax.security.auth.PrivateCredentialPermission;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

import weatherforecast.dao.CityDao;
import weatherforecast.dao.JsonDao;
import weatherforecast.model.CityWeather;
import weatherforecast.model.City_ID;
import weatherforecast.service.MyLocationListener;
import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.provider.AlarmClock;
import android.text.format.Time;
import android.view.TextureView;
import android.widget.RemoteViews;
import android.widget.TextView;

public class WeatherWidget extends AppWidgetProvider{

	
	private  Timer timer;
	private Context context;//用于handler
	private String oldtime;
	private LocationClient mLocationClient;
	@SuppressLint("SimpleDateFormat") @Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		// TODO Auto-generated method stub
		super.onUpdate(context, appWidgetManager, appWidgetIds);
		this.context=context;
		
			 
		 new Thread() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				super.run();
				MyLocationListener myListener = new MyLocationListener();
				LocationClientOption option = new LocationClientOption(); 
			     option.setIsNeedAddress(true);
			     mLocationClient.setLocOption(option);
			     mLocationClient.registerLocationListener( myListener );				//注册监听函数
			     mLocationClient.start(); 
			     mLocationClient.requestLocation();
			     
//			     while(myListener.getErrcode()==-1)
//			     {
//			    	 //mLocationClient.start(); 
//				     //mLocationClient.requestLocation();
			    	System.out.println("你丫等会");
//			     };
			     //System.out.println("妥了");
			     System.out.println(myListener.getErrcode());
			}
			 
		 }.start();
		 
	     
		ArrayList<City_ID> list=CityDao.getIDByName("海淀");
		CityWeather cityWeather = JsonDao.getCityWeatherbyCityID(list.get(0).getId()+"");
		//设置remoteVeiw
		RemoteViews rViews=new RemoteViews(context.getPackageName(), R.layout.widget_layout);
		if(cityWeather!=null)
		{
			rViews.setTextViewText(R.id.textViewWidgetTemp, cityWeather.getTemp1());
			rViews.setTextViewText(R.id.widgetTextviewCity,cityWeather.getCity());
			rViews.setTextViewText(R.id.textViewWidgetWeather,cityWeather.getWeather1());
			rViews.setTextViewText(R.id.textViewWidgetIndex,cityWeather.getIndex());
			
		}
				
		Date now = new Date(); 
		SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");//获得当前时间
		String time = dateFormat.format( now ); 
		oldtime=new String(time);
		rViews.setTextViewText(R.id.widgetTextviewTime,time);
		
		dateFormat= new SimpleDateFormat("EEEE");//星期几
		time = dateFormat.format( now );
		rViews.setTextViewText(R.id.textViewWidgetTime2,time);
		//设置日期
		dateFormat= new SimpleDateFormat("MM");
		String month = dateFormat.format( now );
		dateFormat= new SimpleDateFormat("dd");
		String day = dateFormat.format( now );
		rViews.setTextViewText(R.id.textViewWidgetDate,month+"月"+day+"日");
		
		//声明AppWidgetManager  更新桌面控件
		AppWidgetManager aManager=AppWidgetManager.getInstance(context);
		ComponentName cName=new ComponentName(context, WeatherWidget.class);
		//创建点击事件
		Intent intent=new Intent( );
		intent.setClass(context, WeatherMainActivity.class);
		PendingIntent pIntent=PendingIntent.getActivity(context, 0, intent, 0);
		rViews.setOnClickPendingIntent(R.id.imageView_widgetlayout, pIntent);
		
		pIntent=PendingIntent.getActivity(context, 0, new Intent(AlarmClock.ACTION_SET_ALARM), 0);
		rViews.setOnClickPendingIntent(R.id.widgetTextviewTime, pIntent);
		rViews.setOnClickPendingIntent(R.id.textViewWidgetTime2, pIntent);
		rViews.setOnClickPendingIntent(R.id.textViewWidgetDate, pIntent);
		aManager.updateAppWidget(cName, rViews);
		
		
		//定时刷新时钟
		 final android.os.Handler handler=new android.os.Handler(){

				@Override
				public void handleMessage(Message msg) {
					// TODO Auto-generated method stub
					if(msg.what==0x123)
					{
						RemoteViews rViews=new RemoteViews(WeatherWidget.this.context.getPackageName(), R.layout.widget_layout);
						Date now = new Date(); 
						SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");//可以方便地修改日期格式
						String time = dateFormat.format( now ); 
						oldtime=new String(time);
						rViews.setTextViewText(R.id.widgetTextviewTime,time);
						AppWidgetManager aManager=AppWidgetManager.getInstance(WeatherWidget.this.context);
						ComponentName cName=new ComponentName(WeatherWidget.this.context, WeatherWidget.class);
						aManager.updateAppWidget(cName, rViews);
						
					}
					
					super.handleMessage(msg);
				}};
		
		timer=new Timer();
		timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Date now = new Date(); 
				SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");//可以方便地修改日期格式
				String time = dateFormat.format( now ); 
				
				if(!oldtime.equals(time))
				{
					
					handler.sendEmptyMessage(0x123);
				}
				
				
			}
		}, 0, 1000);
		
		
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
