package weatherforecast.view;

import java.io.IOException;
import java.util.ArrayList;

import weatherforecast.util.CreateDB;

import android.database.SQLException;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

public class WeatherMainActivity extends BaseActivity {

	CreateDB myDbHelper;
	ViewPager vp;	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		initDB();
		
		vp = new ViewPager(this);
		vp.setId("VP".hashCode());
		vp.setAdapter(new WeatherPagerAdapter(getSupportFragmentManager()));
		setContentView(vp);

		vp.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageScrollStateChanged(int arg0) { }

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) { }

			@Override
			public void onPageSelected(int position) {
				switch (position) {
				case 0://页面在第一个的时候侧滑菜单的属性
					getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
					break;
				default://页面不在第一个页面的时候设置侧滑菜单的属性
					getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
					break;
				}
			}

		});
		
		// set the Behind View
		setBehindContentView(R.layout.menu_frame);
		getSupportFragmentManager()
		.beginTransaction()
		.replace(R.id.menu_frame, new CityListFragment(myDbHelper))
		.commit();
		vp.setCurrentItem(0);//设置启动页面
		getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
	}
	

	public class WeatherPagerAdapter extends FragmentPagerAdapter {
		
		private ArrayList<Fragment> mFragments;

		private final int[] cities = new int[] {
				0,
				1
		};
		
		public WeatherPagerAdapter(FragmentManager fm) {
			super(fm);
			mFragments = new ArrayList<Fragment>();
			for (int citiId : cities)
				mFragments.add(new WeatherHomeFragment(citiId,myDbHelper));
		}

		@Override
		public int getCount() {
			return mFragments.size();
		}

		@Override
		public Fragment getItem(int position) {
			return mFragments.get(position);
		}

	}
	
	public void initDB() {
		// TODO Auto-generated constructor stub
		CreateDB myDbHelper = new CreateDB(this);  
		// myDbHelper = new CreateDB(this);  
   
        try { 
        	myDbHelper.createDataBase();  
        } catch (IOException ioe) {  
        	throw new Error("Unable to create database");  
        }  
        try {  
        	myDbHelper.openDataBase();  
        }catch(SQLException sqle){  
        	throw sqle;  
        }
	}
	
	public ViewPager getVp(){
		return this.vp;
	}

}
