#include "BurstlyVungleAdaptor.h"

#import "BurstlyVungleInterstitial.h"

@implementation BurstlyVungleAdaptor

- (id)initAdNetworkWithParams: (NSDictionary*)params {
	self = [super init];
	if (self != nil) {
        NSString *app = [params objectForKey:@"appId"];
        [VGVunglePub startWithPubAppID:app];
	}
	return self;
}

- (NSString*)adaptorVersion { return @"1.0.0"; }
- (NSString*)sdkVersion { return [VGVunglePub versionString]; }
- (BOOL)isIdiomSupported: (UIUserInterfaceIdiom)idiom { return YES; }

- (BurstlyAdPlacementType)adPlacementTypeFor:(NSDictionary*)params { return BurstlyAdPlacementTypeInterstitial; }

- (id<BurstlyAdBannerProtocol>)newBannerAdWithParams: (NSDictionary*)params andError: (NSError**)error { return nil; }

- (id<BurstlyAdInterstitialProtocol>)newInterstitialAdWithParams:(NSDictionary*)params andError:(NSError**)error
{ return [[BurstlyVungleInterstitial alloc] initWithParams:params]; }

@end