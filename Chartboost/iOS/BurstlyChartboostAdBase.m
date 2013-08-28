//
//  BurstlyChartboostAdBase.m
//  Burstly
//
// Copyright 2013 Burstly Inc. All rights reserved.
//

#import "BurstlyChartboostAdBase.h"

#define BURSTLY_CHARTBOOST_APP_ID_KEY                @"appId"
#define BURSTLY_CHARTBOOST_APP_SIGNATURE_KEY                @"appSignature"

@implementation BurstlyChartboostAdBase

#pragma mark - Constructor  / Destructor
- (id)initWithParams: (NSDictionary *)params
{
    self = [super init];
    if (self != nil) {
        _params = [params retain];
    }
    return self;
}

- (void)dealloc
{
    [_params release];
    _params = nil;
    [super dealloc];
}

#pragma mark - Ad Properties
- (NSString *)appId {
    return [_params objectForKey: BURSTLY_CHARTBOOST_APP_ID_KEY];
}

- (NSString *)appSignature {
    return [_params objectForKey: BURSTLY_CHARTBOOST_APP_SIGNATURE_KEY];
}


@end
