interface Params {
    phoneNumber: string;
    languageCode?: string;
}
export declare const signInFirebaseWithPhoneNumber: ({ phoneNumber, languageCode }: Params, successCallback: (result?: any) => void) => void;
export {};
