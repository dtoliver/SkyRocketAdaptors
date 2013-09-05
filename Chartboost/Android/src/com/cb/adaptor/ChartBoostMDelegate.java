package com.cb.adaptor;

import com.chartboost.sdk.ChartboostDelegate;

public class ChartBoostMDelegate extends ChartBoostDelegateAdaptor {

	private ChartboostDelegate activityDelegate = new ChartBoostDelegateAdaptor();
	private ChartboostDelegate adapterDelegate = new ChartBoostDelegateAdaptor();
	
	public void setActivityDelegate(ChartboostDelegate activityDelegate) {
		this.activityDelegate = activityDelegate;
	}
	
	public void unsetActivityDelegate() {
		 activityDelegate = new ChartBoostDelegateAdaptor();
	}
	
	public void setAdapterDelegate(ChartboostDelegate adapterDelegate) {
		this.adapterDelegate = adapterDelegate;
	}
	
	public void unsetAdapterDelegate() {
		adapterDelegate = new ChartBoostDelegateAdaptor();
	}
	
	@Override
	public void didCacheMoreApps() {		
		activityDelegate.didCacheMoreApps();
		adapterDelegate.didCacheMoreApps();
	}

	@Override
	public void didFailToLoadMoreApps() {		
		activityDelegate.didFailToLoadMoreApps();
		adapterDelegate.didFailToLoadMoreApps();
	}

	@Override
	public void didCloseMoreApps() {		
		activityDelegate.didCloseMoreApps();
		adapterDelegate.didCloseMoreApps();
	}

	@Override
	public void didClickMoreApps() {		
		activityDelegate.didClickMoreApps();
		adapterDelegate.didClickMoreApps();
	}

	@Override
	public void didShowMoreApps() {		
		activityDelegate.didShowMoreApps();
		adapterDelegate.didShowMoreApps();
	}

	public void didCacheInterstitial(String paramString) {
		activityDelegate.didCacheInterstitial(paramString);
		adapterDelegate.didCacheInterstitial(paramString);
	}

	public void didFailToLoadInterstitial(String paramString) {
		activityDelegate.didFailToLoadInterstitial(paramString);
		adapterDelegate.didFailToLoadInterstitial(paramString);
	}

	public void didDismissInterstitial(String paramString) {
		activityDelegate.didDismissInterstitial(paramString);
		adapterDelegate.didDismissInterstitial(paramString);
	}

	public void didCloseInterstitial(String paramString) {
		activityDelegate.didCloseInterstitial(paramString);
		adapterDelegate.didCloseInterstitial(paramString);
	}

	public void didClickInterstitial(String paramString) {
		activityDelegate.didClickInterstitial(paramString);
		adapterDelegate.didClickInterstitial(paramString);
	}

	public void didShowInterstitial(String paramString) {
		activityDelegate.didShowInterstitial(paramString);
		adapterDelegate.didShowInterstitial(paramString);
	}

}