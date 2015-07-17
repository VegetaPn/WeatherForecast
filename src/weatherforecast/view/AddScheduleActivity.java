package weatherforecast.view;

import java.util.Calendar;
import java.util.TimeZone;

import com.umeng.analytics.MobclickAgent;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View;
import android.widget.TimePicker;
import android.widget.Toast;
import weatherforecast.view.SwitchButton.OnChangeListener;  


public class AddScheduleActivity extends Activity {

	private TimePicker timePicker;
	private int hour;
	private int minute;
	
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
    }  

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onResume(AddScheduleActivity.this);
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPause(AddScheduleActivity.this);
	}
}
