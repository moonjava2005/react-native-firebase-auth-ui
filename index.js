import {NativeModules} from 'react-native';

const {RNFirebaseAuthUi} = NativeModules;

export const signInFirebaseWithPhoneNumber = ({phoneNumber, languageCode}) => {
    return new Promise((resolve, reject) => {
        RNFirebaseAuthUi.signInWithPhoneNumber({phoneNumber, languageCode}, (_result) => {
            resolve(_result);
        }, (_error) => {
            reject(_error);
        });
    })
};
