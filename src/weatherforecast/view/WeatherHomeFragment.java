package weatherforecast.view;
import weatherforecast.view.R;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.app.Activity;
import android.content.SharedPreferences;
import android.database.SQLException;
import android.graphics.Color;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.TimeZone;

import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.LineChartView;

import com.baoyz.widget.PullRefreshLayout;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import weatherforecast.dao.*;
import weatherforecast.model.CityWeather;
import weatherforecast.util.CreateDB;

public class WeatherHomeFragment extends Fragment {

	private int cityId;
	CreateDB myDbHelper;
	ScrollView scrl;
	PullRefreshLayout refreshView;
	String[] weekOfDays = {"","����", "��һ", "�ܶ�", "����", "����", "����", "����"};
	SlidingMenu slide;
	
	public WeatherHomeFragment() {
		super();
		// TODO Auto-generated constructor stub
	}

	public WeatherHomeFragment(int i,CreateDB db,SlidingMenu slide){
		super();
		this.cityId=i;
		this.myDbHelper=db;
		this.slide=slide;
	}
	
	public int getCityId(){
		return cityId;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		if (savedInstanceState != null)
			cityId = savedInstanceState.getInt("index");
		View v = inflater.inflate(R.layout.weather_home_fragment, container, false);
		
		ImageButton menu=(ImageButton)v.findViewById(R.id.imageButton1);
		menu.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				slide.showMenu();
			}
		});
		
		refreshView = (PullRefreshLayout)v.findViewById(R.id.swipeRefreshLayout);
		refreshView.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshView.setRefreshing(false);
                    }
                }, 1500);
            }
        });

		initView(v);
		return v;
	}
	
	private void initChart(View v,CityWeather city){

		LineChartView chart1 = (LineChartView)v.findViewById(R.id.chart1);
		List<Line> lines = new ArrayList<Line>();
			List<PointValue> values = new ArrayList<PointValue>();
	        values.add(new PointValue(0, Integer.parseInt(city.getMax1())));
	        values.add(new PointValue(1, Integer.parseInt(city.getMax2())));
	        values.add(new PointValue(2, Integer.parseInt(city.getMax3())));
	        values.add(new PointValue(3, Integer.parseInt(city.getMax4())));
	        values.add(new PointValue(4, Integer.parseInt(city.getMax5())));
	        values.add(new PointValue(5, Integer.parseInt(city.getMax6())));
	        values.add(new PointValue(6, Integer.parseInt(city.getMax7())));
	    ArrayList<Float> t=new ArrayList<Float>();
	    for(PointValue p:values){
	    	t.add(p.getY());
	    }
	    Collections.sort(t);
        Line line = new Line(values);
        line.setColor(Color.rgb(253, 185, 51));
        line.setShape(ValueShape.CIRCLE);
        line.setCubic(true);
        line.setFilled(false);
        line.setHasLabels(true);
        line.setHasLabelsOnlyForSelected(false);
        line.setHasLines(true);
        line.setHasPoints(true);
	    line.setStrokeWidth(2);
        lines.add(line);
        LineChartData data = new LineChartData(lines);
	    data.setAxisXBottom(null);
        data.setAxisYLeft(null);
        data.setBaseValue(Float.NEGATIVE_INFINITY);
        chart1.setLineChartData(data);
        chart1.setZoomEnabled(false);
        chart1.setViewportCalculationEnabled(false);
        
        final Viewport vv = new Viewport(chart1.getMaximumViewport());
        vv.bottom = t.get(0)-2;
        vv.top = t.get(6)+2;
        vv.left = 0;
        vv.right = 6;
        chart1.setMaximumViewport(vv);
        chart1.setCurrentViewport(vv);
        
        LineChartView chart2 = (LineChartView)v.findViewById(R.id.chart2);
		List<Line> lines2 = new ArrayList<Line>();
	        List<PointValue> values2 = new ArrayList<PointValue>();
	        values2.add(new PointValue(0, Integer.parseInt(city.getMin1())));
	        values2.add(new PointValue(1, Integer.parseInt(city.getMin2())));
	        values2.add(new PointValue(2, Integer.parseInt(city.getMin3())));
	        values2.add(new PointValue(3, Integer.parseInt(city.getMin4())));
	        values2.add(new PointValue(4, Integer.parseInt(city.getMin5())));
	        values2.add(new PointValue(5, Integer.parseInt(city.getMin6())));
	        values2.add(new PointValue(6, Integer.parseInt(city.getMin7())));
        ArrayList<Float> t2=new ArrayList<Float>();
	    for(PointValue p:values2){
	    	t2.add(p.getY());
	    }
	    Collections.sort(t2);
	    Line line2 = new Line(values2);
	    line2.setColor(Color.rgb(0, 191, 255));
	    line2.setShape(ValueShape.CIRCLE);
	    line2.setCubic(true);
	    line2.setFilled(false);
	    line2.setHasLabels(true);
	    line2.setHasLabelsOnlyForSelected(false);
	    line2.setHasLines(true);
	    line2.setHasPoints(true);
	    line2.setStrokeWidth(2);
	    lines2.add(line2);
	    LineChartData data2 = new LineChartData(lines2);
	    data2.setAxisXBottom(null);
        data2.setAxisYLeft(null);
        data2.setBaseValue(Float.NEGATIVE_INFINITY);
        chart2.setLineChartData(data2);
        chart2.setZoomEnabled(false);
        chart2.setViewportCalculationEnabled(false);
        
        final Viewport vv2 = new Viewport(chart1.getMaximumViewport());
        vv2.bottom = t2.get(0)-1;
        vv2.top = t2.get(6)+2;
        vv2.left = 0;
        vv2.right = 6;
        chart2.setMaximumViewport(vv2);
        chart2.setCurrentViewport(vv2);
	    
	}
	
	private void initView(View v){
		TextView t;
		TextView t1,t2;
		CityWeather cityWeather=JsonDaoPro.parseJson(getJson(cityId));
		refreshView = (PullRefreshLayout) v.findViewById(R.id.swipeRefreshLayout);
		refreshView.setRefreshStyle(PullRefreshLayout.STYLE_WATER_DROP);
		scrl=(ScrollView)v.findViewById(R.id.scrollView1);
		int screenHeight = getActivity().getWindowManager().getDefaultDisplay().getHeight();
		LinearLayout imagePanel;
		Button aqi = (Button)v.findViewById(R.id.button_aqi);
		imagePanel = (LinearLayout)v.findViewById(R.id.imagePanel);
		LayoutParams p = imagePanel.getLayoutParams();
		LayoutParams b = aqi.getLayoutParams();
		int[] position=new int[2];
		aqi.getLocationOnScreen(position);
		p.height=screenHeight/2;
		System.out.println("image height:"+p.height);
		System.out.println("btn y:"+position[1]);
		System.out.println("btn height:"+aqi.getMeasuredHeight());
		imagePanel.setLayoutParams(p);
		
		initChart(v,cityWeather);
		
		TextView textCityName=(TextView)v.findViewById(R.id.textCityname);
		TextView lastRefresh=(TextView)v.findViewById(R.id.textLastRefresh);
		TextView nowT=(TextView)v.findViewById(R.id.text_now_T);
		TextView now_max_T=(TextView)v.findViewById(R.id.text_now_maxT);
		TextView now_min_T=(TextView)v.findViewById(R.id.text_now_minT);
		TextView nowChinese=(TextView)v.findViewById(R.id.text_now_weatherChinese);
		TextView nowDescrip=(TextView)v.findViewById(R.id.text_now_descrip);
		ImageView nowWeather=(ImageView)v.findViewById(R.id.image_now_weather);
		ImageView imageTomorrow=(ImageView)v.findViewById(R.id.image_tomorrow);
		TextView textTomorrowT=(TextView)v.findViewById(R.id.text_tomorrow_T);
		TextView textTomorrowWind=(TextView)v.findViewById(R.id.text_tomorrow_wind);
		TextView textTomorrowDescrip=(TextView)v.findViewById(R.id.text_tomorrow_descrip);
		RelativeLayout btn7_1=(RelativeLayout)v.findViewById(R.id.btn_7_1);
		RelativeLayout btn7_2=(RelativeLayout)v.findViewById(R.id.btn_7_2);
		RelativeLayout btn7_3=(RelativeLayout)v.findViewById(R.id.btn_7_3);
		RelativeLayout btn7_4=(RelativeLayout)v.findViewById(R.id.btn_7_4);
		RelativeLayout btn7_5=(RelativeLayout)v.findViewById(R.id.btn_7_5);
		RelativeLayout btn7_6=(RelativeLayout)v.findViewById(R.id.btn_7_6);
		RelativeLayout btn7_7=(RelativeLayout)v.findViewById(R.id.btn_7_7);
		
		TextView text7_1_ch=(TextView)v.findViewById(R.id.text_7_1_ch);
		ImageView image7_1_max=(ImageView)v.findViewById(R.id.image_7_1_max);
		TextView text7_1_max=(TextView)v.findViewById(R.id.text_7_1_max);
		TextView text7_1_min=(TextView)v.findViewById(R.id.text_7_1_min);
		ImageView image7_1_min=(ImageView)v.findViewById(R.id.image_7_1_min);
		TextView text7_1_date=(TextView)v.findViewById(R.id.text_7_1_date);
		
		TextView text7_2_ch=(TextView)v.findViewById(R.id.text_7_2_ch);
		ImageView image7_2_max=(ImageView)v.findViewById(R.id.image_7_2_max);
		TextView text7_2_max=(TextView)v.findViewById(R.id.text_7_2_max);
		TextView text7_2_min=(TextView)v.findViewById(R.id.text_7_2_min);
		ImageView image7_2_min=(ImageView)v.findViewById(R.id.image_7_2_min);
		TextView text7_2_date=(TextView)v.findViewById(R.id.text_7_2_date);
		
		TextView text7_3_ch=(TextView)v.findViewById(R.id.text_7_3_ch);
		ImageView image7_3_max=(ImageView)v.findViewById(R.id.image_7_3_max);
		TextView text7_3_max=(TextView)v.findViewById(R.id.text_7_3_max);
		TextView text7_3_min=(TextView)v.findViewById(R.id.text_7_3_min);
		ImageView image7_3_min=(ImageView)v.findViewById(R.id.image_7_3_min);
		TextView text7_3_date=(TextView)v.findViewById(R.id.text_7_3_date);
		
		TextView text7_4_ch=(TextView)v.findViewById(R.id.text_7_4_ch);
		ImageView image7_4_max=(ImageView)v.findViewById(R.id.image_7_4_max);
		TextView text7_4_max=(TextView)v.findViewById(R.id.text_7_4_max);
		TextView text7_4_min=(TextView)v.findViewById(R.id.text_7_4_min);
		ImageView image7_4_min=(ImageView)v.findViewById(R.id.image_7_4_min);
		TextView text7_4_date=(TextView)v.findViewById(R.id.text_7_4_date);
		
		TextView text7_5_ch=(TextView)v.findViewById(R.id.text_7_5_ch);
		ImageView image7_5_max=(ImageView)v.findViewById(R.id.image_7_5_max);
		TextView text7_5_max=(TextView)v.findViewById(R.id.text_7_5_max);
		TextView text7_5_min=(TextView)v.findViewById(R.id.text_7_5_min);
		ImageView image7_5_min=(ImageView)v.findViewById(R.id.image_7_5_min);
		TextView text7_5_date=(TextView)v.findViewById(R.id.text_7_5_date);
		
		TextView text7_6_ch=(TextView)v.findViewById(R.id.text_7_6_ch);
		ImageView image7_6_max=(ImageView)v.findViewById(R.id.image_7_6_max);
		TextView text7_6_max=(TextView)v.findViewById(R.id.text_7_6_max);
		TextView text7_6_min=(TextView)v.findViewById(R.id.text_7_6_min);
		ImageView image7_6_min=(ImageView)v.findViewById(R.id.image_7_6_min);
		TextView text7_6_date=(TextView)v.findViewById(R.id.text_7_6_date);
		
		TextView text7_7_ch=(TextView)v.findViewById(R.id.text_7_7_ch);
		ImageView image7_7_max=(ImageView)v.findViewById(R.id.image_7_7_max);
		TextView text7_7_max=(TextView)v.findViewById(R.id.text_7_7_max);
		TextView text7_7_min=(TextView)v.findViewById(R.id.text_7_7_min);
		ImageView image7_7_min=(ImageView)v.findViewById(R.id.image_7_7_min);
		TextView text7_7_date=(TextView)v.findViewById(R.id.text_7_7_date);
		
		RelativeLayout zhishu1=(RelativeLayout)v.findViewById(R.id.zhishu1);
		RelativeLayout zhishu2=(RelativeLayout)v.findViewById(R.id.zhishu2);
		RelativeLayout zhishu3=(RelativeLayout)v.findViewById(R.id.zhishu3);
		RelativeLayout zhishu4=(RelativeLayout)v.findViewById(R.id.zhishu4);
		RelativeLayout zhishu5=(RelativeLayout)v.findViewById(R.id.zhishu5);
		RelativeLayout zhishu6=(RelativeLayout)v.findViewById(R.id.zhishu6);
		
		ImageView image_zhishu1=(ImageView)v.findViewById(R.id.image_zhishu_1);
		TextView text_zhishu1_name=(TextView)v.findViewById(R.id.text_zhishu_1_name);
		TextView text_zhishu1_content=(TextView)v.findViewById(R.id.text_zhishu_1_content);
		
		ImageView image_zhishu2=(ImageView)v.findViewById(R.id.image_zhishu_2);
		TextView text_zhishu2_name=(TextView)v.findViewById(R.id.text_zhishu_2_name);
		TextView text_zhishu2_content=(TextView)v.findViewById(R.id.text_zhishu_2_content);
		
		ImageView image_zhishu3=(ImageView)v.findViewById(R.id.image_zhishu_3);
		TextView text_zhishu3_name=(TextView)v.findViewById(R.id.text_zhishu_3_name);
		TextView text_zhishu3_content=(TextView)v.findViewById(R.id.text_zhishu_3_content);
		
		ImageView image_zhishu4=(ImageView)v.findViewById(R.id.image_zhishu_4);
		TextView text_zhishu4_name=(TextView)v.findViewById(R.id.text_zhishu_4_name);
		TextView text_zhishu4_content=(TextView)v.findViewById(R.id.text_zhishu_4_content);
		
		ImageView image_zhishu5=(ImageView)v.findViewById(R.id.image_zhishu_5);
		TextView text_zhishu5_name=(TextView)v.findViewById(R.id.text_zhishu_5_name);
		TextView text_zhishu5_content=(TextView)v.findViewById(R.id.text_zhishu_5_content);
		
		ImageView image_zhishu6=(ImageView)v.findViewById(R.id.image_zhishu_6);
		TextView text_zhishu6_name=(TextView)v.findViewById(R.id.text_zhishu_6_name);
		TextView text_zhishu6_content=(TextView)v.findViewById(R.id.text_zhishu_6_content);
		
		textCityName.setText(cityWeather.getCity());
		lastRefresh.setText("����ʱ��"+cityWeather.getLoc());
		nowT.setText(cityWeather.getNtmp()+"��");
		now_max_T.setText(cityWeather.getMax1());
		now_min_T.setText(cityWeather.getMin1());
		nowChinese.setText(cityWeather.getNtxt());
		nowDescrip.setText(cityWeather.getTxt1());
		nowWeather.setImageResource(getResources().getIdentifier("d"+cityWeather.getNcode(),"drawable", getActivity().getPackageName()));
		imageTomorrow.setImageResource(getResources().getIdentifier("d"+cityWeather.getCode_d2(),"drawable", getActivity().getPackageName()));
		textTomorrowT.setText(cityWeather.getMax2()+"��~"+cityWeather.getMin2()+"��");
		textTomorrowWind.setText(cityWeather.getDir2()+"/"+cityWeather.getSc2());
		textTomorrowDescrip.setText(cityWeather.getTxt_n2());
	//	initBtnListener();
		
		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
		try {
			calendar.setTime(format.parse(cityWeather.getDate1()));
			text7_1_ch.setText("����");
			image7_1_max.setImageResource(getResources().getIdentifier("d"+cityWeather.getCode_d1(),"drawable", getActivity().getPackageName()));
			text7_1_max.setText(cityWeather.getTxt_d1());
			text7_1_min.setText(cityWeather.getTxt_n1());
			image7_1_min.setImageResource(getResources().getIdentifier("n"+cityWeather.getCode_n1(),"drawable", getActivity().getPackageName()));
			text7_1_date.setText(calendar.get(Calendar.MONTH)+1+"/"+calendar.get(Calendar.DAY_OF_MONTH));
			
			calendar.setTime(format.parse(cityWeather.getDate2()));
			text7_2_ch.setText(weekOfDays[calendar.get(Calendar.DAY_OF_WEEK)]);
			image7_2_max.setImageResource(getResources().getIdentifier("d"+cityWeather.getCode_d2(),"drawable", getActivity().getPackageName()));
			text7_2_max.setText(cityWeather.getTxt_d2());
			text7_2_min.setText(cityWeather.getTxt_n2());
			image7_2_min.setImageResource(getResources().getIdentifier("n"+cityWeather.getCode_n2(),"drawable", getActivity().getPackageName()));
			text7_2_date.setText(calendar.get(Calendar.MONTH)+1+"/"+calendar.get(Calendar.DAY_OF_MONTH));
			
			calendar.setTime(format.parse(cityWeather.getDate3()));
			text7_3_ch.setText(weekOfDays[calendar.get(Calendar.DAY_OF_WEEK)]);
			image7_3_max.setImageResource(getResources().getIdentifier("d"+cityWeather.getCode_d3(),"drawable", getActivity().getPackageName()));
			text7_3_max.setText(cityWeather.getTxt_d3());
			text7_3_min.setText(cityWeather.getTxt_n3());
			image7_3_min.setImageResource(getResources().getIdentifier("n"+cityWeather.getCode_n3(),"drawable", getActivity().getPackageName()));
			text7_3_date.setText(calendar.get(Calendar.MONTH)+1+"/"+calendar.get(Calendar.DAY_OF_MONTH));
			
			calendar.setTime(format.parse(cityWeather.getDate4()));
			text7_4_ch.setText(weekOfDays[calendar.get(Calendar.DAY_OF_WEEK)]);
			image7_4_max.setImageResource(getResources().getIdentifier("d"+cityWeather.getCode_d4(),"drawable", getActivity().getPackageName()));
			text7_4_max.setText(cityWeather.getTxt_d4());
			text7_4_min.setText(cityWeather.getTxt_n4());
			image7_4_min.setImageResource(getResources().getIdentifier("n"+cityWeather.getCode_n4(),"drawable", getActivity().getPackageName()));
			text7_4_date.setText(calendar.get(Calendar.MONTH)+1+"/"+calendar.get(Calendar.DAY_OF_MONTH));
		
			calendar.setTime(format.parse(cityWeather.getDate5()));
			text7_5_ch.setText(weekOfDays[calendar.get(Calendar.DAY_OF_WEEK)]);
			image7_5_max.setImageResource(getResources().getIdentifier("d"+cityWeather.getCode_d5(),"drawable", getActivity().getPackageName()));
			text7_5_max.setText(cityWeather.getTxt_d5());
			text7_5_min.setText(cityWeather.getTxt_n5());
			image7_5_min.setImageResource(getResources().getIdentifier("n"+cityWeather.getCode_n5(),"drawable", getActivity().getPackageName()));
			text7_5_date.setText(calendar.get(Calendar.MONTH)+1+"/"+calendar.get(Calendar.DAY_OF_MONTH));
		
			calendar.setTime(format.parse(cityWeather.getDate6()));
			text7_6_ch.setText(weekOfDays[calendar.get(Calendar.DAY_OF_WEEK)]);
			image7_6_max.setImageResource(getResources().getIdentifier("d"+cityWeather.getCode_d6(),"drawable", getActivity().getPackageName()));
			text7_6_max.setText(cityWeather.getTxt_d6());
			text7_6_min.setText(cityWeather.getTxt_n6());
			image7_6_min.setImageResource(getResources().getIdentifier("n"+cityWeather.getCode_n6(),"drawable", getActivity().getPackageName()));
			text7_6_date.setText(calendar.get(Calendar.MONTH)+1+"/"+calendar.get(Calendar.DAY_OF_MONTH));
		
			calendar.setTime(format.parse(cityWeather.getDate7()));
			text7_7_ch.setText(weekOfDays[calendar.get(Calendar.DAY_OF_WEEK)]);
			image7_7_max.setImageResource(getResources().getIdentifier("d"+cityWeather.getCode_d7(),"drawable", getActivity().getPackageName()));
			text7_7_max.setText(cityWeather.getTxt_d7());
			text7_7_min.setText(cityWeather.getTxt_n7());
			image7_7_min.setImageResource(getResources().getIdentifier("n"+cityWeather.getCode_n7(),"drawable", getActivity().getPackageName()));
			text7_7_date.setText(calendar.get(Calendar.MONTH)+1+"/"+calendar.get(Calendar.DAY_OF_MONTH));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	//	image_zhishu1.setImageResource();
		text_zhishu1_name.setText("����");
		text_zhishu1_content.setText(cityWeather.getBrf3());
		System.out.println("����:"+cityWeather.getBrf3());
	
	//	image_zhishu2.setImageResource();
		text_zhishu2_name.setText("������");
		text_zhishu2_content.setText(cityWeather.getBrf7());
		System.out.println("������:"+cityWeather.getBrf7());
		
	//	image_zhishu3.setImageResource();
		text_zhishu3_name.setText("ϴ��");
		text_zhishu3_content.setText(cityWeather.getBrf2());
		System.out.println("ϴ��:"+cityWeather.getBrf2());
		
	//	image_zhishu4.setImageResource();
		text_zhishu4_name.setText("����");
		text_zhishu4_content.setText(cityWeather.getBrf6());
		System.out.println("����:"+cityWeather.getBrf6());
		
	//	image_zhishu5.setImageResource();
		text_zhishu5_name.setText("��ð");
		text_zhishu5_content.setText(cityWeather.getBrf4());
		System.out.println("��ð:"+cityWeather.getBrf4());
		
	//	image_zhishu6.setImageResource();
		text_zhishu6_name.setText("�˶�");
		text_zhishu6_content.setText(cityWeather.getBrf5());
		System.out.println("�˶�:"+cityWeather.getBrf5());
			
	}
	
	private String setJson(int cityId){
		String name=String.valueOf(cityId);
		String value=JsonDaoPro.getWeatherJSON(name);
		SharedPreferences mySharedPreferences= getActivity().getSharedPreferences("cityData",Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = mySharedPreferences.edit();
		editor.putString(name,value); 
		editor.commit();
		return value;
	}
	
	public String getJson(int cityId){
		SharedPreferences mySharedPreferences= getActivity().getSharedPreferences("cityData",Activity.MODE_PRIVATE); 
		String jsonData=mySharedPreferences.getString(String.valueOf(cityId), "");
		if(jsonData == "")
			jsonData=setJson(cityId);
		return jsonData; 
	}
	
	public void scrollToTop(){
		scrl.post(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				scrl.scrollTo(0, 0);
			}
			
		});
	}
}
