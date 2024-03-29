package com.dw.webtime;

import android.content.DialogInterface;
import android.net.http.SslError;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.os.Handler;
import android.webkit.WebSettings;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.content.Intent;
import android.net.Uri;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends AppCompatActivity {

    private WebView webview;
    private ProgressBar spinner;
    private Animation fadeInAnimation;
    String mainURL = "https://www.003274.xyz";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in); // Smooth fade in

        webview = (WebView) findViewById(R.id.webView);
        spinner = (ProgressBar) findViewById(R.id.progressBar1);
        // spinner.setVisibility(View.GONE);
        webview.setWebViewClient(new CustomWebViewClient());

        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setDomStorageEnabled(true);
        webview.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        webview.setOverScrollMode(WebView.OVER_SCROLL_NEVER);
        webview.loadUrl("https://www.003274.xyz/hd");
    }

    /**
     * This allows for a splash screen
     * Hide elements once the page loads
     * Show custom error page
     * Resolve issue with SSL certificate
     **/
    private class CustomWebViewClient extends WebViewClient {
        // Handle SSL issue
        @Override
        public void onReceivedSslError(WebView view, final SslErrorHandler handler, SslError error) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setMessage(R.string.notification_error_ssl_cert_invalid);

            builder.setPositiveButton("continue", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    handler.proceed();
                }
            });

            builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    handler.cancel();
                }
            });
            final AlertDialog dialog = builder.create();
            dialog.show();
        }

        // USE THIS IF YOU WANT EXTERNAL LINKS TO OPEN IN THE DEFAULT BROWSER
         // Check for external link
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (!url.startsWith(mainURL)) {
                // Open in system browser
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                view.getContext().startActivity(intent);
                return true;
            } else {
                // Open in our WebView
                view.loadUrl(url);
                return false;
            }
        }

        // Animation on app open
        @Override
        public void onPageFinished(WebView view, String url) {
            if (!view.isShown()) {
                spinner.setVisibility(View.GONE);
                view.startAnimation(fadeInAnimation);
                view.setVisibility(view.VISIBLE);
            }
            super.onPageFinished(view, url);
        }

        // Show custom error page
        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            mainURL = view.getUrl();
            setContentView(R.layout.error);
            super.onReceivedError(view, errorCode, description, failingUrl);
        }

    }

    private boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (webview.canGoBack()) {
            webview.goBack();
        } else {
            if (doubleBackToExitPressedOnce) {
                finish();
                return;
            }
            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, R.string.exit_app, Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }
    }

    /* Retry Loading the page */
    public void tryAgain(View v) {
        setContentView(R.layout.activity_main);
        webview = (WebView) findViewById(R.id.webView);
        spinner = (ProgressBar) findViewById(R.id.progressBar1);
        webview.setWebViewClient(new CustomWebViewClient());

        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setDomStorageEnabled(true);
        webview.setOverScrollMode(WebView.OVER_SCROLL_NEVER);
        webview.loadUrl(mainURL);
    }
}
