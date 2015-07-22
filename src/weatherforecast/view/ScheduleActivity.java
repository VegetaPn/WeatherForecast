package weatherforecast.view;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.TimeZone;

import com.umeng.analytics.MobclickAgent;


import weatherforecast.service.GetDateTime;
import weatherforecast.util.ScheduleDBHelper;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnCreateContextMenuListener;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Toast;


public class ScheduleActivity extends Activity {

	private ScheduleDBHelper dbHelper = new ScheduleDBHelper(ScheduleActivity.this,"ScheduleDB");
	private SQLiteDatabase db;
	private TextView textView1;
	private TextView textView2;
	private ImageButton imageButton;
	//private TableLayout tableLayout; 
	private ListView listView;
	private ArrayList<HashMap<String,String>> ar;
	//得到ListView选中条目的行数，默认从0开始
	//private int index = 0;
	private SimpleAdapter adapter;
	
	private TableLayout tableLayout; 
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

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_schedule);
		textView1 = (TextView) findViewById(R.id.nowDate);
		textView2 = (TextView) findViewById(R.id.time);
		//显示当前日期与时间
		GetDateTime gdt = new GetDateTime();
		textView1.setText(gdt.stringDate());
		textView2.setText(gdt.stringTime());
		imageButton = (ImageButton) findViewById(R.id.imageButton);
		//tableLayout = (TableLayout) findViewById(R.id.tableLayout);
		listView = (ListView) findViewById(R.id.list);
		//初始化ListView的第一条条目内容
		ar = new ArrayList<HashMap<String,String>>();
		HashMap<String,String> map = new HashMap();
		map.put("id", "ID");
		map.put("时间", "时间");
		map.put("日程", "日程");
		map.put("地点", "地点");
		ar.add(map);
		//将ar与R.layout.item中的参数进行适配，最后两个参数指定数据一一对应的关系
		adapter = new SimpleAdapter(ScheduleActivity.this, ar, R.layout.item, new String[]{"id","时间","日程","地点"}, new int[]{R.id.scheID,R.id.time,R.id.content,R.id.place});
		listView.setAdapter(adapter);
		
		//将数据库中已有日程数据读取出来并显示在ListView中
		dbHelper.getReadableDatabase();
		db = dbHelper.getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from schedule", null);
		while(cursor.moveToNext()) {
			String sTime = cursor.getString(1);
			sTime = sTime.substring(sTime.length()-5,sTime.length());
			sTime = sTime.replaceAll("-",":");
			String id = cursor.getString(0);
			String time = sTime;
			String schedule = cursor.getString(2);
			String place = cursor.getString(3);
			addRow(id, schedule, place, time);
		}
		cursor.close();
		
		//添加新的日程信息，跳转到AddScheduleActivity
		imageButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent();
				i.setClass(ScheduleActivity.this, AddScheduleActivity.class);
				startActivityForResult(i, 101);  
			}
		});
		
		listView.setOnCreateContextMenuListener(new OnCreateContextMenuListener() {
			//长按条目弹出菜单
			public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {
                menu.setHeaderTitle("选择操作");
                menu.add(0, 0, 0, "删除该日程");
			}
        });
		
		/*listView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				//Toast.makeText(ScheduleActivity.this, "删除", Toast.LENGTH_LONG).show();
				index = arg2;
				
				//弹出AlertDialog
				AlertDialog.Builder builder = new Builder(ScheduleActivity.this);
                builder.setMessage("是否删除此日程？");
                builder.setTitle("删除提示");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener(){

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub
						String delID = ar.get(index).get("id").toString();
						String delID = index + "";
						String delTime = ar.get(index).get("时间").toString();
						delTime = delTime.replaceAll(":","-");
						
						//得到数据库中存储的时间
						final Calendar cal = Calendar.getInstance();
						cal.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
						String mYear = String.valueOf(cal.get(Calendar.YEAR));
						String mMonth = String.valueOf(cal.get(Calendar.MONTH) + 1);
						String mDay = String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
						if(mMonth.length() == 1)
							mMonth = "0" + mMonth;
						if(mDay.length() == 1)
							mDay = "0" + mDay;
						delTime = mYear + "-" + mMonth + "-" + mDay + "-" + delTime;
						String delSchedule = ar.get(index).get("日程").toString();
						String delPlace = ar.get(index).get("地点").toString();
						SQLiteDatabase db = dbHelper.getReadableDatabase();  
				
					    //从表中删除指定的一条数据  
						db.delete("schedule","id = ?",new String[]{delID});
						db.close();
						//从ListView中删除相应日程并更新ListView
						ar.remove(index);
						adapter.notifyDataSetChanged();
						listView.invalidate();
					}
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener(){

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub
						arg0.dismiss();
					}
                });
                builder.create().show();
				return true;
			}
		});*/
	}

	//删除日程时选中菜单选项后弹出AlertDialog
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
        //info.id得到listview中选择的条目绑定的id
        final int delID = (int) info.id;
        int position = info.position;
        final String delid = ar.get(position).get("id").toString();
        System.out.println(delID);
        switch (item.getItemId()) {
        case 0:
            //System.out.println("删除"+info.id);
			
			//弹出AlertDialog
			AlertDialog.Builder builder = new Builder(ScheduleActivity.this);
            builder.setMessage("是否删除此日程？");
            builder.setTitle("删除提示");
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener(){

				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					// TODO Auto-generated method stub
					
					SQLiteDatabase db = dbHelper.getReadableDatabase();  
			
				    //从表中删除指定的一条数据  
					db.delete("schedule","id = ?",new String[]{delid + ""});
					db.close();
					//从ListView中删除相应日程并更新ListView
					ar.remove(delID);
					adapter.notifyDataSetChanged();
					listView.invalidate();
				}
            });
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener(){

				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					// TODO Auto-generated method stub
					arg0.dismiss();
				}
            });
            builder.create().show();
            return true;
        default:
        	return super.onContextItemSelected(item);
        }
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	//向ListView中添加一项
	private void addRow(String id, String schedule, String place, String time){
		//if((schedule != "") || (place != "")){
		HashMap<String,String> map = new HashMap();
		map.put("id", id);
		map.put("时间", time);
		map.put("日程", schedule);
		map.put("地点", place);
		ar.add(map);
		
		//将ar与R.layout.item中的参数进行适配，最后两个参数指定数据一一对应的关系
		adapter = new SimpleAdapter(ScheduleActivity.this, ar, R.layout.item, new String[]{"id","时间","日程","地点"}, new int[]{R.id.scheID,R.id.time,R.id.content,R.id.place});
		listView.setAdapter(adapter);
		//}
	}
		
	// 回调方法，从AddScheduleActivity回来的时候会执行这个方法  
    @Override  
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {  
    	super.onActivityResult(requestCode, resultCode, data); 
    	try {
			Bundle bundle = data.getExtras();
			String id = bundle.getString("id");
			String time = bundle.getString("time");
			String schedule = bundle.getString("schedule");
			String place = bundle.getString("place");
			addRow(id, schedule, place, time);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			// do nothing
		}
    }  
}

