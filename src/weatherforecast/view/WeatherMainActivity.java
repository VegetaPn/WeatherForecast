package weatherforecast.view;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;

import weatherforecast.dao.CityDao;
import weatherforecast.model.City_ID;
import weatherforecast.util.CreateDB;

import android.database.SQLException;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

public class WeatherMainActivity extends BaseActivity {

	CreateDB myDbHelper;
	ViewPager vp;	
	ScrollView scrl;
	ArrayList<Fragment> mFragments;
	WeatherPagerAdapter adapter;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		mFragments=new ArrayList<Fragment>();
		initDB();
		scrl=(ScrollView)findViewById(R.id.scrollView1);
		vp = new ViewPager(this);
		vp.setId("VP".hashCode());
		adapter = new WeatherPagerAdapter(getSupportFragmentManager(),mFragments);
		vp.setAdapter(adapter);
		setViewPagerScrollSpeed(vp);
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
				WeatherHomeFragment w = (WeatherHomeFragment) vp.getAdapter().instantiateItem(vp, position);
				w.scrollToTop();
				
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
	
	private void setViewPagerScrollSpeed(ViewPager vp){  
        try {  
            Field mScroller = null;  
            mScroller = ViewPager.class.getDeclaredField("mScroller");  
            mScroller.setAccessible(true);   
            FixedSpeedScroller scroller = new FixedSpeedScroller( vp.getContext( ) );  
            mScroller.set( vp, scroller);  
        }catch(NoSuchFieldException e){  
              
        }catch (IllegalArgumentException e){  
              
        }catch (IllegalAccessException e){  
              
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
        
        ArrayList<City_ID> list = CityDao.showicity();
		for (City_ID city : list)
			mFragments.add(new WeatherHomeFragment(city.getId(),myDbHelper));
	}
	
	public ViewPager getVp(){
		return this.vp;
	}
	
	public class WeatherPagerAdapter extends FragmentPagerAdapter {
		
		private ArrayList<Fragment> mFragments;
		private FragmentManager fm;
		
		public WeatherPagerAdapter(FragmentManager fm,ArrayList<Fragment> frag) {
			super(fm);
			this.mFragments=frag;
			this.fm=fm;
		}

		@Override
		public int getCount() {
			return mFragments.size();
		}

		@Override
		public Fragment getItem(int position) {
			return mFragments.get(position);
		}
		
		@Override
		public int getItemPosition(Object object) {
			// TODO Auto-generated method stub
			return PagerAdapter.POSITION_NONE;
		}
/*
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			// TODO Auto-generated method stub
			((ViewPager) container).removeView(((ViewPager) container).getChildAt(position)); 
		//	super.destroyItem(container, position, object);
		}
*/
		@Override

		public Object instantiateItem(ViewGroup container,int position) {

			//得到缓存的fragment

		    Fragment fragment = (Fragment)super.instantiateItem(container,position);
		    
		    if(fragment != mFragments.get(position)){
		    	System.out.println("1111");
			    FragmentTransaction ft =fm.beginTransaction();
			    
			    String preTag = fragment.getTag(); 
			    
			    //移除旧的fragment

			    ft.remove(fragment);
			    //换成新的fragment
			    fragment=mFragments.get(position);
			    
			    ft.remove(fragment);
			    ft.commit();
			    fm.executePendingTransactions();
			    ft=fm.beginTransaction();
			    ft.add(container.getId(), fragment, preTag);

			    ft.attach(fragment);

			    ft.commit();
		    }
		    return fragment;

		}
		
		public void remove(int index){
			mFragments.remove(index);
			notifyDataSetChanged();
		//	setFragments(mFragments);
		}
		
		public int getIdAt(int index){
			WeatherHomeFragment w = (WeatherHomeFragment) mFragments.get(index);
			return w.getCityId();
		}
		
		public void setFragments(ArrayList fragments) {
			   if(this.mFragments != null){
			      FragmentTransaction ft = fm.beginTransaction();
			      for(Fragment f:this.mFragments){
	    			  ft.remove(f);
			      }
			      ft.commit();
			      ft=null;
			      fm.executePendingTransactions();
			   }
			  this.mFragments = fragments;
			  notifyDataSetChanged();
			}

	}
}
