#import "BurstlyAdInterstitialProtocol.h"
#import <vunglepub/vunglepub.h>

@interface BurstlyVungleInterstitial : NSObject <BurstlyAdInterstitialProtocol, VGVunglePubDelegate> {}
- (id)initWithParams:(NSDictionary*)params;
@end