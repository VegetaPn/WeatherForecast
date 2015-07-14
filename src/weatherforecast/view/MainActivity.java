package weatherforecast.view;

import java.io.IOException;
import java.util.ArrayList;


import com.baidu.mapapi.utils.i;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;


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
 * ������
 */
public class MainActivity extends Activity {

	private CityWeather cityWeather;
	private EditText editTextInputCityName;
	private TextView textViewShowMessage;
	private Button btn_searchWeather;
	private Button btn_location;
	private Button btn_addCity;
	public LocationClient mLocationClient = null;							// ��λ����ͻ��˵���������ɾ
	public MyLocationListener myListener = new MyLocationListener();		// ��ɾ


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CreateDB myDbHelper = new CreateDB(this); 
        
        /*����Ϊ��λ�ͻ��˵����ɴ���*/
        /*��Ҫɾ*/
        mLocationClient = new LocationClient(getApplicationContext());     	//����LocationClient��
        LocationClientOption option = new LocationClientOption();
        option.setIsNeedAddress(true);
        mLocationClient.setLocOption(option);
        mLocationClient.registerLocationListener( myListener );				//ע���������
        mLocationClient.start();
        /*����*/
   
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
  
    
    /**
     * ��ѯ��ť�ļ�������
     * @author �����
     *
     */
    private class WeatherSearchListener implements OnClickListener {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			String city = editTextInputCityName.getEditableText().toString();
			ArrayList<City_ID> list=CityDao.getIDByName(city);
			if(list.size()!=0){
				
				cityWeather = JsonDao.getCityWeatherbyCityID(list.get(0).getId()+"");
				String result = cityWeather.getCity()+"\n"+cityWeather.getCityid()+"\n"
						+cityWeather.getDate_y()+"\n"+cityWeather.getIndex()+"\n"
						+cityWeather.getIndex_co()+"\n"+cityWeather.getIndex_d()+"\n"
						+cityWeather.getIndex_tr()+"\n"+cityWeather.getIndex_uv()+"\n"
						+cityWeather.getIndex_xc()+"\n"+cityWeather.getTemp1()+"\n"
						+cityWeather.getWeather1()+"\n"+cityWeather.getWeek()+"\n"
						+cityWeather.getWind1()+"\n";
				textViewShowMessage.setText(result);
				
			}
			
		}
    	
    }
    
    
    /**
     * ��λ��ť �ļ�������
     * @author �����
     */
    private class LocationButtonListener implements OnClickListener {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			mLocationClient.requestLocation();
			textViewShowMessage.setText("��������"+myListener.getCityName()
					+"\n��������"+myListener.getDistrictName()
					+"\nErrcode: "+myListener.getErrcode());
		}
    	
    }
    
    /**
     * ��ӳ��а�ť�ļ�����
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
