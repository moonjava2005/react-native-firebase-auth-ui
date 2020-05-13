declare type Params = {
    phoneNumber: string,
    languageCode?: string
}

declare function signInFirebaseWithPhoneNumbere({phoneNumber, languageCode}: Params, successCallback: (result?: any) => void): void

export {
    Params
}
export default signInFirebaseWithPhoneNumbere
