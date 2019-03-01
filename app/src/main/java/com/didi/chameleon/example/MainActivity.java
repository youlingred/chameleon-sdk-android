package com.didi.chameleon.example;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.didi.chameleon.sdk.CmlEngine;
import com.didi.chameleon.sdk.bundle.CmlBundle;
import com.didi.chameleon.sdk.utils.Util;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";

    // 演示打开一般的URL
    private static final String URL_NORMAL = "https://www.didiglobal.com";
    // 这是一个可以正常打开的 JS_BUNDLE
//    private static final String URL_JS_BUNDLE_OK = "https://static.didialift.com/pinche/gift/chameleon-ui-builtin/web/chameleon-ui-builtin.html?cml_addr=https%3A%2F%2Fstatic.didialift.com%2Fpinche%2Fgift%2Fchameleon-ui-builtin%2Fweex%2Fchameleon-ui-builtin.js";
    private static final String URL_JS_BUNDLE_OK = "https://static.didialift.com/pinche/gift/chameleon-ui-builtin/web/chameleon-ui-builtin.html?cml_addr=http://172.22.138.92:8000/weex/cml-demo-say.js";
    // 这是一个错误的 JS_BUNDLE
    private static final String URL_JS_BUNDLE_ERR = "https://www.didiglobal.com?cml_addr=xxx.js";
    // 这是一个测试预加载的 JS_BUNDLE
    private static final String URL_JS_BUNDLE_PRELOAD = "https://static.didialift.com/pinche/gift/chameleon-ui-builtin/web/chameleon-ui-builtin.html?cml_addr=https%3A%2F%2Fstatic.didialift.com%2Fpinche%2Fgift%2Fchameleon-ui-builtin%2Fweex%2Fchameleon-ui-builtin.js";
    // 演示自定义Module 和 JS 通信, 加载本地jsbundle需设置 CmlEnvironment.CML_DEBUG = true
    private static final String URL_MODULE_DEMO = "file://local/cml-demo-say.js";
//    private static final String URL_MODULE_DEMO = "file://local/index1.js";

    private TextView txtOpenUrl;
    private TextView txtOpenJSBundle;
    private TextView txtPreload;
    private TextView txtDegrade;
    private TextView txtJsCallNative;
    private TextView txtNativeCallJs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtOpenUrl = findViewById(R.id.txt_open_url);
        txtOpenJSBundle = findViewById(R.id.txt_open_js_bundle);
        txtPreload = findViewById(R.id.txt_preload);
        txtDegrade = findViewById(R.id.txt_auto_degrade);
        txtJsCallNative = findViewById(R.id.txt_full_screen);
        txtNativeCallJs = findViewById(R.id.txt_module_native_to_js);
        txtOpenUrl.setOnClickListener(this);
        txtOpenJSBundle.setOnClickListener(this);
        txtPreload.setOnClickListener(this);
        txtDegrade.setOnClickListener(this);
        txtJsCallNative.setOnClickListener(this);
        txtNativeCallJs.setOnClickListener(this);

        // 在业务层设置预加载地址
        CmlEngine.getInstance().initPreloadList(getPreloadList());
        // 执行预加载
        CmlEngine.getInstance().performPreload();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_open_url:
                CmlEngine.getInstance().launchPage(this, URL_NORMAL, null);
                break;
            case R.id.txt_open_js_bundle:
                CmlEngine.getInstance().launchPage(this, URL_JS_BUNDLE_OK, null);
                break;
            case R.id.txt_preload:
                CmlEngine.getInstance().launchPage(this, URL_JS_BUNDLE_PRELOAD, null);
                break;
            case R.id.txt_auto_degrade:
                CmlEngine.getInstance().launchPage(this, URL_JS_BUNDLE_ERR, null);
                break;
            case R.id.txt_full_screen:
                CmlEngine.getInstance().launchPage(this, URL_MODULE_DEMO, null);
                break;
            case R.id.txt_module_native_to_js:
                startActivity(new Intent(this, CmlViewActivity.class));
                break;
        }
    }

    private List<CmlBundle> getPreloadList() {
        List<CmlBundle> cmlModels = new ArrayList<>();
        CmlBundle model = new CmlBundle();
        model.bundle = Util.parseCmlUrl(URL_JS_BUNDLE_PRELOAD);
        model.priority = 2;
        cmlModels.add(model);
        return cmlModels;
    }
}