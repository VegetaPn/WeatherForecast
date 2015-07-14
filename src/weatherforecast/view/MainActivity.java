package weatherforecast.view;

import java.io.IOException;
import java.util.ArrayList;

import com.baidu.mapapi.utils.i;

import weatherforecast.dao.*;
import weatherforecast.model.CityWeather;
import weatherforecast.model.City_ID;
import weatherforecast.util.CreateDB;
import android.os.Bundle;
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
 * 测试用，此界面的一切代买和控件可删或改
 */
public class MainActivity extends Activity {

	private EditText editText1;
	private TextView textView2;
	private Button button1;
	private CityWeather cityWeather;
	private Button button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CreateDB myDbHelper = new CreateDB(this);  
       // myDbHelper = new CreateDB(this);  
   
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
        textView2 = (TextView) findViewById(R.id.textView2);
        button2=(Button) findViewById(R.id.button2);
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
        
        
        
        button2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i=new Intent();
				i.setClass(MainActivity.this, AddCityActivity.class);
				startActivity(i);
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
