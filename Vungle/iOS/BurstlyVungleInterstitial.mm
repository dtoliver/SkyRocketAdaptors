#include "BurstlyVungleInterstitial.h"

@implementation BurstlyVungleInterstitial

@synthesize delegate = _delegate;

- (id)initWithParams:(NSDictionary*)params
{
	if (self = [super init]) {
		[VGVunglePub setDelegate:self];
    }
    return self;
}

- (void)dealloc
{
	[VGVunglePub setDelegate:nil];
    [super dealloc];
}

- (void)loadInterstitialInBackground
{ [NSTimer scheduledTimerWithTimeInterval:0 target:self selector:[VGVunglePub adIsAvailable] ? @selector(reportSuccess) : @selector(reportFailure) userInfo:nil repeats:NO]; }

- (void)reportSuccess { [self.delegate interstitialDidLoadAd: self]; }
- (void)reportFailure { [self.delegate interstitial:self didFailToLoadAdWithError:nil]; }

- (void)cancelInterstitialLoading {}

- (void)presentInterstitial { [VGVunglePub playModalAd:self.delegate.viewControllerForModalPresentation animated:YES showClose:YES]; }

- (void)vungleViewWillAppear:(UIViewController*)viewController { [self.delegate interstitialWillPresentFullScreen:self]; [self.delegate interstitialDidPresentFullScreen:self]; }
- (void)vungleViewDidDisappear:(UIViewController*)viewController { [self.delegate interstitialDidDismissFullScreen:self]; }

@end