package weatherforecast.view;

import java.io.IOException;
import java.util.ArrayList;


import com.baidu.mapapi.utils.i;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.umeng.analytics.MobclickAgent;


import weatherforecast.dao.*;
import weatherforecast.model.CityWeather;
import weatherforecast.service.MyLocationListener;
import weatherforecast.service.NotiService;
import weatherforecast.model.City_ID;
import weatherforecast.util.CreateDB;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.content.Intent;
import android.database.SQLException;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RemoteViews.RemoteView;
import android.widget.TextView;

/*
 * 主界面
 */
public class MainActivity extends Activity {

	private CityWeather cityWeather;
	private EditText editTextInputCityName;
	private TextView textViewShowMessage;
	private Button btn_searchWeather;
	private Button btn_location;
	private Button btn_addCity;
	public LocationClient mLocationClient = null;							// 定位服务客户端的声明，勿删
	public MyLocationListener myListener = new MyLocationListener();		// 勿删


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CreateDB myDbHelper = new CreateDB(this); 
        
        /*以下为定位客户端的生成代码*/
        /*不要删*/
        mLocationClient = new LocationClient(getApplicationContext());     	//声明LocationClient类
        LocationClientOption option = new LocationClientOption();
        option.setIsNeedAddress(true);
        mLocationClient.setLocOption(option);
        mLocationClient.registerLocationListener( myListener );				//注册监听函数
        mLocationClient.start();
        /*以上*/
   
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
     
        setContentView(R.layout.activity_main);
        
        editTextInputCityName = (EditText) findViewById(R.id.editTextInputCityName);
        btn_searchWeather = (Button) findViewById(R.id.btn_searchWeather);
        btn_location = (Button) findViewById(R.id.btn_location);
        btn_addCity = (Button) findViewById(R.id.btn_addCity);
        textViewShowMessage = (TextView) findViewById(R.id.textViewShowMessage);
        btn_searchWeather.setOnClickListener(new WeatherSearchListener());
        btn_location.setOnClickListener(new LocationButtonListener());
        btn_addCity.setOnClickListener(new AddCityButtonListener());
    }

    public void onPause() {
    	super.onPause();
    	MobclickAgent.onPause(this);
    	}
    
    /* (non-Javadoc)
	 * @see android.app.Activity#onResume()
	 */
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		NotiService noti = new NotiService(MainActivity.this);
		noti.showNotify("消息标题", "消息内容啦啦啦啦啦啦啦");
		MobclickAgent.onResume(MainActivity.this);
	}



	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
  
    
    /**
     * 查询按钮的监听器类
     * @author 延昊南
     *
     */
    private class WeatherSearchListener implements OnClickListener {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			String city = editTextInputCityName.getEditableText().toString();
			ArrayList<City_ID> list=CityDao.getIDByName(city);
			if(list.size()!=0){
				JsonDaoPro.getWeatherJSON(list.get(0).getId()+"");
//				cityWeather = JsonDao.getCityWeatherbyCityID(list.get(0).getId()+"");
//				String result = cityWeather.getCity()+"\n"+cityWeather.getCityid()+"\n"
//						+cityWeather.getDate_y()+"\n"+cityWeather.getIndex()+"\n"
//						+cityWeather.getIndex_co()+"\n"+cityWeather.getIndex_d()+"\n"
//						+cityWeather.getIndex_tr()+"\n"+cityWeather.getIndex_uv()+"\n"
//						+cityWeather.getIndex_xc()+"\n"+cityWeather.getTemp1()+"\n"
//						+cityWeather.getWeather1()+"\n"+cityWeather.getWeek()+"\n"
//						+cityWeather.getWind1()+"\n";
//				textViewShowMessage.setText(result);
				
				
				
			}
			
		}
    	
    }
    
    
    /**
     * 定位按钮 的监听器类
     * @author 延昊南
     */
    private class LocationButtonListener implements OnClickListener {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			mLocationClient.requestLocation();
			textViewShowMessage.setText("城市名："+myListener.getCityName()
					+"\n区县名："+myListener.getDistrictName()
					+"\nErrcode: "+myListener.getErrcode());
		}
    	
    }
    
    /**
     * 添加城市按钮的监听器
     *
     */
    private class AddCityButtonListener implements OnClickListener {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Intent i=new Intent();
			i.setClass(MainActivity.this, AddCityActivity.class);
			startActivity(i);
		}
    	
    }
}
