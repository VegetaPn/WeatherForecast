package weatherforecast.view;
import weatherforecast.view.R;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import android.widget.Toast;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.TimerTask;

import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;

import com.baoyz.widget.PullRefreshLayout;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners.SnsPostListener;

import weatherforecast.dao.*;
import weatherforecast.model.CityWeather;
import weatherforecast.util.CreateDB;

public class WeatherHomeFragment extends Fragment {
	UMSocialService mController = UMServiceFactory.getUMSocialService("com.umeng.share");
	private int cityId;
	CreateDB myDbHelper;
	ScrollView scrl;
	PullRefreshLayout refreshView;
	private static final int msgKey1 = 1123;
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
		View v = inflater.inflate(R.layout.weather_home_fragment, container, false);
		
		
		ImageButton menu=(ImageButton)v.findViewById(R.id.imageButton1);
		menu.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				slide.showMenu();
			}
		});
		scrl=(ScrollView)v.findViewById(R.id.scrollView1);
		
		if(cityId == 0){
			scrl.setVisibility(View.GONE);
			return v;
		}
		
		refreshView = (PullRefreshLayout)v.findViewById(R.id.swipeRefreshLayout);
		refreshView.setRefreshStyle(PullRefreshLayout.STYLE_WATER_DROP);
		refreshView.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshView.post(new Runnable() {
                    @Override
                    public void run() {
                    	Message msg = new Message();
                    	msg.obj=getActivity();
						msg.what = msgKey1;
						mHandler.sendMessage(msg);
                    }
                });
            }
        });
		ImageButton share=(ImageButton)v.findViewById(R.id.imageButton2);
		mController.getConfig().setPlatforms(SHARE_MEDIA.SINA,SHARE_MEDIA.RENREN,
				SHARE_MEDIA.QZONE,SHARE_MEDIA.SMS,SHARE_MEDIA.EMAIL);
		
		share.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String jsonData=getJson(false);
				CityWeather cityWeather=JsonDaoPro.parseJson(jsonData);
				String str="#��������# ����"+cityWeather.getCity()+",�¶���:"+cityWeather.getNtmp()
						+"�ȣ������:"+cityWeather.getMax1()+"�ȣ�����£�"+cityWeather.getMin1()+"�ȣ�"+cityWeather.getTxt1();
				mController.setShareContent(str);
				mController.openShare(getActivity(), new SnsPostListener() {
					@Override
					public void onStart() {
					//����ʼ
					}
					@Override
					public void onComplete(SHARE_MEDIA platform, int eCode, SocializeEntity entity) {
					//���������eCode==200�������ɹ�����200�������ʧ��
					}
					});
			}
		});
		
		initView(v,false);
		return v;
	}
	
	private Handler mHandler = new Handler(){
		@Override
        public void handleMessage(Message msg){
    	    super.handleMessage(msg);
            switch(msg.what){
		        case msgKey1:
		        	( (WeatherMainActivity)getActivity() ).refreshFragments();
		        	refreshView.setRefreshing(false);
	            default:
	                break;
            }
        }
    };
	
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
	
	public int refreshView(){
		int resault=0;
		if(cityId != 0){
			if(getView() != null)
				resault=initView(getView(),true);
			else
				return -111;
		}
		return resault;
	}
	

	private int initView(View v,boolean isRefresh){
		String jsonData=getJson(isRefresh);
		if(jsonData==null)
			return -1;
		final CityWeather cityWeather=JsonDaoPro.parseJson(jsonData);
		String span=getDateDifference();
		int screenHeight = getActivity().getWindowManager().getDefaultDisplay().getHeight();
		
		LinearLayout appLayout=(LinearLayout) v.findViewById(R.id.LinearLayout1);
		int weatherCode=Integer.parseInt(cityWeather.getNcode());
		switch(weatherCode/100){
			case 1:
				appLayout.setBackgroundResource(getResources().getIdentifier("bg1","drawable", getActivity().getPackageName()));
				break;
			case 2:
				appLayout.setBackgroundResource(getResources().getIdentifier("bg2","drawable", getActivity().getPackageName()));
				break;
			case 3:
			case 5:
				appLayout.setBackgroundResource(getResources().getIdentifier("bg3","drawable", getActivity().getPackageName()));
				break;
			case 4:
				appLayout.setBackgroundResource(getResources().getIdentifier("bg4","drawable", getActivity().getPackageName()));
				break;
			default:
				break;
			
		}
		LinearLayout imagePanel;
		imagePanel = (LinearLayout)v.findViewById(R.id.imagePanel);
		LayoutParams p = imagePanel.getLayoutParams();
		p.height=screenHeight/3;
		imagePanel.setLayoutParams(p);
		
		initChart(v,cityWeather);
		
		RelativeLayout btn7_1=(RelativeLayout)v.findViewById(R.id.btn_7_1);
		RelativeLayout btn7_2=(RelativeLayout)v.findViewById(R.id.btn_7_2);
		RelativeLayout btn7_3=(RelativeLayout)v.findViewById(R.id.btn_7_3);
		RelativeLayout btn7_4=(RelativeLayout)v.findViewById(R.id.btn_7_4);
		RelativeLayout btn7_5=(RelativeLayout)v.findViewById(R.id.btn_7_5);
		RelativeLayout btn7_6=(RelativeLayout)v.findViewById(R.id.btn_7_6);
		RelativeLayout btn7_7=(RelativeLayout)v.findViewById(R.id.btn_7_7);
		RelativeLayout zhishu1=(RelativeLayout)v.findViewById(R.id.zhishu1);
		RelativeLayout zhishu2=(RelativeLayout)v.findViewById(R.id.zhishu2);
		RelativeLayout zhishu3=(RelativeLayout)v.findViewById(R.id.zhishu3);
		RelativeLayout zhishu4=(RelativeLayout)v.findViewById(R.id.zhishu4);
		RelativeLayout zhishu5=(RelativeLayout)v.findViewById(R.id.zhishu5);
		RelativeLayout zhishu6=(RelativeLayout)v.findViewById(R.id.zhishu6);
		
		final WeatherMainActivity mainAct=(WeatherMainActivity)getActivity();
		/*
		Button aqi=(Button)v.findViewById(R.id.button_aqi);
		aqi.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent aqiIntent=new Intent(mainAct,ExponentActivity.class);
				mainAct.startActivity(aqiIntent);
			}
			
		});
		*/
		OnClickListener forecastListener=new OnClickListener(){

			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(mainAct,ScheduleActivity.class);
				switch(view.getId()){
				case R.id.btn_7_1:
					intent.putExtra("dateString", cityWeather.getDate1());
					break;
				case R.id.btn_7_2:
					intent.putExtra("dateString", cityWeather.getDate2());
					break;
				case R.id.btn_7_3:
					intent.putExtra("dateString", cityWeather.getDate3());
					break;
				case R.id.btn_7_4:
					intent.putExtra("dateString", cityWeather.getDate4());			
					break;
				case R.id.btn_7_5:
					intent.putExtra("dateString", cityWeather.getDate5());
					break;
				case R.id.btn_7_6:
					intent.putExtra("dateString", cityWeather.getDate6());
					break;
				case R.id.btn_7_7:
					intent.putExtra("dateString", cityWeather.getDate7());
					break;
				}
				mainAct.startActivity(intent);
			}
			
		};
		btn7_1.setOnClickListener(forecastListener);
		btn7_2.setOnClickListener(forecastListener);
		btn7_3.setOnClickListener(forecastListener);
		btn7_4.setOnClickListener(forecastListener);
		btn7_5.setOnClickListener(forecastListener);
		btn7_6.setOnClickListener(forecastListener);
		btn7_7.setOnClickListener(forecastListener);
		
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
		lastRefresh.setText(span);
		nowT.setText(cityWeather.getNtmp()+"��");
		now_max_T.setText(cityWeather.getMax1());
		now_min_T.setText(cityWeather.getMin1());
		nowChinese.setText(cityWeather.getNtxt());
		nowDescrip.setText(cityWeather.getTxt1());
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");//�������ڸ�ʽ
		try {
			Date sunRise=df.parse(cityWeather.getDate1()+" "+cityWeather.getSr1());
			Date sunDown=df.parse(cityWeather.getDate1()+" "+cityWeather.getSs1());
			System.out.println("sunrise:"+sunRise);
			Date now=new Date();
			if(now.compareTo(sunRise)>0 && now.compareTo(sunDown)<0){
				nowWeather.setImageResource(getResources().getIdentifier("b"+cityWeather.getNcode(),"drawable", getActivity().getPackageName()));
			}else{
				nowWeather.setImageResource(getResources().getIdentifier("c"+cityWeather.getNcode(),"drawable", getActivity().getPackageName()));
				if(Integer.parseInt(cityWeather.getNcode())/100 == 1){
					appLayout.setBackgroundResource(getResources().getIdentifier("bg1_n","drawable", getActivity().getPackageName()));
				}
			}
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
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
			image7_1_max.setImageResource(getResources().getIdentifier("d"+cityWeather.getNcode(),"drawable", getActivity().getPackageName()));
			text7_1_max.setText(cityWeather.getNtxt());
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
		
		int screenWidth = getActivity().getWindowManager().getDefaultDisplay().getWidth();
		LayoutParams lp=image_zhishu1.getLayoutParams();
		lp.width=(int) (screenWidth/3*0.5);
		lp.height=lp.width;
		image_zhishu1.setLayoutParams(lp);
		text_zhishu1_name.setText("����");
		text_zhishu1_content.setText(cityWeather.getBrf3());
	
		image_zhishu2.setLayoutParams(lp);
		text_zhishu2_name.setText("������");
		text_zhishu2_content.setText(cityWeather.getBrf7());
		
		image_zhishu3.setLayoutParams(lp);
		text_zhishu3_name.setText("ϴ��");
		text_zhishu3_content.setText(cityWeather.getBrf2());
		
		image_zhishu4.setLayoutParams(lp);
		text_zhishu4_name.setText("����");
		text_zhishu4_content.setText(cityWeather.getBrf6());
		
		image_zhishu5.setLayoutParams(lp);
		text_zhishu5_name.setText("��ð");
		text_zhishu5_content.setText(cityWeather.getBrf4());
		
		image_zhishu6.setLayoutParams(lp);
		text_zhishu6_name.setText("�˶�");
		text_zhishu6_content.setText(cityWeather.getBrf5());
		
		return 0;
	}
	
	public String setJson(int cityId,Activity mainActivity){
		String name=String.valueOf(cityId);
		String value=JsonDaoPro.getWeatherJSON(name);
		if(value == null)
			return null;
		SharedPreferences mySharedPreferences= mainActivity.getSharedPreferences("cityData",Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = mySharedPreferences.edit();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//�������ڸ�ʽ
		String last=df.format(new Date());// new Date()Ϊ��ȡ��ǰϵͳʱ��
		editor.putString(name,value); 
		editor.putString(name+"-l",last);
		editor.commit();
		return value;
	}
	
	public String getJson(boolean isRefresh){
		SharedPreferences mySharedPreferences= getActivity().getSharedPreferences("cityData",Activity.MODE_PRIVATE); 
		String jsonData=mySharedPreferences.getString(String.valueOf(cityId), "");
		if(jsonData == "" || isRefresh){
			System.out.println("get json from server:"+cityId);
			jsonData=setJson(cityId,getActivity());
		}
		return jsonData; 
	}
	
	public String getDateDifference(){
		String dif="";
		Date now=new Date();
		SharedPreferences mySharedPreferences= getActivity().getSharedPreferences("cityData",Activity.MODE_PRIVATE); 
		String oldTime=mySharedPreferences.getString(String.valueOf(cityId)+"-l", "");
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//�������ڸ�ʽ
		Date old=new Date();
		try {
			old=df.parse(oldTime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		long span=now.getTime()-old.getTime();
		int day=(int) (span/(24*60*60*1000));
		int hour=(int) (span/(60*60*1000));
		int min=(int) (span/(60*1000));
		if(day>0)
			dif=day+"��ǰ";
		else if(hour>0)
			dif=hour+"Сʱǰ";
		else if(min>0)
			dif=min+"����ǰ";
		else
			dif="�ո�ˢ��";
		return dif;
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
