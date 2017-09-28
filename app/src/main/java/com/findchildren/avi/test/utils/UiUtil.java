package com.findchildren.avi.test.utils;

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

//    public static void showToast(Context context, int resString) {
//        Handler h = new Handler(Looper.getMainLooper());
//        h.post( metod(context, resString));
//    }
//
//    private static Runnable metod(final Context context, final int resString) {
//        Runnable r = new Runnable() {
//            @Override
//            public void run() {
//                if (mToast == null || !mToast.getView().isShown())
//                    mToast = Toast.makeText(context, resString, Toast.LENGTH_SHORT);
//                mToast.show();
//            }
//        };
//       return r;
//    }

    public static void hideView(View view) {
        view.setVisibility(View.INVISIBLE);
    }

    public static void showView(View view) {
        view.setVisibility(View.VISIBLE);
    }


}
