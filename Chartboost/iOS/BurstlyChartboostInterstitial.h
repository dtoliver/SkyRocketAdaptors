//
//  BurstlyChartboostInterstitial.h
//  Burstly
//
// Copyright 2013 Burstly Inc. All rights reserved.
//

#import "BurstlyChartboostAdBase.h"
#import "BurstlyAdInterstitialProtocol.h"
#import "Chartboost.h"

@interface BurstlyChartboostInterstitial : BurstlyChartboostAdBase <BurstlyAdInterstitialProtocol, ChartboostDelegate>{
    BOOL _requestCancelled;
    Chartboost* _interstitial;
}


#pragma mark - Constructor
- (id)initWithParams: (NSDictionary *)params;

@end
