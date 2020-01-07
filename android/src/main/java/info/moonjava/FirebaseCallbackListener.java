package info.moonjava;

import android.content.Intent;

public interface FirebaseCallbackListener {
    public void onActivityResult(int requestCode, int resultCode, Intent data);
}
