package weatherforecast.view;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import android.os.Bundle;
import android.app.Activity;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.SimpleAdapter;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class TripActivity extends Activity {

	private TextView textView1;
	private TextView textView2;
	private GridView gridView;
	private ImageButton imageButton;
	private int num = 3;
	private TableLayout tableLayout;
	private static String mYear;
	private static String mMonth;
	private static String mDay;
	private static int hour;
	private static int minute;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_trip);
		textView1 = (TextView) findViewById(R.id.date);
		textView2 = (TextView) findViewById(R.id.time);
		textView1.setText(stringDate());
		textView2.setText(stringTime());
		imageButton = (ImageButton) findViewById(R.id.imageButton);
		tableLayout = (TableLayout) findViewById(R.id.tableLayout);
		
		/*
		//准备要向GridView中添加的数据条目
		List<Map<String,Object>> items = new ArrayList<Map<String,Object>>();
		String[] trip = {"时间","当日行程","+"};
		for(int i = 0;i < 3;i++){
			Map<String,Object> item = new HashMap<String,Object>();
			item.put("textItem", trip[i]);
			items.add(item);
		}
		//实例化一个适配器
		SimpleAdapter adapter = new SimpleAdapter(this,items,R.layout.grid_item,new String[]{"textItem"},new int[]{R.id.text_item});
		//获得GridView实例
		gridView = (GridView) findViewById(R.id.gridview);
		//为GridView设置适配器
		gridView.setAdapter(adapter);
		*/
		
		imageButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				addStraightRow();
				addTripRow();
				num++;
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public String stringDate() {
		final Calendar cal = Calendar.getInstance();
		cal.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
		mYear = String.valueOf(cal.get(Calendar.YEAR));
		mMonth = String.valueOf(cal.get(Calendar.MONTH) + 1);
		mDay = String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
		hour = cal.get(Calendar.HOUR_OF_DAY);
		minute = cal.get(Calendar.MINUTE);
		return mYear + "/" + mMonth + "/" + mDay;
	}
	
	public String stringTime() {
		final Calendar cal = Calendar.getInstance();
		cal.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
		hour = cal.get(Calendar.HOUR_OF_DAY);
		minute = cal.get(Calendar.MINUTE);
		return hour + ":" + minute;
	}
	
	private void addStraightRow() {
		TableRow tableRow = new TableRow(this);
		TextView textView = new TextView(this);
		tableRow.addView(textView);
		//tableLayout.addView(tableRow);
	}
	
	private void addTripRow() {
		TableRow tableRow = new TableRow(this);
		TextView textView1 = new TextView(this);
		TextView textView2 = new TextView(this);
		TextView textView3 = new TextView(this);
		int length = tableLayout.getMeasuredWidth();
		tableRow.setGravity(Gravity.CENTER);
		textView1.setWidth(length/3);
		textView2.setWidth(length/3);
		textView3.setWidth(length/3);
		textView1.setText(String.valueOf(" " + num));
		textView2.setText(" 休息");
		textView3.setText(" 寝室");
		tableRow.addView(textView1);
		tableRow.addView(textView2);
		tableRow.addView(textView3);
		tableLayout.addView(tableRow);
	}
	
}

