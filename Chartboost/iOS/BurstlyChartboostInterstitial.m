//
//  BurstlyChartboostInterstitial.m
//  Burstly
//
// Copyright 2013 Burstly Inc. All rights reserved.
//

#import "BurstlyChartboostInterstitial.h"

#define BURSTLY_CHARTBOOST_INTERSTITIAL_ERROR_DOMAIN        @"com.burstly.chartboostinterstitial"

@implementation BurstlyChartboostInterstitial

#pragma mark - Constructor / Destructor
- (id)initWithParams: (NSDictionary *)params
{
    self = [super initWithParams: params];
    if (self != nil) {
        _interstitial = [Chartboost sharedChartboost];
        
        _interstitial.appId = [self appId];
        _interstitial.appSignature = [self appSignature];
        
        _interstitial.delegate = self;
        
        // Begin a user session. Must not be dependent on user actions or any prior network requests.
        [_interstitial startSession];
    }
    return self;
}

- (void)dealloc
{
    [self cancelInterstitialLoading];
    [super dealloc];
}

#pragma mark - Burstly Ad Interstitial Protocol

@synthesize delegate = _delegate;

- (void)loadInterstitialInBackground
{
    _requestCancelled = NO;
    [_interstitial cacheInterstitial];
}

- (void)cancelInterstitialLoading
{
    _requestCancelled = YES;
    _interstitial.delegate = nil;
    [_interstitial release];
    _interstitial = nil;
}

- (void)presentInterstitial
{
    if (_requestCancelled) {
        return;
    }
    if ([_interstitial hasCachedInterstitial]) {
        _interstitial.delegate = self;
        [_interstitial showInterstitial];
    }
    else
    {
        NSString *errorDescription = [NSString stringWithFormat: @"Chartboost interstitial is not ready."];
        NSError *error = [NSError errorWithDomain: BURSTLY_CHARTBOOST_INTERSTITIAL_ERROR_DOMAIN
                                             code: 1
                                         userInfo: [NSDictionary dictionaryWithObject: errorDescription
                                                                               forKey: NSLocalizedDescriptionKey]];
        [self.delegate interstitial: self didFailToPresentFullScreenWithError: error];
    }
}

#pragma mark - Chartboost Delegate Protocol
// All of the delegate methods below are optional.

// Called before requesting an interestitial from the back-end
- (BOOL)shouldRequestInterstitial:(NSString *)location
{
    if (_requestCancelled) {
        return NO;
    }
    return YES;
}

// Called when an interstitial has been received, before it is presented on screen
// Return NO if showing an interstitial is currently innapropriate, for example if the user has entered the main game mode.
- (BOOL)shouldDisplayInterstitial:(NSString *)location
{
    if (_requestCancelled) {
        return NO;
    }
    [self.delegate interstitialWillPresentFullScreen: self];
    return YES;
}

// Called when an interstitial has been received and cached.
- (void)didCacheInterstitial:(NSString *)location
{
    [self.delegate interstitialDidLoadAd: self];
}

// Called when an interstitial has failed to come back from the server
- (void)didFailToLoadInterstitial:(NSString *)location
{
    if (_requestCancelled) {
        return;
    }
    NSString *errorDescription = [NSString stringWithFormat: @"%@: can't get the interstitial from server.",
                                  self];
    NSLog(@"%@",errorDescription);
    NSError  *error            = [NSError errorWithDomain: BURSTLY_CHARTBOOST_INTERSTITIAL_ERROR_DOMAIN
                                                     code: 1
                                                 userInfo: [NSDictionary dictionaryWithObject: errorDescription
                                                                                       forKey: NSLocalizedDescriptionKey]];
    [self.delegate interstitial: self
       didFailToLoadAdWithError: error];
}

// Called when the user dismisses the interstitial
// If you are displaying the add yourself, dismiss it now.
- (void)didDismissInterstitial:(NSString *)location
{
}

// Same as above, but only called when dismissed for a close
- (void)didCloseInterstitial:(NSString *)location
{
    [self.delegate interstitialDidDismissFullScreen: self];
}

// Same as above, but only called when dismissed for a click
- (void)didClickInterstitial:(NSString *)location
{
    [self.delegate interstitialWasClicked: self];
    [self.delegate interstitialDidDismissFullScreen: self];
}

// Whether Chartboost should show ads in the first session
// Defaults to YES
- (BOOL)shouldRequestInterstitialsInFirstSession
{
    return YES;
}
@end
