package weatherforecast.view;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import weatherforecast.dao.CityDao;
import weatherforecast.dao.JsonDaoPro;
import weatherforecast.model.CityWeather;
import weatherforecast.model.City_ID;
import weatherforecast.service.MyLocationListener;
import weatherforecast.util.CreateDB;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.RemoteViews;
import android.widget.ScrollView;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

public class WeatherMainActivity extends BaseActivity {

	CreateDB myDbHelper;
	ViewPager vp;	
	ScrollView scrl;
	ArrayList<Fragment> mFragments;
	WeatherPagerAdapter adapter;
	private LocationClient mLocationClient;
	private MyLocationListener myListener;
	private  Timer timer,timer2;
	private boolean iflocate=false;
	private RemoteViews remoteViews;
	private Notification notification;
	private android.os.Handler handler;
	private NotificationManager nManager;
	private PendingIntent pIntent;
	private TimerTask task;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		myListener = new MyLocationListener();
		LocationClientOption option = new LocationClientOption(); 
		option.setIsNeedAddress(true);
		mLocationClient=new LocationClient(WeatherMainActivity.this);
		mLocationClient.setLocOption(option);
		mLocationClient.registerLocationListener( myListener );				//注册监听函数
		mLocationClient.start(); 
		mLocationClient.requestLocation();
		
		mFragments=new ArrayList<Fragment>();
		initDB();
				
		getSupportActionBar().hide();
		vp = new ViewPager(this);
		vp.setId("VP".hashCode());
		adapter = new WeatherPagerAdapter(getSupportFragmentManager(),mFragments);
		vp.setAdapter(adapter);
		vp.setOffscreenPageLimit(2);
		setViewPagerScrollSpeed(vp);
		setContentView(vp);
	
		// set the Behind View
		setBehindContentView(R.layout.menu_frame);
		getSupportFragmentManager()
		.beginTransaction()
		.replace(R.id.menu_frame, new CityListFragment(myDbHelper,getSlidingMenu()))
		.commit();
		vp.setCurrentItem(0);
		getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
		
