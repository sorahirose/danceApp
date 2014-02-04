package com.rufflez.swipeyandlist;

import android.os.Bundle;
import android.app.ActionBar;
import android.content.res.Configuration;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends ActionBarActivity {

	private TabsAdapter mTabsAdapter;
	private String[] mMenuList;
	/** Navigation Drawer layout */
	private DrawerLayout mDrawerLayout;

	/** Drawer メニューリスト */
	private ListView mDrawerList;
	/** トグルドロワー */
	private ActionBarDrawerToggle mDrawerToggle;
	private static ViewPager pager;
	private ActionBar actionBar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_drawer_toggle_layout);
		//ViewPagerのインスタンス生成
		pager = (ViewPager)findViewById(R.id.viewpager);
		//ActionBar取得
		actionBar = getActionBar();
		//ホームボタンの表示消す
		actionBar.setDisplayHomeAsUpEnabled(false);
		//ホームボタン押せるように
		actionBar.setHomeButtonEnabled(true);
		//Tabモードに
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		mMenuList = getResources().getStringArray(R.array.menu_list_array);
		// Navigation Drawer作成
		createNavigationDrawer();
		//FragmentPagerAdapterのTabアダプターを生成
		mTabsAdapter = new TabsAdapter(this, pager); //アクティビティ,ページャー
		mTabsAdapter.addTab(actionBar.newTab().setText("Show"), ShowCaseFragment.class, null);
		mTabsAdapter.addTab(actionBar.newTab().setText("Battle"), BattleFragment.class, null);
		mTabsAdapter.addTab(actionBar.newTab().setText("Lesson"), LessonFragment.class, null);

	}
	private void createNavigationDrawer() {
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);
		mDrawerList.setAdapter(new ArrayAdapter<String>(this,
				R.layout.drawer_list_item, mMenuList));
		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

		// ActionBarDrawerToggle ties together the the proper interactions
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.drawable.ic_launcher, R.string.drawer_open, R.string.drawer_close) {
			public void onDrawerClosed(View view) {
				//ドロワーが閉まったら
				Log.d("!!onDrawerClosed!!","ドロワーが閉まった");
			}
			public void onDrawerOpened(View drawerView) {
				//ドロワーが開いたら
				Log.d("!!onDrawerOpened!!","ドロワーが開いた");
			}
			@Override
			public void onDrawerSlide(View drawerView, float slideOffset) {
				// ActionBarDrawerToggleクラス内の同メソッドにてアイコンのアニメーションの処理をしている。
				// overrideするときは気を付けること。
				super.onDrawerSlide(drawerView, slideOffset);
			}
			@Override
			public void onDrawerStateChanged(int newState) {
				// 表示済み、閉じ済みの状態：0
				// ドラッグ中状態:1
				// ドラッグを放した後のアニメーション中：2
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);
	}
	/* The click listner for ListView in the navigation drawer */
	private class DrawerItemClickListener implements ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			Log.d("!!DrawerItemClick!!","!!!!");
			mDrawerLayout.closeDrawer(GravityCompat.START);
		}
	}
	/*
	 * (非 Javadoc)
	 * 
	 * @see
	 * com.actionbarsherlock.app.SherlockFragmentActivity#onPostCreate(android
	 * .os.Bundle)
	 */
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	/*
	 * (非 Javadoc)
	 * 
	 * @see
	 * com.actionbarsherlock.app.SherlockFragmentActivity#onConfigurationChanged
	 * (android.content.res.Configuration)
	 */
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}
	/*
	 * (非 Javadoc)
	 * 
	 * @see jp.i_dig.community.activity.AbstractCommunityAppActivity#
	 * onOptionsItemSelected(com.actionbarsherlock.view.MenuItem)
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);
		if (item != null && item.getItemId() == android.R.id.home) {
			if (mDrawerLayout.isDrawerVisible(GravityCompat.START)) {
				mDrawerLayout.closeDrawer(GravityCompat.START);
			} else {
				mDrawerLayout.openDrawer(GravityCompat.START);
			}
			return true;
		}
		return false;
	}
}
