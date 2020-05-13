import {NativeModules} from 'react-native';

const {RNFirebaseAuthUi} = NativeModules;

interface Params {
    phoneNumber: string,
    languageCode?: string
}

export const signInFirebaseWithPhoneNumber = ({phoneNumber, languageCode}: Params, successCallback: (result?: any) => void): void => {
    RNFirebaseAuthUi.signInWithPhoneNumber({phoneNumber, languageCode}, successCallback);
};
