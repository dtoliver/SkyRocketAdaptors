//
//  BurstlyChartboostAdaptor.m
//  Burstly
//
// Copyright 2013 Burstly Inc. All rights reserved.
//

#import "BurstlyChartboostAdaptor.h"

@implementation BurstlyChartboostAdaptor

#pragma mark - BurstlyAdNetworkAdaptor Protocol

- (id)initAdNetworkWithParams: (NSDictionary*)params
{
    self = [super init];
    if (self != nil) {
    }
    return self;
}

- (NSString *)adaptorVersion {
    return @"1";
}

- (NSString *)sdkVersion {
    //Chartboost doesn't have special method/constant for sdk versioning, added manually
    return @"3.2.1";
}

- (BOOL)isIdiomSupported: (UIUserInterfaceIdiom)idiom {
    return YES;
}

- (BurstlyAdPlacementType)adPlacementTypeFor: (NSDictionary *)params {
    if ([params objectForKey: @"isInterstitial"]) {
        return BurstlyAdPlacementTypeInterstitial;
    }
    return BurstlyAdPlacementTypeBanner;
}

- (id<BurstlyAdBannerProtocol>)newBannerAdWithParams: (NSDictionary *)params
                                            andError: (NSError **)error
{
    NSLog(@"Chartboost adaptor includes only interstitial ads");
    return nil;
}


- (id<BurstlyAdInterstitialProtocol>)newInterstitialAdWithParams: (NSDictionary *)params
                                                        andError: (NSError **)error
{
    BurstlyChartboostInterstitial *adInterstitial = [[BurstlyChartboostInterstitial alloc] initWithParams: params];
    return adInterstitial;
}


@end
