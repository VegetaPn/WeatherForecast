package weatherforecast.view;


import weatherforecast.util.CreateDB;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class CityListFragment extends ListFragment {
	private CreateDB helper;
	
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
		String[] cities = {"北京","石家庄"};
		ArrayAdapter<String> cityAdapter = new ArrayAdapter<String>(getActivity(), 
				R.layout.city_list_item, R.id.textCityname, cities);
		setListAdapter(cityAdapter);
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
