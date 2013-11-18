package com.heyzap.integration;

import java.util.Map;

import android.content.Context;

import com.burstly.lib.component.IBurstlyAdaptor;
import com.burstly.lib.feature.networks.IAdaptorFactory;
import com.heyzap.internal.Logger;

/**
 * Factory class for {@link HeyzapAdaptor}.
 */
public class HeyzapAdaptorFactory implements IAdaptorFactory {
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
    private static final String ADAPTOR_NAME = "heyzap";

    @Override
    public void initialize(final Map<String, ?> params) throws IllegalArgumentException {
        Logger.log("HeyzapAdaptorFactory Initialization called");
    }

    @Override
    public IBurstlyAdaptor createAdaptor(final Map<String, ?> params) {
        if (params == null) {
            Logger.debug("HeyzapAdaptorFactory createAdaptor parameters cannot be null");
            return null;
        }
        
        final Object context = params.get(CONTEXT);
        final Object viewId = params.get(VIEW_ID);
        final Object adaptorName = params.get(ADAPTOR_NAME);
        
        return new HeyzapAdaptor((Context)context, (String)viewId, (String)adaptorName);
    }

    @Override
    public void destroy() {
        Logger.log("HeyzapAdaptorFactory destroyed.");
    }

}