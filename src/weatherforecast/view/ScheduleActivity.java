package weatherforecast.view;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

import com.umeng.analytics.MobclickAgent;


import weatherforecast.service.GetDateTime;
import weatherforecast.util.ScheduleDBHelper;
import android.R.integer;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.AlertDialog.Builder;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract.Contacts.Data;
import android.text.format.DateFormat;
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
import android.widget.Button;
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
	private ImageButton imageButton;
	private ImageButton button;
	private static final int msgKey1 = 1;
	//private TableLayout tableLayout; 
	private ListView listView;
	private ArrayList<HashMap<String,String>> ar;
	//�õ�ListViewѡ����Ŀ��������Ĭ�ϴ�0��ʼ
	//private int index = 0;
	private SimpleAdapter adapter;
	 
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
		
		imageButton = (ImageButton) findViewById(R.id.btn_add_schedule);
		button = (ImageButton) findViewById(R.id.ret);
		//tableLayout = (TableLayout) findViewById(R.id.tableLayout);
		listView = (ListView) findViewById(R.id.list);
		//��ʼ��ListView�ĵ�һ����Ŀ����
		ar = new ArrayList<HashMap<String,String>>();
		HashMap<String,String> map = new HashMap();
		map.put("id", "ID");
		map.put("ʱ��", "ʱ��");
		map.put("�ճ�", "�ճ�");
		map.put("�ص�", "�ص�");
		ar.add(map);
		//��ar��R.layout.item�еĲ����������䣬�����������ָ������һһ��Ӧ�Ĺ�ϵ
		adapter = new SimpleAdapter(ScheduleActivity.this, ar, R.layout.item, new String[]{"id","ʱ��","�ճ�","�ص�"}, new int[]{R.id.scheID,R.id.time,R.id.content,R.id.place});
		listView.setAdapter(adapter);
		
		Intent it = getIntent();
		final String nowDate;
		String tmp = it.getStringExtra("dateString");
		if(tmp == null){
			final Calendar cal = Calendar.getInstance();
			cal.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
			String mYear = String.valueOf(cal.get(Calendar.YEAR));
			String mMonth = String.valueOf(cal.get(Calendar.MONTH) + 1);
			String mDay = String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
			if(mMonth.length() == 1)
				mMonth = "0" + mMonth;
			if(mDay.length() == 1)
				mDay = "0" + mDay;
			nowDate = mYear + "-" + mMonth + "-" + mDay;
		} else {
			nowDate = tmp;
		}
		//���ݴ������ڽ����ݿ��������ճ����ݶ�ȡ��������ʾ��ListView��
		initListView(nowDate);
		
		//����µ��ճ���Ϣ����ת��AddScheduleActivity
		imageButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent();
				i.setClass(ScheduleActivity.this, AddScheduleActivity.class);
				i.putExtra("dateStr", nowDate);
				startActivityForResult(i, 101);  
			}
		});
		
		//���ذ�ť���ص�������
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				onBackPressed();
			}
		});
		
		listView.setOnCreateContextMenuListener(new OnCreateContextMenuListener() {
			//������Ŀ�����˵�
			public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {
                menu.setHeaderTitle("ѡ�����");
                //menu.add(0, 0, 0, "�޸��ճ�");
                menu.add(0, 1, 1, "ɾ���ճ�");
			}
        });	
	}

	//���ݴ������ڽ����ݿ��������ճ����ݶ�ȡ��������ʾ��ListView��
	public void initListView(String dateStr) {
		dbHelper.getReadableDatabase();
		db = dbHelper.getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from schedule where date LIKE '%" + dateStr + "%'", null);
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
	}
	
	//ɾ���ճ�ʱѡ�в˵�ѡ��󵯳�AlertDialog
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
        //info.id�õ�listview��ѡ�����Ŀ�󶨵�id
        final int delID = (int) info.id;
        int position = info.position;
        final String delid = ar.get(position).get("id").toString();
        System.out.println(delID);
        switch (item.getItemId()) {
        /*case 0:
        	System.out.println("�޸��ճ�");
        	String scheContent = ar.get(position).get("�ճ�").toString();
        	String schePlace = ar.get(position).get("�ص�").toString();
        	Intent it = new Intent();
        	it.setClass(ScheduleActivity.this, AddScheduleActivity.class);
        	Bundle bundle = new Bundle();
			bundle.putString("Schedule", scheContent);
			bundle.putString("Place", scheContent);
			it.putExtras(bundle);
			startActivity(it);*/
        case 1:
			//����AlertDialog
			AlertDialog.Builder builder = new Builder(ScheduleActivity.this);
            builder.setMessage("�Ƿ�ɾ�����ճ̣�");
            builder.setTitle("ɾ����ʾ");
            builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener(){

				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					// TODO Auto-generated method stub
					
					SQLiteDatabase db = dbHelper.getReadableDatabase();  
			
				    //�ӱ���ɾ��ָ����һ������  
					db.delete("schedule","id = ?",new String[]{delid + ""});
					db.close();
					//��ListView��ɾ����Ӧ�ճ̲�����ListView
					ar.remove(delID);
					adapter.notifyDataSetChanged();
					listView.invalidate();
					
					/* ����ɾ���ճ̴��룺ɾ���ճ̺�ȡ������ */
					cancelAlarm(delID);
				}
            });
            builder.setNegativeButton("ȡ��", new DialogInterface.OnClickListener(){

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

	private void cancelAlarm(int id) {
    	//am.cancel(pi);
		Intent intent = new Intent();  
        intent.setAction("schedule");
		final AlarmManager am = (AlarmManager)this.getSystemService(ALARM_SERVICE); 
		final PendingIntent pi = PendingIntent.getBroadcast(this, id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		am.cancel(pi);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	//��ListView�����һ��
	private void addRow(String id, String schedule, String place, String time){
		//if((schedule != "") || (place != "")){
		HashMap<String,String> map = new HashMap();
		map.put("id", id);
		map.put("ʱ��", time);
		map.put("�ճ�", schedule);
		map.put("�ص�", place);
		ar.add(map);
		
		//��ar��R.layout.item�еĲ����������䣬�����������ָ������һһ��Ӧ�Ĺ�ϵ
		adapter = new SimpleAdapter(ScheduleActivity.this, ar, R.layout.item, new String[]{"id","ʱ��","�ճ�","�ص�"}, new int[]{R.id.scheID,R.id.time,R.id.content,R.id.place});
		listView.setAdapter(adapter);
	}
		
	// �ص���������AddScheduleActivity������ʱ���ִ���������  
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
    
    //��ѯ������ճ�������һ������ΪActivity���ڶ�������Ϊ���ڣ���ʽΪyyyy-mm-dd
    public static int getTotalSchedule(Context context, String str) {
    	int count = 0;
    	ScheduleDBHelper dbHelper = new ScheduleDBHelper(context, "ScheduleDB");
    	SQLiteDatabase db = dbHelper.getReadableDatabase();  
    	Cursor cursor = db.rawQuery("select * from schedule where date LIKE '%" + str + "%'", null);
    	while(cursor.moveToNext()){
    		count++;
    	}
    	cursor.close();

    	return count;
    }
}

