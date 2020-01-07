
package info.moonjava;

import android.app.Activity;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;

import java.util.Arrays;

public class RNFirebaseAuthUiModule extends ReactContextBaseJavaModule {

    private static final String MODULE_NAME = "RNFirebaseAuthUi";
    private static ReactApplicationContext reactContext;
    private static final int PHONE_AUTH_REQUEST_CODE = 2020;
    private static final String NO_ACTIVITY_ERROR_CODE = "NO_ACTIVTY";
    private static final String REQUEST_EXISTING_ERROR_CODE = "REQUEST_EXISTING";
    private static final String CANCEL_ERROR_CODE = "CANCEL";
    private static final String NO_RESPONSE_ERROR_CODE = "NO_RESPONSE";
    private static final String GET_ID_TOKEN_FAILED_ERROR_CODE = "GET_ID_TOKEN_FAILED";
    private Promise currentPromise;

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
    public void signInWithPhoneNumber(ReadableMap options, Promise promise) {
        if (currentPromise != null) {
            currentPromise.reject(REQUEST_EXISTING_ERROR_CODE, "Has another request processing");
            currentPromise = null;
        }
        final Activity activity = getCurrentActivity();
        if (activity == null) {
            promise.reject(NO_ACTIVITY_ERROR_CODE, "No active activity");
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
        currentPromise = promise;
        activity.startActivityForResult(
                AuthUI.getInstance().createSignInIntentBuilder().setAvailableProviders(Arrays.asList(
                        new AuthUI.IdpConfig.PhoneBuilder().setDefaultNumber(phoneNumber).build())).build(),
                PHONE_AUTH_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == PHONE_AUTH_REQUEST_CODE) {
                FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                if (firebaseUser != null) {
                    firebaseUser.getIdToken(true).addOnSuccessListener(result -> {
                        String token = result.getToken();
                        String phoneNumber = firebaseUser.getPhoneNumber();
                        WritableMap _result = new WritableNativeMap();
                        _result.putString("token", token);
                        _result.putString("phoneNumber", phoneNumber);
                        currentPromise.resolve(_result);
                        currentPromise = null;
                    }).addOnFailureListener(_throwable -> {
                        currentPromise.reject(GET_ID_TOKEN_FAILED_ERROR_CODE, "Get Id token failed", _throwable);
                        currentPromise = null;
                    }).addOnCanceledListener(() -> {
                        currentPromise.reject(CANCEL_ERROR_CODE, "Get Id token canceled");
                        currentPromise = null;
                    });
                }
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
                    currentPromise.reject(code + "", message);
                    currentPromise = null;
                } else {
                    currentPromise.reject(NO_RESPONSE_ERROR_CODE, "Error with no response");
                    currentPromise = null;
                }
            }
        } else {
            currentPromise.reject(CANCEL_ERROR_CODE, "User cancels");
            currentPromise = null;
        }
    }
}