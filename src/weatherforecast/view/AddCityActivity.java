package weatherforecast.view;

import java.util.ArrayList;

import weatherforecast.dao.CityDao;
import weatherforecast.model.City_ID;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AddCityActivity  extends Activity{

	private LinearLayout linearLayout;
    private EditText edit;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_city);
		linearLayout=(LinearLayout) findViewById(R.id.addCityL1);
        edit=(EditText) findViewById(R.id.editTextInputCityName);
        
        TextView t = new TextView(this);
        t.setText("当前定位城市");
        linearLayout.addView(t);
        t = new TextView(this);
        t.setText("热门城市列表");
        linearLayout.addView(t);    
        
        edit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				linearLayout.removeAllViews();
				
			}
		});
        
        edit.addTextChangedListener(new TextWatcher() {
		
		@Override
		public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
			// TODO Auto-generated method stub
			linearLayout.removeAllViews();
		    TextView tv = new TextView(AddCityActivity.this);
		    @SuppressWarnings("unused")
			CityDao cityDao=new CityDao();
	        ArrayList<City_ID> list=CityDao.getIDByName(edit.getText().toString());
	        for(int i=0;i<list.size();i++)
	        {
	        	City_ID city_ID=list.get(i);
	        	String city=""+city_ID.getNamecn()+","+city_ID.getDistrictcn()+","+city_ID.getProvcn();
	        	tv = new TextView(AddCityActivity.this);
	        	tv.setText(city);
	        	
	        	 tv.setOnClickListener(new OnClickListener() {
	 				
	 				@Override
	 				public void onClick(View arg0) {
	 					// TODO Auto-generated method stub
	 					Intent i=new Intent();
	 					i.setClass(AddCityActivity.this, testSucceed.class);
	 					startActivity(i);
	 					System.out.println("niyadianle");
	 					
	 				}
	 			});
	        	 
	        	 linearLayout.addView(tv);
	        }
		    
			
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

}
