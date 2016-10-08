package com.csform.android.androidwebview.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.csform.android.androidwebview.BaseActivity;
import com.csform.android.androidwebview.MapActivity;
import com.csform.android.androidwebview.R;


public class ContactFragment extends Fragment implements OnClickListener {
	
	//TODO Change here your contact information
	private String address = "I. F. Jukića 11, Banja Luka, Bosnia and Herzegovina";
	private String officePhone = "+387 65 172 455";
	private String mobilePhone = "+387 65 864 314";
	private String faxPhone = null;
	private String email = "dev@csform.com";
	private String web = "www.csform.com";
	
	public static ContactFragment newInstance() {
		ContactFragment contactFragment = new ContactFragment();
		return contactFragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View root = inflater.inflate(R.layout.contact, container, false);
		
		//Find every TextView so you can change fonts and data
		TextView companyName = (TextView) root.findViewById(R.id.contact_company_name);
		TextView telTitle = (TextView) root.findViewById(R.id.contact_phone);
		ImageView telCallView = (ImageView) root.findViewById(R.id.contact_phone_icon);
		TextView mobTitle = (TextView) root.findViewById(R.id.contact_mob);
		ImageView mobCallView = (ImageView) root.findViewById(R.id.contact_mob_icon);
		TextView faxTitle = (TextView) root.findViewById(R.id.contact_fax);
		ImageView faxCallView = (ImageView) root.findViewById(R.id.contact_fax_icon);
		TextView emailTitle = (TextView) root.findViewById(R.id.contact_email);
		ImageView emailSendView = (ImageView) root.findViewById(R.id.contact_email_icon);
		TextView webTitle = (TextView) root.findViewById(R.id.contact_web);
		ImageView websiteVisitView = (ImageView) root.findViewById(R.id.contact_web_icon);
		TextView openMapTitle = (TextView) root.findViewById(R.id.contact_address);
		ImageView openMapView = (ImageView) root.findViewById(R.id.contact_address_icon);
		
		if (!TextUtils.isEmpty(officePhone)) {
			telTitle.setText(officePhone);
		} else {
			telTitle.setText(R.string.no_data_available);
		}
		if (!TextUtils.isEmpty(mobilePhone)) {
			mobTitle.setText(mobilePhone);
		} else {
			mobTitle.setText(R.string.no_data_available);
		}
		if (!TextUtils.isEmpty(faxPhone)) {
			faxTitle.setText(faxPhone);
		} else {
			faxTitle.setText(R.string.no_data_available);
		}
		if (!TextUtils.isEmpty(email)) {
			emailTitle.setText(email);
		} else {
			emailTitle.setText(R.string.no_data_available);
		}
		if (!TextUtils.isEmpty(web)) {
			webTitle.setText(web);
		} else {
			webTitle.setText(R.string.no_data_available);
		}
		if (!TextUtils.isEmpty(address)) {
			openMapTitle.setText(address);
		} else {
			openMapTitle.setText(R.string.no_data_available);
		}
		if (!TextUtils.isEmpty(address)) {
			openMapTitle.setText(address);
		} else {
			openMapTitle.setText(R.string.no_data_available);
		}

		//Adjust fonts:
		companyName.setTypeface(BaseActivity.sRobotoThin);
		telTitle.setTypeface(BaseActivity.sRobotoLight);
		faxTitle.setTypeface(BaseActivity.sRobotoLight);
		mobTitle.setTypeface(BaseActivity.sRobotoLight);
		emailTitle.setTypeface(BaseActivity.sRobotoLight);
		webTitle.setTypeface(BaseActivity.sRobotoLight);
		openMapTitle.setTypeface(BaseActivity.sRobotoLight);
		
		
		openMapView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getActivity(), MapActivity.class));
			}
		});
		
		//Set listeners:
		telCallView.setOnClickListener(this);
		mobCallView.setOnClickListener(this);
		faxCallView.setOnClickListener(this);
		emailSendView.setOnClickListener(this);
		websiteVisitView.setOnClickListener(this);
		
		return root;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.contact_phone_icon:
			if (officePhone != null) {
				startCall(officePhone);
			} else {
				makeToast(R.string.number_not_available);
			}
			break;
		case R.id.contact_mob_icon:
			if (mobilePhone != null) {
				startCall(mobilePhone);
			} else {
				makeToast(R.string.number_not_available);
			}
			break;
		case R.id.contact_fax_icon:
			if (faxPhone != null) {
				startCall(faxPhone);
			} else {
				makeToast(R.string.number_not_available);
			}
			break;
		case R.id.contact_email_icon:
			if (email != null) {
				sendMail(email);
			} else {
				makeToast(R.string.email_not_available);
			}
			break;
		case R.id.contact_web_icon:
			if (web != null) {
				visitWebsite(web);
			} else {
				makeToast(R.string.website_not_available);
			}
			break;
		}
	}
	
	private void makeToast(int res) {
		((BaseActivity) getActivity()).makeToast(res);
	}
	
	private void startCall(String number) {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setData(Uri.parse("tel:" + number));
		startActivity(intent);
	}
	
	private void sendMail(String email) {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setData(Uri.parse("mailto:" + email));
		startActivity(intent);
	}
	
	private void visitWebsite(String website) {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setData(Uri.parse("http://" + website));
		startActivity(intent);
	}
}
