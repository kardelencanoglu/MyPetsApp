package com.kardelen.sahipsizkahramanlar.utils;

import android.os.Handler;
import android.view.View;

public class Utils {

    public static void setUpFullscreen(View decorView){
        int uiOptions = View.SYSTEM_UI_FLAG_IMMERSIVE
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        decorView.setOnSystemUiVisibilityChangeListener
                (visibility -> {
                    if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
                        Handler h = new Handler();
                        h.postDelayed(() -> decorView.setSystemUiVisibility(uiOptions), 2000);
                    }
                });
    }
}
