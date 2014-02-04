package com.rufflez.swipeyandlist;
import android.os.Build;
import android.os.Bundle;  
import android.annotation.TargetApi;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;  
import android.view.View;  
import android.view.ViewGroup;  
import android.widget.ArrayAdapter;
  
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class LessonFragment extends ListFragment {  
	String[] listItems;
    @Override  
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {  
        // 第３引数のbooleanは"container"にreturnするViewを追加するかどうか  
        //trueにすると最終的なlayoutに再度、同じView groupが表示されてしまうのでfalseでOKらしい  
//        return inflater.inflate(R.layout.fragment_lesson, container, false); 
    	View rootView =inflater.inflate(R.layout.list, container, false);
    	listItems = getResources().getStringArray(R.array.list);
    	setListAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,listItems));
        return rootView;  
    }  
}  
