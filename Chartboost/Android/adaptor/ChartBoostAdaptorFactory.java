package com.cb.adaptor;

import java.util.Map;

import android.content.Context;

import com.burstly.lib.component.IBurstlyAdaptor;
import com.burstly.lib.feature.networks.IAdaptorFactory;
import com.chartboost.sdk.Chartboost;

public class ChartBoostAdaptorFactory implements IAdaptorFactory {
	
    /**
     * A key for context object being passed in parameters.
     */
    private static final String CONTEXT = "context";

    /**
     * A key for current BurstlyView id object being passed in parameters.
     */
    private static final String VIEW_ID = "viewId";

    /**
     * A key for adaptor name being passed in parameters.
     */
    private static final String ADAPTOR_NAME = "adaptorName";
    
    Chartboost mCB;

	@Override
	public IBurstlyAdaptor createAdaptor(Map<String, ?> params) {
		return new ChartBoostAdaptor((Context) params.get(CONTEXT), (String) params.get(VIEW_ID), (String) params.get(ADAPTOR_NAME));		
	}

	@Override
	public void destroy() {
	}
    
    @Override
    public String getAdaptorVersion() {
        return null;
    }
    
    @Override
    public String getSdkVersion() {
        return null;
    }

	@Override
	public void initialize(Map<String, ?> paramsFromServer) throws IllegalArgumentException {
	}
}
