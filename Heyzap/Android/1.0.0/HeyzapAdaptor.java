package com.heyzap.integration;

import java.util.Map;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.burstly.lib.component.IBurstlyAdaptor;
import com.burstly.lib.component.IBurstlyAdaptorListener;
import com.burstly.lib.ui.BurstlyView;
import com.heyzap.internal.Logger;
import com.heyzap.sdk.ads.BannerOverlay;
import com.heyzap.sdk.ads.HeyzapAds;
import com.heyzap.sdk.ads.InterstitialOverlay;
import com.heyzap.sdk.ads.OnAdDisplayListener;

/**
 * Burstly Integration of Heyzap's Ad Platform.
 * 
 * Burstly's sample integration code/documentation:
 * https://github.com/burstly/Android-AdaptorIntegrationSample
 */
public class HeyzapAdaptor implements IBurstlyAdaptor {
    /**
     * Flag for ad showing
     */
    static boolean isShowingNow;
    static boolean wasShowing;

    /**
     * Adaptor name received from server.
     */
    private String adaptorName;

    /**
     * Specifies whether current ad is an interstitial ad.
     */
    boolean isInterstitial;

    /**
     * Currents context. Strong reference is safe, because adaptors are being destroyed on
     * {@link BurstlyView#destroy()}.
     */
    Context context;

    /**
     * Listener used to inform BurstlySDK about the state of the integrated library.
     */
    IBurstlyAdaptorListener adaptorListener;

    /**
     * Constructs a new {@link HeyzapAdaptor} instance.
     */
    public HeyzapAdaptor(final Context context, final String viewId, final String adaptorName) {
        this.context = context;
        this.adaptorName = adaptorName;
        
        // initialize SDK
        HeyzapAds.start(context, getBurstlyAdDisplayListener());
        HeyzapAds.thirdParty = "burstly";
        
        isShowingNow = wasShowing = false;
        
        Log.i("HeyzapAdaptor", "HeyzapAdaptor()");
        Logger.log("Adapter: " + adaptorName + " instantiated, view id: " + viewId);
    }
    
    /**
     * Create new instance of AdsSDKResponseHandler,
     * this instance calls didLoad and failedToLoad on the adaptor
     * 
     * @return
     */
    protected OnAdDisplayListener getBurstlyAdDisplayListener() {
        return new OnAdDisplayListener() {
            public void onShow() {
                Log.i("HeyzapAdaptor", "onShow()");
                adaptorListener.onShow(getNetworkName());
            };
            public void onFailedToFetch() {
                Log.i("HeyzapAdaptor", "onFailedToFetch()");
                adaptorListener.failedToLoad(getNetworkName(), true, "Could not fetch ad."); 
            };
            public void onClick() {
                Log.i("HeyzapAdaptor", "onClick()");
                adaptorListener.adWasClicked(getNetworkName(), isInterstitial);
            };
            public void onHide() {
                Log.i("HeyzapAdaptor", "onHide()");
                adaptorListener.shownFullscreen(new IBurstlyAdaptorListener.FullscreenInfo(getNetworkName(), false));
                adaptorListener.onHide(adaptorName);
            };
            public void onFailedToShow() {
                Log.i("HeyzapAdaptor", "onFailedToShow()");
            };
            public void onAvailable() {
                Log.i("HeyzapAdaptor", "onAvailable()");
                adaptorListener.dismissedFullscreen(new IBurstlyAdaptorListener.FullscreenInfo(getNetworkName(), false));
                adaptorListener.didLoad(getNetworkName(), isInterstitial);
            };
        };
    }

    /**
     *
     * IBurstlyAdaptor implementation:
     *
     */

    @Override
    public void startTransaction(final Map<String, ?> paramsFromServer) throws IllegalArgumentException {
        // is_interstitial will be a parameter set-up in the Heyzap 3rd Party section of the Burstly.com Dashboard (?),
        // we'll need to send this extra parameter to the fetch_ad endpoint to specify whether to return banner/interstitial.
        if (paramsFromServer == null) {
            throw new IllegalArgumentException("Parameters from server cannot be null.");
        }
        
        // change to isBanner in Burstly UI:
        String isInterstitialFromServer  = (String)paramsFromServer.get("isInterstitial");
        isInterstitial = isInterstitialFromServer == null || (isInterstitialFromServer != null && isInterstitialFromServer.equalsIgnoreCase("YES"));

        Logger.log("Transaction started with parameters from server: " + paramsFromServer.toString());
        Log.i("HeyzapAdaptor", "startTransaction()");
    }

    @Override
    public View getNewAd() {
        Log.i("HeyzapAdaptor", "getNewAd()");
        
        if (isInterstitial && InterstitialOverlay.isAvailable()) {
            Log.i("HeyzapAdaptor", "getNewAd() return interstitial");
            return InterstitialOverlay.getInstance();
        } else if (BannerOverlay.isAvailable()) {
            Log.i("HeyzapAdaptor", "getNewAd() return banner");
            return BannerOverlay.getInstance();
        } else {
            BannerOverlay.display(context, Gravity.TOP);
            adaptorListener.didLoad(getNetworkName(), isInterstitial);
        }

        Log.i("HeyzapAdaptor", "getNewAd() return null");
        return null;
    }

