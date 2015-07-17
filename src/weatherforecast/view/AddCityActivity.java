package weatherforecast.view;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.umeng.analytics.MobclickAgent;

import weatherforecast.dao.CityDao;
import weatherforecast.model.City_ID;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class AddCityActivity  extends Activity{

	private LinearLayout linearLayout;
    private EditText edit;
    private ListView listView;
    private ArrayList<City_ID> list;
    private AddCityActivity activity;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_city);
		activity = this;
		linearLayout=(LinearLayout) findViewById(R.id.addCityL1);
        edit=(EditText) findViewById(R.id.editTextInputCityName);
        listView=(ListView) findViewById(R.id.listViewshowcity);
        
        listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				//System.out.println(list.get(arg2).getId());
				MobclickAgent.onEvent(AddCityActivity.this,"add a city");
				if(CityDao.insertCity(list.get(arg2))){//加入到收藏城市列表
					activity.finish();//插入成功
				}else{
					Toast toast=Toast.makeText(AddCityActivity.this, "该城市已经被添加到收藏列表，无需再次添加",Toast.LENGTH_SHORT);
					toast.show();
					
				}
				//System.out.println("dianji"+list.get(arg2).getNamecn());
				
			}
		});
        
//        TextView t = new TextView(this);
//        t.setText("当前定位城市");
//        linearLayout.addView(t);
//        t = new TextView(this);
//        t.setText("热门城市列表");
//        linearLayout.addView(t);    
        
        edit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub		
				
			}
		});
        
        edit.addTextChangedListener(new TextWatcher() {
		
		@Override
		public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
			// TODO Auto-generated method stub
		    @SuppressWarnings("unused")
			CityDao cityDao=new CityDao();
	        list=CityDao.getIDByName(edit.getText().toString());
	        Comparator<City_ID> comparator = new Comparator<City_ID>(){  
	            public int compare(City_ID s1, City_ID s2) {  
	                //按id排序 
	            return s1.getId()-s2.getId();  }};  
	            Collections.sort(list,comparator);  
	        String[] showCityStrings=new String[list.size()]; 
	        for(int i=0;i<list.size();i++)
	        {
	        	final City_ID city_ID=list.get(i);
	        	String city=""+city_ID.getNamecn()+","+city_ID.getDistrictcn()+","+city_ID.getProvcn();
	        	showCityStrings[i]=city;
	        }
	         
	        ArrayAdapter<String> adapter=new ArrayAdapter<String>(AddCityActivity.this,R.layout.viewitem, showCityStrings);
	        listView.setAdapter(adapter);			
		}
		
		@Override
		public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
				int arg3) {
			// TODO Auto-generated method stub	
		}
		
		@Override
		public void afterTextChanged(Editable arg0) {
			// TODO Auto-generated method stub
			    
		}
	});
    
         
    }
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onResume(AddCityActivity.this);
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPause(AddCityActivity.this);
	}

}
