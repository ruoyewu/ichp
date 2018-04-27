package com.wuruoye.ichp.base.util;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

/**
 * @Created : wuruoye
 * @Date : 2018/4/27 20:39.
 * @Description :
 */

public class ClipboardUtil {
    public static void copyText(String text, Context context) {
        ClipboardManager manager = (ClipboardManager) context
                .getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData data = ClipData.newPlainText("label", text);
        manager.setPrimaryClip(data);
    }
}
