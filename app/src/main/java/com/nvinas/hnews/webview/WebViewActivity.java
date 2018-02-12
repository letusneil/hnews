package com.nvinas.hnews.webview;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.nvinas.hnews.R;
import com.nvinas.hnews.common.util.CommonUtil;
import com.wang.avi.AVLoadingIndicatorView;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * Created by nvinas on 11/02/2018.
 */

public class WebViewActivity extends AppCompatActivity {

    @BindView(R.id.webview)
    WebView webView;

    @BindView(R.id.avi_progress_indicator)
    AVLoadingIndicatorView progressIndicator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        ButterKnife.bind(this);

        initWebView();
    }

    @SuppressWarnings("SetJavaScriptEnabled")
    private void initWebView() {
        Intent intent = getIntent();
        if (intent != null) {
            String url = intent.getStringExtra(CommonUtil.Constants.INTENT_KEY_URL);
            Timber.d("Load url: %s", url);

            if (TextUtils.isEmpty(url)) {
                Timber.d("Empty url");
                finish();
                return;
            }

            if (url.endsWith(CommonUtil.Constants.PDF_FILE)) {
                url = CommonUtil.Constants.GOOGLE_PDF_VIEWER_URL + url;
            }

            webView.setVisibility(View.GONE);
            webView.getSettings().setJavaScriptEnabled(true);
            webView.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    super.onPageStarted(view, url, favicon);
                    progressIndicator.setVisibility(View.VISIBLE);
                    progressIndicator.show();
                }

                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                    progressIndicator.setVisibility(View.GONE);
                    progressIndicator.hide();
                    webView.setVisibility(View.VISIBLE);
                }
            });
            webView.loadUrl(url);
        }
    }


}
