
package info.moonjava.firebase.auth.ui;

import android.app.Activity;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.FirebaseUiException;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;

public class RNFirebaseAuthUiModule extends ReactContextBaseJavaModule implements FirebaseCallbackListener {

    private static final String MODULE_NAME = "RNFirebaseAuthUi";
    private static ReactApplicationContext reactContext;
    private static final int PHONE_AUTH_REQUEST_CODE = 2020;
    private static final String NO_ACTIVITY_ERROR_CODE = "NO_ACTIVTY";
    private static final String REQUEST_EXISTING_ERROR_CODE = "REQUEST_EXISTING";
    private static final String CANCEL_ERROR_CODE = "CANCEL";
    private static final String NO_RESPONSE_ERROR_CODE = "NO_RESPONSE";
    private static final String GET_ID_TOKEN_FAILED_ERROR_CODE = "GET_ID_TOKEN_FAILED";
    private Callback currentSuccessCallback;

    public RNFirebaseAuthUiModule(@NonNull ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
        FirebaseCallbackManager.getInstance().addListener(this);
    }

    @NonNull
    @Override
    public String getName() {
        return MODULE_NAME;
    }

    @ReactMethod
    public void signInWithPhoneNumber(ReadableMap options, Callback successCallback) {
        this.currentSuccessCallback = successCallback;
        final Activity activity = getCurrentActivity();
        if (activity == null) {
            return;
        }
        String defaultCountryIsoCode = null;
        String phoneNumber = null;
        if (options != null) {
            if (options.hasKey("defaultCountryIsoCode")) {
                defaultCountryIsoCode = options.getString("defaultCountryIsoCode");
            }
            if (options.hasKey("phoneNumber")) {
                phoneNumber = options.getString("phoneNumber");
            }
        }
        if (defaultCountryIsoCode == null) {
            defaultCountryIsoCode = "VN";
        }
        if (phoneNumber == null) {
            phoneNumber = "+84";
        }
        activity.startActivityForResult(
                AuthUI.getInstance().createSignInIntentBuilder().setAvailableProviders(Arrays.asList(
                        new AuthUI.IdpConfig.PhoneBuilder().setDefaultNumber(phoneNumber).build())).build(),
                PHONE_AUTH_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PHONE_AUTH_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                if (firebaseUser != null) {
                    firebaseUser.getIdToken(true).addOnSuccessListener(result -> {
                        String token = result.getToken();
                        String phoneNumber = firebaseUser.getPhoneNumber();
                        WritableMap _result = new WritableNativeMap();
                        _result.putString("token", token);
                        _result.putString("phoneNumber", phoneNumber);
                        if (this.currentSuccessCallback != null) {
                            this.currentSuccessCallback.invoke(_result);
                        }
                    });
                } else {
                    IdpResponse response = IdpResponse.fromResultIntent(data);
                    if (response != null) {
                        FirebaseUiException uiException = response.getError();
                        int code = 0;
                        String message = null;
                        if (uiException != null) {
                            code = uiException.getErrorCode();
                            message = uiException.getMessage();
                        }

                    }
                }
            }
        }
    }
}