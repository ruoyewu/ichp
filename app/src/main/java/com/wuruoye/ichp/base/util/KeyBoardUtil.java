package com.wuruoye.ichp.base.util;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.wuruoye.ichp.base.App;

/**
 * Created by wuruoye on 2018/2/13.
 * this file is to
 */

public class KeyBoardUtil {

    public static void hideSoftKeyboard(EditText et) {
        InputMethodManager manager = (InputMethodManager) App.Companion.getApp()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.hideSoftInputFromWindow(et.getWindowToken(), 0);
    }

    public static void showSoftKeBoard(View view) {
        if (view.requestFocus()) {
            InputMethodManager manager = (InputMethodManager) App.Companion.getApp()
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            manager.showSoftInput(view, InputMethodManager.SHOW_FORCED);
        }
    }
}
