package weatherforecast.view;


import java.util.ArrayList;

import weatherforecast.dao.CityDao;
import weatherforecast.model.City_ID;
import weatherforecast.util.CreateDB;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class CityListFragment extends ListFragment {
	private CreateDB helper;
	private Button addCity;
	
	public CityListFragment(CreateDB db) {
		super();
		// TODO Auto-generated constructor stub
		this.helper=db;
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.menu_list, null);
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		ArrayList<City_ID> list=CityDao.showicity();
		String cities[] = new String[list.size()];
		for(int i=0;i<list.size();i++){
			cities[i] = list.get(i).getNamecn();
		}
		
		ArrayAdapter<String> cityAdapter = new ArrayAdapter<String>(getActivity(), 
				R.layout.city_list_item, R.id.textCityname, cities);
		setListAdapter(cityAdapter);
		
		addCity = (Button)getActivity().findViewById(R.id.addCity);
		addCity.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i=new Intent();
				i.setClass(getActivity(), AddCityActivity.class);
				startActivity(i);
			}
			
		});
	}


	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onListItemClick(ListView lv, View v, int position, long id) {

		switchFragment(position);
	}
	
	// the meat of switching the above fragment
	private void switchFragment(int position) {
		if (getActivity() == null)
			return;
		
		if (getActivity() instanceof WeatherMainActivity) {
			WeatherMainActivity pager = (WeatherMainActivity) getActivity();
			pager.getVp().setCurrentItem(position);
			pager.getSlidingMenu().showContent();
		}
	}
}
