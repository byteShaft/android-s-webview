package com.csform.android.androidwebview.model;

public class LeftMenuItem {
	
	public static final long LEFT_MENU_HOME = 11;
	public static final long LEFT_MENU_ABOUT = 12;
	public static final long LEFT_MENU_CONTACT = 13;
	public static final long LEFT_MENU_FACEBOOK = 14;
	public static final long LEFT_MENU_TWITTER = 15;
	public static final long LEFT_MENU_LINKED_IN = 16;
	public static final long LEFT_MENU_PINTEREST = 17;
	public static final long LEFT_MENU_YOUTUBE_CHANNEL = 18;
	public static final long LEFT_MENU_FLICKR = 19;
	public static final long LEFT_PAGE_FROM_SITE = 20;
	
	private int icon;
	private String name;
	private long tag;
	
	public LeftMenuItem(int icon, String name, long tag) {
		this.icon = icon;
		this.name = name;
		this.tag = tag;
	}

	public int getIcon() {
		return icon;
	}

	public void setIcon(int icon) {
		this.icon = icon;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getTag() {
		return tag;
	}
	public void setTag(long tag) {
		this.tag = tag;
	}
}
