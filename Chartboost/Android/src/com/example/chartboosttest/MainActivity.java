package com.example.chartboosttest;

import android.os.Bundle;
import android.provider.Settings.Secure;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.burstly.lib.conveniencelayer.Burstly;
import com.burstly.lib.conveniencelayer.BurstlyActivity;
import com.burstly.lib.conveniencelayer.BurstlyInterstitial;
import com.chartboost.sdk.Chartboost;
import com.chartboost.sdk.impl.r;

public class MainActivity extends BurstlyActivity {
	
	BurstlyInterstitial mInterstitial;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        
		setContentView(R.layout.activity_main);
	
		Burstly.init(this, "wcVhpRjwk0S5cflC2_Sv5Q");
				
		mInterstitial = new BurstlyInterstitial(this, "0850986989081234298", "custom", false);
		
		//mInterstitial.cacheAd();
		
		//mInterstitial.showAd();
        
		String android_id = Secure.getString(getBaseContext().getContentResolver(),Secure.ANDROID_ID);
        
		Log.e("Android deviceId", android_id);
	}
	
	public void clickShowAd(View v){
		if(mInterstitial.hasCachedAd()){
			mInterstitial.showAd();
		}
	}
	
	public void clickCacheAd(View v){
		mInterstitial.cacheAd();		
	}

	@Override
	public void onResume() {
	    Burstly.onResumeActivity(this);
	    super.onResume();
	}

	@Override
	public void onPause() {
	    Burstly.onPauseActivity(this);
	    super.onPause();
	}

	@Override
	public void onDestroy() {
	    Burstly.onDestroyActivity(this);
	    super.onDestroy();
	}

}
