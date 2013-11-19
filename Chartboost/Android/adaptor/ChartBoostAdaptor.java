package com.cb.adaptor;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import com.burstly.lib.component.IBurstlyAdaptor;
import com.burstly.lib.component.IBurstlyAdaptorListener;
import com.burstly.lib.component.IBurstlyAdaptorListener.FullscreenInfo;
import com.cb.utils.Logger;
import com.chartboost.sdk.Chartboost;


public class ChartBoostAdaptor implements IBurstlyAdaptor {
	
	private Chartboost mCB;
	
	private String appId;
	
	private String appSignature;
			
	private ChartBoostMDelegate mDelegate = new ChartBoostMDelegate();
	
	private Context mContext;	
	
	private String mNetworkName;

	private IBurstlyAdaptorListener mAdaptorListener;
	
	private boolean isMoreApps;
	
	public static class InternalChartBoostDelegate extends ChartBoostDelegateAdaptor {		

		private Reference<ChartBoostAdaptor> mAdaptor;
		
		public InternalChartBoostDelegate(final ChartBoostAdaptor adaptor){
			mAdaptor = new WeakReference<ChartBoostAdaptor>(adaptor);
		}
		
		@Override
		public void didCacheInterstitial(String paramString) {
			super.didCacheInterstitial(paramString);
			final ChartBoostAdaptor adaptor = mAdaptor.get();
			adaptor.mAdaptorListener.didLoad(adaptor.getNetworkName(), true);
			Logger.logDebug(this, "interstitial cached " + paramString);
		}

		@Override
		public void didFailToLoadInterstitial(String paramString) {
			super.didFailToLoadInterstitial(paramString);
			final ChartBoostAdaptor adaptor = mAdaptor.get();
			adaptor.mAdaptorListener.failedToLoad(adaptor.getNetworkName(), false, "");
			Logger.logDebug(this, "interstitial failed to load " + paramString);
		}

		@Override
		public void didClickInterstitial(String paramString) {
			super.didClickInterstitial(paramString);
			final ChartBoostAdaptor adaptor = mAdaptor.get();
			adaptor.mAdaptorListener.adWasClicked(adaptor.getNetworkName(), true);
			Logger.logDebug(this, "interstitial clicked" + paramString);
		}

		@Override
		public void didShowInterstitial(String paramString) {
			super.didShowInterstitial(paramString);
			final ChartBoostAdaptor adaptor = mAdaptor.get();
			final FullscreenInfo FULLSCREEN_INFO = new FullscreenInfo(adaptor.getNetworkName(), false);
			adaptor.mAdaptorListener.shownFullscreen(FULLSCREEN_INFO);
			Logger.logDebug(this, "interstitial shown " + paramString);
		}

		@Override
		public void didCloseInterstitial(String paramString) {
			super.didCloseInterstitial(paramString);
			final ChartBoostAdaptor adaptor = mAdaptor.get();
			final FullscreenInfo FULLSCREEN_INFO = new FullscreenInfo(adaptor.getNetworkName(), false);
			adaptor.mAdaptorListener.dismissedFullscreen(FULLSCREEN_INFO);
			Logger.logDebug(this, "interstitial closed " + paramString);
		}
		
		@Override
		public boolean shouldRequestInterstitial(String paramString) {
			Logger.logDebug(this, "shouldRequestInterstitial " + paramString);
			return true;
		}

		@Override
		public boolean shouldDisplayInterstitial(String paramString) {
			Logger.logDebug(this, "shouldDisplayInterstitial " + paramString);
			return true;
		}
		
		@Override
		public boolean shouldRequestMoreApps() {
			return true;
		}
		
		@Override
		public boolean shouldDisplayMoreApps() {
			return true;
		}		

		@Override
		public void didCacheMoreApps() {
			super.didCacheMoreApps();
			final ChartBoostAdaptor adaptor = mAdaptor.get();
			adaptor.mAdaptorListener.didLoad(adaptor.getNetworkName(), true);
			Logger.logDebug(this, "more apps cached ");
		}

		@Override
		public void didFailToLoadMoreApps() {
			super.didFailToLoadMoreApps();
			final ChartBoostAdaptor adaptor = mAdaptor.get();
			adaptor.mAdaptorListener.failedToLoad(adaptor.getNetworkName(), false, "");
			Logger.logDebug(this, "more apps failed to load ");
		}

