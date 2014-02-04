package com.rufflez.swipeyandlist;
import android.os.Bundle;  
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;  
import android.view.View;  
import android.view.ViewGroup;  
import android.widget.ArrayAdapter;
  
public class BattleFragment extends ListFragment { 
	String[] listItems;
    @Override  
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {  
    	View rootView =inflater.inflate(R.layout.list, container, false);
    	listItems = getResources().getStringArray(R.array.list);
    	setListAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,listItems));
        return rootView;  
    }  
}  
