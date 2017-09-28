package com.findchildren.avi.test.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Toast;

import com.findchildren.avi.test.R;


/**
 * Created by Avi on 27.09.2017.
 */

public class UiUtil {
    private static Toast mToast;
    FragmentTransaction fragmentTransaction;

    public static void showToast(Activity act, String msg){
        Toast.makeText(act, msg, Toast.LENGTH_SHORT).show();
    }

    public static void hideView(View view) {
        view.setVisibility(View.INVISIBLE);
    }

    public static void showView(View view) {
        view.setVisibility(View.VISIBLE);
    }


}
