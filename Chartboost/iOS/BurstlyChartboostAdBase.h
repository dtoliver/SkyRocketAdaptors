//
//  BurstlyChartboostAdBase.h
//  Burstly
//
// Copyright 2013 Burstly Inc. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface BurstlyChartboostAdBase : NSObject
{
    NSDictionary *_params;
}

#pragma mark - Constructor
- (id)initWithParams: (NSDictionary *)params;

#pragma mark - Ad Properties
- (NSString *)appId;
- (NSString *)appSignature;
@end
