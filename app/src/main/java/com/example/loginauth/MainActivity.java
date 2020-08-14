package com.example.loginauth;

import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    Button view;
    WebView webview;
    ProgressDialog pDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_main);
        view=findViewById(R.id.view);
        webview=findViewById(R.id.webview);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.setVisibility(View.GONE);
                String webviewurl = "http://www.hmritm.ac.in/wp-content/uploads/2020/04/Notice-Rapid-Spread-of-Novel-Corona-Virus-COVID-19-converted.pdf";
                webview.getSettings().setJavaScriptEnabled(true);
                if(webviewurl.contains(".pdf")){
                    webviewurl = "https://docs.google.com/gview?embedded=true&url=" + webviewurl;        }
                webview.loadUrl(webviewurl);
                webview.setWebViewClient(new WebViewClient(){
                    @Override
                    public void onPageFinished(WebView view, String url) {
                        super.onPageFinished(view, url);
                        webview.loadUrl("javascript:(function() { " +
                                "document.getElementsByClassName('ndfHFb-c4YZDc-GSQQnc-LgbsSe ndfHFb-c4YZDc-to915-LgbsSe VIpgJd-TzA9Ye-eEGnhe ndfHFb-c4YZDc-LgbsSe')[0].style.display='none'; })()");
                    }
                });

            }
        });
//        view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String format = "https://drive.google.com/viewerng/viewer?embedded=true&url=%s";
//                String fullPath = String.format(Locale.ENGLISH, format, "https://www.india.gov.in/sites/upload_files/npi/files/coi_part_full.pdf");
//                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(fullPath));
//                startActivity(browserIntent);
//            }
//        });
    }


}
