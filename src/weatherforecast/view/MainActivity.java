package weatherforecast.view;

import java.io.IOException;
import java.util.ArrayList;

<<<<<<< HEAD
import com.baidu.mapapi.utils.i;
=======
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
>>>>>>> 124d86df54fd81900445218644717ad173699b32

import weatherforecast.dao.*;
import weatherforecast.model.CityWeather;
import weatherforecast.service.MyLocationListener;
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
import android.widget.TextView;

/*
 * 主界面，
 * 测试用，此界面的部分代码和控件可删或改
 */
public class MainActivity extends Activity {

	private EditText editText1;
	private TextView textView2;
	private Button button1;
	private Button btn_location;
	private CityWeather cityWeather;
<<<<<<< HEAD
	private Button button2;
=======
	public LocationClient mLocationClient = null;							// 定位服务客户端的声明，勿删
	public MyLocationListener myListener = new MyLocationListener();		// 勿删
	private StringBuffer sb = null;
>>>>>>> 124d86df54fd81900445218644717ad173699b32

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
        
        editText1 = (EditText) findViewById(R.id.editText1);
        button1 = (Button) findViewById(R.id.button1);
        btn_location = (Button) findViewById(R.id.button2);
        textView2 = (TextView) findViewById(R.id.textView2);
<<<<<<< HEAD
        button2=(Button) findViewById(R.id.button2);
=======
        
        mLocationClient.start();
        
>>>>>>> 124d86df54fd81900445218644717ad173699b32
        button1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String city = editText1.getEditableText().toString();
				ArrayList<City_ID> list=CityDao.getIDByName(city);
				
				cityWeather = JsonDao.getCityWeatherbyCityID(list.get(1).getId()+"");
				String result = cityWeather.getCity()+"\n"+cityWeather.getCityid()+"\n"
						+cityWeather.getDate_y()+"\n"+cityWeather.getIndex()+"\n"
						+cityWeather.getIndex_co()+"\n"+cityWeather.getIndex_d()+"\n"
						+cityWeather.getIndex_tr()+"\n"+cityWeather.getIndex_uv()+"\n"
						+cityWeather.getIndex_xc()+"\n"+cityWeather.getTemp1()+"\n"
						+cityWeather.getWeather1()+"\n"+cityWeather.getWeek()+"\n"
						+cityWeather.getWind1()+"\n";
				textView2.setText(result);
			}
		});
        
<<<<<<< HEAD
        
        
        button2.setOnClickListener(new OnClickListener() {
=======
        /*
         * 目前不会非阻塞来显示结果，等到学会后再改
         */
        btn_location.setOnClickListener(new OnClickListener() {
>>>>>>> 124d86df54fd81900445218644717ad173699b32
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
<<<<<<< HEAD
				Intent i=new Intent();
				i.setClass(MainActivity.this, AddCityActivity.class);
				startActivity(i);
=======
		        mLocationClient.requestLocation();
		        textView2.setText("城市名："+myListener.getCityName()
						+"\n区县名："+myListener.getDistrictName()
						+"\nErrcode: "+myListener.getErrcode());
>>>>>>> 124d86df54fd81900445218644717ad173699b32
			}
		});
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
  
}
