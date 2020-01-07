import {NativeModules} from 'react-native';

const {RNFirebaseAuthUi} = NativeModules;

export const signInFirebaseWithPhoneNumber = ({phoneNumber, languageCode}, successCallback) => {
    RNFirebaseAuthUi.signInWithPhoneNumber({phoneNumber, languageCode}, successCallback);
};
