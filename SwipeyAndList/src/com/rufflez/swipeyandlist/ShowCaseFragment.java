package com.rufflez.swipeyandlist;
import java.util.ArrayList;
import java.util.List;

import android.os.Build;
import android.os.Bundle;  
import android.annotation.TargetApi;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;  
import android.view.View;  
import android.view.ViewGroup;  
import android.widget.ArrayAdapter;
import android.widget.TextView;
  
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class ShowCaseFragment extends ListFragment {  
	String[] listItems;
//    @Override  
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {  
//        // 第３引数のbooleanは"container"にreturnするViewを追加するかどうか  
//        //trueにすると最終的なlayoutに再度、同じView groupが表示されてしまうのでfalseでOKらしい  
////    	View rootView =inflater.inflate(R.layout.lista, container, false);
////    	listItems = getResources().getStringArray(R.array.list);
//    	
////    	setListAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,listItems));
////        return rootView;   
//    }  
    @Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		String[] text ={"1つめ","2つめ","3つめ","4つめ"};
		String[] url ={"http://i.ytimg.com/vi/6Y3OzrnBBlc/default.jpg","http://i1.ytimg.com/vi/YTXCIKHF1fE/default.jpg","http://i.ytimg.com/vi/6Y3OzrnBBlc/default.jpg","http://i1.ytimg.com/vi/YTXCIKHF1fE/default.jpg"};
		// データの作成
		List<YoutubeListData> objects = new ArrayList<YoutubeListData>();
		for (int i = 0; i < url.length; i++) {
			YoutubeListData item = new YoutubeListData();
			item.setTextData(text[i]);
			item.setThumbnailUrl(url[i]);
			objects.add(item);
		}
		
//		
//		
//		YoutubeListData item1 = new YoutubeListData();
//		item1.setTextData("1つめ");
//		item1.setThumbnailUrl("http://i.ytimg.com/vi/6Y3OzrnBBlc/default.jpg");
//		objects.add(item1);
//		
//		YoutubeListData item2 = new YoutubeListData();
//		item2.setTextData("2つめ");
//		item2.setThumbnailUrl("http://i1.ytimg.com/vi/6Y3OzrnBBlc/default.jpg");
//		objects.add(item2);
//		
//		YoutubeListData item3 = new YoutubeListData();
//		item3.setTextData("3つめ");
//		item3.setThumbnailUrl("http://i.ytimg.com/vi/6Y3OzrnBBlc/default.jpg");
//		objects.add(item3);
//		
		
		YoutubeListAdapter customAdapter = new YoutubeListAdapter(getActivity(), 0, objects);
		setListAdapter(customAdapter);
	}
}  
