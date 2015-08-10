package weatherforecast.view;


import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.nhaarman.listviewanimations.appearance.AnimationAdapter;
import com.nhaarman.listviewanimations.appearance.simple.AlphaInAnimationAdapter;
import com.nhaarman.listviewanimations.appearance.simple.SwingLeftInAnimationAdapter;
import com.nhaarman.listviewanimations.itemmanipulation.DynamicListView;
import com.nhaarman.listviewanimations.itemmanipulation.dragdrop.TouchViewDraggableManager;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.OnDismissCallback;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.undo.SimpleSwipeUndoAdapter;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.undo.UndoAdapter;

import weatherforecast.dao.CityDao;
import weatherforecast.model.City_ID;
import weatherforecast.util.CreateDB;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class CityListFragment extends ListFragment {
	private CreateDB helper;
	private ImageButton addCity;
	private MyListAdapter<String> listAdapter;
	private ArrayList<String> nameList;
	private Handler locHandler;
	SlidingMenu slide;
	
	public CityListFragment(CreateDB db,SlidingMenu slide) {
		super();
		// TODO Auto-generated constructor stub
		this.helper=db;
		this.slide=slide;
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.menu_list, null);
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		ArrayList<City_ID> list=CityDao.showicity();
		nameList = new ArrayList<String>();
		for(int i=0;i<list.size();i++){
			nameList.add(list.get(i).getNamecn());
		}
		
		listAdapter = new MyListAdapter<String>(getActivity(), 
				R.layout.city_list_item, R.id.textCityname, nameList);
		final DynamicListView dyList = (DynamicListView) getListView();
		
		dyList.enableSwipeToDismiss(
			    new OnDismissCallback() {
			        @Override
			        public void onDismiss(@NonNull final ViewGroup listView, @NonNull final int[] reverseSortedPositions) {
			            for (int position : reverseSortedPositions) {
			            	listAdapter.remove(nameList.get(position));
			            }
			        }
			    }
			);
		
		SimpleSwipeUndoAdapter swipeUndoAdapter = new SimpleSwipeUndoAdapter(listAdapter, getActivity(),
			    new OnDismissCallback() {
			        @Override
			        public void onDismiss(@NonNull final ViewGroup listView, @NonNull final int[] reverseSortedPositions) {
			            for (int position : reverseSortedPositions) {
			            	listAdapter.remove(nameList.get(position));
			            	WeatherMainActivity mainAct= (WeatherMainActivity) getActivity();
			            	removeKey(mainAct.adapter.getIdAt(position+1));
			            	CityDao.deleteCity(mainAct.adapter.getIdAt(position+1));
			    			mainAct.adapter.remove(position+1);
			            }
			        }
			    }
			);
		swipeUndoAdapter.setAbsListView(dyList);
		setListAdapter(swipeUndoAdapter);
		dyList.enableSimpleSwipeUndo();
	
		addCity = (ImageButton)getActivity().findViewById(R.id.addCity);
		addCity.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i=new Intent();
				i.setClass(getActivity(), AddCityActivity.class);
				startActivityForResult(i, 101);
			}
			
		});
		
		RelativeLayout btnLoc=(RelativeLayout) getView().findViewById(R.id.btnLocation);
		btnLoc.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				switchFragment(0);
			}
		});
		
		this.startTimer();
	}


	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
	//	super.onActivityResult(requestCode, resultCode, data);
		if(requestCode==101 && resultCode!=-1){
			WeatherMainActivity mainAct= (WeatherMainActivity) getActivity();
			WeatherHomeFragment newPage = new WeatherHomeFragment(resultCode,helper,slide);
			mainAct.mFragments.add(newPage);
			mainAct.adapter.notifyDataSetChanged();
			mainAct.getVp().setCurrentItem(mainAct.mFragments.indexOf(newPage),false);
			nameList.add(CityDao.getCityByID(resultCode).getNamecn());
			listAdapter.notifyDataSetChanged();
		}
	}

	@Override
	public void onListItemClick(ListView lv, View v, int position, long id) {

		switchFragment(position+1);
	}
	
	// the meat of switching the above fragment
	private void switchFragment(int position) {
		if (getActivity() == null)
			return;
		
		if (getActivity() instanceof WeatherMainActivity) {
			WeatherMainActivity pager = (WeatherMainActivity) getActivity();
			pager.getVp().setCurrentItem(position, false);
			pager.getSlidingMenu().showContent();
		}
	}
	
	public void removeKey(int id){
		SharedPreferences mySharedPreferences= getActivity().getSharedPreferences("cityData",Activity.MODE_PRIVATE); 
		SharedPreferences.Editor edit = mySharedPreferences.edit();
		edit.remove(String.valueOf(id));
		edit.remove(String.valueOf(id)+"-l");
		edit.commit();
	}
	
	public class MyListAdapter<T> extends ArrayAdapter<T> implements UndoAdapter {

		private final Context mContext;
		
		

		public MyListAdapter(Context context, int resource, int textViewResourceId,
				List<T> objects) {
			super(context, resource, textViewResourceId, objects);
			this.mContext=context;
			// TODO Auto-generated constructor stub
		}

		@Override
		@NonNull
		public View getUndoClickView(@NonNull View view) {
			// TODO Auto-generated method stub
			return view.findViewById(R.id.undo_row_undobutton);
		}

		@Override
		@NonNull
		public View getUndoView(int position, @Nullable View convertView,
				@NonNull ViewGroup parent) {
			// TODO Auto-generated method stub
			View view = convertView;
	        if (view == null) {
	            view = LayoutInflater.from(mContext).inflate(R.layout.undo_row, parent, false);
	        }
	        return view;
		}

	}
	
	public void startTimer(){
		final WeatherMainActivity mainActivity=(WeatherMainActivity)getActivity();
		locHandler=new Handler(){

			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				if(msg.what == 6666){
					WeatherHomeFragment newPage = new WeatherHomeFragment(mainActivity.getLocId(),helper,slide);
					mainActivity.mFragments.remove(0);
					mainActivity.mFragments.add(0, newPage);
					mainActivity.adapter.notifyDataSetChanged();
					TextView locText=(TextView) getView().findViewById(R.id.textLocname);
					locText.setText(CityDao.getCityByID(mainActivity.getLocId()).getNamecn());
				}
			}
			
		};
		TimerTask task = new TimerTask() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				int locID=mainActivity.getLocId();
				if(locID != 0){
					Message msg = new Message();
					msg.what = 6666;
					locHandler.sendMessage(msg);
					this.cancel();
				}
			}
		};
		
		Timer timer = new Timer();
		timer.schedule(task,0, 1000);
	}
}
