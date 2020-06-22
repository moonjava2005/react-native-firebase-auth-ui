import { NativeModules } from 'react-native';
var RNFirebaseAuthUi = NativeModules.RNFirebaseAuthUi;
export var signInFirebaseWithPhoneNumber = function (_a, successCallback) {
    var phoneNumber = _a.phoneNumber, languageCode = _a.languageCode;
    RNFirebaseAuthUi.signInWithPhoneNumber({ phoneNumber: phoneNumber, languageCode: languageCode }, successCallback);
};
//# sourceMappingURL=index.js.map