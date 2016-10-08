package com.csform.android.androidwebview.fragment;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.webkit.DownloadListener;
import android.webkit.URLUtil;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.csform.android.androidwebview.R;

public class WebViewFragment extends Fragment implements OnTouchListener,
		Handler.Callback {

	public static final int OBTAIN_PROGRESS_EVERY = 100;
	public static final String ARG_URL = "url";
	private static final int CLICK_ON_WEBVIEW = 1;
	private static final int CLICK_ON_URL = 2;
	private final Handler handler = new Handler(this);
	private WebViewClient client;
	private ProgressBar mProgressBar;
	private WebView mWebView;
	private Handler mHandler = new Handler();

	private Runnable mRunnable = new Runnable() {

		@Override
		public void run() {
			int progress = mWebView.getProgress() + (int)((100 - mWebView.getProgress())/10);
			mProgressBar.setProgress(progress);
			if (progress != 100) {
				mHandler.postDelayed(this, OBTAIN_PROGRESS_EVERY);
			}
		}
	};
	
	public WebView getWebView() {
		return mWebView;
	}
	
	public void setPogressBarWebView() {
		mHandler = new Handler();
		mHandler.postDelayed(mRunnable, OBTAIN_PROGRESS_EVERY);
		mRunnable = new Runnable() {
			
			@Override
			public void run() {
				int progress = mWebView.getProgress() + (int)((100 - mWebView.getProgress())/10);
				mProgressBar.setProgress(progress);
				if (progress != 100) {
					mHandler.postDelayed(this, OBTAIN_PROGRESS_EVERY);
				}
			}
		};
	}

	public static WebViewFragment newInstance(String URL) {
		WebViewFragment webViewFragment = new WebViewFragment();
		Bundle args = new Bundle(1);
		args.putString(ARG_URL, URL);
		webViewFragment.setArguments(args);
		return webViewFragment;
	}
	
	@SuppressLint("SetJavaScriptEnabled")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View root = inflater.inflate(R.layout.web_view, container, false);
		setHasOptionsMenu(true);
		mWebView = (WebView) root.findViewById(R.id.web_view);
		mProgressBar = (ProgressBar) root.findViewById(R.id.progress_bar);
		mWebView.loadUrl(getArguments().getString(ARG_URL));
		mHandler.postDelayed(mRunnable, OBTAIN_PROGRESS_EVERY);
		// to open links in this webview, use this line of code:
//		mWebView.setWebViewClient(new WebViewClient());
		// to enable JavaScript, use this line of code:
		mWebView.getSettings().setJavaScriptEnabled(true);
		// to load pages faster, use this line of code:
		mWebView.getSettings().setAppCacheEnabled(true);
		// to enable pinch to zoom, use this line of code:
		mWebView.getSettings().setBuiltInZoomControls(true);
		client = new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.i("TAG", url);
                if (url.startsWith("tel:")) {
					Intent intent = new Intent(Intent.ACTION_DIAL,
							Uri.parse(url));
					startActivity(intent);
					return true;
				} else if(url.startsWith("mailto:")) {
					Intent intent = new Intent(Intent.ACTION_VIEW,
	                        Uri.parse(url)); 
	                startActivity(intent); 
					return true;
				} else if(url.startsWith("sms:")) {
					Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
					startActivity(intent);
					return true;
				} else if(url.startsWith("vnd:")) {
					Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
					startActivity(intent);
					return true;
				} else if(url.startsWith("https://www.youtube.com/") || url.startsWith("http://www.youtube.com/")) {
					Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
					startActivity(intent);
					return true;
				}
				setPogressBarWebView();
				return false;
			}
		};
		mWebView.setWebViewClient(client);
        mWebView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String s, String s1, String s2, String s3, long l) {
                Log.i("TAG", s);
                final String fileName= URLUtil.guessFileName(s,s2,s3);
                android.util.Log.d("Applog","fileName:"+fileName);
                if (s.contains("mp3")) {
                    Uri source = Uri.parse(s);
                    // Make a new request pointing to the .apk url
                    DownloadManager.Request request = new DownloadManager.Request(source);
                    // appears the same in Notification bar while downloading
                    request.setTitle(fileName);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                        request.allowScanningByMediaScanner();
                        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                    }
                    // save the file in the "Downloads" folder of SDCARD
                    request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName);
                    // get download service and enqueue file
                    DownloadManager manager = (DownloadManager) getActivity().getApplicationContext()
                            .getSystemService(Context.DOWNLOAD_SERVICE);
                    manager.enqueue(request);
                }
            }
        });
		mWebView.setVerticalScrollBarEnabled(false);

		return root;
	}

	@Override
	public boolean handleMessage(Message msg) {
		// TODO Auto-generated method stub
		if (msg.what == CLICK_ON_URL) {
			handler.removeMessages(CLICK_ON_WEBVIEW);
			return true;
		}
		if (msg.what == CLICK_ON_WEBVIEW) {
			Toast.makeText(getActivity(), "WebView clicked", Toast.LENGTH_SHORT)
					.show();
			return true;
		}
		return false;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		if (v.getId() == R.id.web_view
				&& event.getAction() == MotionEvent.ACTION_DOWN) {
			handler.sendEmptyMessageDelayed(CLICK_ON_WEBVIEW, 500);
		}
		return false;
	}
}