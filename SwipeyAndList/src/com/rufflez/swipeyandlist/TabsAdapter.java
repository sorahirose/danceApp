package com.rufflez.swipeyandlist;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;

public class TabsAdapter extends FragmentPagerAdapter implements TabListener, OnPageChangeListener{
	private final Context mContext; //コンテキスト
	private final ActionBar mActionBar;
	private final ViewPager mViewPager;
	private final ArrayList<TabInfo> mTabs = new ArrayList<TabInfo>(); //リストの中身生成
	private final String TAG = "";
	
	//リストの中身生成
	static final class TabInfo{
		private final Class<?> clss;//どんな方のクラスでも
		private final Bundle args;//受け渡し
		TabInfo(Class<?> _class, Bundle _args){
			clss = _class;
			args = _args;
		}
	}
	
	//コンストラクタ
	public TabsAdapter(FragmentActivity activity, ViewPager pager) {
		super(activity.getSupportFragmentManager());
		//アクティビティを取得
		mContext = activity;
		//アクションバー
		mActionBar = activity.getActionBar();
		//MainActivityのページャー
		mViewPager = pager;
		//ページャーに対してAdapterをセット
		mViewPager.setAdapter(this);
		//ページが変わったら通知
		mViewPager.setOnPageChangeListener(this);
	}
	//addTabメソッド(actionBar.newTab().setText("Show"), List_View.class, null)
	//ListViewクラスが渡ってくる
	public void addTab(Tab tab, Class<?> clss, Bundle args){
		TabInfo info = new TabInfo(clss, args);
		//tabに対してtagをセットする
		tab.setTag(info);
		//リスナーセット
		tab.setTabListener(this);
		//リストビューに対して中身をセット
		mTabs.add(info);
		//アクションバーに追加
		mActionBar.addTab(tab);
		notifyDataSetChanged();
	}
	@Override
	public void onPageScrollStateChanged(int state) {
		
	}
	@Override
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
		
	}
	@Override
	public void onPageSelected(int position) {
		mActionBar.setSelectedNavigationItem(position);
		
	}
	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		
	}
	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		//ページャーに対して最新の情報をセットする
		mViewPager.setCurrentItem(tab.getPosition());
		Log.v(TAG, "clicked");
		Object tag = tab.getTag();
		for (int i = 0; i<mTabs.size(); i++){
			if (mTabs.get(i) == tag){
				mViewPager.setCurrentItem(i);
			}
		}
	}
	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		
	}
	@Override
	public Fragment getItem(int position) {
		//ポジション取得
		TabInfo info = mTabs.get(position);
		//フラグメントを取得
		return Fragment.instantiate(mContext, info.clss.getName(), info.args);
	}
	@Override
	public int getCount() {
		return mTabs.size();
	}
}
