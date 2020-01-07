package info.moonjava;

import android.content.Intent;

import java.util.ArrayList;
import java.util.List;

public class FirebaseCallbackManager {
    static FirebaseCallbackManager instance;
    private List<FirebaseCallbackListener> listeners = new ArrayList<>(10);

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        for (FirebaseCallbackListener listener : listeners) {
            listener.onActivityResult(requestCode, resultCode, data);
        }
    }

    void addListener(FirebaseCallbackListener listener) {
        listeners.add(listener);
    }

    void removeListener(FirebaseCallbackListener listener) {
        listeners.remove(listener);
    }

    public static FirebaseCallbackManager getInstance() {
        if (instance == null) {
            instance = new FirebaseCallbackManager();
        }
        return instance;
    }
}