    @Override
    public View precacheAd() {
        Log.i("HeyzapAdaptor", "precacheAd()");
        // only used to pre-cache banner-ads

        if (BannerOverlay.isAvailable()) {
            return BannerOverlay.getInstance();
        } else {
            // no ad is available, fetch:
            BannerOverlay.load(context);//, getBurstlyAdDisplayListener());
        }
        
        return null;
    }

    @Override
    public void precacheInterstitialAd() {
        Log.i("HeyzapAdaptor", "precacheInterstitialAd()");
        InterstitialOverlay.load(context, getBurstlyAdDisplayListener());
    }

    @Override
    public void showPrecachedInterstitialAd() {
        Log.i("HeyzapAdaptor", "showPrecachedInterstitialAd()");
        if (InterstitialOverlay.isAvailable()) {
            String error = "Interstitial could not be shown because one is showing now.";
            
            if (!isShowingNow) {
                // try to show the pre-cached ad:
                try {
                    InterstitialOverlay.display(context);
                    
                    Logger.log("Showing interstitial ad.");
                    
                    isShowingNow = true;

                    // tell Burstly we successfully loaded an ad:
                    adaptorListener.didLoad(getNetworkName(), isInterstitial);
                    
                    return;
                }
                catch (final ActivityNotFoundException anfe) {
                    error = anfe.getMessage();
                }
            }
            
            isShowingNow = false;
            
            // tell Burstly we didn't load an ad:
            adaptorListener.failedToLoad(getNetworkName(), isInterstitial, error);
        } else {
            adaptorListener.failedToLoad(getNetworkName(), isInterstitial, "No precached ad.");
        }
    }

    @Override
    public BurstlyAdType getAdType() {
        Log.i("HeyzapAdaptor", "getAdType(), isInterstitial: " + isInterstitial);
        return isInterstitial ? BurstlyAdType.INTERSTITIAL_AD_TYPE : BurstlyAdType.BANNER_AD_TYPE;
    }

    @Override
    public String getNetworkName() {
        Log.i("HeyzapAdaptor", "getNetworkName()");
        return adaptorName;
    }

    @Override
    public boolean supports(final String action) {
        Log.i("HeyzapAdaptor", "supports()");
        // Android-AdaptorIntegrationSample/BurstlySDK_Android_1.20.0.35106/docs/com/burstly/lib/component/IBurstlyAdaptor.html:
        // "Queries the adaptor for supported actions. Currently supported actions are: precacheInterstitial"
        
        return action.equals(AdaptorAction.PRECACHE_INTERSTITIAL.getCode()); // support precacheInterstial
    }

    @Override
    public void setAdaptorListener(final IBurstlyAdaptorListener listener) {
        Log.i("HeyzapAdaptor", "setAdaptorListener()");
        if (listener == null) {
            Logger.warn("IBurstlyAdaptorListener should not be null.");
        }
        adaptorListener = listener;
    }

    @Override
    public void destroy() {
        Log.i("HeyzapAdaptor", "destroy()");
        InterstitialOverlay.dismiss();
        BannerOverlay.dismiss();
        
        isInterstitial = false;
        context = null;
        
        Logger.log("Heyzap Adaptor destroyed.");
    }

    @Override
    public void pause() {
        Log.i("HeyzapAdaptor", "pause()");
        Logger.log("Heyzap Adapter pause.");

        wasShowing = isShowingNow;
        isShowingNow = false;

        if (InterstitialOverlay.getInstance() != null) {
            InterstitialOverlay.getInstance().hide();
        }
        if (BannerOverlay.getInstance() != null) {
            BannerOverlay.getInstance().hide();
        }
    }

    @Override
    public void stop() {
        Log.i("HeyzapAdaptor", "stop()");
        Logger.log("Heyzap Adapter stop.");
        
        isShowingNow = false;

        if (InterstitialOverlay.getInstance() != null) {
            InterstitialOverlay.dismiss();
        }
        if (BannerOverlay.getInstance() != null) {
            BannerOverlay.dismiss();
        }
    }

    @Override
    public void resume() {
        Log.i("HeyzapAdaptor", "resume()");
        Logger.log("Heyzap Adapter resume.");

        if (wasShowing) {
            if (InterstitialOverlay.getInstance() != null) {
                InterstitialOverlay.getInstance().show();
                isShowingNow = true;
            }
            if (BannerOverlay.getInstance() != null) {
                BannerOverlay.getInstance().show();
                isShowingNow = true;
            }
        }
    }

    @Override
    public void startViewSession() {
        Log.i("HeyzapAdaptor", "endViewSession()");
        Logger.log("Heyzap Adapter added to view hierarchy.");
    }

    @Override
    public void endViewSession() {
        Log.i("HeyzapAdaptor", "endViewSession()");
        Logger.log("Heyzap Adapter removed from view hierarchy.");
    }

    @Override
    public void endTransaction(final TransactionCode endCode) {
        Log.i("HeyzapAdaptor", "endTransaction()");
        Logger.log("Transaction ended with code: " + endCode.name());
    }
}