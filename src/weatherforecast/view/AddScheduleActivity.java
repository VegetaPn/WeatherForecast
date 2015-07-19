package weatherforecast.view;

import java.io.File;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

import com.umeng.analytics.MobclickAgent;

import weatherforecast.util.ScheduleDBHelper;
import weatherforecast.view.SwitchButton.OnChangeListener;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;


public class AddScheduleActivity extends Activity {

	private ScheduleDBHelper dbHelper = new ScheduleDBHelper(AddScheduleActivity.this,"ScheduleDB");
	private SQLiteDatabase db;
	private static String mYear;
	private static String mMonth;
	private static String mDay;
	public Integer pickerHour = 0;
	public Integer pickerMinute = 0;
	public String time = "";
	public String schedule = "";
	public String place = "";
	private TimePicker timePicker;
	private int hour;
	private int minute;
	private EditText contentText;
	private EditText placeText;
	private Button cancelButton;
	private Button OKButton;
	private int id = 0;
	private String remind = "";
	private String dateTime = "";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_schedule);
		SwitchButton sb = (SwitchButton) findViewById(R.id.wiperSwitch);  
        sb.setOnChangeListener(new OnChangeListener() {  
              
            @Override  
            public void onChange(SwitchButton sb, boolean state) {  
                // TODO Auto-generated method stub  
                Log.d("switchButton", state ? "开":"关");  
                Toast.makeText(AddScheduleActivity.this, state ? "开":"关", Toast.LENGTH_SHORT).show();  
            }  
        });  
        
        timePicker = (TimePicker) findViewById(R.id.timePiker);
        final Calendar cal = Calendar.getInstance();
		cal.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
		hour = cal.get(Calendar.HOUR_OF_DAY);
		minute = cal.get(Calendar.MINUTE);
        timePicker.setCurrentHour(hour);
        timePicker.setCurrentMinute(minute);
        
        contentText = (EditText) findViewById(R.id.inputTitle);
        placeText = (EditText) findViewById(R.id.inputPlace);
        cancelButton = (Button) findViewById(R.id.ret);
        OKButton = (Button) findViewById(R.id.add);
        dbHelper.getReadableDatabase();
        
        cancelButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent();
				i.setClass(AddScheduleActivity.this,ScheduleActivity.class);
				startActivity(i);
			}
		});
        OKButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				pickerHour = timePicker.getCurrentHour();
				pickerMinute = timePicker.getCurrentMinute();
				String pHour = pickerHour.toString();
				String pMinute = pickerMinute.toString();
				if(pHour.length() == 1)
					pHour = "0" + pHour;
				if(pMinute.length() == 1)
					pMinute = "0" + pMinute;
				time = pHour + ":" + pMinute;
				schedule = contentText.getText().toString();
		        place = placeText.getText().toString();
		
				//向数据库中存储数据
				final Calendar cal = Calendar.getInstance();
				cal.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
				mYear = String.valueOf(cal.get(Calendar.YEAR));
				mMonth = String.valueOf(cal.get(Calendar.MONTH) + 1);
				mDay = String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
				if(mMonth.length() == 1)
					mMonth = "0" + mMonth;
				if(mDay.length() == 1)
					mDay = "0" + mDay;
				db = dbHelper.getReadableDatabase();
				Cursor cur = db.rawQuery("select * from schedule", null);
				if(cur.getCount() == 0)
					id = 1;
				else{
					Cursor cursor = db.rawQuery("select * from schedule limit 1 offset (select count(*) - 1 from schedule)",null);
					while(cursor.moveToNext()) {
						id = Integer.valueOf(cursor.getString(0)) + 1;
					}
					cursor.close();
				}
				dateTime = mYear + "-" + mMonth + "-" + mDay + "-" + pHour + "-" + pMinute;
				remind = "时间：" + pHour + ":" + pMinute + "\n" + "日程：" + schedule + "\n" + "地点：" + place;  
				ContentValues values = new ContentValues();
				values.put("id", id);
				values.put("date", dateTime);
				values.put("content", schedule);
				values.put("place", place);
				db.insert("schedule ", null, values);
				
				//返回结果到ScheduleActivity
		        Intent i = new Intent();
				Bundle bundle = new Bundle();
				bundle.putString("time", time);
				bundle.putString("schedule", schedule);
				bundle.putString("place", place);
				i.putExtras(bundle);
				setResult(-1, i);
				
				long timemilis = convertDate(dateTime);
				setAlarm(remind, timemilis);
				
				finish();
			}
		});
    }  

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void writeFileData(String fileName,String message){ 
		
		try{
			FileOutputStream fout = openFileOutput(fileName, MODE_APPEND);
	        byte [] bytes = message.getBytes(); 
	        fout.write(bytes); 
	        fout.close(); 
	    }catch(Exception e){ 
	    	   e.printStackTrace(); 
	    }
	}    
	
	public String getSDPath() {  
        File sdDir = null;  
        boolean sdCardExist = Environment.getExternalStorageState().equals(  
                android.os.Environment.MEDIA_MOUNTED);          // 判断sd卡是否存在  
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory();  // 获取根目录  
            return sdDir.toString();
        }
        else {
        	String notExist = "There is not a SDcard!";
        	System.out.println(notExist);
        	return notExist;
        }  
    }  
	
	private void setAlarm(String msg, long timemilis) {
    	final AlarmManager am = (AlarmManager)this.getSystemService(ALARM_SERVICE); 
        Intent intent = new Intent();  
        intent.setAction("schedule");  
        intent.putExtra("msg", msg);  
        final PendingIntent pi = PendingIntent.getBroadcast(this, id, intent, PendingIntent.FLAG_UPDATE_CURRENT);  
        final long time = System.currentTimeMillis();  
         
        am.set(AlarmManager.RTC_WAKEUP, timemilis, pi); 
	}
    
	private void cancelAlarm() {
    	//am.cancel(pi);
    }
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onResume(this);
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPause(this);
	}
	private long convertDate(String dt) {
    	Calendar c = Calendar.getInstance();
    	try {
			c.setTime(new SimpleDateFormat("yyyy-MM-dd-HH-mm").parse(dt));
			return c.getTimeInMillis();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return 0;
	}
}