
#import "RNFirebaseAuthUi.h"
#import <FirebaseUI/FUIAuth.h>
#import <FirebaseUI/FUIPhoneAuth.h>
#define RNFirebaseUiWrongCode  17044;
typedef NS_ENUM(NSInteger, RNFirebaseUiErrorCode) {
  RNCPositionErrorCancel = 1,
};
@implementation RNFirebaseAuthUi
{
  FUIPhoneAuth *phoneProvider;
  RCTResponseSenderBlock _successCallback;
  RCTResponseSenderBlock _errorCallback;
}
- (dispatch_queue_t)methodQueue
{
    return dispatch_get_main_queue();
}

- (void)authUI:(FUIAuth *)authUI
    didSignInWithAuthDataResult:(nullable FIRAuthDataResult *)authDataResult
                            URL:(nullable NSURL *)url
                          error:(nullable NSError *)error
{
  if(error!=nil)
  {
    NSInteger _errorCode=error.code;
    if(_errorCode==1)
    {
      _errorCallback(@[@{
                         @"code":@(1),
                         @"message":@"User cancels"
      }]);
    }
  }
  else if(authDataResult!=nil)
  {
    FIRUser *firUser=authDataResult.user;
    if(firUser!=nil)
    {
      NSString *phoneNumber=firUser.phoneNumber;
      [firUser getIDTokenForcingRefresh:YES completion:^(NSString *token, NSError *_Nullable error) {
        if(error==nil&&token!=nil) {
          self->_successCallback(@[@{
            @"token":token,
            @"phoneNumber":phoneNumber!=nil?phoneNumber:[NSNull null]
          }]);
        }
      }];
    }
  }
}

RCT_EXPORT_MODULE()
RCT_EXPORT_METHOD(signInWithPhoneNumber:(NSDictionary*) options withSuccessCallback:(RCTResponseSenderBlock)successBlock
errorCallback:(RCTResponseSenderBlock)errorBlock) {
  _successCallback=successBlock;
  _errorCallback=errorBlock;
  FUIAuth *fuiAuth=[FUIAuth defaultAuthUI];
  if(phoneProvider==nil)
  {
    [fuiAuth auth].languageCode=@"vi";
    fuiAuth.delegate = self;
    phoneProvider = [[FUIPhoneAuth alloc] initWithAuthUI:[FUIAuth defaultAuthUI]];
    [FUIAuth defaultAuthUI].providers = @[phoneProvider];
  }
  NSString *phoneNumber;
  if(options!=nil)
  {
    if([options objectForKey:@"phoneNumber"]!=nil)
    {
      phoneNumber=options[@"phoneNumber"];
    }
    if([options objectForKey:@"languageCode"]!=nil)
    {
      [fuiAuth auth].languageCode=options[@"languageCode"];
    }
  }
  UIViewController *rootViewController = [UIApplication sharedApplication].delegate.window.rootViewController;
  [phoneProvider signInWithPresentingViewController:rootViewController phoneNumber:phoneNumber];
}

@end
  
