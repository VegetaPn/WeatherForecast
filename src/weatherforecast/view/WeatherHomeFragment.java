package weatherforecast.view;
import weatherforecast.view.R;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.database.SQLException;
import java.io.IOException;
import weatherforecast.dao.*;
import weatherforecast.model.CityWeather;
import weatherforecast.util.CreateDB;

public class WeatherHomeFragment extends Fragment {

	private int cityId;
	CreateDB myDbHelper;
	ScrollView scrl;
	
	public WeatherHomeFragment() {
		
	}

	public WeatherHomeFragment(int i,CreateDB db){
		this.cityId=i;
		this.myDbHelper=db;
	}
	

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		if (savedInstanceState != null)
			cityId = savedInstanceState.getInt("index");
		View v = inflater.inflate(R.layout.weather_home_fragment, container, false);
		initView(v);
		return v;
	}
	
	private void initView(View v){
		TextView t;
		TextView t1,t2;
		CityWeather cityWeather;
		t = (TextView)v.findViewById(R.id.text_weather);
		cityWeather = JsonDao.getCityWeatherbyCityID(cityId+"");
		String result = cityWeather.getCity()+"\n"+cityWeather.getCityid()+"\n"
				+cityWeather.getDate_y()+"\n"+cityWeather.getIndex()+"\n"
				+cityWeather.getIndex_co()+"\n"+cityWeather.getIndex_d()+"\n"
				+cityWeather.getIndex_tr()+"\n"+cityWeather.getIndex_uv()+"\n"
				+cityWeather.getIndex_xc()+"\n"+cityWeather.getTemp1()+"\n"
				+cityWeather.getWeather1()+"\n"+cityWeather.getWeek()+"\n"
				+cityWeather.getWind1()+"\n";
		t.setText(result);
		t1=(TextView)v.findViewById(R.id.textView1);
		t2=(TextView)v.findViewById(R.id.textView2);
		t1.setText("������һЩ������Ϣ\n������һЩ������Ϣ\n������һЩ������Ϣ\n������һЩ������Ϣ\n������һЩ������Ϣ\n" +
				"������һЩ������Ϣ\n������һЩ������Ϣ\n������һЩ������Ϣ\n������һЩ������Ϣ\n������һЩ������Ϣ\n������һЩ������Ϣ\n" +
				"������һЩ������Ϣ\n������һЩ������Ϣ\n������һЩ������Ϣ\n������һЩ������Ϣ\n������һЩ������Ϣ\n������һЩ������Ϣ\n");
		t2.setText("������һЩ������Ϣ\n������һЩ������Ϣ\n������һЩ������Ϣ\n������һЩ������Ϣ\n������һЩ������Ϣ\n" +
				"������һЩ������Ϣ\n������һЩ������Ϣ\n������һЩ������Ϣ\n������һЩ������Ϣ\n������һЩ������Ϣ\n������һЩ������Ϣ\n" +
				"������һЩ������Ϣ\n������һЩ������Ϣ\n������һЩ������Ϣ\n������һЩ������Ϣ\n������һЩ������Ϣ\n������һЩ������Ϣ\n");
		
		scrl=(ScrollView)v.findViewById(R.id.scrollView1);
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
