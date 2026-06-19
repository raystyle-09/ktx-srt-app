package com.ktxsrt.booking;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private WebView webView;

    // ★★★ 여기에 본인 NAS 주소를 입력하세요 ★★★
    private static final String SERVER_URL = "https://ray-disk.asuscomm.com:5443";

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = findViewById(R.id.webview);
        WebSettings s = webView.getSettings();
        s.setJavaScriptEnabled(true);
        s.setDomStorageEnabled(true);          // localStorage/세션 유지
        s.setDatabaseEnabled(true);
        s.setUseWideViewPort(true);
        s.setLoadWithOverviewMode(true);
        s.setCacheMode(WebSettings.LOAD_DEFAULT);
        s.setMediaPlaybackRequiresUserGesture(false);

        // 외부 링크도 앱 내에서 열기
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
            @Override
            public void onReceivedError(WebView view, int errorCode,
                                        String description, String failingUrl) {
                showError();
            }
        });
        webView.setWebChromeClient(new WebChromeClient());

        if (savedInstanceState != null) {
            webView.restoreState(savedInstanceState);
        } else {
            webView.loadUrl(SERVER_URL);
        }
    }

    private void showError() {
        new AlertDialog.Builder(this)
                .setTitle("연결 실패")
                .setMessage("NAS 서버에 접속할 수 없습니다.\n\n" +
                        "• NAS가 켜져 있는지\n" +
                        "• 주소(" + SERVER_URL + ")가 맞는지\n" +
                        "• 같은 네트워크이거나 외부 접속이 가능한지\n확인하세요.")
                .setPositiveButton("다시 시도", (d, w) -> webView.loadUrl(SERVER_URL))
                .setNegativeButton("종료", (d, w) -> finish())
                .setCancelable(false)
                .show();
    }

    // 뒤로가기 = 웹 히스토리 뒤로
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        webView.saveState(outState);
    }
}
