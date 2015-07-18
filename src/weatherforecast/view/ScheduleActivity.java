package weatherforecast.view;

import weatherforecast.service.GetDateTime;
import weatherforecast.util.ScheduleDBHelper;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;


public class ScheduleActivity extends Activity {

	private ScheduleDBHelper dbHelper = new ScheduleDBHelper(ScheduleActivity.this,"ScheduleDB");
	private SQLiteDatabase db;
	private TextView textView1;
	private TextView textView2;
	private GridView gridView;
	private ImageButton imageButton;
	private TableLayout tableLayout; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_schedule);
		textView1 = (TextView) findViewById(R.id.nowDate);
		textView2 = (TextView) findViewById(R.id.time);
		GetDateTime gdt = new GetDateTime();
		textView1.setText(gdt.stringDate());
		textView2.setText(gdt.stringTime());
		imageButton = (ImageButton) findViewById(R.id.imageButton);
		tableLayout = (TableLayout) findViewById(R.id.tableLayout);
		dbHelper.getReadableDatabase();
		db = dbHelper.getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from schedule", null);
		while(cursor.moveToNext()) {
			String sTime = cursor.getString(1);
			sTime = sTime.substring(sTime.length()-5,sTime.length());
			sTime = sTime.replaceAll("-",":");
			String time = sTime;
			String schedule = cursor.getString(2);
			String place = cursor.getString(3);
			addRow(schedule, place, time);
		}
		cursor.close();
		
		/*
		//׼��Ҫ��GridView����ӵ�������Ŀ
		List<Map<String,Object>> items = new ArrayList<Map<String,Object>>();
		String[] trip = {"ʱ��","�����г�","+"};
		for(int i = 0;i < 3;i++){
			Map<String,Object> item = new HashMap<String,Object>();
			item.put("textItem", trip[i]);
			items.add(item);
		}
		//ʵ����һ��������
		SimpleAdapter adapter = new SimpleAdapter(this,items,R.layout.grid_item,new String[]{"textItem"},new int[]{R.id.text_item});
		//���GridViewʵ��
		gridView = (GridView) findViewById(R.id.gridview);
		//ΪGridView����������
		gridView.setAdapter(adapter);
		*/
		
		imageButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent();
				i.setClass(ScheduleActivity.this, AddScheduleActivity.class);
				startActivityForResult(i, 101);  
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	//��TableLayout�����һ��
	private void addRow(String schedule, String place, String time){
		TableRow tableRow = new TableRow(this);
		TextView textView1 = new TextView(this);
		TextView textView2 = new TextView(this);
		TextView textView3 = new TextView(this);
		TextView textView4 = new TextView(this);
		WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);  
		int length = wm.getDefaultDisplay().getWidth();//��Ļ��� 
		//int length = tableLayout.getMeasuredWidth();
		//System.out.println(length+"");
		//tableRow.setGravity(Gravity.CENTER);
		textView1.setWidth(length/4);
		textView2.setWidth(length/4);
		textView3.setWidth(length/4);
		textView4.setWidth(length/4);
		
		if((schedule != "") || (place != "")){
			textView1.setText(time);
			textView2.setText(schedule);
			textView3.setText(place);	
			textView4.setText("    ");	
		}
		
		tableRow.addView(textView1);
		tableRow.addView(textView2);
		tableRow.addView(textView3);
		tableRow.addView(textView4);
		tableLayout.addView(tableRow);

	}
	// �ص��������ӵڶ���ҳ�������ʱ���ִ���������  
    @Override  
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {  
    	super.onActivityResult(requestCode, resultCode, data); 
    	try {
			Bundle bundle = data.getExtras();
			String time = bundle.getString("time");
			String schedule = bundle.getString("schedule");
			String place = bundle.getString("place");
			addRow(schedule, place, time);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			// do nothing
		}
    }  
}