		@Override
		public void didCloseMoreApps() {
			super.didCloseMoreApps();
			final ChartBoostAdaptor adaptor = mAdaptor.get();
			final FullscreenInfo FULLSCREEN_INFO = new FullscreenInfo(adaptor.getNetworkName(), false);
			adaptor.mAdaptorListener.dismissedFullscreen(FULLSCREEN_INFO);
			Logger.logDebug(this, "more apps closed ");	
		}

		@Override
		public void didClickMoreApps() {
			super.didClickMoreApps();
			final ChartBoostAdaptor adaptor = mAdaptor.get();
			adaptor.mAdaptorListener.adWasClicked(adaptor.getNetworkName(), true);
			Logger.logDebug(this, "more apps clicked");
		}

		@Override
		public void didShowMoreApps() {
			super.didShowMoreApps();
			final ChartBoostAdaptor adaptor = mAdaptor.get();
			final FullscreenInfo FULLSCREEN_INFO = new FullscreenInfo(adaptor.getNetworkName(), false);
			adaptor.mAdaptorListener.shownFullscreen(FULLSCREEN_INFO);
			Logger.logDebug(this, "more apps shown ");
		}
	}

	public ChartBoostAdaptor(Context context, String viewId, String adaptorName) {
		mContext = context;
		mNetworkName = adaptorName;
	}

	@Override
	public void destroy() {
		Logger.logDebug(this, "Adaptor destroyed.");
		mDelegate.unsetAdapterDelegate();
	}

	@Override
	public void endTransaction(TransactionCode endCode) {
		Logger.logDebug(this, "Transaction ended with code: " + endCode.name());
	}

	@Override
	public void endViewSession() {		
		Logger.logDebug(this, "View session ended.");
	}

	@Override
	public BurstlyAdType getAdType() {
		return BurstlyAdType.INTERSTITIAL_AD_TYPE;
	}

	@Override
	public String getNetworkName() {		
		return mNetworkName;
	}

	@Override
	public View getNewAd() {
		precacheInterstitialAd();
		return null;
	}

	@Override
	public void pause() {
	}

	@Override
	public View precacheAd() {
		return getNewAd();
	}

	@Override
	public void precacheInterstitialAd() {
		mCB.clearCache();
		mCB.clearImageCache();
		if(isMoreApps){
			mCB.cacheMoreApps();
		}else{
			mCB.cacheInterstitial();
		}
	}

	@Override
	public void resume() {
	}

    @Override
    public void setAdaptorListener(final IBurstlyAdaptorListener listener) {
        if (listener == null) {
            Logger.logWarning(this, "IBurstlyAdaptorListener should not be null.");
        }
        mAdaptorListener = listener;
    }

	@Override
	public void showPrecachedInterstitialAd() {
		if(!isMoreApps && mCB.hasCachedInterstitial()){
			Chartboost.sharedChartboost().showInterstitial();
		}else if(isMoreApps && mCB.hasCachedMoreApps()){
			Chartboost.sharedChartboost().showMoreApps();
		}else {
			mAdaptorListener.failedToLoad(mNetworkName, true, "No precached ad.");
		}		
	}

	@Override
	public void startTransaction(Map<String, ?> paramsFromServer)
			throws IllegalArgumentException {		
        mContext = (Context) paramsFromServer.get("context");
		appId = (String) paramsFromServer.get("appId");
		appSignature = (String) paramsFromServer.get("appSignature");
		
		String MoreApps = (String) paramsFromServer.get("isMoreApps");
		
		if(MoreApps.compareToIgnoreCase("true")==0){
			isMoreApps = true;
		}else{
			isMoreApps = false;
		}
		
		Logger.logDebug(this, "isMoreApps:" + isMoreApps);
		
		mCB = Chartboost.sharedChartboost();
		mDelegate.setAdapterDelegate(new InternalChartBoostDelegate(this));
		mCB.onCreate((Activity) mContext, appId, appSignature, mDelegate);
		mCB.onStart((Activity) mContext);
		mCB.startSession();
		mCB.setImpressionsUseActivities(true);
	}

	@Override
	public void startViewSession() {
		Logger.logDebug(this, "start view session");		
	}

	@Override
	public void stop() {
	}

	@Override
	public boolean supports(String arg0) {		
		return true;
	}
}