		//通知栏
		initNotification();
		
		
	}
	
	private void setViewPagerScrollSpeed(ViewPager vp){  
        try {  
            Field mScroller = null;  
            mScroller = ViewPager.class.getDeclaredField("mScroller");  
            mScroller.setAccessible(true);   
            FixedSpeedScroller scroller = new FixedSpeedScroller( vp.getContext( ) );  
            mScroller.set( vp, scroller);  
        }catch(NoSuchFieldException e){  
              
        }catch (IllegalArgumentException e){  
              
        }catch (IllegalAccessException e){  
              
        }  
    }
	
	public void initDB() {
		// TODO Auto-generated constructor stub
		CreateDB myDbHelper = new CreateDB(this);  
   
        try { 
        	myDbHelper.createDataBase();  
        } catch (IOException ioe) {  
        	throw new Error("Unable to create database");  
        }  
        try {  
        	myDbHelper.openDataBase();  
        }catch(SQLException sqle){  
        	throw sqle;  
        }
        
        ArrayList<City_ID> list = CityDao.showicity();
		for (City_ID city : list)
			mFragments.add(new WeatherHomeFragment(city.getId(),myDbHelper,getSlidingMenu()));
	}
	
	public ViewPager getVp(){
		return this.vp;
	}
	
	public class WeatherPagerAdapter extends FragmentPagerAdapter {
		
		private ArrayList<Fragment> mFragments;
		private FragmentManager fm;
		
		public WeatherPagerAdapter(FragmentManager fm,ArrayList<Fragment> frag) {
			super(fm);
			this.mFragments=frag;
			this.fm=fm;
		}

		@Override
		public int getCount() {
			return mFragments.size();
		}

		@Override
		public Fragment getItem(int position) {
			return mFragments.get(position);
		}
		
		@Override
		public int getItemPosition(Object object) {
			// TODO Auto-generated method stub
			return PagerAdapter.POSITION_NONE;
		}

		@Override
		public Object instantiateItem(ViewGroup container,int position) {


		    Fragment fragment = (Fragment)super.instantiateItem(container,position);
		    System.out.println("instantiate:"+fragment.getTag());
		    if(fragment.getTag() != mFragments.get(position).getTag()){
			    FragmentTransaction ft =fm.beginTransaction();
			    
			    String preTag = fragment.getTag(); 
			    

			    ft.remove(fragment);
			    fragment=mFragments.get(position);

			    ft.add(container.getId(), fragment, preTag);
			    ft.attach(fragment);
			    ft.commit();
			    fm.executePendingTransactions();
		    }
		    return fragment;

		}
		
		public void remove(int index){
			FragmentTransaction ft =fm.beginTransaction();
			mFragments.remove(index);
			ArrayList<Fragment> newFragments = new ArrayList<Fragment>();
			for(Fragment f : mFragments){
				WeatherHomeFragment ff=(WeatherHomeFragment)f;
				WeatherHomeFragment newF=new WeatherHomeFragment(ff.getCityId(),myDbHelper,getSlidingMenu());
				newFragments.add(newF);
				System.out.println("remove run:oldtag-"+ff.getTag()+"newtag-"+newF.getTag());
			}
			mFragments.clear();
			mFragments.addAll(newFragments);
			notifyDataSetChanged();
		}
		
		public int getIdAt(int index){
			WeatherHomeFragment w = (WeatherHomeFragment) mFragments.get(index);
			return w.getCityId();
		}
	}
	
	public class MyViewPager extends ViewPager{

	    float preX;
		public MyViewPager(Context context) {
			super(context);
			preX = 0;
			// TODO Auto-generated constructor stub
		}

		@Override
		public boolean onInterceptTouchEvent(MotionEvent event) {
			// TODO Auto-generated method stub
			boolean res = super.onInterceptTouchEvent(event);
			if(event.getAction() == MotionEvent.ACTION_DOWN) {  
		        preX = event.getX();  
		    } else {  
		        if( Math.abs(event.getX() - preX)> 2 ) {  
		            return true;  
		        } else {  
		            preX = event.getX();  
		        }  
		    }  
		    return res; 
		}
		
		
		
	}
    
    private void initNotification(){
    	
    	
    	
    	nManager=(NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    	Intent intent=new Intent(this,WeatherMainActivity.class);
    	pIntent=PendingIntent.getActivity(WeatherMainActivity.this, 0, intent, 0);
    	notification=new Notification();
    	
    	remoteViews=new RemoteViews(getPackageName(),R.layout.notification_layout);
    	
//    	ArrayList<City_ID> list=CityDao.getIDByName("烟台");
//		CityWeather cityWeather = JsonDaoPro.parseJson(JsonDaoPro.getWeatherJSON(list.get(0).getId()+""));
    	

//		if(cityWeather!=null)
//		{
//			Calendar cal = Calendar.getInstance();
//			int nowID;
//			if(5<cal.get(Calendar.HOUR_OF_DAY)&&cal.get(Calendar.HOUR_OF_DAY)<21)
//			{
//				nowID=WeatherMainActivity.this.getResources().getIdentifier("d"+cityWeather.getCode_d1(),"drawable", WeatherMainActivity.this.getPackageName());	
//			}else
//			{
//				
//				nowID=WeatherMainActivity.this.getResources().getIdentifier("n"+cityWeather.getCode_n1(),"drawable", WeatherMainActivity.this.getPackageName());
//			}
//			remoteViews.setImageViewResource(R.id.imageViewNotification, nowID);
//			remoteViews.setTextViewText(R.id.textViewNotificationTemNow, cityWeather.getNtmp()+"℃");
//			remoteViews.setTextViewText(R.id.textViewNotificationTem, cityWeather.getMax1()+"°~"+cityWeather.getMin1()+"°");
//			remoteViews.setTextViewText(R.id.textViewNotificationLoc,cityWeather.getCity()+"   ");
//			notification.icon=nowID;
//	    	notification.tickerText="今日温度："+cityWeather.getNtmp()+"℃";
//		}
    	task=new TimerTask() {
    				
    				@Override
    				public void run() {
    					// TODO Auto-generated method stub
    					System.out.println("code"+myListener.getErrcode());
    					if(myListener.getErrcode()==161&&iflocate==false)
    					{
    						handler.sendEmptyMessage(0);
    					}else if(myListener.getErrcode()!=-1&&myListener.getErrcode()!=161)
    					{
    						mLocationClient.start(); 
    						mLocationClient.requestLocation();
    					}
    				}
    			};
		handler=new android.os.Handler()
		 {
			 @Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				
				if(msg.what==0)
				{
			    	
					CityWeather cityWeather = null;
					System.out.println(myListener.getDistrictName());
					City_ID list=CityDao.getCurrentCityID(myListener.getDistrictName());
					String jsonString=JsonDaoPro.getWeatherJSON(list.getId()+"");
					if(jsonString!=null)
					{
						cityWeather = JsonDaoPro.parseJson(jsonString);
						
					}		
					
					if(cityWeather!=null)
					{
						System.out.println("hhahhahha");
						Calendar cal = Calendar.getInstance();
						int nowID;
						if(5<cal.get(Calendar.HOUR_OF_DAY)&&cal.get(Calendar.HOUR_OF_DAY)<21)
						{
							nowID=WeatherMainActivity.this.getResources().getIdentifier("d"+cityWeather.getCode_d1(),"drawable", WeatherMainActivity.this.getPackageName());	
						}else
						{
							
							nowID=WeatherMainActivity.this.getResources().getIdentifier("n"+cityWeather.getCode_n1(),"drawable", WeatherMainActivity.this.getPackageName());
						}
						remoteViews.setImageViewResource(R.id.imageViewNotification, nowID);
						remoteViews.setTextViewText(R.id.textViewNotificationTemNow, cityWeather.getNtmp()+"℃");
						remoteViews.setTextViewText(R.id.textViewNotificationTem, cityWeather.getMax1()+"°~"+cityWeather.getMin1()+"°");
						remoteViews.setTextViewText(R.id.textViewNotificationLoc,cityWeather.getCity()+"   ");
						notification.icon=nowID;
				    	notification.tickerText="今日温度："+cityWeather.getNtmp()+"℃";
				    	notification.contentView=remoteViews;
				    	notification.contentIntent=pIntent;
				    	notification.flags |= Notification.FLAG_ONGOING_EVENT; 
				    	nManager.notify(0, notification);
				    	iflocate=true;
				    	task.cancel();
					}
					
					
				}else if(msg.what==0x123)
				{
					
					City_ID list=CityDao.getCurrentCityID(myListener.getDistrictName());
					CityWeather cityWeather = JsonDaoPro.parseJson(JsonDaoPro.getWeatherJSON(list.getId()+""));
					if(cityWeather!=null)
					{
						Calendar cal = Calendar.getInstance();
						int nowID;
						if(5<cal.get(Calendar.HOUR_OF_DAY)&&cal.get(Calendar.HOUR_OF_DAY)<21)
						{
							nowID=WeatherMainActivity.this.getResources().getIdentifier("d"+cityWeather.getCode_d1(),"drawable", WeatherMainActivity.this.getPackageName());	
						}else
						{
							
							nowID=WeatherMainActivity.this.getResources().getIdentifier("n"+cityWeather.getCode_n1(),"drawable", WeatherMainActivity.this.getPackageName());
						}
						remoteViews.setImageViewResource(R.id.imageViewNotification, nowID);
						remoteViews.setTextViewText(R.id.textViewNotificationTemNow, cityWeather.getNtmp()+"℃");
						remoteViews.setTextViewText(R.id.textViewNotificationTem, cityWeather.getMax1()+"°~"+cityWeather.getMin1()+"°");
						remoteViews.setTextViewText(R.id.textViewNotificationLoc,cityWeather.getCity()+"   ");
						notification.icon=nowID;
				    	notification.tickerText="今日温度："+cityWeather.getNtmp()+"℃";
				    	notification.contentView=remoteViews;
				    	notification.contentIntent=pIntent;
				    	notification.flags |= Notification.FLAG_ONGOING_EVENT; 
				    	nManager.notify(0, notification);
					}
				}
			}
		 };
		 
		 
	   
		timer=new Timer();
		timer.schedule(task,0, 5000);
		
		timer2=new Timer();
		timer2.schedule(new TimerTask() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				
				if(myListener.getErrcode()==161)
				{
					handler.sendEmptyMessage(0x123);
				}else if(myListener.getErrcode()!=-1&&myListener.getErrcode()!=161)
				{
					mLocationClient.start(); 
					mLocationClient.requestLocation();
				}
			}
		}, 0, 3600000);
		 
    }
}
