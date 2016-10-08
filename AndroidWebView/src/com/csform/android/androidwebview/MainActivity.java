package com.csform.android.androidwebview;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.csform.android.androidwebview.adapter.LeftMenuAdapter;
import com.csform.android.androidwebview.fragment.AboutUsFragment;
import com.csform.android.androidwebview.fragment.ContactFragment;
import com.csform.android.androidwebview.fragment.WebViewFragment;
import com.csform.android.androidwebview.model.LeftMenuItem;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class MainActivity extends BaseActivity {

	private AdView mAdView;

	private SyncData syncData;

	private Dialog mSplashScreenDialog;
	private boolean mShouldSetContentView = true;

	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
//	private List<String> mLeftMenuItems;
	private List<LeftMenuItem> mLeftMenuItems;

	private String mTitle;

	Fragment fragment;

	private boolean mShouldFinish = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		setMainViews();
		syncData = new SyncData(this);
		syncData.execute();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (mAdView != null) {
			mAdView.destroy();
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		if (mAdView != null) {
			mAdView.resume();
		}
	}

	@Override
	public void onPause() {
		if (mAdView != null) {
			mAdView.pause();
		}
		super.onPause();
	}

	@SuppressLint("NewApi")
	private void setMainViews() {
		setContentView(R.layout.activity_main);

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
			getActionBar().setHomeButtonEnabled(true);
		}

		mAdView = (AdView) findViewById(R.id.main_activity_ad_view);
		mAdView.loadAd(new AdRequest.Builder().build());

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);
		mDrawerLayout.setFocusableInTouchMode(false);
		initLeftMenuItem();
		mDrawerList.setAdapter(new LeftMenuAdapter(this, mLeftMenuItems));

		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, R.string.drawer_open,
				R.string.drawer_close) {
			public void onDrawerClosed(View view) {
				super.onDrawerClosed(view);
				setMainTitle(mTitle);
				supportInvalidateOptionsMenu();
				mShouldFinish = false;
			}

			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
				setMainTitle(getResources().getString(R.string.app_name));
				supportInvalidateOptionsMenu();
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);

	}
	
	public void showSplashScreen() {
		mSplashScreenDialog = new Dialog(this, R.style.SplashScreenStyle);
		mSplashScreenDialog.setContentView(R.layout.splash_screen);
		mSplashScreenDialog.show();

		TextView welcomeText = (TextView) mSplashScreenDialog
				.findViewById(R.id.splash_screen_welcome);
		welcomeText.setTypeface(sRobotoThin);

		mSplashScreenDialog.setOnCancelListener(new OnCancelListener() {

			@Override
			public void onCancel(DialogInterface dialogInterface) {
				mShouldSetContentView = false;
				finish();
			}
		});
		mSplashScreenDialog.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss(DialogInterface dialog) {
				if (mShouldSetContentView) {
					// TODO This is the first selected tab after app is opened
					selectItem(0);
				}
			}
		});
	}

	public void hideSplashScreen() {
		if (mSplashScreenDialog != null) {
			mSplashScreenDialog.dismiss();
			mSplashScreenDialog = null;
		}
	}

	private class DrawerItemClickListener implements
			ListView.OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			selectItem(id);
			parent.setSelection(position);
		}
	}

	private void initLeftMenuItem() {
		// TODO You can remove any line of code if you don't have info about
		// certain category,
		// or you can reorder them as you want

		mLeftMenuItems = new ArrayList<LeftMenuItem>();
		mLeftMenuItems.add(new LeftMenuItem(R.drawable.home, getString(R.string.home), LeftMenuItem.LEFT_MENU_HOME));
		
		//TODO If you want to add only pages from your site, delete form HERE
		mLeftMenuItems.add(new LeftMenuItem(R.drawable.aboutus, getString(R.string.about_us), LeftMenuItem.LEFT_MENU_ABOUT));
		mLeftMenuItems.add(new LeftMenuItem(R.drawable.contact, getString(R.string.contact), LeftMenuItem.LEFT_MENU_CONTACT));
		mLeftMenuItems.add(new LeftMenuItem(R.drawable.facebook, getString(R.string.facebook), LeftMenuItem.LEFT_MENU_FACEBOOK));
		mLeftMenuItems.add(new LeftMenuItem(R.drawable.youtube, getString(R.string.youtube), LeftMenuItem.LEFT_MENU_YOUTUBE_CHANNEL));
		mLeftMenuItems.add(new LeftMenuItem(R.drawable.ltwiter, getString(R.string.twitter), LeftMenuItem.LEFT_MENU_TWITTER));
		mLeftMenuItems.add(new LeftMenuItem(R.drawable.linkedin, getString(R.string.linked_in), LeftMenuItem.LEFT_MENU_LINKED_IN));
		mLeftMenuItems.add(new LeftMenuItem(R.drawable.pinterest, getString(R.string.pinterest), LeftMenuItem.LEFT_MENU_PINTEREST));
		mLeftMenuItems.add(new LeftMenuItem(R.drawable.flickr, getString(R.string.flickr), LeftMenuItem.LEFT_MENU_FLICKR));
		//to HERE, and add pages below
		
		mLeftMenuItems.add(new LeftMenuItem(R.drawable.send, getString(R.string.page_from_your_site), LeftMenuItem.LEFT_PAGE_FROM_SITE));
		
	}
	
	private void selectItem(long tag) {
		//TODO If you have added item in initLeftMenuItem(), this is the place
		//where you should add action on item click. No need to pay attention on
		//order you put items in initLeftMenuItem() method, like shown below:
		//(Facebook is in initLeftMenuItem() method added before Twitter, but
		//in this method it is added after Twitter, but ordering in left menu
		//is still: Facebook before Twitter)
		
		mDrawerLayout.closeDrawer(mDrawerList);
		fragment = AboutUsFragment.newInstance();
		long pos = mLeftMenuItems.get((int)tag).getTag();
		if(pos == LeftMenuItem.LEFT_MENU_HOME) {
			fragment = WebViewFragment.newInstance(getString(R.string.uri));
			mTitle = getString(R.string.home);
		}
		//TODO If you want to add only pages from your site, delete form HERE
		else if (pos == LeftMenuItem.LEFT_MENU_ABOUT) {
			fragment = AboutUsFragment.newInstance();
			mTitle = getString(R.string.about_us);
		} else if (pos == LeftMenuItem.LEFT_MENU_CONTACT) {
			fragment = ContactFragment.newInstance();
			mTitle = getString(R.string.contact);
		} else if (pos == LeftMenuItem.LEFT_MENU_TWITTER) {
			fragment = WebViewFragment.newInstance(getString(R.string.my_twitter));
			mTitle = getString(R.string.twitter);
		} else if (pos == LeftMenuItem.LEFT_MENU_FACEBOOK) {
			fragment = WebViewFragment.newInstance(getString(R.string.my_facebook));
			mTitle = getString(R.string.facebook);
		} else if (pos == LeftMenuItem.LEFT_MENU_LINKED_IN) {
			fragment = WebViewFragment.newInstance(getString(R.string.my_linked_in));
			mTitle = getString(R.string.linked_in);
		} else if (pos == LeftMenuItem.LEFT_MENU_PINTEREST) {
			fragment = WebViewFragment.newInstance(getString(R.string.my_pinterest));
			mTitle = getString(R.string.pinterest);
		} else if (pos == LeftMenuItem.LEFT_MENU_YOUTUBE_CHANNEL) {
			fragment = WebViewFragment.newInstance(getString(R.string.my_youtube));
			mTitle = getString(R.string.youtube);
		} else if (pos == LeftMenuItem.LEFT_MENU_FLICKR) {
			fragment = WebViewFragment.newInstance(getString(R.string.my_flickr));
			mTitle = getString(R.string.flickr);
		} 
		//to HERE, and add else-if like this below
		
		//TODO This is example for adding page from your site
		else if (pos == LeftMenuItem.LEFT_PAGE_FROM_SITE) {
			fragment = WebViewFragment.newInstance(getString(R.string.my_page_from_your_site));
			mTitle = getString(R.string.page_from_your_site);
		}
		getSupportFragmentManager()
				.beginTransaction()
				.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
				.replace(R.id.content_frame, fragment)
				.commitAllowingStateLoss();
	}

	@Override
	public void onBackPressed() {
		if(fragment instanceof WebViewFragment && ((WebViewFragment) fragment).getWebView().canGoBack()) {
				((WebViewFragment) fragment).getWebView().goBack();
				((WebViewFragment) fragment).setPogressBarWebView();
		} else {
			if (!mShouldFinish && !mDrawerLayout.isDrawerOpen(mDrawerList)) {
				makeToast(R.string.confirm_exit);
				mDrawerLayout.openDrawer(mDrawerList);
				mShouldFinish = true;
			} else if (!mShouldFinish
					&& mDrawerLayout.isDrawerOpen(mDrawerList)) {
				mDrawerLayout.closeDrawer(mDrawerList);
			} else {
				super.onBackPressed();
			}
		}
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		switch (item.getItemId()) {
		case R.id.action_settings:
			// TODO share
			Intent sharingIntent = new Intent(
					android.content.Intent.ACTION_SEND);
			sharingIntent.setType("text/plain");
			String shareBody = getString(R.string.uri);
			sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
					"Subject Here");
			sharingIntent
					.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
			startActivity(Intent.createChooser(sharingIntent, "Share via"));
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@SuppressLint("NewApi")
	public void setMainTitle(String title) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setTitle(title);
		} else {
			setTitle(title);
		}
	}
	
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	private class SyncData extends AsyncTask<Void, Void, Integer> {
		private MainActivity mMainActivity;

		public SyncData(MainActivity mainActivity) {
			this.mMainActivity = mainActivity;
		}

		@Override
		protected void onPreExecute() {
			mMainActivity.showSplashScreen();
		}

		@Override
		protected Integer doInBackground(Void... params) {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return 1;
		}

		@Override
		protected void onPostExecute(Integer result) {
			hideSplashScreen();
		}
	}
}
