package com.didi.chameleon.sdk.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.text.TextUtils;

import com.didi.chameleon.sdk.CmlConstant;
import com.didi.chameleon.sdk.CmlEnvironment;

public class Util {
    private static final String TAG = "sdk_util";

    public static boolean isApkDebug(Context context) {
        try {
            ApplicationInfo info = context.getApplicationInfo();
            return (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * @param url js路径
     * @return 根据协议，从主链接中解析出H5地址
     */
    public static String parseH5Url(String url) {
        String h5Url = url;
        if (!TextUtils.isEmpty(url) && url.contains("?")) {
            h5Url = url.substring(0, url.indexOf("?"));
        }
        return h5Url;
    }

    public static boolean isWebPageUrl(String url) {
        if (!url.startsWith("https://") && !url.startsWith("http://") && !url.startsWith("file://")) {
            return false;
        }
        if (!url.endsWith(".html") && !url.endsWith(".htm") && !url.endsWith(".aspx")
                && !url.endsWith(".jsp") && !url.endsWith(".php") && !url.endsWith(".perl")
                && !url.endsWith(".cgi")) {
            return false;
        }
        return true;
    }

    /**
     * @param url js路径
     * @return 根据协议，从主链接中解析出js地址
     */
    public static String parseCmlUrl(String url) {
        if (CmlEnvironment.CML_DEBUG && url.startsWith("file://")) {
            return url;
        }

        String cmlUrl = parseCmlUrl(url, CmlConstant.CML_PARAM_KEY);
        if (TextUtils.isEmpty(cmlUrl)) {
            cmlUrl = parseCmlUrl(url, CmlConstant.OLD_PARAM_KEY);
        }
        return cmlUrl;
    }

    public static String parseCmlUrl(String url, String keyWord) {
        if (TextUtils.isEmpty(url)) {
            return "";
        }
        if (!url.contains(keyWord)) {
            return "";
        }
        Uri uri = Uri.parse(url);
        String cmlUrl = null;
        try {
            cmlUrl = uri.getQueryParameter(keyWord);
            if (cmlUrl == null) {
                // 兼容url被encode过
                cmlUrl = Uri.parse(Uri.decode(url)).getQueryParameter(keyWord);
            }
        } catch (Exception e) {
            CmlLogUtil.e(TAG, "parseCmlUrl error, msg = " + e.toString());
        }
        return cmlUrl;
    }
}